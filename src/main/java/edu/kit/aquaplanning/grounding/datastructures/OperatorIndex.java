package edu.kit.aquaplanning.grounding.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;

/**
 * Lookup structure for applicable actions given a state in a lifted setting.
 * Designed to consider only a small subset of all possible argument
 * combinations when dealing with operators with many parameters.
 */
public class OperatorIndex {

	/**
	 * Holds some lookup information about a (lifted) operator.
	 */
	private class OperatorInfo {
		/**
		 * The instantiated operators so far originating from this operator
		 */
		public ArgumentNode instantiatedOperators;
		/**
		 * The flat atomic conditions that are contained in the operator
		 */
		public List<Condition> conditions;
		/**
		 * Maps each argument name to its position in the argument list
		 */
		public Map<String, Integer> argPositions;
		/**
		 * True iff this.conditions contains any non-negated conditions
		 */
		public boolean hasPositiveConditions;

		public OperatorInfo(PlanningProblem p, Operator op) {
			this.conditions = new ArrayList<>();
			this.hasPositiveConditions = getFlatPreconditions(op, this.conditions);
			this.instantiatedOperators = new ArgumentNode(argumentIds);
			this.argPositions = new HashMap<>();
			for (int i = 0; i < op.getArguments().size(); i++) {
				argPositions.put(op.getArguments().get(i).getName(), i);
			}
		}
	}

	/**
	 * Maps the name of an operator to the argument combinations with which it has
	 * already been instantiated.
	 */
	private Map<String, OperatorInfo> operatorInformation;
	/**
	 * Maps the name of a predicate to operators which may become applicable if some
	 * condition of that predicate becomes true.
	 */
	private Map<String, List<Operator>> negPredicateOperatorMap;
	private Map<String, List<Operator>> posPredicateOperatorMap;
	/**
	 * List of operators which have no preconditions at all, or no simple (i.e.
	 * conjunctive) preconditions.
	 */
	private List<Operator> operatorsWithoutPreconditions;
	/**
	 * Maps each constant in the problem to a positive ID.
	 */
	private Map<String, Integer> argumentIds;

	private PlanningProblem p;

	public OperatorIndex(PlanningProblem p) {

		this.operatorInformation = new HashMap<>();
		this.posPredicateOperatorMap = new HashMap<>();
		this.negPredicateOperatorMap = new HashMap<>();
		this.operatorsWithoutPreconditions = new ArrayList<>();
		this.p = p;

		// Initialize argument IDs
		argumentIds = new HashMap<>();
		int id = 1;
		for (Argument constant : p.getConstants()) {
			argumentIds.put(constant.getName(), id++);
		}

		// For each operator
		opLoop: for (Operator op : p.getOperators()) {

			// Initialize and gather some lookup data on the operator
			OperatorInfo info = new OperatorInfo(p, op);
			operatorInformation.put(op.getName(), info);

			// Process preconditions
			List<AbstractCondition> preconds = new ArrayList<>();
			preconds.add(op.getPrecondition());
			for (int preIdx = 0; preIdx < preconds.size(); preIdx++) {
				AbstractCondition pre = preconds.get(preIdx);
				switch (pre.getConditionType()) {
				case atomic:
					Condition cond = (Condition) pre;
					if (cond.getPredicate().isDerived())
						break;
					if (cond.getPredicate().getName().equals("="))
						break;
					if (cond.isNegated())
						break;
					Map<String, List<Operator>> predicateOperatorMap = cond.isNegated() ? negPredicateOperatorMap
							: posPredicateOperatorMap;
					if (!predicateOperatorMap.containsKey(cond.getPredicate().getName())) {
						predicateOperatorMap.put(cond.getPredicate().getName(), new ArrayList<>());
					}
					predicateOperatorMap.get(cond.getPredicate().getName()).add(op);
					continue opLoop;
				case negation:
					break;
				case conjunction:
					ConditionSet set = (ConditionSet) pre;
					preconds.addAll(set.getConditions());
					break;
				default:
					break;
				}
			}
			// No actual precondition added:
			// add operator to operators without precondition
			operatorsWithoutPreconditions.add(op);
		}
	}

	/**
	 * Given a state in a relaxed and lifted setting, returns all actions which are
	 * applicable in that state and have not been returned as applicable before.
	 */
	public List<Operator> getRelaxedApplicableLiftedActions(LiftedState s) {

		// Find basic operators which may be applicable in some instantiation
		List<Operator> filteredOps = new ArrayList<>();
		for (String p : s.getOccurringPredicates(/* negated= */false)) {
			List<Operator> ops = posPredicateOperatorMap.get(p);
			if (ops != null)
				filteredOps.addAll(ops);
		}
		filteredOps.addAll(operatorsWithoutPreconditions);

		// Final structure of applicable actions
		List<Operator> applicableOps = new ArrayList<>();

		// For each operator which may be instantiateable
		for (Operator op : filteredOps) {

			OperatorInfo info = operatorInformation.get(op.getName());

			List<ArgumentAssignment> partialArgAssignments = new ArrayList<>();
			partialArgAssignments.add(new ArgumentAssignment(op.getArguments().size()));

			// Get flat list of (positive or negative) conditions
			List<Condition> flatPreconds = info.conditions;

			// For each parameter position, contains the possible arguments there
			List<Set<Argument>> eligibleArgumentSets = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++)
				eligibleArgumentSets.add(new HashSet<>());

			// For each parameter position, contains the impossible arguments there
			List<Set<Argument>> ineligibleArgumentSets = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++)
				ineligibleArgumentSets.add(new HashSet<>());

			// For each parameter position, "true" if the argument is not constrained
			// by any of the preconditions
			boolean[] unconstrainedArgs = new boolean[op.getArguments().size()];
			for (int i = 0; i < unconstrainedArgs.length; i++) {
				unconstrainedArgs[i] = true;
			}

			// For each of the operator's simple preconditions
			for (Condition pre : flatPreconds) {

				if (pre.isNegated())
					continue;

				String predicateName = pre.getPredicate().getName();

				List<Set<Argument>> eligibleArgs = new ArrayList<>();
				for (int pos = 0; pos < op.getArguments().size(); pos++)
					eligibleArgs.add(new HashSet<>());

				// For each condition in the current state which satisfies pre
				// (Skip equality predicates and negative conditions for now)
				if (!predicateName.equals("=")) {
					for (Condition trueCondition : s.getConditions(predicateName, pre.isNegated())) {

						// For each of the state condition's arguments
						for (int condArgIdx = 0; condArgIdx < trueCondition.getNumArgs(); condArgIdx++) {
							Argument opArg = pre.getArguments().get(condArgIdx);
							if (opArg.isConstant())
								continue;

							int opArgIdx = op.getArguments().indexOf(opArg);
							Argument constArg = trueCondition.getArguments().get(condArgIdx);

							// Insert constArg at position opArgIdx
							// into the action's possible arguments
							eligibleArgs.get(opArgIdx).add(constArg);
							unconstrainedArgs[opArgIdx] = false;
						}
					}
				}

				// Add new information to (in)eligible arguments of the operator
				for (int pos = 0; pos < op.getArguments().size(); pos++) {

					// For each problem constant of fitting type
					for (Argument constant : p.getConstants()) {
						if (p.isArgumentOfType(constant, op.getArgumentTypes().get(pos))) {
							if (unconstrainedArgs[pos] || eligibleArgs.get(pos).contains(constant)) {
								// This constant is eligible at this position
								eligibleArgumentSets.get(pos).add(constant);
							} else if (pre.getArguments().contains(op.getArguments().get(pos))
									&& !predicateName.equals("=")) {
								// The operator argument at this position is constrained by the precondition,
								// but this constant is not part of the eligible arguments:
								// This constant cannot occur at this position
								ineligibleArgumentSets.get(pos).add(constant);
							}
						}
					}
				}
			}

			// Compile a single list of eligible arguments for each parameter position
			List<List<Argument>> eligibleArguments = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++) {
				List<Argument> args = new ArrayList<>();

				// For each problem constant of fitting type
				for (Argument constant : p.getConstants()) {
					if (p.isArgumentOfType(constant, op.getArgumentTypes().get(pos))) {

						if (!info.hasPositiveConditions) {
							// No positive preconditions: any constant of correct type is allowed
							args.add(constant);
						} else if (eligibleArgumentSets.get(pos).contains(constant)
								&& !ineligibleArgumentSets.get(pos).contains(constant)) {
							// Constant is eligible at this position
							args.add(constant);
						}
					}
				}
				eligibleArguments.add(args);
			}

			// Search data structure: stack of partial argument assignments
			// and corresponding bitvectors indicating which of the operator's conditions
			// were already checked
			Stack<ArgumentAssignment> assignmentStack = new Stack<>();
			Stack<boolean[]> checkedConditionsStack = new Stack<>();
			assignmentStack.push(new ArgumentAssignment(op.getArguments().size()));
			checkedConditionsStack.push(new boolean[flatPreconds.size()]);

			Map<String, Integer> argIndices = info.argPositions;

			// Find a suitable order of which arguments to instantiate first
			int[] orderedArgIndices = new int[op.getArguments().size()];
			List<Argument> args = new ArrayList<>();
			args.addAll(op.getArguments());
			String opStr = op.getPrecondition().toString();
			args.sort((arg1, arg2) -> {
				// Sort arguments in decreasing order by the amount
				// of occurrences in the operator's preconditions
				int occ1 = opStr.length() - opStr.replace(arg1.getName(), "").length();
				int occ2 = opStr.length() - opStr.replace(arg2.getName(), "").length();
				return occ1 - occ2;
			});
			int i = 0;
			for (Argument arg : args) {
				orderedArgIndices[i++] = argIndices.get(arg.getName());
			}

			// Explore all potentially valid argument combinations depth-first,
			// pruning wherever some precondition becomes unsatisfiable
			while (!assignmentStack.isEmpty()) {
				ArgumentAssignment partialAssignment = assignmentStack.pop();
				boolean[] checkedConditions = checkedConditionsStack.pop();
				int decisionLevel = partialAssignment.getDecisionLevel();

				if (decisionLevel == op.getArguments().size()) {
					// Assignment is complete

					// Has this operator not been instantiated yet?
					args = partialAssignment.toList();
					if (!info.instantiatedOperators.contains(args)) {

						// Create and add new operator
						Operator applicableOp = op.getOperatorWithGroundArguments(args);
						applicableOps.add(applicableOp);

						// Remember that this operator has been instantiated
						info.instantiatedOperators.add(args);
					}

				} else {

					// Assignment is not complete yet: decide on next argument
					int argPos = orderedArgIndices[decisionLevel];
					for (Argument arg : eligibleArguments.get(argPos)) {
						ArgumentAssignment newAssignment = new ArgumentAssignment(partialAssignment);
						boolean[] newCheckedConditions = Arrays.copyOf(checkedConditions, checkedConditions.length);
						newAssignment.set(argPos, arg);

						// Is the assignment consistent up to now?
						boolean holds = true;
						precondLoop: for (int p = 0; p < flatPreconds.size(); p++) {
							Condition pre = flatPreconds.get(p);
							if (newCheckedConditions[p])
								continue;

							// Build precondition with according arguments
							final Condition c = new Condition(pre.getPredicate(), pre.isNegated());
							for (int condArgIdx = 0; condArgIdx < pre.getNumArgs(); condArgIdx++) {
								Argument condArg = pre.getArguments().get(condArgIdx);
								int opArgIdx = argIndices.getOrDefault(condArg.getName(), -1);
								if (opArgIdx >= 0) {
									if (newAssignment.get(opArgIdx) == null) {
										// not instantiated yet -- skip condition
										continue precondLoop;
									} else {
										c.addArgument(newAssignment.get(opArgIdx));
									}
								} else {
									c.addArgument(condArg);
								}
							}

							// Does the precondition hold?
							if (!s.holds(c)) {
								holds = false;
								break;
							} else {
								newCheckedConditions[p] = true;
							}
						}
						if (holds) {
							// New assignment is still consistent;
							// Remember partial assignment for further exploration
							assignmentStack.push(newAssignment);
							checkedConditionsStack.push(newCheckedConditions);
						} // else: inconsistent assignment, discard
					}
				}
			}
		}

		return applicableOps;
	}

	private boolean getFlatPreconditions(Operator op, List<Condition> result) {

		List<AbstractCondition> preconds = new ArrayList<>();
		preconds.add(op.getPrecondition());
		boolean hasPositivePreconds = false;
		for (int preIdx = 0; preIdx < preconds.size(); preIdx++) {
			AbstractCondition pre = preconds.get(preIdx);
			switch (pre.getConditionType()) {
			case conjunction:
				preconds.addAll(((ConditionSet) pre).getConditions());
				break;
			case atomic:
				Condition opCond = (Condition) pre;
				if (opCond.getPredicate().isDerived()) {
					// Derived predicate -- ignore
					break;
				} else {
					result.add(opCond);
					if (!opCond.isNegated())
						hasPositivePreconds = true;
					break;
				}
			default:
				break;
			}
		}
		return hasPositivePreconds;
	}

	public Map<String, Integer> getArgumentIds() {
		return argumentIds;
	}
}
