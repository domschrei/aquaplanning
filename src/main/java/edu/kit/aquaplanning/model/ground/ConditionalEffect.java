package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ConditionalEffect {

	private AtomSet conditionsPos;
	private AtomSet conditionsNeg;
	private AtomSet effectsPos;
	private AtomSet effectsNeg;

	public ConditionalEffect() {

		this.conditionsPos = new AtomSet(new ArrayList<>());
		this.conditionsNeg = new AtomSet(new ArrayList<>());
		this.effectsPos = new AtomSet(new ArrayList<>());
		this.effectsNeg = new AtomSet(new ArrayList<>());
	}

	public ConditionalEffect(List<Atom> conditions, List<Atom> effects) {

		this.conditionsPos = new AtomSet(conditions, true);
		this.conditionsNeg = new AtomSet(conditions, false);
		this.effectsPos = new AtomSet(effects, true);
		this.effectsNeg = new AtomSet(effects, false);
	}

	public AtomSet getConditionsPos() {
		return conditionsPos;
	}

	public AtomSet getConditionsNeg() {
		return conditionsNeg;
	}

	public AtomSet getEffectsPos() {
		return effectsPos;
	}

	public AtomSet getEffectsNeg() {
		return effectsNeg;
	}

	@Override
	public String toString() {

		return toString(atomSet -> atomSet.toString());
	}

	public String toString(Function<AtomSet, String> atomSetToString) {

		String out = "";
		out += "{ ";
		out += atomSetToString.apply(conditionsPos) + " ; NOT " + atomSetToString.apply(conditionsNeg) + " ";
		out += "} => { ";
		out += atomSetToString.apply(effectsPos) + " ; NOT " + atomSetToString.apply(effectsNeg) + " ";
		out += "}";
		return out;
	}
}
