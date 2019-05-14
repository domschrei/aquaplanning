package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.model.lifted.condition.Condition;

/**
 * Grounder doing a reachability analysis through some approximated state space
 * until a fixpoint is reached.
 */
public class PlanningGraphGrounder extends BaseGrounder {

	private PlanningGraph graph;
	private boolean reduceAtoms;

	public PlanningGraphGrounder(Configuration config) {
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

		// Will rigid predicates be removed from the problem?
		reduceAtoms = !config.keepRigidConditions && !config.keepDisjunctions;
		if (reduceAtoms && !problem.getDerivedPredicates().isEmpty()) {
			// TODO properly handle it by also simplifying the DPs' semantics
			Logger.log(Logger.WARN,
					"Derived predicates are in the problem: " + "Cannot simplify away rigid conditions.");
			reduceAtoms = false;
		}

		// Will equality predicates remain in the problem?
		if (!reduceAtoms) {
			// --yes: add equality conditions to initial state
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
		graph = new PlanningGraph(problem);
		while (graph.hasNextLayer()) {
			graph.computeNextLayer();
		}

		// Generate action objects from reached operators
		Logger.log(Logger.INFO_V, "Generating ground and simplified action objects ...");
		Set<Action> actionSet = new HashSet<>();
		LiftedState finalState = getState();
		List<Operator> filteredActions = new ArrayList<>();
		for (Operator op : graph.getLiftedActions()) {
			if (reduceAtoms) {
				op = simplifyRigidConditions(op, finalState);
			}
			// Was the operator simplified away?
			if (op != null) {
				// -- no
				actionSet.add(getAction(op));
				filteredActions.add(op);
			}
		}
		graph.setFilteredActions(filteredActions);
		actions = new ArrayList<>();
		actions.addAll(actionSet);
		actions.sort((a1, a2) -> a1.getName().compareTo(a2.getName()));

		// Extract (and simplify) initial state
		State initialState = getInitialState(finalState, reduceAtoms);

		// Extract (and simplify) goal
		Goal goal = getGoal(finalState, reduceAtoms);

		// Ground derived predicates' semantics
		groundDerivedAtoms(finalState, reduceAtoms);

		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, goal,
				problem.hasActionCosts(), extractAtomNames(), extractNumericAtomNames());
		return planningProblem;
	}

	public LiftedState getState() {
		return new LiftedState(graph.getLiftedState(graph.getCurrentLayer()));
	}

	public List<Operator> getFilteredActions() {
		return graph.getFilteredActions();
	}

	public Map<String, Integer> getArgumentIndices() {
		return graph.getArgumentIndices();
	}
}
