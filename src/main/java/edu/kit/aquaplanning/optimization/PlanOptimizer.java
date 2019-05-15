package edu.kit.aquaplanning.optimization;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;

public abstract class PlanOptimizer {

	protected GroundPlanningProblem problem;

	public PlanOptimizer(GroundPlanningProblem problem) {
		this.problem = problem;
	}

	public abstract ActionPlan improvePlan(ActionPlan initialPlan, Clock remainingTime);
}