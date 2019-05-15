package edu.kit.aquaplanning.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.Configuration.PlannerType;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.util.Logger;

/**
 * A simple portfolio planner which launches a number of different planners in
 * parallel.
 */
public class PortfolioParallelPlanner extends GroundPlanner {

	private int numThreads;
	private List<Thread> threads;
	private ActionPlan plan;

	public PortfolioParallelPlanner(Configuration config) {
		super(config);
		numThreads = config.numThreads;
	}

	/**
	 * Callback for when some planner finds a plan.
	 */
	private synchronized void onPlanFound(ActionPlan plan) {
		if (this.plan != null) {
			// Another planner already found a plan
			return;
		}
		this.plan = plan;
		// Interrupt all planners
		List<Thread> myThreadsList = new ArrayList<>(threads);
		for (Thread thread : myThreadsList) {
			// This interruption is acknowledged inside each planner thread
			// when withinComputationalBounds() is checked the next time.
			thread.interrupt();
		}
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {

		startSearch();
		threads = new ArrayList<>();
		plan = null;
		Random random = new Random(this.config.seed); // seed generator

		for (int i = 1; i <= numThreads; i++) {

			// Default configuration with random seed
			Configuration config = this.config.copy();
			config.seed = random.nextInt();
			config.plannerType = PlannerType.forwardSSS;
			config.searchStrategy = Mode.randomChoice;

			// Some more diversification
			switch (i) {
			case 1:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.bestFirst;
				config.heuristic = HeuristicType.ffWilliams;
				break;
			case 2:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.bestFirst;
				config.heuristic = HeuristicType.ffTrautmann;
				break;
			case 3:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.bestFirst;
				config.heuristic = HeuristicType.ffFroleyks;
				break;
			case 4:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.bestFirst;
				config.heuristic = HeuristicType.manhattanGoalDistance;
				break;
			case 5:
				config.plannerType = PlannerType.hegemannSat;
				break;
			case 6:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.depthFirst;
				break;
			case 7:
				config.plannerType = PlannerType.forwardSSS;
				config.searchStrategy = Mode.breadthFirst;
				break;
			}

			// Create planner
			GroundPlanner planner = getGroundPlanner(config);

			// Create a thread running the planner
			final int threadNum = i;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					ActionPlan plan = planner.findPlan(problem);
					if (plan != null) {
						Logger.log(Logger.INFO, "Planner \"" + planner + "\" (index " + threadNum + ") found a plan.");
						onPlanFound(plan);
					}
				}
			});
			threads.add(thread);

			// Start the planner (non-blocking call)
			thread.start();
		}

		// Wait for all threads to finish
		// (if some plan has been found, all threads are interrupted)
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Plan is not null iff any planner was successful
		return plan;
	}
}
