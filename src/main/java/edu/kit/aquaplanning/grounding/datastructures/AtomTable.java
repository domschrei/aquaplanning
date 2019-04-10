package edu.kit.aquaplanning.grounding.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.DerivedAtom;
import edu.kit.aquaplanning.model.ground.NumericAtom;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Axiom;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

/**
 * Produces and maintains information on atoms, 
 * numeric atoms and derived atoms.
 */
public class AtomTable {
	
	private PlanningProblem problem;
	
	//private Map<String, AtomLookup> atoms;
	private Map<String, Atom> atoms;
	private Map<Integer, String> atomNames;
	private Map<String, DerivedAtom> derivedAtoms;
	private Map<Integer, String> derivedAtomNames;
	private Map<String, NumericAtom> numericAtoms;
	private Map<Integer, String> numericAtomNames;
	
	public AtomTable() {
		atoms = new HashMap<>();
		atomNames = new HashMap<>();
		derivedAtoms = new HashMap<>();
		derivedAtomNames = new HashMap<>();
		numericAtoms = new HashMap<>();
		numericAtomNames = new HashMap<>();
	}
	
	public void setProblem(PlanningProblem problem) {
		this.problem = problem;
	}
	
	/**
	 * Retrieves a copy of the atom corresponding to the provided predicate 
	 * and constant arguments. If this atom has not been grounded before,
	 * it will be created.
	 */
	public Atom atom(Predicate p, List<Argument> constants, boolean negated) {
		
		// Check if predicate is simple
		if (p.isDerived()) {
			throw new IllegalArgumentException("Attempted to create simple atom "
					+ "of a derived predicate.\nPredicate: " + p + "; constants: " + constants);
		}
		// Key: name of atom
		String atomName = getAtomName(p, constants);
		// Does the action already exists?
		if (!atoms.containsKey(atomName)) {
			// -- no: create new atom
			int atomId = atoms.size();
			atoms.put(atomName, new Atom(atomId, atomName, true));
			atomNames.put(atomId, atomName);
		}
		// Copy of atom
		Atom atom = atoms.get(atomName).copy();
		
		atom.set(!negated);
		return atom;
	}
	
	/**
	 * Retrieves the derived atom corresponding to the provided
	 * derived predicate and constant arguments. If this atom has not 
	 * been grounded before, it will be created. Note that the original
	 * object is returned and changes to it will be reflected in the
	 * original data structure.
	 */
	public DerivedAtom derivedAtom(Predicate p, List<Argument> constants) {
		
		// Check if predicate is simple
		if (!p.isDerived()) {
			throw new IllegalArgumentException("Attempted to create derived atom "
					+ "of a simple predicate.\nPredicate: " + p + "; constants: " + constants);
		}
		// Key: name of atom
		String atomName = getAtomName(p, constants);
		// Does the action already exists?
		if (!derivedAtoms.containsKey(atomName)) {
			// -- no: create new atom
			Axiom axiom = problem.getDerivedPredicates().get(p.getName());
			int atomId = - (atoms.size() + derivedAtoms.size());
			AbstractCondition cond = axiom.getCondition().getConditionBoundToArguments(
					axiom.getArguments(), constants);
			derivedAtoms.put(atomName, new DerivedAtom(atomId, atomName, cond));
			derivedAtomNames.put(atomId, atomName);
		}
		// Return derived atom
		return derivedAtoms.get(atomName);
	}
	
	public NumericAtom numericAtom(Function f, float value) {
		
		String atomName = getAtomName(f);
		if (!numericAtoms.containsKey(atomName)) {
			int atomId = numericAtoms.size();
			NumericAtom atom = new NumericAtom(atomId, atomName, Float.NaN);
			numericAtoms.put(atomName, atom);
			numericAtomNames.put(atomId, atomName);
		}
		NumericAtom valuedAtom = numericAtoms.get(atomName).copy();
		valuedAtom.setValue(value);
		return valuedAtom;
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	public String getAtomName(Predicate p, List<Argument> args) {
		
		String atomName = "(" + p.getName() + " ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an atom with a given predicate and a list
	 * of constant arguments.
	 */
	public String getAtomName(Function f) {
		
		String atomName = "(" + f.getName() + " ";
		for (Argument c : f.getArguments()) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Assembles the name of an action corresponding to the provided 
	 * operator with the provided list of constant arguments.
	 */
	public String getActionName(Operator op, List<Argument> args) {
		
		String atomName = "(" + op.getName() + " ";
		for (Argument c : args) {
			atomName += c.getName() + " ";
		}
		atomName = atomName.substring(0, atomName.length()-1) + ")";
		return atomName;
	}
	
	/**
	 * Compiles all atom names into a flat list, where the ith item
	 * is the name of the atom of ID i, and returns the list.
	 */
	public List<String> extractAtomNames() {
		
		List<String> atomNames = new ArrayList<>();
		for (int i = 0; i < this.atomNames.size(); i++) {
			atomNames.add(this.atomNames.get(i));
		}
		return atomNames;
	}
	/**
	 * Compiles all numeric atom names into a flat list.
	 */
	public List<String> extractNumericAtomNames() {
		
		List<String> atomNames = new ArrayList<>();
		for (int i = 0; i < numericAtoms.size(); i++) {
			atomNames.add(this.numericAtomNames.get(i));
		}
		return atomNames;
	}
	
	/**
	 * Returns the collected map of derived atoms.
	 * Keys are the atom names as retrieved by getDerivedAtomName().
	 */
	public Map<String, DerivedAtom> getDerivedAtoms() {
		return derivedAtoms;
	}

	public Map<String, NumericAtom> getNumericAtoms() {
		return numericAtoms;
	}
}
