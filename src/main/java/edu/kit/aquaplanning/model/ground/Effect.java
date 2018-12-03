package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

public class Effect {

	public enum EffectType {
		atom, conjunction, condition;
	}
	
	private EffectType type;
	private List<Effect> children;
	private Atom atom;
	private Precondition condition; // only for EffectType.condition.
	
	public Effect(EffectType type) {
		this.type = type;
		this.children = new ArrayList<>();
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
		default:
			return "error";
		}
	}
}
