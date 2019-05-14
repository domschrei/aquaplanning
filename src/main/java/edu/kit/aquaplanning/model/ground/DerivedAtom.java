package edu.kit.aquaplanning.model.ground;

import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

public class DerivedAtom {

	private int id;
	private String name;
	private AbstractCondition liftedCondition;
	private Precondition condition;

	public DerivedAtom(int id, String name, AbstractCondition liftedCondition) {
		this.id = id;
		this.name = name;
		this.liftedCondition = liftedCondition;
	}

	/**
	 * Copies the provided derived atom into a new object.
	 */
	public DerivedAtom(DerivedAtom other) {
		this.id = other.id;
		this.name = new String(other.name);
		this.liftedCondition = other.liftedCondition.copy();
		this.condition = new Precondition(other.condition);
	}

	public void setCondition(Precondition condition) {
		this.condition = condition;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Precondition getCondition() {
		return condition;
	}

	public AbstractCondition getLiftedCondition() {
		return liftedCondition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		DerivedAtom other = (DerivedAtom) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}
}
