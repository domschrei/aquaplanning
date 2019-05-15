package edu.kit.aquaplanning.model.ground;

public class ActionPlan extends Plan<Action> {

	/**
	 * Calculates the total cost of the plan
	 * 
	 * @return The cost of the plan
	 */
	public int getCost() {
		int sum = 0;
		for (Action a : sequence) {
			sum += a.getCost();
		}
		return sum;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		int step = 0;
		for (Action action : sequence) {
			builder.append(step + " : " + action.getCleanedName() + "\n");
			step++;
		}
		return builder.toString();
	}
	
	public ActionPlan copy() {
		ActionPlan newPlan = new ActionPlan();
		for (Action a : sequence) {
			newPlan.appendAtBack(a);
		}
		return newPlan;
	}
}
