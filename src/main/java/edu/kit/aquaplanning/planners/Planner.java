package edu.kit.aquaplanning.planners;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;

/**
 * Blueprint for a planner operating on a fully grounded planning problem.
 * 
 * @author Dominik Schreiber
 */
public abstract class Planner {
	
	protected Configuration config;
	
	public Planner(Configuration config) {
		this.config = config;
	}
	
	/**
	 * Checks the used amount of iterations and the elapsed time
	 * against computational bounds specified in the configuration.
	 * If false is returned, the planner should stop.
	 */
	protected boolean withinComputationalBounds(int iterations) {
		
		boolean withinIterations = false;
		boolean withinTime = false;

		if (config.maxIterations <= 0) {
			withinIterations = true; // no bound specified
		} else {
			withinIterations = (iterations <= config.maxIterations);
		}
		if (config.maxTimeSeconds <= 0) {
			withinTime = true; // no bound specified
		} else {
			long timeMillis = System.currentTimeMillis();
			long expired = timeMillis - config.startTimeMillis;
			withinTime = (expired / 1000 < config.maxTimeSeconds);			
		}

		return withinIterations && withinTime;
	}
	
	/**
	 * Attempt to find a solution plan for the provided problem.
	 */
	public abstract Plan findPlan(GroundPlanningProblem problem);
	
	/**
	 * Constructs a planner object fitting the provided configuration.
	 */
	public static Planner getPlanner(Configuration config) {
		
		switch (config.plannerType) {
		case forwardSSS:
			return new ForwardSearchPlanner(config);
		case satBased:
			return new SimpleSatPlanner(config);
		case hegemannSat:
			return new HegemannsSatPlanner(config);
		}
		return null;
	}
}
