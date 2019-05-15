package edu.kit.aquaplanning.model.lifted.condition;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import edu.kit.aquaplanning.model.lifted.Argument;

public class Negation extends AbstractCondition {

	private AbstractCondition condition;

	public Negation() {
		super(ConditionType.negation);
	}

	public void setChildCondition(AbstractCondition cond) {
		this.condition = cond;
	}

	public AbstractCondition getChildCondition() {
		return condition;
	}

	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		Negation c = new Negation();
		c.setChildCondition(condition.getConditionBoundToArguments(refArgs, argValues));
		return c;
	}

	@Override
	public AbstractCondition simplify(boolean negated) {

		return condition.simplify(!negated);
	}

	@Override
	public AbstractCondition getDNF() {
		boolean negated = true;
		while (condition.getConditionType() == ConditionType.negation) {
			condition = ((Negation) condition).condition;
			negated = !negated;
		}
		Condition c = (Condition) condition.copy();
		c.setNegated(negated != c.isNegated());
		return c;
	}

	@Override
	public String toString() {
		return "¬" + condition.toString();
	}

	@Override
	public Negation copy() {
		Negation n = new Negation();
		n.setChildCondition(condition.copy());
		return n;
	}

	@Override
	public AbstractCondition traverse(Function<AbstractCondition, AbstractCondition> map, int recurseMode) {

		Negation result;

		// Apply the inner function, if tail recursion is done
		if (recurseMode == AbstractCondition.RECURSE_TAIL) {
			result = (Negation) map.apply(this);
		} else {
			result = copy();
		}

		// Recurse
		result.setChildCondition(result.getChildCondition().traverse(map, recurseMode));

		// Apply the inner function, if head recursion is done
		if (recurseMode == AbstractCondition.RECURSE_HEAD) {
			return map.apply(result);
		}

		return result;
	}
	
	@Override
	public boolean holds(Predicate<Condition> liftedStateMap) {
		return !condition.holds(liftedStateMap);
	}
}
