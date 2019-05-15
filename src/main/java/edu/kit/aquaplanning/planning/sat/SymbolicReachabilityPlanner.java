package edu.kit.aquaplanning.planning.sat;

import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.planning.GroundPlanner;
import edu.kit.aquaplanning.sat.Sat4jSolver;
import edu.kit.aquaplanning.sat.SymbolicReachabilityFormula;
import edu.kit.aquaplanning.sat.SymbolicReachabilitySolver;
import edu.kit.aquaplanning.sat.encoders.ForeachEncoding;
import edu.kit.aquaplanning.sat.encoders.PlanningToSatEncoder;

public class SymbolicReachabilityPlanner extends GroundPlanner {

	public SymbolicReachabilityPlanner(Configuration config) {
		super(config);
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {
		PlanningToSatEncoder encoder = new ForeachEncoding();
		SymbolicReachabilityFormula fla = encoder.encodeProblem(problem);
		SymbolicReachabilitySolver solver = new SymbolicReachabilitySolver(new Sat4jSolver());
		List<int[]> model = solver.solve(fla);
		if (model != null) {
			return encoder.decodePlan(problem, model);
		} else {
			return null;
		}
	}

}
