package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;

/**
 * A structure representing a (delete-)relaxed planning graph of a planning
 * problem in *lifted* form, i.e. all arguments and parameters of atoms 
 * and actions are still present. Can be used to ground a planning problem
 * in a cautious way by only considering reachable actions and atoms.
 */
public class RelaxedPlanningGraph {

	private PlanningProblem problem;
	private List<Argument> constants;
	private List<Set<Condition>> liftedStates;
	private List<List<Operator>> liftedActions;
	
	/**
	 * Initialized a relaxed planning graph of the specified planning problem.
	 * The graph's layers are computed successively by calling hasNextLayer
	 * and computeNextLayer.
	 */
	public RelaxedPlanningGraph(PlanningProblem problem) {
		
		this.problem = problem;
		this.liftedActions = new ArrayList<>();
		this.liftedStates = new ArrayList<>();
				
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		
		// initial layer
		liftedStates.add(new HashSet<>());
		liftedStates.get(0).addAll(problem.getInitialState());
	}
	
	/**
	 * Check if the planning graph can/should be expanded another time.
	 * Only returns false if a fixpoint has been reached, i.e. two successive
	 * states have the same size.
	 */
	public boolean hasNextLayer() {
		
		int layer = getCurrentLayer();
		if (layer == 0)
			return true;
		
		return getLiftedState(layer).size() != getLiftedState(layer-1).size();
	}
	
	/**
	 * Expands the relaxed planning graph by one layer, computing all
	 * applicable actions and applying them to get a new state.
	 */
	public void computeNextLayer() {
		
		// Current state
		Set<Condition> state = liftedStates.get(liftedStates.size()-1);
		
		// Add all actions which are applicable
		List<Operator> newActions = getLiftedActionsReachableFrom(state);
		liftedActions.add(newActions);
		System.out.println("Layer " + getCurrentLayer() + ": " 
							+ state.size() + " atoms and "
							+ newActions.size() + " actions reachable");
		
		// Apply actions into new state
		Set<Condition> newState = new HashSet<>();
		newState.addAll(state);
		for (Operator op : newActions) {
			applyPositiveEffects(op, newState);
		}
		liftedStates.add(newState);
	}
	
	/**
	 * Returns the currently highest valid index 
	 * for getLiftedState and getLiftedActions.
	 */
	public int getCurrentLayer() {
		
		return liftedStates.size()-1;
	}
	
	/**
	 * Returns a lifted state representing the provided layer.
	 */
	public Set<Condition> getLiftedState(int layer) {
		
		return liftedStates.get(layer);
	}
	
	/**
	 * Returns all actions applicable at the provided layer.
	 */
	public List<Operator> getLiftedActions(int layer) {
		
		return liftedActions.get(layer);
	}
	
	/**
	 * Given a state in lifted representation (i.e. a list of true conditions
	 * where all arguments are replaced by constants), returns all
	 * actions (in lifted representation) which are applicable in that state.
	 */
	protected List<Operator> getLiftedActionsReachableFrom(Set<Condition> liftedState) {
		
		List<Operator> reachableOperators = new ArrayList<>();
		
		// For each operator
		for (int i = 0; i < problem.getOperators().size(); i++) {
			Operator op = problem.getOperators().get(i);
			
			// Iterate over all possible argument combinations
			List<List<Argument>> arguments = ArgumentCombination.getEligibleArguments(
					op.getArguments(), problem, constants);
			new ArgumentCombination.Iterator(arguments).forEachRemaining(args -> {
				
				boolean addArguments = true;
						
				// Does the precondition hold with these arguments in a relaxed sense?
				boolean holds = holdsCondition(op.getPrecondition(), op, args, liftedState);												
				if (!holds) {
					// -- no
					addArguments = false;
				}
				
				// Add lifted action, if reachable
				if (addArguments) {
					Operator liftedAction = op.getOperatorWithGroundArguments(args);
					reachableOperators.add(liftedAction);
				}
			});
		}
		
		return reachableOperators;
	}
	
	/**
	 * Given a lifted action executed in some (lifted) state, adds all of 
	 * its positive effects (including conditional effects) to the state.
	 */
	protected void applyPositiveEffects(Operator liftedAction, Set<Condition> liftedState) {
		
		List<AbstractCondition> effects = new ArrayList<>();
		effects.add(liftedAction.getEffect());
		
		// For each effect to process
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
				
				// Does this prerequisite hold in a relaxed sense?
				if (!holdsCondition(cond.getPrerequisite(), liftedAction, 
						liftedAction.getArguments(), liftedState)) {
					// -- no; dismiss this conditional effect
					applyEffects = false;
				}
				// Are all prerequisites satisfied?
				if (applyEffects) {
					// -- yes; add consequences to processing queue
					effects.add(cond.getConsequence());
				}
				
			} else if (effect.getConditionType() == ConditionType.quantification) {
				
				// -- quantification: resolve into flat atom list,
				// and add all atoms to processing queue
				effects.add(ArgumentCombination.resolveQuantification(
						(Quantification) effect, problem, constants));
				
			} else if (effect.getConditionType() == ConditionType.conjunction) {
				
				effects.addAll(((ConditionSet) effect).getConditions());
			}
		}
	}
	
	/**
	 * Checks if the provided condition inside the provided operator 
	 * holds in the given state when the operator arguments are assigned 
	 * the provided list of arguments.
	 */
	private boolean holdsCondition(AbstractCondition abstractCond, Operator op, 
				List<Argument> opArgs, Set<Condition> liftedState) {
		
		switch (abstractCond.getConditionType()) {
		
		case atomic:
			Condition cond = (Condition) abstractCond;
			
			// Set the ground arguments as the arguments of the condition
			Condition groundCond = cond.getConditionBoundToArguments(op.getArguments(), opArgs);
			
			// If equality condition, check if it holds
			if (isEqualityCondition(groundCond)) {
				return groundCond.isNegated() != holdsEqualityCondition(groundCond);
			}
			
			if (groundCond.getPredicate().isDerived()) {
				return true; // derived predicate: just assume that it holds
			}
			
			// When delete-relaxed: negation is dismissed, so it "holds"
			if (groundCond.isNegated()) {
				return true;
			}
			
			// Search the provided state for this condition
			if (liftedState.contains(groundCond)) {
				// Condition is contained in the state;
				// if the condition is negated, the entire thing does not hold
				return !cond.isNegated();
			} else {
				// Condition not contained in the state; 
				// if the condition is negated, then it holds; else, not
				return cond.isNegated();
			}
			
		case negation:
			return true;
			
		case quantification:
			AbstractCondition condition = ArgumentCombination.resolveQuantification(
					(Quantification) abstractCond, problem, constants);
			if (!holdsCondition(condition, op, opArgs, liftedState)) {
				return false;
			}
			return true;
			
		case conjunction:
			for (AbstractCondition c : ((ConditionSet) abstractCond).getConditions()) {
				if (!holdsCondition(c, op, opArgs, liftedState)) {
					return false;
				}
			}
			return true;
			
		case disjunction:
			for (AbstractCondition c : ((ConditionSet) abstractCond).getConditions()) {
				if (holdsCondition(c, op, opArgs, liftedState)) {
					return true;
				}
			}
			return false;
			
		case implication:
			return true;
		
		case numericPrecondition:
			return true; // TODO better relaxation
		
		default:
			throw new IllegalArgumentException("Illegal condition type.");
		}
	}	
	
	private boolean isEqualityCondition(Condition cond) {
		return cond.getPredicate().getName().equals("=");
	}
	
	private boolean holdsEqualityCondition(Condition cond) {
		
		if (!isEqualityCondition(cond)) {
			throw new IllegalArgumentException("The provided condition does not represent an equality");
		}
		
		if (cond.getNumArgs() == 2) {
			if (cond.getArguments().get(0).equals(cond.getArguments().get(1))) {
				return true;
			}
		}
		return false;
	}
}
