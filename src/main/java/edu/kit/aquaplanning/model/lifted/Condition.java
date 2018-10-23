package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Condition extends AbstractCondition {

	private Predicate predicate;
	private List<Argument> arguments;
	private boolean negated;
	
	public Condition(Predicate predicate) {
		super(ConditionType.atomic);
		this.predicate = predicate;
		this.arguments = new ArrayList<>();
	}
	
	public Condition(Predicate predicate, boolean negated) {
		super(ConditionType.atomic);
		this.predicate = predicate;
		this.negated = negated;
		this.arguments = new ArrayList<>();
	}
	
	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}
		
	public Predicate getPredicate() {
		return predicate;
	}

	public int getNumArgs() {
		return arguments.size();
	}
	
	public List<Argument> getArguments() {
		return arguments;
	}

	public boolean isNegated() {
		return negated;
	}
	
	public Condition withoutNegation() {
		
		Condition c = new Condition(predicate);
		arguments.forEach(arg -> c.addArgument(arg));
		return c;
	}
	
	public Condition copy() {
		
		Condition c = new Condition(predicate);
		arguments.forEach(arg -> c.addArgument(arg));
		c.negated = negated;
		return c;
	}
	
	
	@Override
	public String toString() {
		String out = "";
		if (negated)
			out += "¬";
		out += predicate.getName() + "( ";
		for (Argument arg : arguments) {
			out += arg.getName() + " ";
		}
		out += ")";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
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
		Condition other = (Condition) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (negated != other.negated)
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		return true;
	}
	
	
}
