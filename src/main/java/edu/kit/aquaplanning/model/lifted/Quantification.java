package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Quantification extends AbstractCondition {

	public enum Quantifier {
		universal, existential;
	}
	private Quantifier quantifier;
	private List<Argument> variables;
	private AbstractCondition condition;
	
	public Quantification(Quantifier q) {

		super(ConditionType.quantification);
		this.quantifier = q;
		this.variables = new ArrayList<>();
	}
	
	public void addVariable(Argument arg) {
		variables.add(arg);
	}
	
	public void setCondition(AbstractCondition condition) {
		this.condition = condition;
	}
	
	public Quantifier getQuantifier() {
		return quantifier;
	}
	
	public List<Argument> getVariables() {
		return variables;
	}
	
	public AbstractCondition getCondition() {
		return condition;
	}
	
	@Override
	public Quantification getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		Quantification q = new Quantification(quantifier);
		q.setCondition(condition.getConditionBoundToArguments(refArgs, argValues));
		for (Argument arg : variables) {
			q.addVariable(arg);
		}
		return q;
	}
	
	@Override
	public String toString() {
		
		String out = "";
		for (Argument var : variables) {
			out += (quantifier == Quantifier.existential ? "∃" : "∀") + var + " ";
		}
		out += ": { ";
		out += condition + " ";
		out += "}";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((quantifier == null) ? 0 : quantifier.hashCode());
		result = prime * result + ((variables == null) ? 0 : variables.hashCode());
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
		Quantification other = (Quantification) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (quantifier != other.quantifier)
			return false;
		if (variables == null) {
			if (other.variables != null)
				return false;
		} else if (!variables.equals(other.variables))
			return false;
		return true;
	}
}
