package edu.kit.aquaplanning.model.lifted;

import java.util.List;

public abstract class AbstractCondition {

	public enum ConditionType {
		atomic, consequential, quantification, conjunction, 
		disjunction, negation, implication, numericPrecondition, numericEffect;
	}
	
	protected ConditionType conditionType;
	
	public AbstractCondition(ConditionType conditionType) {
		this.conditionType = conditionType;
	}
	
	public ConditionType getConditionType() {
		return conditionType;
	}
	
	/**
	 * Given a list of variable arguments and a corresponding list 
	 * of constant arguments, replaces all occurrences of the 
	 * concerned variables with the corresponding constant.
	 * The original condition remains unchanged; a copy is returned.
	 */
	public abstract AbstractCondition getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues);
	
	/**
	 * Returns a copy of this condition which is logically equivalent 
	 * and where negations only occur on an atomic level. Implications
	 * are converted into disjunctions, and simple nested conjunctions
	 * or disjunctions are unified.<br/>
	 * Before this conversion, the condition should be quantification-free.
	 */
	public abstract AbstractCondition simplify(boolean negated);
	
	/**
	 * Returns a copy of this condition which is logically equivalent 
	 * and in Disjunctive Normal Form (DNF).<br/>
	 * Before this conversion, the condition should be quantification-free
	 * and simplified (by calling simplify(false)).
	 */
	public abstract AbstractCondition getDNF();
	
	public abstract AbstractCondition copy();
}
