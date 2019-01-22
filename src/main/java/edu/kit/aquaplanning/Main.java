package edu.kit.aquaplanning;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.optimization.Clock;
import edu.kit.aquaplanning.optimization.SimplePlanOptimizer;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.Planner;
import edu.kit.aquaplanning.util.Logger;
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
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Parse arguments
		cmd.parse(args);
		
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
	
	/**
	 * Prints the provided plan to stdout. 
	 * If the config says so, also outputs the plan to a file.
	 */
	private static void printPlan(Configuration config, Plan plan) throws IOException {
		
		if (config.planOutputFile != null) {
			// Write plan to file
			FileWriter w = new FileWriter(config.planOutputFile);
			w.write(plan.toString());
			w.close();
			Logger.log(Logger.INFO, "Plan written to " + config.planOutputFile + ".");
		} else {
			// No output file => Always output plan to stdout
			Logger.log(Logger.ESSENTIAL, plan.toString());
		}
	}
	
	public static void main(String[] args) throws Exception {
				
		// Read configuration from command line arguments
		Configuration config = parse(args);
		Logger.init(config.verbosityLevel);
		
		// Welcome message
		Logger.log(Logger.INFO, "This is Aquaplanning - QUick Automated Planning.");
		Logger.log(Logger.INFO, "Running on " + InetAddress.getLocalHost().getHostName());
		
		// Configuration defaults are editable in Configuration.java.
		// For debugging, you can also override the configuration here, e.g.
		// config.heuristic = HeuristicType.manhattanGoalDistance;
		
		try {	
			// Step 1: Parsing of domain and problem files
			Logger.log(Logger.INFO, "Parsing ...");
			PlanningProblem p = new ProblemParser().parse(config.domainFile, config.problemFile);
			Logger.log(Logger.INFO_V, p.toString()); // print parsed problem
			Logger.log(Logger.INFO, "Parsing complete.\n");
			
			// Step 2: Grounding (to get "flat" sets of actions and atoms)
			Logger.log(Logger.INFO, "Grounding ...");
			Grounder grounder = new RelaxedPlanningGraphGrounder(config);
			GroundPlanningProblem planningProblem = grounder.ground(p);
			// Print ground problem
			Logger.log(Logger.INFO_V, planningProblem.toString());
			Logger.log(Logger.INFO, "Grounding complete. " + planningProblem.getActions().size() 
					+ " actions resulted from the grounding.\n");
			
			// Step 3: Planning
			Logger.log(Logger.INFO, "Planning ...");
			Planner planner = Planner.getPlanner(config);
			Plan plan = planner.findPlan(planningProblem);
			
			// Solution found?
			if (plan == null) {
				// -- no
				
				Logger.log(Logger.ESSENTIAL, "Planner did not find any solution.");
				
			} else {
				// -- yes
				
				Logger.log(Logger.INFO, "Planner finished with a plan of length " 
						+ plan.getLength() + ".");
				printPlan(config, plan);
				
				if (config.optimizePlan) {
					// Employ plan optimization
					
					Logger.log(Logger.INFO, "Plan optimization ...");
					SimplePlanOptimizer o = new SimplePlanOptimizer(planningProblem);
					plan = o.improvePlan(plan, new Clock(5000)); // TODO set proper time limit
					Logger.log(Logger.INFO, "Final plan has a length of " + plan.getLength() + ".");
					printPlan(config, plan);
				}
				
				// Step 4: Validate plan (directly outputting any errors)
				Logger.log(Logger.INFO, "Validating ...");
				if (Validator.planIsValid(planningProblem, plan)) {
					Logger.log(Logger.INFO, "Plan has been found to be valid.");
				}
			}
			
		} catch (Exception e) {
			Logger.log(Logger.ERROR, "An error occurred.");
			e.printStackTrace();
		}
	}
}
