package edu.kit.aquaplanning.planning.datastructures;

import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.State;

public class GroundRelaxedPlanningGraph {

	private State state;
	private List<Action> actions;
	private boolean hasNextLayer;
	
	public GroundRelaxedPlanningGraph(State state, List<Action> actions) {
		this.state = state;
		this.actions = actions;
		this.hasNextLayer = true;
	}
	
	public boolean hasNextLayer() {
		
		return hasNextLayer;
	}
	
	public State computeNextLayer() {
		
		State newState = new State(state);
		for (Action action : actions) {
			if (action.isApplicableRelaxed(state)) {
				State result = action.applyRelaxed(state);
				newState.addAllTrueAtomsFrom(result);
			}
		}
		if (state.size() == newState.size()) {
			// Fixpoint reached
			hasNextLayer = false;
		}
		state = newState;
		return state;
	}
}
