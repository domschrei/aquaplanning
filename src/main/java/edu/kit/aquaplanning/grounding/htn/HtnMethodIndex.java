package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.grounding.datastructures.ArgumentAssignment;
import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.model.ground.htn.Reduction;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.htn.Constraint;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;

public class HtnMethodIndex {

	private PlanningProblem p;
	private PlanningGraphGrounder grounder;

	private Set<String> primitiveTaskNames;
	private Set<String> actionStrings;
	private LiftedState convergedState;

	// Stores method->reductions mappings that were already computed
	private Map<Method, List<Reduction>> reductions;

	private class PartiallyGroundMethod {
		ArgumentAssignment assignment;
		Method method;

		public PartiallyGroundMethod(ArgumentAssignment assignment, Method method) {
			this.assignment = assignment;
			this.method = method;
		}
	}

	public HtnMethodIndex(PlanningGraphGrounder grounder, LiftedState convergedState,
			List<Operator> instantiatedOperators) {

		this.grounder = grounder;
		this.p = grounder.getProblem();
		this.primitiveTaskNames = new HashSet<>();
		this.actionStrings = new HashSet<>();

		this.convergedState = convergedState;
		for (Operator op : instantiatedOperators) {
			primitiveTaskNames.add(op.getName());
			actionStrings.add(op.toActionString());
		}

		this.reductions = new HashMap<>();
	}

	public List<Reduction> getRelevantReductions(List<Method> methods) {

		List<Reduction> reductions = new ArrayList<>();
		for (Method m : methods) {
			reductions.addAll(getRelevantReductions(m));
		}
		return reductions;
	}

	public List<Reduction> getRelevantReductions(Method m) {

		List<Reduction> newReductions = new ArrayList<>();

		if (reductions.containsKey(m)) {
			return reductions.get(m);
		}
		if (!checkConsistency(m)) {
			return new ArrayList<>();
		}
		if (m.getImplicitArguments().size() == 0) {
			m = simplify(m);
			if (m != null) {
				newReductions.add(new Reduction(m, c -> grounder.toPrecondition(c, false)));
				reductions.put(m, newReductions);

				m = simplify(m);
				if (m != null) {
					List<Reduction> simplifiedList = new ArrayList<>();
					simplifiedList.add(new Reduction(m, c -> grounder.toPrecondition(c, false)));
					return simplifiedList;
				} else {
					return new ArrayList<>();
				}
			}

		}

		List<List<Argument>> eligibleConstants = new ArrayList<>();
		for (Argument arg : m.getImplicitArguments()) {
			List<Argument> constants = new ArrayList<>();
			for (Argument constant : p.getConstants()) {
				if (p.isArgumentOfType(constant, arg.getType())) {
					constants.add(constant);
				}
			}
			eligibleConstants.add(constants);
		}

		Map<String, Integer> argIndices = new HashMap<>();
		int pos = 0;
		for (Argument arg : m.getImplicitArguments()) {
			argIndices.put(arg.getName(), pos++);
		}

		List<Argument> sortedArgs = new ArrayList<>();
		sortedArgs.addAll(m.getImplicitArguments());
		Map<Argument, Float> argOccurrences = getArgumentOccurrences(m);
		int[] orderedArgIndices = new int[m.getImplicitArguments().size()];
		sortedArgs.sort((arg1, arg2) -> {
			// Sort arguments in decreasing order by the amount
			// of occurrences in constraints and subtasks
			float occ1 = argOccurrences.get(arg1);
			float occ2 = argOccurrences.get(arg2);
			return (int) (1000 * (occ2 - occ1));
		});
		int idx = 0;
		for (Argument arg : sortedArgs) {
			orderedArgIndices[idx++] = argIndices.get(arg.getName());
		}

		List<Argument> implicitArgs = m.getImplicitArguments();
		ArgumentAssignment initAssignment = new ArgumentAssignment(implicitArgs.size());
		Stack<PartiallyGroundMethod> assignmentStack = new Stack<>();
		assignmentStack.push(new PartiallyGroundMethod(initAssignment,
				m.getMethodBoundToArguments(m.getExplicitArguments(), m.getImplicitArguments())));

		while (!assignmentStack.isEmpty()) {

			PartiallyGroundMethod pgm = assignmentStack.pop();
			ArgumentAssignment assignment = pgm.assignment;
			Method method = pgm.method;
			if (assignment.getDecisionLevel() == implicitArgs.size()) {

				// Finished
				method = simplify(method);
				if (method != null) {
					try {
						newReductions.add(new Reduction(method, c -> grounder.toPrecondition(c, false)));
					} catch (IllegalArgumentException e) {
						continue;
					}
				}

			} else {

				// Choose an argument assignment
				int argPos = orderedArgIndices[assignment.getDecisionLevel()];
				Argument arg = implicitArgs.get(argPos);
				for (Argument constant : eligibleConstants.get(argPos)) {
					PartiallyGroundMethod newPgm = extendAssignment(pgm, argPos, arg, constant);
					if (newPgm != null) {
						assignmentStack.push(newPgm);
					}
				}
			}
		}

		reductions.put(m, newReductions);
		return newReductions;
	}

	private PartiallyGroundMethod extendAssignment(PartiallyGroundMethod pgm, int argPos, Argument refArg,
			Argument argVal) {

		ArgumentAssignment newAssignment = new ArgumentAssignment(pgm.assignment);
		newAssignment.set(argPos, argVal);
		Method newMethod = pgm.method.copy();
		newMethod.setImplicitArgument(refArg, argVal);

		if (checkConsistency(newMethod)) {
			return new PartiallyGroundMethod(newAssignment, newMethod);
		} else {
			return null;
		}
	}

	private boolean checkConsistency(Method method) {

		// If the method contains any primitive tasks,
		// see if the respective action occurs in the problem
		for (Task t : method.getSubtasks()) {
			if (primitiveTaskNames.contains(t.getName())) {
				// Subtask is primitive
				if (t.getArguments().stream().allMatch(arg -> arg.isConstant())) {
					// Subtask is fully instantiated
					if (!actionStrings.contains(t.toTaskString())) {
						// Action does not occur
						return false;
					}
				}
			}
		}

		// If the method contains any constraints,
		// see if all pos. conditions exist in the lifted superstate
		for (Constraint c : method.getConstraints()) {
			AbstractCondition cond = c.getCondition();
			List<AbstractCondition> conditions = new ArrayList<>();
			conditions.add(cond);
			for (int i = 0; i < conditions.size(); i++) {
				cond = conditions.get(i);
				switch (cond.getConditionType()) {
				case atomic:
					Condition atom = (Condition) cond;
					if (atom.getArguments().stream().anyMatch(arg -> !arg.isConstant())) {
						// Condition is not fully instantiated
						continue;
					}
					if (!convergedState.holds(atom)) {
						return false;
					}
					break;
				case conjunction:
					conditions.addAll(((ConditionSet) cond).getConditions());
					break;
				default:
					break;
				}
			}
		}

		return true;
	}

	public Method simplify(Method method) {

		if (!checkConsistency(method)) {
			return null;
		}
		Method newMethod = method.copy();
		newMethod.getConstraints().clear();
		for (Constraint c : method.getConstraints()) {
			AbstractCondition simplified = grounder.simplifyRigidConditions(c.getCondition(), convergedState,
					"method-constr");
			if (simplified == null) {
				// Condition is not satisfiable: method is invalid
				return null;
			}

			Constraint newC = c.copy();
			newC.setCondition(simplified);
			newMethod.addConstraint(newC);
		}
		return newMethod;
	}

	private Map<Argument, Float> getArgumentOccurrences(Method m) {

		Map<Argument, Float> argOccurrences = new HashMap<>();
		for (Argument arg : m.getExplicitArguments())
			argOccurrences.put(arg, 0f);
		for (Argument arg : m.getImplicitArguments())
			argOccurrences.put(arg, 0f);

		for (Task t : m.getSubtasks()) {
			if (primitiveTaskNames.contains(t.getName())) {
				// Primitive task
				for (Argument arg : t.getArguments()) {
					argOccurrences.put(arg, argOccurrences.getOrDefault(arg, 0f) + 1f / t.getArguments().size());
				}
			}
		}
		for (Constraint c : m.getConstraints()) {
			AbstractCondition cond = c.getCondition();
			List<AbstractCondition> conditions = new ArrayList<>();
			conditions.add(cond);
			for (int i = 0; i < conditions.size(); i++) {
				cond = conditions.get(i);
				switch (cond.getConditionType()) {
				case atomic:
					Condition atom = (Condition) cond;
					for (Argument arg : atom.getArguments()) {
						argOccurrences.put(arg, argOccurrences.getOrDefault(arg, 0f) + 1f / atom.getNumArgs());
					}
					break;
				case conjunction:
					conditions.addAll(((ConditionSet) cond).getConditions());
					break;
				default:
					break;
				}
			}
		}
		return argOccurrences;
	}
}
