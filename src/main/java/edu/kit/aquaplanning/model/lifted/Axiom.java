package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

/**
 * Represents the definition of a derived predicate.
 */
public class Axiom {

	/**
	 * The underlying derived predicate.
	 */
	private Predicate predicate;
	/**
	 * The arguments of the derived predicate to which the inner condition is bound
	 * to.
	 */
	private List<Argument> arguments;
	/**
	 * The inner condition providing the meaning of the axiom.
	 */
	private AbstractCondition condition;

	public Axiom(Predicate predicate) {
		this.predicate = predicate;
		this.arguments = new ArrayList<>();
	}

	public Axiom(Predicate predicate, List<Argument> arguments) {
		this.predicate = predicate;
		this.arguments = arguments;
	}

	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}

	public void setCondition(AbstractCondition condition) {
		this.condition = condition;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public AbstractCondition getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return predicate + "" + arguments;
	}
}
