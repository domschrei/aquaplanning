package edu.kit.aquaplanning.aquaplanning;

import edu.kit.aquaplanning.sat.Sat4jSolver;
import junit.framework.TestCase;

public class TestSatSolver extends TestCase {
	
	public void testSatSolverUnsat() {
		Sat4jSolver s = new Sat4jSolver();
		s.addClause(new int[] {1,2});
		s.addClause(new int[] {-1,2});
		s.addClause(new int[] {1,-2});
		s.addClause(new int[] {-1,-2});
		assertFalse(s.isSatisfiable());	
	}

	public void testSatSolverIncremental() {
		Sat4jSolver s = new Sat4jSolver();
		s.addClause(new int[] {1,4});
		s.addClause(new int[] {-1,4});
		s.addClause(new int[] {1,-4});
		assertTrue(s.isSatisfiable());
		assertNotNull(s.getModel());
		assertFalse(s.isSatisfiable(new int[] {-1}));
		assertNull(s.getModel());
		assertTrue(s.isSatisfiable(new int[] {1,2}));
		assertNotNull(s.getModel());
	}
	
}
