package edu.kit.aquaplanning.model.ground;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Plan implements Iterable<Action> {

	private List<Action> actions;
	
	public Plan() {
		this.actions = new LinkedList<>();
	}
	
	public void appendAtBack(Action action) {
		actions.add(action);
	}
	
	public void appendAtFront(Action action) {
		actions.add(0, action);
	}
	
	public int getLength() {
		return actions.size();
	}

	/**
	 * Calculates the total cost of the plan
	 * @return
	 * 		The cost of the plan
	 */
	public int getCost() {
		int sum = 0;
		for (Action a : actions) {
			sum += a.getCost();
		}
		return sum;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		int step = 0;
		for (Action action : actions) {
			builder.append(step + " : " + action.getCleanedName() + "\n");
			step++;
		}
		return builder.toString();
	}

	@Override
	public Iterator<Action> iterator() {
		
		return actions.iterator();
	}
	
	public Action get(int index) {
		return actions.get(index);
	}
	
	public Plan copy() {
		Plan newPlan = new Plan();
		for (Action a : actions) {
			newPlan.appendAtBack(a);
		}
		return newPlan;
	}
}
