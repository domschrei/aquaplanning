package edu.kit.aquaplanning.model.ground;

public class Atom {

	private int id;
	private String name;
	private boolean value;

	public Atom(int id, String name, boolean value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	/**
	 * Copies the provided atom into a new object.
	 */
	public Atom(Atom other) {
		this.id = other.id;
		this.name = new String(other.name);
		this.value = other.value;
	}

	public void set(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (!value && name.startsWith("=")) {
			return "≠" + name.substring(1);
		} else {
			return value ? name : "¬" + name;
		}
	}

	public int getId() {
		return id;
	}

	public Atom copy() {
		return new Atom(id, name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atom other = (Atom) obj;
		if (other.id != id || other.value != value)
			return false;
		return true;
	}
}
