package edu.kit.aquaplanning.optimization;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.util.Logger;

/**
 * A very simple plan optimizer which finds and removes loops in state space,
 * i.e. when the exact same state is visited multiple times while executing the
 * plan. This can only find an improved plan if config.revisitStates was set to
 * true during planning.
 */
public class SimplePlanOptimizer extends PlanOptimizer {

	public SimplePlanOptimizer(GroundPlanningProblem problem) {
		super(problem);
	}

	/**
	 * Finds and removes simple loops in state space.
	 */
	@Override
	public ActionPlan improvePlan(ActionPlan initialPlan, Clock clock) {

		// Initial plan to be optimized
		ActionPlan plan = initialPlan.copy();

		while (true) {

			// Iterate over actions in the plan
			State state = problem.getInitialState(); // current state
			List<State> visitedStates = new ArrayList<>();
			visitedStates.add(state);
			List<Action> actions = new ArrayList<>();
			for (int actionIdx = 0; actionIdx < plan.getLength(); actionIdx++) {
				Action a = plan.get(actionIdx); // retrieve action
				state = a.apply(state); // proceed to next state

				// Is there a state-space loop?
				if (visitedStates.contains(state)) {
					// Loop detected: remove all actions in between
					// the last occurrence of the state and the current position
					int loopStart = visitedStates.indexOf(state);
					actions = actions.subList(0, loopStart);
					visitedStates = visitedStates.subList(0, loopStart + 1);
				} else {
					// No loop: add action and remember new state
					actions.add(a);
					visitedStates.add(state);
				}
				// Check if there is still time left!
				if (!clock.hasTimeLeft()) {
					Logger.log(Logger.INFO, "TIMEOUT: Plan optimizer terminating.");
					return plan;
				}
			}

			// Did the plan improve?
			if (actions.size() < plan.getLength()) {
				// -- yes: update plan
				final ActionPlan newPlan = new ActionPlan();
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
