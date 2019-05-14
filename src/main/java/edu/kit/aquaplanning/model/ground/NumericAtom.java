package edu.kit.aquaplanning.model.ground;

public class NumericAtom {

	private int id;
	private String name;
	private float value;

	public NumericAtom(int id, String name, float value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public NumericAtom copy() {
		return new NumericAtom(id, name, value);
	}

	@Override
	public String toString() {
		return name;
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
		NumericAtom other = (NumericAtom) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
