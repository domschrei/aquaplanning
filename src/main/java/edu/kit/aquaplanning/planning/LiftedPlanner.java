package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.OperatorPlan;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planning.sat.LiftedSatPlanner;
import edu.kit.aquaplanning.validation.Validator;

public abstract class LiftedPlanner extends Planner {

	protected PlanningProblem problem;
	
	public LiftedPlanner(Configuration config) {
		super(config);
	}

	public static LiftedPlanner getLiftedPlanner(Configuration config) {
		switch (config.plannerType) {
		case liftedSat:
			return new LiftedSatPlanner(config);
		default:
			return null;
		}
	}
	
	@Override
	public boolean validatePlan(Plan<?> plan) {
		if (plan instanceof OperatorPlan)
			return Validator.planIsValid(problem, (OperatorPlan) plan);
		else
			throw new RuntimeException("A plan of invalid type was handed to the planner's validation.");
	}
}
