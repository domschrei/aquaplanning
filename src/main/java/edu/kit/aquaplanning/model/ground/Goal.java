package edu.kit.aquaplanning.model.ground;

import java.util.List;

public class Goal {
	
	private List<Atom> atoms;
	
	public Goal(List<Atom> atoms) {
		this.atoms = atoms;
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
		
		for (Atom atom : atoms) {
			// Only check positive atoms
			if (atom.getValue() && !state.holds(atom)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Atom> getAtoms() {
		return atoms;
	}

	@Override
	public String toString() {
		return atoms.toString();
	}
}
