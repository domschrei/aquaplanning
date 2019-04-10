package edu.kit.aquaplanning;

import edu.kit.aquaplanning.planning.SearchStrategy;
import edu.kit.aquaplanning.util.Logger;
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
	
	@Option(paramLabel = "satFile", names = "-SAT", description = "Output SAT formulae to file(s)")
	public String satFormulaFile;
	
	/* Computational bounds */
	
	@Option(paramLabel = "maxIterations", names = {"-m", "--max-iterations"}, 
			description = "Maximum amount of iterations in outer loop "
					+ "of the planning algorithm", defaultValue = "0")
	public int maxIterations;
	
	@Option(paramLabel = "maxSeconds", names = {"-t", "--max-seconds"}, 
			description = "Maximum total runtime in seconds", defaultValue = "0")
	public int maxTimeSeconds;
	
	@Option(paramLabel = "searchSeconds", names = {"-st", "--search-seconds"}, 
			description = "Maximum search time in seconds", defaultValue = "0")
	public int searchTimeSeconds;
	
	@Option(paramLabel = "numThreads", names = {"-T", "--threads"}, 
			description = "The amount of threads to spawn (where applicable)", defaultValue = "1")
	public int numThreads;
	
	/* Output */
	
	@Option(paramLabel = "verbosityLevel", names = {"-v", "--verbosity"}, 
			description = "How verbose output should be "
					+ "(0: errors only, 1: warnings, 2: brief information, etc.) " + USAGE_DEFAULT, 
			defaultValue = (Logger.INFO + ""))
	public int verbosityLevel;
	
	
	
	/*
	 * Preprocessing and grounding configuration
	 */
	
	@Option(names = {"-kd", "--keep-disjunctions"}, description = "Do not compile disjunctive conditions "
			+ "into simple actions, but keep complex logical structure during planning")
	public boolean keepDisjunctions;
	@Option(names = {"-kr", "--keep-rigid-conditions"}, description = "Do not simplify away conditions "
			+ "that are rigid according to the planning graph (-kr collides with -kd)")
	public boolean keepRigidConditions;
	@Option(names = {"-rc", "--remove-cond-effects"}, description = "Compile conditional effects "
			+ "into multiple STRIPS operators (-rc collides with -kd)")
	public boolean eliminateConditionalEffects;
	
	
	/* 
	 * Planner configuration 
	 */
	
	public enum PlannerType {
		forwardSSS, satBased, hegemannSat, parallel;
	}
	@Option(paramLabel = "plannerType", names = {"-p", "--planner"}, 
			description = "Planner type to use: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "forwardSSS")
	public PlannerType plannerType;
	
	/* Forward search space planning */
	
	public enum HeuristicType {
		manhattanGoalDistance, relaxedPathLength, actionInterferenceRelaxation, ffTrautmann, ffFroleyks, ffWilliams;
	}
	@Option(paramLabel = "heuristicClass", names = {"-H", "--heuristic"}, 
			description = "Heuristic for forward search: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "ffTrautmann")
	public HeuristicType heuristic;
	@Option(paramLabel = "heuristicWeight", names = {"-w", "--heuristic-weight"},
			description = "Weight of heuristic when using a weighted search strategy " + USAGE_DEFAULT, 
			defaultValue = "10")
	public int heuristicWeight;
	
	@Option(paramLabel = "searchStrategy", names = {"-s", "--search"}, 
			description = "Search strategy for forward search: " + USAGE_OPTIONS_AND_DEFAULT, 
			defaultValue = "bestFirst")
	public SearchStrategy.Mode searchStrategy;
	
	@Option(names = {"-r", "--revisit-states"}, description = "Re-enter a search node "
			+ "even when the state has been reached before")
	public boolean revisitStates;
	
	@Option(names = {"-S", "--seed"}, description = "Random seed to use for randomized search strategies",
			defaultValue = "1337")
	public int seed = 1337;
	
	/* SAT-based planning */
	
	public enum SatSolverMode {
		sat4j, ipasir4j;
	}
	@Option(names = {"--sat-mode"}, description = "Which SAT solver to use, i.e. internal SAT4j or external solver via ipasir4j: "
			+ USAGE_OPTIONS_AND_DEFAULT, defaultValue = "sat4j")
	public SatSolverMode satSolverMode;
	
	
	/* 
	 * Post-processing 
	 */
	
	@Option(names = {"-O", "--optimize"}, description = "Optimize plan during postprocessing")
	public boolean optimizePlan;
	
	
	/**
	 * currentTimeMillis() time when the application was launched.
	 */
	public long startTimeMillis;
	
	public Configuration() {
		startTimeMillis = System.currentTimeMillis();
	}

	public Configuration copy() {
		
		Configuration config = new Configuration();
		config.domainFile = domainFile;
		config.problemFile = problemFile;
		config.planOutputFile = planOutputFile;
		config.maxIterations = maxIterations;
		config.maxTimeSeconds = maxTimeSeconds;
		config.numThreads = numThreads;
		config.keepDisjunctions = keepDisjunctions;
		config.plannerType = plannerType;
		config.heuristic = heuristic;
		config.heuristicWeight = heuristicWeight;
		config.searchStrategy = searchStrategy;
		config.revisitStates = revisitStates;
		config.seed = seed;
		config.satSolverMode = satSolverMode;
		config.optimizePlan = optimizePlan;
		config.startTimeMillis = startTimeMillis;
		config.searchTimeSeconds = searchTimeSeconds;
		return config;
	}

	@Override
	public String toString() {
		return "Configuration \n domainFile=" + domainFile + "\n problemFile=" + problemFile + "\n planOutputFile="
				+ planOutputFile + "\n maxIterations=" + maxIterations + "\n maxTimeSeconds=" + maxTimeSeconds
				+ "\n searchTimeSeconds=" + searchTimeSeconds + "\n numThreads=" + numThreads + "\n verbosityLevel="
				+ verbosityLevel + "\n keepDisjunctions=" + keepDisjunctions 
				+ "\n plannerType=" + plannerType + "\n heuristic=" + heuristic + "\n heuristicWeight=" + heuristicWeight
				+ "\n searchStrategy=" + searchStrategy + "\n revisitStates=" + revisitStates + "\n seed=" + seed
				+ "\n satSolverMode=" + satSolverMode + "\n optimizePlan=" + optimizePlan 
				+ "\n startTimeMillis=" + startTimeMillis + "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domainFile == null) ? 0 : domainFile.hashCode());
		result = prime * result + (eliminateConditionalEffects ? 1231 : 1237);
		result = prime * result + ((heuristic == null) ? 0 : heuristic.hashCode());
		result = prime * result + heuristicWeight;
		result = prime * result + (keepDisjunctions ? 1231 : 1237);
		result = prime * result + (keepRigidConditions ? 1231 : 1237);
		result = prime * result + maxIterations;
		result = prime * result + maxTimeSeconds;
		result = prime * result + numThreads;
		result = prime * result + (optimizePlan ? 1231 : 1237);
		result = prime * result + ((planOutputFile == null) ? 0 : planOutputFile.hashCode());
		result = prime * result + ((plannerType == null) ? 0 : plannerType.hashCode());
		result = prime * result + ((problemFile == null) ? 0 : problemFile.hashCode());
		result = prime * result + (revisitStates ? 1231 : 1237);
		result = prime * result + ((satFormulaFile == null) ? 0 : satFormulaFile.hashCode());
		result = prime * result + ((satSolverMode == null) ? 0 : satSolverMode.hashCode());
		result = prime * result + ((searchStrategy == null) ? 0 : searchStrategy.hashCode());
		result = prime * result + searchTimeSeconds;
		result = prime * result + seed;
		result = prime * result + (int) (startTimeMillis ^ (startTimeMillis >>> 32));
		result = prime * result + verbosityLevel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (domainFile == null) {
			if (other.domainFile != null)
				return false;
		} else if (!domainFile.equals(other.domainFile))
			return false;
		if (eliminateConditionalEffects != other.eliminateConditionalEffects)
			return false;
		if (heuristic != other.heuristic)
			return false;
		if (heuristicWeight != other.heuristicWeight)
			return false;
		if (keepDisjunctions != other.keepDisjunctions)
			return false;
		if (keepRigidConditions != other.keepRigidConditions)
			return false;
		if (maxIterations != other.maxIterations)
			return false;
		if (maxTimeSeconds != other.maxTimeSeconds)
			return false;
		if (numThreads != other.numThreads)
			return false;
		if (optimizePlan != other.optimizePlan)
			return false;
		if (planOutputFile == null) {
			if (other.planOutputFile != null)
				return false;
		} else if (!planOutputFile.equals(other.planOutputFile))
			return false;
		if (plannerType != other.plannerType)
			return false;
		if (problemFile == null) {
			if (other.problemFile != null)
				return false;
		} else if (!problemFile.equals(other.problemFile))
			return false;
		if (revisitStates != other.revisitStates)
			return false;
		if (satFormulaFile == null) {
			if (other.satFormulaFile != null)
				return false;
		} else if (!satFormulaFile.equals(other.satFormulaFile))
			return false;
		if (satSolverMode != other.satSolverMode)
			return false;
		if (searchStrategy != other.searchStrategy)
			return false;
		if (searchTimeSeconds != other.searchTimeSeconds)
			return false;
		if (seed != other.seed)
			return false;
		if (startTimeMillis != other.startTimeMillis)
			return false;
		if (verbosityLevel != other.verbosityLevel)
			return false;
		return true;
	}
}
