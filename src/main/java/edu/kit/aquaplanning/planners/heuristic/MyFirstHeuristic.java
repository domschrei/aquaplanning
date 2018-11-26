
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
public class MyFirstHeuristic extends Heuristic {

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
        private List<Integer> satisfying;

        public ApplicableAction(Action action, List<Integer> toSatisfy) {
            this.action = action;
            value = 0;
            satisfying = new ArrayList<>();
            AtomSet atoms = action.getEffectsPos();
            for (int id : toSatisfy) {
                if (atoms.get(id)) {
                    satisfying.add(id);
                    value++;
                }
            }
        }

        public void removeGoal(int id) {
            if (satisfying.contains(id)) {
                satisfying.remove(id);
                value--;
            }
        }

        public Action getAction() {
            return action;
        }

        @Override
        public int compareTo(ApplicableAction other) {
            return value - other.value;
        }
    }

    public MyFirstHeuristic(GroundPlanningProblem groundProblem, PlanningProblem liftedProblem) {
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
        while (graph.hasNextLayer()) {
            State nextState = graph.computeNextLayer();
            states.addFirst(nextState);
            if (problem.getGoal().isSatisfiedRelaxed(nextState)) {
                break;
            }
            // Goal reached?
        }
        int planLength = 0;
        AtomSet goals = new AtomSet(problem.getGoal().getAtoms(), true);
        while (!states.isEmpty()) {
          System.out.println("Not finished yet");
            State currentState = states.removeFirst();
            List<ApplicableAction> possibleActions = new ArrayList<>();
            List<Integer> goalList = new ArrayList<Integer>();
            System.out.println("Atoms in goalList: " + goals.toString());
            for (int i = 0; i < problem.getNumAtoms(); i++) {
                if (goals.get(i) && !currentState.holds() {
                    goalList.add(i);
                    // System.out.println("Atom " + i + " is in GoalList");
                }
            }
            for (Action action : problem.getActions()) {
                if (action.isApplicableRelaxed(currentState)) {
                    System.out.println("Action " + action.getName() + " is applicable");
                    possibleActions.add(new ApplicableAction(action, goalList));
                }
            
            AtomSet newGoals = new AtomSet(new ArrayList<>());
            AtomSet effects = new AtomSet(new ArrayList<>());
            // while (!goals.all(effects)) {
            while (!effects.all(goals)) {
                ApplicableAction apply = Collections.max(possibleActions);
                System.out.println("Best action is " + apply.getAction().getName() + " with value " + apply.value);
                if (apply.value == 0) {
                    return Integer.MAX_VALUE;
                }
                possibleActions.remove(apply);
                newGoals.applyTrueAtoms(apply.getAction().getPreconditionsPos());
                System.out.println("Action has Preconditions: " + apply.getAction().getPreconditionsPos().toString());
                effects.applyTrueAtoms(apply.getAction().getEffectsPos());
                System.out.println("Action has Effects: " + apply.getAction().getEffectsPos().toString());
                for (int id : apply.satisfying) {
                  System.out.println("Atom " + id + " is now satisfied");
                    for (ApplicableAction pa : possibleActions) {
                        pa.removeGoal(id);
                    }
                }
                planLength++;
            }
            goals = newGoals;
            System.out.println("All goals satisfied");
        }
        System.out.println("Plan length: " + planLength);
        System.exit(0);
        return planLength;
    }
}
