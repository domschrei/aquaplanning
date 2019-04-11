package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.Configuration.PlannerType;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.util.Logger;

/**
 * A simple portfolio planner which launches a number of different planners in parallel.
 */
public class SequantialPortfolioPlanner extends Planner {

	public SequantialPortfolioPlanner(Configuration config) {
		super(config);
	}

	
	@Override
	public Plan findPlan(GroundPlanningProblem problem) {
		startSearch();
		Plan plan = null;
		config.searchTimeSeconds = 200;
		config.plannerType = PlannerType.greedy;
		Logger.log(Logger.INFO, "Starting greedy search");
		Planner p = Planner.getPlanner(config);
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}
		// try Trautman
		config.searchTimeSeconds = 40;
		config.plannerType = PlannerType.forwardSSS;
		config.searchStrategy = Mode.bestFirst;
		config.heuristic = HeuristicType.ffTrautmann;
		p = Planner.getPlanner(config);
		Logger.log(Logger.INFO, "Starting heuristic search");
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}
		config.plannerType = PlannerType.hegemannSat;
		config.searchTimeSeconds = 0;
		p = Planner.getPlanner(config);
		Logger.log(Logger.INFO, "Starting Satisfiability Search");
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}
		return plan;
	}
}
