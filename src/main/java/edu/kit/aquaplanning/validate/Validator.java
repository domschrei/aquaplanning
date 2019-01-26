package edu.kit.aquaplanning.validate;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.util.Logger;

/**
 * Basic plan validator.
 */
public class Validator {

	/**
	 * For a given planning problem, checks if the provided plan is valid.
	 * Does a traversal through state space applying the actions in the plan 
	 * and checking if each action is applicable and if the actions transform 
	 * the state in a way such that the goals hold in the end.
	 */
	public static boolean planIsValid(GroundPlanningProblem problem, Plan plan) {
		
		State state = problem.getInitialState();
		int step = 1;
		
        System.out.println("Initial state: " + state);
		for (Action action : plan) {
			
			if (!action.isApplicable(state)) {
				Logger.log(Logger.ERROR, "Error at step " + step + ": Action " 
						+ action + " is not applicable in state " + problem.stateToString(state) + ".");
				return false;
			}
			
			state = action.apply(state);
            System.out.println("Step " + step + ": " + state);
			step++;
		}
		
		if (!problem.getGoal().isSatisfied(state)) {
			Logger.log(Logger.ERROR, "Error: The goal " + problem.getGoal() 
				+ " is not satisfied in the final state " + problem.stateToString(state) + ".");
			return false;
		}
		
		return true;
	}
	
}
