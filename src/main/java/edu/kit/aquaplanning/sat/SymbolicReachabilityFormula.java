package edu.kit.aquaplanning.sat;

import java.util.List;

public class SymbolicReachabilityFormula {
	public List<Integer> actionVariables;

	public int[] initialConditions;
	public int[] goalConditions;
	public SatFormula universalClauses;
	public SatFormula transitionClauses;

	public SymbolicReachabilityFormula() {
	}

}
