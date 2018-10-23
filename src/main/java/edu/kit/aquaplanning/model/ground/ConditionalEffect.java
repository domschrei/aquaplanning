package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

public class ConditionalEffect {

	private List<Atom> conditions;
	private List<Atom> effects;

	public ConditionalEffect() {
		this.conditions = new ArrayList<>();
		this.effects = new ArrayList<>();
	}

	public ConditionalEffect(List<Atom> conditions, List<Atom> effects) {
		this.conditions = conditions;
		this.effects = effects;
	}
	
	public List<Atom> getConditions() {
		return conditions;
	}
	
	public List<Atom> getEffects() {
		return effects;
	}
	
	@Override
	public String toString() {
		
		String out = "";
		out += "{ ";
		for (Atom c : conditions) {
			out += c + " ";
		}
		out += "} => { ";
		for (Atom c : effects) {
			out += c + " ";
		}
		out += "}";
		return out;
	}
}
