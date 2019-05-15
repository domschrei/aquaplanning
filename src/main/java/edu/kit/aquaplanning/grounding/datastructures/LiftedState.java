package edu.kit.aquaplanning.grounding.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.condition.Condition;

/**
 * Represents a state in a dual representation: All positive atoms AND all negative atoms
 * are both stored explicitly in the form of argument trees for each predicate name.
 * As such, a LiftedState instance may also represent a super-state where some atoms are
 * both false AND true. If an atom occurs neither positive nor negated, it is assumed
 * to be false (closed-world assumption).
 */
public class LiftedState {

	/**
	 * Maps a predicate name to a flat list of all conditions in the state with that
	 * predicate name.
	 */
	private Map<String, List<Condition>> conditionsPos;
	private Map<String, List<Condition>> conditionsNeg;
	/**
	 * Maps a predicate name to a set of all argument combinations which occur in
	 * the state with that predicate.
	 */
	private Map<String, ArgumentNode> conditionTreePos;
	private Map<String, ArgumentNode> conditionTreeNeg;
	/**
	 * Maps each problem constant to a unique positive integer.
	 */
	private Map<String, Integer> argumentIds;
	
	private boolean modified = false;

	/**
	 * @param allConstants All of the constants occurring in the surrounding planning problem
	 * @param conditions The set of conditions contained in the initial state 
	 * (may contain positive and negated conditions)
	 */
	public LiftedState(List<Argument> allConstants, Collection<Condition> conditions) {
		
		initArgumentIds(allConstants);
		init(conditions);
	}
	
	/**
	 * @param conditions The set of conditions contained in the initial state 
	 * (may contain positive and negated conditions)
	 */
	public LiftedState(Collection<Condition> conditions) {

		// Set ID of each argument occurring in the conditions
		this.argumentIds = new HashMap<>();
		int argId = 1;
		for (Condition c : conditions) {
			for (Argument arg : c.getArguments()) {
				if (!argumentIds.containsKey(arg.getName())) {
					argumentIds.put(arg.getName(), argId++);
				}
			}
		}
		
		init(conditions);
	}
	
	private void initArgumentIds(List<Argument> constants) {
		
		this.argumentIds = new HashMap<>();

		int argId = 1;
		// Set ID of each argument
		for (Argument arg : constants) {
			if (!argumentIds.containsKey(arg.getName())) {
				argumentIds.put(arg.getName(), argId++);
			}
		}
	}
	
	private void init(Collection<Condition> conditions) {
		
		this.conditionsPos = new HashMap<>();
		this.conditionsNeg = new HashMap<>();
		this.conditionTreePos = new HashMap<>();
		this.conditionTreeNeg = new HashMap<>();
		
		for (Condition c : conditions) {
			Map<String, List<Condition>> stateConditions = c.isNegated() ? conditionsNeg : conditionsPos;
			Map<String, ArgumentNode> conditionTree = c.isNegated() ? conditionTreeNeg : conditionTreePos;

			// Add condition to correct flat conditions list
			String predicateName = c.getPredicate().getName();
			if (!stateConditions.containsKey(predicateName)) {
				stateConditions.put(predicateName, new ArrayList<>());
			}
			stateConditions.get(predicateName).add(c);

			// Add condition arguments to correct set structure
			if (!conditionTree.containsKey(predicateName)) {
				conditionTree.put(predicateName, new ArgumentNode(argumentIds));
			}
			conditionTree.get(predicateName).add(c.getArguments());
		}
	}

	/**
	 * Returns all predicates' names for which at least one condition occurs in the
	 * state.
	 */
	public Set<String> getOccurringPredicates(boolean negated) {
		return (negated ? conditionTreeNeg.keySet() : conditionTreePos.keySet());
	}

	/**
	 * Returns all conditions in the state of the provided predicate name.
	 * <b>Warning</b>: This method is only valid on immutable / constant LiftedState instances.
	 * As soon as the state is altered by adding or removing conditions, this method
	 * cannot be called any more and will throw an exception.
	 */
	public List<Condition> getConditions(String p, boolean negated) {
		
		if (modified)
			throw new RuntimeException("The LiftedState instance has been modified; "
					+ "the called method is only available on constant LiftedState objects.");
		
		Map<String, List<Condition>> conditions = negated ? conditionsNeg : conditionsPos;
		if (!conditions.containsKey(p)) {
			conditions.put(p, new ArrayList<>());
		}
		return conditions.get(p);
	}

	/**
	 * Checks if a given *positive* condition is contained in the state. Checks
	 * equality conditions syntactically.
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
				// If neither tree contains the condition, a negative condition is assumed to
				// hold
				node = conditionTreePos.get(condition.getPredicate().getName());
				return (node == null || !node.contains(condition.getArguments()));
			}
		}
		// Positive condition
		return conditionTreePos.getOrDefault(condition.getPredicate().getName(), new ArgumentNode(argumentIds))
				.contains(condition.getArguments());
	}

	/**
	 * Adds a (positive or negated) condition to the state 
	 * and removes its complementary condition as necessary.
	 */
	public void add(Condition condition) {
		modified = true;
		Map<String, ArgumentNode> tree = (condition.isNegated() ? conditionTreeNeg : conditionTreePos);
		if (!tree.containsKey(condition.getPredicate().getName()))
			tree.put(condition.getPredicate().getName(), new ArgumentNode(argumentIds));
		tree.get(condition.getPredicate().getName()).add(condition.getArguments());
		
		condition = condition.copy();
		condition.setNegated(!condition.isNegated());
		remove(condition);
	}
	private void remove(Condition condition) {
		modified = true;
		ArgumentNode node = (condition.isNegated() ? conditionTreeNeg : conditionTreePos).get(condition.getPredicate().getName());
		if (node != null) 
			node.remove(condition.getArguments());
	}
	
	/**
	 * Note: Depending on whether the state object has been modified by add() calls,
	 * the textual representation will vary.
	 */
	@Override
	public String toString() {
		String out = "";
		if (modified) {
			out += "POSITIVE: " + conditionTreePos.toString() + " ";
			out += "NEGATIVE: " + conditionTreeNeg.toString();
		} else {			
			for (List<Condition> conds : conditionsPos.values()) {
				out += conds.toString();
			}
			for (List<Condition> conds : conditionsNeg.values()) {
				out += "¬" + conds.toString();
			}
		}
		return out;
	}

	public Map<String, Integer> getArgumentIds() {
		return argumentIds;
	}
}
