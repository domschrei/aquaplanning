package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

public class State {

	private List<Boolean> atoms;
	
	public State(List<Atom> atomList) {
		
		this.atoms = new ArrayList<>(atomList.size());
		for (Atom atom : atomList) {
			while (atoms.size() <= atom.getId()) {
				atoms.add(false);
			}
			atoms.set(atom.getId(), atom.getValue());
		}
	}
	
	public State(State other) {
		
		atoms = new ArrayList<>();
		atoms.addAll(other.atoms);
	}
	
	public void set(Atom atom) {
		
		while (atoms.size() <= atom.getId()) {
			atoms.add(false);
		}
		atoms.set(atom.getId(), atom.getValue());
	}
	
	public void setAllTrueAtomsFrom(State other) {
		
		for (int i = 0; i < other.atoms.size(); i++) {
			boolean atom = other.atoms.get(i);
			if (atom) {
				while (atoms.size() <= i) {
					atoms.add(false);
				}
				atoms.set(i, true);
			}
		}
	}
	
	public boolean holds(Atom atom) {
		
		if (atoms.size() <= atom.getId())
			return !atom.getValue();
		
		return atoms.get(atom.getId()) == atom.getValue();
	}
	
	public List<Boolean> getAtoms() {
		return atoms;
	}
	
	/**
	 * Returns the amount of _true_ atoms in the state.
	 */
	public int size() {
		int size = 0;
		for (Boolean atom : atoms) {
			if (atom)
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
			if (atoms.get(i) != other.atoms.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (Boolean atom : atoms) {
			result = prime * result + (atom ? 1 : 0);
		}
		return result;
	}
	
	/**
	 * Only outputs raw booleans! 
	 * Use groundPlanningProblem.stateToString(state) instead.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Boolean atom : atoms) {
			builder.append(atom.toString() + " ");
		}
		return builder.toString();
	}
}
