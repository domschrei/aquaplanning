package edu.kit.aquaplanning.model.lifted;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PlanningProblem {

	private String domainName;
	private String problemName;
	private Map<String, Type> types;
	private List<Argument> constants; // both from domain and problem
	private Map<String, Predicate> predicates;
	private Map<String, DerivedPredicate> derivedPredicates;
	private List<Operator> operators;
	private List<Condition> initialState;
	private List<AbstractCondition> goals;
	private boolean hasActionCosts;
	
	public PlanningProblem(String domainName, String problemName, Map<String, Type> types, 
			List<Argument> constants, Map<String, Predicate> predicates, 
			Map<String, DerivedPredicate> derivedPredicates,
			List<Operator> operators, List<Condition> initialState, 
			List<AbstractCondition> goals, boolean hasActionCosts) {
		super();
		this.domainName = domainName;
		this.problemName = problemName;
		this.types = types;
		this.constants = constants;
		this.predicates = predicates;
		this.derivedPredicates = derivedPredicates;
		this.operators = operators;
		this.initialState = initialState;
		this.goals = goals;
		this.hasActionCosts = hasActionCosts;
	}
	
	public boolean isArgumentOfType(Argument arg, Type type) {
		
		Type argType = arg.getType();
		return isTypeSupertypeOfType(argType, type);
	}
	
	public boolean isTypeSupertypeOfType(Type argType, Type type) {
		
		if (argType.equals(type)) {
			return true;
		
		} else {
			
			Stack<String> subtypes = new Stack<>();
			for (String subtypeName : type.getSubtypes()) {
				subtypes.push(subtypeName);
			}
			
			while (!subtypes.isEmpty()) {
				
				String subtypeName = subtypes.pop();
				Type subtype = types.get(subtypeName);
				
				if (subtype.equals(argType)) {
					return true;
				}
				
				for (String subsubtypeName : subtype.getSubtypes()) {
					subtypes.push(subsubtypeName);
				}
			}
		}
		
		return false;
	}
	
	public Type getType(String name) {
		return types.get(name);
	}
	
	public List<Argument> getConstants() {
		return constants;
	}
	
	public Predicate getPredicate(String name) {
		return predicates.get(name);
	}
	
	public Map<String, Predicate> getPredicates() {
		return predicates;
	}
	
	public Map<String, DerivedPredicate> getDerivedPredicates() {
		return derivedPredicates;
	}
	
	public List<Operator> getOperators() {
		return operators;
	}
	
	public List<Condition> getInitialState() {
		return initialState;
	}
	
	public List<AbstractCondition> getGoals() {
		return goals;
	}
	
	public boolean hasActionCosts() {
		return hasActionCosts;
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append("Domain " + domainName + ", instance " + problemName + "\n");
		str.append("Types:\n");
		for (Type type : types.values()) {
			// hide virtual types
			//if (type.toString().charAt(0) != '_' && type.toString().charAt(0) != '=')
				str.append("  " + type + "\n");
		}
		str.append("Constants:\n");
		for (Argument c : constants) {
			str.append("  " + c + "\n");
		}
		str.append("Predicates:\n");
		for (Predicate p : predicates.values()) {
			// hide virtual predicates
			//if (p.toString().charAt(0) != '_' && p.toString().charAt(0) != '=')
				str.append("  " + p + "\n");
		}
		for (DerivedPredicate p : derivedPredicates.values()) {
			str.append("  " + p + "\n");
		}
		str.append("Operators:\n");
		for (Operator o : operators) {
			str.append("  " + o + "\n");
		}
		str.append("Initial state:\n");
		for (Condition c : initialState) {
			str.append("  " + c + "\n");
		}
		str.append("Goals:\n");
		for (AbstractCondition c : goals) {
			str.append("  " + c + "\n");
		}
		String out = str.toString();
		if (out.endsWith("\n")) {
			out = out.substring(0, out.length()-1);
		}
		return out;
	}
}
