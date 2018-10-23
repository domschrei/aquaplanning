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
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		int step = 1;
		for (Action action : actions) {
			builder.append(step + ":\t" + action.getName() + "\n");
			step++;
		}
		return builder.toString();
	}

	@Override
	public Iterator<Action> iterator() {
		
		return actions.iterator();
	}
}
