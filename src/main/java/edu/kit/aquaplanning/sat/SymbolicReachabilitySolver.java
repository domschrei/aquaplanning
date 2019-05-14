package edu.kit.aquaplanning.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymbolicReachabilitySolver {

	private AbstractSatSolver satSolver;

	public SymbolicReachabilitySolver(AbstractSatSolver satSolver) {
		this.satSolver = satSolver;
	}

	public List<int[]> solve(SymbolicReachabilityFormula srp) {

		// add initial state
		for (int lit : srp.initialConditions) {
			satSolver.addClause(new int[] { lit });
		}
		// add universal state step 0 and 1
		addFormulaWithOffset(srp.universalClauses, 0);
		addFormulaWithOffset(srp.universalClauses, 1);

		// add transition
		addFormulaWithOffset(srp.transitionClauses, 0);

		int vars = srp.universalClauses.variablesCount;
		int step = 1;
		while (true) {
			System.out.println("Solving step " + step);
			boolean sat = satSolver.isSatisfiable(offsetArray(srp.goalConditions, step * vars));
			// boolean sat = satSolver.isSatisfiable();
			if (sat) {
				int[] model = new int[(step + 1) * vars + 1];
				for (int i = 0; i < model.length; i++) {
					model[i] = satSolver.getValue(i);
				}
				List<int[]> result = new ArrayList<>();
				for (int i = 0; i <= step; i++) {
					int[] chunk = Arrays.copyOfRange(model, i * vars, (i + 1) * vars + 1);
					result.add(offsetArray(chunk, -i * vars));
				}
				return result;
			} else {
				step++;
				addFormulaWithOffset(srp.universalClauses, step);
				addFormulaWithOffset(srp.transitionClauses, step - 1);
			}
			if (step > 100)
				return null;
		}
	}

	private void addFormulaWithOffset(SatFormula fla, int step) {
		for (int[] cl : fla.clauses) {
			satSolver.addClause(offsetArray(cl, step * fla.variablesCount));
		}
		for (int[] amos : fla.atMostOneGroups) {
			satSolver.addAtMostOneConstraint(offsetArray(amos, step * fla.variablesCount));
		}
	}

	private int[] offsetArray(int[] array, int offset) {
		int[] newcl = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			newcl[i] = array[i] > 0 ? array[i] + offset : array[i] - offset;
		}
		return newcl;
	}

}
