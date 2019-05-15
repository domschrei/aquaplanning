package edu.kit.aquaplanning.aquaplanning;

import java.io.File;
import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.optimization.Clock;
import edu.kit.aquaplanning.optimization.SimplePlanOptimizer;
import edu.kit.aquaplanning.parsing.PlanParser;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.validation.Validator;
import junit.framework.TestCase;

public class TestPlanOptimizer extends TestCase {

	public void testOptimizer() throws IOException {
		File benchdir = new File("benchmarks");
		for (File domdir : benchdir.listFiles()) {
			String domain = domdir.getCanonicalPath() + "/domain.pddl";
			// File domain
			for (File f : domdir.listFiles()) {
				if (f.getName().endsWith(".plan.zip")) {
					String plan = f.getCanonicalPath();
					String problem = plan.replaceAll(".sas.plan.zip", "");
					testBenchmark(domain, problem, plan);
				}
			}
		}
	}

	private void testBenchmark(String domain, String problem, String plan) throws IOException {
		System.out.println("Testing plan improver on " + plan);
		PlanningProblem pp = new ProblemParser().parse(domain, problem);
		Grounder grounder = new PlanningGraphGrounder(new Configuration());
		GroundPlanningProblem gpp = grounder.ground(pp);

		ActionPlan p = PlanParser.parsePlan(plan, gpp);
		System.out.println("initial plan valid: " + Validator.planIsValid(gpp, p));

		// TODO change the next line to use your optimizer
		SimplePlanOptimizer spo = new SimplePlanOptimizer(gpp);
		ActionPlan p2 = spo.improvePlan(p, new Clock(1000));
		System.out.println("new plan valid: " + Validator.planIsValid(gpp, p2));
		System.out.println(
				String.format("Plan was improved from %d actions to %d actions", p.getLength(), p2.getLength()));
	}

}
