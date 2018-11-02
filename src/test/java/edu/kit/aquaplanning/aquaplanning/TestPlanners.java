package edu.kit.aquaplanning.aquaplanning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.grounding.FullEnumerationGrounder;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.DefaultPlanner;
import edu.kit.aquaplanning.validate.Validator;
import junit.framework.TestCase;

public class TestPlanners extends TestCase {
		
	public void testQuantifications() throws FileNotFoundException, IOException {

		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p1.pddl");
		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p2.pddl");
	}
	
	public void testDefaultDomains() throws FileNotFoundException, IOException {
		
		String[] domains = {"rover", "childsnack", "gripper"};
		for (String domain : domains) {
			fullTest("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl");
		}
	}
	
	public void testCustomDomains() throws FileNotFoundException, IOException {

		fullTest("testfiles/RPG/domain.pddl", "testfiles/RPG/p01.pddl");
		fullTest("testfiles/TM/domain.pddl", "testfiles/TM/p_det.pddl");
	}
	
	private void fullTest(String domainFile, String problemFile) throws FileNotFoundException, IOException {
		
		System.out.println("Testing domain \"" + domainFile + "\", problem \"" + problemFile + "\".");
		
		List<Grounder> grounders = new ArrayList<>();
		grounders.add(new FullEnumerationGrounder());
		grounders.add(new RelaxedPlanningGraphGrounder());
				
		System.out.println("Parsing ...");
		PlanningProblem pp = new ProblemParser().parse(domainFile, problemFile);
		
		for (Grounder grounder : grounders) {
			
			System.out.println("Grounding with " 
					+ grounder.getClass().getSimpleName() + "...");
			GroundPlanningProblem gpp = grounder.ground(pp);
			
			System.out.println("Planning ...");
			Plan plan = new DefaultPlanner().findPlan(gpp);
			
			assertTrue(plan != null && plan.getLength() > 0);
			assertTrue(Validator.planIsValid(gpp, plan));
			
			System.out.println("Done.");
		}
		
		System.out.println();
	}

}