package edu.kit.aquaplanning.sat;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.SatSolverMode;

public abstract class AbstractSatSolver {

	public abstract void addClause(int... clauses);

	public abstract void addAssumption(int assumption);

	public abstract Boolean isSatisfiable();

	public abstract Boolean isSatisfiable(int[] assumptions);

	public abstract int getValue(int variable);

	public abstract void setTimeLimit(int seconds);

	public abstract void release();

	// TODO Add binary encoding
	public void addAtMostOneConstraint(int[] clause) {
		for (int i = 0; i < clause.length; i++) {
			for (int j = i + 1; j < clause.length; j++) {
				addClause(-clause[i], -clause[j]);
			}
		}
	}

	public static AbstractSatSolver getSolver(Configuration config) {

		if (config.satSolverMode == SatSolverMode.ipasir4j) {
			return new IpasirSatSolver();
		} else {
			if (config.satFormulaFile != null) {
				return new Sat4jSolver(new SimpleSatPrinter(config.satFormulaFile));
			} else {
				return new Sat4jSolver();
			}
		}
	}
}
