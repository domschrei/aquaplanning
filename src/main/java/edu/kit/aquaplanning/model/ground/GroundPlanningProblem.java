package edu.kit.aquaplanning.model.ground;

import java.util.List;

/**
 * Represents a planning problem in ground form, i.e. all atoms and actions are
 * flat lists/sets without any parameters.
 */
public class GroundPlanningProblem {

	private State initialState;
	private List<Action> actions;

	private Goal goal;
	private List<String> atomNames;
	private List<String> numericAtomNames;

	private boolean hasActionCosts;
	private Boolean hasConditionalEffects;
	private Boolean hasComplexConditions;

	public GroundPlanningProblem(State initState, List<Action> actions, Goal goal, boolean hasActionCosts,
			List<String> atomNames, List<String> numericAtomNames) {

		this.initialState = initState;
		this.actions = actions;
		this.goal = goal;
		this.hasActionCosts = hasActionCosts;
		this.atomNames = atomNames;
		this.numericAtomNames = numericAtomNames;
	}

	public GroundPlanningProblem(GroundPlanningProblem other) {
		super();
		this.initialState = other.initialState;
		this.actions = other.actions;
		this.goal = other.goal;
		this.hasActionCosts = other.hasActionCosts;
		this.atomNames = other.atomNames;
		this.numericAtomNames = other.numericAtomNames;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State s) {
		this.initialState = s;
	}

	public List<Action> getActions() {
		return actions;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal g) {
		this.goal = g;
	}

	public boolean hasActionCosts() {
		return hasActionCosts;
	}

	public boolean hasConditionalEffects() {

		// Already checked? -> Return result
		if (hasConditionalEffects != null) {
			return hasConditionalEffects;
		}

		// Check if problem contains conditional effects
		for (Action a : actions) {
			if (a.getConditionalEffects() != null && !a.getConditionalEffects().isEmpty()) {
				hasConditionalEffects = true;
				break;
			}
		}
		if (hasConditionalEffects == null)
			hasConditionalEffects = false;

		return hasConditionalEffects;
	}

	public boolean hasComplexConditions() {

		if (hasComplexConditions != null)
			return hasComplexConditions;

		for (Action a : actions) {
			if (a.getComplexPrecondition() != null || a.getComplexEffect() != null) {
				hasComplexConditions = true;
				break;
			}
		}
		if (hasComplexConditions == null) {
			hasComplexConditions = false;
		}

		return hasComplexConditions;
	}

	public List<String> getAtomNames() {
		return atomNames;
	}

	public List<String> getNumericAtomNames() {
		return numericAtomNames;
	}

	public int getNumAtoms() {
		return atomNames.size();
	}

	/**
	 * Returns the provided AtomSet in human-readable form.
	 */
	public String atomSetToString(AtomSet atoms) {

		String out = "{ ";
		for (int i = 0; i < this.atomNames.size(); i++) {
			if (atoms.get(i)) {
				out += atomNames.get(i) + " ";
			}
		}
		return out + "}";
	}

	/**
	 * Returns the provided State in human-readable form.
	 */
	public String stateToString(State state) {

		String out = "{ ";
		List<Boolean> atoms = state.getAtoms();
		for (int i = 0; i < atoms.size(); i++) {
			if (atoms.get(i)) {
				out += atomNames.get(i) + " ";
			}
		}
		for (int i = 0; i < numericAtomNames.size(); i++) {
			out += numericAtomNames.get(i) + "=" + state.get(new NumericAtom(i, numericAtomNames.get(i), 0)) + " ";
		}
		return out + "}";
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Initial state: ");
		builder.append(stateToString(initialState));
		builder.append("\nActions (" + actions.size() + "):\n");
		for (Action action : actions) {
			builder.append("  " + action.toString(atomSet -> atomSetToString(atomSet)) + "\n");
		}
		builder.append("Goal: ");
		builder.append(goal.toString());
		if (hasActionCosts) {
			builder.append("\nMetric: minimize (total-cost).");
		}
		return builder.toString();
	}
}
