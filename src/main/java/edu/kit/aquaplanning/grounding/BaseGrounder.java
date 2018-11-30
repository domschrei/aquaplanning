package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.Effect;
import edu.kit.aquaplanning.model.ground.Effect.EffectType;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Implication;
import edu.kit.aquaplanning.model.lifted.Negation;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;

@SuppressWarnings("unchecked") // needed for return type of getSimpleAtoms
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
		
		List<Object> result = getSimpleAtoms(constantConditions);
		return new State((List<Atom>) result.get(0));
	}
	
	/**
	 * Processes all conditions in the provided list
	 * and returns ground atoms and conditional effects. 
	 * The first element in the returned list is a List<\Atom> consisting of all atoms,
	 * and the second element is a List<\ConditionalEffect> with all conditional effects.
	 * <br/>This method only works for simple, i.e. conjunctive conditions.
	 */
	protected List<Object> getSimpleAtoms(List<? extends AbstractCondition> conditions) {
		
		List<Atom> atoms = new ArrayList<>();
		List<ConditionalEffect> condEffects = new ArrayList<>();
				
		// As long as conditions are left to process ...
		List<AbstractCondition> conditionsToProcess = new ArrayList<>();
		conditionsToProcess.addAll(conditions);
		for (int i = 0; i < conditionsToProcess.size(); i++) {
			AbstractCondition c = conditionsToProcess.get(i);
			
			if (c.getConditionType() == ConditionType.atomic) {
				
				// Atomic condition
				Condition cond = (Condition) c;
				Atom atom = atom(cond.getPredicate(), cond.getArguments());
				atom.set(!cond.isNegated());
				atoms.add(atom);
				
			} else if (c.getConditionType() == ConditionType.negation) {
				
				// Negated atomic condition
				boolean negated = false;
				while (c.getConditionType() == ConditionType.negation) {
					c = ((Negation) c).getChildCondition();
					negated = !negated;
				}
				if (c.getConditionType() != ConditionType.atomic) {
					throw new IllegalArgumentException("Negation surrounds non-atomic condition, "
							+ "but the condition was identified as simple.");
				}
				Condition cond = (Condition) c;
				Atom atom = atom(cond.getPredicate(), cond.getArguments());
				if (cond.isNegated()) negated = !negated;
				atom.set(!negated);
				atoms.add(atom);
				
			} else if (c.getConditionType() == ConditionType.quantification) {
				
				// Resolve quantification and add all resulting atoms
				// to the processing queue
				List<AbstractCondition> dequantifieds = Arrays.asList(
					ArgumentCombination.resolveQuantification((Quantification) c, problem, constants));
				List<Object> results = getSimpleAtoms(dequantifieds);
				atoms.addAll((List<Atom>) results.get(0));
				condEffects.addAll((List<ConditionalEffect>) results.get(1));
			
			} else if (c.getConditionType() == ConditionType.conjunction) {
				
				ConditionSet set = (ConditionSet) c;
				for (AbstractCondition child : set.getConditions()) {
					conditionsToProcess.add(child);
				}
				
			} else if (c.getConditionType() == ConditionType.consequential) {
				
				ConsequentialCondition cc = (ConsequentialCondition) c;
				List<Object> resultsPre = getSimpleAtoms(Arrays.asList(cc.getPrerequisite()));
				List<Object> resultsCons = getSimpleAtoms(Arrays.asList(cc.getConsequence()));
				List<Atom> pre = new ArrayList<>();
				pre.addAll((List<Atom>) resultsPre.get(0));
				List<Atom> cons = new ArrayList<>();
				cons.addAll((List<Atom>) resultsCons.get(0));
				ConditionalEffect effect = new ConditionalEffect(pre, cons);
				condEffects.add(effect);
				
			} else {
				throw new IllegalArgumentException("A condition proves to be complex, "
						+ "but it was previously identified as simple. Condition: " + c);
			}
		};
		
		return Arrays.asList(atoms, condEffects);
	}
	
	protected Precondition toPrecondition(AbstractCondition cond, boolean negated) {
		
		Precondition pre;
		ConditionSet set;
		switch (cond.getConditionType()) {
		case atomic:
			pre = new Precondition(PreconditionType.atom);
			Condition c = (Condition) cond;
			pre.setAtom(atom(c.getPredicate(), c.getArguments(), negated));
			return pre;
		case negation:
			return toPrecondition(((Negation) cond).getChildCondition(), !negated);
		case conjunction:
			pre = new Precondition(PreconditionType.conjunction);
			set = (ConditionSet) cond;
			for (AbstractCondition child : set.getConditions()) {
				pre.add(toPrecondition(child, false));
			}
			break;
		case disjunction:
			pre = new Precondition(PreconditionType.disjunction);
			set = (ConditionSet) cond;
			for (AbstractCondition child : set.getConditions()) {
				pre.add(toPrecondition(child, false));
			}
			break;
		case implication:
			pre = new Precondition(PreconditionType.implication);
			Implication i = (Implication) cond;
			pre.add(toPrecondition(i.getIfCondition(), false));
			pre.add(toPrecondition(i.getThenCondition(), false));
			break;
		case quantification:
			Quantification q = (Quantification) cond;
			AbstractCondition dequantified = ArgumentCombination.resolveQuantification(q, problem, constants);
			return toPrecondition(dequantified, negated);
		default:
			throw new IllegalArgumentException("Invalid precondition type.");
		}
		
		if (negated) {				
			Precondition negPre = new Precondition(PreconditionType.negation);
			negPre.add(pre);
			return negPre;
		} else {
			return pre;
		}
	}
	
	protected Effect toEffect(AbstractCondition cond) {
		
		Effect effect;
		Condition c;
		switch (cond.getConditionType()) {
		case atomic:
			effect = new Effect(EffectType.atom);
			c = (Condition) cond;
			effect.setAtom(atom(c.getPredicate(), c.getArguments()));
			return effect;
		case negation:
			boolean negated = false;
			while (cond.getConditionType() == ConditionType.negation) {
				cond = ((Negation) cond).getChildCondition();
				negated = !negated;
			}
			if (cond.getConditionType() == ConditionType.atomic) {
				effect = new Effect(EffectType.atom);
				c = (Condition) cond;
				effect.setAtom(atom(c.getPredicate(), c.getArguments(), negated));
				return effect;
			} else {
				throw new IllegalArgumentException("Negation inside an effect on a non-atom level.");
			}
		case conjunction:
			effect = new Effect(EffectType.conjunction);
			ConditionSet conj = (ConditionSet) cond;
			for (AbstractCondition child : conj.getConditions()) {
				effect.add(toEffect(child));
			}
			return effect;
		case consequential:
			effect = new Effect(EffectType.condition);
			ConsequentialCondition cc = (ConsequentialCondition) cond;
			effect.setCondition(toPrecondition(cc.getPrerequisite(), false));
			effect.add(toEffect(cc.getConsequence()));
			return effect;
		case quantification:
			Quantification q = (Quantification) cond;
			if (q.getQuantifier() == Quantifier.universal) {
				AbstractCondition dequantified = ArgumentCombination.resolveQuantification(q, problem, constants);
				return toEffect(dequantified);
			}
			return null;
		case disjunction:
		case implication:
		default:
			throw new IllegalArgumentException("Invalid effect type \"" + cond.getConditionType() + ".");
		}
	}
	
	/**
	 * Assemble an Action object out of an operator 
	 * whose arguments are fully replaced by constants.
	 */
	protected Action getAction(Operator liftedAction) {
		
		Action action = null;
		
		// Assemble action name
		String actionName = liftedAction.getName() + "( ";
		for (Argument arg : liftedAction.getArguments()) {
			actionName += arg.getName() + " ";
		}
		actionName += ")";
		
		if (isConditionConjunctive(liftedAction.getPrecondition(), false, false) &&
				isConditionConjunctive(liftedAction.getEffect(), false, false)) {
			
			// Simple action

			// Assemble preconditions and effects
			List<Object> results = getSimpleAtoms(Arrays.asList(liftedAction.getPrecondition()));
			List<Atom> preconditions = (List<Atom>) results.get(0);
			results = getSimpleAtoms(Arrays.asList(liftedAction.getEffect()));
			List<Atom> effects = (List<Atom>) results.get(0);
			List<ConditionalEffect> conditionalEffects = (List<ConditionalEffect>) results.get(1);
			
			// Assemble action
			action = new Action(actionName, preconditions, effects, conditionalEffects);
		
		} else {
			
			// Complex action
			Precondition precond = toPrecondition(liftedAction.getPrecondition(), false);
			Effect effect = toEffect(liftedAction.getEffect());
			action = new Action(actionName, precond, effect);
		}
		
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
	
	
	private boolean isConditionConjunctive(AbstractCondition cond, boolean insideConditionalEffect, boolean insideQuantification) {
		
		switch (cond.getConditionType()) {
		case atomic:
			return true;
		case negation:
			return ((Negation) cond).getChildCondition().getConditionType() == ConditionType.atomic;
		case conjunction:
			for (AbstractCondition c : ((ConditionSet) cond).getConditions()) {
				if (!isConditionConjunctive(c, insideConditionalEffect, insideQuantification))
					return false;
			}
			return true;
		case disjunction:
		case implication:
			return false;
		case consequential:
			if (insideConditionalEffect)
				return false;
			ConsequentialCondition cc = (ConsequentialCondition) cond;
			if (!isConditionConjunctive(cc.getPrerequisite(), true, insideQuantification)) {
				return false;
			}
			if (!isConditionConjunctive(cc.getConsequence(), true, insideQuantification)) {
				return false;
			}
			return true;
		case quantification:
			if (insideQuantification)
				return false;
			if (((Quantification) cond).getQuantifier() == Quantification.Quantifier.existential)
				return false;
			return isConditionConjunctive(((Quantification) cond).getCondition(), insideConditionalEffect, true);
		default:
			throw new IllegalArgumentException("Invalid precondition type.");
		}
	}
}
