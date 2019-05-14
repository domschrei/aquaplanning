package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.grounding.datastructures.ArgumentNode;
import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer.FactStatus;
import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.ground.htn.Reduction;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.util.Pair;

public class HtnGrounder {

	private GroundPlanningProblem groundProblem;
	private HtnPlanningProblem htnLiftedProblem;
	private PlanningGraphGrounder grounder;
	
	private List<HierarchyLayer> hierarchyLayers;

	private Map<String, Action> actionMap;
	private Map<String, List<Reduction>> reductionMap;

	private Map<Integer, Set<Task>> supportingTasksPos;
	private Map<Integer, Set<Task>> supportingTasksNeg;
	
	private Function<AbstractCondition, Precondition> conditionGrounder;
	private HtnMethodIndex methodIndex;
	
	private Reduction initReduction;
	private int globalVariableStart = 1;
	
	public HtnGrounder(GroundPlanningProblem problem, PlanningGraphGrounder grounder) {
		
		this.groundProblem = problem;		
		HtnPlanningProblem htnLiftedProblem = (HtnPlanningProblem) grounder.getProblem();
		this.htnLiftedProblem = htnLiftedProblem;
		this.grounder = grounder;
		
		actionMap = new HashMap<>();
		for (Action a : problem.getActions()) {
			String task = a.getName();
			actionMap.put(task, a);
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

		Logger.log(Logger.INFO, "Calculating lifted support reductions per occurring atom ...");

		LiftedState convergedState = grounder.getState();
		Set<Condition> allConditions = new HashSet<>();
		for (boolean negated : Arrays.asList(false, true)) {
			for (String predicate : convergedState.getOccurringPredicates(negated)) {
				for (Condition c : convergedState.getConditions(predicate, negated)) {
					allConditions.add(c);
				}
			}			
		}
		
		supportingTasksPos = new HashMap<>();
		supportingTasksNeg = new HashMap<>();
		TaskEffectorTable effectors = new TaskEffectorTable(htnLiftedProblem);
		for (Condition c : allConditions) {
			int atom = grounder.getAtomTable().atom(c.getPredicate(), c.getArguments(), false).getId();
			c = c.copy(); c.setNegated(false);
			supportingTasksPos.put(atom, effectors.getSupportingLiftedTasks(c));
			//System.out.println(c + " : " + supportingTasksPos.get(atom));
			c = c.copy(); c.setNegated(true);
			supportingTasksNeg.put(atom, effectors.getSupportingLiftedTasks(c));
			//System.out.println(c + " : " + supportingTasksNeg.get(atom));
		}
		
		Logger.log(Logger.INFO, "Creating initial layer ...");
		
		State posState = new State(groundProblem.getInitialState());
		State negState = new State(posState);
		
		methodIndex = new HtnMethodIndex(grounder,
				grounder.getState(), grounder.getFilteredActions());
		
		HierarchyLayer initLayer = new HierarchyLayer();
		
		// Add complete state @ 0
		int numAtoms = groundProblem.getNumAtoms();
		int pos = 0;
		for (int p = 0; p < numAtoms; p++) {
			initLayer.addFact(0, p+1);
		}
		addFactsStatus(initLayer, pos, posState, negState);
				
		Method initMethod = htnLiftedProblem.getInitialTaskNetwork();
		initMethod = methodIndex.simplify(initMethod);
		initReduction = new Reduction(initMethod, conditionGrounder);
		
		for (pos = 0; pos < initReduction.getNumSubtasks(); pos++) {
			
			Set<Action> occurringActions = new HashSet<>();
			Set<Reduction> occurringReductions = new HashSet<>();
			
			Task task = initMethod.getSubtasks().get(pos);
			String t = initReduction.getSubtask(pos);
			Action a = actionMap.get(t);
			if (a != null && isActionApplicable(a, posState, negState)) {
				initLayer.addAction(pos, a);
				addActionFacts(a, pos, initLayer);
				occurringActions.add(a);
			} else {
				List<Method> taskMethods = new ArrayList<>();
				for (Method m : htnLiftedProblem.getMethods()) {
					if (matches(task, m)) {
						m = m.getMethodBoundToArguments(task.getArguments(), m.getImplicitArguments());
						taskMethods.add(m);
					}
				}
				if (!taskMethods.isEmpty()) {
					List<Reduction> reductions = methodIndex.getRelevantReductions(taskMethods);
					for (Reduction r : reductions) {
						if (isReductionApplicable(r, posState, negState)) {
							if (isReductionPseudoPrimitive(r)) {
								Action action = actionMap.get(r.getSubtask(0));
								initLayer.addAction(pos, action);
								addActionFacts(action, pos, initLayer);
								occurringActions.add(action);
							} else {
								initLayer.addReduction(pos, r);
								occurringReductions.add(r);								
							}
						}
					}
					reductionMap.put(t, reductions);
				}
			}
			// Add constraints of initTaskNetwork @ pos(+1, if after)
			Precondition p = initReduction.getConstraint(pos);
			addCondition(p, pos, initLayer);
			
			extendState(occurringActions, occurringReductions, posState, negState);
			addFactsStatus(initLayer, pos, posState, negState);
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
		addFactsStatus(initLayer, pos, posState, negState);
		
		globalVariableStart = initLayer.consolidate(globalVariableStart, /*factVariablesToReuse=*/null);
		hierarchyLayers = new ArrayList<>();
		hierarchyLayers.add(initLayer);
		
		System.out.println((actionMap.size() + reductionMap.size()) + " tasks are known.");
	}
	
	public void computeNextLayer() {
		
		HierarchyLayer lastLayer = hierarchyLayers.get(hierarchyLayers.size()-1);
		HierarchyLayer nextLayer = new HierarchyLayer();
		
		State posState = new State(groundProblem.getInitialState());
		State negState = new State(posState);
		int successorPos = 0;
		addFactsStatus(nextLayer, successorPos, posState, negState);
		
		List<Map<Integer, Integer>> factVariablesToReuse = new ArrayList<>();
		
		for (int pos = 0; pos < lastLayer.getSize(); pos++) {
			lastLayer.setSuccessor(pos, successorPos);
			
			// Collect fact variables that can be reused at the next layer
			Map<Integer, Integer> factVarsAtPos = new HashMap<>();
			for (int fact : lastLayer.getFacts(pos)) {
				if (lastLayer.isFactFluent(pos, fact)) {
					factVarsAtPos.put(fact, lastLayer.getFactVariable(pos, fact));
				}
			}
			factVariablesToReuse.add(factVarsAtPos);
			
			/*
			System.out.println(pos + ", pos: " + groundProblem.stateToString(posState));
			System.out.println(pos + ", neg: " + groundProblem.stateToString(negState));
			*/
			
			int offset = 1;
			int minOffset = lastLayer.getSize();
			
			for (Action a : lastLayer.getActions(pos)) {
				if (!isActionApplicable(a, posState, negState)) {
					continue;
				}
				nextLayer.addAction(successorPos, a);
				addActionFacts(a, successorPos, nextLayer);
				minOffset = 1;
			}
			for (int p : lastLayer.getFacts(pos)) {
				nextLayer.addFact(successorPos, p);
			}
						
			int childIdx = 0;
			List<Reduction> applicableReductions = new ArrayList<>();
			for (Reduction r : lastLayer.getReductions(pos)) {
				if (isReductionApplicable(r, 0, posState, negState))
					applicableReductions.add(r);
			}
			boolean children = true;
			while (children) {
				children = false;
				
				for (Reduction r : applicableReductions) {
					Pair<Integer, Integer> offsetInterval = addChildren(r, successorPos, childIdx, nextLayer, posState, negState);
					if (offsetInterval.getRight() > 0) children = true;
					minOffset = Math.min(minOffset, offsetInterval.getLeft());
					offset = Math.max(offset, offsetInterval.getRight());
				}
				extendState(nextLayer.getActions(successorPos+childIdx), nextLayer.getReductions(successorPos+childIdx), posState, negState);
				addFactsStatus(nextLayer, successorPos+childIdx, posState, negState);
				
				childIdx++;
			}
			
			while (minOffset < offset) {
				nextLayer.addAction(successorPos+minOffset, HierarchyLayer.BLANK_ACTION);
				minOffset++;
			}
			
			successorPos += offset;
			while (factVariablesToReuse.size() < successorPos) {
				factVariablesToReuse.add(new HashMap<>());
			}
		}
		
		globalVariableStart = nextLayer.consolidate(globalVariableStart, factVariablesToReuse);
		hierarchyLayers.add(nextLayer);
		
		System.out.println((actionMap.size() + reductionMap.size()) + " tasks are known.");
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
	
	public Pair<Integer, Integer> addChildren(Reduction r, int startPos, int childIdx,
			HierarchyLayer nextLayer, State positives, State negatives) {

		int maxOffset = 0;
		int minOffset = nextLayer.getSize();

		if (childIdx >= r.getNumSubtasks())
			return new Pair<>(minOffset, maxOffset);
		
		String task = r.getSubtask(childIdx);
		Action a = actionMap.get(task);
		if (a != null) {
			if (childIdx != 0 || isActionApplicable(a, positives, negatives)) {
				nextLayer.addAction(startPos + childIdx, a);
				// add preconditions, effects @ parentPos+pos(+1)
				addActionFacts(a, startPos+childIdx, nextLayer);
				maxOffset = Math.max(maxOffset, childIdx+1);
				minOffset = Math.min(minOffset, 1);
			}
		} else {
			List<Method> methods = new ArrayList<>();
			for (Method m : htnLiftedProblem.getMethods()) {
				if (matches(r.getBaseMethod().getSubtasks().get(childIdx), m)) {
					m = m.getMethodBoundToArguments(r.getBaseMethod().getSubtasks().get(childIdx).getArguments(), m.getImplicitArguments());
					methods.add(m);						
				}
			}
			if (!methods.isEmpty()) {
				List<Reduction> instMethods = methodIndex.getRelevantReductions(methods);
				for (Reduction child : instMethods) {
					if (childIdx == 0 && !isReductionApplicable(r, positives, negatives))
						continue;
					if (isReductionPseudoPrimitive(child)) {
						Action action = actionMap.get(child.getSubtask(0));
						nextLayer.addAction(startPos + childIdx, action);
						addActionFacts(action, startPos + childIdx, nextLayer);
					} else {
						nextLayer.addReduction(startPos + childIdx, child);
					}
					maxOffset = Math.max(maxOffset, childIdx+1);
					minOffset = Math.min(minOffset, childIdx+1);
				}
				reductionMap.put(task, instMethods);
			}
		}
		// Add constraints of r @ parentPos+pos
		addCondition(r.getConstraint(childIdx+1), startPos+childIdx+1, nextLayer);
		if (childIdx == 0)
			addCondition(r.getConstraint(childIdx), startPos+childIdx, nextLayer);
		
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

	public boolean isActionApplicable(Action a, State positives, State negatives) {
		boolean applicable = true;
		if (!positives.getAtomSet().all(a.getPreconditionsPos()))
			applicable = false;
		if (!negatives.getAtomSet().none(a.getPreconditionsNeg()))
			applicable = false;
		if (!applicable) {
			/*
			System.out.println(a + " not applicable in pos. state " + groundProblem.stateToString(positives) 
			+ ", neg. state " + groundProblem.stateToString(negatives));
			System.out.println("  pre pos: " + groundProblem.atomSetToString(a.getPreconditionsPos()));
			System.out.println("  pre neg: " + groundProblem.atomSetToString(a.getPreconditionsNeg()));*/
		}
		return applicable;
	}
	
	public boolean isReductionApplicable(Reduction r, State positives, State negatives) {
		return isReductionApplicable(r, 0, positives, negatives);
	}
	
	public boolean isReductionApplicable(Reduction r, int childIdx, State positives, State negatives) {
		
		if (r.getConstraint(childIdx) == null)
			return true; // no constraint -> always applicable
		
		List<Precondition> pres = new ArrayList<>();
		pres.add(r.getConstraint(childIdx));
		
		for (int i = 0; i < pres.size(); i++) {
			Precondition pre = pres.get(i);
			if (pre.isType(PreconditionType.atom)) {
				
				if (pre.getAtom().getValue() && !positives.holds(pre.getAtom()))
					return false;
				if (!pre.getAtom().getValue() && !negatives.holds(pre.getAtom()))
					return false;
				
			} else if (pre.isType(PreconditionType.conjunction)) {
				pres.addAll(pre.getChildren());
			}
		}
		// TODO check subtask for applicability?
		return true;
	}
	
	public boolean isReductionPseudoPrimitive(Reduction r) {
		return r.getNumSubtasks() == 1 && actionMap.containsKey(r.getSubtask(0));
	}
	
	public boolean isActionNoop(Action action) {
		return action.getPreconditionsPos().getFirstTrueAtom() < 0 
			&& action.getPreconditionsNeg().getFirstTrueAtom() < 0 
			&& action.getEffectsPos().getFirstTrueAtom() < 0 
			&& action.getEffectsNeg().getFirstTrueAtom() < 0;
	}
	
	/**
	 * 
	 * @param actions
	 * @param reductions
	 * @param positives
	 * @param negatives
	 */
	public void extendState(Set<Action> actions, Set<Reduction> reductions, State positives, State negatives) {
		
		// Build sets of occurring ground methods
		Map<String, ArgumentNode> methods = new HashMap<>();
		for (Reduction r : reductions) {
			String taskName = r.getBaseMethod().getName();
			if (!methods.containsKey(taskName))
				methods.put(taskName, new ArgumentNode(grounder.getArgumentIndices()));
			ArgumentNode node = methods.get(taskName);
			node.add(r.getBaseMethod().getExplicitArguments());
		}
		
		/*
		for (String key : methods.keySet()) {
			System.out.println(key + " : " + methods.get(key).toString());
		}*/
		
		// Check atoms which may possibly change by a reduction
		for (boolean value : Arrays.asList(true, false)) {
			
			AtomSet atoms = (value ? positives : negatives).getAtomSet();
			for (int atom = 0; atom < atoms.size(); atom++) {
				
				if (atoms.get(atom) == value)
					continue; // atom to check is already (un)set
				
				// Get all tasks which may change the atom from !value to value
				Set<Task> support = (value ? supportingTasksPos : supportingTasksNeg).get(atom);
				if (support == null) {
					continue;
				}
				boolean supported = false;
				for (Task t : support) {
					if (methods.containsKey(t.getName())) {
						if (!methods.get(t.getName()).containsPartiallyInstantiatedArgs(t.getArguments())) {
							//System.out.println("*** " + t.toTaskString());
							continue;
						}
						
						// Supporting task occurs here: extend state
						if (value) {
							positives.getAtomSet().set(atom);
						} else {
							negatives.getAtomSet().unset(atom);
						}
						
						supported = true;
						break;
					} else {
						//System.out.println(t.getName() + " not in methods");
					}
				}
				if (!supported) {
					//System.out.println((value ? "+" : "-") + groundProblem.getAtomNames().get(atom));
				}
			}
		}
		
		// Apply effects of occurring actions
		for (Action a : actions) {
			for (int atom = a.getEffectsPos().getFirstTrueAtom(); atom >= 0; 
					atom = a.getEffectsPos().getNextTrueAtom(atom+1)) {
				positives.getAtomSet().set(atom);
			}
			for (int atom = a.getEffectsNeg().getFirstTrueAtom(); atom >= 0; 
					atom = a.getEffectsNeg().getNextTrueAtom(atom+1)) {
				negatives.getAtomSet().unset(atom);
			}
		}
	}
	
	public void addFactsStatus(HierarchyLayer layer, int pos, State posState, State negState) {
		for (int f = 0; f < groundProblem.getNumAtoms(); f++) {
			if (!posState.getAtomSet().get(f)) {
				// Atom was never positive so far: constant negative
				layer.addFactStatus(pos, f+1, FactStatus.constantNegative);
			} else if (negState.getAtomSet().get(f)) {
				// Atom was never negative so far: constant positive
				layer.addFactStatus(pos, f+1, FactStatus.constantPositive);
			} else {
				// Atom can be positive or negative as of now
				layer.addFactStatus(pos, f+1, FactStatus.fluent);
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
	
	public Action getAction(String task) {
		return actionMap.getOrDefault(task, null);
	}
	public List<Reduction> getReductions(String task) {
		return reductionMap.getOrDefault(task, new ArrayList<>());
	}
}
