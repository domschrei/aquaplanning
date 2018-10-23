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
	
	public void set(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value ? name : "¬" + name;
	}
	
	public int getId() {
		return id;
	}
	
	public Atom copy() {
		return new Atom(id, name, value);
	}
}
