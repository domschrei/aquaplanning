package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.optimization.Clock;
import edu.kit.aquaplanning.optimization.SimplePlanOptimizer;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.validation.Validator;

public abstract class GroundPlanner extends Planner {

	protected PlanningGraphGrounder grounder;
	protected GroundPlanningProblem problem;

	public GroundPlanner(Configuration config) {
		super(config);
	}

	public static GroundPlanner getGroundPlanner(Configuration config) {
		switch (config.plannerType) {
		case forwardSSS:
			return new ForwardSearchPlanner(config);
		case satBased:
			return new SimpleSatPlanner(config);
		case hegemannSat:
			return new HegemannsSatPlanner(config);
		case parallel:
			Logger.log(Logger.INFO, "Doing parallel planning with up to " + config.numThreads + " threads.");
			return new PortfolioParallelPlanner(config);
		case greedy:
			return new GreedyBestFirstSearchPlanner(config);
		case seqpfolio:
			return new SequentialPortfolioPlanner(config);
		default:
			return null;
		}
	}

	@Override
	public ActionPlan plan(PlanningProblem p) {
		
		Logger.log(Logger.INFO, "Grounding ...");
		grounder = new PlanningGraphGrounder(config);
		problem = grounder.ground(p);
		if (problem == null) {
			Logger.log(Logger.ESSENTIAL, "The problem has been found to be unsatisfiable. Exiting.");
			return null;
		}
		
		// Print ground problem
		if (Logger.INFO_VV <= config.verbosityLevel) {
			Logger.log(Logger.INFO_VV, problem.toString());
		}
		Logger.log(Logger.INFO,
				"Grounding complete. " + problem.getActions().size() + " actions resulted from the grounding.\n");
		Logger.log(Logger.INFO, "Ground problem contains " + (problem.hasConditionalEffects() ? "some" : "no")
				+ " conditional effects.");
		Logger.log(Logger.INFO,
				"Ground problem contains " + (problem.hasComplexConditions() ? "some" : "no") + " complex conditions.");
		
		ActionPlan plan = findPlan(problem);

		Logger.log(Logger.INFO, "Plan length: " + plan.getLength());

		if (config.optimizePlan) {
			// Employ plan optimization

			Logger.log(Logger.INFO, "Plan optimization ...");
			SimplePlanOptimizer o = new SimplePlanOptimizer(problem);
			plan = o.improvePlan(plan, new Clock(5000)); // TODO set proper time limit
			Logger.log(Logger.INFO, "Final plan has a length of " + plan.getLength() + ".");
		}
		return plan;
	}

	public abstract ActionPlan findPlan(GroundPlanningProblem problem);

	@Override
	public boolean validatePlan(Plan<?> plan) {
		if (plan instanceof ActionPlan)
			return Validator.planIsValid(problem, (ActionPlan) plan);
		else
			throw new RuntimeException("A plan of invalid type was handed to the planner's validation.");
	}

}
