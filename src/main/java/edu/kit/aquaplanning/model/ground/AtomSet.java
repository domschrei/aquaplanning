package edu.kit.aquaplanning.model.ground;

import java.util.BitSet;
import java.util.List;

/**
 * A set of atoms. Can be used to represent a set of true atoms
 * XOR a set of false atoms (not both at the same time).
 */
public class AtomSet {

	private BitSet atoms;

	/**
	 * Initialized an atom set from a list of Atom objects.
	 */
	public AtomSet(List<Atom> atoms) {
		this.atoms = new BitSet(atoms.size());
		for (Atom atom : atoms) {
			this.atoms.set(atom.getId(), atom.getValue());
		}
	}

	/**
	 * Initialized an atom set from a list of Atom objects.
	 * Only sets the atoms in the list which have the provided value.
	 */
	public AtomSet(List<Atom> atoms, boolean filteredValue) {
		this.atoms = new BitSet(atoms.size());
		for (Atom atom : atoms) {
			if (atom.getValue() == filteredValue)
				this.atoms.set(atom.getId(), true);
		}
	}
	

	/**
	 * Get the index of first true atom or -1 if none is true
	 */
	public int getFirstTrueAtom() {
		return atoms.nextSetBit(0);
	}
	
	/**
	 * Get the index of the next true atom at or after index i,
	 * or -1 if none such atom is true
	 */
	public int getNextTrueAtom(int i) {
		return atoms.nextSetBit(i);
	}

	/**
	 * True iff the provided atom is contained in this set
	 * (or, if the atom has a value of false, it is *not* contained).
	 */
	public boolean get(Atom atom) {
		return atom.getValue() == atoms.get(atom.getId());
	}
	
	/**
	 * True iff the atom of the provided ID is contained in this set..
	 */
	public boolean get(int id) {
		return atoms.get(id);
	}
	
	/**
	 * True iff all atoms which are set in the provided other AtomSet
	 * are also contained in this AtomSet.
	 */
	public boolean all(AtomSet other) {
		BitSet bs = (BitSet) other.atoms.clone();
		bs.andNot(atoms);
		return bs.isEmpty();
	}
	
	/**
	 * True iff none of the atoms which are set in the provided 
	 * other AtomSet are contained in this AtomSet.
	 */
	public boolean none(AtomSet other) {
		for (int i = other.atoms.nextSetBit(0); i >= 0; i = other.atoms.nextSetBit(i+1)) {
			if (atoms.get(i))
				return false;
		}
		return true;
	}

	/**
	 * Calculates the logical AND of this AtomSet with another AtomSets and returns the result.
	 * @param other
	 * 		The other AtomSet
	 * @return
	 * 		The logical AND of this and the other AtomSet
	 */
	public AtomSet and(AtomSet other) {
		AtomSet b = (AtomSet) this.clone();
		b.atoms.and(other.atoms);
		return b;
	}
	
	/**
	 * Calculates the logical XOR of this AtomSet with another AtomSets and returns the result.
	 * @param other
	 * 		The other AtomSet
	 * @return
	 * 		The logical AND of this and the other AtomSet
	 */
	public AtomSet xor(AtomSet other) {
		AtomSet b = (AtomSet) this.clone();
		b.atoms.xor(other.atoms);
		return b;
	}
	
	/**
	 * Sets the provided atom as contained in this set.
	 */
	public void set(Atom atom) {
		this.atoms.set(atom.getId(), atom.getValue());
	}
	
	/**
	 * In this AtomSet, sets all atoms which are contained
	 * in the other provided AtomSet.
	 */
	public void applyTrueAtoms(AtomSet other) {
		this.atoms.or(other.atoms);
	}
	
	/**
	 * In this AtomSet, *unsets* all atoms which are contained
	 * in the other provided AtomSet.
	 */
	public void applyTrueAtomsAsFalse(AtomSet other) {
		this.atoms.andNot(other.atoms);
	}
	
	/**
	 * The amount of atoms contained in this set.
	 */
	public int numAtoms() {
		return atoms.cardinality();
	}

	/**
	 * The internal size of the allocated set.
	 */
	public int size() {
		return this.atoms.size();
	}
	
	private AtomSet() {}
	@Override
	protected Object clone() {
		AtomSet newSet = new AtomSet();
		newSet.atoms = (BitSet) this.atoms.clone();
		return newSet;
	}
	
	@Override
	public String toString() {
		return atoms.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atoms == null) ? 0 : atoms.hashCode());
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
		AtomSet other = (AtomSet) obj;
		if (atoms == null) {
			if (other.atoms != null)
				return false;
		} else if (!atoms.equals(other.atoms))
			return false;
		return true;
	}
}
