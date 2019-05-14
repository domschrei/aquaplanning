package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent object types as defined in PDDL.
 */
public class Type {

	private String name;
	private List<String> subtypes;

	public Type(String name) {
		this.setName(name);
		subtypes = new ArrayList<>();
	}

	public void addSubtype(String subtype) {
		subtypes.add(subtype);
	}

	public void addSubtypes(List<String> subtypes) {
		for (String newSubtype : subtypes) {
			if (!this.subtypes.contains(newSubtype)) {
				this.subtypes.add(newSubtype);
			}
		}
	}

	public List<String> getSubtypes() {
		return subtypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		// result = prime * result + ((subtypes == null) ? 0 : subtypes.hashCode());
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
		Type other = (Type) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;

		return true;
	}

	@Override
	public String toString() {
		String out = getName() + "{ ";
		for (String type : subtypes) {
			out += type.toString() + " ";
		}
		out += "}";
		return out;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
