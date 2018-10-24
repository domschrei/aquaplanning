package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;

/**
 * Abstract base class which all grounders should inherit from.
 * Provides a basic data structure for grounding and retrieval
 * of atoms, and implements a few utility methods for different
 * tasks during a grounding procedure. 
 */
public abstract class BaseGrounder implements Grounder {

	protected PlanningProblem problem;
	
	protected List<Argument> constants;
	protected Map<String, Atom> atoms;
	protected List<Action> actions;
	
	/**
	 * Retrieves the atom corresponding to the provided predicate 
	 * and constant arguments. If this atom has not been grounded before,
	 * it will be created.
	 */
	protected Atom atom(Predicate p, List<Argument> constants) {
		
		// Create data structure for atoms, if necessary
		if (atoms == null) {
			atoms = new HashMap<>();
		}
		// Key: name of atom
		String atomName = getAtomName(p, constants);
		// Does the action already exists?
		if (!atoms.containsKey(atomName)) {
			// -- no: create new atom
			atoms.put(atomName, new Atom(atoms.size(), atomName, true));
		}
		// Return copy of atom
		return atoms.get(atomName).copy();
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	protected String getAtomName(Predicate p, List<Argument> args) {
		
		String atomName = p.getName() + "( ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName += ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an action corresponding to the provided 
	 * operator with the provided list of constant arguments.
	 */
	protected String getActionName(Operator op, List<Argument> args) {
		
		String name = op.getName() + "( ";
		for (Argument c : args) {
			name += c.getName() + " ";
		}
		name += ")";
		return name;
	}
	
	/**
	 * Creates a State object corresponding to the provided set
	 * of constant conditions.
	 */
	protected State getState(List<Condition> constantConditions) {
		
		return new State(getAtoms(constantConditions));
	}
	
	/**
	 * Processes all non-consequential conditions in the provided list
	 * and returns a flat list of corresponding atoms.
	 */
	protected List<Atom> getAtoms(List<? extends AbstractCondition> conditions) {
		
		List<Atom> atoms = new ArrayList<>();
		List<AbstractCondition> conditionsToProcess = new ArrayList<>();
		
		// As long as conditions are left to process ...
		conditionsToProcess.addAll(conditions);
		for (int i = 0; i < conditionsToProcess.size(); i++) {
			AbstractCondition c = conditionsToProcess.get(i);
			
			if (c.getConditionType() == ConditionType.atomic) {
				
				// Atomic condition
				Condition cond = (Condition) c;
				Atom atom = atom(cond.getPredicate(), cond.getArguments());
				atom.set(!cond.isNegated());
				atoms.add(atom);
				
			} else if (c.getConditionType() == ConditionType.quantification) {
				
				// Resolve quantification and add all resulting atoms
				// to the processing queue
				conditionsToProcess.addAll(resolveQuantification((Quantification) c));
				
			} // Conditional effects are not treated here
		};
		
		return atoms;
	}
	
	/**
	 * Processes all consequential conditions in the provided list
	 * and returns a list of ground conditional effects.
	 */
	protected List<ConditionalEffect> getConditionalEffects(List<AbstractCondition> conditions) {
		
		List<ConditionalEffect> effects = new ArrayList<>();
		conditions.forEach(c -> {
			if (c.getConditionType() == ConditionType.consequential) {
				
				// Ground prerequisites and consequences
				ConsequentialCondition cond = (ConsequentialCondition) c;
				List<Atom> pre = getAtoms(cond.getPrerequisites());
				List<Atom> eff = getAtoms(cond.getConsequences());
				effects.add(new ConditionalEffect(pre, eff));
			}
		});
		return effects;
	}
	
	/**
	 * Assemble an Action object out of an operator 
	 * whose arguments are fully replaced by constants.
	 */
	protected Action getAction(Operator liftedAction) {
		
		// Assemble preconditions and effects
		List<Atom> preconditions = getAtoms(liftedAction.getPreconditions());
		List<Atom> effects = getAtoms(liftedAction.getEffects());
		List<ConditionalEffect> conditionalEffects = getConditionalEffects(liftedAction.getEffects());
		
		// Assemble action name
		String actionName = liftedAction.getName() + "( ";
		for (Argument arg : liftedAction.getArguments()) {
			actionName += arg.getName() + " ";
		}
		actionName += ")";
		
		// Assemble action
		Action action = new Action(actionName, preconditions, effects, conditionalEffects);
		action.setCost(liftedAction.getCost());
		return action;
	}
	
	/**
	 * Given a state in lifted representation (i.e. a list of true conditions
	 * where all arguments are replaced by constants), returns all
	 * actions (in lifted representation) which are applicable in that state.
	 */
	protected List<Operator> getLiftedActionsReachableFrom(List<Condition> liftedState) {
		
		List<Operator> reachableOperators = new ArrayList<>();
		
		// For each operator
		for (Operator op : problem.getOperators()) {
					
			// Get all possible argument combinations
			List<List<Argument>> arguments = ArgumentCombination.getEligibleArguments(
					op.getArguments(), problem, constants);
			List<List<Argument>> argumentCombinations = new ArrayList<>();
			new ArgumentCombination.Iterator(arguments).forEachRemaining(l -> argumentCombinations.add(l));
			
			// Now filter this list by checking each precondition
			List<AbstractCondition> preconditions = new ArrayList<>();
			preconditions.addAll(op.getPreconditions());
			for (int condIdx = 0; condIdx < preconditions.size(); condIdx++) {
				AbstractCondition cond = preconditions.get(condIdx);
				
				if (cond.getConditionType() == ConditionType.atomic) {
					
					// Find out which argument choices of the operator 
					// are valid such that the precondition is satisfied
					for (int combIdx = 0; combIdx < argumentCombinations.size(); combIdx++) {
						List<Argument> args = argumentCombinations.get(combIdx);
						
						// Does the precondition hold with these arguments?
						if (!holdsCondition((Condition) cond, op, args, liftedState)) {
							// -- no; directly remove it
							argumentCombinations.remove(combIdx--);
						}
					}
					
				} else if (cond.getConditionType() == ConditionType.quantification) {
					
					// Fully instantiate the quantification, producing a set
					// of atomic conditions which we will check later
					preconditions.addAll(resolveQuantification((Quantification) cond));
				}
			}

			// Create one lifted action for each valid choice of arguments
			for (List<Argument> validArgs : argumentCombinations) {
				Operator liftedAction = op.getOperatorWithGroundArguments(validArgs);
				reachableOperators.add(liftedAction);
			}
		}
		
		return reachableOperators;
	}
	
	/**
	 * Given a lifted action executed in some (lifted) state, adds all of 
	 * its positive effects (including conditional effects) to the state.
	 */
	protected void applyPositiveEffects(Operator liftedAction, List<Condition> liftedState) {
		 
		List<AbstractCondition> effects = new ArrayList<>();

		// For each effect
		effects.addAll(liftedAction.getEffects());
		for (int i = 0; i < effects.size(); i++) {
			AbstractCondition effect = effects.get(i);
			
			if (effect.getConditionType() == ConditionType.atomic) {
				
				// -- atomic effect: directly add condition, 
				// if positive and not already present
				Condition cond = (Condition) effect;
				if (!cond.isNegated() && !liftedState.contains(cond)) {
					liftedState.add(cond);
				}
				
			} else if (effect.getConditionType() == ConditionType.consequential) {
				
				// -- conditional effect: check if prerequisites hold
				ConsequentialCondition cond = (ConsequentialCondition) effect;
				boolean applyEffects = true;
				for (Condition prerequisite : cond.getPrerequisites()) {
					// Does this prerequisite hold?
					if (!holdsCondition(prerequisite, liftedAction, 
							liftedAction.getArguments(), liftedState)) {
						// -- no; dismiss this conditional effect
						applyEffects = false;
						break;
					}
				}
				// Are all prerequisites satisfied?
				if (applyEffects) {
					// -- yes; add consequences to processing queue
					for (Condition consequence : cond.getConsequences()) {
						effects.add(consequence);
					}
				}
				
			} else if (effect.getConditionType() == ConditionType.quantification) {
				
				// -- quantification: resolve into flat atom list,
				// and add all atoms to processing queue
				effects.addAll(resolveQuantification((Quantification) effect));
			}
		}
	}
	
	/**
	 * Checks if the provided condition inside the provided operator 
	 * holds in the given state when the operator arguments are assigned 
	 * the provided list of arguments.
	 */
	private boolean holdsCondition(Condition cond, Operator op, 
				List<Argument> opArgs, List<Condition> liftedState) {
		
		// Set the ground arguments as the arguments of the condition
		Condition groundCond = cond.getConditionBoundToArguments(op.getArguments(), opArgs);
		//groundConditionArguments(groundCond, op.getArguments(), opArgs);
		
		if (groundCond.getPredicate().getName().equals("=")) {
			// Equality predicate: just check if the two args are equal
			if (groundCond.getArguments().get(0).equals(groundCond.getArguments().get(1))) {
				return !cond.isNegated();
			}
		}
		
		// Search the provided state for this condition
		for (Condition stateCond : liftedState) {
			if (stateCond.equals(groundCond)) {
				return true;
			}
		}
		
		// If the condition is negated, then it holds; else, not
		return cond.isNegated();
	}
	
	/**
	 * Processes a quantification where all arguments except for the 
	 * quantified variables are already ground, and returns a flat list
	 * of atoms providing the same logical information.
	 */
	protected List<AbstractCondition> resolveQuantification(Quantification q) {
		
		List<AbstractCondition> dequantifiedConds = new ArrayList<>();
		
		if (q.getQuantifier() != Quantifier.universal) {
			System.err.println("Only universal quantifiers are supported.");
		}
		
		// Iterator over all possible combinations of quantified variables' values
		List<Argument> quantifiedArgs = q.getVariables();
		List<List<Argument>> eligibleDequantifiedArgs = ArgumentCombination
				.getEligibleArguments(quantifiedArgs, problem, constants);
		ArgumentCombination.Iterator dequantifiedArgIterator = 
				new ArgumentCombination.Iterator(eligibleDequantifiedArgs);
		
		dequantifiedArgIterator.forEachRemaining(dequantifiedArgs -> {
			// dequantifiedArgs : the arguments for the quantified variables
			
			// For each quantified condition, create a condition
			// with all quantified variables replaced
			for (Condition cond : q.getConditions()) {
				List<Argument> condArgs = new ArrayList<>();
				
				// For each argument of the condition
				for (int argIdx = 0; argIdx < cond.getNumArgs(); argIdx++) {
					Argument arg = cond.getArguments().get(argIdx);
					Argument c = arg.copy();
					
					if (!arg.isConstant()) {
						// arg is a variable
						// Is this variable bound to the quantifier?
						int qArgIdx = quantifiedArgs.indexOf(arg);
						if (qArgIdx >= 0) {
							// -- yes: assign the corresponding dequantified argument
							c = dequantifiedArgs.get(qArgIdx);
						}
					}
					// Add created constant to this condition's arguments
					condArgs.add(c);
				}
				
				// Assemble new condition
				Condition newCondition = new Condition(cond.getPredicate(), cond.isNegated());
				for (Argument arg : condArgs) newCondition.addArgument(arg);
				dequantifiedConds.add(newCondition);
			}						
		});
		
		return dequantifiedConds;
	}
}
