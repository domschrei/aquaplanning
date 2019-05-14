package edu.kit.aquaplanning.model.ground;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Goal {

	private List<Atom> atoms;
	private List<Atom> positiveAtoms;

	private boolean isComplex = false;
	private Precondition complexCondition;

	public Goal(List<Atom> atoms) {
		this.atoms = atoms;

		this.positiveAtoms = new LinkedList<>();
		for (Atom atom : atoms) {
			if (atom.getValue()) {
				positiveAtoms.add(atom);
			}
		}
	}

	public Goal(Precondition complexCondition) {
		isComplex = true;
		this.complexCondition = complexCondition;
	}

	/**
	 * Copies the provided goal into a new object.
	 */
	public Goal(Goal other) {
		if (other.isComplex) {
			this.isComplex = true;
			this.complexCondition = new Precondition(other.complexCondition);
		} else {
			this.atoms = new ArrayList<Atom>(other.atoms);
			this.positiveAtoms = new LinkedList<Atom>(other.positiveAtoms);
		}
	}

	/**
	 * Returns true iff all goal atoms are satisfied in the provided state.
	 */
	public boolean isSatisfied(State state) {

		if (isComplex) {
			return complexCondition.holds(state);
		}

		for (Atom atom : atoms) {
			if (!state.holds(atom)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true iff the goal is satisfied in a state in a delete-relaxed sense,
	 * i.e. only the positive goals need to hold.
	 */
	public boolean isSatisfiedRelaxed(State state) {

		if (isComplex) {
			return complexCondition.holdsRelaxed(state);
		}

		// Only check positive atoms
		for (Atom atom : positiveAtoms) {
			if (!state.holds(atom)) {
				return false;
			}
		}
		return true;
	}

	public List<Atom> getAtoms() {
		if (isComplex) {
			throw new IllegalArgumentException("Cannot retrieve flat atom list of a complex goal");
		}
		return atoms;
	}

	public List<Atom> getPositiveAtoms() {
		if (isComplex) {
			throw new IllegalArgumentException("Cannot retrieve flat atom list of a complex goal");
		}
		return positiveAtoms;
	}

	public Precondition getComplexCondition() {
		if (!isComplex) {
			throw new IllegalArgumentException("Cannot retrieve complex condition object of a simple goal");
		}
		return complexCondition;
	}

	@Override
	public String toString() {
		if (isComplex) {
			return complexCondition.toString();
		} else {
			return atoms.toString();
		}
	}
}
