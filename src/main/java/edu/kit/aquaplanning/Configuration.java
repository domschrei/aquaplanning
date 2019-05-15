package edu.kit.aquaplanning;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import edu.kit.aquaplanning.planning.datastructures.SearchStrategy;
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
	public static final String USAGE_OPTIONS_AND_DEFAULT = "@|fg(green) ${COMPLETION-CANDIDATES}|@ " + USAGE_DEFAULT;

	/*
	 * General properties and settings
	 */

	/* Input files */

	@Parameters(index = "0", paramLabel = "domainFile", description = "Path and name of the PDDL domain file")
	public String domainFile;
	@Parameters(index = "1", paramLabel = "problemFile", description = "Path and name of the PDDL problem file")
	public String problemFile;

	/* Output files */

	@Option(paramLabel = "file", names = "-o", description = "Output plan to file")
	public String planOutputFile;

	@Option(paramLabel = "satFile", names = "-SAT", description = "Output SAT formulae to file(s)")
	public String satFormulaFile;

	/* Validation */

	@Option(paramLabel = "planFile", names = { "-?", "--validate" }, description = "Validate the plan "
			+ "in the provided file (and don't do planning)")
	public String planFileToValidate;

	/* Computational bounds */

	@Option(paramLabel = "maxIterations", names = { "-m",
			"--max-iterations" }, description = "Maximum amount of iterations in outer loop "
					+ "of the planning algorithm", defaultValue = "0")
	public int maxIterations;

	@Option(paramLabel = "maxSeconds", names = { "-t",
			"--max-seconds" }, description = "Maximum total runtime in seconds", defaultValue = "0")
	public int maxTimeSeconds;

	@Option(paramLabel = "searchSeconds", names = { "-st",
			"--search-seconds" }, description = "Maximum search time in seconds", defaultValue = "0")
	public int searchTimeSeconds;

	@Option(paramLabel = "numThreads", names = { "-T",
			"--threads" }, description = "The amount of threads to spawn (where applicable)", defaultValue = "1")
	public int numThreads;

	/* Output */

	@Option(paramLabel = "verbosityLevel", names = { "-v",
			"--verbosity" }, description = "How verbose output should be "
					+ "(0: errors only, 1: warnings, 2: brief information, etc.) "
					+ USAGE_DEFAULT, defaultValue = (Logger.INFO + ""))
	public int verbosityLevel;

	/*
	 * Preprocessing and grounding configuration
	 */

	@Option(names = { "-kd", "--keep-disjunctions" }, description = "Do not compile disjunctive conditions "
			+ "into simple actions, but keep complex logical structure during planning")
	public boolean keepDisjunctions;
	@Option(names = { "-kr", "--keep-rigid-conditions" }, description = "Do not simplify away conditions "
			+ "that are rigid according to the planning graph (-kr collides with -kd)")
	public boolean keepRigidConditions;
	@Option(names = { "-rc", "--remove-cond-effects" }, description = "Compile conditional effects "
			+ "into multiple STRIPS operators (-rc collides with -kd)")
	public boolean eliminateConditionalEffects;
	@Option(names = { "-ko", "--keep-action-costs" }, description = "Do not discard action cost statements")
	public boolean keepActionCosts;

	/*
	 * Planner configuration
	 */

	public enum PlannerType {
		forwardSSS, satBased, hegemannSat, parallel, greedy, seqpfolio, liftedSat;
	}

	@Option(paramLabel = "plannerType", names = { "-p", "--planner" }, description = "Planner type to use: "
			+ USAGE_OPTIONS_AND_DEFAULT, defaultValue = "forwardSSS")
	public PlannerType plannerType;

	/* Forward search space planning */

	public enum HeuristicType {
		manhattanGoalDistance, relaxedPathLength, actionInterferenceRelaxation, ffTrautmann, ffFroleyks, ffWilliams;
	}

	@Option(paramLabel = "heuristicClass", names = { "-H",
			"--heuristic" }, description = "Heuristic for forward search: "
					+ USAGE_OPTIONS_AND_DEFAULT, defaultValue = "ffTrautmann")
	public HeuristicType heuristic;
	@Option(paramLabel = "heuristicWeight", names = { "-w",
			"--heuristic-weight" }, description = "Weight of heuristic when using a weighted search strategy "
					+ USAGE_DEFAULT, defaultValue = "10")
	public int heuristicWeight;

	@Option(paramLabel = "searchStrategy", names = { "-s",
			"--search" }, description = "Search strategy for forward search: "
					+ USAGE_OPTIONS_AND_DEFAULT, defaultValue = "bestFirst")
	public SearchStrategy.Mode searchStrategy;

	@Option(names = { "-r", "--revisit-states" }, description = "Re-enter a search node "
			+ "even when the state has been reached before")
	public boolean revisitStates;

	@Option(names = { "-S",
			"--seed" }, description = "Random seed to use for randomized search strategies", defaultValue = "1337")
	public int seed = 1337;

	/* SAT-based planning */

	public enum SatSolverMode {
		sat4j, ipasir4j;
	}

	@Option(names = {
			"--sat-mode" }, description = "Which SAT solver to use, i.e. internal SAT4j or external solver via ipasir4j: "
					+ USAGE_OPTIONS_AND_DEFAULT, defaultValue = "sat4j")
	public SatSolverMode satSolverMode;

	/*
	 * Post-processing
	 */

	@Option(names = { "-O", "--optimize" }, description = "Optimize plan during postprocessing")
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
		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				if (!Modifier.isFinal(field.getModifiers()))
					field.set(config, field.get(this));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return config;
	}

	@Override
	public String toString() {

		StringBuilder out = new StringBuilder("Configuration { ");
		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				// Only print non-final fields which have a value other than null
				if (!Modifier.isFinal(field.getModifiers()) && field.get(this) != null)
					out.append(field.getName() + "=" + field.get(this) + " ");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		out.append("}");
		return out.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				Object obj = field.get(this);
				if (!Modifier.isFinal(field.getModifiers()))
					result = prime * result + (obj == null ? 0 : obj.hashCode());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Configuration)) {
			return false;
		}
		return hashCode() == ((Configuration) obj).hashCode();
	}
}
