package edu.kit.aquaplanning.aquaplanning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.ForwardSearchPlanner;
import edu.kit.aquaplanning.planners.Planner;
import edu.kit.aquaplanning.planners.SearchStrategy.Mode;
import edu.kit.aquaplanning.planners.SimpleSatPlanner;
import edu.kit.aquaplanning.validate.Validator;
import junit.framework.TestCase;

public class TestPlanners extends TestCase {
		
	public void testQuantifications() throws FileNotFoundException, IOException {

		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p1.pddl");
		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p2.pddl");
	}
	
	public void testAdlFeatures() throws FileNotFoundException, IOException {

		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl");
		fullTest("testfiles/adl/domain2.pddl", "testfiles/adl/p2.pddl");
	}
	
	public void testDefaultDomains() throws FileNotFoundException, IOException {
		
		String[] domains = {"rover", "childsnack", "gripper"};
		for (String domain : domains) {
			fullTest("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl");
		}
	}
	
	public void testSatPlan() throws FileNotFoundException, IOException {
		String[] domains = {"rover", "childsnack", "gripper"};
		Grounder grounder = new RelaxedPlanningGraphGrounder(new Configuration());
		for (String domain : domains) {
			PlanningProblem pp = new ProblemParser().parse("testfiles/" + domain + "/domain.pddl", 
					"testfiles/" + domain + "/p01.pddl");
			GroundPlanningProblem gpp = grounder.ground(pp);
			testSatPlan(gpp);
		}
	}
	
	private void testSatPlan(GroundPlanningProblem gpp) {
		Planner planner = new SimpleSatPlanner(new Configuration());
		Plan plan = planner.findPlan(gpp);
		System.out.println(plan);
		assertTrue(plan != null);
		assertTrue(plan.getLength() > 0);
		assertTrue(Validator.planIsValid(gpp, plan));	
	}


	public void testCustomDomains() throws FileNotFoundException, IOException {

		fullTest("testfiles/RPG/domain.pddl", "testfiles/RPG/p01.pddl");
		fullTest("testfiles/TM/domain.pddl", "testfiles/TM/p_det.pddl");
	}
	
	private void fullTest(String domainFile, String problemFile) throws FileNotFoundException, IOException {
		
		System.out.println("Testing domain \"" + domainFile + "\", problem \"" + problemFile + "\".");
		
		List<Grounder> grounders = new ArrayList<>();
		grounders.add(new RelaxedPlanningGraphGrounder(new Configuration()));
				
		System.out.println("Parsing ...");
		PlanningProblem pp = new ProblemParser().parse(domainFile, problemFile);
		
		for (Grounder grounder : grounders) {
			
			System.out.println("Grounding with " 
					+ grounder.getClass().getSimpleName() + "...");
			GroundPlanningProblem gpp = grounder.ground(pp);
			
			System.out.println("Planning ...");
			Configuration c = new Configuration();
			c.searchStrategy = Mode.bestFirst;
			c.heuristic = HeuristicType.relaxedPathLength;
			Planner planner = new ForwardSearchPlanner(c);
			Plan plan = planner.findPlan(gpp);
			
			System.out.println(plan);
			assertTrue(plan != null);
			assertTrue(plan.getLength() > 0);
			assertTrue(Validator.planIsValid(gpp, plan));
			
			System.out.println("Done.");
		}
		
		System.out.println();
	}

}