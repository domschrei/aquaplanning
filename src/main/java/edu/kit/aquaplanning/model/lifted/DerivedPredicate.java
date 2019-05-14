package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

public class DerivedPredicate extends Predicate {

	private List<Argument> arguments;
	private AbstractCondition condition;

	public DerivedPredicate(String name) {
		super(name);
		this.arguments = new ArrayList<>();
	}

	public void addArgument(Argument arg) {
		arguments.add(arg);
		super.addArgumentType(arg.getType());
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCondition(AbstractCondition condition) {
		this.condition = condition;
	}

	public AbstractCondition getCondition() {
		return condition;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		String out = "";
		out += name + "( ";
		for (Argument arg : arguments) {
			out += arg.getName() + "(" + arg.getType().getName() + ") ";
		}
		out += ") := " + condition.toString();
		return out;
	}

	public DerivedPredicate copy() {
		DerivedPredicate p = new DerivedPredicate(this.name);
		p.condition = this.condition.copy();
		p.arguments.addAll(this.arguments);
		return p;
	}
}
