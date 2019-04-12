package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.datastructures.AtomTable;
import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.DerivedAtom;
import edu.kit.aquaplanning.model.ground.Effect;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundNumericExpression;
import edu.kit.aquaplanning.model.ground.NumericAtom;
import edu.kit.aquaplanning.model.ground.Effect.EffectType;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.NumericExpression;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.util.Pair;
import edu.kit.aquaplanning.util.Triple;
import edu.kit.aquaplanning.model.lifted.NumericExpression.TermType;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.condition.Implication;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.condition.NumericCondition;
import edu.kit.aquaplanning.model.lifted.condition.NumericEffect;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.condition.NumericEffect.Type;

/**
 * Abstract base class which all grounders should inherit from.
 * Provides a basic data structure for grounding and retrieval
 * of atoms, and implements a few utility methods for different
 * tasks during a grounding procedure. 
 */
public abstract class BaseGrounder implements Grounder {

	/**
	 * Maintains all atoms, numeric atoms and derived atoms
	 * which occurred so far during grounding.
	 */
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
				error("A simple set of conditions contains non-atomic condition " + c + ".");
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
				error("A simple set of conditions contains non-atomic condition " + c + ".");
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
	public Precondition toPrecondition(AbstractCondition cond, boolean negated) {
		
		Precondition pre = null;
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
			error("Invalid numeric expression type \"" 
					+ exp.getType() + "\".");
			return null;
		}
	}
	
	/**
	 * Simplifies non-disjunctive preconditions and effects in the operator
	 * with respect to the provided super-state. If this state is the final state 
	 * a planning graph converges to, then all conditions will be removed 
	 * that are constant true in the given problem ("rigid conditions").
	 */
	public Operator simplifyRigidConditions(Operator op, LiftedState liftedState) {

		ConditionSet pre = (ConditionSet) simplifyRigidConditions(op.getPrecondition(), liftedState, "pre");
		ConditionSet eff = (ConditionSet) simplifyRigidConditions(op.getEffect(), liftedState, "eff");
		if (pre == null) {
			Logger.log(Logger.ERROR, "Precondition of " + op.toActionString() + " is trivially false after simplification.");
			System.exit(1);
		}
		if (eff == null) {
			Logger.log(Logger.ERROR, "Effect of " + op.toActionString() + " is trivially false after simplification.");
			System.exit(1);
		}
		if (eff.getConditions().size() > 0) {			
			op.setPrecondition(pre);
			op.setEffect(simplifyRigidConditions(op.getEffect(), liftedState, "eff"));
			return op;
		} else {
			return op; //null;
		}
	}
	
	/**
	 * Simplifies away rigid conditions from the provided condition.
	 * Only operates on conjunctive conditions; disjunctive conditions are
	 * ignored and appended verbatim to the output condition.
	 *  
	 * @return null, if the condition is unsatisfiable in the state;
	 * an empty ConditionSet instance, if the condition is constant true;
	 * a non-empty ConditionSet instance, else
	 */
	public AbstractCondition simplifyRigidConditions(AbstractCondition condition, LiftedState liftedState, String context) { 
		
		ConditionSet resultingConditions = new ConditionSet(ConditionType.conjunction);
		boolean validSimplification = true;
		
		List<AbstractCondition> conditions = new ArrayList<>();
		conditions.add(condition);
		for (int i = 0; i < conditions.size(); i++) {
			AbstractCondition c = conditions.get(i);
			
			if (c.getConditionType() == ConditionType.conjunction) {
				conditions.addAll((((ConditionSet) c).getConditions()));
				
			} else if (c.getConditionType() == ConditionType.atomic) {
				
				Condition cond = (Condition) c;
				if (cond.getPredicate().isDerived()) {
					resultingConditions.add(cond);
					continue;
				}
				
				Condition condNegated = cond.copy();
				condNegated.setNegated(!cond.isNegated());
				if (!liftedState.holds(cond)) {
					// Condition is never satisfiable
					return null;
				}
				if (!liftedState.holds(condNegated)) {
					// Simplify
					continue;
				} else {
					resultingConditions.add(cond);
				}
			
			} else if (c.getConditionType() == ConditionType.consequential) {
				
				// Conditional effect
				ConsequentialCondition condEff = (ConsequentialCondition) c;
				AbstractCondition prerequisite = simplifyRigidConditions(condEff.getPrerequisite(), liftedState, "condeff-pre");
				AbstractCondition consequence = simplifyRigidConditions(condEff.getConsequence(), liftedState, "condeff-cons");
				if (prerequisite == null) {
					// Unsatisfiable prerequisite: the whole cond. effect can be removed
					continue;
				}
				if (((ConditionSet) prerequisite).getConditions().size() == 0) {
					// Constant true prerequisite
					if (consequence == null) {
						Logger.log(Logger.ERROR, "ERROR: Contradictory consequence in a conditional effect.");
						System.exit(1);
					} else if (((ConditionSet) consequence).getConditions().size() == 0) {
						// The consequence is a trivial statement, too
						continue;
					} else {
						// Just add the consequence (instead of the whole cond. effect)
						resultingConditions.add(consequence);
					}
				} else {
					// Non-trivial prerequisite: Add simplified cond. effect
					resultingConditions.add(new ConsequentialCondition(prerequisite, consequence));
				}
				
			} else if (c.getConditionType() == ConditionType.disjunction 
					|| c.getConditionType() == ConditionType.implication 
					|| c.getConditionType() == ConditionType.quantification
					|| c.getConditionType() == ConditionType.negation) {
				// Disjunctive / complex condition structure: No simplification implemented
				Logger.log(Logger.WARN, "Simplification not possible: " + c + " (type " + c.getConditionType() + ")");
				Logger.log(Logger.WARN, "Part of condition " + condition);
				validSimplification = false;
				break;

			} else {
				// Other condition structure such as numeric conditions;
				// append to output conjunction
				resultingConditions.add(c);
			}
		}
		
		if (validSimplification) {
			return resultingConditions;
		} else {
			return condition;
		}
	}
	
	/**
	 * Assemble an Action object out of an operator 
	 * whose arguments are fully replaced by constants.
	 */
	protected Action getAction(Operator liftedAction) {
		
		// Assemble action name
		String actionName = atomTable.getActionName(liftedAction, liftedAction.getArguments());
		
		// Create ground preconditions and effects,
		// split into simple and complex parts
		Pair<List<Atom>, Precondition> pre = splitAndGroundPrecondition(liftedAction.getPrecondition());
		Triple<List<Atom>, List<ConditionalEffect>, Effect> eff = splitAndGroundEffect(liftedAction.getEffect());
		
		// Assemble action
		Action action = new Action(actionName, pre.getLeft(), pre.getRight(), eff.getLeft(), eff.getMid(), eff.getRight());
		action.setCost(liftedAction.getCost());	
		return action;
	}
	
	/**
	 * Grounds and returns the initial state.
	 */
	protected State getInitialState(LiftedState convergedState, boolean reduceAtoms) {
		
		List<Atom> initialStateAtoms = new ArrayList<>();
		problem.getInitialState().forEach(cond -> {
			if (cond.getConditionType() == ConditionType.atomic) {
				
				if (reduceAtoms) {
					
					// Simplify condition (may be removed if always true)
					ConditionSet simplified = (ConditionSet) simplifyRigidConditions(cond, convergedState, "init");
					if (simplified.getConditions().size() > 0) {
						Condition c = (Condition) simplified.getConditions().get(0);
						initialStateAtoms.add(atomTable.atom(c.getPredicate(), 
								c.getArguments(), c.isNegated()));					
					}
					
				} else {
					
					// Do not simplify initial state
					Condition c = (Condition) cond;
					initialStateAtoms.add(atomTable.atom(c.getPredicate(), 
							c.getArguments(), c.isNegated()));
				}
			}
		});
		
		initialStateAtoms.add(atomTable.atom(trueCondition.getPredicate(), 
				trueCondition.getArguments(), false));
		State initialState = new State(initialStateAtoms);
		
		// Define numeric atoms by initial state
		Set<NumericAtom> definedNumericAtoms = new HashSet<>();
		for (Function f : problem.getInitialFunctionValues().keySet()) {			
			NumericAtom atom = atomTable.numericAtom(f, 
					problem.getInitialFunctionValues().get(f));
			initialState.set(atom);
			definedNumericAtoms.add(atom);
		}
		// Any undefined numeric atoms?
		for (NumericAtom atom : atomTable.getNumericAtoms().values()) {			
			if (!definedNumericAtoms.contains(atom)) {
				Logger.log(Logger.WARN, "Fluent " + atom + " has no initial value; defaulting to zero.");
				atom = atom.copy();
				atom.setValue(0);
				initialState.set(atom);
			}
		}
		return initialState;
	}
	
	Goal getGoal(LiftedState finalState, boolean reduceAtoms) {
		
		ConditionSet goalSetUnsimplified = new ConditionSet(ConditionType.conjunction);
		problem.getGoals().forEach(c -> goalSetUnsimplified.add(c));
		ConditionSet goalSet;
		if (reduceAtoms) {
			goalSet = (ConditionSet) simplifyRigidConditions(goalSetUnsimplified, finalState, "goal");
			if (goalSet == null) {
				// TODO unreachable goal; directly return unsatisfiability
				Logger.log(Logger.INFO, "Goal is unreachable according to planning graph analysis.");
			}
		} else {
			goalSet = goalSetUnsimplified;
		}
		Goal goal;
		Pair<List<Atom>, Precondition> splitGoal = splitAndGroundPrecondition(goalSet);
		if (splitGoal.getRight() != null) {
			// Complex goal: add simple AND complex parts
			Precondition complexGoal = splitGoal.getRight();
			splitGoal.getLeft().forEach(atom -> {
				Precondition atomPre = new Precondition(PreconditionType.atom);
				atomPre.setAtom(atom);
				complexGoal.add(atomPre);
			});
			goal = new Goal(complexGoal);
		} else {
			// Simple goal
			goal = new Goal(splitGoal.getLeft());
		}
		return goal;
	}
	
	// Constant conditions
	protected final Condition trueCondition = new Condition(new Predicate("_TRUE"));
	protected final Condition falseCondition = new Condition(new Predicate("_FALSE"));
	
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
	
	/**
	 * Assembles the logical meaning of all relevant derived atoms
	 * and grounds the respective expressions.
	 */
	protected void groundDerivedAtoms(LiftedState finalState, boolean simplifyRigids) {
		
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
					if (simplifyRigids) {
						cond = simplifyRigidConditions(cond, finalState, "derived");						
					}
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
	
	public void setProblem(PlanningProblem problem) {
		this.problem = problem;
		atomTable.setProblem(problem);
	}
	
	public PlanningProblem getProblem() {
		return problem;
	}
	
	public AtomTable getAtomTable() {
		return atomTable;
	}
	
	private void error(String msg) {
		Logger.log(Logger.ERROR, "An error during grounding occurred: " + msg);
		throw new RuntimeException(msg);
	}
}
