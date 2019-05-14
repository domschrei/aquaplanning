package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;

public class HtnGraph {

	public class HtnGraphNode {
		
		public Task task;
		public Set<HtnGraphEdge> parents;

		public HtnGraphNode(Task task) {
			this.task = task;
			this.parents = new HashSet<>();
		}
	}
	
	public class HtnGraphEdge {
		
		public Task parentTask;
		public List<Integer> argumentIndices;
		
		public HtnGraphEdge(Task task, List<Integer> argIndices) {
			this.parentTask = task;
			this.argumentIndices = argIndices;
		}
	}
	
	// maps predicate name to all entry edges with such an effect
	private Map<String, List<HtnGraphEdge>> tasksWithPosEffect;
	private Map<String, List<HtnGraphEdge>> tasksWithNegEffect;
	
	private Map<Task, HtnGraphNode> operatorNodes;
	private Map<Task, List<HtnGraphNode>> methodNodes;
	
	public HtnGraph(HtnPlanningProblem problem) {
		
		this.tasksWithPosEffect = new HashMap<>();
		this.tasksWithNegEffect = new HashMap<>();

		// Generate all necessary nodes
		operatorNodes = new HashMap<>();
		methodNodes = new HashMap<>();
		Map<Method, HtnGraphNode> nodeOfMethod = new HashMap<>();
		for (Operator op : problem.getOperators()) {
			Task opTask = op.toTask().normalize();
			operatorNodes.put(opTask, new HtnGraphNode(opTask));
		}
		for (Method m : problem.getMethods()) {
			Task mTask = m.toTask().normalize();
			Task mTaskImplicit = m.toTaskWithImplicitArgs().normalize();
			HtnGraphNode node = new HtnGraphNode(mTask/*Implicit*/);
			if (!methodNodes.containsKey(mTask))
				methodNodes.put(mTask, new ArrayList<>());
			methodNodes.get(mTask).add(node);
			nodeOfMethod.put(m, node);
		}
		
		// Generate edges between nodes
		for (Method m : problem.getMethods()) {
			HtnGraphNode mNode = nodeOfMethod.get(m);
			for (Task subtask : m.getSubtasks()) {
				Task normSubtask = subtask.normalize();
				
				HtnGraphNode opNode = operatorNodes.get(normSubtask);
				if (opNode != null) {
					HtnGraphEdge edge = new HtnGraphEdge(mNode.task, getParentArgumentIndices(m, subtask));
					opNode.parents.add(edge);
				} else {
					List<HtnGraphNode> mNodes = methodNodes.get(normSubtask);
					for (HtnGraphNode mSubNode : mNodes) {
						HtnGraphEdge edge = new HtnGraphEdge(mNode.task, getParentArgumentIndices(m, subtask));
						mSubNode.parents.add(edge);
					}
				}
			}
		}
		
		for (Operator op : problem.getOperators()) {
			Task normTask = op.toTask().normalize();
			Operator normOp = op.getOperatorWithGroundArguments(normTask.getArguments());
			normOp.getEffect().traverse(effect -> {
				if (effect.getConditionType() == ConditionType.negation) {
					effect = ((Negation) effect).getChildCondition();
				}
				if (effect.getConditionType() == ConditionType.atomic) {
					Condition c = (Condition) effect;
					Map<String, List<HtnGraphEdge>> map = (c.isNegated() ? tasksWithNegEffect : tasksWithPosEffect);
					if (!map.containsKey(c.getPredicate().getName())) {
						map.put(c.getPredicate().getName(), new ArrayList<>());
					}
					HtnGraphEdge edge = new HtnGraphEdge(normTask, getParentArgumentIndices(normOp, c));
					map.get(c.getPredicate().getName()).add(edge);
				}
				return effect;
			}, AbstractCondition.RECURSE_HEAD);
		}
	}
	
	public Set<Task> getSupportingLiftedTasks(Condition c) {
		
		Set<Task> supportingTasks = new HashSet<>();
		
		String predicateName = c.getPredicate().getName();
		Map<String, List<HtnGraphEdge>> tasksWithEffect = (c.isNegated() ? tasksWithNegEffect : tasksWithPosEffect);
		
		List<HtnGraphEdge> edges = new ArrayList<>();
		List<List<Argument>> argLists = new ArrayList<>();
		edges.addAll(tasksWithEffect.getOrDefault(predicateName, new ArrayList<>()));
		for (int i = 0; i < edges.size(); i++) {
			List<Argument> args = new ArrayList<>();
			for (int argIdx : edges.get(i).argumentIndices) {
				args.add(argIdx >= 0 ? c.getArguments().get(argIdx) : null);
			}
			argLists.add(args);
		}
		
		Set<HtnGraphEdge> visitedEdges = new HashSet<>();
		
		for (int i = 0; i < edges.size(); i++) {
			HtnGraphEdge edge = edges.get(i);
			Task task = edge.parentTask;
			List<Argument> args = argLists.get(i);
			
			Task instantiatedTask = task.getTaskBoundToArguments(task.getArguments(), args, /*setNullArgs=*/true);
			supportingTasks.add(instantiatedTask);
			
			for (HtnGraphNode node : getNodes(task)) {
				List<HtnGraphEdge> nextEdges = new ArrayList<>();
				nextEdges.addAll(node.parents);
				
				for (int j = 0; j < nextEdges.size(); j++) {
					if (visitedEdges.contains(nextEdges.get(j)))
						continue;
					
					edges.add(nextEdges.get(j));
					List<Argument> nextArgs = new ArrayList<>();
					for (int argIdx : nextEdges.get(j).argumentIndices) {
						Argument nextArg = null;
						if (argIdx >= instantiatedTask.getArguments().size()) {
						} else if (argIdx >= 0) {
							nextArg = instantiatedTask.getArguments().get(argIdx);
						}
						nextArgs.add(nextArg);
					}
					argLists.add(nextArgs);
				}
			}
			
			visitedEdges.add(edge);
		}
		
		return supportingTasks;
	}
	
	private List<HtnGraphNode> getNodes(Task task) {
		List<HtnGraphNode> nodes = new ArrayList<>();
		if (operatorNodes.containsKey(task))
			nodes.add(operatorNodes.get(task));
		if (methodNodes.containsKey(task)) {
			nodes.addAll(methodNodes.get(task));
		}
		return nodes;
	}
 	
	private List<Integer> getParentArgumentIndices(Method m, Task subtask) {
		List<Argument> allMethodArgs = new ArrayList<>();
		allMethodArgs.addAll(m.getExplicitArguments());
		allMethodArgs.addAll(m.getImplicitArguments());
		return getParentArgumentIndices(allMethodArgs, subtask.getArguments());
	}
	private List<Integer> getParentArgumentIndices(Operator op, Condition c) {
		return getParentArgumentIndices(op.getArguments(), c.getArguments());
	}
	private List<Integer> getParentArgumentIndices(List<Argument> parentArgs, List<Argument> childArgs) {
		
		List<Integer> argIndices = new ArrayList<>();
		for (int i = 0; i < parentArgs.size(); i++) {
			int idx = childArgs.indexOf(parentArgs.get(i));
			argIndices.add(idx);
		}
		return argIndices;
	}
}
