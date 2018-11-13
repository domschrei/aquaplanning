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
		System.out.println("Layer " + getCurrentLayer() + ": " 
							+ newActions.size() + " actions reachable");
		
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
		
		// For each operator
		for (int i = 0; i < problem.getOperators().size(); i++) {
			final int opIdx = i;
			Operator op = problem.getOperators().get(opIdx);
			
			// Expand quantifications into flat conditions
			List<AbstractCondition> preconditions = new ArrayList<>();
			preconditions.addAll(op.getPreconditions());
			for (int condIdx = 0; condIdx < preconditions.size(); condIdx++) {
				AbstractCondition cond = preconditions.get(condIdx);
				
				if (cond.getConditionType() == ConditionType.quantification) {
					
					// Instantiate the quantification
					List<AbstractCondition> conds = ArgumentCombination.resolveQuantification(
							(Quantification) cond, problem, constants);
					preconditions.addAll(conds);
				}
			}
			
			// Iterate over all possible argument combinations
			List<List<Argument>> arguments = ArgumentCombination.getEligibleArguments(
					op.getArguments(), problem, constants);
			new ArgumentCombination.Iterator(arguments).forEachRemaining(args -> {
				
				// For each (flat) condition:
				boolean addArguments = true;
				for (AbstractCondition abstractCond : preconditions) {
					if (abstractCond.getConditionType() == ConditionType.atomic) {						
						Condition cond = (Condition) abstractCond;
						
						// Does the precondition hold with these arguments?
						if (!holdsCondition((Condition) cond, op, args, liftedState)) {
							// -- no
							addArguments = false;
							break;
						}
					}
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
				for (AbstractCondition prerequisite : cond.getPrerequisites()) {
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
					for (AbstractCondition consequence : cond.getConsequences()) {
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
	private boolean holdsCondition(AbstractCondition abstractCond, Operator op, 
				List<Argument> opArgs, List<Condition> liftedState) {
		
		List<AbstractCondition> conditions = new ArrayList<>();
		conditions.add(abstractCond);
		
		for (int condIdx = 0; condIdx < conditions.size(); condIdx++) {
			AbstractCondition c = conditions.get(condIdx);
			
			if (c.getConditionType() == ConditionType.atomic) {
				
				Condition cond = (Condition) c;
				// Set the ground arguments as the arguments of the condition
				Condition groundCond = cond.getConditionBoundToArguments(op.getArguments(), opArgs);
				
				if (groundCond.getPredicate().getName().equals("=")) {
					// Equality predicate: just check if the two args are equal
					if (groundCond.getArguments().get(0).equals(groundCond.getArguments().get(1))) {
						if (cond.isNegated())
							// -- Inequality condition is false 
							return false;
					}
				}
				
				// Search the provided state for this condition
				boolean holds = false;
				for (Condition stateCond : liftedState) {
					if (stateCond.equals(groundCond)) {
						holds = true;
					}
				}
				
				if (!holds) {
					// Condition not contained in the state; 
					// if the condition is negated, then it holds; else, not
					if (!cond.isNegated())
						return false;
				}
				
			} else if (c.getConditionType() == ConditionType.quantification) {
				
				conditions.addAll(ArgumentCombination.resolveQuantification(
						(Quantification) c, problem, constants));
			}
		}
		
		return true;
	}
}
