package edu.kit.aquaplanning.planning.sat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.planning.GroundPlanner;
import edu.kit.aquaplanning.sat.AbstractSatSolver;

public class SimpleSatPlanner extends GroundPlanner {

	private Map<String, Integer> actionIds;
	// supporting actions for positive atoms
	private Map<Integer, List<Action>> supportingActionsPositive;
	// supporting actions for negative atoms
	private Map<Integer, List<Action>> supportingActionsNegative;
	private List<Action> empty = new ArrayList<>();
	private boolean ignoreAtMostOneAction = false;

	public SimpleSatPlanner(Configuration config) {
		super(config);
	}

	public void setIgnoreAtMostOneAction(boolean value) {
		this.ignoreAtMostOneAction = value;
	}

	private List<Action> getSupportingActions(int atomid, boolean positive) {
		List<Action> result = positive ? supportingActionsPositive.get(atomid) : supportingActionsNegative.get(atomid);
		return result != null ? result : empty;
	}

	protected void initializeActionIdsAndSupports(GroundPlanningProblem problem) {
		startSearch();
		actionIds = new HashMap<>();
		supportingActionsPositive = new HashMap<>();
		supportingActionsNegative = new HashMap<>();

		int nextId = problem.getNumAtoms();
		for (Action a : problem.getActions()) {
			// set action IDs
			actionIds.put(a.getName(), nextId);
			nextId++;

			// calculate atom supports
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getEffectsPos().get(atomid)) {
					if (!supportingActionsPositive.containsKey(atomid)) {
						supportingActionsPositive.put(atomid, new ArrayList<>());
					}
					supportingActionsPositive.get(atomid).add(a);
				}
				if (a.getEffectsNeg().get(atomid)) {
					if (!supportingActionsNegative.containsKey(atomid)) {
						supportingActionsNegative.put(atomid, new ArrayList<>());
					}
					supportingActionsNegative.get(atomid).add(a);
				}
			}
		}
	}

	// number of Boolean variables in each sat solving step
	// it is the sum of number of actions and atoms
	protected int satVarsPerStep;

	protected int getActionSatVariable(String name, int step) {
		int aid = actionIds.get(name);
		return 1 + aid + step * (satVarsPerStep);
	}

	private int getAtomSatVariable(int atomId, int step) {
		return 1 + atomId + step * (satVarsPerStep);
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {

		// calculate number of sat variables required for each step
		satVarsPerStep = problem.getNumAtoms() + problem.getActions().size();

		// assign IDs to actions and calculate supporting actions for atoms
		initializeActionIdsAndSupports(problem);

		// initialize the SAT solver
		AbstractSatSolver solver = AbstractSatSolver.getSolver(config);

		// add the initial state unit clauses
		addInitialStateClauses(problem, solver);

		// find the plan
		int step = 0;
		while (withinComputationalBounds(step + 1)) {

			// add the universal clauses for this step
			addUniversalClauses(problem, solver, step);

			// add the transitional clauses for this and next step
			addTransitionalClauses(problem, solver, step);

			// we will assume that the goal is satisfied after this step
			int[] assumptions = calculateGoalAssumptions(problem, step + 1);

			Boolean result = solver.isSatisfiable(assumptions);
			if (result != null && result) {
				// We found a Plan!
				break;
			} else {
				// Try more steps
				step++;
			}
		}

		if (!withinComputationalBounds(step + 1)) {
			// No plan found in the given computational bounds
			return null;
		}

		// Decode the plan
		ActionPlan plan = new ActionPlan();
		for (int i = 0; i <= step; i++) {
			for (Action a : problem.getActions()) {
				if (solver.getValue(getActionSatVariable(a.getName(), i)) > 0) {
					plan.appendAtBack(a);
				}
			}
		}
		return plan;
	}

	/**
	 * Calculates the assumptions that represent that the goal is reached
	 * 
	 * @param problem
	 * @param step
	 * @return
	 */
	protected int[] calculateGoalAssumptions(GroundPlanningProblem problem, int step) {
		List<Atom> goalAtoms = problem.getGoal().getAtoms();
		int[] assumptions = new int[goalAtoms.size()];
		for (int i = 0; i < goalAtoms.size(); i++) {
			assumptions[i] = getAtomSatVariable(goalAtoms.get(i).getId(), step);
		}
		return assumptions;
	}

	/**
	 * Clauses that hold in the initial state (first step)
	 * 
	 * @param problem
	 * @param solver
	 */
	protected void addInitialStateClauses(GroundPlanningProblem problem, AbstractSatSolver solver) {
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
	 * 
	 * @param problem
	 * @param solver
	 * @param step
	 */
	protected void addTransitionalClauses(GroundPlanningProblem problem, AbstractSatSolver solver, int step) {
		// actions imply their effects
		for (Action a : problem.getActions()) {
			int actionSatId = getActionSatVariable(a.getName(), step);
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getEffectsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, step + 1);
					solver.addClause(new int[] { -actionSatId, atomSatId });
				}
				if (a.getEffectsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, step + 1);
					solver.addClause(new int[] { -actionSatId, -atomSatId });
				}
			}
		}

		// frame axioms -- if an atom changes then there must be an action causing it
		for (int atomId = 0; atomId < problem.getNumAtoms(); atomId++) {
			// change of atom from true to false
			List<Action> supports = getSupportingActions(atomId, false);
			int[] p2n = new int[2 + supports.size()];
			p2n[0] = -getAtomSatVariable(atomId, step);
			p2n[1] = getAtomSatVariable(atomId, step + 1);
			int next = 2;
			for (Action a : supports) {
				p2n[next] = getActionSatVariable(a.getName(), step);
				next++;
			}
			solver.addClause(p2n);

			// change of atom from false to true
			supports = getSupportingActions(atomId, true);
			int[] n2p = new int[2 + getSupportingActions(atomId, true).size()];
			n2p[0] = getAtomSatVariable(atomId, step);
			n2p[1] = -getAtomSatVariable(atomId, step + 1);
			next = 2;
			for (Action a : supports) {
				n2p[next] = getActionSatVariable(a.getName(), step);
				next++;
			}
			solver.addClause(n2p);
		}
	}

	/**
	 * Clauses that hold in each step
	 * 
	 * @param problem
	 * @param solver
	 * @param step
	 */
	protected void addUniversalClauses(GroundPlanningProblem problem, AbstractSatSolver solver, int step) {
		// actions imply their preconditions
		for (Action a : problem.getActions()) {
			int actionSatId = getActionSatVariable(a.getName(), step);
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getPreconditionsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, step);
					solver.addClause(new int[] { -actionSatId, atomSatId });
				}
				if (a.getPreconditionsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, step);
					solver.addClause(new int[] { -actionSatId, -atomSatId });
				}
			}
		}
		// at least one action
		int[] clause = new int[problem.getActions().size()];
		for (int i = 0; i < problem.getActions().size(); i++) {
			int actionSatId = getActionSatVariable(problem.getActions().get(i).getName(), step);
			clause[i] = actionSatId;
		}
		solver.addClause(clause);

		// at most one action
		if (ignoreAtMostOneAction == false) {
			for (Action a1 : problem.getActions()) {
				for (Action a2 : problem.getActions()) {
					int actionSatId1 = getActionSatVariable(a1.getName(), step);
					int actionSatId2 = getActionSatVariable(a2.getName(), step);
					if (actionSatId1 < actionSatId2) {
						solver.addClause(new int[] { -actionSatId1, -actionSatId2 });
					}
				}
			}
		}
	}

}
