package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;

/**
 * Static (immutable) structure for a certain lifted state,
 * with a couple of data structures accelerating checks of conditions.
 */
public class LiftedState {

	/**
	 * Maps a predicate name to a flat list of all conditions
	 * in the state with that predicate name.
	 */
	private Map<String, List<Condition>> conditions;
	/**
	 * Maps a predicate name to a set of all argument combinations
	 * which occur in the state with that predicate.
	 */
	private Map<String, ArgumentNode> conditionTree;
	/**
	 * Maps each problem constant to a unique positive integer.
	 */
	private Map<String, Integer> argumentIds;
	
	public LiftedState(Set<Condition> conditions) {
		this.conditions = new HashMap<>();
		this.conditionTree = new HashMap<>();
		this.argumentIds = new HashMap<>();
		
		int argId = 1;
		for (Condition c : conditions) {
			
			// Add condition to correct flat conditions list 
			String predicateName = c.getPredicate().getName();
			if (!this.conditions.containsKey(predicateName)) {
				this.conditions.put(predicateName, new ArrayList<>());
			}
			this.conditions.get(predicateName).add(c);
			
			// Set ID of each argument
			for (Argument arg : c.getArguments()) {
				if (!argumentIds.containsKey(arg.getName())) {
					argumentIds.put(arg.getName(), argId++);
				}
			}
			
			// Add condition arguments to correct set structure
			if (!this.conditionTree.containsKey(predicateName)) {
				this.conditionTree.put(predicateName, new ArgumentNode(argumentIds));
			}
			this.conditionTree.get(predicateName).add(c.getArguments());
		}
	}
	
	public Set<String> getOccurringPredicates() {
		return conditionTree.keySet();
	}
	
	public List<Condition> getConditions(String p) {
		if (!this.conditions.containsKey(p)) {
			this.conditions.put(p, new ArrayList<>());
		}
		return this.conditions.get(p);
	}
	
	public boolean holds(Condition condition) {
		if (condition.getPredicate().getName().equals("=")) {
			// Equality predicate
			String arg1 = condition.getArguments().get(0).getName();
			String arg2 = condition.getArguments().get(1).getName();
			return arg1.equals(arg2);
		}
		// Normal predicate
		return conditionTree.getOrDefault(condition.getPredicate().getName(), new ArgumentNode(argumentIds))
				.contains(condition.getArguments());
	}
	
	@Override
	public String toString() {
		String out = "";
		for (List<Condition> conds : conditions.values()) {
			out += conds.toString();
		}
		return out;
	}
}
