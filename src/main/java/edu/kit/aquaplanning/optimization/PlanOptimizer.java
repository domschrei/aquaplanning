package edu.kit.aquaplanning.optimization;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;

public abstract class PlanOptimizer {

	protected GroundPlanningProblem problem;
	
	public PlanOptimizer(GroundPlanningProblem problem) {
		this.problem = problem;
	}
	
	public abstract Plan improvePlan(Plan initialPlan, Clock remainingTime);
}