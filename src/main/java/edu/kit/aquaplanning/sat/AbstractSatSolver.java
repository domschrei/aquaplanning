package edu.kit.aquaplanning.sat;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.Configuration.SatSolverMode;

public abstract class AbstractSatSolver {

	protected int[] model;
	
	public abstract void addClause(int... clauses);
	public abstract void addAssumption(int assumption);
	public abstract Boolean isSatisfiable();
	public abstract Boolean isSatisfiable(int[] assumptions);
	public abstract int getValue(int variable);
	public abstract void setTimeLimit(int seconds);
	public abstract void release();
	
	public static AbstractSatSolver getSolver(Configuration config) {
		
		if (config.satSolverMode == SatSolverMode.ipasir4j) {
			return new IpasirSatSolver();
		} else  {
			if (config.satFormulaFile != null) {
				return new Sat4jSolver(new SimpleSatPrinter(config.satFormulaFile));
			} else {
				return new Sat4jSolver();
			}
		}
	}
}
