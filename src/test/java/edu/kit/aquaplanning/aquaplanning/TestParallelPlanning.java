package edu.kit.aquaplanning.aquaplanning;

import java.io.File;
import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.SimpleParallelPlanner;
import edu.kit.aquaplanning.validate.Validator;
import junit.framework.TestCase;

public class TestParallelPlanning extends TestCase {

	public void testOptimizer() throws IOException {
		File benchdir = new File("benchmarks");
		for (File domdir : benchdir.listFiles()) {
			String domain = domdir.getCanonicalPath() + "/domain.pddl";
			//if (domain.contains("Barman") || domain.contains("Childsnack")) {
			//	continue;
			//}
			//File domain
			for (File f : domdir.listFiles()) {
				if (f.getName().startsWith("p") && f.getName().endsWith(".pddl")) {
					String problem = f.getCanonicalPath();
					testBenchmark(domain, problem);
				}
			}
		}
	}

	private void testBenchmark(String domain, String problem) throws IOException {
		System.out.println("Testing planner on " + problem);
		PlanningProblem pp = new ProblemParser().parse(domain, problem);
		Grounder grounder = new RelaxedPlanningGraphGrounder(new Configuration());
		GroundPlanningProblem gpp = grounder.ground(pp);
		Configuration config = new Configuration();
		
		config.numThreads = 8;
		config.maxTimeSeconds = 3;
		
		// TODO Change here to test your own planner
		SimpleParallelPlanner spp = new SimpleParallelPlanner(config);
		Plan p = spp.findPlan(gpp);
		if (p != null) {
			System.out.println("Plan is valid:" + Validator.planIsValid(gpp, p));
		} else {
			System.out.println("TIMEOUT");
		}
		

	}

}
