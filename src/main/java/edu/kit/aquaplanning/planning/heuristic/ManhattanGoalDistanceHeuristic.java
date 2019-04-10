package edu.kit.aquaplanning.planning.heuristic;

import java.util.Random;

import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;

/**
 * Simple and greedy heuristic which maps a state to the amount of goals
 * which are still unsatisfied.
 */
public class ManhattanGoalDistanceHeuristic extends Heuristic {

	private GroundPlanningProblem problem;
	private Random rnd;
	
	public ManhattanGoalDistanceHeuristic(GroundPlanningProblem p) {
		problem = p;
		rnd = new Random();
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
		return 10*(node.depth + unsatisfiedGoals)+rnd.nextInt(10);
	}
}
