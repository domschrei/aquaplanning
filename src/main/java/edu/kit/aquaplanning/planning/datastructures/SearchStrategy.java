package edu.kit.aquaplanning.planning.datastructures;

import edu.kit.aquaplanning.Configuration;

/**
 * The strategy decides how the queue maintains and picks search nodes.
 */
public class SearchStrategy {
	
	public enum Mode {
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
		/**
		 * Like A*, but weights the heuristic value stronger than the
		 * cost so far, i.e. the node with the lowest value of
		 * f(n)+c*h(n) is picked where c is a constant set separately.
		 */
		weightedAStar;
	}	
	
	private Mode mode;
	private int heuristicWeight = 10; // only for heuristic modes
	private int seed = 1337;
	
	/**
	 * Denotes whether a state can be visited multiple times during a search
	 * (true) or it is visited only once and then memorized as "finished" 
	 * (false). Forbidding to revisit states can have an impact on optimality,
	 * but not on completeness.
	 */
	private boolean revisitStates = false;
	
	public SearchStrategy(Configuration config) {
		this.mode = config.searchStrategy;
		this.heuristicWeight = config.heuristicWeight;
		this.revisitStates = config.revisitStates;
		this.seed = config.seed;
	}
	
	/**
	 * Initializes a search strategy of the provided mode
	 * (one of SearchStrategy.BREADTH_FIRST, SearchStrategy.DEPTH_FIRST, ...).
	 * Do not use this constructor with modes requiring a weight.
	 */
	public SearchStrategy(Mode mode) {
		this.mode = mode;
	}
	
	/**
	 * Initializes a search strategy of the provided weighted mode
	 * (one of SearchStrategy.WEIGHTED_A_STAR, ...).
	 * Do not use this constructor with modes not requiring a weight.
	 */
	public SearchStrategy(Mode mode, int weight) {
		this.mode = mode;
		this.heuristicWeight = weight;
	}
	
	/**
	 * Decides whether a state can be visited multiple times during a search
	 * (true) or it is visited only once and then memorized as "finished" 
	 * (false). Forbidding to revisit states can have an impact on optimality,
	 * but not on completeness.
	 */
	public void setRevisitStates(boolean revisitStates) {
		this.revisitStates = revisitStates;
	}
	
	public boolean isHeuristical() {
		if (mode == Mode.aStar || mode == Mode.weightedAStar || mode == Mode.bestFirst) {
			return true;
		}
		return false;
	}
	
	public boolean hasWeight() {
		if (mode == Mode.weightedAStar) {
			return true;
		}
		return false;
	}
	
	public boolean canRevisitStates() {
		return revisitStates;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public int getHeuristicWeight() {
		return heuristicWeight;
	}
	
	public int getSeed() {
		return seed;
	}
}