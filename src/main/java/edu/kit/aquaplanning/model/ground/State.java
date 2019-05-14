package edu.kit.aquaplanning.model.ground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a world state as a set of atoms which are currently true.
 */
public class State {

	/**
	 * Internal AtomSet of all true atoms.
	 */
	private AtomSet atoms;

	/**
	 * Truth values of derived atoms, where already known.
	 */
	private Map<DerivedAtom, Boolean> derivedAtoms;

	/**
	 * Maps ID of numeric atom to its current value
	 */
	private Map<Integer, Float> numericAtoms;

	/**
	 * Creates a state containing exactly all TRUE atoms in the provided list.
	 */
	public State(List<Atom> atomList) {

		this.atoms = new AtomSet(atomList);
		this.derivedAtoms = new HashMap<>();
		this.numericAtoms = new HashMap<>();
	}

	/**
	 * Copies the provided state into a new object.
	 */
	public State(State other) {

		atoms = (AtomSet) other.atoms.clone();
		this.derivedAtoms = new HashMap<>(); // TODO clone?
		this.numericAtoms = new HashMap<>();
		this.numericAtoms.putAll(other.numericAtoms);
	}

	/**
	 * Creates a state containing all the atoms in the provided set.
	 */
	public State(AtomSet atomSet) {

		this.atoms = atomSet;
		this.derivedAtoms = new HashMap<>();
		this.numericAtoms = new HashMap<>();
	}

	/**
	 * Sets the provided atom. If the atom has negative value, it is removed from
	 * the state; else, it is added to the state.
	 */
	public void set(Atom atom) {

		atoms.set(atom);
	}

	public void set(NumericAtom atom) {

		numericAtoms.put(atom.getId(), atom.getValue());
	}

	/**
	 * Extends this state by all atoms contained in the provided other state.
	 */
	public void addAllTrueAtomsFrom(State other) {

		atoms.applyTrueAtoms(other.atoms);
	}

	/**
	 * True, if the given atom holds in this state (i.e. (the atom is true AND the
	 * atom is in the state) OR (the atom is false AND the atom is not in the
	 * state)).
	 */
	public boolean holds(Atom atom) {

		return atoms.get(atom);
	}

	public float get(NumericAtom atom) {
		return numericAtoms.get(atom.getId());
	}

	/**
	 * True, if all atoms in the provided AtomSet are contained in the state.
	 */
	public boolean holdsAll(AtomSet atoms) {

		return this.atoms.all(atoms);
	}

	/**
	 * True, if none of the atoms in the provided AtomSet are contained in the
	 * state.
	 */
	public boolean holdsNone(AtomSet atoms) {

		return this.atoms.none(atoms);
	}

	/**
	 * Determines and returns the truth value of a derived atom. Note: Has side
	 * effects to the internal data structures of this object in order to accelerate
	 * subsequent calls of this method.
	 */
	public boolean holds(DerivedAtom derivedAtom) {

		boolean visitedBefore = derivedAtoms.containsKey(derivedAtom);
		if (!visitedBefore) {
			// Value is not known yet:
			// open the derived atom
			derivedAtoms.put(derivedAtom, null);
			// try to find the value
			Boolean value = derivedAtom.getCondition().holds(this);
			// close the derived atom
			derivedAtoms.put(derivedAtom, value);
			return value;
		} else {
			Boolean value = derivedAtoms.get(derivedAtom);
			if (value == null) {
				// Derived atom is currently open: No gain of knowledge
				return false;
			} else {
				// value is known: return it
				return value;
			}
		}
	}

	/**
	 * True, if this state is a superset of the provided state, i.e. all atoms in
	 * the provided state are also contained in this state.
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
	 * Returns a list of booleans representing the atoms of the respective ID at
	 * each index. Warning: non-trivial runtime.
	 */
	public List<Boolean> getAtoms() {

		List<Boolean> atoms = new ArrayList<>();

		// For each set atom
		for (int i = this.atoms.getFirstTrueAtom(); i >= 0; i = this.atoms.getNextTrueAtom(i + 1)) {
			while (atoms.size() < i)
				atoms.add(false);
			atoms.add(this.atoms.get(i));
		}

		return atoms;
	}

	/**
	 * Returns the set of atoms of this state. (Trivial runtime)
	 */
	public AtomSet getAtomSet() {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!other.atoms.equals(atoms))
			return false;
		for (int i = 0; i < numericAtoms.size(); i++) {
			if (!other.numericAtoms.get(i).equals(numericAtoms.get(i)))
				return false;
		}
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
		for (int i = 0; i < numericAtoms.size(); i++) {
			Float atom = numericAtoms.get(i);
			result = prime * result + atom.hashCode();
		}
		return result;
	}

	/**
	 * Only outputs raw booleans! Use groundPlanningProblem.stateToString(state)
	 * instead.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < atoms.size(); i++) {
			boolean atom = atoms.get(i);
			builder.append((atom ? "1" : "0") + " ");
		}
		for (int i = 0; i < numericAtoms.size(); i++) {
			float atom = numericAtoms.get(i);
			builder.append(atom + " ");
		}
		return builder.toString();
	}
}
