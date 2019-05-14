package edu.kit.aquaplanning.grounding.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.condition.Condition;

/**
 * Static (immutable) structure for a certain lifted state,
 * with a couple of data structures accelerating checks of conditions.
 */
public class LiftedState {

	/**
	 * Maps a predicate name to a flat list of all conditions
	 * in the state with that predicate name.
	 */
	private Map<String, List<Condition>> conditionsPos;
	private Map<String, List<Condition>> conditionsNeg;
	/**
	 * Maps a predicate name to a set of all argument combinations
	 * which occur in the state with that predicate.
	 */
	private Map<String, ArgumentNode> conditionTreePos;
	private Map<String, ArgumentNode> conditionTreeNeg;
	/**
	 * Maps each problem constant to a unique positive integer.
	 */
	private Map<String, Integer> argumentIds;
	
	public LiftedState(Set<Condition> conditions) {
		
		this.conditionsPos = new HashMap<>();
		this.conditionsNeg = new HashMap<>();
		this.conditionTreePos = new HashMap<>();
		this.conditionTreeNeg = new HashMap<>();
		this.argumentIds = new HashMap<>();
		
		int argId = 1;
		for (Condition c : conditions) {
			Map<String, List<Condition>> stateConditions = c.isNegated() ? conditionsNeg : conditionsPos;
			Map<String, ArgumentNode> conditionTree = c.isNegated() ? conditionTreeNeg : conditionTreePos;
			
			// Add condition to correct flat conditions list 
			String predicateName = c.getPredicate().getName();
			if (!stateConditions.containsKey(predicateName)) {
				stateConditions.put(predicateName, new ArrayList<>());
			}
			stateConditions.get(predicateName).add(c);
			
			// Set ID of each argument
			for (Argument arg : c.getArguments()) {
				if (!argumentIds.containsKey(arg.getName())) {
					argumentIds.put(arg.getName(), argId++);
				}
			}
			
			// Add condition arguments to correct set structure
			if (!conditionTree.containsKey(predicateName)) {
				conditionTree.put(predicateName, new ArgumentNode(argumentIds));
			}
			conditionTree.get(predicateName).add(c.getArguments());
		}
	}
	
	/**
	 * Returns all predicates' names for which at least 
	 * one condition occurs in the state.
	 */
	public Set<String> getOccurringPredicates(boolean negated) {
		return (negated ? conditionTreeNeg.keySet() : conditionTreePos.keySet());
	}
	
	/**
	 * Returns all conditions in the state of the provided
	 * predicate name.
	 */
	public List<Condition> getConditions(String p, boolean negated) {
		Map<String, List<Condition>> conditions = negated ? conditionsNeg : conditionsPos;
		if (!conditions.containsKey(p)) {
			conditions.put(p, new ArrayList<>());
		}
		return conditions.get(p);
	}
	
	/**
	 * Checks if a given *positive* condition is contained 
	 * in the state. Checks equality conditions syntactically.
	 */
	public boolean holds(Condition condition) {
		
		if (condition.getPredicate().getName().equals("=")) {
			// Equality predicate
			String arg1 = condition.getArguments().get(0).getName();
			String arg2 = condition.getArguments().get(1).getName();
			return condition.isNegated() != arg1.equals(arg2);
		}
		// Normal predicate
		
		if (condition.isNegated()) {
			// Negative condition
			ArgumentNode node = conditionTreeNeg.get(condition.getPredicate().getName());
			if (node != null && node.contains(condition.getArguments())) {
				return true;
			} else {
				// If neither tree contains the condition, a negative condition is assumed to hold
				node = conditionTreePos.get(condition.getPredicate().getName());
				return (node == null || !node.contains(condition.getArguments()));
			}
		}
		// Positive condition
		return conditionTreePos.getOrDefault(condition.getPredicate().getName(), new ArgumentNode(argumentIds))
				.contains(condition.getArguments());
	}
	
	@Override
	public String toString() {
		String out = "";
		for (List<Condition> conds : conditionsPos.values()) {
			out += conds.toString();
		}
		for (List<Condition> conds : conditionsNeg.values()) {
			out += "¬" + conds.toString();
		}
		return out;
	}
	
	public Map<String, Integer> getArgumentIds() {
		return argumentIds;
	}
}
