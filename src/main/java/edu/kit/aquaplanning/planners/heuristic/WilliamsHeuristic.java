
package edu.kit.aquaplanning.planners.heuristic;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Collections;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planners.SearchNode;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.planners.GroundRelaxedPlanningGraph;
import edu.kit.aquaplanning.model.ground.AtomSet;

/**
 * Rename this class: [Lastname]sHeuristic .
 */
public class WilliamsHeuristic extends Heuristic {

    // All information your heuristic should need
    // (together with the search node)
    private GroundPlanningProblem problem;

    // Define more members, if needed ...

    /**
     * Constructor (please do not change the signature except for the class name)
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

        public void removeEffects(AtomSet effects) {
            satisfiable.applyTrueAtomsAsFalse(effects);
            value = satisfiable.numAtoms();
        }

        public Action getAction() {
            return action;
        }

        @Override
        public int compareTo(ApplicableAction other) {
            return value - other.value;
        }
    }

    public WilliamsHeuristic(GroundPlanningProblem groundProblem, PlanningProblem liftedProblem) {
        this.problem = groundProblem;
    }

    /**
     * Implement this method.
     */
    @Override
    public int value(SearchNode node) {

        State state = node.state;

        // Is the goal already satisfied (in a relaxed definition)?
        if (problem.getGoal().isSatisfiedRelaxed(state)) {
            return 0;
        }

        // Traverse deletion-relaxed planning graph
        GroundRelaxedPlanningGraph graph = new GroundRelaxedPlanningGraph(state, problem.getActions());
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
        // System.out.println("size: " + states.size());
        while (!states.isEmpty()) {
            // System.out.println("Not finished yet");
            State currentState = states.removeFirst();
            // System.out.println(currentState.toString());
            List<ApplicableAction> possibleActions = new ArrayList<>();
            List<Integer> goalList = new ArrayList<Integer>();
            AtomSet toSatisfy = new AtomSet(new ArrayList<Atom>());
            // System.out.println("New new goals: " + goals.toString());
            for (int i = 0; i < problem.getNumAtoms(); i++) {
                if (goals.get(i) && (!currentState.holds(new Atom(i, "", true)))) {
                    goalList.add(i);
                    toSatisfy.set(new Atom(i, "", true));
                }
            }
            // System.out.println("Goals " + toSatisfy + " not yet satisfied");
            for (Action action : problem.getActions()) {
                if (action.isApplicableRelaxed(currentState)) {
                    // System.out.println("Action " + action.getName() + " is applicable");
                    possibleActions.add(new ApplicableAction(action, toSatisfy));
                }
            }

            AtomSet preconditions = new AtomSet(new ArrayList<>());
            AtomSet effects = new AtomSet(new ArrayList<>());
            // while (!goals.all(effects)) {
            while (!effects.all(toSatisfy)) {
                if (possibleActions.size() == 0) {
                    return Integer.MAX_VALUE;
                }
                ApplicableAction apply = Collections.max(possibleActions);
                // System.out.println("Best action is " + apply.getAction().getName() + " with value " + apply.value);
                if (apply.value == 0) {
                    return Integer.MAX_VALUE;
                }
                preconditions.applyTrueAtoms(apply.getAction().getPreconditionsPos());
                // System.out.println("Action has Preconditions: " + apply.getAction().getPreconditionsPos().toString());
                effects.applyTrueAtoms(apply.getAction().getEffectsPos());
                // System.out.println("Action has Effects: " + apply.getAction().getEffectsPos().toString());
                possibleActions.remove(apply);
                // System.out.println("Atom " + id + " is now satisfied");
                for (ApplicableAction pa : possibleActions) {
                    pa.removeEffects(apply.getAction().getEffectsPos());
                }
                planLength++;
            }
            // System.out.println("New goals: " + preconditions.toString());
            goals.applyTrueAtomsAsFalse(effects);
            goals.applyTrueAtoms(preconditions);
            // System.out.println("All goals satisfied");
        }
        // System.out.println("Plan length: " + planLength);
        // System.exit(0);
        return planLength;
    }
}
