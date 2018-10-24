package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public abstract class AbstractCondition {

	public enum ConditionType {
		atomic, consequential, quantification;
	}
	
	private ConditionType conditionType;
	
	public AbstractCondition(ConditionType conditionType) {
		this.conditionType = conditionType;
	}
	
	public ConditionType getConditionType() {
		return conditionType;
	}
	
	public abstract AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues);
}
