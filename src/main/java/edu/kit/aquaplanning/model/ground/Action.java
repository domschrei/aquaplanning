package edu.kit.aquaplanning.model.ground;

import java.util.List;

public class Action {

	private String name;

	private List<Atom> preconditions;
	private List<Atom> effects;
	private List<ConditionalEffect> conditionalEffects;
	private int cost;
	
	public Action(String name, List<Atom> preconditions, List<Atom> effects, 
			List<ConditionalEffect> conditionalEffects) {
		this.name = name;
		this.preconditions = preconditions;
		this.effects = effects;
		this.conditionalEffects = conditionalEffects;
	}

	public boolean isApplicable(State state) {
		for (Atom precondition : preconditions) {
			if (!state.holds(precondition)) {
				return false;
			}
		}
		return true;
	}
	
	public State apply(State state) {
		
		// Apply basic effects
		State newState = new State(state);
		for (Atom effect : effects) {
			newState.set(effect);
		}
		
		// Apply conditional effects, if applicable
		for (ConditionalEffect condEffect : conditionalEffects) {
			
			// Are all conditions satisfied?
			boolean isActive = true;
			for (Atom condition : condEffect.getConditions()) {
				if (!state.holds(condition)) {
					isActive = false;
					break;
				}
			}
			if (isActive) {
				// -- yes: apply the consequences
				for (Atom consequence : condEffect.getEffects()) {
					newState.set(consequence);
				}
			}
		}
		return newState;
	}
	
	@Override
	public String toString() {
		
		String out = "";
		out += name;
		if (cost != 0) {			
			out += "[cost:" + cost + "]";
		}
		out += " PRE: { ";
		for (Atom a : preconditions) {
			out += a + " ";
		}
		out += "} POST: { ";
		for (Atom a : effects) {
			out += a + " ";
		}
		for (ConditionalEffect eff : conditionalEffects) {
			out += eff + " ";
		}
		out += "}";
		return out;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public double getCost() {
		return cost;
	}
}
