package edu.kit.aquaplanning.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.aquaplanning.util.Logger;

/**
 * Basic representation of a CNF formula
 */
public class SatFormula {
	public int variablesCount;
	public List<int[]> clauses;
	public List<int[]> atMostOneGroups;

	public SatFormula(int vars) {
		variablesCount = vars;
		clauses = new ArrayList<int[]>();
		atMostOneGroups = new ArrayList<int[]>();
	}

	public SatFormula(int variables, List<int[]> clauses, List<int[]> atMostOneGroups) {
		this.variablesCount = variables;
		this.clauses = clauses;
		this.atMostOneGroups = atMostOneGroups;
	}

	public SatFormula() {
		variablesCount = 0;
		clauses = new ArrayList<>();
		atMostOneGroups = new ArrayList<>();
	}

	public void addClause(int[] cls) {
		clauses.add(cls);
	}

	public void addAtMostOneGroup(int[] group) {
		atMostOneGroups.add(group);
	}

	public SatFormula copy() {
		SatFormula f = new SatFormula();
		f.variablesCount = variablesCount;
		f.clauses = new ArrayList<int[]>();
		f.atMostOneGroups = new ArrayList<int[]>();
		for (int[] cl : clauses) {
			f.clauses.add(Arrays.copyOf(cl, cl.length));
		}
		for (int[] cl : atMostOneGroups) {
			f.atMostOneGroups.add(Arrays.copyOf(cl, cl.length));
		}
		return f;
	}

	public SatFormula shallowCopy() {
		SatFormula f = new SatFormula();
		f.variablesCount = variablesCount;
		f.clauses = new ArrayList<int[]>(clauses);
		f.atMostOneGroups = new ArrayList<>(atMostOneGroups);
		return f;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("p cnf %d %d\n", variablesCount, clauses.size()));
		for (int[] cl : clauses) {
			sb.append(Arrays.toString(cl) + "\n");
		}
		for (int[] gr : atMostOneGroups) {
			sb.append("AMO " + Arrays.toString(gr) + "\n");
		}
		return sb.toString();
	}

	/**
	 * Return true if the given model satisfies the formula
	 * 
	 * @param model
	 * @return
	 */
	public boolean validateSolution(int[] model) {
		clauseLoop: for (int[] cl : clauses) {
			for (int l : cl) {
				if (model[Math.abs(l)] == l)
					continue clauseLoop;
			}
			Logger.log(Logger.ERROR, String.format("The clause (%s) is not satisfied", Arrays.toString(cl)));
			return false;
		}
		for (int[] gr : atMostOneGroups) {
			int satLits = 0;
			for (int l : gr) {
				if (model[Math.abs(l)] == l) {
					satLits++;
				}
			}
			if (satLits > 1) {
				Logger.log(Logger.ERROR, String.format("The amo group (%s) is not satisfied", Arrays.toString(gr)));
				return false;
			}
		}
		return true;
	}

}