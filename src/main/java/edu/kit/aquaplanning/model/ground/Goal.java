package edu.kit.aquaplanning.model.ground;

import java.util.List;
import java.util.LinkedList;

public class Goal {
	
	private List<Atom> atoms;
	private List<Atom> positiveAtoms;
	
	public Goal(List<Atom> atoms) {
		this.atoms = atoms;
		
		this.positiveAtoms = new LinkedList<>();
		for (Atom atom : atoms) {
			if (atom.getValue()) {
				positiveAtoms.add(atom);
			}
		}
	}
	
	/**
	 * Returns true iff all goal atoms are satisfied in the
	 * provided state.
	 */
	public boolean isSatisfied(State state) {
		
		for (Atom atom : atoms) {
			if (!state.holds(atom)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true iff the goal is satisfied in a state 
	 * in a delete-relaxed sense, i.e. only the positive goals 
	 * need to hold.
	 */
	public boolean isSatisfiedRelaxed(State state) {
		
		// Only check positive atoms
		for (Atom atom : positiveAtoms) {
			if (!state.holds(atom)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Atom> getAtoms() {
		return atoms;
	}
	
	public List<Atom> getPositiveAtoms() {
		return positiveAtoms;
	}

	@Override
	public String toString() {
		return atoms.toString();
	}
}
