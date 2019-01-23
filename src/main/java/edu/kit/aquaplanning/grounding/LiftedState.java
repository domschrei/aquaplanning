package edu.kit.aquaplanning.grounding;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.Predicate;

public class LiftedState {

	private Map<Predicate, Set<Condition>> conditions;
	
	public LiftedState(Set<Condition> conditions) {
		this.conditions = new HashMap<>();
		for (Condition c : conditions) {
			Predicate p = c.getPredicate();
			if (!this.conditions.containsKey(p)) {
				this.conditions.put(p, new HashSet<>());
			}
			this.conditions.get(p).add(c);
		}		
	}
	
	public LiftedState(List<Condition> conditions) {
		this.conditions = new HashMap<>();
		for (Condition c : conditions) {
			Predicate p = c.getPredicate();
			if (!this.conditions.containsKey(p)) {
				this.conditions.put(p, new HashSet<>());
			}
			this.conditions.get(p).add(c);
		}
	}
	
	public Set<Predicate> getOccurringPredicates() {
		return conditions.keySet();
	}
	
	public Set<Condition> getConditions(Predicate p) {
		Set<Condition> conditions = this.conditions.get(p);
		return (conditions == null ? new HashSet<>() : conditions);
	}
	
	public Collection<Set<Condition>> getConditions() {
		return conditions.values();
	}
	
	@Override
	public String toString() {
		String out = "";
		for (Set<Condition> conds : conditions.values()) {
			out += conds.toString();
		}
		return out;
	}
}
