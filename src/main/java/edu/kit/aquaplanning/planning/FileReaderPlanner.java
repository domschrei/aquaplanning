package edu.kit.aquaplanning.planning;

import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.parsing.PlanParser;
import edu.kit.aquaplanning.util.Logger;

public class FileReaderPlanner extends GroundPlanner {

	public FileReaderPlanner(Configuration config) {
		super(config);
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {
		if (Logger.INFO_VV <= config.verbosityLevel) {
			Logger.log(Logger.INFO_VV, problem.toString());
		}
		try {
			return PlanParser.parsePlan(config.planFileToValidate, problem);
		} catch (IOException e) {
			return null;
		}
	}
}
