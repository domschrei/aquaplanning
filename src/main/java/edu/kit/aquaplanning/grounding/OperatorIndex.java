package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.Negation;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;

/**
 * Lookup structure for applicable actions given a state in a lifted setting.
 */
public class OperatorIndex {

	/**
	 * Maps the name of an operator to the arguments with which it has
	 * already been instantiated.
	 */
	private Map<String, ArgumentNode> instantiatedOperators;
	/**
	 * Maps the name of a predicate to operators which may become applicable
	 * if some condition of that predicate becomes true.
	 */
	private Map<String, List<Operator>> predicateOperatorMap;
	/**
	 * List of operators which have no preconditions at all, or
	 * no simple (i.e. conjunctive) preconditions.
	 */
	private List<Operator> operatorsWithoutPreconditions;
	/**
	 * Maps the name of an operator to a mapping of (argument names
	 * to their position within the operator's parameters).
	 */
	private Map<String, Map<String, Integer>> operatorArgPositions;
	/**
	 * Maps each constant in the problem to a positive ID.
	 */
	private Map<String, Integer> argumentIds;
	
	private PlanningProblem p;
	
	public OperatorIndex(PlanningProblem p) {
		
		this.instantiatedOperators = new HashMap<>();
		this.predicateOperatorMap = new HashMap<>();
		this.operatorsWithoutPreconditions = new ArrayList<>();
		this.operatorArgPositions = new HashMap<>();
		this.p = p;
		
		// For each operator
		opLoop: for (Operator op : p.getOperators()) {
			
			// Process arguments
			Map<String, Integer> argPositions = new HashMap<>();
			for (int i = 0; i < op.getArguments().size(); i++) {
				argPositions.put(op.getArguments().get(i).getName(), i);
			}
			operatorArgPositions.put(op.getName(), argPositions);
			
			// Process preconditions
			List<AbstractCondition> preconds = new ArrayList<>();
			preconds.add(op.getPrecondition());
			for (int preIdx = 0; preIdx < preconds.size(); preIdx++) {
				AbstractCondition pre = preconds.get(preIdx);
				switch (pre.getConditionType()) {
				case atomic:
					Condition cond = (Condition) pre;
					if (cond.isNegated())
						break;
					if (cond.getPredicate().isDerived())
						break;
					if (cond.getPredicate().getName().equals("="))
						break;
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
		
		// Initialize argument IDs
		argumentIds = new HashMap<>();
		int id = 1;
		for (Argument constant : p.getConstants()) {
			argumentIds.put(constant.getName(), id++);
		}
	}
	
	/**
	 * Given a state in a lifted setting, returns all actions which are applicable
	 * in that state and have not been returned as applicable before.
	 */
	public List<Operator> getRelaxedApplicableLiftedActions(LiftedState s) {
		
		// Find basic operators which may be applicable in some instantiation
		List<Operator> filteredOps = new ArrayList<>();
		for (String p : s.getOccurringPredicates()) {
			List<Operator> ops = predicateOperatorMap.get(p);
			if (ops != null)
				filteredOps.addAll(ops);			
		}
		filteredOps.addAll(operatorsWithoutPreconditions);
		
		// Final structure of applicable actions
		List<Operator> applicableOps = new ArrayList<>();
		
		// For each operator which may be instantiateable
		for (Operator op : filteredOps) {
			
			List<ArgumentAssignment> partialArgAssignments = new ArrayList<>();
			partialArgAssignments.add(new ArgumentAssignment(op.getArguments().size()));
			
			// Compile preconditions into flat list of positive conditions
			List<Condition> flatPreconds = new ArrayList<>();
			List<AbstractCondition> preconds = new ArrayList<>();
			preconds.add(op.getPrecondition());
			for (int preIdx = 0; preIdx < preconds.size(); preIdx++) {
				AbstractCondition pre = preconds.get(preIdx);
				boolean negated = false;
				switch (pre.getConditionType()) {
				case conjunction:
					preconds.addAll(((ConditionSet) pre).getConditions());
					break;
				case negation:
					negated = true;
					pre = ((Negation) pre).getChildCondition();
				case atomic:
					Condition opCond = (Condition) pre;
					negated ^= opCond.isNegated();
					if (negated) {
						// Negated condition
						break;
					} else if (opCond.getPredicate().isDerived()) {
						// Derived predicate -- ignore
						break;
					} else {
						flatPreconds.add(opCond);
						break;
					}
				default:
					break;				
				}
			}
			
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
				String predicateName = pre.getPredicate().getName();
				
				List<Set<Argument>> eligibleArgs = new ArrayList<>();
				for (int pos = 0; pos < op.getArguments().size(); pos++)
					eligibleArgs.add(new HashSet<>());
				
				// For each condition in the current state which satisfies pre
				// (Skip equality predicates for now)
				if (!predicateName.equals("=")) {
					for (Condition trueCondition : s.getConditions(predicateName)) {
					
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
						
						if (flatPreconds.isEmpty()) {
							// No preconditions: any constant of correct type is allowed
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
			
			Stack<ArgumentAssignment> assignmentStack = new Stack<>();
			assignmentStack.push(new ArgumentAssignment(op.getArguments().size()));
			Map<String, Integer> argIndices = operatorArgPositions.get(op.getName());
			
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
			
			while (!assignmentStack.isEmpty()) {
				ArgumentAssignment partialAssignment = assignmentStack.pop();
				int decisionLevel = partialAssignment.getDecisionLevel();
				
				if (decisionLevel == op.getArguments().size()) {
					// Assignment is complete
					
					// Has this operator not been instantiated yet?
					args = partialAssignment.toList();
					if (!instantiatedOperators.getOrDefault(op.getName(), 
							new ArgumentNode(argumentIds)).contains(args)) {
						
						// Create and add new operator
						Operator applicableOp = op.getOperatorWithGroundArguments(args);
						applicableOps.add(applicableOp);
						
						// Remember that this operator has been instantiated
						if (!instantiatedOperators.containsKey(op.getName())) {
							instantiatedOperators.put(op.getName(), new ArgumentNode(argumentIds));
						}
						instantiatedOperators.get(op.getName()).add(args);
					}
					
				} else {
					
					// Assignment is not complete yet: decide on next argument
					int argPos = orderedArgIndices[decisionLevel];
					for (Argument arg : eligibleArguments.get(argPos)) {
						ArgumentAssignment newAssignment = new ArgumentAssignment(partialAssignment);
						newAssignment.set(argPos, arg);
						
						// Is the assignment consistent up to now?
						boolean holds = true;
						precondLoop: for (Condition pre : flatPreconds) {
							
							// Build precondition with according arguments
							final Condition c = new Condition(pre.getPredicate());
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
							}
						}
						if (holds) {
							// Remember partial assignment for further exploration
							assignmentStack.push(newAssignment);
						}				
					}
				}
			}
		}
		
		return applicableOps;
	}
}
