package edu.kit.aquaplanning.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Sat4jSolver extends AbstractSatSolver {
	
	protected ISolver solver;
	private List<Integer> assumptions;
	private SimpleSatPrinter printer;
	
	public Sat4jSolver() {
		solver = SolverFactory.newDefault();
		model = null;
	}
	
	public Sat4jSolver(SimpleSatPrinter printer) {
		solver = SolverFactory.newDefault();
		model = null;
		this.printer = printer;
	}

	/**
	 * Add a clause which is an array of integers, a positive (negative) integer 
	 * represents a positive (negative) literal. Variables are numbered starting
	 * with 1 and NOT ZERO!
	 * @param clause
	 * @return
	 */
	public void addClause(int... clause) {
		try {
			solver.addClause(new VecInt(clause));
			if (printer != null)
				printer.addClause(new VecInt(clause));
		} catch (ContradictionException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public void addAssumption(int assumption) {
		if (assumptions == null) {
			assumptions = new ArrayList<>();
		}
		assumptions.add(assumption);
	}
	
	public boolean addAtMostOneConstraint(int[] clause) {
		try {
			solver.addAtMost(new VecInt(clause), 1);
			if (printer != null) {
				printer.addAtMostOneConstraint(new VecInt(clause));
			}
		} catch (ContradictionException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Set the time limit for each individual solve call
	 * @param seconds
	 */
	public void setTimeLimit(int seconds) {
		solver.setTimeout(seconds);
	}
		
	/**
	 * Return true if the formula specified by the addClause calls is satisfiable under
	 * the given assumptions and false it is unsatisfiable. Return null in case of the
	 * time limit is reached.
	 * @param assumptions list of temporary unit clauses that hold only for this call
	 * @return
	 */
	public Boolean isSatisfiable(int[] assumptions) {
		try {
			if (printer != null)
				printer.addAssumptionsAndPrint(new VecInt(assumptions));
			int[] s4jModel = solver.findModel(new VecInt(assumptions));
			if (s4jModel == null) {
				model = null;
				return false;
			} else {
				// transform the model into a more convenient format
				model = new int[solver.nVars()+1];
				Arrays.fill(model, 0);
				for (int lit : s4jModel) {
					model[Math.abs(lit)] = lit;
				}
				return true;
			}
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	/**
	 * Return true if the formula specified by the addClause calls is satisfiable
	 * false it is unsatisfiable. Return null in case of the time limit is reached.
	 * @return
	 */
	public Boolean isSatisfiable() {
		int[] array = assumptions.stream().mapToInt(i->i).toArray();
		assumptions = null;
		return isSatisfiable(array);
	}
	
	/**
	 * Get the truth values of the satisfying assignment if it exists.
	 * getModel()[var] is +var if true and -var if false
	 * @return
	 */
	public int[] getModel() {
		return model;
	}
	
	@Override
	public int getValue(int variable) {
		return model[variable];
	}
	
	@Override
	public void release() {
		solver.reset();
	}
}
