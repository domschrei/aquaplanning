package edu.kit.aquaplanning.grounding.htn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.grounding.datastructures.ArgumentNode;
import edu.kit.aquaplanning.model.ground.Action;
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
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
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
	
	private HtnMethodIndex methodIndex;
	private TaskEffectorTable effectorTable;

	private Reduction initReduction;
	private int globalVariableStart = 1;

	public HtnGrounder(Configuration config, GroundPlanningProblem problem, PlanningGraphGrounder grounder) {

		this.groundProblem = problem;
		HtnPlanningProblem htnLiftedProblem = (HtnPlanningProblem) grounder.getProblem();
		this.htnLiftedProblem = htnLiftedProblem;
		this.grounder = grounder;

		// Lookup structures task-name -> {action,reductions}
		actionMap = new HashMap<>();
		for (Action a : problem.getActions()) {
			String task = a.getName();
			actionMap.put(task, a);
		}
		reductionMap = new HashMap<>();
		
		// Preprocess HTN portion of the problem (implicit arguments etc.)
		HtnPreprocessor preprocessor = new HtnPreprocessor(config);
		preprocessor.preprocess(problem, grounder);
		
		// Create index structure for finding all relevant reductions for a given compound task
		methodIndex = new HtnMethodIndex(grounder, grounder.getState(), grounder.getFilteredActions());
		
		// Create table of effectors / supporting tasks for each fact literal in the problem
		effectorTable = new TaskEffectorTable(htnLiftedProblem);
		effectorTable.calculateSupports(grounder.getState(), c -> 
			grounder.getAtomTable().atom(c.getPredicate(), c.getArguments(), false).getId()
		);

		// Grounder of conditions in lifted representation into ground preconditions
		// (needed for creation of reductions from methods)
		Function<AbstractCondition, Precondition> conditionGrounder = (c -> grounder.toPrecondition(c, false));
		
		// Create initial task network reduction
		Method initMethod = htnLiftedProblem.getInitialTaskNetwork();
		initMethod = methodIndex.simplify(initMethod);
		initReduction = new Reduction(initMethod, conditionGrounder);
		
		Logger.log(Logger.INFO, "Creating initial layer ...");
		
		HierarchyLayer initLayer = new HierarchyLayer();
		
		State posState = new State(groundProblem.getInitialState());
		State negState = new State(posState);
		
		// Add all facts at the initial position
		int numAtoms = groundProblem.getNumAtoms();
		int pos = 0;
		for (int p = 0; p < numAtoms; p++) {
			initLayer.addFact(0, p + 1);
		}
		
		// Define each position of the initial layer, using the initial reduction
		for (pos = 0; pos < initReduction.getNumSubtasks(); pos++) {

			addFactsStatus(initLayer, pos, posState, negState);

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
								if (!isActionApplicable(action, posState, negState))
									continue;
								initLayer.addAction(pos, action);
								addActionFacts(action, pos, initLayer);
								occurringActions.add(action);
								if (r.getConstraint(0) != null)
									addCondition(r.getConstraint(0), pos, initLayer);
								if (r.getConstraint(1) != null)
									addCondition(r.getConstraint(1), pos+1, initLayer);
							}
							initLayer.addReduction(pos, r);
							occurringReductions.add(r);							
						}
					}
					reductionMap.put(t, reductions);
				}
			}
			// Add constraints of initTaskNetwork @ pos(+1, if after)
			Precondition p = initReduction.getConstraint(pos);
			addCondition(p, pos, initLayer);

			extendState(occurringActions, occurringReductions, posState, negState);
		}
		// Add all facts at the final position
		for (int p = 0; p < numAtoms; p++) {
			initLayer.addFact(pos, p + 1);
		}
		// "After" constraint of initial task network
		Precondition p = initReduction.getConstraint(pos);
		addCondition(p, pos, initLayer);
		addFactsStatus(initLayer, pos, posState, negState);
		
		// Consolidate initial layer
		globalVariableStart = initLayer.consolidate(globalVariableStart, /*factVariablesToReuse=*/null);
		hierarchyLayers = new ArrayList<>();
		hierarchyLayers.add(initLayer);
	}
	
	public void computeNextLayer() {

		HierarchyLayer lastLayer = hierarchyLayers.get(hierarchyLayers.size() - 1);
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
			List<Reduction> inapplicableReductions = new ArrayList<>();
			for (Reduction r : lastLayer.getReductions(pos)) {
				if (isReductionApplicable(r, 0, posState, negState)) {
					applicableReductions.add(r);
				} else {
					inapplicableReductions.add(r);
				}
			}
			lastLayer.getReductions(pos).removeAll(inapplicableReductions);
			
			boolean children = true;
			while (children) {
				children = false;

				for (Reduction r : applicableReductions) {
					Pair<Integer, Integer> offsetInterval = addChildren(r, successorPos, childIdx, nextLayer, posState,
							negState);
					if (offsetInterval.getRight() > 0)
						children = true;
					minOffset = Math.min(minOffset, offsetInterval.getLeft());
					offset = Math.max(offset, offsetInterval.getRight());
				}
				extendState(nextLayer.getActions(successorPos + childIdx),
						nextLayer.getReductions(successorPos + childIdx), posState, negState);
				addFactsStatus(nextLayer, successorPos + childIdx, posState, negState);

				childIdx++;
			}

			while (minOffset < offset) {
				nextLayer.addAction(successorPos + minOffset, HierarchyLayer.BLANK_ACTION);
				minOffset++;
			}

			successorPos += offset;
			while (factVariablesToReuse.size() < successorPos) {
				factVariablesToReuse.add(new HashMap<>());
			}
		}

		globalVariableStart = nextLayer.consolidate(globalVariableStart, factVariablesToReuse);
		hierarchyLayers.add(nextLayer);
	}

	public List<HierarchyLayer> getHierarchyLayers() {
		return hierarchyLayers;
	}

	public void addActionFacts(Action a, int startPos, HierarchyLayer nextLayer) {

		for (int p = a.getPreconditionsPos().getFirstTrueAtom(); p >= 0; p = a.getPreconditionsPos()
				.getNextTrueAtom(p + 1)) {
			nextLayer.addFact(startPos, p + 1);
		}
		for (int p = a.getPreconditionsNeg().getFirstTrueAtom(); p >= 0; p = a.getPreconditionsNeg()
				.getNextTrueAtom(p + 1)) {
			nextLayer.addFact(startPos, p + 1);
		}
		for (int p = a.getEffectsPos().getFirstTrueAtom(); p >= 0; p = a.getEffectsPos().getNextTrueAtom(p + 1)) {
			nextLayer.addFact(startPos + 1, p + 1);
		}
		for (int p = a.getEffectsNeg().getFirstTrueAtom(); p >= 0; p = a.getEffectsNeg().getNextTrueAtom(p + 1)) {
			nextLayer.addFact(startPos + 1, p + 1);
		}
	}

	public Pair<Integer, Integer> addChildren(Reduction r, int startPos, int childIdx, HierarchyLayer nextLayer,
			State positives, State negatives) {

		int maxOffset = 0;
		int minOffset = nextLayer.getSize();

		if (childIdx >= r.getNumSubtasks())
			return new Pair<>(minOffset, maxOffset);
				
		// Add constraints of r @ parentPos+pos
		addCondition(r.getConstraint(childIdx + 1), startPos + childIdx + 1, nextLayer);
		if (childIdx == 0)
			addCondition(r.getConstraint(childIdx), startPos + childIdx, nextLayer);
		
		String task = r.getSubtask(childIdx);
		Action a = actionMap.get(task);
		if (a != null) {
			if (isActionApplicable(a, positives, negatives)) {
				nextLayer.addAction(startPos + childIdx, a);
				// add preconditions, effects @ parentPos+pos(+1)
				addActionFacts(a, startPos + childIdx, nextLayer);
				maxOffset = Math.max(maxOffset, childIdx + 1);
				minOffset = Math.min(minOffset, 1);
			} else return new Pair<>(minOffset, maxOffset);
		} else {
			List<Method> methods = new ArrayList<>();
			for (Method m : htnLiftedProblem.getMethods()) {
				if (matches(r.getBaseMethod().getSubtasks().get(childIdx), m)) {
					m = m.getMethodBoundToArguments(r.getBaseMethod().getSubtasks().get(childIdx).getArguments(),
							m.getImplicitArguments());
					methods.add(m);
				}
			}
			if (!methods.isEmpty()) {
				List<Reduction> instMethods = methodIndex.getRelevantReductions(methods);
				for (Reduction child : instMethods) {
					if (!isReductionApplicable(child, positives, negatives)) {
						continue;
					}
					if (isReductionPseudoPrimitive(child)) {
						Action action = actionMap.get(child.getSubtask(0)); // pseudoPrimitiveReductionToAction(child);
						if (!isActionApplicable(action, positives, negatives))
							continue;
						nextLayer.addAction(startPos + childIdx, action);
						addActionFacts(action, startPos + childIdx, nextLayer);
						if (child.getConstraint(0) != null)
							addCondition(child.getConstraint(0), startPos + childIdx, nextLayer);
						if (child.getConstraint(1) != null)
							addCondition(child.getConstraint(1), startPos + childIdx+1, nextLayer);
					} 
					nextLayer.addReduction(startPos + childIdx, child);
					maxOffset = Math.max(maxOffset, childIdx + 1);
					minOffset = Math.min(minOffset, childIdx + 1);
				}
				reductionMap.put(task, instMethods);
			}
		}

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
				layer.addFact(pos, pre.getAtom().getId() + 1);
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

	/**
	 * True iff the reduction has expansion size 1 and its sole subtask is an action.
	 */
	public boolean isReductionPseudoPrimitive(Reduction r) {
		return r.getNumSubtasks() == 1 && actionMap.containsKey(r.getSubtask(0));
	}

	/**
	 * True iff the action does not have any preconditions or effects.
	 */
	public boolean isActionNoop(Action action) {
		return action.getPreconditionsPos().getFirstTrueAtom() < 0
				&& action.getPreconditionsNeg().getFirstTrueAtom() < 0 
				&& action.getEffectsPos().getFirstTrueAtom() < 0
				&& action.getEffectsNeg().getFirstTrueAtom() < 0;
	}
	
	/**
	 * Given a pair of states which are the positive and negative hull of the currently possible states,
	 * checks which of the facts may change by one of the provided actions or reductions
	 * and modifies the pair of states accordingly.
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

		// Check atoms which may possibly change by a reduction
		for (boolean value : Arrays.asList(true, false)) {
			
			AtomSet atoms = (value ? positives : negatives).getAtomSet();
			for (int atom = 0; atom < atoms.size(); atom++) {

				if (atoms.get(atom) == value)
					continue; // atom to check is already (un)set

				// Get all tasks which may change the atom from !value to value
				Set<Task> support = effectorTable.getSupportingTasks((value ? 1 : -1) * (atom+1));
				if (support == null) {
					continue;
				}
				
				for (Task t : support) {
					if (methods.containsKey(t.getName())) {
						if (!methods.get(t.getName()).containsPartiallyInstantiatedArgs(t.getArguments())) {
							continue;
						}
						// Supporting task occurs here: extend state
						if (value) {
							positives.getAtomSet().set(atom);
						} else {
							negatives.getAtomSet().unset(atom);
						}
						break;
					} 
				}
			}
		}

		// Apply effects of occurring actions
		for (Action a : actions) {
			for (int atom = a.getEffectsPos().getFirstTrueAtom(); atom >= 0; atom = a.getEffectsPos()
					.getNextTrueAtom(atom + 1)) {
				positives.getAtomSet().set(atom);
			}
			for (int atom = a.getEffectsNeg().getFirstTrueAtom(); atom >= 0; atom = a.getEffectsNeg()
					.getNextTrueAtom(atom + 1)) {
				negatives.getAtomSet().unset(atom);
			}
		}
	}

	/**
	 * Adds information to the provided hierarchical layer on which facts
	 * are constant positive, constant negative, and fluent at the provided position.
	 */
	public void addFactsStatus(HierarchyLayer layer, int pos, State posState, State negState) {
		for (int f = 0; f < groundProblem.getNumAtoms(); f++) {
			if (!posState.getAtomSet().get(f)) {
				// Atom was never positive so far: constant negative
				layer.addFactStatus(pos, f + 1, FactStatus.constantNegative);
			} else if (negState.getAtomSet().get(f)) {
				// Atom was never negative so far: constant positive
				layer.addFactStatus(pos, f + 1, FactStatus.constantPositive);
			} else {
				// Atom can be positive or negative as of now
				layer.addFactStatus(pos, f + 1, FactStatus.fluent);
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
