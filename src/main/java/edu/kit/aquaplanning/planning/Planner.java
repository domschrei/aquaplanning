package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.util.Logger;

/**
 * Blueprint for a planner operating on a fully grounded planning problem.
 * 
 * @author Dominik Schreiber
 */
public abstract class Planner {
	
	protected Configuration config;
	protected long searchStartMillis = 0;
	
	public Planner(Configuration config) {
		this.config = config;
	}
	
	protected void startSearch() {
		searchStartMillis = System.currentTimeMillis();
	}
	
	/**
	 * Checks the used amount of iterations and the elapsed time
	 * against computational bounds specified in the configuration.
	 * If false is returned, the planner should stop.
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
	public abstract Plan findPlan(GroundPlanningProblem problem);
	
	/**
	 * Constructs a planner object according to the provided configuration.
	 */
	public static Planner getPlanner(Configuration config) {
		
		switch (config.plannerType) {
		case forwardSSS:
			return new ForwardSearchPlanner(config);
		case satBased:
			return new SimpleSatPlanner(config);
		case hegemannSat:
			return new HegemannsSatPlanner(config);
		case parallel:
			Logger.log(Logger.INFO, "Doing parallel planning with up to " 
						+ config.numThreads + " threads.");
			return new PortfolioParallelPlanner(config);
		case greedy:
			return new GreedyBestFirstSearchPlanner(config);
		case seqpfolio:
			return new SequentialPortfolioPlanner(config);
		default:
			break;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().toString();
	}
}
