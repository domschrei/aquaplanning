package edu.kit.aquaplanning.planning.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.htn.HtnGrounder;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer.FactStatus;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayerStatistics;
import edu.kit.aquaplanning.model.ground.htn.Reduction;
import edu.kit.aquaplanning.planning.GroundPlanner;
import edu.kit.aquaplanning.sat.AbstractSatSolver;
import edu.kit.aquaplanning.util.BinaryEncoding;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.util.Pair;

public class TreeRexPlanner extends GroundPlanner {

	public static final int MAX_SPECULATIVE_LAYER_GENERATION = 5;
	
	private HtnGrounder htnGrounder;
	private int depth;

	private AbstractSatSolver solver;
	private Configuration config;
	
	private ActionPlan plan;

	public TreeRexPlanner(Configuration config) {
		super(config);
		this.config = config;
	}

	public ActionPlan findPlan(GroundPlanningProblem problem) {

		Logger.log(Logger.INFO, "Initializing HTN grounding ...");
		htnGrounder = new HtnGrounder(config, problem, grounder);
		Logger.log(Logger.INFO, "Initialized.");
		
		solver = AbstractSatSolver.getSolver(config);

		depth = 0;
		assumptions = new ArrayList<>();
		
		plan = null;
		try {
			if (config.numThreads >= 2) {
				plan = runInParallel();
			} else {
				plan = runSequentially();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
				
		return plan;
	}
	
	private ActionPlan runInParallel() throws InterruptedException {
		
		layerQueue = new LinkedBlockingQueue<>();
		
		Thread extensionThread = new Thread(layerExtensionProcedure());
		Thread resolutionThread = new Thread(resolutionProcedure(extensionThread));
		
		extensionThread.start();
		resolutionThread.start();
		
		resolutionThread.join();
		
		if (plan != null && config.optimizePlan) {
			plan = optimizePlan();
		}
		solver.release();
		
		return plan;
	}
	
	private ActionPlan runSequentially() throws InterruptedException {
		
		Logger.log(Logger.INFO, "Generating depth " + depth);

		HierarchyLayer newLayer = htnGrounder.getHierarchyLayers().get(depth);
		HierarchyLayerStatistics statistics = newLayer.collectStatistics();
		//System.out.println(newLayer);
		System.out.println(statistics);

		encodeInitialLayer(newLayer);

		Logger.log(Logger.INFO, "Solving ...");
		Boolean result = solve();

		while (result == false) {

			depth++;
			Logger.log(Logger.INFO, "Generating depth " + depth);
			HierarchyLayer oldLayer = newLayer;
			htnGrounder.computeNextLayer();
			newLayer = htnGrounder.getHierarchyLayers().get(depth);
			System.out.println(newLayer);
			newLayer.extendStatistics(statistics);
			System.out.println(statistics);

			encodeNextLayer(oldLayer, newLayer);

			Logger.log(Logger.INFO, "Solving ...");
			result = solve();
		}
		
		if (result == true) {
			Logger.log(Logger.INFO, "Extracting plan ...");
			plan = extractPlan();
			
			if (plan != null && config.optimizePlan) {
				plan = optimizePlan();
			}
			
			solver.release();
			return plan;
		} else {
			return null;
		}
	}
	
	private ActionPlan optimizePlan() {
		
		HierarchyLayer finalLayer = htnGrounder.getHierarchyLayers().get(depth);
		addPlanLengthOptimizationClauses(finalLayer);
		
		ActionPlan plan = this.plan;
		
		Thread hook = new Thread(() -> {
			if (this.plan != null) {
				Logger.log(Logger.INFO, "Received termination signal; printing best found plan.");
				System.out.println(this.plan);
			}
		});
		Runtime.getRuntime().addShutdownHook(hook);
		
		int planLength = plan.getLength();
		while (true) {
			System.out.println("Plan length : " + planLength);
			assumeSmallerPlanLength(finalLayer, planLength);
			Boolean result = solve();
			if (result == true) {
				plan = extractPlan();
				this.plan = plan;
				planLength = plan.getLength();
			} else {
				System.out.println("Plan length " + planLength + " is optimal at this layer.");
				break;
			}
		}
		
		Runtime.getRuntime().removeShutdownHook(hook);
		
		return plan;
	}
	
	private void encodeInitialLayer(HierarchyLayer layer) throws InterruptedException {
		
		Logger.log(Logger.INFO, "[RES] Adding initial clauses ...");

		// Initial clauses (init task network)
		addInitTaskNetwork(layer, htnGrounder.getInitReduction());
		addInitState(layer);
		addGoal(layer);

		// Universal clauses
		for (int pos = 0; pos + 1 < layer.getSize(); pos++) {
			addReductionClauses(layer, pos);
			Map<Integer, List<Action>> support = addActionClauses(layer, pos);
			addFrameAxioms(layer, pos, support, null);
		}

		// Goal assumption
		assumeFinishedPlan(layer);
		
		Logger.log(Logger.INFO, "[RES] Clauses added.");
	}
	
	private void encodeNextLayer(HierarchyLayer oldLayer, HierarchyLayer newLayer) throws InterruptedException {
		
		Logger.log(Logger.INFO, "[RES] Adding clauses ...");

		// Transitional clauses oldLayer -> newLayer
		addPropagations(oldLayer, newLayer);
		
		for (int oldPos = 0; oldPos+1 < oldLayer.getSize(); oldPos++) {
			Pair<List<Map<Reduction, Set<Reduction>>>, 
				List<Map<Action, Set<Reduction>>>> predecessors = addExpansions(oldLayer, newLayer, oldPos);
			addPredecessors(oldLayer, newLayer, oldPos, predecessors.getLeft(), predecessors.getRight());			
		}

		// Universal clauses for new layer
		for (int pos = 0; pos + 1 < newLayer.getSize(); pos++) {
			addReductionClauses(newLayer, pos);
			Map<Integer, List<Action>> support = addActionClauses(newLayer, pos);
			addFrameAxioms(newLayer, pos, support, null);
		}

		// Goal assumption
		assumeFinishedPlan(newLayer);
		
		Logger.log(Logger.INFO, "[RES] Clauses added.");
	}

	private void addInitState(HierarchyLayer layer) {

		tagNextClauses(ClauseTag.INIT_STATE);
		State initState = htnGrounder.getGroundProblem().getInitialState();
		List<Boolean> atoms = initState.getAtoms();
		for (int i = 0; i < atoms.size(); i++) {
			if (layer.isFactFluent(0, i + 1))
				addClause((atoms.get(i) ? 1 : -1) * layer.getFactVariable(0, i + 1));
		}
	}

	private void addGoal(HierarchyLayer layer) {
		
		tagNextClauses(ClauseTag.GOAL_STATE);
		Goal goal = htnGrounder.getGroundProblem().getGoal();
		for (Atom atom : goal.getAtoms()) {
			if (layer.isFactFluent(0, atom.getId() + 1))
				addClause((atom.getValue() ? 1 : -1) * layer.getFactVariable(layer.getSize() - 1, atom.getId() + 1));
		}
	}

	/**
	 * Add all before/after/between constraints and non-primitiveness of a reduction
	 */
	private void addInitTaskNetwork(HierarchyLayer layer, Reduction r) {

		for (int p = 0; p < r.getNumSubtasks(); p++) {

			tagNextClauses(ClauseTag.INIT_TASKS);
			String subtask = r.getSubtask(p);
			boolean added = false;
			Action a = htnGrounder.getAction(subtask);
			if (a != null) {
				addToClause(layer.getActionVariable(p, a));
				added = true;
			}
			for (Reduction red : htnGrounder.getReductions(subtask)) {
				if (layer.contains(p, red)) {
					addToClause(layer.getReductionVariable(p, red));
					added = true;
				}
			}
			if (!added) {
				Logger.log(Logger.ERROR, "Position " + p + " of the initial task network is empty.");
				System.exit(1);
			}
			finishClause();
			
			tagNextClauses(ClauseTag.REDUCTION_CONSTRAINTS);
			Precondition constraint = r.getConstraint(p);
			addCondition(layer, p, constraint, 0);
		}
		
		Precondition constraint = r.getConstraint(r.getNumSubtasks());
		addCondition(layer, r.getNumSubtasks(), constraint, 0);
	}

	/**
	 * Add all before/after/between constraints and non-primitiveness of reductions
	 * @throws InterruptedException 
	 */
	private void addReductionClauses(HierarchyLayer layer, int pos) throws InterruptedException {

		List<Integer> reductionVars = new ArrayList<>();
		
		layer.mutex(pos).acquire();

		reductionLoop: for (Reduction r : layer.getReductions(pos)) {
			
			tagNextClauses(ClauseTag.REDUCTION_NONPRIMITIVENESS);
			if (htnGrounder.isReductionPseudoPrimitive(r)) {
				
				// If the reduction is pseudo-primitive, then the position counts as primitive
				// and its according action co-occurs there
				
				tagNextClauses(ClauseTag.REDUCTION_CONSTRAINTS);
				
				Action action = htnGrounder.getAction(r.getSubtask(0));
				if (action == null || !layer.contains(pos, action)) {
					addClause(-layer.getReductionVariable(pos, r));
					continue;
				}
				
				for (int i = 0; i <= 1; i++) {
					if (r.getConstraint(i) == null)
						continue;
					/*
					if (!checkCondition(layer, pos+i, r.getConstraint(i))) {							
						addClause(-layer.getReductionVariable(pos, r));
						continue reductionLoop;
					}*/
					addCondition(layer, pos+i, r.getConstraint(i), -layer.getReductionVariable(pos, r));
				}
				
				addClause(-layer.getReductionVariable(pos, r), layer.getActionVariable(pos, action));
				
			} else {	
				// If a "proper" reduction occurs, the position is non-primitive
				addClause(-layer.getReductionVariable(pos, r), -layer.getPrimitivenessVariable(pos));
			}
			
			reductionVars.add(layer.getReductionVariable(pos, r));
		}
		layer.mutex(pos).release();
		
		if (HierarchyLayer.ADD_REDUCTION_AMO)
			addAtMostOne(reductionVars, layer.getReductionBinaryEncoding(pos));
	}

	/**
	 * Add primitiveness, preconditions and effects of each action and amo over
	 * actions Returns the supporting actions for each fact at that position
	 */
	private Map<Integer, List<Action>> addActionClauses(HierarchyLayer layer, int pos) {

		Map<Integer, List<Action>> supportingActions = new HashMap<>();

		for (Action a : layer.getActions(pos)) {

			tagNextClauses(ClauseTag.ACTION_PRIMITIVENESS);
			// If the action occurs, the position is primitive
			addClause(-layer.getActionVariable(pos, a), layer.getPrimitivenessVariable(pos));
			
			// Add preconditions and effects
			List<Integer> facts = new ArrayList<>();
			/*
			if (!checkCondition(layer, pos, a.getPreconditionsPos(), true)
					|| !checkCondition(layer, pos, a.getPreconditionsNeg(), false)) {
				// Action not applicable
				addClause(-layer.getActionVariable(pos, a));
				continue;
			}*/
			tagNextClauses(ClauseTag.ACTION_CONDITIONS);
			addCondition(layer, pos, a.getPreconditionsPos(), -layer.getActionVariable(pos, a), true, null);
			addCondition(layer, pos, a.getPreconditionsNeg(), -layer.getActionVariable(pos, a), false, null);
			addCondition(layer, pos + 1, a.getEffectsPos(), -layer.getActionVariable(pos, a), true, facts);
			addCondition(layer, pos + 1, a.getEffectsNeg(), -layer.getActionVariable(pos, a), false, facts);

			// Add action as support to all facts that may change due to it
			for (int fact : facts) {
				List<Action> support = supportingActions.getOrDefault(fact, new ArrayList<>());
				support.add(a);
				supportingActions.put(fact, support);
			}
		}

		List<Action> actions = new ArrayList<>();
		actions.addAll(layer.getActions(pos));

		tagNextClauses(ClauseTag.ACTION_AMO);
		List<Integer> actionVars = new ArrayList<>();
		for (Action action : actions) {
			actionVars.add(layer.getActionVariable(pos, action));
		}
		BinaryEncoding enc = layer.getActionBinaryEncoding(pos);
		addAtMostOne(actionVars, enc);
		
		return supportingActions;
	}

	/**
	 * Adds frame axioms from pos to pos+1.
	 */
	private void addFrameAxioms(HierarchyLayer layer, int pos, Map<Integer, List<Action>> supportingActions,
			Map<Integer, List<Reduction>> supportingReductions) {

		tagNextClauses(ClauseTag.FRAME_AXIOMS);
		
		for (int fact : layer.getFacts(pos + 1)) {

			if (!layer.isFactFluent(pos + 1, fact)) {
				continue;
			}

			int posBefore = layer.getLatestPositionOfFact(pos, fact);
			
			// NEGATIVE -> POSITIVE
			if (layer.getFactStatus(posBefore, fact) != FactStatus.constantPositive
					&& layer.getFactStatus(pos + 1, fact) != FactStatus.constantNegative) {

				if (layer.isFactFluent(posBefore, fact))
					addToClause(layer.getFactVariable(posBefore, fact));
				addToClause(-layer.getFactVariable(pos + 1, fact));
				if (supportingReductions == null) {
					for (int p = posBefore; p <= pos; p++) {
						addToClause(-layer.getPrimitivenessVariable(p));
					}
				} else {
					for (Reduction r : supportingReductions.getOrDefault(fact, new ArrayList<>())) {
						addToClause(layer.getReductionVariable(pos, r));
					}
				}
				for (Action a : supportingActions.getOrDefault(fact, new ArrayList<>())) {
					addToClause(layer.getActionVariable(pos, a));
				}
				finishClause();
			}

			// POSITIVE -> NEGATIVE
			if (layer.getFactStatus(posBefore, fact) != FactStatus.constantNegative
					&& layer.getFactStatus(pos + 1, fact) != FactStatus.constantPositive) {

				if (layer.isFactFluent(posBefore, fact))
					addToClause(-layer.getFactVariable(posBefore, fact));
				addToClause(layer.getFactVariable(pos + 1, fact));
				if (supportingReductions == null) {
					for (int p = posBefore; p <= pos; p++) {
						addToClause(-layer.getPrimitivenessVariable(p));
					}
				} else {
					for (Reduction r : supportingReductions.getOrDefault(fact, new ArrayList<>())) {
						addToClause(layer.getReductionVariable(pos, r));
					}
				}
				for (Action a : supportingActions.getOrDefault(-fact, new ArrayList<>())) {
					addToClause(layer.getActionVariable(pos, a));
				}
				finishClause();
			}
		}
	}

	/**
	 * Primitivity, actions, facts
	 */
	private void addPropagations(HierarchyLayer oldLayer, HierarchyLayer newLayer) {

		for (int pos = 0; pos < oldLayer.getSize(); pos++) {
			int successorPos = oldLayer.getSuccessorPosition(pos);

			tagNextClauses(ClauseTag.ACTION_PROPAGATION);
			
			addClause(-oldLayer.getPrimitivenessVariable(pos), newLayer.getPrimitivenessVariable(successorPos));

			for (Action a : oldLayer.getActions(pos)) {
				if (!newLayer.contains(successorPos, a)) {
					// Action turned out to be invalid
					addClause(-oldLayer.getActionVariable(pos, a));
					continue;
				}
				addClause(-oldLayer.getActionVariable(pos, a), newLayer.getActionVariable(successorPos, a));
			}
			
			tagNextClauses(ClauseTag.FACT_PROPAGATION);
			
			// Propagation of facts from previous layer to the next layer 
			for (int fact : oldLayer.getFacts(pos)) {
				if (oldLayer.getFactStatus(pos, fact) == FactStatus.constantPositive) { 
					if (newLayer.isFactFluent(successorPos, fact)) {
						addClause(newLayer.getFactVariable(successorPos, fact));
					}
				} else if (oldLayer.getFactStatus(pos, fact) == FactStatus.constantNegative) { 
					if (newLayer.isFactFluent(successorPos, fact)) {
						addClause(-newLayer.getFactVariable(successorPos, fact));
					}
				} else { 
					if (newLayer.isFactFluent(successorPos, fact)) { 
						int oldVar = oldLayer.getFactVariable(pos, fact); 
						int newVar = newLayer.getFactVariable(successorPos, fact);
						if (oldVar != newVar) {
							addClause(oldVar, -newVar);
							addClause(-oldVar, newVar); 
						}
					} 
				} 
			}
		}
	}

	/**
	 * Reductions
	 * @throws InterruptedException 
	 */
	private Pair<List<Map<Reduction, Set<Reduction>>>, List<Map<Action, Set<Reduction>>>> 
					addExpansions(HierarchyLayer oldLayer, HierarchyLayer newLayer, int oldPos) throws InterruptedException {
		
		int successorPos = oldLayer.getSuccessorPosition(oldPos);
		int maxExpansionSize;
		if (oldLayer.getSuccessorPosition(oldPos+1) >= 0)
			maxExpansionSize = oldLayer.getSuccessorPosition(oldPos + 1) - successorPos;
		else
			maxExpansionSize = newLayer.getSize() - successorPos;
				
		List<Map<Reduction, Set<Reduction>>> reductionPredecessors = new ArrayList<>();
		List<Map<Action, Set<Reduction>>> actionPredecessors = new ArrayList<>();
		for (int p = 0; p < maxExpansionSize; p++) {
			reductionPredecessors.add(new HashMap<>());
			actionPredecessors.add(new HashMap<>());
		}
		
		oldLayer.mutex(oldPos).acquire();
		
		loopReductions: for (Reduction r : oldLayer.getReductions(oldPos)) {
			
			tagNextClauses(ClauseTag.REDUCTION_CONSTRAINTS);
			// Add reduction's constraints
			for (int p = 0; p <= r.getNumSubtasks(); p++) {
				Precondition constraint = r.getConstraint(p);
				/*
				if (!checkCondition(newLayer, successorPos + p, constraint)) {
					addClause(-oldLayer.getReductionVariable(oldPos, r));
					continue loopReductions;
				}*/
				addCondition(newLayer, successorPos + p, constraint, -oldLayer.getReductionVariable(oldPos, r));
			}
			
			tagNextClauses(ClauseTag.REDUCTION_EXPANSION);
			
			int p = 0;
			for (; p < r.getNumSubtasks(); p++) {
				if (htnGrounder.isReductionPseudoPrimitive(r)) {
					Action action = htnGrounder.getAction(r.getSubtask(0));
					if (!actionPredecessors.get(0).containsKey(action)) {
						actionPredecessors.get(0).put(action, new HashSet<>());
					}
					actionPredecessors.get(0).get(action).add(r);
					continue;
				}
				String subtask = r.getSubtask(p);
				addToClause(-oldLayer.getReductionVariable(oldPos, r));
				Action action = htnGrounder.getAction(subtask);
				if (action != null && newLayer.contains(successorPos + p, action)) {
					addToClause(newLayer.getActionVariable(successorPos + p, action));
					if (!actionPredecessors.get(p).containsKey(action)) {
						actionPredecessors.get(p).put(action, new HashSet<>());
					}
					actionPredecessors.get(p).get(action).add(r);
				}
				for (Reduction red : htnGrounder.getReductions(subtask)) {
					if (newLayer.contains(successorPos + p, red)) {
						
						if (htnGrounder.isReductionPseudoPrimitive(red)) {
							action = htnGrounder.getAction(red.getSubtask(0));
							if (!actionPredecessors.get(p).containsKey(action)) {
								actionPredecessors.get(p).put(action, new HashSet<>());
							}
							actionPredecessors.get(p).get(action).add(r);
						}
						
						addToClause(newLayer.getReductionVariable(successorPos + p, red));
						if (!reductionPredecessors.get(p).containsKey(red)) {
							reductionPredecessors.get(p).put(red, new HashSet<>());
						}
						reductionPredecessors.get(p).get(red).add(r);
					}
				}
				finishClause();
			}
			
			// Fill empty positions with blank actions
			for (; p < maxExpansionSize; p++) {
				
				Action blank = HierarchyLayer.BLANK_ACTION;
				addClause(-oldLayer.getReductionVariable(oldPos, r),
						newLayer.getActionVariable(successorPos + p, blank));
				if (!actionPredecessors.get(p).containsKey(blank)) {
					actionPredecessors.get(p).put(blank, new HashSet<>());
				}
				actionPredecessors.get(p).get(blank).add(r);
			}
		}
		oldLayer.mutex(oldPos).release();
		
		return new Pair<>(reductionPredecessors, actionPredecessors);
	}
	
	private void addPredecessors(HierarchyLayer oldLayer, HierarchyLayer newLayer, int oldPos,
			List<Map<Reduction, Set<Reduction>>> reductionPredecessors, 
			List<Map<Action, Set<Reduction>>> actionPredecessors) throws InterruptedException {
		
		tagNextClauses(ClauseTag.REDUCTION_PREDECESSORS);
		
		int maxExpansionSize;
		if (oldLayer.getSuccessorPosition(oldPos+1) >= 0)
			maxExpansionSize = oldLayer.getSuccessorPosition(oldPos+1) - oldLayer.getSuccessorPosition(oldPos);
		else
			maxExpansionSize = newLayer.getSize() - oldLayer.getSuccessorPosition(oldPos);
		
		for (int succPos = oldLayer.getSuccessorPosition(oldPos); 
				succPos < oldLayer.getSuccessorPosition(oldPos)+maxExpansionSize; succPos++) {
			
			int offset = succPos - oldLayer.getSuccessorPosition(oldPos);
			
			Map<Reduction, Set<Reduction>> pred = reductionPredecessors.get(offset);
			Map<Action, Set<Reduction>> actionPred = actionPredecessors.get(offset);
			
			newLayer.mutex(succPos).acquire();
			for (Reduction child : newLayer.getReductions(succPos)) {
				
				addToClause(-newLayer.getReductionVariable(succPos, child));
				for (Reduction parent : pred.getOrDefault(child, new HashSet<>())) {
					if (oldLayer.contains(oldPos, parent)) {
						addToClause(oldLayer.getReductionVariable(oldPos, parent));
					}
				}
				finishClause();
			}
			newLayer.mutex(succPos).release();
			
			for (Action child : newLayer.getActions(succPos)) {
				//System.out.print(child + "@" + succPos + " : ");
				addToClause(-newLayer.getActionVariable(succPos, child));
				if ((child.equals(HierarchyLayer.BLANK_ACTION) || offset == 0) 
						&& oldLayer.contains(oldPos, child)) {
					addToClause(oldLayer.getActionVariable(oldPos, child));
					//System.out.print("old ");
				}
				if (offset > 0 && child.equals(HierarchyLayer.BLANK_ACTION)) {
					addToClause(oldLayer.getPrimitivenessVariable(oldPos));
					//System.out.print("already_primitive ");
				}
				for (Reduction parent : actionPred.getOrDefault(child, new HashSet<>())) {
					if (oldLayer.contains(oldPos, parent)) {
						addToClause(oldLayer.getReductionVariable(oldPos, parent));
						//System.out.print(parent + " ");
					}
				}
				finishClause();
				//System.out.println();
			}
		}
	}
	
	private void addAtMostOne(List<Integer> variables, BinaryEncoding enc) {
		
		if (enc == null) {
			// Pairwise At-Most-One constraints
			for (int i = 0; i < variables.size(); i++) {
				for (int j = i + 1; j < variables.size(); j++) {
					addClause(-variables.get(i),
							-variables.get(j));
				}
			}
		} else {
			// Binary At-Most-One constraints
			enc.getForbiddenValues().forEach(clause -> addClause(clause));
			for (int i = 0; i < variables.size(); i++) {
				int lit = -variables.get(i);

				int[] bitLits = enc.posLiterals(i);
				for (int bitLit : bitLits) {
					addClause(lit, bitLit);
				}
			}
		}
	}

	private void assumeFinishedPlan(HierarchyLayer newLayer) {

		for (int pos = 0; pos < newLayer.getSize(); pos++) {
			addAssumption(newLayer.getPrimitivenessVariable(pos));
		}
	}
	
	private void addPlanLengthOptimizationClauses(HierarchyLayer finalLayer) {
		
		tagNextClauses(ClauseTag.PLAN_COUNTER);
		
		for (int pos = 0; pos < finalLayer.getSize(); pos++) {
			addClause(finalLayer.getPrimitivenessVariable(pos));
		}
		
		addClause(varPlanLengthGreaterEquals(0, 0));
		
		for (int pos = 1; pos < finalLayer.getSize(); pos++) {
			for (int x = 0; x < pos; x++) {
				
				List<Action> noopActions = new ArrayList<>();
				for (Action action : finalLayer.getActions(pos-1)) {
					if (htnGrounder.isActionNoop(action)) {
						noopActions.add(action);
					}
				}

				// Plan length does not decrease
				addClause(-varPlanLengthGreaterEquals(pos-1, x), varPlanLengthGreaterEquals(pos, x));
				
				// Plan length increases if proper action is present
				addToClause(-varPlanLengthGreaterEquals(pos-1, x));
				for (Action noop : noopActions) {
					addToClause(finalLayer.getActionVariable(pos-1, noop));
				}
				addToClause(varPlanLengthGreaterEquals(pos, x+1));
				finishClause();
			}
		}
	}
	
	private void assumeSmallerPlanLength(HierarchyLayer finalLayer, int x) {
		addAssumption(-varPlanLengthGreaterEquals(finalLayer.getSize()-1, x));
	}
	
	private int varPlanLengthGreaterEquals(int pos, int x) {
		if (x > pos) throw new RuntimeException();
		int var = htnGrounder.getGlobalVariableCount();
		for (int p = 0; p < pos; p++) var += p+1;
		return var+x;
	}

	private boolean checkCondition(HierarchyLayer layer, int pos, Precondition cond) {

		List<Precondition> conditions = new ArrayList<>();
		conditions.add(cond);
		for (int i = 0; i < conditions.size(); i++) {
			Precondition pre = conditions.get(i);
			if (pre.getType() == PreconditionType.atom) {
				Atom atom = pre.getAtom();
				int fact = atom.getId() + 1;
				if (!checkCondition(layer, pos, fact, atom.getValue())) {
					return false;
				}
			} else if (pre.getType() == PreconditionType.conjunction) {
				conditions.addAll(pre.getChildren());
			}
		}
		return true;
	}

	private boolean checkCondition(HierarchyLayer layer, int pos, AtomSet atoms, boolean sign) {

		for (int idx = atoms.getFirstTrueAtom(); idx >= 0; idx = atoms.getNextTrueAtom(idx + 1)) {
			int fact = (sign ? 1 : -1) * (idx + 1);
			if (!checkCondition(layer, pos, fact, sign)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkCondition(HierarchyLayer layer, int pos, int fact, boolean sign) {

		if (layer.getFactStatus(pos, fact) == (sign ? FactStatus.constantNegative : FactStatus.constantPositive)) {
			return false;
		}
		if (layer.getFactStatus(pos, fact) == (!sign ? FactStatus.constantNegative : FactStatus.constantPositive)) {
			return true;
		}
		
		int posBefore = layer.getLatestPositionOfFactVariable(pos, Math.abs(fact));
		if (posBefore < 0)
			return false;
		if (posBefore >= 0 && posBefore < pos) {
			throw new RuntimeException();
			//return checkCondition(layer, posBefore, fact, sign);
		}
		
		return true;
	}

	private void addCondition(HierarchyLayer layer, int pos, Precondition cond, int firstLit) {

		List<Precondition> conditions = new ArrayList<>();
		conditions.add(cond);
		for (int i = 0; i < conditions.size(); i++) {
			Precondition pre = conditions.get(i);
			if (pre.getType() == PreconditionType.atom) {
				Atom atom = pre.getAtom();
				int fact = atom.getId() + 1;

				if (!layer.isFactFluent(pos, fact)) {
					continue;
				}
				int posBefore = layer.getLatestPositionOfFactVariable(pos, fact);
				if (posBefore < pos) {
					System.out.println(atom + " : " + posBefore + " < " + pos);
					throw new RuntimeException();
					//continue;
					/*if (posBefore < 0 || !layer.isFactFluent(posBefore, fact)) {
						continue;
					}*/
				}
				
				if (firstLit != 0) {
					addToClause(firstLit);
				}
				addToClause((atom.getValue() ? 1 : -1) * layer.getFactVariable(posBefore, fact));
				finishClause();

			} else if (pre.getType() == PreconditionType.conjunction) {
				conditions.addAll(pre.getChildren());
			} else {
				throw new RuntimeException("Invalid precondition type");
			}
		}
	}

	private void addCondition(HierarchyLayer layer, int pos, AtomSet atoms, int firstLit, boolean sign,
			List<Integer> facts) {

		for (int idx = atoms.getFirstTrueAtom(); idx >= 0; idx = atoms.getNextTrueAtom(idx + 1)) {
			int fact = (sign ? 1 : -1) * (idx + 1);

			if (!layer.isFactFluent(pos, fact)) {
				continue;
			}
			int posBefore = layer.getLatestPositionOfFactVariable(pos, fact);
			if (posBefore < pos) {
				System.out.println(fact + " : " + posBefore + " < " + pos);
				throw new RuntimeException();
				//continue;
				/*if (posBefore < 0 || !layer.isFactFluent(posBefore, fact)) {
					continue;
				}*/
			}

			if (firstLit != 0) {
				addToClause(firstLit);
			}
			addToClause(layer.getFactVariable(posBefore, fact));
			finishClause();

			if (facts != null)
				facts.add(fact);
		}
	}

	List<Integer> currentClause;
	List<Integer> assumptions;
	int addedClauses = 0;
	ClauseTag clauseTag;
	enum ClauseTag {
		INIT_TASKS(0), INIT_STATE(1), GOAL_STATE(2), ACTION_CONDITIONS(3), REDUCTION_CONSTRAINTS(4), FACT_PROPAGATION(5), ACTION_PROPAGATION(6), REDUCTION_EXPANSION(7), 
		ACTION_PRIMITIVENESS(8), REDUCTION_NONPRIMITIVENESS(9), ACTION_AMO(10), FRAME_AXIOMS(11), REDUCTION_PREDECESSORS(12), PLAN_COUNTER(13);
		ClauseTag(int i) {
			this.i = i;
		}
		private final int i;
		public int index() {return i;};
	}
	int[] addedClausesByTag;
	
	private void tagNextClauses(ClauseTag tag) {
		clauseTag = tag;
		if (addedClausesByTag == null) {
			addedClausesByTag = new int[ClauseTag.values().length];
			for (int i = 0; i < addedClausesByTag.length; i++) {
				addedClausesByTag[i] = 0;
			}
		}
		addedClausesByTag[clauseTag.index()]++;
	}
	
	private void addToClause(int lit) {
		if (currentClause == null)
			currentClause = new ArrayList<>();
		currentClause.add(lit);
	}

	private void finishClause() {
		int[] array = currentClause.stream().mapToInt(i -> i).toArray();
		addClause(array);
		currentClause = null;
		addedClauses++;
		if (clauseTag != null)
			addedClausesByTag[clauseTag.index()]++;
	}

	private void addClause(int... lits) {
		solver.addClause(lits);
		addedClauses++;
		if (clauseTag != null)
			addedClausesByTag[clauseTag.index()]++;
	}

	private void addAssumption(int lit) {
		solver.addAssumption(lit);
	}

	private Boolean solve() {
		Logger.log(Logger.INFO_V, "[RES] Added clauses by type:");
		for (ClauseTag tag : ClauseTag.values()) {
			Logger.log(Logger.INFO_V, (tag.toString() + "        ").substring(0, 12) + "\t" + addedClausesByTag[tag.index()]);
		}
		Boolean result = solver.isSatisfiable();
		Logger.log(Logger.INFO, "[RES] Solver returned " + (result != null && result ? "SAT" : "UNSAT") + ".");
		return result;
	}

	private ActionPlan extractPlan() {
		ActionPlan plan = new ActionPlan();

		HierarchyLayer finalLayer = htnGrounder.getHierarchyLayers().get(depth);
		for (int pos = 0; pos < finalLayer.getSize(); pos++) {
			for (Action a : finalLayer.getActions(pos)) {
				if (htnGrounder.isActionNoop(a))
					continue;
				int actionVar = finalLayer.getActionVariable(pos, a);
				if (solver.getValue(actionVar) > 0) {
					plan.appendAtBack(a);
				}
			}
		}

		return plan;
	}
	
	private LinkedBlockingQueue<HierarchyLayer> layerQueue;
	
	private Runnable layerExtensionProcedure() {
		return () -> {
			try {
				int depth = 0;
				while (!Thread.interrupted()) {
					HierarchyLayer layer = htnGrounder.getHierarchyLayers().get(depth);
					boolean printedWaitingMsg = false;
					while (layerQueue.size() >= MAX_SPECULATIVE_LAYER_GENERATION) {
						Thread.sleep(100);
						if (!printedWaitingMsg) {
							Logger.log(Logger.INFO_V, "[EXT] Waiting for previous layers to be solved ...");
							printedWaitingMsg = true;
						}
					}
					layerQueue.put(layer);
					//System.out.println(layer);
					HierarchyLayerStatistics statistics = layer.collectStatistics();
					Logger.log(Logger.INFO_V, "[EXT] " + statistics.toString());
					Logger.log(Logger.INFO_V, "[EXT] Generating layer #" + depth + " ...");
					boolean completed = htnGrounder.computeNextLayer();
					if (!completed)
						throw new InterruptedException();
					Logger.log(Logger.INFO_V, "[EXT] Layer #" + depth + " generated.");
					depth++;
				}
			} catch (InterruptedException e) {
				Logger.log(Logger.INFO_V, "[EXT] Thread interrupted.");
			}
		};
	}
	
	private Runnable resolutionProcedure(Thread extensionThread) {
		return () -> {
			try {
				depth = 0;
				HierarchyLayer newLayer;
				if (layerQueue.isEmpty())
					Logger.log(Logger.INFO_V, "[RES] Waiting for next layer to become available ...");
				newLayer = layerQueue.take();
				encodeInitialLayer(newLayer);
				Boolean result = solve();
				HierarchyLayer oldLayer = newLayer;
				while (result != true && !Thread.interrupted()) {
					depth++;
					oldLayer = newLayer;
					Logger.log(Logger.INFO_V, "[RES] Encoding layer #" + depth + " ...");
					newLayer = layerQueue.take();
					encodeNextLayer(oldLayer, newLayer);
					Logger.log(Logger.INFO_V, "[RES] Solving layer #" + depth + " ...");
					result = solve();
				}
				if (result == true) {
					Logger.log(Logger.INFO, "[RES] Extracting plan ...");
					plan = extractPlan();
					extensionThread.interrupt();
				}
			} catch (InterruptedException e) {
				Logger.log(Logger.INFO_V, "[RES] Thread interrupted.");
			}
		};
	}
}
