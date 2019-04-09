package edu.kit.aquaplanning.sat.encoders;

import java.util.List;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.sat.SymbolicReachabilityFormula;

public interface PlanningToSatEncoder {
	/**
	 * Encode a planning problem into 4 sat formulas
	 * @param problem
	 * @return
	 */
	public SymbolicReachabilityFormula encodeProblem(GroundPlanningProblem problem);

	/**
	 * @param problem
	 * @param model
	 * @return
	 */
	public Plan decodePlan(GroundPlanningProblem problem, List<int[]> model);

}
