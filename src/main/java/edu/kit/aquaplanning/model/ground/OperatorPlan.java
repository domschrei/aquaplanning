package edu.kit.aquaplanning.model.ground;

import edu.kit.aquaplanning.model.lifted.Operator;

public class OperatorPlan extends Plan<Operator> {

	/**
	 * Calculates the total cost of the plan
	 * 
	 * @return The cost of the plan
	 */
	public int getCost() {
		int sum = 0;
		for (Operator op : sequence) {
			sum += op.getCost();
		}
		return sum;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		int step = 0;
		for (Operator op : sequence) {
			builder.append(step + " : " + op.toActionString() + "\n");
			step++;
		}
		return builder.toString();
	}

	public OperatorPlan copy() {
		OperatorPlan newPlan = new OperatorPlan();
		for (Operator op : sequence) {
			newPlan.appendAtBack(op);
		}
		return newPlan;
	}
}
