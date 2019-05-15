package edu.kit.aquaplanning.aquaplanning;

import java.io.File;
import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planning.ForwardSearchPlanner;
import edu.kit.aquaplanning.planning.Planner;
import edu.kit.aquaplanning.planning.PortfolioParallelPlanner;
import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import junit.framework.TestCase;

public class TestParallelPlanning extends TestCase {

	public void testSatPlanner() throws IOException {
		Configuration config = new Configuration();
		config.searchTimeSeconds = 3;
		SimpleSatPlanner spp = new SimpleSatPlanner(config);
		spp.setIgnoreAtMostOneAction(true);
		testOnAll(spp);
	}

	public void testSatHeuristic() throws IOException {
		Configuration config = new Configuration();
		config.numThreads = 1;
		config.searchTimeSeconds = 3;
		config.searchStrategy = Mode.bestFirst;
		config.heuristic = HeuristicType.actionInterferenceRelaxation;
		ForwardSearchPlanner fsp = new ForwardSearchPlanner(config);
		testOnAll(fsp);
	}

	public void testParallelPlanner() throws IOException {
		Configuration config = new Configuration();
		config.numThreads = 8;
		config.searchTimeSeconds = 3;
		PortfolioParallelPlanner spp = new PortfolioParallelPlanner(config);
		testOnAll(spp);
	}

	private void testOnAll(Planner planner) throws IOException {
		File benchdir = new File("benchmarks");
		for (File domdir : benchdir.listFiles()) {
			String domain = domdir.getCanonicalPath() + "/domain.pddl";
			for (File f : domdir.listFiles()) {
				if (f.getName().startsWith("p") && f.getName().endsWith(".pddl")) {
					String problem = f.getCanonicalPath();
					// testBenchmark(domain, problem);
					testPlannerOnBenchmark(planner, domain, problem);
				}
			}
		}
	}

	private void testPlannerOnBenchmark(Planner planner, String domain, String problem) throws IOException {
		System.out.println("Testing planner on " + domain + ", " + problem);
		PlanningProblem pp = new ProblemParser().parse(domain, problem);
		Plan<?> p = planner.plan(pp);
		if (p != null) {
			System.out.println(p);
			System.out.println("Plan is valid:" + planner.validatePlan(p));
		} else {
			System.out.println("TIMEOUT");
		}
	}

}
