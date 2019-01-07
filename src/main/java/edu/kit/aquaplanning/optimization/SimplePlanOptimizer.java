package edu.kit.aquaplanning.optimization;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.State;

public class SimplePlanOptimizer extends PlanOptimizer {

	public SimplePlanOptimizer(GroundPlanningProblem problem) {
		super(problem);
	}

	/**
	 * Finds and removes simple loops in state space.
	 */
	@Override
	public Plan improvePlan(Plan initialPlan, Clock clock) {
		
		// Initial plan to be optimized
		Plan plan = initialPlan.copy();
		
		// Periodically check if there is still time left!
		while (clock.hasTimeLeft()) {
			
			// Iterate over actions in the plan
			State state = problem.getInitialState();
			List<State> visitedStates = new ArrayList<>();
			visitedStates.add(state);
			List<Action> actions = new ArrayList<>();
			for (int actionIdx = 0; actionIdx < plan.getLength(); actionIdx++) {
				Action a = plan.get(actionIdx);
				state = a.apply(state);
				
				// Is there a state-space loop?
				if (visitedStates.contains(state)) {
					int loopStart = visitedStates.indexOf(state);
					// Loop detected: remove everything starting at that index
					actions = actions.subList(0, loopStart);
					visitedStates = visitedStates.subList(0, loopStart+1);
				} else {
					// No loop: add action and remember new state
					actions.add(a);					
					visitedStates.add(state);
				}
			}
			
			if (actions.size() < plan.getLength()) {
				// Plan has improved
				final Plan newPlan = new Plan();
				actions.forEach(action -> newPlan.appendAtBack(action));
				plan = newPlan;
			} else {
				// No changes made: Plan improvement finished
				break;
			}
		}
		
		// Return best plan found so far
		return plan;
	}
}
