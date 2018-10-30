package edu.kit.aquaplanning.planners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.State;

/**
 * Very simple planner doing forward search. Does not do any pruning, 
 * chooses at random any node from the current frontier.
 * 
 * Based on Algorithm 2.2 from "Automated Planning and Acting" 
 * by Malik Ghallab et al., 2016.
 * 
 * @author Dominik Schreiber
 */
public class DefaultPlanner implements Planner {

	private final int randomSeed = 1337;
	private final int maxIterations = 10000000;
	
	@Override
	public Plan findPlan(GroundPlanningProblem problem) {
		Random random = new Random(randomSeed);
		
		State initState = problem.getInitialState();
		Goal goal = problem.getGoal();
		List<Action> actions = problem.getActions();		
		
		// Initialize forward search
		List<SearchNode> frontier = new ArrayList<>();
		frontier.add(new SearchNode(null, initState));
		
		int iteration = 1;
		while (!frontier.isEmpty() && iteration < maxIterations) {
			
			// Visit node (random node-picking policy)
			// (At this point, some awesome heuristic could be employed)
			SearchNode node = frontier.get(random.nextInt(frontier.size()));
			
			// Is the goal reached?
			if (goal.isSatisfied(node.state)) {
				
				// Extract plan
				Plan plan = new Plan();
				while (node != null && node.lastAction != null) {
					plan.appendAtFront(node.lastAction);
					node = node.parent;
				}
				return plan;
			}
			
			// Expand node: iterate over operators
			for (Action action : actions) {
				
				// Can this operator be applied to this state?
				if (action.isApplicable(node.state)) {
										
					// Create new node by applying the operator
					State newState = action.apply(node.state);
					
					// Did the new state already occur in some ancestor?
					if (node.pathContains(newState)) {
						// yes: Cycle in state space detected -- discard
						continue;
					} else {
						// no: add new node to frontier
						// (At this point, you could do additional pruning, 
						// i.e. directly discard unpromising nodes)
						SearchNode newNode = new SearchNode(node, newState);
						newNode.lastAction = action;
						frontier.add(newNode);
					}
				}
			}
			
			iteration++;
		}
		
		// Failure to find a plan within the maximum amount of iterations
		return null;
	}
	
	/**
	 * Represents a node in a state-space search for a plan.
	 * Contains the parent node (the previous state), the operator
	 * which translated the 
	 */
	private class SearchNode {
		
		public int depth;
		public SearchNode parent;
		public State state;
		public Action lastAction;
		
		public SearchNode(SearchNode parent, State state) {
			if (parent != null)
				this.depth = parent.depth+1;
			this.parent = parent;
			this.state = state;
		}
		
		/**
		 * Checks whether the specified state has already been
		 * visited inside the path this node is a leaf of.
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
		 * Returns the action cost of the sequence of actions
		 * up until this node. Currently considers each action
		 * to have a cost of 1 plus any specified action cost.
		 */
		public int getCost() {
			
			int cost = 0;
			SearchNode node = this;
			while (node != null) {
				cost += node.lastAction.getCost()+1;
				node = node.parent;
			}
			return cost;
		}
	}
}
