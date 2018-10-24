package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;

/**
 * Naïve grounder doing an exhaustive enumeration 
 * of all combineable atoms and operators.
 */
public class DefaultGrounder extends BaseGrounder {
	
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
				
		// Instantiate actions
		groundOperators();
		
		// Extract initial state
		State initialState = getState(problem.getInitialState());
		
		// Extract goal
		List<Atom> goalAtomList = getAtoms(problem.getGoals());
		for (Quantification q : problem.getQuantifiedGoals()) {
			goalAtomList.addAll(groundQuantification(q, constants, new ArrayList<>()));
		}
		Goal goal = new Goal(goalAtomList);
		
		// Assemble finished problem
		GroundPlanningProblem planningProblem = new GroundPlanningProblem(initialState, actions, 
				goal, problem.hasActionCosts());
		return planningProblem;
	}
	
	/**
	 * For each operator, assigns each possible combination of constants to its arguments,
	 * creating a number of flat actions. Also does some primitive filtering: actions with
	 * inconsistent and/or trivially unsatisfiable condition sets are dismissed.
	 */
	private void groundOperators() {
		
		actions = new ArrayList<>();
		
		for (Operator operator : problem.getOperators()) {
			
			// Iterator over all possible argument combinations
			List<List<Argument>> eligibleArguments = ArgumentCombination.getEligibleArgumentsOfType(
					operator.getArgumentTypes(), problem, constants);
			ArgumentCombination.Iterator it = new ArgumentCombination.Iterator(
					eligibleArguments);
			while (it.hasNext()) {
				
				// Ground the operator using these arguments
				List<Argument> arguments = it.next();
				
				// Do grounding, and add resulting action
				// (if it hasn't been simplified away)
				Action action = groundOperator(operator, arguments);
				if (action != null)
					actions.add(action);
			}
		}
	}
	
	/**
	 * Grounds a single operator with the provided constants 
	 * as a substitution for the operator's arguments.
	 */
	private Action groundOperator(Operator operator, List<Argument> arguments) {
		
		// Ground preconditions
		List<Atom> preconditions = new ArrayList<>();
		for (AbstractCondition cond : operator.getPreconditions()) {
			if (cond.getConditionType() == ConditionType.atomic) {				
				Atom atom = getAtomOfActionCondition(operator, (Condition) cond, arguments);
				preconditions.add(atom);
			} else if (cond.getConditionType() == ConditionType.quantification) {
				preconditions.addAll(groundQuantification((Quantification) cond, 
						arguments, operator.getArguments()));
			}
		}
		
		// Ground effects
		List<Atom> effects = new ArrayList<>();
		List<ConditionalEffect> conditionalEffects = new ArrayList<>();
		for (AbstractCondition cond : operator.getEffects()) {
			if (cond.getConditionType() == ConditionType.atomic) {	
				Atom atom = getAtomOfActionCondition(operator, (Condition) cond, arguments);
				effects.add(atom);
			} else if (cond.getConditionType() == ConditionType.quantification) {
				effects.addAll(groundQuantification((Quantification) cond, 
						arguments, operator.getArguments()));
			} else if (cond.getConditionType() == ConditionType.consequential) {
				ConsequentialCondition condEffect = (ConsequentialCondition) cond;
				List<Atom> conditions = new ArrayList<>();
				List<Atom> consequences = new ArrayList<>();
				for (Condition c : condEffect.getPrerequisites()) {
					conditions.add(getAtomOfActionCondition(operator, c, arguments));
				}
				for (Condition c : condEffect.getConsequences()) {
					consequences.add(getAtomOfActionCondition(operator, c, arguments));
				}
				conditionalEffects.add(new ConditionalEffect(conditions, consequences));
			}
		}
		
		// Assemble action and simplify the action's conditions
		// (possible leading to "null" if something is not satisfiable)
		String actionName = getActionName(operator, arguments);
		return Simplification.getSimplifiedAction(actionName, preconditions, effects, 
				conditionalEffects, operator.getCost());
	}
	
	/**
	 * Grounds a single quantification on the basis of a constants list
	 * corresponding to some ground arguments of the enclosing action 
	 * and an arguments list corresponding to the (un-ground) arguments 
	 * of the enclosing operator. 
	 * If the quantification does not have an enclosed operator, 
	 * empty lists can be provided (requiring that all un-quantified arguments
	 * in the conditions are constants). 
	 * The result is a flat list of atoms corresponding to each possible 
	 * combination of the quantified variables.
	 */
	private List<Atom> groundQuantification(Quantification q, List<Argument> arguments,
			List<Argument> operatorArguments) {
		
		List<Atom> dequantifiedConds = new ArrayList<>();
		
		if (q.getQuantifier() != Quantifier.universal) {
			System.err.println("Only universal quantifiers are supported.");
		}
		
		// Iterator over all possible combinations of quantified variables' values
		List<Argument> quantifiedArgs = q.getVariables();
		List<List<Argument>> eligibleDequantifiedArgs = ArgumentCombination.getEligibleArguments(
				quantifiedArgs, problem, constants);
		ArgumentCombination.Iterator dequantifiedArgIterator = 
				new ArgumentCombination.Iterator(eligibleDequantifiedArgs);
		
		dequantifiedArgIterator.forEachRemaining(dequantifiedArgs -> {
			// Two types of constant arguments now:
			// * arguments : the action's arguments for this grounding operation
			// * dequantifiedArgs : the arguments for the quantified variables
			
			// For each quantified precondition, find the right constants
			for (Condition cond : q.getConditions()) {
				List<Argument> condArgs = new ArrayList<>();
				
				// For each argument of the condition
				for (int argIdx = 0; argIdx < cond.getNumArgs(); argIdx++) {
					Argument arg = cond.getArguments().get(argIdx);
					Argument c = null;
					
					if (arg.isConstant()) {
						// Easy: arg is already a constant
						c = arg;
					} else {
						// arg is a variable
						// Is this variable bound to the quantifier?
						int qArgIdx = quantifiedArgs.indexOf(arg);
						if (qArgIdx >= 0) {
							// -- yes: assign the corresponding dequantified argument
							c = dequantifiedArgs.get(qArgIdx);
						} else {
							// -- no: assign the fitting action argument
							// (by simple name matching)
							int opArgIdx = 0;
							for (Argument opArg : operatorArguments) {
								if (opArg.getName().equals(arg.getName())) {
									c = arguments.get(opArgIdx);
									break;
								}
								opArgIdx++;
							}
						}
					}
					// Add created constant to this condition's arguments
					condArgs.add(c);
				}
				
				// Assemble atom
				Atom atom = atom(cond.getPredicate(), condArgs);
				atom.set(!cond.isNegated());
				dequantifiedConds.add(atom);
			}						
		});
		
		return dequantifiedConds;
	}
	
	/**
	 * Given an operator and a nested condition, instantiates the condition
	 * with the provided constants to retrieve the corresponding atom.
	 */
	private Atom getAtomOfActionCondition(Operator op, Condition cond, List<Argument> args) {
		
		List<Argument> condConstants = new ArrayList<>();
		
		// For each of the condition's arguments
		for (Argument condArg : cond.getArguments()) {
			
			// Find the correct constant for this argument
			Argument c = null;
			if (condArg.isConstant()) {
				// It is already a constant 
				c = new Argument(condArg.getName(), condArg.getType());
			} else {
				// It is a variable: find its corresponding constant value
				int argIdx = 0;
				for (Argument opArg : op.getArguments()) {
					if (opArg.getName().equals(condArg.getName())) {
						c = new Argument(args.get(argIdx).getName(), opArg.getType());
						break;
					}
					argIdx++;
				}
			}
			
			condConstants.add(c);
		}
		
		// Assemble atom
		Atom condAtom = atom(cond.getPredicate(), condConstants);
		condAtom.set(!cond.isNegated());
		return condAtom;
	}
}
