package edu.kit.aquaplanning.aquaplanning;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.OperatorPlan;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.PlanParser;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.util.Triple;
import edu.kit.aquaplanning.validation.Validator;
import junit.framework.TestCase;

public class TestValidation extends TestCase {
	
	public void testGroundValidation() throws IOException {
	
		for (Triple<String,String,String> instance : getTestInstances()) {
			String domain = instance.getLeft(), problem = instance.getMid(), plan = instance.getRight();
			
			System.out.println("Testing ground plan validation on " + plan);
			PlanningProblem pp = new ProblemParser().parse(domain, problem);
			Grounder grounder = new PlanningGraphGrounder(new Configuration());
			GroundPlanningProblem gpp = grounder.ground(pp);
			
			ActionPlan p = PlanParser.parsePlan(plan, gpp);
			assertTrue("Plan is reported to be invalid, but should be valid.", Validator.planIsValid(gpp, p));
		}
		
	}
	
	public void testLiftedValidation() throws IOException {
		
		for (Triple<String,String,String> instance : getTestInstances()) {
			String domain = instance.getLeft(), problem = instance.getMid(), plan = instance.getRight();
			
			System.out.println("Testing lifted plan validation on " + plan);
			PlanningProblem pp = new ProblemParser().parse(domain, problem);
			
			OperatorPlan p = PlanParser.parsePlan(plan, pp);
			assertTrue(plan + ": Plan is reported to be invalid, but should be valid.", Validator.planIsValid(pp, p));

			Operator op = p.remove(0);
			assertFalse(plan + ": Plan is reported to be valid, but should be invalid.", Validator.planIsValid(pp, p));
			p.appendAtFront(op);
			
			op = p.remove(p.getLength()-1);
			assertFalse(plan + ": Plan is reported to be valid, but should be invalid.", Validator.planIsValid(pp, p));
		}
	}

	private Collection<Triple<String,String,String>> getTestInstances() throws IOException {
		
		List<Triple<String,String,String>> instances = new ArrayList<>();
		
		File benchdir = new File("benchmarks");
		for (File domdir : benchdir.listFiles()) {
			String domain = domdir.getCanonicalPath() + "/domain.pddl";
			// File domain
			for (File f : domdir.listFiles()) {
				if (f.getName().endsWith(".plan.zip")) {
					String plan = f.getCanonicalPath();
					String problem = plan.replaceAll(".sas.plan.zip", "");
					instances.add(new Triple<String, String, String>(domain, problem, plan));
				}
			}
		}
		
		return instances;
	}
}
