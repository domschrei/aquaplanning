public abstract class GroundPlanner implements BasePlanner {

  public GroundPlanner(Configuration config) {
    super(config);
  }

  @Override
  public Plan findPlan(PlanningProblem problem) {
    Logger.log(Logger.INFO, "Grounding ...");
    Grounder grounder = new PlanningGraphGrounder(config);
    GroundPlanningProblem planningProblem = grounder.ground(problem);
    if (planningProblem == null) {
      Logger.log(Logger.ESSENTIAL, "The problem has been found to be unsatisfiable. Exiting.");
      return;
    }
    // Print ground problem
    if (Logger.INFO_VV <= config.verbosityLevel) {				
      Logger.log(Logger.INFO_VV, planningProblem.toString());
    }
    Logger.log(Logger.INFO, "Grounding complete. " + planningProblem.getActions().size() 
        + " actions resulted from the grounding.\n");
    Logger.log(Logger.INFO, "Ground problem contains " + (planningProblem.hasConditionalEffects() ? "some" : "no") + " conditional effects.");
    Logger.log(Logger.INFO, "Ground problem contains " + (planningProblem.hasComplexConditions() ? "some" : "no") + " complex conditions.");

    return plan(planningProblem);
  }

  protected abstract Plan plan(GroundPlanningProblem problem);

}
