package edu.kit.aquaplanning;

import java.io.FileWriter;

import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.Planner;
import edu.kit.aquaplanning.validate.Validator;
import picocli.CommandLine;

/**
 * Reads a pair of PDDL files (domain and problem), does grounding,
 * and calls some planner in order to find a solution.
 */
public class Main {
	
	/**
	 * Parses the command line arguments and returns 
	 * a configuration object. Will print messages and exit 
	 * the application depending on requested help and/or
	 * errors which occur during parsing.
	 */
	public static Configuration parse(String[] args) {
		
		Configuration config = new Configuration();
		CommandLine cmd = new CommandLine(config);
		
		// Too few arguments? -> print usage, exit
		if (args.length < 2) {
			cmd.usage(System.out);
			System.exit(0);
		}
		
		// Parse arguments
		try {
			cmd.parse(args);
		} catch (Exception e) {
			// Error: print and exit
			System.err.println(e.getMessage());
			System.err.println("Exiting.");
			System.exit(1);
		}
		
		// Usage and version information
		if (cmd.isUsageHelpRequested()) {
			cmd.usage(System.out);
			System.exit(0);
		}
		if (cmd.isVersionHelpRequested()) {
			cmd.printVersionHelp(System.out);
			System.exit(0);
		}
		
		return config;
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("This is Aquaplanning - QUick Automated Planning.");
		
		// Read configuration from command line arguments
		Configuration config = parse(args);
		
		// Configuration defaults are editable in Configuration.java.
		// For debugging, you can also override the configuration here, e.g.
		// config.heuristic = HeuristicType.manhattanGoalDistance;
		
		try {	
			// Step 1: Parsing of domain and problem files
			System.out.println("Parsing ...");
			PlanningProblem p = new ProblemParser().parse(config.domainFile, config.problemFile);
			System.out.println(p); // print parsed problem
			System.out.println("Parsing complete.\n");
			
			// Step 2: Grounding (to get "flat" sets of actions and atoms)
			System.out.println("Grounding ...");
			Grounder grounder = new RelaxedPlanningGraphGrounder(config);
			GroundPlanningProblem planningProblem = grounder.ground(p);
			// Print ground problem (attention: can be a lot!)
			System.out.println(planningProblem);
			System.out.println("Grounding complete. " + planningProblem.getActions().size() 
					+ " actions resulted from the grounding.\n");
			
			// Step 3: Planning
			System.out.println("Planning ...");
			Planner planner = Planner.getPlanner(config);
			Plan plan = planner.findPlan(planningProblem);
			
			// Solution found?
			if (plan == null) {
				// -- no
				
				System.out.println("Planner did not find any solution.");
				
			} else {
				// -- yes
				
				System.out.println("Planner finished with a plan of length " 
						+ plan.getLength() + ".");
				
				// Print found plan
				System.out.println(plan);
				if (config.planOutputFile != null) {
					// Write plan to file
					FileWriter w = new FileWriter(config.planOutputFile);
					w.write(plan.toString());
					w.close();
				}
				
				// Step 4: Validate plan (directly outputting any errors)
				System.out.println("Validating ...");
				if (Validator.planIsValid(planningProblem, plan)) {
					System.out.println("Plan has been found to be valid.");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

