package edu.kit.aquaplanning.planning.heuristic;

import java.util.List;
import java.util.ArrayList;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planning.datastructures.GroundRelaxedPlanningGraph;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;

/**
 * Linear action traversal Fast-forward heuristic
 */
public class FroleyksHeuristic extends Heuristic {
	private GroundPlanningProblem groundProblem;

	public FroleyksHeuristic(GroundPlanningProblem groundProblem) {
		this.groundProblem = groundProblem;
		List<Action> ac = groundProblem.getActions();
		System.out.println(ac.get(0));
	}

	@Override
	public int value(SearchNode node) {
		State state = node.state;

		// Is the goal already satisfied (in a relaxed definition)?
		if (groundProblem.getGoal().isSatisfiedRelaxed(state)) {
			return 0;
		}

		// Traverse deletion-relaxed planning graph
		List<State> states = new ArrayList<>();
		states.add(state);
		GroundRelaxedPlanningGraph graph = new GroundRelaxedPlanningGraph(groundProblem, state,
				groundProblem.getActions());
		State g_hat = new State(groundProblem.getGoal().getAtoms());
		while (graph.hasNextLayer()) {
			State nextState = graph.computeNextLayer();
			states.add(nextState);
			// Goal reached?
			if (nextState.isSupersetOf(g_hat)) {
				List<Action> allActions = groundProblem.getActions();
				ActionPlan p = new ActionPlan();
				for (int stateIndex = states.size() - 2; stateIndex > 0; --stateIndex) {
					State s_hat = states.get(stateIndex);
					List<Action> A = new ArrayList<>();

					// Choose set of actions − with a sledgehammer
					for (Action action : allActions) {
						if (s_hat.isSupersetOf(g_hat)) {
							break;
						}
						if (action.isApplicableRelaxed(s_hat)) {
							if (!s_hat.holdsAll(action.getEffectsPos())) {
								A.add(action);
								action.applyRelaxed(s_hat);
							}
						}
					}
					for (Action action : A) {
						p.appendAtFront(action);
						g_hat.removeAll(action.getEffectsPos());
					}

					for (Action action : A) {
						g_hat.addAll(action.getPreconditionsPos());
					}
				}
				return p.getLength();
			}
		}

		// Goals could not be reached: unsolvable from this state
		return Integer.MAX_VALUE;
	}
}
