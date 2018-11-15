package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class State {

	private BitSet atoms;
	
	public State(List<Atom> atomList) {
		
		this.atoms = new BitSet(atomList.size());
		for (Atom atom : atomList) {
			atoms.set(atom.getId(), atom.getValue());
		}
	}
	
	public State(State other) {
		
		atoms = (BitSet) other.atoms.clone();
	}
	
	public void set(Atom atom) {
		
		atoms.set(atom.getId(), atom.getValue());
	}
	
	public void setAllTrueAtomsFrom(State other) {
		
		atoms.or(other.atoms);
	}
	
	public boolean holds(Atom atom) {
		
		if (atoms.size() <= atom.getId())
			return !atom.getValue();
		
		return atoms.get(atom.getId()) == atom.getValue();
	}
	
	public List<Boolean> getAtoms() {
		
		List<Boolean> atoms = new ArrayList<>(this.atoms.size());
		for (int i = 0; i < this.atoms.size(); i++) {
			atoms.add(this.atoms.get(i));
		}
		return atoms;
	}
	
	/**
	 * Returns the amount of _true_ atoms in the state.
	 */
	public int size() {
		return atoms.cardinality();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		State other = (State) obj;
		if (!other.atoms.equals(atoms))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (int i = 0; i < atoms.size(); i++) {
			boolean atom = atoms.get(i);
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
		for (int i = 0; i < atoms.size(); i++) {
			boolean atom = atoms.get(i);
			builder.append((atom ? "1" : "0") + " ");
		}
		return builder.toString();
	}
}
