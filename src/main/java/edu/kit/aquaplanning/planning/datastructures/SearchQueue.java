package edu.kit.aquaplanning.planning.datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.planning.heuristic.Heuristic;

/**
 * Maintains a structure of search nodes. Nodes can be added and polled from the
 * structure. Which node is polled depends on the chosen strategy.
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
			throw new IllegalArgumentException("A heuristic must be provided in the constructor.");
		}
		initFrontier();
	}

	/**
	 * Initializes a forward search queue with a heuristical strategy and a
	 * corresponding heuristic.
	 */
	public SearchQueue(SearchStrategy s, Heuristic h) {
		this.strategy = s;
		this.h = h;
		initFrontier();
	}

	private void initFrontier() {

		switch (strategy.getMode()) {
		case breadthFirst:
			queue = new ArrayDeque<>();
			break;
		case depthFirst:
			stack = new Stack<>();
			break;
		case bestFirst:
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
			// Compare heuristic scores
			n1.heuristicValue - n2.heuristicValue);
			break;
		case aStar:
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
			// Compare (cost so far + heuristic scores)
			n1.depth + n1.heuristicValue - (n2.depth + n2.heuristicValue));
			break;
		case weightedAStar:
			int heuristicWeight = strategy.getHeuristicWeight();
			queue = new PriorityQueue<SearchNode>((n1, n2) ->
			// Compare (cost so far + heuristic scores)
			n1.depth + heuristicWeight * n1.heuristicValue - (n2.depth + heuristicWeight * n2.heuristicValue));
			break;
		case randomChoice:
			list = new ArrayList<>();
			random = new Random(strategy.getSeed());
			break;
		}
		visitedStates = new HashSet<>();
	}

	/**
	 * Returns true if the provided node is unneeded and should be discarded.
	 */
	public boolean canBePruned(SearchNode node) {

		// If revisiting states is forbidden:
		// Has the state already been visited?
		if (!strategy.canRevisitStates() && visitedStates.contains(node.state.hashCode())) {
			return true;
		}
		return false;
	}

	/**
	 * Proposes to add a search node to the structure. It may be internally decided
	 * that the node is not needed, discarding the node instead.
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
		} else if (strategy.getMode() == Mode.breadthFirst) {
			queue.add(node);
		} else if (strategy.getMode() == Mode.depthFirst) {
			stack.push(node);
		} else if (strategy.getMode() == Mode.randomChoice) {
			list.add(node);
		}
	}

	/**
	 * Polls a node according to the employed strategy.
	 */
	public SearchNode get() {

		SearchNode node;
		if (strategy.getMode() == Mode.depthFirst) {
			node = stack.pop();
		} else if (strategy.getMode() == Mode.randomChoice) {
			int r = random.nextInt(list.size());
			node = list.remove(r);
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

		if (strategy.getMode() == Mode.depthFirst) {
			return stack.isEmpty();
		} else if (strategy.getMode() == Mode.randomChoice) {
			return list.isEmpty();
		} else {
			return queue.isEmpty();
		}
	}
}
