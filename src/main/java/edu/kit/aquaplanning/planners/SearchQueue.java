package edu.kit.aquaplanning.planners;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.planners.heuristic.Heuristic;

/**
 * Maintains a structure of search nodes. Nodes can be added and polled
 * from the structure. Which node is polled depends on the chosen strategy.
 */
public class SearchQueue {

	private SearchStrategy strategy;
	private Heuristic h;
	
	// Different data structures used depending on the employed strategy
	private Queue<SearchNode> queue;
	private Stack<SearchNode> stack;
	private List<SearchNode> list;
	private Random random;
	
	/**
	 * Contains the hashes of all states which have already been visited.
	 */
	private Set<Integer> visitedStates;
	
	/**
	 * Initializes a forward search queue with a non-heuristical strategy.
	 */
	public SearchQueue(SearchStrategy s) {
		this.strategy = s;
		if (s.isHeuristical()) {
			throw new IllegalArgumentException(
					"A heuristic must be provided in the constructor.");
		}
		initFrontier();
	}
	
	/**
	 * Initializes a forward search queue with a heuristical strategy 
	 * and a corresponding heuristic.
	 */
	public SearchQueue(SearchStrategy s, Heuristic h) {
		this.strategy = s;
		this.h = h;
		initFrontier();
	}
	
	private void initFrontier() {
		
		switch (strategy.getMode()) {
		case SearchStrategy.BREADTH_FIRST:
			queue = new ArrayDeque<>();
			break;
		case SearchStrategy.DEPTH_FIRST:
			stack = new Stack<>();
			break;
		case SearchStrategy.BEST_FIRST:
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
					// Compare heuristic scores
					n1.heuristicValue - n2.heuristicValue
			);
			break;
		case SearchStrategy.A_STAR:
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
					// Compare (cost so far + heuristic scores)
					n1.depth + n1.heuristicValue - (n2.depth + n2.heuristicValue)
			);
			break;
		case SearchStrategy.WEIGHTED_A_STAR:
			int heuristicWeight = strategy.getHeuristicWeight();
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
					// Compare (cost so far + heuristic scores)
					n1.depth + heuristicWeight * n1.heuristicValue 
					- (n2.depth + heuristicWeight * n2.heuristicValue)
			);
			break;
		case SearchStrategy.RANDOM_CHOICE:
			list = new ArrayList<>();
			random = new Random(1337); // <-- seed
		}
		visitedStates = new HashSet<>();
	}
	
	/**
	 * Returns true if the provided node is unneeded 
	 * and should be discarded.
	 */
	public boolean canBePruned(SearchNode node) {
		
		// If revisiting states is forbidden:
		// Has the state already been visited?
		if (!strategy.canRevisitStates() && 
				visitedStates.contains(node.state.hashCode())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Proposes to add a search node to the structure. 
	 * It may be internally decided that the node is not needed,
	 * discarding the node instead.
	 */
	public void add(SearchNode node) {
		
		// Should the node be pruned away?
		if (canBePruned(node))
			return;
		
		if (strategy.isHeuristical()) {
			// Compute heuristic value for the node
			node.heuristicValue = h.value(node);
			if (node.heuristicValue < Integer.MAX_VALUE) {
				// Only add node if heuristic does not return infinity
				queue.add(node);
			}
		} else if (strategy.getMode() == SearchStrategy.BREADTH_FIRST) {
			queue.add(node);
		} else if (strategy.getMode() == SearchStrategy.DEPTH_FIRST) {
			stack.push(node);
		} else if (strategy.getMode() == SearchStrategy.RANDOM_CHOICE) {
			list.add(node);
		}
	}
	
	/**
	 * Polls a node according to the employed strategy.
	 */
	public SearchNode get() {
		
		SearchNode node;
		if (strategy.getMode() == SearchStrategy.DEPTH_FIRST) {
			node = stack.pop();
		} else if (strategy.getMode() == SearchStrategy.RANDOM_CHOICE) {
			node = list.remove(random.nextInt(list.size()));
		} else {
			node = queue.poll();
		}
		
		// If revisiting states during the search is forbidden:
		if (!strategy.canRevisitStates()) {			
			// Add the state's hash code to the visited states
			visitedStates.add(node.state.hashCode());
		}
		
		return node;
	}
	
	/**
	 * Returns true iff there are no nodes left to visit.
	 */
	public boolean isEmpty() {
		
		if (strategy.getMode() == SearchStrategy.DEPTH_FIRST) {
			return stack.isEmpty();
		} else if (strategy.getMode() == SearchStrategy.RANDOM_CHOICE) {
			return list.isEmpty();
		} else {
			return queue.isEmpty();
		}
	}
}
