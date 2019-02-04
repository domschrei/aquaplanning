package edu.kit.aquaplanning.planners;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.sat.SatSolver;

/**
 * This class represents a SAT planner with a fixed plan length.
 */
public class FixedLengthSatPlanner extends SimpleSatPlanner {
	
	private int planLength;
	
	public FixedLengthSatPlanner(Configuration config, int planLength) {
		super(config);
		this.planLength = planLength;
	}
	
	@Override
	public Plan findPlan(GroundPlanningProblem problem) {
		
		// calculate number of sat variables required for each step
		satVarsPerStep = problem.getNumAtoms() + problem.getActions().size();
		
		// assign IDs to actions and calculate supporting actions for atoms
		initializeActionIdsAndSupports(problem);
		
		// initialize the SAT solver
		SatSolver solver = new SatSolver();
		
		// add the initial state unit clauses
		addInitialStateClauses(problem, solver);

		// find the plan
		for (int i = 0; i < planLength; i++) {
			// add the universal clauses for this step
			addUniversalClauses(problem, solver, i);
			
			// add the transitional clauses for this and next step
			addTransitionalClauses(problem, solver, i);
		}
		
		// we will assume that the goal is satisfied after this step
		int[] assumptions = calculateGoalAssumptions(problem, planLength);
		
		if (config.maxTimeSeconds > 0) {
			solver.setSolverTimeLimit(config.maxTimeSeconds);			
		}
		Plan plan = new Plan();
		if (solver.isSatisfiable(assumptions)) {
			System.out.println("Found plan!");
			// We found a Plan!
			// Decode the plan
			int[] model = solver.getModel();
			for (int i = 0; i < planLength; i++) {
				for (Action a : problem.getActions()) {
					if (model[getActionSatVariable(a.getName(), i)] > 0) {
						plan.appendAtBack(a);
					}
				}
			}
		}
		else {
			System.out.println("No plan found!");
		}
		
		return plan;
	}
	
}
