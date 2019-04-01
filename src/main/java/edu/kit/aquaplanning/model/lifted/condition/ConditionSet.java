package edu.kit.aquaplanning.model.lifted.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import edu.kit.aquaplanning.model.lifted.Argument;

public class ConditionSet extends AbstractCondition {

	private List<AbstractCondition> conditions;

	public ConditionSet(ConditionType type) {
		super(type);
		conditionType = type;
		this.conditions = new ArrayList<AbstractCondition>();
	}

	public void add(AbstractCondition c) {
		this.conditions.add(c);
	}
	
	public List<AbstractCondition> getConditions() {
		return conditions;
	}
	
	public boolean isDisjunctive() {
		return getConditionType() == ConditionType.disjunction;
	}
	
	@Override
	public AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		ConditionSet c = new ConditionSet(conditionType);
		for (AbstractCondition subCond : conditions) {
			c.add(subCond.getConditionBoundToArguments(refArgs, argValues));
		}
		return c;
	}
	
	@Override
	public AbstractCondition simplify(boolean negated) {
		
		ConditionType type = null;
		if (negated) {
			// Swap junctor (de Morgan rule)
			type = (conditionType == ConditionType.disjunction ? 
					ConditionType.conjunction : ConditionType.disjunction);
		} else {
			type = this.conditionType;
		}
		ConditionSet c = new ConditionSet(type);
		
		// Propagate negations down to children, and simplify them too
		for (AbstractCondition child : conditions) {
			child = child.simplify(negated);
			
			// Does the child have the same junctor as its parent?
			if (child.getConditionType() == type) {
				// Simplify away nested AND/OR into a single set
				for (AbstractCondition grandchild : ((ConditionSet) child).getConditions()) {
					// grandchild has already been simplified
					c.add(grandchild);
				}
			} else {			
				c.add(child);
			}
		}
		
		return c;
	}
	
	@Override
	public AbstractCondition getDNF() {
		
		// First, bring all children into DNF
		List<AbstractCondition> dnfChildren = new ArrayList<>();
		for (AbstractCondition child : conditions) {
			dnfChildren.add(child.getDNF());
		}
		
		if (conditionType == ConditionType.disjunction) {
			// Disjunction: build full DNF by putting everything into one big disjunction
			
			ConditionSet newDisjunction = new ConditionSet(ConditionType.disjunction);
			for (AbstractCondition dnfChild : dnfChildren) {
				if (dnfChild.getConditionType() == ConditionType.disjunction) {
					// Add each "grandchild", discarding the child
					for (AbstractCondition grandChild : ((ConditionSet) dnfChild).getConditions()) {
						newDisjunction.add(grandChild);
					}
				} else {
					// Just add the child
					newDisjunction.add(dnfChild);
				}
			}
			return newDisjunction;
		
		} else if (conditionType == ConditionType.conjunction) {
			// Conjunction: apply distributive rule
			
			for (AbstractCondition child : dnfChildren) {
				if (child.getConditionType() == ConditionType.disjunction) {
					
					// Top-level conjunction, a child is a disjunction
					
					ConditionSet newDisjunction = new ConditionSet(ConditionType.disjunction);
					for (AbstractCondition disjunctionChild : ((ConditionSet) child).getConditions()) {
						ConditionSet newConjunction = new ConditionSet(ConditionType.conjunction);
						
						// Does the child have the same junctor as its parent?
						if (disjunctionChild.getConditionType() == ConditionType.conjunction) {
							// Simplify away nested AND into a single set
							for (AbstractCondition grandchild : ((ConditionSet) disjunctionChild).getConditions()) {
								newConjunction.add(grandchild);
							}
						} else {			
							newConjunction.add(disjunctionChild);
						}
						
						for (AbstractCondition c : dnfChildren) {
							if (!c.equals(child)) {
								newConjunction.add(c);
							}
						}
						newDisjunction.add(newConjunction);
					}
					return newDisjunction.getDNF();
				}
			}
		}
		
		// No possible transformations
		ConditionSet c = new ConditionSet(conditionType);
		for (AbstractCondition child : dnfChildren) {
			c.add(child);
		}
		return c;
	}
	
	@Override
	public String toString() {
		String out = isDisjunctive() ? "OR { " : "AND { ";
		for (AbstractCondition c : conditions) {
			out += c + " ";
		}
		return out + "}";
	}
	
	@Override
	public ConditionSet copy() {
		ConditionSet set = new ConditionSet(conditionType);
		for (AbstractCondition c : conditions)
			set.add(c);
		return set;
	}

	@Override
	public AbstractCondition traverse(Function<AbstractCondition, AbstractCondition> map, int recurseMode) {
		
		ConditionSet result;
		
		// Apply the inner function, if tail recursion is done
		if (recurseMode == AbstractCondition.RECURSE_TAIL) {
			result = (ConditionSet) map.apply(this);
		} else {
			result = copy();
		}
		
		// Recurse
		List<AbstractCondition> newConditions = new ArrayList<>();
		for (AbstractCondition cond : result.getConditions()) {
			AbstractCondition child = cond.traverse(map, recurseMode);
			if (child != null)
				newConditions.add(child);
		}
		result.conditions = newConditions;
		
		// Apply the inner function, if head recursion is done
		if (recurseMode == AbstractCondition.RECURSE_HEAD) {
			return map.apply(result);
		}
		
		return result;
	}
}
