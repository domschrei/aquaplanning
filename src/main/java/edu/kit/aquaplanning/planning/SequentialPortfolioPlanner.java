package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.Configuration.PlannerType;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.util.Logger;

/**
 * A simple portfolio planner which launches a number of different planners one
 * after another.
 */
public class SequentialPortfolioPlanner extends GroundPlanner {

	public SequentialPortfolioPlanner(Configuration config) {
		super(config);
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {

		startSearch();
		ActionPlan plan = null;

		/* 1. Greedy forward search */

		config.searchTimeSeconds = 200;
		config.plannerType = PlannerType.greedy;
		Logger.log(Logger.INFO, "Starting greedy search");
		GroundPlanner p = getGroundPlanner(config);
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}

		/* 2. Slower, more informed forward search */

		// run "forever" if problem has non-primitive logical conditions
		// (SAT planner does not handle these), else run for 40 seconds
		boolean hasNonprimitiveLogic = problem.hasConditionalEffects() || problem.hasComplexConditions();
		config.searchTimeSeconds = hasNonprimitiveLogic ? 0 : 40;
		config.plannerType = PlannerType.forwardSSS;
		config.searchStrategy = Mode.bestFirst;
		config.heuristic = HeuristicType.ffTrautmann;
		p = getGroundPlanner(config);
		Logger.log(Logger.INFO, "Starting heuristic search");
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}

		/* 3. SAT-based planning */

		config.plannerType = PlannerType.hegemannSat;
		config.searchTimeSeconds = 0;
		p = getGroundPlanner(config);
		Logger.log(Logger.INFO, "Starting Satisfiability Search");
		plan = p.findPlan(problem);
		if (plan != null) {
			return plan;
		}

		return plan;
	}
}
