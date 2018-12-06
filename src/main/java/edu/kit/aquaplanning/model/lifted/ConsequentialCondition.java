package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public class ConsequentialCondition extends AbstractCondition {

	private AbstractCondition prerequisite;
	private AbstractCondition consequence;
	
	public ConsequentialCondition(AbstractCondition prerequisite, AbstractCondition consequence) {
		super(ConditionType.consequential);
		this.prerequisite = prerequisite;
		this.consequence = consequence;
	}
	
	public ConsequentialCondition() {
		super(ConditionType.consequential);
	}
	
	public void setPrerequisite(AbstractCondition cond) {
		this.prerequisite = cond;
	}
	
	public void setConsequence(AbstractCondition cond) {
		this.consequence = cond;
	}
	
	public AbstractCondition getPrerequisite() {
		return prerequisite;
	}
	
	public AbstractCondition getConsequence() {
		return consequence;
	}
	
	@Override
	public ConsequentialCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		ConsequentialCondition c = new ConsequentialCondition();
		c.setPrerequisite(prerequisite.getConditionBoundToArguments(refArgs, argValues));
		c.setConsequence(consequence.getConditionBoundToArguments(refArgs, argValues));
		return c;
	}
	
	@Override
	public AbstractCondition simplify(boolean negated) {
		
		if (negated) {
			throw new IllegalArgumentException("Negated conditional effect is not legal.");
		}
		ConsequentialCondition c = new ConsequentialCondition();
		c.setPrerequisite(prerequisite.simplify(false));
		c.setConsequence(consequence.simplify(false));
		return c;
	}
	
	@Override
	public AbstractCondition getDNF() {
		
		ConsequentialCondition c = new ConsequentialCondition();
		c.setPrerequisite(prerequisite.getDNF());
		c.setConsequence(consequence);
		return c;
	}
	
	@Override
	public String toString() {
		String out = "";
		out += "{ ";
		out += prerequisite.toString() + " ";
		out += "} => { ";
		out += consequence.toString() + " ";
		out += "}";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consequence == null) ? 0 : consequence.hashCode());
		result = prime * result + ((prerequisite == null) ? 0 : prerequisite.hashCode());
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
		if (consequence == null) {
			if (other.consequence != null)
				return false;
		} else if (!consequence.equals(other.consequence))
			return false;
		if (prerequisite == null) {
			if (other.prerequisite != null)
				return false;
		} else if (!prerequisite.equals(other.prerequisite))
			return false;
		return true;
	}
	
	@Override
	public ConsequentialCondition copy() {
		
		ConsequentialCondition cc = new ConsequentialCondition();
		cc.setPrerequisite(prerequisite.copy());
		cc.setConsequence(consequence.copy());
		return cc;
	}
}
