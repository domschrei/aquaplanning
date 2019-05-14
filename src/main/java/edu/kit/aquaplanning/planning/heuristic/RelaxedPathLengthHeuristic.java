package edu.kit.aquaplanning.planning.heuristic;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planning.datastructures.GroundRelaxedPlanningGraph;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;

/**
 * An admissible heuristic which behaves like a simplified version of the
 * max-cost or add-cost heuristics with uniform action cost. Constructs a
 * deletion-relaxed planning graph originating at the current state and returns
 * the amount of iterations needed until all (positive) goals are reached in the
 * planning graph. If a fixpoint is reached in the graph before all goals are
 * satisfied, the problem is not solvable from this state, and INT_MAX is
 * returned.
 */
public class RelaxedPathLengthHeuristic extends Heuristic {

	private GroundPlanningProblem problem;

	public RelaxedPathLengthHeuristic(GroundPlanningProblem p) {
		super();
		this.problem = p;
	}

	@Override
	public int value(SearchNode node) {

		State state = node.state;

		// Is the goal already satisfied (in a relaxed definition)?
		if (problem.getGoal().isSatisfiedRelaxed(state)) {
			return 0;
		}

		// Traverse deletion-relaxed planning graph
		GroundRelaxedPlanningGraph graph = new GroundRelaxedPlanningGraph(problem, state, problem.getActions());
		int depth = 1;
		while (graph.hasNextLayer()) {
			State nextState = graph.computeNextLayer();

			// Goal reached?
			if (problem.getGoal().isSatisfiedRelaxed(nextState)) {
				return depth;
			}

			depth++;
		}

		// Goals could not be reached: unsolvable from this state
		return Integer.MAX_VALUE;
	}
}
