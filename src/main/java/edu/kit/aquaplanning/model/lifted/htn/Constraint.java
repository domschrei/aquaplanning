package edu.kit.aquaplanning.model.lifted.htn;

import java.util.Arrays;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

public class Constraint {

	public enum ConstraintType {
		before, after, between;
	}

	private ConstraintType type;

	private String tagFirst;
	private String tagSecond;
	private AbstractCondition condition;

	public Constraint(ConstraintType type, String tag) {
		this.type = type;
		this.tagFirst = tag;
	}

	public Constraint(String tagFirst, String tagSecond) {
		this.type = ConstraintType.between;
		this.tagFirst = tagFirst;
		this.tagSecond = tagSecond;
	}

	public void setCondition(AbstractCondition condition) {
		this.condition = condition;
	}

	public ConstraintType getType() {
		return type;
	}

	public AbstractCondition getCondition() {
		return condition;
	}

	public String getSingleTag() {
		if (tagSecond != null)
			throw new IllegalArgumentException("Constraint does have a second tag!");
		return tagFirst;
	}

	public String getFirstTag() {
		return tagFirst;
	}

	public String getSecondTag() {
		return tagSecond;
	}

	public Constraint getConstraintBoundToArguments(List<Argument> refArgs, List<Argument> argVals) {
		Constraint c = new Constraint(type, tagFirst);
		c.tagSecond = tagSecond;
		c.condition = condition.getConditionBoundToArguments(refArgs, argVals);
		return c;
	}

	public void setArgument(Argument refArg, Argument argVal) {
		List<Argument> refArgs = Arrays.asList(refArg);
		List<Argument> argVals = Arrays.asList(argVal);
		condition = condition.getConditionBoundToArguments(refArgs, argVals);
	}

	@Override
	public String toString() {
		if (type == ConstraintType.between) {
			return "(between " + condition + " " + tagFirst + " " + tagSecond + ")";
		} else {
			return "(" + type + " " + condition + " " + tagFirst + ")";
		}
	}

	public Constraint copy() {
		Constraint c = new Constraint(type, tagFirst);
		c.tagSecond = tagSecond;
		c.condition = condition.copy();
		return c;
	}
}
