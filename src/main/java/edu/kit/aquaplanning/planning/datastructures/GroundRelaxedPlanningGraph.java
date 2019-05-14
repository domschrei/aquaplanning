package edu.kit.aquaplanning.planning.datastructures;

import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;

public class GroundRelaxedPlanningGraph {

	private State state;
	private boolean hasNextLayer;
	private ActionIndex actionIndex;
	
	public GroundRelaxedPlanningGraph(GroundPlanningProblem gpp, State state, List<Action> actions) {
		this.state = state;
		this.hasNextLayer = true;
		this.actionIndex = new ActionIndex(gpp, /*relaxed=*/true);
	}
	
	public boolean hasNextLayer() {
		
		return hasNextLayer;
	}
	
	public State computeNextLayer() {
		
		State newState = new State(state);
		for (Action action : actionIndex.getApplicableActions(state)) {
			State result = action.applyRelaxed(state);
			newState.addAllTrueAtomsFrom(result);
		}
		if (state.size() == newState.size()) {
			// Fixpoint reached
			hasNextLayer = false;
		}
		state = newState;
		return state;
	}
}
