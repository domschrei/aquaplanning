package edu.kit.aquaplanning.planners.heuristic;

import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.planners.SearchNode;


/**
 * Hegemann's Heuristic.
 *
 * @author Patrick Hegemann
 */
public class HegemannsHeuristic extends Heuristic {

	// All information your heuristic should need
	// (together with the search node)
	private GroundPlanningProblem groundProblem;
	private PlanningProblem liftedProblem;
	
	// Define more members, if needed ...
	
	/**
	 * Constructor (please do not change the signature except for the class name)
	 */
	public HegemannsHeuristic(GroundPlanningProblem groundProblem, PlanningProblem liftedProblem) {
	    System.out.println("This is Hegemann's Heuristic calling");
		this.groundProblem = groundProblem;
		this.liftedProblem = liftedProblem;
	}
	
	/**
	 * Implement this method.
	 */
	@Override
	public int value(SearchNode node) {
		// This genius line avoids that the IDE complains about unused members.
		return groundProblem.hashCode() + liftedProblem.hashCode();
	}
}
