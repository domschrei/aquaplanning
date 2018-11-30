package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class ConditionSet extends AbstractCondition {

	private List<AbstractCondition> conditions;

	public ConditionSet(ConditionType type) {
		super(type);
		conditionType = type;
		this.conditions = new ArrayList<AbstractCondition>();
	}

	public void add(AbstractCondition c) {
		this.conditions.add(c);
	}
	
	public List<AbstractCondition> getConditions() {
		return conditions;
	}
	
	public boolean isDisjunctive() {
		return getConditionType() == ConditionType.disjunction;
	}
	
	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		ConditionSet c = new ConditionSet(conditionType);
		for (AbstractCondition subCond : conditions) {
			c.add(subCond.getConditionBoundToArguments(refArgs, argValues));
		}
		return c;
	}
	
	@Override
	public String toString() {
		String out = isDisjunctive() ? "OR { " : "AND { ";
		for (AbstractCondition c : conditions) {
			out += c.toString() + " ";
		}
		return out + "}";
	}
}
