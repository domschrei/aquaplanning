package edu.kit.aquaplanning;

import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.ForwardSearchPlanner;
import edu.kit.aquaplanning.planners.Planner;
import edu.kit.aquaplanning.planners.SearchStrategy;
import edu.kit.aquaplanning.planners.heuristic.Heuristic;
import edu.kit.aquaplanning.planners.heuristic.RelaxedPathLengthHeuristic;
import edu.kit.aquaplanning.validate.Validator;

/**
 * Reads a pair of PDDL files (domain and problem), does grounding,
 * and calls some planner in order to find a solution.
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("This is Aquaplanning - QUick Automated Planning");
		
		// Check arguments
		if (args.length < 2) {
			System.out.println("Please specify the domain file "
					+ "and the problem file as arguments.");
			System.exit(0);
		}
		
		try {
			
			// Step 1: Parsing of domain and problem files
			System.out.println("Parsing ...");
			PlanningProblem p = new ProblemParser().parse(args[0], args[1]);
			System.out.println(p); // print parsed problem
			System.out.println("Parsing complete.\n");
			
			// Step 2: Grounding (to get "flat" sets of actions and atoms)
			System.out.println("Grounding ...");
			Grounder grounder = new RelaxedPlanningGraphGrounder();
			GroundPlanningProblem planningProblem = grounder.ground(p);
			// Print ground problem (attention: can be a lot!)
			System.out.println(planningProblem);
			System.out.println("Grounding complete. " + planningProblem.getActions().size() 
					+ " actions resulted from the grounding.\n");
			
			// Step 3: Planning
			System.out.println("Planning ...");
			// TODO enter search strategy and heuristic here
			SearchStrategy strategy = new SearchStrategy(SearchStrategy.BEST_FIRST);
			Heuristic h = new RelaxedPathLengthHeuristic(planningProblem);
			Planner planner = new ForwardSearchPlanner(strategy, h);
			Plan plan = planner.findPlan(planningProblem);
			
			// Solution found?
			if (plan == null) {
				// -- no
				
				System.out.println("Planner did not find any solution.");
				
			} else {
				// -- yes
				
				System.out.println("Planner finished with a plan of length " + plan.getLength() + ".");
				
				// Print found plan
				System.out.println(plan);
				
				// Step 4: Validate plan (directly outputting any errors)
				System.out.println("Validating ...");
				if (Validator.planIsValid(planningProblem, plan)) {
					System.out.println("Plan has been found to be valid.");
				}
			}
			
		} catch (Exception e) {
		
			System.out.println("An unexpected error occurred.");
			e.printStackTrace();
		}
	}
	
}
