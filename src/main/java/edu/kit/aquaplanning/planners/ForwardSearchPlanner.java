package edu.kit.aquaplanning.planners;

import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planners.heuristic.Heuristic;

/**
 * Generic state space forward search planner. Different search strategies 
 * and heuristics may be employed.
 * 
 * Based on Algorithm 2.2 from "Automated Planning and Acting" 
 * by Malik Ghallab et al., 2016.
 * 
 * @author Dominik Schreiber
 */
public class ForwardSearchPlanner implements Planner {

	private final int maxIterations = 1000000;
	
	private SearchStrategy strategy;
	private Heuristic heuristic;
	
	public ForwardSearchPlanner(SearchStrategy strategy) {
		this.strategy = strategy;
		if (strategy.isHeuristical()) {
			throw new IllegalArgumentException("Heuristic needed, but not provided.");
		}
	}
	
	public ForwardSearchPlanner(SearchStrategy strategy, Heuristic heuristic) {
		this.strategy = strategy;
		this.heuristic = heuristic;
		if (!strategy.isHeuristical()) {
			System.out.println("Warning: Heuristic not needed, but provided.");
		}
	}
	
	@Override
	public Plan findPlan(GroundPlanningProblem problem) {
		
		// Important objects from the planning problem
		State initState = problem.getInitialState();
		Goal goal = problem.getGoal();
		List<Action> actions = problem.getActions();		
		
		// Initialize forward search
		SearchQueue frontier;
		if (strategy.isHeuristical()) {
			frontier = new SearchQueue(strategy, heuristic);
		} else {
			frontier = new SearchQueue(strategy);
		}
		frontier.add(new SearchNode(null, initState));
		
		int iteration = 1;
		int visitedNodesPrintInterval = 28;
		long timeStart = System.nanoTime();
		
		while (!frontier.isEmpty() && iteration < maxIterations) {
			
			// Visit node (by the heuristic provided to the priority queue)
			SearchNode node = frontier.get();
			
			// Is the goal reached?
			if (goal.isSatisfied(node.state)) {
				
				// Extract plan
				Plan plan = new Plan();
				while (node != null && node.lastAction != null) {
					plan.appendAtFront(node.lastAction);
					node = node.parent;
				}
				System.out.println("Visited " + iteration + " nodes.");
				long timeStop = System.nanoTime();
				System.out.println("Search time: " + (timeStop - timeStart)/1000000 + "ms");
				return plan;
			}
			
			// Expand node: iterate over operators
			for (Action action : actions) {
				
				// Can this operator be applied to this state?
				if (action.isApplicable(node.state)) {
					
					// Create new node by applying the operator
					State newState = action.apply(node.state);
					
					// Add new node to frontier
					SearchNode newNode = new SearchNode(node, newState);
					newNode.lastAction = action;
					frontier.add(newNode);
				}
			}
			
			iteration++;
			
			// Print amount of visited nodes
			if ((iteration << visitedNodesPrintInterval) == 0) {
				System.out.println("Visited " + iteration + " nodes.");
				visitedNodesPrintInterval--;
			}
		}
		
		// Failure to find a plan within the maximum amount of iterations
		// (or the problem is unsolvable and search space is exhausted)
		System.out.println("Visited " + iteration + " nodes.");
		return null;
	}
}
