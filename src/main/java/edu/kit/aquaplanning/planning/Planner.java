package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.util.Logger;

/**
 * Interface for a planner.
 * 
 * @author Dominik Schreiber
 */
public interface Planner {
	/**
	 * Attempt to find a solution plan for the provided problem.
	 */
	public abstract Plan findPlan(PlanningProblem problem);
	
	public String toString();
}
