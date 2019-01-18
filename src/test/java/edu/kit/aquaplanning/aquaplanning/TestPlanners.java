package edu.kit.aquaplanning.aquaplanning;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.Configuration.PlannerType;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.optimization.Clock;
import edu.kit.aquaplanning.optimization.SimplePlanOptimizer;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.ForwardSearchPlanner;
import edu.kit.aquaplanning.planners.HegemannsSatPlanner;
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
	
	public void testDerivedPredicates() throws FileNotFoundException, IOException {

		fullTest("testfiles/derivedPredicates/domain1.pddl", "testfiles/derivedPredicates/p1.pddl");
		fullTest("testfiles/derivedPredicates/domain2.pddl", "testfiles/derivedPredicates/p2.pddl");
		fullTest("testfiles/derivedPredicates/domain3.pddl", "testfiles/derivedPredicates/p3.pddl");
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
	
	public void testPlanOptimization() throws FileNotFoundException, IOException {
		
		Configuration config = new Configuration();
		config.optimizePlan = true;
		config.revisitStates = true;
		config.searchStrategy = Mode.randomChoice;
		config.seed = 1337;
		config.plannerType = PlannerType.forwardSSS;
		config.domainFile = "testfiles/gripper/domain.pddl";
		config.problemFile = "testfiles/gripper/p01.pddl";
		PlanningProblem pp = new ProblemParser().parse(config.domainFile, config.problemFile);
		GroundPlanningProblem gpp = new RelaxedPlanningGraphGrounder(config).ground(pp);
		Plan plan = Planner.getPlanner(config).findPlan(gpp);
		assertTrue(Validator.planIsValid(gpp, plan));
		System.out.println("Initial plan length: " + plan.getLength());
		System.out.println(plan);
		Plan newPlan = new SimplePlanOptimizer(gpp).improvePlan(plan, new Clock(5000));
		assertTrue(Validator.planIsValid(gpp, newPlan));
		System.out.println("New plan length: " + newPlan.getLength());
		System.out.println(newPlan);
		assertTrue("The plan optimization somehow worsened the plan.",
				plan.getLength() >= newPlan.getLength());
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
			testHegemannsSatPlan(gpp);
		}
	}
	
	private void testSatPlan(GroundPlanningProblem gpp) {
		Planner planner = new SimpleSatPlanner(new Configuration());
		Plan plan = planner.findPlan(gpp);
		System.out.println(plan);
		assertNotNull(plan);
		assertTrue(plan.getLength() > 0);
		assertTrue(Validator.planIsValid(gpp, plan));	
	}

	private void testHegemannsSatPlan(GroundPlanningProblem gpp) {
		Planner planner = new HegemannsSatPlanner(new Configuration());
		Plan plan = planner.findPlan(gpp);
		System.out.println(plan);
		assertNotNull(plan);
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
		String out = pp.toString();
		assertTrue("String representation of problem is null", out != null);
		
		System.out.println("Grounding ...");
		Grounder grounder = new RelaxedPlanningGraphGrounder(new Configuration());
		gpp = grounder.ground(pp);
		out = gpp.toString();
		assertTrue("String representation of ground problem is null", out != null);
		
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