package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

public class State {

	private List<Atom> atoms;
	
	public State(List<Atom> atomList) {
		
		this.atoms = new ArrayList<>();
		for (Atom atom : atomList) {
			while (atoms.size() <= atom.getId()) {
				atoms.add(new Atom(atoms.size(), "[auto]", false));
			}
			atoms.set(atom.getId(), atom);
		}
	}
		
	public State(State other) {
		
		atoms = new ArrayList<>();
		
		for (Atom atom : other.atoms) {
			atoms.add(atom.getId(), atom.copy());
		}
	}
	
	public void set(Atom atom) {
		
		while (atoms.size() <= atom.getId()) {
			atoms.add(new Atom(atoms.size(), "[auto]", false));
		}
		atoms.set(atom.getId(), atom.copy());
	}
	
	public boolean holds(Atom atom) {
		
		if (atoms.size() <= atom.getId())
			return !atom.getValue();
		
		return atoms.get(atom.getId()).getValue() == atom.getValue();
	}
	
	public List<Atom> getAtoms() {
		return atoms;
	}
	
	/**
	 * Returns the amount of _true_ atoms in the state.
	 */
	public int size() {
		int size = 0;
		for (Atom atom : atoms) {
			if (atom.getValue())
				size++;
		}
		return size;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		State other = (State) obj;
		if (other.atoms.size() != atoms.size())
			return false;
		for (int i = 0; i < atoms.size(); i++) {
			if (atoms.get(i).getValue() != other.atoms.get(i).getValue()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (Atom atom : atoms) {
			result = prime * result + (atom.getValue() ? 1 : 0);
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Atom atom : atoms) {
			if (atom.getValue()) {
				builder.append(atom.toString() + " ");
			}
		}
		return builder.toString();
	}
}
