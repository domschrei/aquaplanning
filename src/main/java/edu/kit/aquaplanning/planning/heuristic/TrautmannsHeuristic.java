package edu.kit.aquaplanning.planning.heuristic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.ConditionalEffect;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planning.datastructures.GroundRelaxedPlanningGraph;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;

/**
 * Fast-forward heuristic using a greedy planning strategy
 */
public class TrautmannsHeuristic extends Heuristic {

	private GroundPlanningProblem problem;

	public TrautmannsHeuristic(GroundPlanningProblem groundProblem) {
		this.problem = groundProblem;
	}

	@Override
	public int value(SearchNode node) {
		State state = node.state;

		// Is the goal already satisfied (in a relaxed definition)?
		if (problem.getGoal().isSatisfiedRelaxed(state)) {
			return 0;
		}

		// Traverse deletion-relaxed planning graph and collect states and applicable
		// actions
		GroundRelaxedPlanningGraph graph = new GroundRelaxedPlanningGraph(problem, state, problem.getActions());
		List<State> states = new LinkedList<State>();
		List<List<Action>> actions = new LinkedList<List<Action>>();
		while (graph.hasNextLayer()) {
			// Goal reached?
			if (problem.getGoal().isSatisfiedRelaxed(state)) {
				break;
			}

			states.add(0, state);
			List<Action> localActions = new LinkedList<>();
			for (Action action : problem.getActions()) {
				if (action.isApplicableRelaxed(state)) {
					localActions.add(action);
				}
			}
			actions.add(0, localActions);

			state = graph.computeNextLayer();
		}

		if (!problem.getGoal().isSatisfiedRelaxed(state)) {
			// Goals could not be reached: unsolvable from this state
			return Integer.MAX_VALUE;
		}

		// Compute relaxed plan
		List<Atom> goal = problem.getGoal().getPositiveAtoms();
		Iterator<State> itStates = states.iterator();
		Iterator<List<Action>> itActions = actions.iterator();
		int chosenActions = 0;
		while (itStates.hasNext()) {
			state = itStates.next();
			List<Action> localActions = itActions.next();

			// check which atoms are satisfied by the preceding state and which not
			List<Atom> satisfied = new LinkedList<>();
			List<Atom> unsatisfied = new LinkedList<>();
			for (Atom atom : goal) {
				if (state.holds(atom)) {
					satisfied.add(atom);
				} else {
					unsatisfied.add(atom);
				}
			}

			// Get actions that satisfy the unsatisfied atoms
			Map<Action, List<Atom>> satisfiers = new LinkedHashMap<>();
			for (Action action : localActions) {
				for (Atom atom : unsatisfied) {
					if (action.getEffectsPos().get(atom)) {
						if (!satisfiers.containsKey(action)) {
							satisfiers.put(action, new LinkedList<>());
						}
						satisfiers.get(action).add(atom);
					} else {
						for (ConditionalEffect eff : action.getConditionalEffects()) {
							if (eff.getEffectsPos().get(atom)) {
								if (!satisfiers.containsKey(action)) {
									satisfiers.put(action, new LinkedList<>());
								}
								satisfiers.get(action).add(atom);
								continue;
							}
						}
					}
				}
			}

			// Choose actions until all atoms are satisfied
			goal = new LinkedList<>(satisfied);
			while (!satisfiers.isEmpty()) {
				// Choose best action
				Action a = getBestAction(satisfiers);
				remove(satisfiers, satisfiers.get(a));
				for (int i = 0; i < problem.getNumAtoms(); i++) {
					if (a.getPreconditionsPos().get(i)) {
						Atom atom = new Atom(i, "", true);
						goal.add(atom);
					}
				}
				chosenActions++;
			}
		}
		return chosenActions;
	}

	/**
	 * Returns the action with the best rating.
	 * 
	 * @param satisfiers the map from actions to satisfied atoms
	 * @return the action with the best rating.
	 */
	protected Action getBestAction(Map<Action, List<Atom>> satisfiers) {
		Action bestAction = null;
		for (Action a : satisfiers.keySet()) {
			if (bestAction == null)
				bestAction = a;
			else {
				List<Atom> atoms1 = satisfiers.get(a);
				List<Atom> atoms2 = satisfiers.get(bestAction);
				if (compareActions(a, atoms1, bestAction, atoms2) > 0)
					bestAction = a;
			}
		}
		return bestAction;
	}

	/**
	 * Removes the given atoms from the given map
	 * 
	 * @param satisfiers the map from actions to satisfied atoms
	 * @param atoms      the list of atoms which shall be removed
	 */
	private void remove(Map<Action, List<Atom>> satisfiers, List<Atom> atoms) {
		atoms = new LinkedList<>(atoms);
		List<Action> delete = new LinkedList<>();
		for (Action action : satisfiers.keySet()) {
			for (Atom atom : atoms)
				satisfiers.get(action).remove(atom);
			if (satisfiers.get(action).isEmpty())
				delete.add(action);
		}
		for (Action action : delete)
			satisfiers.remove(action);
	}

	/**
	 * Compares the two given actions using the given satisfied atoms. Returns a
	 * negative integer, zero, or a positive integer as the first action is less
	 * than, equal to, or greater than the second. The action that is regarded as
	 * greater is the one that will be chosen first.
	 * 
	 * @param a1     the first action
	 * @param atoms1 the goal atoms which are satisfied by a1
	 * @param a2     the second action
	 * @param atoms2 the goal atoms which are satisfied by a2
	 * @return
	 */
	private int compareActions(Action a1, List<Atom> atoms1, Action a2, List<Atom> atoms2) {
		if (atoms1.size() > atoms2.size())
			return 1;
		else if (atoms1.size() < atoms2.size())
			return -1;
		if (a1.getPreconditionsPos().size() < a2.getPreconditionsPos().size())
			return 1;
		else if (a1.getPreconditionsPos().size() > a2.getPreconditionsPos().size())
			return -1;
		return 0;
	}
}
