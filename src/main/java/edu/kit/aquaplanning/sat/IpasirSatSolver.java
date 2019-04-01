package edu.kit.aquaplanning.sat;

import edu.kit.aquaplanning.util.Logger;
import ipasir4j.Ipasir4j;
import ipasir4j.Ipasir4j.IPASIR_RESULT;

public class IpasirSatSolver {

	private Ipasir4j solver;
	
	private int numClauses;
	private int numAssumptions;
	
	public IpasirSatSolver() {
		try {
			solver = new Ipasir4j();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
		numClauses = 0;
		numAssumptions = 0;
	}
	
	public void addClause(int... lits) {
		solver.addClause(lits);
		numClauses++;
	}
	
	public void addAssumption(int lit) {
		solver.assume(lit);
		numAssumptions++;
	}
	
	public Boolean solve() {
		Logger.log(Logger.INFO, "Attempting to solve formula with " 
					+ numClauses + " clauses and " + numAssumptions + " assumptions.");
		IPASIR_RESULT result = solver.solve();
		numAssumptions = 0;
		if (result == IPASIR_RESULT.SATISFIABLE)
			return true;
		if (result == IPASIR_RESULT.UNSATISFIABLE)
			return false;
		return null;
	}
	
	public void release() {
		solver.release();
	}
	
	public boolean holds(int var) {
		return solver.value(var) > 0;
	}
}
