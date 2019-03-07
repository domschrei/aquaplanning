package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

public class Operator {

	private String name;
	private List<Argument> arguments;
	private AbstractCondition precondition;
	private AbstractCondition effect;
	private int cost;
	
	public Operator(String name) {
		this.name = name;
		arguments = new ArrayList<>();
		this.cost = 0;
	}
	
	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}
	
	public void setPrecondition(AbstractCondition cond) {
		this.precondition = cond;
	}
	
	public void setEffect(AbstractCondition cond) {
		this.effect = cond;
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
	
	public AbstractCondition getPrecondition() {
		return precondition;
	}
	
	public AbstractCondition getEffect() {
		return effect;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void removeConstantArguments() {
		arguments.removeIf(arg -> arg.isConstant());
	}
	
	/**
	 * Creates a copy of this operator where its arguments have been
	 * assigned the provided list of constants (in that order).
	 */
	public Operator getOperatorWithGroundArguments(List<Argument> args) {
		
		Operator op = new Operator(name);
		for (int argIdx = 0; argIdx < args.size(); argIdx++) {
			Argument arg = args.get(argIdx);
			if (arg == null) {
				arg = this.getArguments().get(argIdx);
				args.set(argIdx, arg);
			}
			op.addArgument(arg);
		}
		AbstractCondition pre = precondition.getConditionBoundToArguments(arguments, args);
		op.setPrecondition(pre);
		AbstractCondition eff = effect.getConditionBoundToArguments(arguments, args);
		op.setEffect(eff);
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
		out += " PRE: ";
		out += precondition + " ";
		out += " POST: ";
		out += effect;
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		//result = prime * result + cost;
		//result = prime * result + ((effect == null) ? 0 : effect.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((precondition == null) ? 0 : precondition.hashCode());
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
		/*if (cost != other.cost)
			return false;
		if (effect == null) {
			if (other.effect != null)
				return false;
		} else if (!effect.equals(other.effect))
			return false;
		*/if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		/*if (precondition == null) {
			if (other.precondition != null)
				return false;
		} else if (!precondition.equals(other.precondition))
			return false;*/
		return true;
	}
}
