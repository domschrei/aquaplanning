package edu.kit.aquaplanning.model.ground;

import java.util.List;
import java.util.function.Function;

/**
 * Represents an action of a ground planning problem with certain
 * preconditions and effects.
 */
public class Action {

	private String name;

	private AtomSet preconditionsPos;
	private AtomSet preconditionsNeg;
	private AtomSet effectsPos;
	private AtomSet effectsNeg;
	private List<ConditionalEffect> conditionalEffects;
	private int cost;
	
	/**
	 * Creates an action with the provided properties.
	 */
	public Action(String name, List<Atom> preconditions, List<Atom> effects, 
			List<ConditionalEffect> conditionalEffects) {
		
		this.name = name;
		this.preconditionsPos = new AtomSet(preconditions, true);
		this.preconditionsNeg = new AtomSet(preconditions, false);
		this.effectsPos = new AtomSet(effects, true);
		this.effectsNeg = new AtomSet(effects, false);
		this.conditionalEffects = conditionalEffects;
	}

	/**
	 * True iff this action is applicable in the provided state.
	 */
	public boolean isApplicable(State state) {
		
		if (!state.holdsAll(preconditionsPos))
			return false;
		if (!state.holdsNone(preconditionsNeg))
			return false;
		
		return true;
	}
	
	/**
	 * True iff this action is applicable in the provided state
	 * in a delete-relaxed sense.
	 */
	public boolean isApplicableRelaxed(State state) {
		
		if (!state.holdsAll(preconditionsPos))
			return false;
		return true;
	}
	
	/**
	 * Returns the result of applying this action to the provided
	 * state. Attention: This method does not check whether the
	 * action is applicable in this state! Check this beforehand with
	 * isApplicable(state).
	 */
	public State apply(State state) {
		
		// Apply basic effects
		State newState = new State(state);
		newState.addAll(effectsPos);
		newState.removeAll(effectsNeg);

		// Apply conditional effects, if applicable
		for (ConditionalEffect condEffect : conditionalEffects) {
			
			// Are all conditions satisfied?
			boolean isActive = state.holdsAll(condEffect.getConditionsPos());
			isActive &= state.holdsNone(condEffect.getConditionsNeg());

			if (isActive) {
				// -- yes: apply the consequences
				newState.addAll(condEffect.getEffectsPos());
				newState.removeAll(condEffect.getEffectsNeg());
			}
		}
		
		return newState;
	}
	
	/**
	 * Returns the result of applying this action to the provided
	 * state, in a delete-relaxed sense. 
	 * Attention: This method does not check whether the action is 
	 * applicable in this state! Check this beforehand with
	 * isApplicableRelaxed(state).
	 */
	public State applyRelaxed(State state) {
		
		// Apply basic positive effects
		State newState = new State(state);
		newState.addAll(effectsPos);
		
		// Apply positive conditional effects, if applicable
		for (ConditionalEffect condEffect : conditionalEffects) {
			
			// Are all POSITIVE conditions satisfied?
			boolean isActive = state.holdsAll(condEffect.getConditionsPos());

			if (isActive) {
				// -- yes: apply the POSITIVE consequences
				newState.addAll(condEffect.getEffectsPos());
			}
		}
				
		return newState;
	}
	
	@Override
	public String toString() {
		
		return toString((atomSet -> atomSet.toString()));
	}
	
	public String toString(Function<AtomSet, String> atomSetToString) {
		
		String out = "";
		out += name;
		if (cost != 0) {			
			out += "[cost:" + cost + "]";
		}
		out += " PRE: { " + atomSetToString.apply(preconditionsPos) 
						+ " ; NOT " + atomSetToString.apply(preconditionsNeg);
		out += "} POST: { " + atomSetToString.apply(effectsPos) 
						+ " ; NOT " + atomSetToString.apply(effectsNeg) + " ; ";
		for (ConditionalEffect eff : conditionalEffects) {
			out += eff.toString(atomSetToString) + " ";
		}
		out += "}";
		return out;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Action other = (Action) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

  public AtomSet getPreconditionsPos() {
    return preconditionsPos;
  }

  public AtomSet getEffectsPos() {
    return effectsPos;
  }

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}

	public List<ConditionalEffect> getConditionalEffects() {
		return conditionalEffects;
	}
}
