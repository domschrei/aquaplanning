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
}
