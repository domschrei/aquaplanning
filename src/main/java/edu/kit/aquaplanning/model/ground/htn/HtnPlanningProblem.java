package edu.kit.aquaplanning.model.ground.htn;

import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Axiom;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.htn.Method;

public class HtnPlanningProblem extends PlanningProblem {

	private Map<String, List<Type>> tasks;
	private List<Method> methods;
	private Method initialTaskNetwork;
	
	public HtnPlanningProblem(String domainName, String problemName, Map<String, Type> types, 
			List<Argument> constants, Map<String, Predicate> predicates, 
			Map<String, Axiom> derivedPredicates, Map<String, Function> functions,
			List<Operator> operators, List<Condition> initialState, 
			Map<Function, Float> initialFunctionValues,
			List<AbstractCondition> goals, boolean hasActionCosts,
			Map<String, List<Type>> tasks, List<Method> methods, Method initialTaskNetwork) {
		
		super(domainName, problemName, types, constants, predicates, derivedPredicates, 
				functions, operators, initialState, initialFunctionValues, goals, hasActionCosts);
		this.tasks = tasks;
		this.methods = methods;
		this.initialTaskNetwork = initialTaskNetwork;
	}

	public Map<String, List<Type>> getTasks() {
		return tasks;
	}
	
	public List<Method> getMethods() {
		return methods;
	}
	
	public Method getInitialTaskNetwork() {
		return initialTaskNetwork;
	}
}
