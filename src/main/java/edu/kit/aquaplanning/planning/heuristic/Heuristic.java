package edu.kit.aquaplanning.planning.heuristic;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;

/**
 * A heuristic approximates the minimum distance from a given state to a goal
 * state.
 */
public abstract class Heuristic {

	/**
	 * Evaluates the heuristic for some search node.
	 */
	public abstract int value(SearchNode node);

	public static Heuristic getHeuristic(GroundPlanningProblem p, Configuration config) {
		switch (config.heuristic) {
		case relaxedPathLength:
			return new RelaxedPathLengthHeuristic(p);
		case manhattanGoalDistance:
			return new ManhattanGoalDistanceHeuristic(p);
		case actionInterferenceRelaxation:
			return new SatAbstractionHeuristic(p, config);
		case ffTrautmann:
			return new TrautmannsHeuristic(p);
		case ffFroleyks:
			return new FroleyksHeuristic(p);
		case ffWilliams:
			return new WilliamsHeuristic(p);
		default:
			break;
		}
		return null;
	}
}
