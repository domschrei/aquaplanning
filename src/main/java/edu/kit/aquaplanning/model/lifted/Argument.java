package edu.kit.aquaplanning.model.lifted;

/**
 * Atomic object with a name (also serving as an identifier) and a type. If the
 * first character of the name is a question mark, then the argument is a
 * variable and, as such, placeholder for some {@link Constant} of appropriate
 * type.
 */
public class Argument {

	private String name;
	private Type type;

	public Argument(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isConstant() {
		return name.charAt(0) != '?';
	}

	public Argument copy() {
		Argument newArg = new Argument(name, type);
		return newArg;
	}

	@Override
	public String toString() {
		String out = "";
		out += name + " (";
		out += type.getName() + ")";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Argument other = (Argument) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		/*
		 * if (type == null) { if (other.type != null) return false; } else if
		 * (!type.equals(other.type)) return false;
		 */
		return true;
	}
}
