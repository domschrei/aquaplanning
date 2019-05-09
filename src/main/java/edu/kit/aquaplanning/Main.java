package edu.kit.aquaplanning;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planning.Planner;
import edu.kit.aquaplanning.util.Logger;
import picocli.CommandLine;

/**
 * Reads a pair of PDDL files (domain and problem), does grounding, and calls
 * some planner in order to find a solution.
 */
public class Main {

  /**
   * Parses the command line arguments and returns a configuration object. Will
   * print messages and exit the application depending on requested help and/or
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
   * Prints the provided plan to stdout. If the config says so, also outputs the
   * plan to a file.
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
      Logger.log(Logger.ESSENTIAL, "Found plan:\n" + plan.toString());
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

      Planner planner = Planner.getPlanner(p, config);
      Plan plan = planner.plan(p);

      // Solution found?
      if (plan == null) {
        // -- no

        Logger.log(Logger.ESSENTIAL, "Could not find any solution.");

      } else {
        // -- yes
        Logger.log(Logger.INFO, "Validating ...");
        boolean isValid = planner.validatePlan(plan);
        Logger.log(Logger.ESSENTIAL, "PLAN " + (isValid ? "VALID" : "INVALID"));
        printPlan(config, plan);
      }

    } catch (Exception e) {
      Logger.log(Logger.ERROR, "An internal error occurred.");
      e.printStackTrace();
    }
  }
}
