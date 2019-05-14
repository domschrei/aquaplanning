package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Represents an action of a ground planning problem with certain preconditions
 * and effects.
 */
public class Action {

	private String name;
	private int cost;

	// Properties of a simple (purely conjunctive) action;
	// For STRIPS planning, only these preconditions and effects
	// need to be considered.
	private AtomSet preconditionsPos;
	private AtomSet preconditionsNeg;
	private AtomSet effectsPos;
	private AtomSet effectsNeg;
	private List<ConditionalEffect> conditionalEffects;

	// Properties of a complex (disjunctive) action,
	// containing all logic which cannot be expressed with bitsets.
	// Can be zero if the action has no complex parts.
	private Precondition complexPrecondition;
	private Effect complexEffect;

	/**
	 * Creates a simple action with the provided properties.
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
	 * Creates a simple action with the provided properties.
	 */
	public Action(String name, AtomSet preconditionsPos, AtomSet preconditionsNeg, AtomSet effectsPos,
			AtomSet effectsNeg, List<ConditionalEffect> conditionalEffects) {
		this.name = name;
		this.preconditionsPos = preconditionsPos;
		this.preconditionsNeg = preconditionsNeg;
		this.effectsPos = effectsPos;
		this.effectsNeg = effectsNeg;
		this.conditionalEffects = conditionalEffects;
	}

	/**
	 * Creates a "purely" complex action with the provided properties.
	 */
	public Action(String name, Precondition complexPrecondition, Effect complexEffect) {
		this.name = name;
		this.complexPrecondition = complexPrecondition;
		this.complexEffect = complexEffect;
		// Data structures for simple preconditions / effects remain empty
		this.preconditionsPos = new AtomSet(Arrays.asList(), true);
		this.preconditionsNeg = new AtomSet(Arrays.asList(), false);
		this.effectsPos = new AtomSet(Arrays.asList(), true);
		this.effectsNeg = new AtomSet(Arrays.asList(), false);
		this.conditionalEffects = new ArrayList<>();
	}

	/**
	 * Creates a "hybrid" action which may feature simple bitset-style preconditions
	 * and effects as well as more complex logical expressions.
	 */
	public Action(String name, List<Atom> simplePre, Precondition complexPre, List<Atom> simpleEff,
			List<ConditionalEffect> condEff, Effect complexEff) {
		this.name = name;
		this.preconditionsPos = new AtomSet(simplePre, true);
		this.preconditionsNeg = new AtomSet(simplePre, false);
		this.complexPrecondition = complexPre;
		this.effectsPos = new AtomSet(simpleEff, true);
		this.effectsNeg = new AtomSet(simpleEff, false);
		this.conditionalEffects = condEff;
		this.complexEffect = complexEff;
	}

	/**
	 * True iff this action is applicable in the provided state.
	 */
	public boolean isApplicable(State state) {

		// Check complex precondition, if present
		if (complexPrecondition != null && !complexPrecondition.holds(state)) {
			return false;
		}
		// Check bitset preconditions
		if (!state.holdsAll(preconditionsPos))
			return false;
		if (!state.holdsNone(preconditionsNeg))
			return false;

		return true;
	}

	/**
	 * True iff this action is applicable in the provided state in a delete-relaxed
	 * sense.
	 */
	public boolean isApplicableRelaxed(State state) {

		// Check complex precondition, if present
		if (complexPrecondition != null && !complexPrecondition.holdsRelaxed(state))
			return false;
		// Check bitset preconditions
		if (!state.holdsAll(preconditionsPos))
			return false;
		return true;
	}

	/**
	 * Returns the result of applying this action to the provided state. Attention:
	 * This method does not check whether the action is applicable in this state!
	 * Check this beforehand with isApplicable(state).
	 */
	public State apply(State state) {

		// Apply effects
		State newState = new State(state);
		// Bitset effects
		newState.removeAll(effectsNeg);
		newState.addAll(effectsPos);
		if (complexEffect != null) {
			// Complex effect
			newState = complexEffect.applyTo(state);
		}

		// Apply (simple) conditional effects, if applicable
		for (ConditionalEffect condEffect : conditionalEffects) {

			// Are all conditions satisfied?
			boolean isActive = state.holdsAll(condEffect.getConditionsPos());
			isActive &= state.holdsNone(condEffect.getConditionsNeg());

			if (isActive) {
				// -- yes: apply the consequences
				newState.removeAll(condEffect.getEffectsNeg());
				newState.addAll(condEffect.getEffectsPos());
			}
		}

		return newState;
	}

	/**
	 * Returns the result of applying this action to the provided state, in a
	 * delete-relaxed sense. Attention: This method does not check whether the
	 * action is applicable in this state! Check this beforehand with
	 * isApplicableRelaxed(state).
	 */
	public State applyRelaxed(State state) {

		// Apply positive effects
		State newState = new State(state);
		// Bitset effects
		newState.addAll(effectsPos);
		if (complexEffect != null) {
			// Complex effect
			newState = complexEffect.applyRelaxedTo(state);
		}

		// Apply (simple) positive conditional effects, if applicable
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
		out += " PRE: { " + (complexPrecondition == null ? "" : complexPrecondition.toString()) + " ";
		if (preconditionsPos.numAtoms() > 0) {
			out += atomSetToString.apply(preconditionsPos) + " ";
		}
		if (preconditionsPos.numAtoms() > 0 && preconditionsNeg.numAtoms() > 0) {
			out += "; ";
		}
		if (preconditionsNeg.numAtoms() > 0) {
			out += "NOT " + atomSetToString.apply(preconditionsNeg) + " ";
		}
		out += "}";
		out += " POST: { " + (complexEffect == null ? "" : complexEffect.toString()) + " ";
		if (effectsPos.numAtoms() > 0) {
			out += atomSetToString.apply(effectsPos) + " ";
		}
		if (effectsPos.numAtoms() > 0 && effectsNeg.numAtoms() > 0) {
			out += "; ";
		}
		if (effectsNeg.numAtoms() > 0) {
			out += "NOT " + atomSetToString.apply(effectsNeg) + " ";
		}
		out += "}";
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

	public String getCleanedName() {
		return getName().replaceAll("\\$.*\\$", "").replaceAll("\\*.*\\*", "");
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

	public AtomSet getPreconditionsPos() {
		return preconditionsPos;
	}

	public AtomSet getPreconditionsNeg() {
		return preconditionsNeg;
	}

	public AtomSet getEffectsPos() {
		return effectsPos;
	}

	public AtomSet getEffectsNeg() {
		return effectsNeg;
	}

	public Precondition getComplexPrecondition() {
		return complexPrecondition;
	}

	public Effect getComplexEffect() {
		return complexEffect;
	}
}
