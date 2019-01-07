package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.NumericCondition.Comparator;

public class Precondition {

	public enum PreconditionType {
		atom, negation, conjunction, disjunction, implication, derived, numeric;
	}
	
	private PreconditionType type;
	private List<Precondition> children;
	private Atom atom;
	private DerivedAtom derivedAtom;
	
	private Comparator comparator;
	private GroundNumericExpression expLeft;
	private GroundNumericExpression expRight;
	
	public Precondition(PreconditionType type) {
		this.type = type;
		this.children = new ArrayList<>();
	}
	
	public void add(Precondition pre) {
		this.children.add(pre);
	}
	
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
				return Math.abs(expLeft.evaluate(state) - expRight.evaluate(state)) < 0.000001;
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
			return true; // TODO better abstraction
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
			return expLeft + " " + comparator + " " + expRight;
		default:
			return "error";
		}
	}
}
