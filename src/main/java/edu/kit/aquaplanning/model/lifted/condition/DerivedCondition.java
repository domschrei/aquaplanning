package edu.kit.aquaplanning.model.lifted.condition;

import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.DerivedPredicate;

public class DerivedCondition extends Condition {

	private DerivedPredicate predicate;

	public DerivedCondition(DerivedPredicate predicate) {
		super(predicate);
		this.predicate = predicate;
	}

	public DerivedCondition(DerivedPredicate predicate, List<Argument> arguments) {
		super(predicate);
		this.predicate = predicate;
		this.getArguments().addAll(arguments);
	}

	@Override
	public void addArgument(Argument arg) {
		super.addArgument(arg);
		// TODO also update predicate args
	}

	@Override
	public DerivedPredicate getPredicate() {
		return predicate;
	}

	@Override
	public DerivedCondition copy() {
		Condition cond = super.copy();
		cond = new DerivedCondition(predicate.copy(), cond.getArguments());
		cond.setNegated(isNegated());
		return (DerivedCondition) cond;
	}

	@Override
	public Condition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {

		Condition boundCond = super.getConditionBoundToArguments(refArgs, argValues);

		AbstractCondition predCond = predicate.getCondition();
		predCond = predCond.getConditionBoundToArguments(predicate.getArguments(), boundCond.getArguments());
		DerivedCondition c = copy();
		c.getArguments().clear();
		for (Argument arg : boundCond.getArguments()) {
			c.addArgument(arg);
		}
		c.predicate.setCondition(predCond);
		return c;
	}

	@Override
	public AbstractCondition simplify(boolean negated) {

		AbstractCondition predCond = predicate.getCondition();
		predCond = predCond.getConditionBoundToArguments(predicate.getArguments(), getArguments());
		predCond = predCond.simplify(negated);

		DerivedCondition c = copy();
		c.predicate.setCondition(predCond);
		return c;
	}
}
