package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents any kind of ground effect of a certain action. Can be one of the
 * following: - an atomic effect (a single atom is added or deleted) - a numeric
 * effect (a numeric atom (ground function) is updated) - a conjunction of
 * multiple effects - a conditional effect (a list of effects is applied if a
 * certain precondition holds)
 */
public class Effect {

	public enum EffectType {
		atom, conjunction, condition, numeric;
	}

	private EffectType type;
	private List<Effect> children;
	private Atom atom; // only for EffectType.atom
	private Precondition condition; // only for EffectType.condition

	// only for EffectType.numeric
	private NumericAtom function;
	private GroundNumericExpression expression;

	public Effect(EffectType type) {
		this.type = type;
		this.children = new ArrayList<>();
	}

	public Effect(Atom atom) {
		this.type = EffectType.atom;
		this.children = new ArrayList<>();
		this.atom = atom;
	}

	public void add(Effect effect) {
		children.add(effect);
	}

	public void setCondition(Precondition condition) {
		this.condition = condition;
	}

	public void setAtom(Atom atom) {
		this.atom = atom;
	}

	public void setFunction(NumericAtom function) {
		this.function = function;
	}

	public void setExpression(GroundNumericExpression expression) {
		this.expression = expression;
	}

	public Effect getSingleChild() {
		if (children.size() == 1) {
			return children.get(0);
		} else {
			throw new IllegalArgumentException("Children size is not equal to one");
		}
	}

	public List<Effect> getChildren() {
		return children;
	}

	public Precondition getCondition() {
		return condition;
	}

	public EffectType getType() {
		return type;
	}

	public boolean isType(EffectType type) {
		return this.type == type;
	}

	public Atom getAtom() {
		return atom;
	}

	public State applyTo(State state) {

		State newState = new State(state);
		apply(state, newState);
		return newState;
	}

	public State applyRelaxedTo(State state) {

		State newState = new State(state);
		applyRelaxed(state, newState);
		return newState;
	}

	private void apply(State oldState, State newState) {

		switch (type) {
		case atom:
			newState.set(atom);
			break;
		case condition:
			if (condition.holds(oldState)) {
				getSingleChild().apply(oldState, newState);
			}
			break;
		case conjunction:
			for (Effect effect : children) {
				effect.apply(oldState, newState);
			}
			break;
		case numeric:
			function.setValue(expression.evaluate(oldState));
			newState.set(function);
			break;
		}
	}

	private void applyRelaxed(State oldState, State newState) {

		switch (type) {
		case atom:
			if (atom.getValue())
				newState.set(atom);
			break;
		case condition:
			if (condition.holdsRelaxed(oldState)) {
				getSingleChild().applyRelaxed(oldState, newState);
			}
			break;
		case conjunction:
			for (Effect effect : children) {
				effect.applyRelaxed(oldState, newState);
			}
			break;
		case numeric:
			// TODO Delete-relaxation extended to numeric effects
			float result = expression.evaluate(oldState);
			if (result > oldState.get(function)) {
				function.setValue(result);
				newState.set(function);
			}
			break;
		}
	}

	@Override
	public String toString() {
		String out;
		switch (type) {
		case atom:
			return atom.toString();
		case condition:
			out = "{ ";
			out += condition.toString() + " => ";
			out += getSingleChild().toString() + " }";
			return out;
		case conjunction:
			out = "{ AND ";
			for (Effect e : children) {
				out += e.toString() + " ";
			}
			return out + "}";
		case numeric:
			return function + ":=" + expression;
		default:
			return "error";
		}
	}
}
