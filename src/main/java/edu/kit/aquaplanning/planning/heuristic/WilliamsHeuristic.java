
package edu.kit.aquaplanning.planning.heuristic;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Collections;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planning.datastructures.GroundRelaxedPlanningGraph;
import edu.kit.aquaplanning.planning.datastructures.SearchNode;
import edu.kit.aquaplanning.model.ground.AtomSet;

public class WilliamsHeuristic extends Heuristic {

	private GroundPlanningProblem problem;

	/**
	 * This class manages applicable actions. The value keeps track of the number of
	 * atoms which this action can satisfy that are not already satisfied.
	 */
	class ApplicableAction implements Comparable<ApplicableAction> {
		public int value;
		private Action action;
		private AtomSet satisfiable;

		public ApplicableAction(Action action, AtomSet toSatisfy) {
			this.action = action;
			AtomSet atoms = action.getEffectsPos();
			AtomSet notSatisfiable = new AtomSet(new ArrayList<Atom>());
			notSatisfiable.applyTrueAtoms(toSatisfy);
			notSatisfiable.applyTrueAtomsAsFalse(atoms);
			satisfiable = new AtomSet(new ArrayList<Atom>());
			satisfiable.applyTrueAtoms(toSatisfy);
			satisfiable.applyTrueAtomsAsFalse(notSatisfiable);
			value = satisfiable.numAtoms();
		}

		/**
		 * Refreshes the value after other actions have satisfied the effects of this
		 * action partially.
		 * 
		 * @param effects The effects that any taken action had
		 */
		public void removeEffects(AtomSet effects) {
			satisfiable.applyTrueAtomsAsFalse(effects);
			value = satisfiable.numAtoms();
		}

		/**
		 * @return the underlying action
		 */
		public Action getAction() {
			return action;
		}

		@Override
		public int compareTo(ApplicableAction other) {
			return value - other.value;
		}
	}

	/**
	 * Constructor for this heuristic
	 * 
	 * @param groundProblem the grounded planning problem
	 */
	public WilliamsHeuristic(GroundPlanningProblem groundProblem) {
		this.problem = groundProblem;
	}

	/**
	 * The game-changing heuristic which assigns a value for A* to each node to be
	 * searched.
	 */
	@Override
	public int value(SearchNode node) {

		State state = node.state;

		// Is the goal already satisfied (in a relaxed definition)?
		if (problem.getGoal().isSatisfiedRelaxed(state)) {
			return 0;
		}

		// Traverse deletion-relaxed planning graph
		GroundRelaxedPlanningGraph graph = new GroundRelaxedPlanningGraph(problem, state, problem.getActions());
		Deque<State> states = new ArrayDeque<State>();
		states.addFirst(state);
		while (graph.hasNextLayer()) {
			State nextState = graph.computeNextLayer();
			if (problem.getGoal().isSatisfiedRelaxed(nextState)) {
				break;
			}
			states.addFirst(nextState);
			// Goal reached?
		}
		int planLength = 0;
		AtomSet goals = new AtomSet(problem.getGoal().getAtoms(), true);
		while (!states.isEmpty()) {
			State currentState = states.removeFirst();
			List<ApplicableAction> possibleActions = new ArrayList<>();
			List<Integer> goalList = new ArrayList<Integer>();
			AtomSet toSatisfy = new AtomSet(new ArrayList<Atom>());
			for (int i = 0; i < problem.getNumAtoms(); i++) {
				if (goals.get(i) && (!currentState.holds(new Atom(i, "", true)))) {
					goalList.add(i);
					toSatisfy.set(new Atom(i, "", true));
				}
			}
			for (Action action : problem.getActions()) {
				if (action.isApplicableRelaxed(currentState)) {
					possibleActions.add(new ApplicableAction(action, toSatisfy));
				}
			}

			AtomSet preconditions = new AtomSet(new ArrayList<>());
			AtomSet effects = new AtomSet(new ArrayList<>());
			while (!effects.all(toSatisfy)) {
				// Find the best action
				if (possibleActions.size() == 0) {
					return Integer.MAX_VALUE;
				}
				ApplicableAction apply = Collections.max(possibleActions);
				// Even the best action would not satisfy any goal
				if (apply.value == 0) {
					return Integer.MAX_VALUE;
				}
				preconditions.applyTrueAtoms(apply.getAction().getPreconditionsPos());
				effects.applyTrueAtoms(apply.getAction().getEffectsPos());
				possibleActions.remove(apply);
				for (ApplicableAction pa : possibleActions) {
					pa.removeEffects(apply.getAction().getEffectsPos());
				}
				planLength++;
			}
			goals.applyTrueAtomsAsFalse(effects);
			goals.applyTrueAtoms(preconditions);
		}
		return planLength;
	}
}
