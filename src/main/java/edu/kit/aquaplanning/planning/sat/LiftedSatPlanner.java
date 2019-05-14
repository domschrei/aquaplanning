package edu.kit.aquaplanning.planning.sat;

import java.util.HashMap;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planning.LiftedPlanner;
import edu.kit.aquaplanning.sat.SymbolicReachabilityFormula;

public class LiftedSatPlanner extends LiftedPlanner {

  public LiftedSatPlanner(Configuration config) {
    super(config);
  }

  @Override
  public Plan plan(PlanningProblem problem) {
    Map<String, Operator> liftedOperator = new HashMap<String, Operator>();
    for (Operator o: problem.getOperators()) {
      liftedOperator.put(o.getName(), o);
    }
    PlanningGraphGrounder grounder = new PlanningGraphGrounder(config);
    GroundPlanningProblem groundedProblem = grounder.ground(problem);
    return null;
  }

  @Override
  public boolean validatePlan(Plan plan) {
    return false;
  }

  private SymbolicReachabilityFormula encoding;

}
