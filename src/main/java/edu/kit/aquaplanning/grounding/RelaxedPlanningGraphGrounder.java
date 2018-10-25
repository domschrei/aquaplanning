package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;

/**
 * Grounder doing a reachability analysis through some 
 * approximated state space until a fixpoint is reached.
 */
public class RelaxedPlanningGraphGrounder extends BaseGrounder {
	
	/**
	 * Grounds the entire problem.
	 */
	@Override
	public GroundPlanningProblem ground(PlanningProblem problem) {
		
		this.problem = problem;
		
		// Create a sorted list of constants
		constants = new ArrayList<>();
		constants.addAll(problem.getConstants());
		constants.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
		
		// Lifted state, initialized with initial state
		List<Condition> liftedSuperstate = new ArrayList<>();
		liftedSuperstate.addAll(problem.getInitialState());
		
		// Traverse approximated state space
		actions = new ArrayList<>();
		int iteration = 0;
		while (iteration < 10000) {
			
			// Copy last state
			List<Condition> oldState = new ArrayList<>();
			oldState.addAll(liftedSuperstate);
			
			// Which actions are applicable in that state?
			List<Operator> liftedActions = getLiftedActionsReachableFrom(liftedSuperstate);
			for (Operator op : liftedActions) {
				
				// Ground operator
				Action a = getAction(op);
				if (!actions.contains(a)) {
					actions.add(a);
				}
				// Apply all positive effects of the action
				applyPositiveEffects(op, liftedSuperstate);
			}
			
			// If the state did not increase, a fixpoint has been reached
			if (oldState.size() == liftedSuperstate.size())
				break;
			
			iteration++;
		}
		
		// Extract initial state
		List<Atom> initialStateAtoms = new ArrayList<>();
		problem.getInitialState().forEach(cond -> {
			initialStateAtoms.add(atom(cond.getPredicate(), cond.getArguments()));
		});
		// Add "equals" predicates to initial state
		Predicate pEquals = problem.getPredicate("=");
		if (pEquals != null) {	
			// for all objects c: add the condition (= c c)
			for (Argument constant : constants) {
				List<Argument> args = new ArrayList<>();
				args.add(constant);
				args.add(constant);
				initialStateAtoms.add(atom(pEquals, args));
			}
		}
		State initialState = new State(initialStateAtoms);
		
		// Extract goal
		List<Atom> goalAtoms = new ArrayList<>();
		problem.getGoals().forEach(cond -> {
			Atom atom = atom(cond.getPredicate(), cond.getArguments());
			atom.set(!cond.isNegated());
			goalAtoms.add(atom);
		});
		for (Quantification q : problem.getQuantifiedGoals()) {
			// Resolve quantifications into flat sets of atoms
			List<AbstractCondition> conditions = resolveQuantification(q);
			goalAtoms.addAll(getAtoms(conditions));
		}
		Goal goal = new Goal(goalAtoms);
		
		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, 
				goal, problem.hasActionCosts());
		return planningProblem;
	}
}
