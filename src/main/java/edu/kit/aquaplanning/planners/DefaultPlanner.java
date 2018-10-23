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
					
					// (At this point, you could do pruning, 
					// i.e. directly discard unpromising nodes)
					
					// Create new node by applying the operator
					State newState = action.apply(node.state);
					SearchNode newNode = new SearchNode(node, newState);
					newNode.lastAction = action;
					frontier.add(newNode);
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
		
		public SearchNode parent;
		public State state;
		public Action lastAction;
		
		public SearchNode(SearchNode parent, State state) {
			this.parent = parent;
			this.state = state;
		}
	}
	
}
