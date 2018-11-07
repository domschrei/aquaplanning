package edu.kit.aquaplanning.planners;

/**
 * The strategy decides how the queue maintains and picks search nodes.
 */
public enum SearchStrategy {
	/**
	 * Maintain a queue of nodes, i.e. First come, first served.
	 */
	breadthFirst, 
	/**
	 * Maintain a stack of nodes, i.e. Last come, first served.
	 */
	depthFirst, 
	/**
	 * Picks a node uniformly at random.
	 */
	randomChoice,
	/**
	 * Using a provided heuristic, always picks the node with the
	 * best heuristic score.
	 */
	bestFirst, 
	/**
	 * Using a provided heuristic, always picks the node with the
	 * lowest value of f(n)+h(n), where f(n) is the node's depth
	 * (i.e. the path's cost so far) and h(n) is the node's 
	 * heuristic score.
	 */
	aStar, 
}