package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer;
import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.ground.htn.Reduction;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.util.Pair;

public class HtnGrounder {

	private GroundPlanningProblem groundProblem;
	private HtnPlanningProblem htnLiftedProblem;
	private List<HierarchyLayer> hierarchyLayers;

	private Map<String, List<Action>> actionMap;
	private Map<String, List<Reduction>> reductionMap;
	
	private Function<AbstractCondition, Precondition> conditionGrounder;
	private HtnMethodIndex methodIndex;
	
	private Reduction initReduction;
	private int globalVariableStart = 1;
	
	public HtnGrounder(GroundPlanningProblem problem, PlanningGraphGrounder grounder) {
		
		this.groundProblem = problem;		
		HtnPlanningProblem htnLiftedProblem = (HtnPlanningProblem) grounder.getProblem();
		this.htnLiftedProblem = htnLiftedProblem;
		
		actionMap = new HashMap<>();
		for (Action a : problem.getActions()) {
			String task = a.getName();
			if (!actionMap.containsKey(task))
				actionMap.put(task, new ArrayList<>());
			actionMap.get(task).add(a);
		}
		reductionMap = new HashMap<>();
		
		//grounder.consolidate();
		conditionGrounder = (c -> grounder.toPrecondition(c, false));
		
		Logger.log(Logger.INFO, "Infering implicit types in methods ...");
		
		boolean change = true;
		while (change) {
			change = false;
			
			for (Method m : htnLiftedProblem.getMethods()) {
				
				Map<String, Integer> implicitArgs = new HashMap<>();
				for (int i = 0; i < m.getImplicitArguments().size(); i++) {
					Type type = m.getImplicitArguments().get(i).getType();
					if (type.getName().equals("_IMPLICIT")) {
						implicitArgs.put(m.getImplicitArguments().get(i).getName(), i);
					}
				}
				List<Type> types = htnLiftedProblem.getTasks().get(m.getName());
				if (types != null)
				for (int typeIdx = 0; typeIdx < types.size(); typeIdx++) {
					if (types.get(typeIdx).getName().equals("_IMPLICIT")) {
						Type argType = m.getTypeOfArgument(m.getExplicitArguments().get(typeIdx).getName());
						types.set(typeIdx, argType);
						if (types.get(typeIdx).getName().equals("_IMPLICIT")) {
							change = true;
						}
					}
				}
				
				for (Task task : m.getSubtasks()) {
					List<Type> taskTypes = htnLiftedProblem.getTasks().get(task.getName());
					for (int i = 0; i < task.getArguments().size(); i++) {
						Argument taskArg = task.getArguments().get(i);
						if (taskTypes.get(i).getName().equals("_IMPLICIT") && !taskArg.getType().getName().equals("_IMPLICIT")) {
							// Type is still undefined in the task definition
							taskTypes.set(i, taskArg.getType());
							change = true;
						}
						if (!taskTypes.get(i).getName().equals("_IMPLICIT") && implicitArgs.containsKey(taskArg.getName())) {
							m.getImplicitArguments().get(implicitArgs.get(taskArg.getName())).setType(taskTypes.get(i));
							taskArg.setType(taskTypes.get(i));
							task.getArguments().set(i, taskArg);
							change = true;
						}
						if (taskArg.getType().getName().equals("_IMPLICIT")) {
							if (m.getExplicitArguments().contains(taskArg)) {
								int argIdx = m.getExplicitArguments().indexOf(taskArg);
								taskArg.setType(m.getExplicitArguments().get(argIdx).getType());
								change |= !taskArg.getType().getName().equals("_IMPLICIT");
							}
							if (m.getImplicitArguments().contains(taskArg)) {
								int argIdx = m.getImplicitArguments().indexOf(taskArg);
								taskArg.setType(m.getImplicitArguments().get(argIdx).getType());
								change |= !taskArg.getType().getName().equals("_IMPLICIT");
							}
						}
					}
				}
	 		}
		}
		
		Logger.log(Logger.INFO, "Creating initial layer ...");
		
		methodIndex = new HtnMethodIndex(grounder,
				grounder.getState(), grounder.getFilteredActions());
		
		HierarchyLayer initLayer = new HierarchyLayer();
		
		// Add complete state @ 0
		int numAtoms = groundProblem.getNumAtoms();
		int pos = 0;
		for (int p = 0; p < numAtoms; p++) {
			initLayer.addFact(0, p+1);
		}
		
		Method initMethod = htnLiftedProblem.getInitialTaskNetwork();
		initMethod = methodIndex.simplify(initMethod);
		initReduction = new Reduction(initMethod, conditionGrounder);
		
		for (pos = 0; pos < initReduction.getNumSubtasks(); pos++) {
			Task task = initMethod.getSubtasks().get(pos);
			String t = initReduction.getSubtask(pos);
			List<Action> taskActions = actionMap.get(t);
			if (taskActions != null) {
				for (Action a : taskActions) {
					initLayer.addAction(pos, a);
					addActionFacts(a, pos, initLayer);
				}
			} else {
				List<Method> taskMethods = new ArrayList<>();
				for (Method m : htnLiftedProblem.getMethods()) {
					if (matches(task, m)) {
						m = m.getMethodBoundToArguments(task.getArguments(), m.getImplicitArguments());
						taskMethods.add(m);
					}
				}
				if (!taskMethods.isEmpty()) {
					List<Reduction> reductions = methodIndex.getRelevantReductions(taskMethods, conditionGrounder);
					for (Reduction r : reductions) {
						initLayer.addReduction(pos, r);
					}
					reductionMap.put(t, reductions);
				}
			}
			// Add constraints of initTaskNetwork @ pos(+1, if after)
			Precondition p = initReduction.getConstraint(pos);
			addCondition(p, pos, initLayer);
		}
		// Add init state and goals @ initTaskNetwork.getSubtasks().size()
		for (int p = 0; p < numAtoms; p++) {
			initLayer.addFact(pos, p+1);
		}
		List<Atom> goal = groundProblem.getGoal().getAtoms();
		for (int p = 0; p < goal.size(); p++) {
			initLayer.addFact(pos, p+1);
		}
		// "After" constraint of initial task network
		Precondition p = initReduction.getConstraint(pos);
		addCondition(p, pos, initLayer);
		
		globalVariableStart = initLayer.consolidate(globalVariableStart);
		hierarchyLayers = new ArrayList<>();
		hierarchyLayers.add(initLayer);
		
		/*
		Logger.log(Logger.INFO, "Starting to calculate supporting tasks ...");
		HtnGraph htnGraph = new HtnGraph(htnLiftedProblem);
		for (String pred : grounder.getState().getOccurringPredicates(false)) {
			for (Condition c : grounder.getState().getConditions(pred, false)) {
				Set<Task> tasks = htnGraph.getSupportingLiftedTasks(c);
				System.out.println(c + " : " + tasks + " (" + tasks.size() + ")");
			}
		}
		for (String pred : grounder.getState().getOccurringPredicates(true)) {
			for (Condition c : grounder.getState().getConditions(pred, true)) {
				Set<Task> tasks = htnGraph.getSupportingLiftedTasks(c);
				System.out.println(c + " : " + tasks + " (" + tasks.size() + ")");
			}
		}
		Logger.log(Logger.INFO, "Finished calculating supporting tasks.");
		*/
	}
	
	public void computeNextLayer() {
		
		HierarchyLayer lastLayer = hierarchyLayers.get(hierarchyLayers.size()-1);
		HierarchyLayer nextLayer = new HierarchyLayer();
		int successorPos = 0;
		for (int pos = 0; pos < lastLayer.getSize(); pos++) {
			lastLayer.setSuccessor(pos, successorPos);
			int offset = 1;
			int minOffset = lastLayer.getSize();
			for (Reduction r : lastLayer.getReductions(pos)) {
				Pair<Integer, Integer> offsetInterval = addChildren(r, successorPos, nextLayer);
				minOffset = Math.min(minOffset, offsetInterval.getLeft());
				offset = Math.max(offset, offsetInterval.getRight());
			}
			while (minOffset < offset) {
				nextLayer.addAction(successorPos+minOffset, HierarchyLayer.BLANK_ACTION);
				minOffset++;
			}
			for (Action a : lastLayer.getActions(pos)) {
				nextLayer.addAction(successorPos, a);
				addActionFacts(a, successorPos, nextLayer);
			}
			for (int p : lastLayer.getFacts(pos)) {
				nextLayer.addFact(successorPos, p);
			}
			successorPos += offset;
		}
		
		globalVariableStart = nextLayer.consolidate(globalVariableStart);
		hierarchyLayers.add(nextLayer);
	}
	
	public List<HierarchyLayer> getHierarchyLayers() {
		return hierarchyLayers;
	}
	
	public void addActionFacts(Action a, int startPos, HierarchyLayer nextLayer) {
				
		for (int p = a.getPreconditionsPos().getFirstTrueAtom(); p >= 0; 
				 p = a.getPreconditionsPos().getNextTrueAtom(p+1)) {
			nextLayer.addFact(startPos, p+1);
		}
		for (int p = a.getPreconditionsNeg().getFirstTrueAtom(); p >= 0; 
				 p = a.getPreconditionsNeg().getNextTrueAtom(p+1)) {
			nextLayer.addFact(startPos, p+1);
		}
		for (int p = a.getEffectsPos().getFirstTrueAtom(); p >= 0; 
				 p = a.getEffectsPos().getNextTrueAtom(p+1)) {
			nextLayer.addFact(startPos+1, p+1);
		}
		for (int p = a.getEffectsNeg().getFirstTrueAtom(); p >= 0; 
				 p = a.getEffectsNeg().getNextTrueAtom(p+1)) {
			nextLayer.addFact(startPos+1, p+1);
		}
	}
	
	public Pair<Integer, Integer> addChildren(Reduction r, int startPos, HierarchyLayer nextLayer) {

		int maxOffset = 1;
		int minOffset = nextLayer.getSize();
		for (int pos = 0; pos < r.getNumSubtasks(); pos++) {
			String task = r.getSubtask(pos);
			List<Action> actions = actionMap.get(task);
			if (actions != null) {
				for (Action a : actions) {					
					nextLayer.addAction(startPos + pos, a);
					// add preconditions, effects @ parentPos+pos(+1)
					addActionFacts(a, startPos+pos, nextLayer);
					maxOffset = Math.max(maxOffset, pos+1);
					minOffset = Math.min(minOffset, 1);
				}
			} else {
				List<Method> methods = new ArrayList<>();
				for (Method m : htnLiftedProblem.getMethods()) {
					if (matches(r.getBaseMethod().getSubtasks().get(pos), m)) {
						m = m.getMethodBoundToArguments(r.getBaseMethod().getSubtasks().get(pos).getArguments(), m.getImplicitArguments());
						methods.add(m);						
					}
				}
				if (!methods.isEmpty()) {
					//System.out.println(methods);
					List<Reduction> instMethods = methodIndex.getRelevantReductions(methods, conditionGrounder);
					//System.out.println(" >>> " + instMethods.size() + " instantiated");
					for (Reduction child : instMethods) {
						nextLayer.addReduction(startPos + pos, child);
						maxOffset = Math.max(maxOffset, pos+1);
						minOffset = Math.min(minOffset, pos+1);
					}
					reductionMap.put(task, instMethods);
				}
			}
			// Add constraints of r @ parentPos+pos
			addCondition(r.getConstraint(pos), startPos+pos, nextLayer);
		}
		addCondition(r.getConstraint(r.getNumSubtasks()), startPos+r.getNumSubtasks(), nextLayer);
		
		return new Pair<>(minOffset, maxOffset);
	}
	
	public boolean matches(Task task, Method method) {
		
		if (!task.getName().equals(method.getName()))
			return false;
		if (method.getExplicitArguments().size() != task.getArguments().size())
			return false;
		for (int i = 0; i < task.getArguments().size(); i++) {
			Argument methodArg = method.getExplicitArguments().get(i);
			Argument taskArg = task.getArguments().get(i);
			if (!htnLiftedProblem.isArgumentOfType(taskArg, methodArg.getType())) {
				return false;
			}
		}
		return true;
	}
	
	public void addCondition(Precondition condition, int pos, HierarchyLayer layer) {
		List<Precondition> conditions = new ArrayList<>();
		conditions.add(condition);
		for (int i = 0; i < conditions.size(); i++) {
			Precondition pre = conditions.get(i);
			switch (pre.getType()) {
			case atom:
				layer.addFact(pos, pre.getAtom().getId()+1);
				break;
			case conjunction:
				conditions.addAll(pre.getChildren());
				break;
			default:
				break;
			}
		}
	}

	
	
	
	
	
	
	public GroundPlanningProblem getGroundProblem() {
		return groundProblem;
	}

	public HtnPlanningProblem getHtnLiftedProblem() {
		return htnLiftedProblem;
	}
	
	public Reduction getInitReduction() {
		return initReduction;
	}
	
	public List<Action> getActions(String task) {
		return actionMap.getOrDefault(task, new ArrayList<>());
	}
	public List<Reduction> getReductions(String task) {
		return reductionMap.getOrDefault(task, new ArrayList<>());
	}
}
