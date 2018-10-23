package edu.kit.aquaplanning.planners;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;

/**
 * Blueprint for a planner operating on a fully grounded planning problem.
 * 
 * @author Dominik Schreiber
 */
public interface Planner {
	
	/**
	 * Attempt to find a solution plan for the provided problem.
	 */
	public Plan findPlan(GroundPlanningProblem problem);
	
}
