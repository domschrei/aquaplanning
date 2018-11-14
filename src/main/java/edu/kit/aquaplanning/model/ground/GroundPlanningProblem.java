package edu.kit.aquaplanning.model.ground;

import java.util.List;

public class GroundPlanningProblem {

	private State initialState;
	private List<Action> actions;
	private Goal goal;
	private boolean hasActionCosts;
	private List<String> atomNames;
	
	public GroundPlanningProblem(State initState, List<Action> actions, 
			Goal goal, boolean hasActionCosts, List<String> atomNames) {
		
		this.initialState = initState;
		this.actions = actions;
		this.goal = goal;
		this.hasActionCosts = hasActionCosts;
		this.atomNames = atomNames;
	}
	
	public State getInitialState() {
		return initialState;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
	public boolean hasActionCosts() {
		return hasActionCosts;
	}
	
	public String stateToString(State state) {
		
		String out = "{ ";
		List<Boolean> atoms = state.getAtoms();
		for (int i = 0; i < atoms.size(); i++) {
			if (atoms.get(i)) {
				out += atomNames.get(i) + " ";
			}
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
			builder.append("  " + action + "\n");
		}
		builder.append("Goal: ");
		builder.append(goal.toString());
		if (hasActionCosts) {			
			builder.append("\nMetric: minimize (total-cost).");
		}
		return builder.toString();
	}
}
