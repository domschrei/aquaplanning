package edu.kit.aquaplanning.planners.heuristic;

import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.planners.SearchNode;

/**
 * Simple and greedy heuristic which maps a state to the amount of goals
 * which are still unsatisfied.
 */
public class ManhattanGoalDistanceHeuristic extends Heuristic {

	private GroundPlanningProblem problem;
	
	public ManhattanGoalDistanceHeuristic(GroundPlanningProblem p) {
		problem = p;
	}
	
	@Override
	public int value(SearchNode node) {
		
		Goal g = problem.getGoal();
		int unsatisfiedGoals = 0;
		for (Atom goal : g.getAtoms()) {
			if (!node.state.holds(goal)) {
				unsatisfiedGoals++;
			}
		}
		return node.depth + unsatisfiedGoals;
	}
}
