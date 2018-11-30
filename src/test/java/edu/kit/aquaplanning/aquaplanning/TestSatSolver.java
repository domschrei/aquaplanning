package edu.kit.aquaplanning.aquaplanning;

import edu.kit.aquaplanning.sat.SatSolver;
import junit.framework.TestCase;

public class TestSatSolver extends TestCase {
	
	public void testSatSolverUnsat() {
		SatSolver s = new SatSolver();
		s.addClause(new int[] {1,2});
		s.addClause(new int[] {-1,2});
		s.addClause(new int[] {1,-2});
		s.addClause(new int[] {-1,-2});
		assertFalse(s.isSatisfiable());	
	}

	public void testSatSolverIncremental() {
		SatSolver s = new SatSolver();
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
