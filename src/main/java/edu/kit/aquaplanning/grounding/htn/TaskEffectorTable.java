package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;

public class TaskEffectorTable {

	private HtnPlanningProblem problem;
	
	private Map<String, List<Task>> posPrimitiveEffectors;
	private Map<String, List<Task>> negPrimitiveEffectors;
	
	private Map<String, Operator> operatorsByName;
	
	public TaskEffectorTable(HtnPlanningProblem problem) {
		
		this.problem = problem;
		this.posPrimitiveEffectors = new HashMap<>();
		this.negPrimitiveEffectors = new HashMap<>();
		this.operatorsByName = new HashMap<>();
		
		for (Operator op : problem.getOperators()) {
			operatorsByName.put(op.getName(), op);
			Task task = op.toTask().normalize();
			op.getEffect().traverse(effect -> {
				boolean negated = false;
				if (effect.getConditionType() == ConditionType.negation) {
					effect = ((Negation) effect).getChildCondition();
					negated = true;
				}
				if (effect.getConditionType() == ConditionType.atomic) {
					Condition c = (Condition) effect.copy();
					negated ^= c.isNegated();
					Map<String, List<Task>> map = (negated ? negPrimitiveEffectors : posPrimitiveEffectors);
					if (!map.containsKey(c.getPredicate().getName())) {
						map.put(c.getPredicate().getName(), new ArrayList<>());
					}
					map.get(c.getPredicate().getName()).add(task);
				}
				return effect;
			}, AbstractCondition.RECURSE_HEAD);
		}
	}
	
	public Set<Task> getSupportingLiftedTasks(Condition c) {
		
		Set<Task> supportingTasks = new HashSet<>();
		
		// Get all primitive tasks supporting the condition
		Stack<Task> taskStack = new Stack<>();
		String predicateName = c.getPredicate().getName();
		List<Task> effectors = (c.isNegated() ? negPrimitiveEffectors : posPrimitiveEffectors).get(predicateName);
		if (effectors == null) return new HashSet<>();
		for (Task effectorTask : effectors) {
			Operator op = operatorsByName.get(effectorTask.getName());
			List<Operator> instantiations = getInstantiationsCausingEffect(op, c);
			instantiations.forEach(instantiation -> taskStack.push(instantiation.toTask()));
		}
		supportingTasks.addAll(taskStack);
		
		while (!taskStack.isEmpty()) {
			Task task = taskStack.pop();
			
			// Which methods contain this task?
			for (Method method : problem.getMethods()) {
				
				// Instantiate possible methods containing the task
				List<Method> methodsContainingTask = getInstantiationsContainingTask(method, task);
				for (Method m : methodsContainingTask) {
					Task mTask = m.toTask();
					if (!supportingTasks.contains(mTask)) {
						taskStack.push(mTask);
						supportingTasks.add(mTask);
					}
				}
			}
		}
		
		return supportingTasks;
	}
	
	public List<Operator> getInstantiationsCausingEffect(Operator op, Condition effect) {
		
		final List<Operator> instantiations = new ArrayList<>();
		
		// For each condition of the operator's effects
		op.getEffect().traverse(cond -> {
			boolean negated = false;
			while (cond.getConditionType() == ConditionType.negation) {
				cond = ((Negation) cond).getChildCondition();
				negated = !negated;
			}
			if (cond.getConditionType() == ConditionType.atomic) {
				Condition c = (Condition) cond.copy();
				negated ^= c.isNegated();
				if (c.getPredicate().getName().equals(effect.getPredicate().getName())) {
					if (negated == effect.isNegated()) {
						
						// This condition matches the provided effect
						
						// Try instantiating an operator to get this effect condition
						// with the correct set of arguments
						List<Argument> instantiatedArgs = instantiate(op.getArguments(), c.getArguments(), effect.getArguments());
						if (instantiatedArgs == null) return effect;
						instantiations.add(op.getOperatorWithGroundArguments(instantiatedArgs));
					}
				}
			}
			return effect;
		}, AbstractCondition.RECURSE_HEAD);
		
		return instantiations;
	}
	
	public List<Method> getInstantiationsContainingTask(Method method, Task task) {
		
		final List<Method> instantiations = new ArrayList<>();
		
		for (Task methodSubtask : method.getSubtasks()) {
			
			if (!methodSubtask.getName().equals(task.getName()))
				continue;
			if (task.getArguments().size() != methodSubtask.getArguments().size())
				continue;
			
			List<Argument> instantiatedArgs = instantiate(method.getExplicitArguments(), methodSubtask.getArguments(), task.getArguments());
			if (instantiatedArgs == null) continue;
			instantiations.add(method.getMethodBoundToArguments(instantiatedArgs, method.getImplicitArguments()));
		}
		
		return instantiations;
	}
	
	public List<Argument> instantiate(List<Argument> originalArgs, 
			List<Argument> uninstantiatedObjectArgs, 
			List<Argument> instantiatedObjectArgs) {
		
		List<Argument> instantiatedArgs = new ArrayList<>(originalArgs);
		for (int condArgIdx = 0; condArgIdx < uninstantiatedObjectArgs.size(); condArgIdx++) {
			Argument arg = uninstantiatedObjectArgs.get(condArgIdx);
			if (arg.isConstant()) continue;
			int opArgIdx = originalArgs.indexOf(arg);
			if (opArgIdx < 0) continue;
			Argument instantiatedArg = instantiatedObjectArgs.get(condArgIdx);
			if (instantiatedArgs.get(opArgIdx).isConstant() && !instantiatedArgs.get(opArgIdx).equals(instantiatedArg)) {
				instantiatedArgs = null;
				break; // some other argument was already set at this position -> illegal instantiation
			}
			instantiatedArgs.set(opArgIdx, instantiatedArg);
		}
		return instantiatedArgs;
	}
}
