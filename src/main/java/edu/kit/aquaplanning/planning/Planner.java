package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planning.htn.TreeRexPlanner;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.util.Logger;

/**
 * Astract base class for a planner.
 * 
 * @author Dominik Schreiber
 */
public abstract class Planner {

	protected Configuration config;
	protected long searchStartMillis = 0;

	public Planner(Configuration config) {
		this.config = config;
	}

	public static Planner getPlanner(PlanningProblem problem, Configuration config) {
		
		if (problem instanceof HtnPlanningProblem) {
			return new TreeRexPlanner(config);
		}
		if (config.planFileToValidate != null) {
			return new FileReaderPlanner(config);
		}
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

	protected void startSearch() {
		searchStartMillis = System.currentTimeMillis();
	}

	/**
	 * Checks the used amount of iterations and the elapsed time against
	 * computational bounds specified in the configuration. If false is returned,
	 * the planner should stop.
	 */
	protected boolean withinComputationalBounds(int iterations) {

		if (Thread.currentThread().isInterrupted())
			return false;

		if (config.maxIterations > 0 && iterations >= config.maxIterations) {
			return false;
		}

		if (config.searchTimeSeconds > 0) {
			long searchTime = System.currentTimeMillis() - searchStartMillis;
			if (searchTime > config.searchTimeSeconds * 1000) {
				return false;
			}
		}

		if (config.maxTimeSeconds > 0) {
			long totalTime = System.currentTimeMillis() - config.startTimeMillis;
			if (totalTime > config.maxTimeSeconds * 1000) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Attempt to find a solution plan for the provided problem.
	 */
	public abstract Plan<?> plan(PlanningProblem problem);

	public abstract boolean validatePlan(Plan<?> plan);

	@Override
	public String toString() {
		return getClass().toString();
	}
}
