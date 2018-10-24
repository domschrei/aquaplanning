package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;

public class ReachableActionsApproximationGrounder implements Grounder {

	private PlanningProblem problem;
	
	private List<Argument> constants;
	private Map<String, Atom> atoms;
	private List<Action> actions;
	
	/**
	 * Grounds the entire problem.
	 */
	@Override
	public GroundPlanningProblem ground(PlanningProblem problem) {
		
		this.problem = problem;
		
		// Create a sorted list of constants
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		constants.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
		
		actions = new ArrayList<>();
		List<Condition> liftedSuperstate = new ArrayList<>();
		liftedSuperstate.addAll(problem.getInitialState());
		int iteration = 0;
		while (iteration < 10000) {
			List<Condition> oldState = new ArrayList<>();
			oldState.addAll(liftedSuperstate);
			
			System.out.println("Superstate at iteration " + iteration + " : " + liftedSuperstate);
			List<Operator> liftedActions = getLiftedActionsReachableFrom(liftedSuperstate);
			for (Operator op : liftedActions) {
				List<Argument> operatorArgs = new ArrayList<>();
				op.getArguments().forEach(arg -> operatorArgs.add(arg));
				Action a = getAction(op);
				if (!actions.contains(a)) {
					actions.add(a);
				}
				applyPositiveEffects(op, liftedSuperstate);
			}
			
			if (oldState.size() == liftedSuperstate.size())
				break;
			
			iteration++;
		}
		
		// Extract initial state
		List<Atom> initialStateAtoms = new ArrayList<>();
		problem.getInitialState().forEach(cond -> {
			initialStateAtoms.add(getAtom(cond.getPredicate(), cond.getArguments()));
		});
		Predicate pEquals = problem.getPredicate("=");
		if (pEquals != null) {			
			for (Argument constant : constants) {
				List<Argument> args = new ArrayList<>();
				args.add(constant);
				args.add(constant);
				initialStateAtoms.add(getAtom(pEquals, args));
			}
		}
		State initialState = new State(initialStateAtoms);
		
		// Extract goal
		List<Atom> goalAtoms = new ArrayList<>();
		problem.getGoals().forEach(cond -> {
			Atom atom = getAtom(cond.getPredicate(), cond.getArguments());
			atom.set(!cond.isNegated());
			goalAtoms.add(atom);
		});
		for (Quantification q : problem.getQuantifiedGoals()) {
			List<AbstractCondition> conditions = resolveQuantification(q);
			goalAtoms.addAll(getAtomList(conditions));
		}
		Goal goal = new Goal(goalAtoms);
		
		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, 
				goal, problem.hasActionCosts());
		return planningProblem;
	}
	
	/**
	 * Assemble an Action object out of an operator 
	 * whose arguments are fully replaced by constants.
	 */
	private Action getAction(Operator liftedAction) {

		List<Atom> preconditions = getAtomList(liftedAction.getPreconditions());
		List<Atom> effects = getAtomList(liftedAction.getEffects());
		List<ConditionalEffect> conditionalEffects = getConditionalEffects(liftedAction.getEffects());
		String actionName = liftedAction.getName() + "( ";
		for (Argument arg : liftedAction.getArguments()) {
			actionName += arg.getName() + " ";
		}
		actionName += ")";
		Action action = new Action(actionName, preconditions, effects, conditionalEffects);
		action.setCost(liftedAction.getCost());
		return action;
	}
	
	private List<Atom> getAtomList(List<? extends AbstractCondition> conditions) {
		
		List<Atom> atoms = new ArrayList<>();
		List<AbstractCondition> conditionsToProcess = new ArrayList<>();
		conditionsToProcess.addAll(conditions);
		for (int i = 0; i < conditionsToProcess.size(); i++) {
			AbstractCondition c = conditionsToProcess.get(i);
			
			if (c.getConditionType() == ConditionType.atomic) {
				Condition cond = (Condition) c;
				Atom atom = getAtom(cond.getPredicate(), cond.getArguments());
				atom.set(!cond.isNegated());
				atoms.add(atom);
			} else if (c.getConditionType() == ConditionType.quantification) {
				conditionsToProcess.addAll(resolveQuantification((Quantification) c));
			} // cond. effects are treated in the method below
		};
		return atoms;
	}
	
	private List<ConditionalEffect> getConditionalEffects(List<AbstractCondition> conditions) {
		
		List<ConditionalEffect> effects = new ArrayList<>();
		conditions.forEach(c -> {
			if (c.getConditionType() == ConditionType.consequential) {
				ConsequentialCondition cond = (ConsequentialCondition) c;
				List<Atom> pre = getAtomList(cond.getPrerequisites());
				List<Atom> eff = getAtomList(cond.getConsequences());
				effects.add(new ConditionalEffect(pre, eff));
			}
		});
		return effects;
	}
	
	private List<AbstractCondition> resolveQuantification(Quantification q) {
		
		List<AbstractCondition> dequantifiedConds = new ArrayList<>();
		
		if (q.getQuantifier() != Quantifier.universal) {
			System.err.println("Only universal quantifiers are supported.");
		}
		
		// Iterator over all possible combinations of quantified variables' values
		List<Argument> quantifiedArgs = q.getVariables();
		List<List<Argument>> eligibleDequantifiedArgs = getEligibleArguments(quantifiedArgs);
		ArgumentCombinationIterator dequantifiedArgIterator = 
				new ArgumentCombinationIterator(eligibleDequantifiedArgs);
		
		dequantifiedArgIterator.forEachRemaining(dequantifiedArgs -> {
			// dequantifiedArgs : the arguments for the quantified variables
			
			// For each quantified condition, find the right constants
			for (Condition cond : q.getConditions()) {
				List<Argument> condArgs = new ArrayList<>();
				
				// For each argument of the condition
				for (int argIdx = 0; argIdx < cond.getNumArgs(); argIdx++) {
					Argument arg = cond.getArguments().get(argIdx);
					Argument c = arg;
					
					if (!arg.isConstant()) {
						// arg is a variable
						// Is this variable bound to the quantifier?
						int qArgIdx = quantifiedArgs.indexOf(arg);
						if (qArgIdx >= 0) {
							// -- yes: assign the corresponding dequantified argument
							c = dequantifiedArgs.get(qArgIdx);
						}
					}
					// Add created constant to this condition's arguments
					condArgs.add(c);
				}
				
				Condition newCondition = new Condition(cond.getPredicate(), cond.isNegated());
				for (Argument arg : condArgs) newCondition.addArgument(arg);
				dequantifiedConds.add(newCondition);
			}						
		});
		
		return dequantifiedConds;
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	private String getAtomName(Predicate p, List<Argument> args) {
		
		String atomName = p.getName() + "( ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName += ")";
		return atomName;
	}
	
	/**
	 * Retrieves the atom corresponding to the provided predicate 
	 * and constant arguments. If this atom has not been grounded before,
	 * it will be created.
	 */
	private Atom getAtom(Predicate p, List<Argument> constants) {
		
		if (atoms == null) {
			atoms = new HashMap<>();
		}
		
		String atomName = getAtomName(p, constants);
		if (!atoms.containsKey(atomName)) {
			atoms.put(atomName, new Atom(atoms.size(), atomName, true));
		}
		return atoms.get(atomName).copy();
	}

	private List<Operator> getLiftedActionsReachableFrom(List<Condition> liftedState) {
		
		List<Operator> reachableOperators = new ArrayList<>();
		
		// For each operator
		for (Operator op : problem.getOperators()) {
					
			// Get all possible argument combinations
			List<List<Argument>> arguments = getEligibleArguments(op.getArguments());
			List<List<Argument>> argumentCombinations = new ArrayList<>();
			new ArgumentCombinationIterator(arguments).forEachRemaining(l -> argumentCombinations.add(l));
			
			// Now filter this list by considering each of its preconditions
			List<AbstractCondition> preconditions = new ArrayList<>();
			preconditions.addAll(op.getPreconditions());
			for (int condIdx = 0; condIdx < preconditions.size(); condIdx++) {
				AbstractCondition cond = preconditions.get(condIdx);
				
				if (cond.getConditionType() == ConditionType.atomic) {
					
					// Find out which groundings of the operator are valid
					// such that the precondition is satisfied
					for (int combIdx = 0; combIdx < argumentCombinations.size(); combIdx++) {
						List<Argument> args = argumentCombinations.get(combIdx);
						
						// Does the precondition hold with this grounding?
						if (!holdsCondition((Condition) cond, op, args, liftedState)) {
							// -- no; directly remove it
							argumentCombinations.remove(combIdx--);
						}
					}
					
				} else if (cond.getConditionType() == ConditionType.quantification) {
					
					// Fully instantiate the quantification, producing a set
					// of atomic conditions which we will check later
					preconditions.addAll(resolveQuantification((Quantification) cond));
				}
			}

			for (List<Argument> validArgs : argumentCombinations) {
				Operator liftedAction = op.getOperatorWithGroundArguments(validArgs);
				reachableOperators.add(liftedAction);
			}
		}
		
		return reachableOperators;
	}
	
	private void applyPositiveEffects(Operator liftedAction, List<Condition> liftedState) {
		 
		List<AbstractCondition> effects = new ArrayList<>();
		effects.addAll(liftedAction.getEffects());
		for (int i = 0; i < effects.size(); i++) {
			AbstractCondition effect = effects.get(i);
			
			if (effect.getConditionType() == ConditionType.atomic) {
				Condition cond = (Condition) effect;
				if (!cond.isNegated() && !liftedState.contains(cond)) {
					liftedState.add(cond);
				}
				
			} else if (effect.getConditionType() == ConditionType.consequential) {
				ConsequentialCondition cond = (ConsequentialCondition) effect;
				boolean applyEffects = true;
				for (Condition prerequisite : cond.getPrerequisites()) {
					if (!holdsCondition(prerequisite, liftedAction, 
							liftedAction.getArguments(), liftedState)) {
						applyEffects = false;
						break;
					}
				}
				if (applyEffects) {
					for (Condition consequence : cond.getConsequences()) {
						effects.add(consequence);
					}
				}
				
			} else if (effect.getConditionType() == ConditionType.quantification) {
				effects.addAll(resolveQuantification((Quantification) effect));
			}
		}
	}
	
	private boolean holdsCondition(Condition cond, Operator op, 
				List<Argument> opArgs, List<Condition> liftedState) {
		
		// Set the ground arguments as the arguments of the condition
		Condition groundCond = cond.copy();
		groundConditionArguments(groundCond, op.getArguments(), opArgs);
		
		if (groundCond.getPredicate().getName().equals("=")) {
			// Equality predicate: just check if the two args are equal
			if (groundCond.getArguments().get(0).equals(groundCond.getArguments().get(1))) {
				return !cond.isNegated();
			}
		}
		
		// Search the provided state for this condition
		for (Condition stateCond : liftedState) {
			if (stateCond.equals(groundCond)) {
				return true;
			}
		}
		
		// If the condition is negated, then it holds; else, not
		return cond.isNegated();
	}
	
	private void groundConditionArguments(Condition cond, List<Argument> opArgs, 
			List<Argument> args) {
		
		for (int condArgIdx = 0; condArgIdx < cond.getNumArgs(); condArgIdx++) {
			Argument condArg = cond.getArguments().get(condArgIdx);
			if (!condArg.isConstant()) {
				
				for (int opArgIdx = 0; opArgIdx < opArgs.size(); opArgIdx++) {
					Argument opArg = opArgs.get(opArgIdx);
					if (condArg.equals(opArg)) {
						cond.getArguments().set(condArgIdx, args.get(opArgIdx));
					}
				}
			}
		}
	}
	

	/**
	 * For a list of arguments, returns a list containing all valid
	 * argument combinations which can be retrieved by
	 * replacing each variable in the arguments by a
	 * constant of an appropriate type.
	 * This list of eligible arguments may have been shortened
	 * by applying simplification strategies.
	 */
	private List<List<Argument>> getEligibleArguments(List<Argument> args) {
		
		List<Type> argTypes = new ArrayList<>();
		for (Argument arg : args) {
			argTypes.add(arg.getType());
		}
		return getEligibleArgumentsOfType(argTypes);
	}
	
	/**
	 * Returns each possible combination of constants with the 
	 * provided order of types.
	 */
	private List<List<Argument>> getEligibleArgumentsOfType(List<Type> argTypes) {
		
		List<List<Argument>> eligibleArguments = new ArrayList<>();
		
		// For each provided type
		for (Type argType : argTypes) {
			List<Argument> eligibleArgumentsAtPos = new ArrayList<>();
			
			// For all possible constants at the argument position
			for (Argument c : constants) {
				if (problem.isArgumentOfType(c, argType)) {
					
					eligibleArgumentsAtPos.add(c);
				}
			}
			
			eligibleArguments.add(eligibleArgumentsAtPos);
		}
		
		return eligibleArguments;
	}
	
	
	/**
	 * Given a list of possible constants at each argument index, 
	 * allows to iterate over all possible combinations.
	 */
	private class ArgumentCombinationIterator implements Iterator<List<Argument>> {

		private List<List<Argument>> eligibleArgs;
		private List<Integer> currentArgIndices;
		private boolean hasNext;
		
		/**
		 * @param eligibleArgs At index i, contains a list of all eligible
		 * constants for the argument position i.
		 */
		public ArgumentCombinationIterator(List<List<Argument>> eligibleArgs) {
			
			this.eligibleArgs = eligibleArgs;
			// Set current argument indices to zero
			// (first argument combination)
			currentArgIndices = new ArrayList<>();
			for (int i = 0; i < eligibleArgs.size(); i++) {
				currentArgIndices.add(0);
			}
			hasNext = true;
		}
		
		/**
		 * True, iff there is another combination not retrieved yet.
		 */
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		/**
		 * Get the next combination of constants.
		 */
		@Override
		public List<Argument> next() {
			
			// Create current constant combination
			List<Argument> args = new ArrayList<>();
			int argPos = 0;
			for (int argIdx : currentArgIndices) {
				args.add(eligibleArgs.get(argPos++).get(argIdx));
			}
			
			// Get to next argument combination, if possible
			hasNext = false;
			for (int pos = currentArgIndices.size()-1; pos >= 0; pos--) {
				
				// Are there more argument options at this position?
				if (currentArgIndices.get(pos)+1 < eligibleArgs.get(pos).size()) {
					// -- Yes
					
					// Proceed to the next argument option at this position
					currentArgIndices.set(pos, currentArgIndices.get(pos)+1);
					
					// Reset all succeeding argument options to zero
					for (int posAfter = pos+1; posAfter < currentArgIndices.size(); posAfter++) {
						currentArgIndices.set(posAfter, 0);
					}
					
					hasNext = true;
					break;
				}
			}
			
			return args;
		}
	}
}
