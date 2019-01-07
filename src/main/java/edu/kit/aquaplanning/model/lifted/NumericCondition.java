package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public class NumericCondition extends AbstractCondition {

	public enum Comparator {
		greater, greaterEquals, lower, lowerEquals, equals;
	}
	
	private Comparator comparator;
	private NumericExpression expLeft;
	private NumericExpression expRight;
	
	public NumericCondition(Comparator comparator) {
		super(ConditionType.numericPrecondition);
		this.comparator = comparator;
	}
	
	public NumericCondition(String comparator) {
		super(ConditionType.numericPrecondition);
		switch (comparator) {
		case ">":
			this.comparator = Comparator.greater; break;
		case ">=":
			this.comparator = Comparator.greaterEquals; break;
		case "<":
			this.comparator = Comparator.lower; break;
		case "<=":
			this.comparator = Comparator.lowerEquals; break;
		case "=":
			this.comparator = Comparator.equals; break;
		}
	}
	
	public void setExpLeft(NumericExpression expLeft) {
		this.expLeft = expLeft;
	}
	
	public void setExpRight(NumericExpression expRight) {
		this.expRight = expRight;
	}
	
	public Comparator getComparator() {
		return comparator;
	}
	
	public NumericExpression getExpLeft() {
		return expLeft;
	}
	
	public NumericExpression getExpRight() {
		return expRight;
	}
	
	@Override
	public String toString() {
		String out = "";
		switch (comparator) {
		case greater:
			out = ">";
			break;
		case greaterEquals:
			out = "≥";
			break;
		case lower:
			out = "<";
			break;
		case lowerEquals:
			out = "≤";
			break;
		case equals:
			out = "=";
			break;
		}
		out = expLeft.toString() + " " + out + " " + expRight.toString();
		return out;
	}
	
	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		NumericCondition copy = copy();
		copy.expLeft = copy.expLeft.getExpressionBoundToArguments(refArgs, argValues);
		copy.expRight = copy.expRight.getExpressionBoundToArguments(refArgs, argValues);
		return copy;
	}

	@Override
	public AbstractCondition simplify(boolean negated) {
		return this.copy();
	}

	@Override
	public AbstractCondition getDNF() {
		return this.copy();
	}

	@Override
	public NumericCondition copy() {
		NumericCondition c = new NumericCondition(comparator);
		c.setExpLeft(expLeft.copy());
		c.setExpRight(expRight.copy());
		return c;
	}
}
