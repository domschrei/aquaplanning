package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Quantification extends AbstractCondition {

	public enum Quantifier {
		universal, existential;
	}
	private Quantifier quantifier;
	private List<Argument> variables;
	private List<Condition> conditions;
	
	public Quantification(Quantifier q) {

		super(ConditionType.quantification);
		this.quantifier = q;
		this.variables = new ArrayList<>();
		this.conditions = new ArrayList<>();
	}
	
	public void addVariable(Argument arg) {
		variables.add(arg);
	}
	
	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public Quantifier getQuantifier() {
		return quantifier;
	}
	
	public List<Argument> getVariables() {
		return variables;
	}
	
	public List<Condition> getConditions() {
		return conditions;
	}
	
	@Override
	public String toString() {
		
		String out = "";
		for (Argument var : variables) {
			out += (quantifier == Quantifier.existential ? "∃" : "∀") + var + " ";
		}
		out += ": { ";
		for (Condition cond : conditions) {
			out += cond + " ";
		}
		out += "}";
		return out;
	}
}
