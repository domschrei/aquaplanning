package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planning.sat.LiftedSatPlanner;

public abstract class LiftedPlanner extends Planner {

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
}
