package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.DerivedAtom;
import edu.kit.aquaplanning.model.ground.Effect;
import edu.kit.aquaplanning.model.ground.GroundNumericExpression;
import edu.kit.aquaplanning.model.ground.NumericAtom;
import edu.kit.aquaplanning.model.ground.Effect.EffectType;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.Axiom;
import edu.kit.aquaplanning.model.lifted.Implication;
import edu.kit.aquaplanning.model.lifted.Negation;
import edu.kit.aquaplanning.model.lifted.NumericCondition;
import edu.kit.aquaplanning.model.lifted.NumericEffect;
import edu.kit.aquaplanning.model.lifted.NumericEffect.Type;
import edu.kit.aquaplanning.model.lifted.NumericExpression;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.util.Pair;
import edu.kit.aquaplanning.util.Triple;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.NumericExpression.TermType;

/**
 * Abstract base class which all grounders should inherit from.
 * Provides a basic data structure for grounding and retrieval
 * of atoms, and implements a few utility methods for different
 * tasks during a grounding procedure. 
 */
public abstract class BaseGrounder implements Grounder {

	protected PlanningProblem problem;
	protected Configuration config;
	
	protected List<Argument> constants;
	
	protected Map<String, Atom> atoms;
	protected Map<Integer, String> atomNames;
	
	protected Map<String, DerivedAtom> derivedAtoms;
	protected Map<Integer, String> derivedAtomNames;
	
	protected Map<String, NumericAtom> numericAtoms;
	protected Map<Integer, String> numericAtomNames;
	
	protected List<Action> actions;
	
	public BaseGrounder(Configuration config) {
		this.config = config;
		
		atoms = new HashMap<>();
		atomNames = new HashMap<>();
		derivedAtoms = new HashMap<>();
		derivedAtomNames = new HashMap<>();
		numericAtoms = new HashMap<>();
		numericAtomNames = new HashMap<>();
	}
	
	/**
	 * Retrieves a copy of the atom corresponding to the provided predicate 
	 * and constant arguments. If this atom has not been grounded before,
	 * it will be created.
	 */
	protected Atom atom(Predicate p, List<Argument> constants) {
		
		// Check if predicate is simple
		if (p.isDerived()) {
			throw new IllegalArgumentException("Attempted to create simple atom "
					+ "of a derived predicate.\nPredicate: " + p + "; constants: " + constants);
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
	
	/**
	 * Retrieves the derived atom corresponding to the provided
	 * derived predicate and constant arguments. If this atom has not 
	 * been grounded before, it will be created. Note that the original
	 * object is returned and changes to it will be reflected in the
	 * original data structure.
	 */
	protected DerivedAtom derivedAtom(Predicate p, List<Argument> constants) {
		
		// Check if predicate is simple
		if (!p.isDerived()) {
			throw new IllegalArgumentException("Attempted to create derived atom "
					+ "of a simple predicate.\nPredicate: " + p + "; constants: " + constants);
		}
		// Key: name of atom
		String atomName = getAtomName(p, constants);
		// Does the action already exists?
		if (!derivedAtoms.containsKey(atomName)) {
			// -- no: create new atom
			int atomId = - (atoms.size() + derivedAtoms.size());
			Axiom axiom = problem.getDerivedPredicates().get(p.getName());
			AbstractCondition cond = axiom.getCondition().getConditionBoundToArguments(
					axiom.getArguments(), constants);
			derivedAtoms.put(atomName, new DerivedAtom(atomId, atomName, cond));
			derivedAtomNames.put(atomId, atomName);
		}
		// Return derived atom
		return derivedAtoms.get(atomName);
	}
	
	protected NumericAtom numericAtom(Function f, float value) {
		
		String atomName = getAtomName(f);
		if (!numericAtoms.containsKey(atomName)) {
			int atomId = numericAtoms.size();
			NumericAtom atom = new NumericAtom(atomId, atomName, Float.NaN);
			numericAtoms.put(atomName, atom);
			numericAtomNames.put(atomId, atomName);
		}
		NumericAtom valuedAtom = numericAtoms.get(atomName).copy();
		valuedAtom.setValue(value);
		return valuedAtom;
	}
	
	/**
	 * Retrieves a copy of the atom corresponding to the provided predicate 
	 * and constant arguments. If this atom has not been grounded before,
	 * it will be created.
	 */
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
		
		String atomName = "(" + p.getName() + " ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	protected String getAtomName(Function f) {
		
		String atomName = "(" + f.getName() + " ";
		for (Argument c : f.getArguments()) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an action corresponding to the provided 
	 * operator with the provided list of constant arguments.
	 */
	protected String getActionName(Operator op, List<Argument> args) {
		
		String atomName = "(" + op.getName() + " ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Creates a State object corresponding to the provided set
	 * of constant conditions.
	 */
	protected State getState(List<Condition> constantConditions) {
		
		ConditionSet set = new ConditionSet(ConditionType.conjunction);
		constantConditions.forEach(c -> set.add(c));
		Pair<List<Atom>, Precondition> result = splitAndGroundPrecondition(set);
		return new State(result.getLeft());
	}
	
	protected Pair<List<Atom>, Precondition> splitAndGroundPrecondition(AbstractCondition cond) {
		
		Pair<ConditionSet, ConditionSet> split = splitCondition(cond);
		ConditionSet simpleSet = split.getLeft();
		ConditionSet complexSet = split.getRight();
		
		List<Atom> atomList = new ArrayList<>();
		Precondition complexPrecondition = new Precondition(PreconditionType.conjunction);
		
		for (AbstractCondition c : simpleSet.getConditions()) {
			if (c.getConditionType() != ConditionType.atomic) {
				error("A simple set of conditions contains non-atomic condition " + c + ".");
			}
			Condition liftedAtom = (Condition) c;
			atomList.add(atom(liftedAtom.getPredicate(), liftedAtom.getArguments(), liftedAtom.isNegated()));
		}
		
		for (AbstractCondition c : complexSet.getConditions()) {
			complexPrecondition.add(toPrecondition(c, false));
		}
		
		return new Pair<>(atomList, complexPrecondition);
	}
	
	protected Triple<List<Atom>, List<ConditionalEffect>, Effect> splitAndGroundEffect(AbstractCondition cond) {
		
		Pair<ConditionSet, ConditionSet> split = splitCondition(cond);
		ConditionSet simpleSet = split.getLeft();
		ConditionSet complexSet = split.getRight();
		
		List<Atom> atomList = new ArrayList<>();
		List<ConditionalEffect> condEffList = new ArrayList<>();
		Effect complexEffect = new Effect(EffectType.conjunction);
		
		for (AbstractCondition c : simpleSet.getConditions()) {
			if (c.getConditionType() != ConditionType.atomic) {
				error("A simple set of conditions contains non-atomic condition " + c + ".");
			}
			Condition liftedAtom = (Condition) c;
			atomList.add(atom(liftedAtom.getPredicate(), liftedAtom.getArguments(), liftedAtom.isNegated()));
		}
		
		List<AbstractCondition> complexSetList = new ArrayList<>();
		complexSetList.addAll(complexSet.getConditions());
		while (!complexSetList.isEmpty()) {
			AbstractCondition c = complexSetList.remove(0);
			if (c.getConditionType() == ConditionType.conjunction) {
				complexSetList.addAll(((ConditionSet) c).getConditions());
			} else if (c.getConditionType() != ConditionType.consequential) {
				error("Condition " + c + " (type " + c.getConditionType() + ") is part of an effect.");
			} else {
				ConsequentialCondition cc = (ConsequentialCondition) c;
				Pair<List<Atom>, Precondition> splitPrerequisite = splitAndGroundPrecondition(cc.getPrerequisite());
				Triple<List<Atom>, List<ConditionalEffect>, Effect> splitConsequence = splitAndGroundEffect(cc.getConsequence());
				Precondition complexPrerequisite = splitPrerequisite.getRight();
				if (complexPrerequisite == null) {
					// Can compile into an "easy" conditional effect
					List<Atom> simplePrerequisite = splitPrerequisite.getLeft();
					ConditionalEffect eff = new ConditionalEffect(simplePrerequisite, splitConsequence.getLeft());
					condEffList.add(eff);
					
				} else {
					// Add **the whole** cond. effect to complex effects
					Effect condEff = toEffect(c);
					complexEffect.add(condEff);
				}				
			}
		}
		
		return new Triple<>(atomList, condEffList, complexEffect);
	}
	
	protected Pair<ConditionSet, ConditionSet> splitCondition(AbstractCondition cond) {
		
		ConditionSet simplePartSet = new ConditionSet(ConditionType.conjunction);
		ConditionSet complexPartSet = new ConditionSet(ConditionType.conjunction);
		
		if (cond.getConditionType() == ConditionType.atomic) {
			simplePartSet.add(cond);
		
		} else if (cond.getConditionType() == ConditionType.conjunction) {
			for (AbstractCondition child : ((ConditionSet) cond).getConditions()) {
				
				if (child.getConditionType() == ConditionType.atomic) {
					simplePartSet.add(child);
				} else {
					complexPartSet.add(child);
				}
			}
		
		} else {
			complexPartSet.add(cond);
		}
		
		return new Pair<>(simplePartSet, complexPartSet);
	}
	
	/**
	 * Converts a lifted condition into a ground precondition.
	 * Set the negated argument to "false" for a usual top-level call.
	 */
	protected Precondition toPrecondition(AbstractCondition cond, boolean negated) {
		
		Precondition pre = null;
		ConditionSet set;
		switch (cond.getConditionType()) {
		case atomic:
			Condition c = (Condition) cond;
			if (c.getPredicate().isDerived()) {
				// Derived condition: Do not ground the derived meaning,
				// but only add a placeholder derived atom
				pre = new Precondition(PreconditionType.derived);
				pre.setDerivedAtom(derivedAtom(c.getPredicate(), c.getArguments()));
				if (c.isNegated() != negated) {
					// Add enclosing negation around atom
					Precondition neg = new Precondition(PreconditionType.negation);
					neg.add(pre);
					pre = neg;
				}
			} else {
				pre = new Precondition(PreconditionType.atom);
				pre.setAtom(atom(c.getPredicate(), c.getArguments(), c.isNegated() != negated));
			}
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
			// A -> B == (not A) or B
			pre.add(toPrecondition(i.getIfCondition(), true));
			pre.add(toPrecondition(i.getThenCondition(), false));
			break;
		case numericPrecondition:
			pre = new Precondition(PreconditionType.numeric);
			NumericCondition numCond = (NumericCondition) cond;
			pre.setComparator(numCond.getComparator());
			pre.setExpLeft(toGroundNumExp(numCond.getExpLeft()));
			pre.setExpRight(toGroundNumExp(numCond.getExpRight()));
			break;
		default:
			error("Invalid precondition type: " + cond.getConditionType());
		}
		
		// Negated conjunction / disjunction / implication: 
		// Surround by negation, if necessary
		if (negated) {				
			Precondition negPre = new Precondition(PreconditionType.negation);
			negPre.add(pre);
			return negPre;
		} else {
			return pre;
		}
	}
	
	/**
	 * Converts a lifted condition into a ground effect.
	 */
	protected Effect toEffect(AbstractCondition cond) {
		
		Effect effect;
		Condition c;
		switch (cond.getConditionType()) {
		case atomic:
			effect = new Effect(EffectType.atom);
			c = (Condition) cond;
			effect.setAtom(atom(c.getPredicate(), c.getArguments(), c.isNegated()));
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
				error("Negation inside an effect on a non-atomic level.");
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
		case numericEffect:
			effect = new Effect(EffectType.numeric);
			NumericEffect numEffect = (NumericEffect) cond;
			NumericAtom atom = numericAtom(numEffect.getFunction(), Float.NaN);
			GroundNumericExpression goalExp = toGroundNumExp(numEffect.getExpression());
			Type type = numEffect.getType();
			if (type != Type.assign) {
				TermType termType = type == Type.increase ? TermType.addition : 
					type == Type.decrease ? TermType.subtraction : 
					type == Type.scaleUp ? TermType.multiplication : 
					/*type == Type.scaleDown ? */TermType.division;
				GroundNumericExpression composite = new GroundNumericExpression(termType);
				composite.add(new GroundNumericExpression(atom));
				composite.add(goalExp);
				goalExp = composite;
			}
			effect.setFunction(atom);
			effect.setExpression(goalExp);
			return effect;
		default:
			error("Invalid effect type \"" 
					+ cond.getConditionType() + "\".");
			return null;
		}
	}
	
	protected GroundNumericExpression toGroundNumExp(NumericExpression exp) {
		GroundNumericExpression gExp;
		switch (exp.getType()) {
		case constant:
			return new GroundNumericExpression(exp.getValue());
		case function:
			return new GroundNumericExpression(numericAtom(exp.getFunction(), Float.NaN));
		case negation:
			gExp = new GroundNumericExpression(TermType.negation);
			gExp.add(gExp);
			return gExp;
		case addition:
		case subtraction:
		case multiplication:
		case division:
			gExp = new GroundNumericExpression(exp.getType());
			for (NumericExpression child : exp.getChildren()) {
				gExp.add(toGroundNumExp(child));
			}
			return gExp;
		default:
			error("Invalid numeric expression type \"" 
					+ exp.getType() + "\".");
			return null;
		}
	}
	
	/**
	 * Assemble an Action object out of an operator 
	 * whose arguments are fully replaced by constants.
	 */
	protected Action getAction(Operator liftedAction) {
		
		Action action = null;
		
		if (!config.keepEqualities) {			
			// Resolve all equalities
			liftedAction = resolveEqualities(liftedAction);
			if (liftedAction == null)
				// Inapplicable or inconsistent action
				return null;
		}
		
		// Assemble action name
		String actionName = getActionName(liftedAction, liftedAction.getArguments());
		
		Pair<List<Atom>, Precondition> pre = splitAndGroundPrecondition(liftedAction.getPrecondition());
		Triple<List<Atom>, List<ConditionalEffect>, Effect> eff = splitAndGroundEffect(liftedAction.getEffect());
		action = new Action(actionName, pre.getLeft(), pre.getRight(), eff.getLeft(), eff.getMid(), eff.getRight());
		
		action.setCost(liftedAction.getCost());
		return action;
	}
	
	// Constant conditions
	protected final Condition trueCondition = new Condition(new Predicate("_TRUE"));
	protected final Condition falseCondition = new Condition(new Predicate("_FALSE"));
	
	/**
	 * Given an operator with simplified conditions, resolves all occurring equalities.
	 * May return null if the precondition is simplified to false.
	 */
	private Operator resolveEqualities(Operator op) {
		
		// Copy operator
		Operator newOp = new Operator(op.getName());
		for (Argument arg : op.getArguments())
			newOp.addArgument(arg);
		newOp.setCost(op.getCost());
		
		// Simplify precondition
		AbstractCondition pre = resolveEqualities(op.getPrecondition());
		if (trueCondition.equals(pre)) {
			// Precondition is always true
			newOp.setPrecondition(new ConditionSet(ConditionType.conjunction));
		} else if (falseCondition.equals(pre)) {
			// Operator is never applicable; should be removed
			return null;
		} else {
			newOp.setPrecondition(pre);
		}
		
		// Simplify effect
		AbstractCondition eff = resolveEqualities(op.getEffect());
		if (trueCondition.equals(eff)) {
			// Effect is "empty"
			newOp.setEffect(new ConditionSet(ConditionType.conjunction));
		} else if (falseCondition.equals(eff)) {
			// Effect is inconsistent; remove operator
			return null;
		} else {
			newOp.setEffect(eff);
		}
		
		return newOp;
	}
	
	/**
	 * Given a simplified condition, resolves all occurring equalities
	 * into true or false, and propagates this up to the whole condition.
	 * May return <code>trueCondition</code> or <code>falseCondition</code>
	 * if the condition simplifies to true or false, respectively.
	 */
	protected AbstractCondition resolveEqualities(AbstractCondition abstractCondition) {
		
		return abstractCondition.traverse(cond -> {
			
			if (cond.getConditionType() == ConditionType.atomic) {
				
				Condition atom = (Condition) cond;
				if (isEqualityCondition(atom)) {
					if (holdsEqualityCondition(atom)) {
						return trueCondition;
					} else {
						return falseCondition;
					}
				}
				
			} else if (cond instanceof ConditionSet) {
				
				ConditionSet set = (ConditionSet) cond;
				ConditionSet newSet = new ConditionSet(set.getConditionType());
				for (AbstractCondition c : set.getConditions()) {
					if (set.getConditionType() == ConditionType.conjunction 
							&& falseCondition.equals(c)) {
						// false atom in a conjunction: whole conjunction is false
						return falseCondition;
					} else if (set.getConditionType() == ConditionType.disjunction 
							&& trueCondition.equals(c)) {
						// true atom in a disjunction: whole disjunction is true
						return trueCondition;
					} else if (!trueCondition.equals(c) && !falseCondition.equals(c)) {
						// only add non-constant valued conditions
						newSet.add(c);
					}
				}
				if (newSet.getConditions().isEmpty()) {
					// Empty conjunction is true, empty disjunction is false
					return (set.getConditionType() == ConditionType.conjunction ? 
							trueCondition : falseCondition);
				}
				return newSet;
				
			} else if (cond.getConditionType() == ConditionType.consequential) {
				
				ConsequentialCondition cc = (ConsequentialCondition) cond;
				AbstractCondition pre = cc.getPrerequisite();
				if (trueCondition.equals(pre)) {
					return resolveEqualities(cc.getConsequence());
				} else if (falseCondition.equals(pre)) {
					return trueCondition; // essentially forget about the cond. effect
				}
			}
			
			return cond;
			
		}, AbstractCondition.RECURSE_HEAD);
	}
	
	/**
	 * Checks whether the given condition is an equality condition.
	 */
	private boolean isEqualityCondition(Condition cond) {
		return cond.getPredicate().getName().equals("=");
	}
	
	/**
	 * Given an equality condition with constant arguments, 
	 * checks if the equality holds.
	 */
	private boolean holdsEqualityCondition(Condition cond) {
		
		if (!isEqualityCondition(cond)) {
			error("The provided condition does not represent an equality.");
		}
		
		if (cond.getNumArgs() == 2) {
			if (cond.getArguments().get(0).getName().equals(
					cond.getArguments().get(1).getName())) {
				return !cond.isNegated();
			} else {
				return cond.isNegated();
			}
		}
		return false;
	}

	/**
	 * Compiles all atom names into a flat list, where the ith item
	 * is the name of the atom of ID i, and returns the list.
	 */
	public List<String> extractAtomNames() {
		
		List<String> atomNames = new ArrayList<>();
		for (int i = 0; i < atoms.size(); i++) {
			atomNames.add(this.atomNames.get(i));
		}
		return atomNames;
	}
	public List<String> extractNumericAtomNames() {
		
		List<String> atomNames = new ArrayList<>();
		for (int i = 0; i < numericAtoms.size(); i++) {
			atomNames.add(this.numericAtomNames.get(i));
		}
		return atomNames;
	}
	
	/**
	 * Assembles the logical meaning of all relevant derived atoms
	 * and grounds the respective expressions.
	 */
	protected void groundDerivedAtoms() {
		
		boolean change = true;
		while (change) {
			change = false;
			
			// Retrieve all relevant derived atoms
			List<String> derivedAtomNames = new ArrayList<>();
			derivedAtomNames.addAll(derivedAtoms.keySet());
			
			// For each derived atom
			for (String derivedAtomName : derivedAtomNames) {
				DerivedAtom da = derivedAtoms.get(derivedAtomName);
				
				// Is the inner condition still missing?
				if (da.getCondition() == null) {
					// Simplify and ground inner condition
					AbstractCondition cond = da.getLiftedCondition();
					cond = resolveEqualities(cond);
					da.setCondition(toPrecondition(cond, false));
					
					if (trueCondition.equals(cond)) {
						// TODO Derived predicate simplifies to true;
						// remove it from all places
					} else if (falseCondition.equals(cond)) {
						// TODO Derived predicate simplifies to false;
						// remove it from all places
					}
					
					change = true;
				}
			}
		}
	}
	
	private void error(String msg) {
		System.err.println("An error during grounding occurred.");
		throw new IllegalArgumentException(msg);
	}
}
