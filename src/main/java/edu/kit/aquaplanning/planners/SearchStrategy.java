package edu.kit.aquaplanning.planners;

/**
 * The strategy decides how the queue maintains and picks search nodes.
 */
public class SearchStrategy {
	/**
	 * Maintain a queue of nodes, i.e. First come, first served.
	 */
	public static final int BREADTH_FIRST = 1; 
	/**
	 * Maintain a stack of nodes, i.e. Last come, first served.
	 */
	public static final int DEPTH_FIRST = 2; 
	/**
	 * Picks a node uniformly at random.
	 */
	public static final int RANDOM_CHOICE = 3;
	/**
	 * Using a provided heuristic, always picks the node with the
	 * best heuristic score.
	 */
	public static final int BEST_FIRST = 4;
	/**
	 * Using a provided heuristic, always picks the node with the
	 * lowest value of f(n)+h(n), where f(n) is the node's depth
	 * (i.e. the path's cost so far) and h(n) is the node's 
	 * heuristic score.
	 */
	public static final int A_STAR = 5;
	/**
	 * Like A*, but weights the heuristic value stronger than the
	 * cost so far, i.e. the node with the lowest value of
	 * f(n)+c*h(n) is picked where c is a constant set separately.
	 */
	public static final int WEIGHTED_A_STAR = 6; 
	
	private int mode;
	private int heuristicWeight = 10;
	
	/**
	 * Denotes whether a state can be visited multiple times during a search
	 * (true) or it is visited only once and then memorized as "finished" 
	 * (false). Forbidding to revisit states can have an impact on optimality,
	 * but not on completeness.
	 */
	private boolean revisitStates = false;
	
	/**
	 * Initializes a search strategy of the provided mode
	 * (one of SearchStrategy.BREADTH_FIRST, SearchStrategy.DEPTH_FIRST, ...).
	 * Do not use this constructor with modes requiring a weight.
	 */
	public SearchStrategy(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Initializes a search strategy of the provided weighted mode
	 * (one of SearchStrategy.WEIGHTED_A_STAR, ...).
	 * Do not use this constructor with modes not requiring a weight.
	 */
	public SearchStrategy(int mode, int weight) {
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
		if (mode == A_STAR || mode == WEIGHTED_A_STAR || mode == BEST_FIRST) {
			return true;
		}
		return false;
	}
	
	public boolean hasWeight() {
		if (mode == WEIGHTED_A_STAR) {
			return true;
		}
		return false;
	}
	
	public boolean canRevisitStates() {
		return revisitStates;
	}
	
	public int getMode() {
		return mode;
	}
	
	public int getHeuristicWeight() {
		return heuristicWeight;
	}
}