/** @author Tomas Balyo, KIT, Karlsruhe */
package ipasir4j;

/**
 * A Java-style ipasir interface
 */
public class Ipasir4j {
	
	public enum IPASIR_RESULT {
		SATISFIABLE,
		UNSATISFIABLE,
		INDETERMINATE
	}
	
	private int solverId;
	
	/**
	 * Default constructor.
	 */
	public Ipasir4j() {
		this("ipasir4j");
	}
	
	/**
	 * Constructor with specifying the name
	 * of the shared library implementing the native
	 * SAT solver functions, default is "ipasir4j".
	 */
	public Ipasir4j(String libraryName) {
		Ipasir4jNative.loadLibrary(libraryName);
		solverId = Ipasir4jNative.init();
	}
	
	/**
	 * Release the solver, it cannot be used after this call.
	 */
	public void release() {
		Ipasir4jNative.release(solverId);
	}
	
	/**
	 * Get the name and version of the linked SAT solver.
	 */
	public String getSignature() {
		return Ipasir4jNative.signature();
	}
	
	/**
	 * Add a literal to the next clause to be added.
	 * Call with 0 to finalize the current clause.
	 */
	public void addLiteral(int lit) {
		Ipasir4jNative.add(solverId, lit);
	}
	
	/**
	 * Add a clause (not necessarily zero terminated)
	 * to the SAT solver.
	 */
	public void addClause(int ... lits) {
		for (int lit : lits) {
			addLiteral(lit);
		}
		if (lits[lits.length-1] != 0) {
			addLiteral(0);
		}
	}
	
	/**
	 * Add an assumption that will be used for the next
	 * 'solve' call.
	 */
	public void assume(int lit) {
		Ipasir4jNative.assume(solverId, lit);
	}
	
	/**
	 * Add a set of assumption that will be used for
	 * the next 'solve' call.
	 */
	public void assume(int ... lits) {
		for (int lit : lits) {
			Ipasir4jNative.assume(solverId, lit);
		}
	}
	
	/**
	 * Solve the SAT problem specified by previous 'add's
	 * (since init) and 'assume's (since last 'solve').
	 */
	public IPASIR_RESULT solve() {
		int result = Ipasir4jNative.solve(solverId);
		if (result == 20) {
			return IPASIR_RESULT.UNSATISFIABLE;
		} else if (result == 10) {
			return IPASIR_RESULT.SATISFIABLE;
		} else {
			return IPASIR_RESULT.INDETERMINATE;
		}
	}
	
	/**
	 * If solve finished with SATISFIABLE this method
	 * return the truth value of the specified variable.
	 * returns var for True, -var for False, 0 for irrelevant.
	 */
	public int value(int variable) {
		return Ipasir4jNative.val(solverId, variable);
	}
	
	/**
	 * Returns true if the given literal was among the assumptions
	 * that were used to prove that the formula is UNSATISFIABLE.
	 */
	public boolean failed(int literal) {
		return Ipasir4jNative.failed(solverId, literal) != 0;
	}
	
	/**
	 * Stop the solver, causes 'solve' to return with INDETERMINATE
	 */
	public void interrupt() {
		Ipasir4jNative.interrupt(solverId);
	}

}
