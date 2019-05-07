package edu.kit.aquaplanning.planning;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.util.Logger;

/**
 * Blueprint for a planner.
 * 
 * @author Dominik Schreiber
 */
public abstract class BasePlanner implements Planner {
	
	protected Configuration config;
	protected long searchStartMillis = 0;
	
	public BasePlanner(Configuration config) {
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
	
	@Override
	public String toString() {
		return getClass().toString();
	}
}
