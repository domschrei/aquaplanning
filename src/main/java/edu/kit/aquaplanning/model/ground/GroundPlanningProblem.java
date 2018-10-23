package edu.kit.aquaplanning.model.ground;

import java.util.List;

public class GroundPlanningProblem {

	private State initialState;
	private List<Action> actions;
	private Goal goal;
	private boolean hasActionCosts;
	
	public GroundPlanningProblem(State initState, List<Action> actions, 
			Goal goal, boolean hasActionCosts) {
		
		this.initialState = initState;
		this.actions = actions;
		this.goal = goal;
		this.hasActionCosts = hasActionCosts;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Initial state: ");
		builder.append(initialState.toString());
		builder.append("\nActions: ");
		for (Action action : actions) {
			System.out.println("  " + action);
		}
		builder.append("\nGoal: ");
		builder.append(goal.toString());
		if (hasActionCosts) {			
			builder.append("\nMetric: minimize (total-cost).");
		}
		return builder.toString();
	}
}
