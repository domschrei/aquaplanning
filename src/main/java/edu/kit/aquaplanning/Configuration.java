package edu.kit.aquaplanning;

import edu.kit.aquaplanning.planners.SearchStrategy;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Bundles all options which can be set for this application.
 */
@Command(mixinStandardHelpOptions = true, version = "Aquaplanning Version 0.0.1-SNAPSHOT")
public class Configuration {
	
	public static final String USAGE_DEFAULT = "(default: @|fg(green) ${DEFAULT-VALUE}|@)";
	public static final String USAGE_OPTIONS_AND_DEFAULT = "@|fg(green) ${COMPLETION-CANDIDATES}|@ "
			+ USAGE_DEFAULT;
	
	
	/* 
	 * General properties and settings 
	 */
	
	/* Input files */
	
	@Parameters(index = "0", paramLabel = "domainFile", 
			description = "Path and name of the PDDL domain file")    
	public String domainFile;
	@Parameters(index = "1", paramLabel = "problemFile", 
			description = "Path and name of the PDDL problem file")    
	public String problemFile;
	
	/* Output files */
	
	@Option(paramLabel = "file", names = "-o", description = "Output plan to file")
	public String planOutputFile;
	
	/* Computational bounds */
	
	@Option(paramLabel = "maxIterations", names = {"-m", "--max-iterations"}, 
			description = "Maximum amount of iterations in outer loop "
					+ "of the planning algorithm", defaultValue = "0")
	public int maxIterations;
	
	@Option(paramLabel = "maxSeconds", names = {"-t", "--max-seconds"}, 
			description = "Maximum total runtime in seconds", defaultValue = "0")
	public int maxTimeSeconds;
	
	
	/*
	 * Preprocessing and grounding configuration
	 */
	
	@Option(names = {"-d", "--keep-disjunctions"}, description = "Do not compile disjunctive conditions "
			+ "into simple actions, but keep complex logical structure during planning")
	public boolean keepDisjunctions;
	@Option(names = {"-q", "--keep-equalities"}, description = "Do not resolve equality conditions, "
			+ "but add them as explicit atoms to the initial state")
	public boolean keepEqualities;
	
	
	/* 
	 * Planner configuration 
	 */
	
	public enum PlannerType {
		forwardSSS, satBased;
	}
	@Option(paramLabel = "plannerType", names = {"-p", "--planner"}, 
			description = "Planner type to use: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "forwardSSS")
	public PlannerType plannerType;
	
	/* Forward search space planning */
	
	public enum HeuristicType {
		manhattanGoalDistance, relaxedPathLength;
	}
	@Option(paramLabel = "heuristicClass", names = {"-H", "--heuristic"}, 
			description = "Heuristic for forward search: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "relaxedPathLength")
	public HeuristicType heuristic;
	@Option(paramLabel = "heuristicWeight", names = {"-w", "--heuristic-weight"},
			description = "Weight of heuristic when using a weighted search strategy " + USAGE_DEFAULT, 
			defaultValue = "10")
	public int heuristicWeight;
	
	@Option(paramLabel = "searchStrategy", names = {"-s", "--search"}, 
			description = "Search strategy for forward search: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "bestFirst")
	public SearchStrategy.Mode searchStrategy;
	
	@Option(names = {"r", "--revisit-states"}, description = "Re-enter a search node "
			+ "even when the state has been reached before")
	public boolean revisitStates;
	
	
	/**
	 * currentTimeMillis() time when the application was launched.
	 */
	public long startTimeMillis;
	
	public Configuration() {
		startTimeMillis = System.currentTimeMillis();
	}
}
