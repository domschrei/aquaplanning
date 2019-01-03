package edu.kit.aquaplanning.planners.heuristic;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.planners.SearchNode;

/**
 * A heuristic approximates the minimum distance from a given state
 * to a goal state.
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
		}
		return null;
	}
}
