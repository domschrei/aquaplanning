package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.condition.NumericCondition.Comparator;

/**
 * Represents any kind of ground precondition of a certain action. Can be one of
 * the following: - Atomic precondition: a single atom or its negation -
 * Negation: another (possibly complex) precondition in negated form - An
 * operation on a number of preconditions (conjunction / disjunction /
 * implication) - A derived atom ("axiom") - Numeric precondition (i.e. some
 * comparator on two numeric expressions)
 */
public class Precondition {

	public enum PreconditionType {
		atom, negation, conjunction, disjunction, implication, derived, numeric;
	}

	private PreconditionType type;

	// atom
	private Atom atom;
	// derived
	private DerivedAtom derivedAtom;
	// negation, conjunction, disjunction, implication
	private List<Precondition> children;

	// numeric
	private Comparator comparator;
	private GroundNumericExpression expLeft;
	private GroundNumericExpression expRight;

	public Precondition(PreconditionType type) {
		this.type = type;
		this.children = new ArrayList<>();
	}

	/**
	 * Copies the provided precondition into a new object.
	 */
	public Precondition(Precondition other) {
		this.type = other.type;
		this.atom = new Atom(other.atom);
		this.derivedAtom = new DerivedAtom(other.derivedAtom);
		this.children = new ArrayList<Precondition>(other.children);
		this.comparator = other.comparator;
		this.expLeft = new GroundNumericExpression(other.expLeft);
		this.expRight = new GroundNumericExpression(other.expRight);
	}

	public void add(Precondition pre) {
		this.children.add(pre);
	}

	// Setters for type-dependent attributes

	public void setAtom(Atom atom) {
		this.atom = atom;
	}

	public void setDerivedAtom(DerivedAtom derivedAtom) {
		this.derivedAtom = derivedAtom;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}

	public void setExpLeft(GroundNumericExpression expLeft) {
		this.expLeft = expLeft;
	}

	public void setExpRight(GroundNumericExpression expRight) {
		this.expRight = expRight;
	}

	public Precondition getSingleChild() {
		if (children.size() == 1) {
			return children.get(0);
		} else {
			throw new IllegalArgumentException("Children size is not equal to one");
		}
	}

	public List<Precondition> getChildren() {
		return children;
	}

	public Atom getAtom() {
		return atom;
	}

	public PreconditionType getType() {
		return type;
	}

	public boolean isType(PreconditionType type) {
		return this.type == type;
	}

	public boolean holds(State state) {

		switch (type) {
		case atom:
			return state.holds(atom);
		case derived:
			return state.holds(derivedAtom);
		case conjunction:
			for (Precondition pre : children) {
				if (!pre.holds(state)) {
					return false;
				}
			}
			return true;
		case disjunction:
			for (Precondition pre : children) {
				if (pre.holds(state)) {
					return true;
				}
			}
			return false;
		case negation:
			return !getSingleChild().holds(state);
		case implication:
			return !children.get(0).holds(state) || children.get(1).holds(state);
		case numeric:
			switch (comparator) {
			case greater:
				return expLeft.evaluate(state) > expRight.evaluate(state);
			case greaterEquals:
				return expLeft.evaluate(state) >= expRight.evaluate(state);
			case lower:
				return expLeft.evaluate(state) < expRight.evaluate(state);
			case lowerEquals:
				return expLeft.evaluate(state) <= expRight.evaluate(state);
			case equals:
				return Math.abs(expLeft.evaluate(state) - expRight.evaluate(state)) < 0.00001f;
			case notEquals:
				return Math.abs(expLeft.evaluate(state) - expRight.evaluate(state)) >= 0.00001f;
			}
			throw new IllegalArgumentException();
		default:
			throw new IllegalArgumentException("Invalid precondition type \"" + type + "\".");
		}
	}

	public boolean holdsRelaxed(State state) {

		switch (type) {
		case atom:
			return !atom.getValue() || state.holds(atom);
		case derived:
			return state.holds(derivedAtom);
		case negation:
			return true;
		case conjunction:
			for (Precondition pre : children) {
				if (!pre.holdsRelaxed(state)) {
					return false;
				}
			}
			return true;
		case disjunction:
			for (Precondition pre : children) {
				if (pre.holdsRelaxed(state)) {
					return true;
				}
			}
			return false;
		case implication:
			return true;
		case numeric:
			float valueLeft = expLeft.evaluate(state);
			float valueRight = expRight.evaluate(state);
			if (expRight.isEffectivelyConstant()) {
				// (fluent exp) <comparator> (constant exp)
				switch (comparator) {
				case greater:
					// "greater" comparison must hold
					return valueLeft > valueRight;
				case equals:
				case greaterEquals:
					// both fall together as "greaterEquals", must hold
					return valueLeft >= valueRight;
				case lower:
				case lowerEquals:
				case notEquals:
					// relaxed: ignore
					return true;
				}
			} else if (expLeft.isEffectivelyConstant()) {
				// (constant exp) <comparator> (fluent exp)
				switch (comparator) {
				case lower:
					// "lower" comparison must hold
					return valueLeft < valueRight;
				case equals:
				case lowerEquals:
					// both fall together as "lowerEquals", must hold
					return valueLeft <= valueRight;
				case greater:
				case greaterEquals:
				case notEquals:
					// relaxed: ignore
					return true;
				}
			}
			return true; // if both sides are fluent
		default:
			throw new IllegalArgumentException("Invalid precondition type \"" + type + "\".");
		}
	}

	@Override
	public String toString() {

		switch (type) {
		case atom:
			return atom.toString();
		case negation:
			return "¬" + getSingleChild().toString();
		case derived:
			return derivedAtom.getName();
		case conjunction:
		case disjunction:
			String out = "{ " + (type == PreconditionType.conjunction ? "AND" : "OR") + " ";
			for (Precondition pre : children) {
				out += pre.toString() + " ";
			}
			return out + "}";
		case implication:
			return "{ " + children.get(0).toString() + " => " + children.get(1).toString() + " }";
		case numeric:
			return "(" + expLeft + " " + toString(comparator) + " " + expRight + ")";
		default:
			return "error";
		}
	}

	private String toString(Comparator comp) {
		switch (comparator) {
		case greater:
			return ">";
		case greaterEquals:
			return "≥";
		case lower:
			return "<";
		case lowerEquals:
			return "≤";
		case equals:
			return "=";
		case notEquals:
			return "!=";
		}
		return null;
	}
}
