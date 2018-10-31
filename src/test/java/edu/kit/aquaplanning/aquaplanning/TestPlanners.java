package edu.kit.aquaplanning.aquaplanning;

import java.io.IOException;

import edu.kit.aquaplanning.grounding.Grounder;
import edu.kit.aquaplanning.grounding.RelaxedPlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.parsing.ProblemParser;
import edu.kit.aquaplanning.planners.DefaultPlanner;
import edu.kit.aquaplanning.planners.Planner;
import edu.kit.aquaplanning.validate.Validator;
import junit.framework.TestCase;

public class TestPlanners extends TestCase {
	
	public void testDefaultPlannerOnTestfiles() throws IOException {
		Planner planner = new DefaultPlanner();
		
		testPlannerOnProblem(planner, new ProblemParser().parse("testfiles/childsnack/domain.pddl", "testfiles/childsnack/p01.pddl"));
		testPlannerOnProblem(planner, new ProblemParser().parse("testfiles/gripper/domain.pddl", "testfiles/gripper/p01.pddl"));
		testPlannerOnProblem(planner, new ProblemParser().parse("testfiles/rover/domain.pddl", "testfiles/rover/p01.pddl"));
		testPlannerOnProblem(planner, new ProblemParser().parse("testfiles/TM/domain.pddl", "testfiles/TM/p_det.pddl"));
		testPlannerOnProblem(planner, new ProblemParser().parse("testfiles/RPG/domain.pddl", "testfiles/RPG/p01.pddl"));
	}
	
	private void testPlannerOnProblem(Planner planner, PlanningProblem problem) {
		
		Grounder grounder = new RelaxedPlanningGraphGrounder();
		GroundPlanningProblem planningProblem = grounder.ground(problem);
		Plan plan = planner.findPlan(planningProblem);
		System.out.println(plan);
		assertTrue(Validator.planIsValid(planningProblem, plan));
	}

}