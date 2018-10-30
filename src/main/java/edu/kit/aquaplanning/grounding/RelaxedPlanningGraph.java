package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;

public class RelaxedPlanningGraph {

	private PlanningProblem problem;
	private List<Argument> constants;
	private List<List<Condition>> liftedStates;
	private List<List<Operator>> liftedActions;
	
	public RelaxedPlanningGraph(PlanningProblem problem) {

		this.problem = problem;
		this.liftedActions = new ArrayList<>();
		this.liftedStates = new ArrayList<>();
		
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		
		// initial layer
		liftedStates.add(new ArrayList<>());
		liftedStates.get(0).addAll(problem.getInitialState());
	}
	
	public boolean hasNextLayer() {
		
		int layer = getCurrentLayer();
		if (layer == 0)
			return true;
		
		return getLiftedState(layer).size() != getLiftedState(layer-1).size();
	}
	
	public void computeNextLayer() {
		
		// Current state
		List<Condition> state = liftedStates.get(liftedStates.size()-1);
		
		// Add all actions which are applicable
		List<Operator> newActions = getLiftedActionsReachableFrom(state);
		liftedActions.add(newActions);
		
		// Apply actions into new state
		List<Condition> newState = new ArrayList<>();
		newState.addAll(state);
		for (Operator op : newActions) {
			applyPositiveEffects(op, newState);
		}
		liftedStates.add(newState);
	}
	
	public int getCurrentLayer() {
		
		return liftedStates.size()-1;
	}
	
	public List<Condition> getLiftedState(int layer) {
		
		return liftedStates.get(layer);
	}
	
	public List<Operator> getLiftedActions(int layer) {
		
		return liftedActions.get(layer);
	}
	
	/**
	 * Given a state in lifted representation (i.e. a list of true conditions
	 * where all arguments are replaced by constants), returns all
	 * actions (in lifted representation) which are applicable in that state.
	 */
	protected List<Operator> getLiftedActionsReachableFrom(List<Condition> liftedState) {
		
		List<Operator> reachableOperators = new ArrayList<>();

		/*
		 * TODO This is inefficient. The possible argument combinations per operator should be 
		 * directly inferred from the current lifted state instead of producing all possible 
		 * operator arguments and only then checking them against the state.
		 */
		
		// For each operator
		for (Operator op : problem.getOperators()) {
					
			// Get all possible argument combinations
			List<List<Argument>> arguments = ArgumentCombination.getEligibleArguments(
					op.getArguments(), problem, constants);
			List<List<Argument>> argumentCombinations = new ArrayList<>();
			new ArgumentCombination.Iterator(arguments).forEachRemaining(l -> argumentCombinations.add(l));
			
			// Now filter this list by checking each precondition
			List<AbstractCondition> preconditions = new ArrayList<>();
			preconditions.addAll(op.getPreconditions());
			for (int condIdx = 0; condIdx < preconditions.size(); condIdx++) {
				AbstractCondition cond = preconditions.get(condIdx);
				
				if (cond.getConditionType() == ConditionType.atomic) {
					
					// Find out which argument choices of the operator 
					// are valid such that the precondition is satisfied
					for (int combIdx = 0; combIdx < argumentCombinations.size(); combIdx++) {
						List<Argument> args = argumentCombinations.get(combIdx);
						
						// Does the precondition hold with these arguments?
						if (!holdsCondition((Condition) cond, op, args, liftedState)) {
							// -- no; directly remove it
							argumentCombinations.remove(combIdx--);
						}
					}
					
				} else if (cond.getConditionType() == ConditionType.quantification) {
					
					// Fully instantiate the quantification, producing a set
					// of atomic conditions which we will check later
					List<AbstractCondition> conds = ArgumentCombination.resolveQuantification(
							(Quantification) cond, problem, constants);
					preconditions.addAll(conds);
				}
			}

			// Create one lifted action for each valid choice of arguments
			for (List<Argument> validArgs : argumentCombinations) {
				Operator liftedAction = op.getOperatorWithGroundArguments(validArgs);
				reachableOperators.add(liftedAction);
			}
		}
		
		return reachableOperators;
	}
	
	/**
	 * Given a lifted action executed in some (lifted) state, adds all of 
	 * its positive effects (including conditional effects) to the state.
	 */
	protected void applyPositiveEffects(Operator liftedAction, List<Condition> liftedState) {
		 
		List<AbstractCondition> effects = new ArrayList<>();

		// For each effect
		effects.addAll(liftedAction.getEffects());
		for (int i = 0; i < effects.size(); i++) {
			AbstractCondition effect = effects.get(i);
			
			if (effect.getConditionType() == ConditionType.atomic) {
				
				// -- atomic effect: directly add condition, 
				// if positive and not already present
				Condition cond = (Condition) effect;
				if (!cond.isNegated() && !liftedState.contains(cond)) {
					liftedState.add(cond);
				}
				
			} else if (effect.getConditionType() == ConditionType.consequential) {
				
				// -- conditional effect: check if prerequisites hold
				ConsequentialCondition cond = (ConsequentialCondition) effect;
				boolean applyEffects = true;
				for (Condition prerequisite : cond.getPrerequisites()) {
					// Does this prerequisite hold?
					if (!holdsCondition(prerequisite, liftedAction, 
							liftedAction.getArguments(), liftedState)) {
						// -- no; dismiss this conditional effect
						applyEffects = false;
						break;
					}
				}
				// Are all prerequisites satisfied?
				if (applyEffects) {
					// -- yes; add consequences to processing queue
					for (Condition consequence : cond.getConsequences()) {
						effects.add(consequence);
					}
				}
				
			} else if (effect.getConditionType() == ConditionType.quantification) {
				
				// -- quantification: resolve into flat atom list,
				// and add all atoms to processing queue
				effects.addAll(ArgumentCombination.resolveQuantification(
						(Quantification) effect, problem, constants));
			}
		}
	}
	
	/**
	 * Checks if the provided condition inside the provided operator 
	 * holds in the given state when the operator arguments are assigned 
	 * the provided list of arguments.
	 */
	private boolean holdsCondition(Condition cond, Operator op, 
				List<Argument> opArgs, List<Condition> liftedState) {
		
		// Set the ground arguments as the arguments of the condition
		Condition groundCond = cond.getConditionBoundToArguments(op.getArguments(), opArgs);
		//groundConditionArguments(groundCond, op.getArguments(), opArgs);
		
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
}
