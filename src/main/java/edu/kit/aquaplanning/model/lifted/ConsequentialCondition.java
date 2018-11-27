package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class ConsequentialCondition extends AbstractCondition {

	private List<AbstractCondition> prerequisites;
	private List<AbstractCondition> consequences;
	
	public ConsequentialCondition(List<AbstractCondition> prerequisites, List<AbstractCondition> consequences) {
		super(ConditionType.consequential);
		this.prerequisites = prerequisites;
		this.consequences = consequences;
	}
	
	public ConsequentialCondition() {
		super(ConditionType.consequential);
		this.prerequisites = new ArrayList<>();
		this.consequences = new ArrayList<>();
	}
	
	public void addPrerequisite(AbstractCondition cond) {
		this.prerequisites.add(cond);
	}
	
	public void addConsequence(AbstractCondition cond) {
		this.consequences.add(cond);
	}
	
	public List<AbstractCondition> getPrerequisites() {
		return prerequisites;
	}
	
	public List<AbstractCondition> getConsequences() {
		return consequences;
	}
	
	@Override
	public ConsequentialCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		ConsequentialCondition c = new ConsequentialCondition();
		for (AbstractCondition cond : prerequisites) {
			c.addPrerequisite(cond.getConditionBoundToArguments(refArgs, argValues));
		}
		for (AbstractCondition cond : consequences) {
			c.addConsequence(cond.getConditionBoundToArguments(refArgs, argValues));
		}
		return c;
	}
	
	@Override
	public String toString() {
		String out = "";
		out += "{ ";
		for (AbstractCondition c : prerequisites) {
			out += c + " ";
		}
		out += "} => { ";
		for (AbstractCondition c : consequences) {
			out += c + " ";
		}
		out += "}";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consequences == null) ? 0 : consequences.hashCode());
		result = prime * result + ((prerequisites == null) ? 0 : prerequisites.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsequentialCondition other = (ConsequentialCondition) obj;
		if (consequences == null) {
			if (other.consequences != null)
				return false;
		} else if (!consequences.equals(other.consequences))
			return false;
		if (prerequisites == null) {
			if (other.prerequisites != null)
				return false;
		} else if (!prerequisites.equals(other.prerequisites))
			return false;
		return true;
	}
}
