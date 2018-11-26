
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
      private List<Atom> satisfying;
      public ApplicableAction(Action action, List<Atom> toSatisfy) {
          this.action = action;
          value = 0;
          satisfying = new ArrayList<Atom>();
          for (Atom a : action.getEffects()) {
            if (a.getValue() && toSatisfy.contains(a)) {
              satisfying.add(a);
              value++;
            }
          }
      }

      public void removeGoal(Atom atom) {
        if (satisfying.contains(atom)) {
          satisfying.remove(atom);
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
        int depth = 1;
        while (graph.hasNextLayer()) {
            State nextState = graph.computeNextLayer();
            if (problem.getGoal().isSatisfiedRelaxed(nextState)) {
                break;
            }
            states.addFirst(nextState);
            // Goal reached?

            depth++;
        }
        Plan plan = new Plan();
        List<Atom> goals = problem.getGoal().getAtoms();
        goals.removeIf(a -> !a.getValue());
        while (!states.isEmpty()) {
            State currentState = states.removeFirst();
            List<ApplicableAction> possibleActions = new ArrayList<>();
            for (Action action : problem.getActions()) {
                if (action.isApplicableRelaxed(currentState)) {
                  possibleActions.add(new ApplicableAction(action, goals));
                }
            }
            List<Atom> newGoals = new ArrayList<>();
            while (!goals.isEmpty()) {
              ApplicableAction apply = Collections.max(possibleActions);
              if (apply.value == 0) {
                return Integer.MAX_VALUE;
              }
              possibleActions.remove(apply);
              for (Atom sat : apply.satisfying) {
                goals.remove(sat);
                for (ApplicableAction pa: possibleActions) {
                  pa.removeGoal(sat);
                }
              }
              plan.appendAtFront(apply.action);
              for (Atom pre : apply.action.getEffects()) {
                if (pre.getValue() && !newGoals.contains(pre)) {
                  newGoals.add(pre);
                }
              }
            }
            goals = newGoals;
        }

        // Goals could not be reached: unsolvable from this state
        return plan.getLength();
    }
}
