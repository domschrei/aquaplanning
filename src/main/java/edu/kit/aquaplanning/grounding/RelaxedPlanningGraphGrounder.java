package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.NumericAtom;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.util.Pair;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.Function;

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
		
		this.problem = problem;
		
		// First, preprocess the problem into a standardized structure
		new Preprocessor(config).preprocess(problem);
		
		// Create a sorted list of constants
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		constants.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
		
		// Will equality predicates remain in the problem?
		if (config.keepEqualities) {
			// add equality conditions
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
		actions = new ArrayList<>();
		int iteration = 0;
		while (graph.hasNextLayer()) {
			graph.computeNextLayer();
			// Ground new operators
			for (Operator op : graph.getLiftedActions(iteration)) {
				Action a = getAction(op); // actual grounding
				if (a != null && !actions.contains(a)) {
					actions.add(a);
				}
			}
			iteration++;
		}
		
		// Extract initial state
		List<Atom> initialStateAtoms = new ArrayList<>();
		graph.getLiftedState(0).forEach(cond -> {
			initialStateAtoms.add(atom(cond.getPredicate(), cond.getArguments()));
		});
		initialStateAtoms.add(atom(trueCondition.getPredicate(), trueCondition.getArguments()));
		State initialState = new State(initialStateAtoms);
		for (Function f : problem.getInitialFunctionValues().keySet()) {
			NumericAtom atom = numericAtom(f, problem.getInitialFunctionValues().get(f));
			initialState.set(atom);
		}
		
		// Extract goal
		ConditionSet goalSet = new ConditionSet(ConditionType.conjunction);
		problem.getGoals().forEach(c -> goalSet.add(c));
		Goal goal;
		Pair<List<Atom>, Precondition> splitGoal = splitAndGroundPrecondition(goalSet);
		if (splitGoal.getRight().getChildren().size() > 0) {
			// Complex goal
			goal = new Goal(splitGoal.getRight());
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
