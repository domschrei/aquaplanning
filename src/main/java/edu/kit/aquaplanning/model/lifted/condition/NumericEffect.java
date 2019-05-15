package edu.kit.aquaplanning.model.lifted.condition;

import java.util.List;
import java.util.function.Predicate;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.NumericExpression;

public class NumericEffect extends AbstractCondition {

	public enum Type {
		assign, scaleUp, scaleDown, increase, decrease;
	}

	private Type type;
	private Function function;
	private NumericExpression expression;

	public NumericEffect(Type type) {
		super(ConditionType.numericEffect);
		this.type = type;
	}

	public NumericEffect(Type type, Function function, NumericExpression exp) {
		super(ConditionType.numericEffect);
		this.type = type;
		this.function = function;
		this.expression = exp;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public void setExpression(NumericExpression expression) {
		this.expression = expression;
	}

	public Function getFunction() {
		return function;
	}

	public NumericExpression getExpression() {
		return expression;
	}

	public Type getType() {
		return type;
	}

	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		return new NumericEffect(type, function.getFunctionBoundToArguments(refArgs, argValues),
				expression.getExpressionBoundToArguments(refArgs, argValues));
	}

	@Override
	public AbstractCondition simplify(boolean negated) {
		return copy();
	}

	@Override
	public AbstractCondition getDNF() {
		return copy();
	}

	@Override
	public AbstractCondition copy() {
		return new NumericEffect(type, function.copy(), expression.copy());
	}

	@Override
	public String toString() {
		String out = function.toString();
		switch (type) {
		case assign:
			out += ":=";
			break;
		case increase:
			out += "+=";
			break;
		case decrease:
			out += "-=";
			break;
		case scaleUp:
			out += "*=";
			break;
		case scaleDown:
			out += "/=";
			break;
		}
		return out + expression;
	}

	@Override
	public AbstractCondition traverse(java.util.function.Function<AbstractCondition, AbstractCondition> map,
			int recurseMode) {
		return map.apply(this);
	}
	
	@Override
	public boolean holds(Predicate<Condition> liftedStateMap) {
		throw new RuntimeException("The evaluation of lifted numeric conditions is not supported yet. "
				+ "Please try evaluating the condition's ground representation instead.");
	}
}
