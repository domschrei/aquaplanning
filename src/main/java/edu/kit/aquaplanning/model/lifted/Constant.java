package edu.kit.aquaplanning.model.lifted;

/**
 * Atomic object which only has a name (also serving as an identifier)
 * and a type. Special case of an {@link Argument}, which can also be a variable.
 */
public class Constant {

	private String name;
	private Type type;
	
	public Constant(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public Constant(Argument arg) {
		this.name = arg.getName();
		this.type = arg.getType();
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return name + " (" + type.getName() + ")";
	}
}
