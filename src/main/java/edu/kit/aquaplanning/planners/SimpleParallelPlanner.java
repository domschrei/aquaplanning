package edu.kit.aquaplanning.planners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planners.SearchStrategy.Mode;

/**
 * A trivial portfolio planner which launches a number of forward search planners,
 * each with an uninformed, random search strategy and a different seed.
 */
public class SimpleParallelPlanner extends Planner {

	private int numThreads;
	private List<Thread> threads;
	private Plan plan;
	
	public SimpleParallelPlanner(Configuration config) {
		super(config);
		numThreads = config.numThreads;
	}

	/**
	 * Callback for when some planner finds a plan.
	 */
	private synchronized void onPlanFound(Plan plan) {
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
	public Plan findPlan(GroundPlanningProblem problem) {
		startSearch();
		threads = new ArrayList<>();
		Random random = new Random(this.config.seed); // seed generator
		
		for (int i = 1; i <= numThreads; i++) {
			
			// Create a new, randomized planner
			Configuration config = this.config.copy();
			config.searchStrategy = Mode.randomChoice;	
			config.seed = random.nextInt();
			Planner planner = new ForwardSearchPlanner(config);
			
			// Create a thread running the planner
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					Plan plan = planner.findPlan(problem);
					if (plan != null) {						
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
