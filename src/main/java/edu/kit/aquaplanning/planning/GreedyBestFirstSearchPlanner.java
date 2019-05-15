package edu.kit.aquaplanning.planning;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planning.datastructures.FullActionIndex;
import edu.kit.aquaplanning.util.Logger;

/**
 * A simple forward best-first-search planner. Does not create parallel plans.
 * Creates very long plans which should be shortened by some post-processing.
 */
public class GreedyBestFirstSearchPlanner extends GroundPlanner {

	private Random rnd;

	public GreedyBestFirstSearchPlanner(Configuration config) {
		super(config);
		rnd = new Random(config.seed);
	}

	@Override
	public ActionPlan findPlan(GroundPlanningProblem problem) {
		startSearch();
		ArrayDeque<State> stateHistory = new ArrayDeque<>();
		ArrayDeque<Action> plan = new ArrayDeque<>();
		// visitedStates = new MoveToFrontHashTable(64*1024*1024);
		HashSet<AtomSet> visitedStates = new HashSet<>();
		FullActionIndex aindex = new FullActionIndex(problem);

		State state = new State(problem.getInitialState());
		Goal goal = problem.getGoal();
		Collection<Action> applicableActions = aindex.getApplicableActions(state);
		int iterations = 0;

		while (!goal.isSatisfied(state) && withinComputationalBounds(iterations)) {
			iterations++;
			visitedStates.add(state.getAtomSet());
			Action best = null;
			int bestValue = -1;

			for (Action a : applicableActions) {
				State newState = a.apply(state);
				if (visitedStates.contains(newState.getAtomSet())) {
					continue;
				} else {
					int value = calculateManhattan(newState, goal);
					if (value > bestValue) {
						bestValue = value;
						best = a;
					}
				}
			}

			if (best == null) {
				if (plan.size() == 0) {
					// Plan does not exist
					return null;
				}
				// backtracking
				plan.removeLast();
				State newState = stateHistory.pollLast();
				updateApplicableActionsChanges(applicableActions, state, newState, aindex);
				state = newState;
			} else {
				// select the best action
				plan.addLast(best);
				stateHistory.addLast(state);
				State newState = best.apply(state);
				updateApplicableActionsChanges(applicableActions, state, newState, aindex);
				state = newState;
			}
		}

		if (goal.isSatisfied(state)) {
			// make the plan
			ActionPlan finalplan = new ActionPlan();
			for (Action a : plan) {
				finalplan.appendAtBack(a);
			}
			Logger.log(Logger.INFO,
					String.format(
							"successfull greedy search, visited %d states, did %d iterations, found plan of length %d",
							visitedStates.size(), iterations, plan.size()));
			return finalplan;
		} else {
			Logger.log(Logger.INFO, String.format("failed greedy search, visited %d states, did %d iterations",
					visitedStates.size(), iterations));
			return null;
		}
	}

	private void updateApplicableActionsChanges(Collection<Action> actions, State oldState, State newState,
			FullActionIndex aindex) {
		// first remove actions that are no more applicable
		Iterator<Action> iter = actions.iterator();
		while (iter.hasNext()) {
			Action a = iter.next();
			if (!a.isApplicable(newState)) {
				iter.remove();
			}
		}
		// add new applicable actions for changed state variables
		if (aindex.getNoPrecondActions() != null) {
			for (Action a : aindex.getNoPrecondActions()) {
				if (a.isApplicable(newState)) {
					actions.add(a);
				}
			}
		}
		// Check and debug
		AtomSet changes = oldState.getAtomSet().xor(newState.getAtomSet());
		int changeId = changes.getFirstTrueAtom();
		while (changeId != -1) {
			int precondIndex = newState.getAtomSet().get(changeId) ? changeId + 1 : -changeId - 1;
			List<Action> cands = aindex.getActionsWithPrecondition(precondIndex);
			if (cands != null) {
				for (Action a : cands) {
					if (a.isApplicable(newState)) {
						actions.add(a);
					}
				}
			}
			changeId = changes.getNextTrueAtom(changeId + 1);
		}
	}

	private int calculateManhattan(State state, Goal goal) {
		int satisfiedGoals = 0;
		for (Atom g : goal.getAtoms()) {
			if (state.holds(g)) {
				satisfiedGoals++;
			}
		}
		return 10 * (satisfiedGoals) + rnd.nextInt(10);
	}

}
