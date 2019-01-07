package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class NumericExpression {

	public static final float UNDEFINED = Float.NaN;
	
	public enum TermType {
		constant, function, negation,
		addition, subtraction, multiplication, division; 
	}
	
	private TermType type;
	
	// For constant expressions
	private float value;
	
	// For function expressions
	private Function function;
	
	// For non-atomic expressions
	private List<NumericExpression> children;
	
	public NumericExpression(TermType type) {
		this.type = type;
		this.children = new ArrayList<NumericExpression>();
	}
	
	public NumericExpression(float constantValue) {
		this.type = TermType.constant;
		this.value = constantValue;
	}
	
	public NumericExpression(Function function) {
		this.type = TermType.function;
		this.function = function;
	}
	
	public void add(NumericExpression exp) {
		this.children.add(exp);
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}

	public TermType getType() {
		return type;
	}

	public float getValue() {
		return value;
	}

	public Function getFunction() {
		return function;
	}

	public List<NumericExpression> getChildren() {
		return children;
	}
	
	public NumericExpression getExpressionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		switch (type) {
		case negation:
			NumericExpression neg = new NumericExpression(type);
			neg.add(children.get(0).getExpressionBoundToArguments(refArgs, argValues));
			return neg;
		case function:
			return new NumericExpression(function.getFunctionBoundToArguments(refArgs, argValues));
		case addition:
		case subtraction:
		case multiplication:
		case division:
			NumericExpression exp = new NumericExpression(type);
			for (NumericExpression child : children) {
				exp.add(child.getExpressionBoundToArguments(refArgs, argValues));
			}
			return exp;
		default:
			return copy();
		}
	}
	
	public NumericExpression copy() {
		NumericExpression exp = new NumericExpression(type);
		exp.children = new ArrayList<>();
		exp.children.addAll(children);
		exp.function = function.copy();
		exp.value = value;
		return exp;
	}
	
	public float evaluate(NumericState s) {
		switch (type) {
		case constant:
			return value;
		case function:
			return s.get(function);
		case negation:
			return - children.get(0).evaluate(s);
		case addition:
		case subtraction:
		case multiplication:
		case division:
			float value = children.get(0).value;
			for (int i = 1; i < children.size(); i++) {
				NumericExpression child = children.get(i);
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
		return UNDEFINED;
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
			return function.toString();
		case addition:
			connection = " + "; break;
		case subtraction:
			connection = " - "; break;
		case multiplication:
			connection = " * "; break;
		case division:
			connection = " / "; break;
		}
		String out = "(";
		for (NumericExpression child : children) {
			out += child.toString() + connection;
		}
		return out.substring(0, out.length()-3) + ")";
	}
}
