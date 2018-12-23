package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.DerivedCondition;

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
	@SuppressWarnings("unchecked") // needed for return type of getSimpleAtoms
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
		State initialState = new State(initialStateAtoms);
		
		// Extract goal
		AbstractCondition complexGoal = null;
		List<Atom> goalAtoms = new ArrayList<>();
		// For each goal
		for (AbstractCondition cond : problem.getGoals()) {
			
			// Simplify equalities from condition, if necessary
			if (!config.keepEqualities) {				
				cond = resolveEqualities(cond);
				if (trueCondition.equals(cond)) {
					// Precondition is always true: not necessary in goal
					continue;
				} else if (falseCondition.equals(cond)) {
					// Condition is always false: goal is unsatisfiable
					return null;
				}
			}
			
			// Is the condition simple?
			if (isConditionConjunctive(cond, false, false)) {				
				if (cond.getConditionType() == ConditionType.quantification) {				
					// Resolve quantifications into flat sets of atoms
					AbstractCondition condition = ArgumentCombination.resolveQuantification(
							(Quantification) cond, problem, constants);
					List<Object> results = getSimpleAtoms(Arrays.asList(condition));
					goalAtoms.addAll((List<Atom>) results.get(0));
				} else if (cond.getConditionType() == ConditionType.conjunction) {
					// Conjunction
					List<Object> results = getSimpleAtoms(((ConditionSet) cond).getConditions());
					goalAtoms.addAll((List<Atom>) results.get(0));
				} else {
					if (cond instanceof DerivedCondition) {
						// Derived condition
						complexGoal = cond;
						break;
					} else {						
						// Atom
						Condition c = (Condition) cond;
						Atom atom = atom(c.getPredicate(), c.getArguments());
						atom.set(!c.isNegated());
						goalAtoms.add(atom);
					}
				}
			} else {
				// Complex condition
				complexGoal = cond;
				break;
			}
		}
		Goal goal;
		if (complexGoal == null) {
			// Goal with simple list of atoms
			goal = new Goal(goalAtoms);
		} else {
			// Goal with a complex logical expression
			if (problem.getGoals().size() != 1) {
				throw new IllegalArgumentException("If the goal is complex, it "
						+ "must be one single condition after preprocessing.");
			}
			Precondition pre = toPrecondition(complexGoal, false);
			goal = new Goal(pre);
		}
		
		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, 
				goal, problem.hasActionCosts(), extractAtomNames());
		return planningProblem;
	}
}
