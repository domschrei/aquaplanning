package edu.kit.aquaplanning.planners.heuristic;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planners.SearchNode;
import edu.kit.aquaplanning.planners.SimpleSatPlanner;

public class SatAbstractionHeuristic extends Heuristic {
	
	private GroundPlanningProblem problem;
	private SimpleSatPlanner satPlanner;
	
	public SatAbstractionHeuristic(GroundPlanningProblem problem, Configuration config) {
		super();
		this.problem = new GroundPlanningProblem(problem);
		this.satPlanner = new SimpleSatPlanner(config);
		this.satPlanner.setIgnoreAtMostOneAction(true);
	}

	@Override
	public int value(SearchNode node) {
		problem.setInitialState(node.state);
		Plan p = satPlanner.findPlan(problem);
		if (p != null) {
			return p.getCost();
		} else {
			return Integer.MAX_VALUE;
		}
	}

}
