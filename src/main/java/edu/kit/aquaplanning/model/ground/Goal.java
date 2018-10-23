package edu.kit.aquaplanning.model.ground;

import java.util.List;

public class Goal {
	
	private List<Atom> atoms;
	
	public Goal(List<Atom> atoms) {
		this.atoms = atoms;
	}
	
	public boolean isSatisfied(State state) {
		
		for (Atom atom : atoms) {
			if (!state.holds(atom)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return atoms.toString();
	}
}
