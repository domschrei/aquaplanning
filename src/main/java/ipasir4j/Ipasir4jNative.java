/** @author Tomas Balyo, KIT, Karlsruhe */
package ipasir4j;

/**
 * This class contains the headers of native methods to be implemented by an
 * ipasir compatible SAT solver. The methods follow the definitions from
 * ipasir.h except for the termination/interruption mechanism.
 */
public class Ipasir4jNative {

	/**
	 * Load the native library containing the implementation of the declared
	 * methods. A shared library named 'xyz' is supposed to be in a file named
	 * 'libxyz.so', which must be located in the current directory or its location
	 * can be specified using the env. variable LD_LIBRARY_PATH
	 */
	public static void loadLibrary(String libraryName) {
		System.loadLibrary(libraryName);
	}

	// Methods corresponding to native Ipasir functions

	/**
	 * Load the native library containing the implementation of the declared
	 * methods. A shared library named 'xyz' is supposed to be in a file named
	 * 'libxyz.so', which must be located in the current directory or its location
	 * can be specified using the env. variable LD_LIBRARY_PATH
	 */
	public static native String signature();

	/**
	 * Initialize a new instance of a solver and return its ID that will be used to
	 * reference it when using the other methods.
	 */
	public static native int init();

	/**
	 * Release the solver specified by the given ID, this solver can not be used
	 * anymore.
	 */
	public static native void release(int solverId);

	/**
	 * Add a literal to the next clause added to the solver specified by the given
	 * ID. Adding the value 0 finalizes the clause and adds it to the solver.
	 */
	public static native void add(int solverId, int lit);

	/**
	 * Add an assumption literal to the solver specified by the given ID. The added
	 * assumption will be used for the next call of the 'solve' method. When the
	 * 'solve' method is finished, the assumptions are cleared.
	 */
	public static native void assume(int solverId, int lit);

	/**
	 * Solve the problem specified by previous calls of 'add' (since the
	 * initializaion of the solver) under the assumptions specified by previous
	 * calls of 'assume' (since the last 'solve'). Returns 10 for satisfiable, 20
	 * for unsatisfiable and 0 for indeterminate (in case of interruption).
	 */
	public static native int solve(int solverId);

	/**
	 * After 'solve' has finished with the result 10 (satisfiable) this method can
	 * be used to get the value of given variable in the satisfying assignment.
	 * Returns: var if var has the value True -var if var has the value False 0 if
	 * the value var is not important
	 */
	public static native int val(int solverId, int var);

	/**
	 * After 'solve' has finished with the result 20 (unsatisfiable) this method can
	 * be used to check whether a given assumptions was used to derive the
	 * contradiction. Returns: 0 if lit used other if lit was not used
	 */
	public static native int failed(int solverId, int lit);

	// Interruption handled using an interrupt method
	// instead of the callback mechanism.

	/**
	 * Interrupts the solving which causes the 'solve' method to return with the
	 * value 0.
	 */
	public static native void interrupt(int solverId);

}
