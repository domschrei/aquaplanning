package edu.kit.aquaplanning.planning.sat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.planning.GroundPlanner;
import edu.kit.aquaplanning.sat.AbstractSatSolver;
import edu.kit.aquaplanning.util.Logger;

public class HegemannsSatPlanner extends GroundPlanner {

	/**
	 * A ranking of actions for use in SAT encodings of planning problems
	 */
	class Ranking implements Comparator<Action> {
		class NodeAttributes {
			boolean visited = false;
			int rank;
		}

		final private List<Action> unvisitedActions;
		private int lastRank = 0;
		final private Map<Action, NodeAttributes> ranks;

		/**
		 * Default constructor
		 * 
		 * @param actions A list of actions that are to be ranked
		 */
		Ranking(List<Action> actions) {
			this.unvisitedActions = new LinkedList<>(actions);

			// Populate map for actions to node attributes
			ranks = new HashMap<>();
			for (Action a : actions) {
				NodeAttributes nodeAttributes = new NodeAttributes();
				ranks.put(a, nodeAttributes);
			}

			// Rank all the actions
			for (Action a : actions) {
				NodeAttributes nodeAttributes = ranks.get(a);
				if (nodeAttributes.visited)
					continue;
				rankAction(a);
			}
		}

		// Recursively ranks the actions
		private void rankAction(Action action) {
			NodeAttributes nodeAttributes = ranks.get(action);
			if (nodeAttributes.visited)
				return;

			nodeAttributes.visited = true;
			unvisitedActions.remove(action);

			// Find supporting actions
			AtomSet pPos = action.getPreconditionsPos();
			AtomSet pNeg = action.getPreconditionsNeg();
			List<Action> supp = new LinkedList<>();
			for (Action other : unvisitedActions) {
				if ((!other.getEffectsPos().none(pPos)) || (!other.getEffectsNeg().none(pNeg))) {
					supp.add(other);
				}
			}
			// Rank all the supporting actions first
			for (Action a : supp) {
				rankAction(a);
			}

			nodeAttributes.rank = lastRank;
			lastRank++;
		}

		@Override
		public int compare(Action a1, Action a2) {
			return ranks.get(a1).rank - ranks.get(a2).rank;
		}
	}

	public HegemannsSatPlanner(Configuration config) {
		super(config);
	}

	private ArrayList<Action> rankedActions;
	private int numAtoms;

	private int satVarsPerLayer = 0;
	private List<int[]> recurrentClauses;

	private double timeLimitDecay = 0.9;
	private int initialTimeLimit = 40;
	private int skipLayers = 5;

	private int getActionSatVariable(int rank, int step) {
		return 1 + rank + numAtoms + step * satVarsPerLayer;
	}

	private int getAtomSatVariable(int atomId, int step) {
		return 1 + atomId + step * satVarsPerLayer;
	}

	public ActionPlan findPlan(GroundPlanningProblem problem) {
		this.numAtoms = problem.getNumAtoms();
		maxSatVar = 0;
		double timeLimit = initialTimeLimit;

		// Calculate ranking and pre-sort our actions
		Ranking ranking = new Ranking(problem.getActions());
		rankedActions = new ArrayList<>(problem.getActions());
		rankedActions.sort(ranking);

		// Calculate supporting actions for atoms
		initializeSupports(problem);

		AbstractSatSolver solver = AbstractSatSolver.getSolver(config);

		// Add the initial state unit clauses
		addInitialStateClauses(problem, solver);

		calculateRecurrentClauses();

		// Find the plan
		int step = 0;
		while (true) {
			Logger.log(Logger.INFO_V, "Step " + step + ": " + (int) Math.ceil(timeLimit) + " seconds limit");

			for (int i = 0; i < skipLayers; i++) {
				addRecurrentClauses(solver, step);
				step++;
			}
			step--;
			// System.out.println("clauses added");

			// we will assume that the goal is satisfied after this step
			int[] assumptions = calculateGoalAssumptions(problem, step + 1);

			solver.setTimeLimit((int) Math.ceil(timeLimit));

			Boolean result = solver.isSatisfiable(assumptions);
			if (result != null && result) {
				// We found a Plan!
				break;
			} else {
				// Try more steps
				step++;
			}

			timeLimit *= timeLimitDecay;
		}

		// Decode the plan
		ActionPlan plan = new ActionPlan();
		for (int i = 0; i <= step; i++) {
			for (int r = 0; r < rankedActions.size(); r++) {
				Action a = rankedActions.get(r);
				if (solver.getValue(getActionSatVariable(r, i)) > 0) {
					plan.appendAtBack(a);
				}
			}
		}
		return plan;
	}

	private void calculateRecurrentClauses() {
		maxSatVar = numAtoms + rankedActions.size();
		recurrentClauses = calculateImplicationChainClauses();
		satVarsPerLayer = maxSatVar;
		recurrentClauses.addAll(calculateUniversalClauses());
		recurrentClauses.addAll(calculateTransitionalClauses());
	}

	private void addRecurrentClauses(AbstractSatSolver solver, int step) {
		if (step == 0) {
			for (int[] clause : recurrentClauses) {
				solver.addClause(clause);
			}
		} else {
			for (int[] clause : recurrentClauses) {
				// Shift all variables in all clauses to fit the desired step
				int[] shiftedClause = new int[clause.length];
				System.arraycopy(clause, 0, shiftedClause, 0, clause.length);
				for (int i = 0; i < shiftedClause.length; i++) {
					shiftedClause[i] += ((step * satVarsPerLayer) * Integer.signum(shiftedClause[i]));
				}
				solver.addClause(shiftedClause);
			}
		}
	}

	private int maxSatVar = 0;

	private int getNextHelperVar() {
		maxSatVar++;
		return maxSatVar;
	}

	private List<int[]> calculateImplicationChainClauses() {
		List<int[]> clauses = new LinkedList<>();

		// Implication chain for each atom for true and false
		for (int atomid = 0; atomid < numAtoms; atomid++) {
			int lastHelper;
			boolean chainInitialized;

			// chain for -p
			lastHelper = 0;
			chainInitialized = false;
			int r = 0;
			for (Action action : rankedActions) {
				if (chainInitialized) {
					if (action.getPreconditionsPos().get(atomid)) {
						clauses.add(new int[] { -lastHelper, -getActionSatVariable(r, 0) });
					}
				}
				if (action.getEffectsNeg().get(atomid)) {
					int newHelper = getNextHelperVar();
					if (chainInitialized) {
						clauses.add(new int[] { -lastHelper, newHelper });
					}
					chainInitialized = true;
					clauses.add(new int[] { -getActionSatVariable(r, 0), newHelper });
					lastHelper = newHelper;
				}
				r++;
			}

			// chain for +p
			lastHelper = 0;
			chainInitialized = false;
			r = 0;
			for (Action action : rankedActions) {
				if (chainInitialized) {
					if (action.getPreconditionsNeg().get(atomid)) {
						clauses.add(new int[] { -lastHelper, -getActionSatVariable(r, 0) });
					}
				}
				if (action.getEffectsPos().get(atomid)) {
					int newHelper = getNextHelperVar();
					if (chainInitialized) {
						clauses.add(new int[] { -lastHelper, newHelper });
					}
					chainInitialized = true;
					clauses.add(new int[] { -getActionSatVariable(r, 0), newHelper });
					lastHelper = newHelper;
				}
				r++;
			}
		}

		// System.out.println("implication chain clauses: " + clauses.size());

		return clauses;
	}

	/*
	 * Stuff from the SimpleSatPlanner class
	 */
	// supporting actions for positive atoms
	private Map<Integer, List<Integer>> supportingActionsPositive;
	// supporting actions for negative atoms
	private Map<Integer, List<Integer>> supportingActionsNegative;
	final private List<Integer> empty = new ArrayList<>();

	/**
	 * Calculates the assumptions that represent that the goal is reached
	 */
	private int[] calculateGoalAssumptions(GroundPlanningProblem problem, int step) {
		List<Atom> goalAtoms = problem.getGoal().getAtoms();
		int[] assumptions = new int[goalAtoms.size()];
		for (int i = 0; i < goalAtoms.size(); i++) {
			assumptions[i] = getAtomSatVariable(goalAtoms.get(i).getId(), step);
		}
		return assumptions;
	}

	/**
	 * Clauses that hold in the initial state (first step)
	 */
	private void addInitialStateClauses(GroundPlanningProblem problem, AbstractSatSolver solver) {
		for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
			int atomSatId = getAtomSatVariable(atomid, 0);
			if (problem.getInitialState().getAtomSet().get(atomid)) {
				solver.addClause(new int[] { atomSatId });
			} else {
				solver.addClause(new int[] { -atomSatId });
			}
		}
	}

	/**
	 * Clauses encoding the transition between step and step+1
	 */
	private List<int[]> calculateTransitionalClauses() {
		List<int[]> clauses = new LinkedList<>();

		// actions imply their effects
		int r = 0;
		for (Action a : rankedActions) {
			int actionSatId = getActionSatVariable(r, 0);
			for (int atomid = 0; atomid < numAtoms; atomid++) {
				if (a.getEffectsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 1);
					clauses.add(new int[] { -actionSatId, atomSatId });
				}
				if (a.getEffectsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 1);
					clauses.add(new int[] { -actionSatId, -atomSatId });
				}
			}
			r++;
		}

		// frame axioms -- if an atom changes then there must be an action causing it
		for (int atomId = 0; atomId < numAtoms; atomId++) {
			// change of atom from true to false
			List<Integer> supports = getSupportingActions(atomId, false);
			int[] p2n = new int[2 + supports.size()];
			p2n[0] = -getAtomSatVariable(atomId, 0);
			p2n[1] = getAtomSatVariable(atomId, 1);
			int next = 2;
			for (int a : supports) {
				p2n[next] = getActionSatVariable(a, 0);
				next++;
			}
			clauses.add(p2n);

			// change of atom from false to true
			supports = getSupportingActions(atomId, true);
			int[] n2p = new int[2 + supports.size()];
			n2p[0] = getAtomSatVariable(atomId, 0);
			n2p[1] = -getAtomSatVariable(atomId, 1);
			next = 2;
			for (int a : supports) {
				n2p[next] = getActionSatVariable(a, 0);
				next++;
			}
			clauses.add(n2p);
		}

		// System.out.println("transitional clauses: " + clauses.size());

		return clauses;
	}

	private List<int[]> calculateUniversalClauses() {
		List<int[]> clauses = new LinkedList<>();

		// actions imply their preconditions
		int r = 0;
		for (Action a : rankedActions) {
			int actionSatId = getActionSatVariable(r, 0);
			for (int atomid = 0; atomid < numAtoms; atomid++) {
				if (a.getPreconditionsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 0);
					clauses.add(new int[] { -actionSatId, atomSatId });
				}
				if (a.getPreconditionsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 0);
					clauses.add(new int[] { -actionSatId, -atomSatId });
				}
			}
			r++;
		}

		return clauses;
	}

	private List<Integer> getSupportingActions(int atomid, boolean positive) {
		List<Integer> result = positive ? supportingActionsPositive.get(atomid) : supportingActionsNegative.get(atomid);
		return result != null ? result : empty;
	}

	private void initializeSupports(GroundPlanningProblem problem) {
		supportingActionsPositive = new HashMap<>();
		supportingActionsNegative = new HashMap<>();

		int r = 0;
		for (Action a : rankedActions) {
			// calculate atom supports
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getEffectsPos().get(atomid)) {
					if (!supportingActionsPositive.containsKey(atomid)) {
						supportingActionsPositive.put(atomid, new LinkedList<>());
					}
					supportingActionsPositive.get(atomid).add(r);
				}
				if (a.getEffectsNeg().get(atomid)) {
					if (!supportingActionsNegative.containsKey(atomid)) {
						supportingActionsNegative.put(atomid, new LinkedList<>());
					}
					supportingActionsNegative.get(atomid).add(r);
				}
			}
			r++;
		}
	}
}
