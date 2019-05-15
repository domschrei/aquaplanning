package edu.kit.aquaplanning.aquaplanning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.HeuristicType;
import edu.kit.aquaplanning.Configuration.PlannerType;
import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.optimization.Clock;
import edu.kit.aquaplanning.optimization.SimplePlanOptimizer;
import edu.kit.aquaplanning.parsing.PlanParser;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planning.ForwardSearchPlanner;
import edu.kit.aquaplanning.planning.GroundPlanner;
import edu.kit.aquaplanning.planning.Planner;
import edu.kit.aquaplanning.planning.datastructures.SearchStrategy.Mode;
import edu.kit.aquaplanning.planning.sat.HegemannsSatPlanner;
import edu.kit.aquaplanning.planning.sat.SimpleSatPlanner;
import edu.kit.aquaplanning.planning.sat.SymbolicReachabilityPlanner;
import edu.kit.aquaplanning.validation.Validator;
import junit.framework.TestCase;

public class TestPlanners extends TestCase {

	public static final String[] DEFAULT_TEST_DOMAINS = { "barman", "rover", "childsnack", "gripper", "zenotravel",
			"nurikabe", "petrinetalignment", "settlers", "GED", "floortile" };
	public static final String[] SAT_TEST_DOMAINS = { "barman", "rover", "childsnack", "gripper", "zenotravel",
			"nurikabe", "GED", "floortile" };
	public static final String[] ADL_TEST_DOMAINS = { "openstacks" };

	private PlanningProblem pp;
	private GroundPlanningProblem gpp;

	public void testQuantifications() throws FileNotFoundException, IOException {

		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p1.pddl", 1, 1);
		fullTest("testfiles/quantifications/domain.pddl", "testfiles/quantifications/p2.pddl", 1, 1);
	}

	public void testDerivedPredicates() throws FileNotFoundException, IOException {

		fullTest("testfiles/derivedPredicates/domain1.pddl", "testfiles/derivedPredicates/p1.pddl", 2, 2);
		fullTest("testfiles/derivedPredicates/domain2.pddl", "testfiles/derivedPredicates/p2.pddl", 1, 1);
		fullTest("testfiles/derivedPredicates/domain3.pddl", "testfiles/derivedPredicates/p3.pddl", 1, 1);
	}

	public void testAdlFeatures() throws FileNotFoundException, IOException {

		Configuration config = new Configuration();

		config.keepDisjunctions = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config, 5, 10);
		fullTest("testfiles/equality/domain1.pddl", "testfiles/equality/p1.pddl", config, 6, 6);

		config.keepDisjunctions = false;
		config.keepRigidConditions = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config, 5, 10);
		fullTest("testfiles/equality/domain1.pddl", "testfiles/equality/p1.pddl", config, 6, 6);

		config.keepDisjunctions = true;
		fullTest("testfiles/adl/domain1.pddl", "testfiles/adl/p1.pddl", config, 5, 10);
		fullTest("testfiles/equality/domain1.pddl", "testfiles/equality/p1.pddl", config, 6, 6);

		fullTest("testfiles/adl/domain2.pddl", "testfiles/adl/p2.pddl", 1, 10);
		fullTest("testfiles/equality/domain2.pddl", "testfiles/equality/p2.pddl", 1, 1);
	}

	public void testNumericPlanning() throws FileNotFoundException, IOException {

		fullTest("testfiles/RPG-with-numeric-fluents/domain.pddl", "testfiles/RPG-with-numeric-fluents/p01.pddl");
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
		GroundPlanningProblem gpp = new PlanningGraphGrounder(config).ground(pp);
		ActionPlan plan = (ActionPlan) Planner.getPlanner(pp, config).plan(pp);
		assertTrue(Validator.planIsValid(gpp, plan));
		System.out.println("Initial plan length: " + plan.getLength());
		System.out.println(plan);
		ActionPlan newPlan = new SimplePlanOptimizer(gpp).improvePlan(plan, new Clock(5000));
		assertTrue(Validator.planIsValid(gpp, newPlan));
		System.out.println("New plan length: " + newPlan.getLength());
		System.out.println(newPlan);
		assertTrue("The plan optimization somehow worsened the plan.", plan.getLength() >= newPlan.getLength());
	}

	public void testGreedyOnDefaultDomains() throws FileNotFoundException, IOException {

		Configuration config = new Configuration();
		config.plannerType = PlannerType.greedy;
		for (String domain : DEFAULT_TEST_DOMAINS) {
			fullTest("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl", config);
		}
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
		Grounder grounder = new PlanningGraphGrounder(new Configuration());
		for (String domain : SAT_TEST_DOMAINS) {
			System.out.println("Testing domain \"" + domain + "\" with SAT.");
			pp = new ProblemParser().parse("testfiles/" + domain + "/domain.pddl", "testfiles/" + domain + "/p01.pddl");
			gpp = grounder.ground(pp);
			testSatPlan(gpp);
			testHegemannsSatPlan(gpp);
			testReachabilityPlanner(gpp);
		}
	}

	private void testSatPlan(GroundPlanningProblem gpp) {
		GroundPlanner planner = new SimpleSatPlanner(new Configuration());
		ActionPlan plan = planner.findPlan(gpp);
		System.out.println(plan);
		assertNotNull(plan);
		assertTrue(plan.getLength() > 0);
		assertTrue(Validator.planIsValid(gpp, plan));
	}

	private void testHegemannsSatPlan(GroundPlanningProblem gpp) {
		GroundPlanner planner = new HegemannsSatPlanner(new Configuration());
		ActionPlan plan = planner.findPlan(gpp);
		System.out.println(plan);
		assertNotNull(plan);
		assertTrue(plan.getLength() > 0);
		assertTrue(Validator.planIsValid(gpp, plan));
	}

	private void testReachabilityPlanner(GroundPlanningProblem gpp) {
		GroundPlanner planner = new SymbolicReachabilityPlanner(new Configuration());
		ActionPlan plan = planner.findPlan(gpp);
		assertNotNull(plan);
		System.out.println(plan);
		assertTrue(plan.getLength() > 0);
		assertTrue(Validator.planIsValid(gpp, plan));
	}

	public void testCustomDomains() throws FileNotFoundException, IOException {

		fullTest("testfiles/RPG/domain.pddl", "testfiles/RPG/p01.pddl", 5, Integer.MAX_VALUE);
		fullTest("testfiles/TM/domain.pddl", "testfiles/TM/p_det.pddl", 8, 8);
	}

	private void fullTest(String domainFile, String problemFile) throws FileNotFoundException, IOException {
		fullTest(domainFile, problemFile, new Configuration());
	}

	private void fullTest(String domainFile, String problemFile, Configuration config)
			throws FileNotFoundException, IOException {

		fullTest(domainFile, problemFile, config, 1, Integer.MAX_VALUE);
	}

	private void fullTest(String domainFile, String problemFile, int minPlanLength, int maxPlanLength)
			throws FileNotFoundException, IOException {

		fullTest(domainFile, problemFile, new Configuration(), minPlanLength, maxPlanLength);
	}

	private void fullTest(String domainFile, String problemFile, Configuration config, int minPlanLength,
			int maxPlanLength) throws FileNotFoundException, IOException {

		System.out.println("Testing domain \"" + domainFile + "\", problem \"" + problemFile + "\".");

		System.out.println("Parsing ...");
		pp = new ProblemParser().parse(domainFile, problemFile);
		String out = pp.toString();
		assertTrue("String representation of problem is null", out != null);

		System.out.println("Grounding ...");
		Grounder grounder = new PlanningGraphGrounder(config);
		gpp = grounder.ground(pp);
		out = gpp.toString();
		assertTrue("String representation of ground problem is null", out != null);

		assertTrue("No actions have been produced during grounding.", gpp.getActions().size() > 0);

		System.out.println("Planning ...");
		if (config.searchStrategy == null) {
			config.searchStrategy = Mode.bestFirst;
			config.heuristic = HeuristicType.relaxedPathLength;
		}
		GroundPlanner planner = new ForwardSearchPlanner(config);
		ActionPlan plan = planner.findPlan(gpp);

		System.out.println(plan);

		assertTrue("No plan has been found.", plan != null);

		assertTrue("The produced plan of length " + plan.getLength()
				+ " is shorter than the minimum valid plan length (" + minPlanLength + ").",
				plan.getLength() >= minPlanLength);
		assertTrue("The produced plan of length " + plan.getLength() + " is larger than the maximum valid plan length ("
				+ maxPlanLength + ").", plan.getLength() <= maxPlanLength);

		assertTrue("The produced plan is invalid.", Validator.planIsValid(gpp, plan));

		// "Naive", basic configuration
		Configuration referenceConfig = new Configuration();
		referenceConfig.keepDisjunctions = true;
		referenceConfig.keepRigidConditions = true;

		if (!config.equals(referenceConfig)) {
			System.out.println("Testing against problem with default config ...");

			// Create a reference ground planning problem under default configuration
			// options
			PlanningProblem referenceP = new ProblemParser().parse(domainFile, problemFile);
			GroundPlanningProblem reference = new PlanningGraphGrounder(referenceConfig).ground(referenceP);

			// Write the found plan and re-interpret it based on the reference problem
			FileWriter w = new FileWriter("_tmp_plan.txt");
			w.write(plan.toString());
			w.close();
			ActionPlan parsedPlan = PlanParser.parsePlan("_tmp_plan.txt", reference);

			// See if the plan is valid under the reference problem, too
			assertTrue("The produced plan is invalid under the problem that is generated using default settings.",
					parsedPlan == null || Validator.planIsValid(reference, parsedPlan));
		}

		System.out.println("Done.\n");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		new File("_tmp_plan.txt").delete();
	}

}
