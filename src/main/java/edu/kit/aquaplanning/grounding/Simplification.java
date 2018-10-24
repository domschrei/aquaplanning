package edu.kit.aquaplanning.grounding;

import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;

public class Simplification {
	
	/**
	 * Represents the result of the simplification of a list of atoms.
	 */
	public enum SimplificationResult {
		unsatisfiable, shortened, unmodified;
	}
	
	/**
	 * Performs a basic simplification on a set of atoms, resolving
	 * each equality predicate into TRUE of FALSE and thus manipulating
	 * the atom set correspondingly. Reports that either the set
	 * was left unmodified, or that it has been shortened by one or
	 * more atoms, or that it has been found to be unsatisfiable.
	 */
	public static SimplificationResult simplifyAtomSet(List<Atom> atoms) {
		
		SimplificationResult result = SimplificationResult.unmodified;
		for (int atomIdx = 0; atomIdx < atoms.size(); atomIdx++) {
			
			Atom atom = atoms.get(atomIdx).copy();
			boolean negated = !atom.getValue();
			atom.set(true);
			if (atom.toString().contains("=")) {
				String[] args = atom.toString().split(" ");
				if (!negated == args[1].equals(args[2])) {
					// Equality holds -- remove from conditions
					result = SimplificationResult.shortened;
					atoms.remove(atomIdx);
					atomIdx--;
				} else {
					// Equality does not hold -- entire set is unsat
					return SimplificationResult.unsatisfiable;
				}
			}
			atom.set(!negated);
		}
		return result;
	}
	
	/**
	 * Checks the given action's consistency, and simplifies away equality 
	 * conditions and trivially (un)satisfiable conditions. 
	 * If the action is found to be inherently contradictory or unsatisfiable, 
	 * null is returned.
	 */
	public static Action getSimplifiedAction(Action a) {
		return getSimplifiedAction(a.getName(), a.getPreconditions(), a.getEffects(), 
				a.getConditionalEffects(), a.getCost());
	}
	
	/**
	 * Assembles an action based on the provided parameters, checks
	 * its consistency, and simplifies away equality conditions and
	 * trivially (un)satisfiable conditions. If the action is found
	 * to be inherently contradictory or unsatisfiable, null is returned.
	 */
	public static Action getSimplifiedAction(String name, List<Atom> preconditions, 
			List<Atom> effects, List<ConditionalEffect> conditionalEffects, int cost) {
		
		// Check consistency of conditions (i.e. no contradictory atoms)
		if (isConsistent(preconditions) && isConsistent(effects)) {
			
			// Simplify out all equality-type atoms
			
			// ... in preconditions
			SimplificationResult result = simplifyAtomSet(preconditions);
			if (result == SimplificationResult.unsatisfiable)
				return null; // unsatisfiable; do not add this atom
			
			// ... in conditional effects
			for (int idx = 0; idx < conditionalEffects.size(); idx++) {					
				ConditionalEffect condEff = conditionalEffects.get(idx);
				result = simplifyAtomSet(condEff.getConditions());
				if (result == SimplificationResult.unsatisfiable) {
					// Remove unsatisfiable conditional effect
					conditionalEffects.remove(idx--); 
				} else if (condEff.getConditions().isEmpty()) {
					// All conditions have been simplified away;
					// compile consequences into general effects
					// and discard conditional effect structure
					effects.addAll(condEff.getEffects());
					conditionalEffects.remove(idx--);
				
				// Check consistency of the conditional effect
				} else if (!isConsistent(condEff.getConditions()) 
						|| !isConsistent(condEff.getEffects())) {
					conditionalEffects.remove(idx--);
				}
			}
			
			// Assemble action
			Action action = new Action(name, preconditions, 
					effects, conditionalEffects);
			action.setCost(cost); // action cost, if supplied
			return action;
		}
		
		return null;
	}
	
	/**
	 * Checks basic consistency of a set of atoms.
	 * A set of atoms is consistent if it does not contain 
	 * both "X" and "not(X)" for any X.
	 */
	private static boolean isConsistent(List<Atom> atoms) {
		
		atoms.sort((a1, a2) -> Integer.valueOf(a1.getId()).compareTo(a2.getId()));
		
		// For each atom
		for (int atomIdx = 0; atomIdx < atoms.size(); atomIdx++) {
			// Does the atom have a neighbor of the same ID?
			if (atomIdx > 0 && atoms.get(atomIdx).getId() == atoms.get(atomIdx-1).getId()) {
				// Do the atoms have contradictory values?
				if (atoms.get(atomIdx).getValue() != atoms.get(atomIdx-1).getValue()) {
					// -- yes
					return false;
				}
			}
		}
		return true;
	}
	
}
