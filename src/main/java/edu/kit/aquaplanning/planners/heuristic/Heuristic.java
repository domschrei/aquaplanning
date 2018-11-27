package edu.kit.aquaplanning.planners.heuristic;

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
}
