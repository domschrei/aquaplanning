package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.abego.treelayout.internal.util.java.lang.string.StringUtil;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.Negation;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;

public class OperatorIndex {

	private Map<Predicate, List<Operator>> predicateOperatorMap;
	private List<Operator> operatorsWithoutPreconditions;
	private Map<Operator, Map<String, Integer>> operatorArgPositions;
	
	private PlanningProblem p;
	
	public OperatorIndex(PlanningProblem p) {
		
		this.predicateOperatorMap = new HashMap<>();
		this.operatorsWithoutPreconditions = new ArrayList<>();
		this.operatorArgPositions = new HashMap<>();
		this.p = p;
		
		opLoop: for (Operator op : p.getOperators()) {
			
			// Process arguments
			Map<String, Integer> argPositions = new HashMap<>();
			for (int i = 0; i < op.getArguments().size(); i++) {
				argPositions.put(op.getArguments().get(i).getName(), i);
			}
			operatorArgPositions.put(op, argPositions);
			
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
					if (!predicateOperatorMap.containsKey(cond.getPredicate())) {
						predicateOperatorMap.put(cond.getPredicate(), new ArrayList<>());
					}
					predicateOperatorMap.get(cond.getPredicate()).add(op);
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
	
	public List<Operator> getRelaxedApplicableLiftedActions(LiftedState s) {
		
		// Find basic operators which may be applicable in some instantiation
		List<Operator> filteredOps = new ArrayList<>();
		for (Predicate p : s.getOccurringPredicates()) {
			List<Operator> ops = predicateOperatorMap.get(p);
			if (ops != null)
				filteredOps.addAll(ops);			
		}
		filteredOps.addAll(operatorsWithoutPreconditions);
		
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
					} else {
						flatPreconds.add(opCond);
						break;
					}
				default:
					break;				
				}
			}
				
			List<Set<Argument>> eligibleArgumentSets = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++)
				eligibleArgumentSets.add(new HashSet<>());
			
			List<Set<Argument>> ineligibleArgumentSets = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++)
				ineligibleArgumentSets.add(new HashSet<>());
			
			boolean[] unconstrainedArgs = new boolean[op.getArguments().size()];
			for (int i = 0; i < unconstrainedArgs.length; i++) {
				unconstrainedArgs[i] = true;
			}
			
			for (Condition pre : flatPreconds) {
				
				List<Set<Argument>> eligibleArgs = new ArrayList<>();
				for (int pos = 0; pos < op.getArguments().size(); pos++)
					eligibleArgs.add(new HashSet<>());
				
				// For each condition in the current state which satisfies pre
				for (Condition trueCondition : s.getConditions(pre.getPredicate())) {
					
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
				
				for (int pos = 0; pos < op.getArguments().size(); pos++) {
					
					for (Argument constant : p.getConstants()) {
						if (p.isArgumentOfType(constant, op.getArgumentTypes().get(pos))) {
							
							if (unconstrainedArgs[pos] || eligibleArgs.get(pos).contains(constant)) {
								eligibleArgumentSets.get(pos).add(constant);
							} else if (pre.getArguments().contains(op.getArguments().get(pos))) {
								ineligibleArgumentSets.get(pos).add(constant);
							}
						}
					}					
				}
			}
			
			// Sets to list
			List<List<Argument>> eligibleArguments = new ArrayList<>();
			for (int pos = 0; pos < op.getArguments().size(); pos++) {
				List<Argument> args = new ArrayList<>();
				for (Argument constant : p.getConstants()) {
					if (p.isArgumentOfType(constant, op.getArgumentTypes().get(pos))) {
						
						if (flatPreconds.isEmpty()) {
							args.add(constant);
						} else if (eligibleArgumentSets.get(pos).contains(constant) 
								&& !ineligibleArgumentSets.get(pos).contains(constant)) {
							args.add(constant);
						}
					}
				}
				eligibleArguments.add(args);
			}
			
			Stack<ArgumentAssignment> assignmentStack = new Stack<>();
			assignmentStack.push(new ArgumentAssignment(op.getArguments().size()));
			Map<String, Integer> argIndices = operatorArgPositions.get(op);
			
			// Find a suitable order of which arguments to instantiate first
			// (sort arguments decreasingly by the amount of occurrences in preconditions)
			int[] orderedArgIndices = new int[op.getArguments().size()];
			List<Argument> args = new ArrayList<>();
			args.addAll(op.getArguments());
			String opStr = op.getPrecondition().toString();
			args.sort((arg1, arg2) -> {
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
					
					Operator applicableOp = op.getOperatorWithGroundArguments(partialAssignment.toList());
					applicableOps.add(applicableOp);
					
				} else {
					
					// Assignment is not complete yet
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
							if (!s.getConditions(pre.getPredicate()).contains(c)) {
								holds = false;
								break;
							}
						}
						if (holds) {
							assignmentStack.push(newAssignment);
						}				
					}
				}
			}
		}
		
		return applicableOps;
	}
	
	private List<ArgumentAssignment> replaceVariableArguments(Operator op, List<ArgumentAssignment> assignments) {
		
		List<ArgumentAssignment> finalAssignments = new ArrayList<>();
		for (int assignmentIdx = 0; assignmentIdx < assignments.size(); assignmentIdx++) {
			ArgumentAssignment assignment = assignments.get(assignmentIdx);
			boolean containsVar = false;
			for (int argIdx = 0; argIdx < assignment.size(); argIdx++) {
				Argument arg = assignment.get(argIdx);
				if (arg == null || !arg.isConstant()) {
					containsVar = true;
					
					for (Argument constArg : p.getConstants()) {
						if (p.isArgumentOfType(constArg, op.getArgumentTypes().get(argIdx))) {
							ArgumentAssignment newAssignment = new ArgumentAssignment(assignment);
							newAssignment.set(argIdx, constArg);
							finalAssignments.add(newAssignment);
						}
					}
				}
			}
			if (!containsVar) {
				finalAssignments.add(assignment);
			}
		}
		return finalAssignments;
	}
	
	private void print(Argument[] assignment) {
		for (Argument arg : assignment) {
			System.out.print("  " + arg);															
		}
		System.out.println();
	}
	
	private void print(List<Argument[]> assignments) {
		for (Argument[] a : assignments) {
			print(a);
		}
	}
}
