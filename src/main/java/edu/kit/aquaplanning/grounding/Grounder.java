package edu.kit.aquaplanning.grounding;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;

public interface Grounder {

	public GroundPlanningProblem ground(PlanningProblem problem);

}
