package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.util.Pair;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;

/**
 * Grounder doing a reachability analysis through some 
 * approximated state space until a fixpoint is reached.
 */
public class RelaxedPlanningGraphGrounder extends BaseGrounder {
	
	public RelaxedPlanningGraphGrounder(Configuration config) {
		super(config);
	}
	
	/**
	 * Grounds the entire problem.
	 */
	@Override
	public GroundPlanningProblem ground(PlanningProblem problem) {
		
		setProblem(problem);
		
		// First, preprocess the problem into a standardized structure
		new Preprocessor(config).preprocess(problem);
		
		// Create a sorted list of constants
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		constants.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
		
		// Will equality predicates remain in the problem?
		if (config.keepEqualities) {
			// --yes: add equality conditions
			Predicate pEquals = problem.getPredicate("=");
			if (pEquals != null) {
				// for all objects c: add the condition (= c c)
				for (Argument constant : constants) {
					List<Argument> args = new ArrayList<>();
					args.add(constant);
					args.add(constant);
					Condition equalsCond = new Condition(pEquals);
					equalsCond.addArgument(constant);
					equalsCond.addArgument(constant);
					problem.getInitialState().add(equalsCond);
				}
			}
		}
		
		// Traverse delete-relaxed state space
		RelaxedPlanningGraph graph = new RelaxedPlanningGraph(problem);
		while (graph.hasNextLayer()) {
			graph.computeNextLayer();
		}
		// Generate action objects
		Logger.log(Logger.INFO_V, "Generating ground action objects ...");
		Set<Action> actionSet = new HashSet<>();
		for (Operator op : graph.getLiftedActions()) {
			Action a = getAction(op); // actual grounding
			if (a != null) {
				actionSet.add(a);
			}
		}
		actions = new ArrayList<>();
		actions.addAll(actionSet);
		actions.sort((a1,a2) -> a1.getName().compareTo(a2.getName()));
		
		// Extract initial state
		State initialState = getInitialState();
		
		// Extract goal
		ConditionSet goalSet = new ConditionSet(ConditionType.conjunction);
		problem.getGoals().forEach(c -> goalSet.add(c));
		Goal goal;
		Pair<List<Atom>, Precondition> splitGoal = splitAndGroundPrecondition(goalSet);
		if (splitGoal.getRight() != null) {
			// Complex goal: add simple AND complex parts
			Precondition complexGoal = splitGoal.getRight();
			splitGoal.getLeft().forEach(atom -> {
				Precondition atomPre = new Precondition(PreconditionType.atom);
				atomPre.setAtom(atom);
				complexGoal.add(atomPre);
			});
			goal = new Goal(complexGoal);
		} else {
			// Simple goal
			goal = new Goal(splitGoal.getLeft());
		}
		
		// Ground derived predicates' semantics
		groundDerivedAtoms();
		
		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, 
				goal, problem.hasActionCosts(), extractAtomNames(), extractNumericAtomNames());
		return planningProblem;
	}
}
