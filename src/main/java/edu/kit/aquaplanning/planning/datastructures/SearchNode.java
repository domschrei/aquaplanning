package edu.kit.aquaplanning.planning.datastructures;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.State;

/**
 * Represents a node in a state-space search for a plan. Contains the parent
 * node (the previous state), the current state, the most recent action leading
 * to this state, and other freely accessible information.
 */
public class SearchNode {

	public int depth;
	public SearchNode parent;
	public State state;
	public Action lastAction;
	public int heuristicValue;

	public SearchNode(SearchNode parent, State state) {
		if (parent != null)
			this.depth = parent.depth + 1;
		this.parent = parent;
		this.state = state;
	}

	/**
	 * Checks whether the specified state has already been visited inside the path
	 * this node is a leaf of.
	 */
	public boolean pathContains(State previousState) {

		SearchNode node = this;
		while (node != null) {
			if (previousState.equals(node.state)) {
				return true;
			}
			node = node.parent;
		}
		return false;
	}

	/**
	 * Returns the action cost of the sequence of actions up until this node.
	 * Currently considers each action to have a cost of 1 plus any specified action
	 * cost.
	 */
	public int getCost() {

		int cost = 0;
		SearchNode node = this;
		while (node != null) {
			cost += node.lastAction.getCost() + 1;
			node = node.parent;
		}
		return cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/**
	 * Two search nodes are considered to be equal if their contained states are
	 * equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchNode other = (SearchNode) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
}