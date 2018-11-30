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
}
