package edu.kit.aquaplanning.sat.encoders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.sat.SatFormula;
import edu.kit.aquaplanning.sat.SymbolicReachabilityFormula;

public class ForeachEncoding implements PlanningToSatEncoder {

	private Map<String, Integer> actionIds;
	// supporting actions for positive atoms
	private Map<Integer, List<Action>> supportingActionsPositive;
	// supporting actions for negative atoms
	private Map<Integer, List<Action>> supportingActionsNegative;
	private List<Action> empty = new ArrayList<>();

	@Override
	public SymbolicReachabilityFormula encodeProblem(GroundPlanningProblem problem) {
		SymbolicReachabilityFormula srp = new SymbolicReachabilityFormula();
		initializeActionIdsAndSupports(problem);
		srp.goalConditions = getGoalAssumptions(problem);
		srp.initialConditions = getInitialStateClauses(problem);
		srp.transitionClauses = getTransitionalClauses(problem);
		srp.universalClauses = getUniversalClauses(problem);
		return srp;
	}

	@Override
	public ActionPlan decodePlan(GroundPlanningProblem problem, List<int[]> model) {
		initializeActionIdsAndSupports(problem);
		ActionPlan plan = new ActionPlan();
		for (int i = 0; i < model.size(); i++) {
			for (Action a : problem.getActions()) {
				if (model.get(i)[getActionSatVariable(a.getName(), 0)] > 0) {
					plan.appendAtBack(a);
				}
			}
		}
		return plan;
	}

	private List<Action> getSupportingActions(int atomid, boolean positive) {
		List<Action> result = positive ? supportingActionsPositive.get(atomid) : supportingActionsNegative.get(atomid);
		return result != null ? result : empty;
	}

	private void initializeActionIdsAndSupports(GroundPlanningProblem problem) {
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
		satVarsPerStep = nextId;
	}

	// number of Boolean variables in each sat solving step
	// it is the sum of number of actions and atoms
	private int satVarsPerStep;

	private int getActionSatVariable(String name, int step) {
		int aid = actionIds.get(name);
		return 1 + aid + step * (satVarsPerStep);
	}

	private int getAtomSatVariable(int atomId, int step) {
		return 1 + atomId + step * (satVarsPerStep);
	}

	/**
	 * Calculates the assumptions that represent that the goal is reached
	 * 
	 * @param problem
	 * @param step
	 * @return
	 */
	private int[] getGoalAssumptions(GroundPlanningProblem problem) {
		List<Atom> goalAtoms = problem.getGoal().getAtoms();
		int[] res = new int[goalAtoms.size()];
		for (int i = 0; i < goalAtoms.size(); i++) {
			res[i] = getAtomSatVariable(goalAtoms.get(i).getId(), 0);
		}
		return res;
	}

	/**
	 * Clauses that hold in the initial state (first step)
	 * 
	 * @param problem
	 * @param solver
	 */
	private int[] getInitialStateClauses(GroundPlanningProblem problem) {
		int[] res = new int[problem.getNumAtoms()];
		for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
			int atomSatId = getAtomSatVariable(atomid, 0);
			if (problem.getInitialState().getAtomSet().get(atomid)) {
				res[atomid] = atomSatId;
			} else {
				res[atomid] = -atomSatId;
			}
		}
		return res;
	}

	/**
	 * Clauses encoding the transition between step and step+1
	 * 
	 * @param problem
	 * @param solver
	 * @param step
	 */
	private SatFormula getTransitionalClauses(GroundPlanningProblem problem) {
		SatFormula fla = new SatFormula(satVarsPerStep);
		// actions imply their effects
		for (Action a : problem.getActions()) {
			int actionSatId = getActionSatVariable(a.getName(), 0);
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getEffectsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 1);
					fla.addClause(new int[] { -actionSatId, atomSatId });
				}
				if (a.getEffectsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 1);
					fla.addClause(new int[] { -actionSatId, -atomSatId });
				}
			}
		}

		// frame axioms -- if an atom changes then there must be an action causing it
		for (int atomId = 0; atomId < problem.getNumAtoms(); atomId++) {
			// change of atom from true to false
			List<Action> supports = getSupportingActions(atomId, false);
			int[] p2n = new int[2 + supports.size()];
			p2n[0] = -getAtomSatVariable(atomId, 0);
			p2n[1] = getAtomSatVariable(atomId, 1);
			int next = 2;
			for (Action a : supports) {
				p2n[next] = getActionSatVariable(a.getName(), 0);
				next++;
			}
			fla.addClause(p2n);

			// change of atom from false to true
			supports = getSupportingActions(atomId, true);
			int[] n2p = new int[2 + getSupportingActions(atomId, true).size()];
			n2p[0] = getAtomSatVariable(atomId, 0);
			n2p[1] = -getAtomSatVariable(atomId, 1);
			next = 2;
			for (Action a : supports) {
				n2p[next] = getActionSatVariable(a.getName(), 0);
				next++;
			}
			fla.addClause(n2p);
		}
		return fla;
	}

	/**
	 * Clauses that hold in each step
	 * 
	 * @param problem
	 * @param solver
	 * @param step
	 */
	private SatFormula getUniversalClauses(GroundPlanningProblem problem) {
		SatFormula fla = new SatFormula(satVarsPerStep);
		// actions imply their preconditions
		for (Action a : problem.getActions()) {
			int actionSatId = getActionSatVariable(a.getName(), 0);
			for (int atomid = 0; atomid < problem.getNumAtoms(); atomid++) {
				if (a.getPreconditionsPos().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 0);
					fla.addClause(new int[] { -actionSatId, atomSatId });
				}
				if (a.getPreconditionsNeg().get(atomid)) {
					int atomSatId = getAtomSatVariable(atomid, 0);
					fla.addClause(new int[] { -actionSatId, -atomSatId });
				}
			}
		}
		// at least one action
		// int[] clause = new int[problem.getActions().size()];
		// for (int i = 0; i < problem.getActions().size(); i++) {
		// int actionSatId = getActionSatVariable(problem.getActions().get(i).getName(),
		// 0);
		// clause[i] = actionSatId;
		// }
		// fla.addClause(clause);

		int[] actionIds = new int[problem.getActions().size()];
		// at most one action
		int aindex = 0;
		for (Action a1 : problem.getActions()) {
			actionIds[aindex] = getActionSatVariable(a1.getName(), 0);
			aindex++;
		}
		fla.addAtMostOneGroup(actionIds);
		return fla;
	}

}
