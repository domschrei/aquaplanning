package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import edu.kit.aquaplanning.model.lifted.Constant;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;

public class DefaultGrounder implements Grounder {

	private PlanningProblem problem;
	
	private List<Constant> constants;
	private Map<String, Atom> atoms;
	private List<Action> actions;
	
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
		
		// Instantiate atoms
		groundConditions();
		
		// Instantiate actions
		groundOperators();
		
		// Extract initial state
		State initialState = getState(problem.getInitialState());
		
		// Extract goal
		List<Atom> goalAtomList = getAtomList(problem.getGoals());
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
	 * For each predicate in the problem, assigns each possible combination of constants
	 * to its arguments, creating a number of flat atoms.
	 */
	private void groundConditions() {
		
		atoms = new HashMap<>();
		
		// For each defined predicate
		for (Entry<String, Predicate> predicateEntry : problem.getPredicates().entrySet()) {
			Predicate p = predicateEntry.getValue();
			
			// Find eligible constants for each position of the predicate
			List<List<Constant>> eligibleArguments = getEligibleArgumentsOfType(p.getArgumentTypes());
			
			// Iterate over all possible argument combinations
			ArgumentCombinationIterator it = new ArgumentCombinationIterator(eligibleArguments);
			while (it.hasNext()) {
				List<Constant> arguments = it.next();
				
				// Create atom
				String atomName = getAtomName(p, arguments);
				int id = atoms.size();
				Atom atom = new Atom(id, atomName, true);
				atoms.put(atomName, atom);
			}
		}
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
			List<List<Constant>> eligibleArguments = getEligibleArgumentsOfType(
					operator.getArgumentTypes());
			ArgumentCombinationIterator it = new ArgumentCombinationIterator(
					eligibleArguments);
			while (it.hasNext()) {
				
				// Ground the operator using these arguments
				List<Constant> arguments = it.next();
				
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
	private Action groundOperator(Operator operator, List<Constant> arguments) {
		
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
		return getSimplifiedAction(actionName, preconditions, effects, 
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
	private List<Atom> groundQuantification(Quantification q, List<Constant> arguments,
			List<Argument> operatorArguments) {
		
		List<Atom> dequantifiedConds = new ArrayList<>();
		
		if (q.getQuantifier() != Quantifier.universal) {
			System.err.println("Only universal quantifiers are supported.");
		}
		
		// Iterator over all possible combinations of quantified variables' values
		List<Argument> quantifiedArgs = q.getVariables();
		List<List<Constant>> eligibleDequantifiedArgs = getEligibleArguments(quantifiedArgs);
		ArgumentCombinationIterator dequantifiedArgIterator = 
				new ArgumentCombinationIterator(eligibleDequantifiedArgs);
		
		dequantifiedArgIterator.forEachRemaining(dequantifiedArgs -> {
			// Two types of constant arguments now:
			// * arguments : the action's arguments for this grounding operation
			// * dequantifiedArgs : the arguments for the quantified variables
			
			// For each quantified precondition, find the right constants
			for (Condition cond : q.getConditions()) {
				List<Constant> condArgs = new ArrayList<>();
				
				// For each argument of the condition
				for (int argIdx = 0; argIdx < cond.getNumArgs(); argIdx++) {
					Argument arg = cond.getArguments().get(argIdx);
					Constant c = null;
					
					if (arg.isConstant()) {
						// Easy: arg is already a constant
						c = arg.toConstant();
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
				Atom atom = atoms.get(getAtomName(cond.getPredicate(), condArgs)).copy();
				atom.set(!cond.isNegated());
				dequantifiedConds.add(atom);
			}						
		});
		
		return dequantifiedConds;
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	private String getAtomName(Predicate p, List<Constant> args) {
		
		String atomName = p.getName() + "( ";
		for (Constant c : args) {
			atomName += c.getName() + " ";
		}
		atomName += ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an action corresponding to the provided 
	 * operator with the provided list of constant arguments.
	 */
	private String getActionName(Operator op, List<Constant> args) {
		
		String name = op.getName() + "( ";
		for (Constant c : args) {
			name += c.getName() + " ";
		}
		name += ")";
		return name;
	}
	
	/**
	 * Creates a State object corresponding to the provided set
	 * of constant conditions.
	 */
	private State getState(List<Condition> constantConditions) {
		
		return new State(getAtomList(constantConditions));
	}
	
	/**
	 * Converts a list of conditions (only with constants as arguments)
	 * into a list of corresponding atoms.
	 */
	private List<Atom> getAtomList(List<Condition> constantConditions) {
		
		List<Atom> atoms = new ArrayList<>();
		
		// For each condition
		for (Condition cond : constantConditions) {
			List<Argument> args = cond.getArguments();
			List<Constant> argsConst = new ArrayList<>();
			args.forEach(arg -> argsConst.add(new Constant(arg.getName(), arg.getType())));
			
			// Find correct atom for this condition
			Atom initAtom = this.atoms.get(getAtomName(cond.getPredicate(), argsConst)).copy();
			initAtom.set(!cond.isNegated());
			atoms.add(initAtom);
		}
		return atoms;
	}
		
	/**
	 * Assembles an action based on the provided parameters, checks
	 * its consistency, and simplifies away equality conditions and
	 * trivially (un)satisfiable conditions. If the action is found
	 * to be inherently contradictory or unsatisfiable, null is returned.
	 */
	private Action getSimplifiedAction(String name, List<Atom> preconditions, 
			List<Atom> effects, List<ConditionalEffect> conditionalEffects, int cost) {
		
		// Check consistency of conditions (i.e. no contradictory atoms)
		if (isConsistent(preconditions) && isConsistent(effects)) {
			
			// Simplify out all equality-type atoms
			
			// ... in preconditions
			SimplificationResult result = simplifyAtomSet(preconditions);
			if (result == SimplificationResult.unsatisfiable)
				return null; // unsatisfiable; do not add this atom
			
			// ... in conditional effects
			for (int idx = 0; idx < conditionalEffects.size(); idx++) {					
				ConditionalEffect condEff = conditionalEffects.get(idx);
				result = simplifyAtomSet(condEff.getConditions());
				if (result == SimplificationResult.unsatisfiable) {
					// Remove unsatisfiable conditional effect
					conditionalEffects.remove(idx--); 
				} else if (condEff.getConditions().isEmpty()) {
					// All conditions have been simplified away;
					// compile consequences into general effects
					// and discard conditional effect structure
					effects.addAll(condEff.getEffects());
					conditionalEffects.remove(idx--);
				
				// Check consistency of the conditional effect
				} else if (!isConsistent(condEff.getConditions()) 
						|| !isConsistent(condEff.getEffects())) {
					conditionalEffects.remove(idx--);
				}
			}
			
			// Assemble action
			Action action = new Action(name, preconditions, 
					effects, conditionalEffects);
			action.setCost(cost); // action cost, if supplied
			return action;
		}
		
		return null;
	}
	
	/**
	 * Represents the result of the simplification of a list of atoms.
	 */
	private enum SimplificationResult {
		unsatisfiable, shortened, unmodified;
	}
	
	/**
	 * Performs a basic simplification on a set of atoms, resolving
	 * each equality predicate into TRUE of FALSE and thus manipulating
	 * the atom set correspondingly. Reports that either the set
	 * was left unmodified, or that it has been shortened by one or
	 * more atoms, or that it has been found to be unsatisfiable.
	 */
	private SimplificationResult simplifyAtomSet(List<Atom> atoms) {
		
		SimplificationResult result = SimplificationResult.unmodified;
		for (int atomIdx = 0; atomIdx < atoms.size(); atomIdx++) {
			
			Atom atom = atoms.get(atomIdx).copy();
			boolean negated = !atom.getValue();
			atom.set(true);
			if (atom.toString().contains("=")) {
				String[] args = atom.toString().split(" ");
				if (!negated == args[1].equals(args[2])) {
					// Equality holds -- remove from conditions
					result = SimplificationResult.shortened;
					atoms.remove(atomIdx);
					atomIdx--;
				} else {
					// Equality does not hold -- entire set is unsat
					return SimplificationResult.unsatisfiable;
				}
			}
			atom.set(!negated);
		}
		return result;
	}
	
	/**
	 * Checks basic consistency of a set of atoms.
	 * A set of atoms is consistent if it does not contain 
	 * both "X" and "not(X)" for any X.
	 */
	private boolean isConsistent(List<Atom> atoms) {
		
		atoms.sort((a1, a2) -> Integer.valueOf(a1.getId()).compareTo(a2.getId()));
		
		// For each atom
		for (int atomIdx = 0; atomIdx < atoms.size(); atomIdx++) {
			// Does the atom have a neighbor of the same ID?
			if (atomIdx > 0 && atoms.get(atomIdx).getId() == atoms.get(atomIdx-1).getId()) {
				// Do the atoms have contradictory values?
				if (atoms.get(atomIdx).getValue() != atoms.get(atomIdx-1).getValue()) {
					// -- yes
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Given an operator and a nested condition, instantiates the condition
	 * with the provided constants to retrieve the corresponding atom.
	 */
	private Atom getAtomOfActionCondition(Operator op, Condition cond, List<Constant> args) {
		
		List<Constant> condConstants = new ArrayList<>();
		
		// For each of the condition's arguments
		for (Argument condArg : cond.getArguments()) {
			
			// Find the correct constant for this argument
			Constant c = null;
			if (condArg.isConstant()) {
				// It is already a constant 
				c = new Constant(condArg.getName(), condArg.getType());
			} else {
				// It is a variable: find its corresponding constant value
				int argIdx = 0;
				for (Argument opArg : op.getArguments()) {
					if (opArg.getName().equals(condArg.getName())) {
						c = new Constant(args.get(argIdx).getName(), opArg.getType());
						break;
					}
					argIdx++;
				}
			}
			
			condConstants.add(c);
		}
		
		// Assemble atom
		Atom condAtom = atoms.get(getAtomName(cond.getPredicate(), condConstants)).copy();
		condAtom.set(!cond.isNegated());
		return condAtom;
	}
	

	/**
	 * For a list of arguments, returns a list containing all valid
	 * argument combinations which can be retrieved by
	 * replacing each variable in the arguments by a
	 * constant of an appropriate type.
	 * This list of eligible arguments may have been shortened
	 * by applying simplification strategies.
	 */
	private List<List<Constant>> getEligibleArguments(List<Argument> args) {
		
		List<Type> argTypes = new ArrayList<>();
		for (Argument arg : args) {
			argTypes.add(arg.getType());
		}
		return getEligibleArgumentsOfType(argTypes);
	}
	
	/**
	 * Returns each possible combination of constants with the 
	 * provided order of types.
	 */
	private List<List<Constant>> getEligibleArgumentsOfType(List<Type> argTypes) {
		
		List<List<Constant>> eligibleArguments = new ArrayList<>();
		
		// For each provided type
		for (Type argType : argTypes) {
			List<Constant> eligibleArgumentsAtPos = new ArrayList<>();
			
			// For all possible constants at the argument position
			for (Constant c : constants) {
				if (problem.isConstantOfType(c, argType)) {
					
					eligibleArgumentsAtPos.add(c);
				}
			}
			
			eligibleArguments.add(eligibleArgumentsAtPos);
		}
		
		return eligibleArguments;
	}
	
	/**
	 * Given a list of possible constants at each argument index, 
	 * allows to iterate over all possible combinations.
	 */
	private class ArgumentCombinationIterator implements Iterator<List<Constant>> {

		private List<List<Constant>> eligibleArgs;
		private List<Integer> currentArgIndices;
		private boolean hasNext;
		
		/**
		 * @param eligibleArgs At index i, contains a list of all eligible
		 * constants for the argument position i.
		 */
		public ArgumentCombinationIterator(List<List<Constant>> eligibleArgs) {
			
			this.eligibleArgs = eligibleArgs;
			// Set current argument indices to zero
			// (first argument combination)
			currentArgIndices = new ArrayList<>();
			for (int i = 0; i < eligibleArgs.size(); i++) {
				currentArgIndices.add(0);
			}
			hasNext = true;
		}
		
		/**
		 * True, iff there is another combination not retrieved yet.
		 */
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		/**
		 * Get the next combination of constants.
		 */
		@Override
		public List<Constant> next() {
			
			// Create current constant combination
			List<Constant> args = new ArrayList<>();
			int argPos = 0;
			for (int argIdx : currentArgIndices) {
				args.add(eligibleArgs.get(argPos++).get(argIdx));
			}
			
			// Get to next argument combination, if possible
			hasNext = false;
			for (int pos = currentArgIndices.size()-1; pos >= 0; pos--) {
				
				// Are there more argument options at this position?
				if (currentArgIndices.get(pos)+1 < eligibleArgs.get(pos).size()) {
					// -- Yes
					
					// Proceed to the next argument option at this position
					currentArgIndices.set(pos, currentArgIndices.get(pos)+1);
					
					// Reset all succeeding argument options to zero
					for (int posAfter = pos+1; posAfter < currentArgIndices.size(); posAfter++) {
						currentArgIndices.set(posAfter, 0);
					}
					
					hasNext = true;
					break;
				}
			}
			
			return args;
		}
	}
}
