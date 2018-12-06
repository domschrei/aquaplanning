package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public class Negation extends AbstractCondition {

	private AbstractCondition condition;
	
	public Negation() {
		super(ConditionType.negation);
	}

	public void setChildCondition(AbstractCondition cond) {
		this.condition = cond;
	}
	
	public AbstractCondition getChildCondition() {
		return condition;
	}
	
	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		Negation c = new Negation();
		c.setChildCondition(condition.getConditionBoundToArguments(refArgs, argValues));
		return c;
	}
	
	@Override
	public AbstractCondition simplify(boolean negated) {
		
		return condition.simplify(!negated);
	}
	
	@Override
	public AbstractCondition getDNF() {
		boolean negated = true;
		while (condition.getConditionType() == ConditionType.negation) {
			condition = ((Negation) condition).condition;
			negated = !negated;
		}
		Condition c = (Condition) condition.copy();
		c.setNegated(negated != c.isNegated());
		return c;
	}
	
	@Override
	public String toString() {
		return "¬" + condition.toString();
	}
	
	@Override
	public AbstractCondition copy() {
		Negation n = new Negation();
		n.setChildCondition(condition.copy());
		return n;
	}
}
