package edu.kit.aquaplanning.model.ground;

import edu.kit.aquaplanning.model.lifted.NumericCondition.Comparator;

public class GroundNumericPrecondition {

	private Comparator comparator;
	private GroundNumericExpression expLeft;
	private GroundNumericExpression expRight;
	
	public GroundNumericPrecondition(Comparator comparator, 
			GroundNumericExpression expLeft, GroundNumericExpression expRight) {
		this.comparator = comparator;
		this.expLeft = expLeft;
		this.expRight = expRight;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public GroundNumericExpression getExpLeft() {
		return expLeft;
	}

	public GroundNumericExpression getExpRight() {
		return expRight;
	}
	
	public boolean holds(State s) {
		switch (comparator) {
		case greater:
			return expLeft.evaluate(s) > expRight.evaluate(s);
		case greaterEquals:
			return expLeft.evaluate(s) >= expRight.evaluate(s);
		case lower:
			return expLeft.evaluate(s) < expRight.evaluate(s);
		case lowerEquals:
			return expLeft.evaluate(s) <= expRight.evaluate(s);
		case equals:
			return expLeft.evaluate(s) == expRight.evaluate(s);
		}
		throw new IllegalArgumentException();
	}
	
	public boolean holdsRelaxed(State s) {
		return true; // TODO
	}
}
