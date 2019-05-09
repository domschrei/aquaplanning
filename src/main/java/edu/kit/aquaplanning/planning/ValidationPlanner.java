package edu.kit.aquaplanning.planning;

import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.parsing.PlanParser;
import edu.kit.aquaplanning.util.Logger;

public class ValidationPlanner extends GroundPlanner {

  public ValidationPlanner(Configuration config) {
    super(config);
  }

  @Override
  public Plan findPlan(GroundPlanningProblem problem) {
    if (Logger.INFO_VV <= config.verbosityLevel) {
      Logger.log(Logger.INFO_VV, problem.toString());
    }
    Logger.log(Logger.INFO,
        "Grounding complete. " + problem.getActions().size() + " actions resulted from the grounding.\n");
    Logger.log(Logger.INFO,
        "Ground problem contains " + (problem.hasConditionalEffects() ? "some" : "no") + " conditional effects.");
    Logger.log(Logger.INFO,
        "Ground problem contains " + (problem.hasComplexConditions() ? "some" : "no") + " complex conditions.");
    Logger.log(Logger.INFO, "Parsing plan " + config.planFileToValidate + " ...");
    try {
      return PlanParser.parsePlan(config.planFileToValidate, problem);
    } catch (IOException e) {
      return null;
    }
  }
}
