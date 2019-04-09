package edu.kit.aquaplanning.sat;

import edu.kit.aquaplanning.util.Logger;
import ipasir4j.Ipasir4j;
import ipasir4j.Ipasir4j.IPASIR_RESULT;

public class IpasirSatSolver extends AbstractSatSolver {

	private Ipasir4j solver;
	private IPASIR_RESULT result;
	
	private int timeoutSeconds = -1;
	
	private int numClauses;
	private int numAssumptions;
	
	public IpasirSatSolver() {
		try {
			solver = new Ipasir4j();
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
			Logger.log(Logger.ERROR, "Please make sure that you provided a valid path "
					+ "to the needed ipasir4j libs via the LD_LIBRARY_PATH environment variable.");
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
	
	@Override
	public void setTimeLimit(int seconds) {
		this.timeoutSeconds = seconds;
	}
	
	public void release() {
		solver.release();
	}

	@Override
	public Boolean isSatisfiable() {
		Logger.log(Logger.INFO, "Attempting to solve formula with " 
				+ numClauses + " clauses and " + numAssumptions + " assumptions.");
		
		result = IPASIR_RESULT.INDETERMINATE;
		
		if (timeoutSeconds >= 0) {
			Thread thread = new Thread(() -> {
				result = solver.solve();
			});
			thread.start();
			
			try {
				Thread.sleep(timeoutSeconds * 1000);
				solver.interrupt();
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			result = solver.solve();
		}
		
		numAssumptions = 0;
		if (result == IPASIR_RESULT.SATISFIABLE)
			return true;
		if (result == IPASIR_RESULT.UNSATISFIABLE)
			return false;
		return null;
	}

	@Override
	public Boolean isSatisfiable(int[] assumptions) {
		for (int assumption : assumptions) {
			addAssumption(assumption);
		}
		return isSatisfiable();
	}

	@Override
	public int getValue(int variable) {
		return solver.value(variable);
	}
}
