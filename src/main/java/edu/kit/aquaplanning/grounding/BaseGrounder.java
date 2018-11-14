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
	protected Map<Integer, String> atomNames;
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
			atomNames = new HashMap<>();
		}
		// Key: name of atom
		String atomName = getAtomName(p, constants);
		// Does the action already exists?
		if (!atoms.containsKey(atomName)) {
			// -- no: create new atom
			int atomId = atoms.size();
			atoms.put(atomName, new Atom(atomId, atomName, true));
			atomNames.put(atomId, atomName);
		}
		// Return copy of atom
		return atoms.get(atomName).copy();
	}
	
	protected Atom atom(Predicate p, List<Argument> constants, boolean negated) {
		
		Atom atom = atom(p, constants);
		atom.set(!negated);
		return atom;
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
				conditionsToProcess.addAll(
					ArgumentCombination.resolveQuantification((Quantification) c, problem, constants));
				
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
		List<AbstractCondition> conditionsToProcess = new ArrayList<>();
		
		// As long as conditions are left to process ...
		conditionsToProcess.addAll(conditions);
		for (int i = 0; i < conditionsToProcess.size(); i++) {
			
			AbstractCondition c = conditionsToProcess.get(i);
			if (c.getConditionType() == ConditionType.consequential) {
				
				// Ground prerequisites and consequences
				ConsequentialCondition cond = (ConsequentialCondition) c;
				List<Atom> pre = getAtoms(cond.getPrerequisites());
				List<Atom> eff = getAtoms(cond.getConsequences());
				effects.add(new ConditionalEffect(pre, eff));
				
			} else if (c.getConditionType() == ConditionType.quantification) {

				// Resolve quantification and add all resulting atoms
				// to the processing queue
				conditionsToProcess.addAll(
					ArgumentCombination.resolveQuantification(
							(Quantification) c, problem, constants));
			}
		}
		
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
	
	public List<String> extractAtomNames() {
		
		List<String> atomNames = new ArrayList<>();
		for (int i = 0; i < atoms.size(); i++) {
			atomNames.add(this.atomNames.get(i));
		}
		return atomNames;
	}
}
