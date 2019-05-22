package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.grounding.datastructures.OperatorIndex;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.util.Logger;

/**
 * A structure representing a (delete-)relaxed planning graph of a planning
 * problem in *lifted* form, i.e. all arguments and parameters of atoms and
 * actions are still present. Can be used to ground a planning problem in a
 * cautious way by only considering reachable actions and atoms.
 */
public class PlanningGraph {

	private List<Argument> constants;
	private List<Set<Condition>> liftedStates;
	private List<List<Operator>> liftedActions;
	private List<Operator> filteredActions;

	private OperatorIndex opIndex;

	/**
	 * Initialized a relaxed planning graph of the specified planning problem. The
	 * graph's layers are computed successively by calling hasNextLayer and
	 * computeNextLayer.
	 */
	public PlanningGraph(PlanningProblem problem) {

		this.liftedActions = new ArrayList<>();
		this.liftedStates = new ArrayList<>();

		this.opIndex = new OperatorIndex(problem);

		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());

		// initial layer
		liftedStates.add(new HashSet<>());
		liftedStates.get(0).addAll(problem.getInitialState());
	}

	/**
	 * Check if the planning graph can/should be expanded another time. Only returns
	 * false if a fixpoint has been reached, i.e. two successive states have the
	 * same size.
	 */
	public boolean hasNextLayer() {

		int layer = getCurrentLayer();
		if (layer == 0)
			return true;

		return getLiftedState(layer).size() != getLiftedState(layer - 1).size();
	}

	/**
	 * Expands the relaxed planning graph by one layer, computing all applicable
	 * actions and applying them to get a new state.
	 */
	public void computeNextLayer() {

		// Current state
		Set<Condition> state = liftedStates.get(liftedStates.size() - 1);

		// Add all actions which are applicable
		List<Operator> newActions = getLiftedActionsReachableFrom(state);
		liftedActions.add(newActions);
		Logger.log(Logger.INFO_V, "Layer " + getCurrentLayer() + " of relaxed planning graph: " + state.size()
				+ " atoms, " + newActions.size() + " new actions reachable.");

		// Apply actions into new state
		Set<Condition> newState = new HashSet<>();
		newState.addAll(state);
		// Apply all operators seen so far to the state
		for (List<Operator> ops : liftedActions) {
			for (Operator op : ops) {
				applyEffects(op, newState);
			}
		}
		Set<Condition> lastState = state;
		Set<Condition> negConditionsToAdd = new HashSet<>();
		for (Condition c : newState) {
			if (!c.isNegated() && !lastState.contains(c)) {
				// c is a positive condition that was just added

				// Also add its negated counterpart to the state
				Condition cNeg = new Condition(c.getPredicate(), /* negated= */true);
				c.getArguments().forEach(arg -> cNeg.addArgument(arg));
				if (!newState.contains(cNeg)) {
					negConditionsToAdd.add(cNeg);
				}
			}
		}
		newState.addAll(negConditionsToAdd);
		liftedStates.add(newState);
	}

	/**
	 * Returns the currently highest valid index for getLiftedState and
	 * getLiftedActions.
	 */
	public int getCurrentLayer() {

		return liftedStates.size() - 1;
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
	public List<Operator> getLiftedActions() {

		List<Operator> allOps = new ArrayList<>();
		for (List<Operator> ops : liftedActions) {
			allOps.addAll(ops);
		}
		return allOps;
	}

	/**
	 * Given a state in lifted representation (i.e. a list of true conditions where
	 * all arguments are replaced by constants), returns all actions (in lifted
	 * representation) which are applicable in that state.
	 */
	protected List<Operator> getLiftedActionsReachableFrom(Set<Condition> liftedState) {

		LiftedState s = new LiftedState(liftedState);
		List<Operator> reachableOps = opIndex.getRelaxedApplicableLiftedActions(s);
		return reachableOps;
	}

	/**
	 * Given a lifted action executed in some (lifted) state, adds all of its
	 * effects to the state.
	 */
	protected void applyEffects(Operator liftedAction, Set<Condition> liftedState) {

		List<AbstractCondition> effects = new ArrayList<>();
		effects.add(liftedAction.getEffect());

		// For each effect to process
		for (int i = 0; i < effects.size(); i++) {
			AbstractCondition effect = effects.get(i);

			if (effect.getConditionType() == ConditionType.atomic) {

				// -- atomic effect: directly add condition
				Condition cond = (Condition) effect;
				liftedState.add(cond);

			} else if (effect.getConditionType() == ConditionType.consequential) {

				// -- conditional effect: check if prerequisites hold
				ConsequentialCondition cond = (ConsequentialCondition) effect;
				boolean applyEffects = true;

				// Does this prerequisite hold?
				if (!holdsCondition(cond.getPrerequisite(), liftedAction, liftedAction.getArguments(), liftedState)) {
					// -- no; dismiss this conditional effect
					applyEffects = false;
				}
				// Are all prerequisites satisfied?
				if (applyEffects) {
					// -- yes; add consequences to processing queue
					effects.add(cond.getConsequence());
				}

			} else if (effect.getConditionType() == ConditionType.conjunction) {

				effects.addAll(((ConditionSet) effect).getConditions());

			} else if (effect.getConditionType() == ConditionType.numericEffect) {

				// TODO better planning graph also incorporating numeric atoms

			} else {

				throw new IllegalArgumentException("An unexpected condition type \"" + effect.getConditionType()
						+ "\" occurred during grounding. " + "Has the problem been properly simplified?");
			}
		}
	}

	protected List<Condition> getUnreachablePreconditions(Operator op, LiftedState liftedState) {
		
		final List<Condition> unreachables = new ArrayList<>();
		op.getPrecondition().traverse(c -> {
			
			AbstractCondition result = c;
			if (c.getConditionType() == ConditionType.atomic) {
				if (!liftedState.holds((Condition) c)) {
					unreachables.add((Condition) c);
				}
			} else if (c.getConditionType() != ConditionType.conjunction) {
				throw new RuntimeException("Invalid condition type \"" + c.getConditionType() + "\".");
			}
			return result;
			
		}, AbstractCondition.RECURSE_HEAD);
		
		return unreachables;
	}
	
	/**
	 * Checks if the provided condition inside the provided operator holds in the
	 * given state when the operator arguments are assigned the provided list of
	 * arguments.
	 */
	private boolean holdsCondition(AbstractCondition abstractCond, Operator op, List<Argument> opArgs,
			Set<Condition> liftedState) {

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

			if (groundCond.isNegated()) {

				// no delete-relaxation, actual planning graph instead

				if (liftedState.contains(groundCond)) {
					// False condition is part of the lifted state
					return true;

				} else if (liftedStates.get(0).contains(groundCond)) {
					// Condition was true at the initial layer and never became false
					return false;
				} else {
					// Condition has been false from the beginning
					return true;
				}

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
			throw new IllegalArgumentException("An unexpected condition type \"" + abstractCond.getConditionType()
					+ "\" occurred during grounding. " + "Has the problem been properly simplified?");
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

	public List<Operator> getFilteredActions() {
		return filteredActions;
	}

	public void setFilteredActions(List<Operator> filteredActions) {
		this.filteredActions = filteredActions;
	}

	public Map<String, Integer> getArgumentIndices() {
		return opIndex.getArgumentIds();
	}
}
