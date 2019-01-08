package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

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

	protected AtomTable atomTable;
	
	protected PlanningProblem problem;
	protected Configuration config;
	
	protected List<Argument> constants;	
	protected List<Action> actions;
	
	public BaseGrounder(Configuration config) {
		this.config = config;
		this.atomTable = new AtomTable();
	}
	
	/**
	 * Converts the provided precondition (with all constant arguments)
	 * into a pair of a flat atom list and a complex precondition object.
	 * The complex precondition may be null if the condition does not
	 * contain any complex parts.
	 */
	protected Pair<List<Atom>, Precondition> splitAndGroundPrecondition(AbstractCondition cond) {
		
		Pair<ConditionSet, ConditionSet> split = splitCondition(cond);
		ConditionSet simpleSet = split.getLeft();
		ConditionSet complexSet = split.getRight();
		
		List<Atom> atomList = new ArrayList<>();
		Precondition complexPrecondition = new Precondition(PreconditionType.conjunction);
		boolean hasComplexPart = false;
		
		for (AbstractCondition c : simpleSet.getConditions()) {
			if (c.getConditionType() != ConditionType.atomic) {
				throw new IllegalArgumentException("Condition " + c 
						+ " is inside a simple condition set");
			}
			Condition liftedAtom = (Condition) c;
			if (liftedAtom.getPredicate().isDerived()) {
				Precondition derived = new Precondition(PreconditionType.derived);
				derived.setDerivedAtom(atomTable.derivedAtom(liftedAtom.getPredicate(), 
						liftedAtom.getArguments()));
				if (liftedAtom.isNegated()) {
					Precondition negated = new Precondition(PreconditionType.negation);
					negated.add(derived);
					complexPrecondition.add(negated);
				} else {					
					complexPrecondition.add(derived);
				}
				hasComplexPart = true;
			} else {				
				atomList.add(atomTable.atom(liftedAtom.getPredicate(), 
						liftedAtom.getArguments(), liftedAtom.isNegated()));
			}
		}
		
		for (AbstractCondition c : complexSet.getConditions()) {
			complexPrecondition.add(toPrecondition(c, false));
			hasComplexPart = true;
		}
		
		return new Pair<>(atomList, hasComplexPart ? complexPrecondition : null);
	}
	
	/**
	 * Converts the provided effect (with all constant arguments)
	 * into a flat atom list, a list of simple conditional effects,
	 * and a complex effect object.
	 * The complex effect may be null if the condition does not
	 * contain any complex parts.
	 */
	protected Triple<List<Atom>, List<ConditionalEffect>, Effect> splitAndGroundEffect(
			AbstractCondition cond) {
		
		Pair<ConditionSet, ConditionSet> split = splitCondition(cond);
		ConditionSet simpleSet = split.getLeft();
		ConditionSet complexSet = split.getRight();
		
		List<Atom> atomList = new ArrayList<>();
		List<ConditionalEffect> condEffList = new ArrayList<>();
		Effect complexEffect = new Effect(EffectType.conjunction);
		
		for (AbstractCondition c : simpleSet.getConditions()) {
			if (c.getConditionType() != ConditionType.atomic) {
				throw new IllegalArgumentException("Condition " + c + " is not atomic.");
			}
			Condition liftedAtom = (Condition) c;
			atomList.add(atomTable.atom(liftedAtom.getPredicate(), liftedAtom.getArguments(), 
					liftedAtom.isNegated()));
		}
		
		List<AbstractCondition> complexSetList = new ArrayList<>();
		complexSetList.addAll(complexSet.getConditions());
		boolean hasComplexEffect = false;
		while (!complexSetList.isEmpty()) {
			AbstractCondition c = complexSetList.remove(0);
			
			switch (c.getConditionType()) {
			case atomic:
				Condition condition = (Condition) c;
				atomList.add(atomTable.atom(condition.getPredicate(), 
						condition.getArguments(), condition.isNegated()));
				break;
			case numericEffect:
				complexEffect.add(toEffect(c));
				hasComplexEffect = true;
				break;
			case conjunction:
				complexSetList.addAll(((ConditionSet) c).getConditions());
				break;
			case consequential:
				ConsequentialCondition cc = (ConsequentialCondition) c;
				
				// Split prerequisite and consequence into simple and complex part
				Pair<List<Atom>, Precondition> splitPrerequisite = splitAndGroundPrecondition(
						cc.getPrerequisite());
				Triple<List<Atom>, List<ConditionalEffect>, Effect> splitConsequence 
						= splitAndGroundEffect(cc.getConsequence());
				
				Precondition complexPrerequisite = splitPrerequisite.getRight();
				if (complexPrerequisite == null) {
					// Can compile into an "easy" conditional effect
					// (the consequence may never have complex parts)
					List<Atom> simplePrerequisite = splitPrerequisite.getLeft();
					ConditionalEffect eff = new ConditionalEffect(simplePrerequisite, 
							splitConsequence.getLeft());
					condEffList.add(eff);
				} else {
					// Add **the whole** cond. effect to complex effects
					Effect condEff = toEffect(c);
					complexEffect.add(condEff);
					hasComplexEffect = true;
				}	
				break;
			default:
				throw new IllegalArgumentException("Condition " + c 
						+ " (type " + c.getConditionType() 
						+ ") is inside an effect.");				
			}
		}
		
		return new Triple<>(atomList, condEffList, hasComplexEffect ? complexEffect : null);
	}
	
	/**
	 * Splits the given condition (with all constant arguments)
	 * into two sets of conditions whereas the first set only
	 * consists of conjunctive atomic conditions and the second
	 * set contains all other (i.e. more complex) expressions.
	 */
	protected Pair<ConditionSet, ConditionSet> splitCondition(AbstractCondition cond) {
		
		ConditionSet simplePartSet = new ConditionSet(ConditionType.conjunction);
		ConditionSet complexPartSet = new ConditionSet(ConditionType.conjunction);
		
		List<AbstractCondition> condList = new ArrayList<>();
		condList.add(cond);
		while (!condList.isEmpty()) {
			
			cond = condList.remove(0);
			if (cond.getConditionType() == ConditionType.atomic) {
				simplePartSet.add(cond);
				
			} else if (cond.getConditionType() == ConditionType.conjunction) {
				condList.addAll(((ConditionSet) cond).getConditions());
				
			} else {
				complexPartSet.add(cond);
			}
		}
		
		return new Pair<>(simplePartSet, complexPartSet);
	}
	
	/**
	 * Converts a lifted condition into a ground precondition.
	 * Set the 2nd argument, "negated", to "false" for a usual top-level call.
	 */
	protected Precondition toPrecondition(AbstractCondition cond, boolean negated) {
		
		Precondition pre;
		ConditionSet set;
		switch (cond.getConditionType()) {
		case atomic:
			Condition c = (Condition) cond;
			if (c.getPredicate().isDerived()) {
				// Derived condition: Do not ground the derived meaning,
				// but only add a placeholder derived atom
				pre = new Precondition(PreconditionType.derived);
				pre.setDerivedAtom(atomTable.derivedAtom(c.getPredicate(), c.getArguments()));
				if (c.isNegated() != negated) {
					// Add enclosing negation around atom
					Precondition neg = new Precondition(PreconditionType.negation);
					neg.add(pre);
					pre = neg;
				}
			} else {
				pre = new Precondition(PreconditionType.atom);
				pre.setAtom(atomTable.atom(c.getPredicate(), c.getArguments(), 
						c.isNegated() != negated));
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
			throw new IllegalArgumentException("Invalid precondition type: " + cond.getConditionType());
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
			effect.setAtom(atomTable.atom(c.getPredicate(), c.getArguments(), c.isNegated()));
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
				effect.setAtom(atomTable.atom(c.getPredicate(), c.getArguments(), negated));
				return effect;
			} else {
				throw new IllegalArgumentException("Negation inside an effect on a non-atomic level.");
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
			NumericAtom atom = atomTable.numericAtom(numEffect.getFunction(), Float.NaN);
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
			throw new IllegalArgumentException("Invalid effect type \"" 
					+ cond.getConditionType() + "\".");
		}
	}
	
	protected GroundNumericExpression toGroundNumExp(NumericExpression exp) {
		GroundNumericExpression gExp;
		switch (exp.getType()) {
		case constant:
			return new GroundNumericExpression(exp.getValue());
		case function:
			return new GroundNumericExpression(atomTable.numericAtom(exp.getFunction(), Float.NaN));
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
			throw new IllegalArgumentException("Invalid numeric expression type \"" 
					+ exp.getType() + "\".");
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
		String actionName = atomTable.getActionName(liftedAction, liftedAction.getArguments());
		
		// Create ground preconditions and effects,
		// split into simple and complex parts
		Pair<List<Atom>, Precondition> pre = splitAndGroundPrecondition(liftedAction.getPrecondition());
		Triple<List<Atom>, List<ConditionalEffect>, Effect> eff = splitAndGroundEffect(liftedAction.getEffect());
		
		// Assemble action
		action = new Action(actionName, pre.getLeft(), pre.getRight(), eff.getLeft(), eff.getMid(), eff.getRight());
		action.setCost(liftedAction.getCost());		
		return action;
	}
	
	/**
	 * Grounds and returns the initial state.
	 */
	protected State getInitialState() {
		
		List<Atom> initialStateAtoms = new ArrayList<>();
		problem.getInitialState().forEach(cond -> {
			if (cond.getConditionType() == ConditionType.atomic) {
				Condition c = (Condition) cond;
				initialStateAtoms.add(atomTable.atom(c.getPredicate(), 
						c.getArguments(), c.isNegated()));
			}
		});
		
		initialStateAtoms.add(atomTable.atom(trueCondition.getPredicate(), 
				trueCondition.getArguments(), false));
		
		State initialState = new State(initialStateAtoms);
		for (Function f : problem.getInitialFunctionValues().keySet()) {
			NumericAtom atom = atomTable.numericAtom(f, 
					problem.getInitialFunctionValues().get(f));
			initialState.set(atom);
		}
		return initialState;
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
			derivedAtomNames.addAll(atomTable.getDerivedAtoms().keySet());
			
			// For each derived atom
			for (String derivedAtomName : derivedAtomNames) {
				DerivedAtom da = atomTable.getDerivedAtoms().get(derivedAtomName);
				
				// Is the inner condition still missing?
				if (da.getCondition() == null) {
					// Simplify and ground inner condition
					AbstractCondition cond = da.getLiftedCondition();
					cond = resolveEqualities(cond);
					da.setCondition(toPrecondition(cond, false));
					change = true;
				}
			}
		}
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
	private AbstractCondition resolveEqualities(AbstractCondition abstractCondition) {
		
		// Traverse expression tree
		return abstractCondition.traverse(cond -> {
			
			if (cond.getConditionType() == ConditionType.atomic) {
				// Check and replace equality conditions
				Condition atom = (Condition) cond;
				if (isEqualityCondition(atom)) {
					if (holdsEqualityCondition(atom)) {
						return trueCondition;
					} else {
						return falseCondition;
					}
				}
				
			} else if (cond instanceof ConditionSet) {
				// Propagate simplifications upwards
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
				// Simplify conditional effects
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
			throw new IllegalArgumentException("The provided condition does not represent an equality");
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
		return atomTable.extractAtomNames();
	}
	/**
	 * Compiles all numeric atom names into a flat list.
	 */
	public List<String> extractNumericAtomNames() {
		return atomTable.extractNumericAtomNames();
	}
	
	public void setProblem(PlanningProblem problem) {
		this.problem = problem;
		atomTable.setProblem(problem);
	}
}
