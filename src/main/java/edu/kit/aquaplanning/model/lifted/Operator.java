package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Operator {

	private String name;
	private List<Argument> arguments;
	private List<AbstractCondition> preconditions;
	private List<AbstractCondition> effects;
	private int cost;
	
	public Operator(String name) {
		this.name = name;
		arguments = new ArrayList<>();
		preconditions = new ArrayList<>();
		effects = new ArrayList<>();
		this.cost = 0;
	}
	
	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}
	
	public void addPrecondition(AbstractCondition cond) {
		this.preconditions.add(cond);
	}
	
	public void addEffect(AbstractCondition cond) {
		this.effects.add(cond);
	}
	
	public void addConditionalEffect(ConsequentialCondition cond) {
		this.effects.add(cond);
	}
	
	public void addQuantifiedPrecondition(Quantification q) {
		this.preconditions.add(q);
	}
	
	public void addQuantifiedEffect(Quantification q) {
		this.effects.add(q);
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Argument> getArguments() {
		return arguments;
	}
	
	public List<Type> getArgumentTypes() {
		
		List<Type> argTypes = new ArrayList<>();
		arguments.forEach(arg -> argTypes.add(arg.getType()));
		return argTypes;
	}
	
	public List<AbstractCondition> getPreconditions() {
		return preconditions;
	}
	
	public List<AbstractCondition> getEffects() {
		return effects;
	}
	
	public int getCost() {
		return cost;
	}
	
	/**
	 * Creates a copy of this operator where its arguments have been
	 * assigned the provided list of constants (in that order).
	 */
	public Operator getOperatorWithGroundArguments(List<Argument> args) {
		
		Operator op = new Operator(name);
		args.forEach(arg -> op.addArgument(arg));
		for (AbstractCondition cond : preconditions) {
			AbstractCondition c = cond.getConditionBoundToArguments(arguments, args);
			op.addPrecondition(c);
		}
		for (AbstractCondition cond : effects) {
			AbstractCondition c = cond.getConditionBoundToArguments(arguments, args);
			op.addEffect(c);
		}
		op.cost = cost;
		return op;
	}
	
	@Override
	public String toString() {
		String out = "";
		out += name + "( ";
		for (Argument arg : arguments) {
			out += arg.toString() + " ";
		}
		out += ")";
		if (cost != 0) {			
			out += "[cost:" + cost + "]";
		}
		out += " PRE: { ";
		for (AbstractCondition c : preconditions) {
			out += c + " ";
		}
		out += "}";
		out += " POST: { ";
		for (AbstractCondition c : effects) {
			out += c + " ";
		}
		out += "}";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + cost;
		result = prime * result + ((effects == null) ? 0 : effects.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((preconditions == null) ? 0 : preconditions.hashCode());
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
		Operator other = (Operator) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (cost != other.cost)
			return false;
		if (effects == null) {
			if (other.effects != null)
				return false;
		} else if (!effects.equals(other.effects))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (preconditions == null) {
			if (other.preconditions != null)
				return false;
		} else if (!preconditions.equals(other.preconditions))
			return false;
		return true;
	}
}
