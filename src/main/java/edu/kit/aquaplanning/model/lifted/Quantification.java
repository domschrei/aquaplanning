package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Quantification extends AbstractCondition {

	public enum Quantifier {
		universal, existential;
	}
	private Quantifier quantifier;
	private List<Argument> variables;
	private List<AbstractCondition> conditions;
	
	public Quantification(Quantifier q) {

		super(ConditionType.quantification);
		this.quantifier = q;
		this.variables = new ArrayList<>();
		this.conditions = new ArrayList<>();
	}
	
	public void addVariable(Argument arg) {
		variables.add(arg);
	}
	
	public void addCondition(AbstractCondition condition) {
		conditions.add(condition);
	}
	
	public Quantifier getQuantifier() {
		return quantifier;
	}
	
	public List<Argument> getVariables() {
		return variables;
	}
	
	public List<AbstractCondition> getConditions() {
		return conditions;
	}
	
	@Override
	public Quantification getConditionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		
		Quantification q = new Quantification(quantifier);
		for (AbstractCondition c : conditions) {
			// TODO bind variable stronger to quantification than to action arguments,
			// not the other way round
			q.addCondition(c.getConditionBoundToArguments(refArgs, argValues));
		}
		for (Argument arg : variables) {
			q.addVariable(arg);
		}
		return q;
	}
	
	@Override
	public String toString() {
		
		String out = "";
		for (Argument var : variables) {
			out += (quantifier == Quantifier.existential ? "∃" : "∀") + var + " ";
		}
		out += ": { ";
		for (AbstractCondition cond : conditions) {
			out += cond + " ";
		}
		out += "}";
		return out;
	}
}
