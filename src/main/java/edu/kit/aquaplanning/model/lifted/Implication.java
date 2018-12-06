package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public class Implication extends AbstractCondition {

	private AbstractCondition ifCondition;
	private AbstractCondition thenCondition;
	
	public Implication() {
		super(ConditionType.implication);
	}
	
	public AbstractCondition getIfCondition() {
		return ifCondition;
	}

	public void setIfCondition(AbstractCondition ifCondition) {
		this.ifCondition = ifCondition;
	}

	public AbstractCondition getThenCondition() {
		return thenCondition;
	}

	public void setThenCondition(AbstractCondition thenCondition) {
		this.thenCondition = thenCondition;
	}

	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		Implication c = new Implication();
		c.setIfCondition(ifCondition.getConditionBoundToArguments(refArgs, argValues));
		c.setThenCondition(thenCondition.getConditionBoundToArguments(refArgs, argValues));
		return c;
	}
	
	@Override
	public AbstractCondition simplify(boolean negated) {
		
		if (negated) {
			// not (A -> B)  ==  not (not A or B)  ==  (A and not B)
			ConditionSet c = new ConditionSet(ConditionType.conjunction);
			c.add(ifCondition.simplify(false));
			c.add(thenCondition.simplify(true));
			return c;
		} else {
			// A -> B  ==  not A or B
			ConditionSet c = new ConditionSet(ConditionType.disjunction);
			c.add(ifCondition.simplify(true));
			c.add(thenCondition.simplify(false));
			return c;
		}
	}
	
	@Override
	public AbstractCondition getDNF() {
		return null;
	}
	
	@Override
	public Implication copy() {
		
		Implication i = new Implication();
		i.setIfCondition(ifCondition.copy());
		i.setThenCondition(thenCondition.copy());
		return i;
	}
}
