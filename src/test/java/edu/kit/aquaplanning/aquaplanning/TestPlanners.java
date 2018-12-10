package edu.kit.aquaplanning.aquaplanning;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	
	public static final String[] DEFAULT_TEST_DOMAINS = {"barman", "rover", "childsnack", 
			"gripper", "zenotravel", "nurikabe", "petrinetalignment", "settlers"};
	public static final String[] SAT_TEST_DOMAINS = {"barman", "rover", "childsnack", 
			"gripper", "zenotravel", "nurikabe"};
	public static final String[] ADL_TEST_DOMAINS = {"openstacks"};
	
	private PlanningProblem pp;
	private GroundPlanningProblem gpp;
	
	public void testQuantifications() throws FileNotFoundException, IOException {

		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p1.pddl");
		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p2.pddl");
	}
	
	public void testAdlFeatures() throws FileNotFoundException, IOException {

		Configuration config = new Configuration();
		config.keepDisjunctions = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config);
		config.keepDisjunctions = false;
		config.keepEqualities = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config);
		config.keepDisjunctions = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config);
		
		fullTest("testfiles/adl/domain2.pddl", "testfiles/adl/p2.pddl");
	}
	
	public void testDefaultDomains() throws FileNotFoundException, IOException {

		for (String domain : DEFAULT_TEST_DOMAINS) {
			fullTest("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl");
		}
		for (String domain : ADL_TEST_DOMAINS) {
			fullTest("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl");
		}
	}
	
	public void testSatPlan() throws FileNotFoundException, IOException {
		
		Grounder grounder = new RelaxedPlanningGraphGrounder(new Configuration());
		for (String domain : SAT_TEST_DOMAINS) {
			System.out.println("Testing domain \"" + domain + "\" with SAT.");
			pp = new ProblemParser().parse("testfiles/" + domain + "/domain.pddl", 
					"testfiles/" + domain + "/p01.pddl");
			gpp = grounder.ground(pp);
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
		fullTest(domainFile, problemFile, new Configuration());
	}
	
	private void fullTest(String domainFile, String problemFile, Configuration config) 
			throws FileNotFoundException, IOException {
		
		System.out.println("Testing domain \"" + domainFile 
				+ "\", problem \"" + problemFile + "\".");
			
		System.out.println("Parsing ...");
		pp = new ProblemParser().parse(domainFile, problemFile);
				
		System.out.println("Grounding ...");
		Grounder grounder = new RelaxedPlanningGraphGrounder(new Configuration());
		gpp = grounder.ground(pp);
		
		assertTrue("No actions have been produced during grounding.",
				gpp.getActions().size() > 0);
		
		System.out.println("Planning ...");
		Configuration c = new Configuration();
		c.searchStrategy = Mode.bestFirst;
		c.heuristic = HeuristicType.relaxedPathLength;
		Planner planner = new ForwardSearchPlanner(c);
		Plan plan = planner.findPlan(gpp);
		
		System.out.println(plan);
		assertTrue("No plan has been found.", plan != null);
		assertTrue("The produced plan is empty.", plan.getLength() > 0);
		assertTrue("The produced plan is invalid.", Validator.planIsValid(gpp, plan));
		
		System.out.println("Done.\n");
	}

}