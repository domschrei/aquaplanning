package edu.kit.aquaplanning.planners;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.planners.SearchStrategy.Mode;

public class SimpleParallelPlanner extends Planner {

	private int numThreads;
	private List<Thread> threads;
	private Plan plan;
	
	public SimpleParallelPlanner(Configuration config) {
		super(config);
		config.searchStrategy = Mode.randomChoice;
		numThreads = config.numThreads;
	}

	private void onPlanFound(Plan plan) {
		this.plan = plan;
	}
	
	@Override
	public Plan findPlan(GroundPlanningProblem problem) {
		
		threads = new ArrayList<>();
		for (int i = 1; i <= numThreads; i++) {
			Planner planner = new ForwardSearchPlanner(config);
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
			thread.start();
		}
		
		while (plan == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return plan;
	}
}
