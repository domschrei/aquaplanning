package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a world state as a set of atoms which are currently true.
 */
public class State {

	/**
	 * Internal AtomSet of all true atoms.
	 */
	private AtomSet atoms;
	
	/**
	 * Creates a state containing exactly all TRUE atoms in the provided list.
	 */
	public State(List<Atom> atomList) {
		
		this.atoms = new AtomSet(atomList);
	}
	
	/**
	 * Copies the provided state into a new object.
	 */
	public State(State other) {
		
		atoms = (AtomSet) other.atoms.clone();
	}
	
	/**
	 * Sets the provided atom. If the atom has negative value,
	 * it is removed from the state; else, it is added to the state.
	 */
	public void set(Atom atom) {
		
		atoms.set(atom);
	}
	
	/**
	 * Extends this state by all atoms contained in the provided
	 * other state.
	 */
	public void addAllTrueAtomsFrom(State other) {
		
		atoms.applyTrueAtoms(other.atoms);
	}
	
	/**
	 * True, if the given atom holds in this state (i.e.
	 * (the atom is true AND the atom is in the state) OR
	 * (the atom is false AND the atom is not in the state)).
	 */
	public boolean holds(Atom atom) {
		
		return atoms.get(atom);
	}
	
	/**
	 * True, if all atoms in the provided AtomSet are contained
	 * in the state.
	 */
	public boolean holdsAll(AtomSet atoms) {
		
		return this.atoms.all(atoms);
	}
	
	/**
	 * True, if none of the atoms in the provided AtomSet are
	 * contained in the state.
	 */
	public boolean holdsNone(AtomSet atoms) {
		
		return this.atoms.none(atoms);
	}
	
	/**
	 * True, if this state is a superset of the provided state, 
	 * i.e. all atoms in the provided state are also contained
	 * in this state.
	 */
	public boolean isSupersetOf(State other) {
		
		return holdsAll(other.atoms);
	}
	
	/**
	 * Adds all atoms in the provided AtomSet to the state.
	 */
	public void addAll(AtomSet atoms) {
		
		this.atoms.applyTrueAtoms(atoms);
	}
	
	/**
	 * Removes all atoms in the provided AtomSet from the state.
	 */
	public void removeAll(AtomSet atoms) {
		
		this.atoms.applyTrueAtomsAsFalse(atoms);
	}
	
	/**
	 * Returns a list of booleans representing the atoms
	 * of the respective ID at each index. Warning: non-trivial
	 * runtime.
	 */
	public List<Boolean> getAtoms() {
		
		List<Boolean> atoms = new ArrayList<>(this.atoms.size());
		for (int i = 0; i < this.atoms.size(); i++) {
			atoms.add(this.atoms.get(i));
		}
		return atoms;
	}
	
	/**
	 * Returns the amount of atoms contained in the state.
	 */
	public int size() {
		return atoms.numAtoms();
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
