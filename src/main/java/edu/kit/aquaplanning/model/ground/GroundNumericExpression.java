package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.NumericExpression;
import edu.kit.aquaplanning.model.lifted.NumericExpression.TermType;

public class GroundNumericExpression {

	private TermType type;

	private float value;
	private NumericAtom atom;
	private List<GroundNumericExpression> children;

	public GroundNumericExpression(TermType type) {
		this.type = type;
		this.children = new ArrayList<>();
	}

	public GroundNumericExpression(float value) {
		this.type = TermType.constant;
		this.value = value;
	}

	public GroundNumericExpression(NumericAtom atom) {
		this.type = TermType.function;
		this.atom = atom;
	}

	/**
	 * Copies the provided ground numeric expression into a new object.
	 */
	public GroundNumericExpression(GroundNumericExpression other) {
		this.type = other.type;
		this.value = other.value;
		this.atom = other.atom.copy();
		this.children = new ArrayList<GroundNumericExpression>(other.children);
	}

	public void add(GroundNumericExpression exp) {
		this.children.add(exp);
	}

	public float evaluate(State s) {
		switch (type) {
		case constant:
			return value;
		case function:
			return s.get(atom);
		case negation:
			return -children.get(0).evaluate(s);
		case addition:
		case subtraction:
		case multiplication:
		case division:
			float value = children.get(0).evaluate(s);
			for (int i = 1; i < children.size(); i++) {
				GroundNumericExpression child = children.get(i);
				if (type == TermType.addition) {
					value += child.evaluate(s);
				} else if (type == TermType.subtraction) {
					value -= child.evaluate(s);
				} else if (type == TermType.multiplication) {
					value *= child.evaluate(s);
				} else if (type == TermType.division) {
					value /= child.evaluate(s);
				}
			}
			return value;
		}
		return NumericExpression.UNDEFINED;
	}

	/**
	 * Determines whether the expression only consists of constant values (i.e. no
	 * numeric atoms are contained).
	 */
	public boolean isEffectivelyConstant() {
		switch (type) {
		case constant:
			return true;
		case function:
			return false;
		default:
			boolean isConstant = true;
			for (GroundNumericExpression child : children) {
				isConstant &= child.isEffectivelyConstant();
			}
			return isConstant;
		}
	}

	@Override
	public String toString() {
		String connection = "";
		switch (type) {
		case constant:
			return value + "";
		case negation:
			return "(-" + children.get(0).toString() + ")";
		case function:
			return atom.toString();
		case addition:
			connection = " + ";
			break;
		case subtraction:
			connection = " - ";
			break;
		case multiplication:
			connection = " * ";
			break;
		case division:
			connection = " / ";
			break;
		}
		String out = "(";
		for (GroundNumericExpression child : children) {
			out += child.toString() + connection;
		}
		return out.substring(0, out.length() - 3) + ")";
	}

	public TermType getType() {
		return type;
	}
}
