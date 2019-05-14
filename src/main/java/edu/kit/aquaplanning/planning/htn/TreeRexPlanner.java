package edu.kit.aquaplanning.planning.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.htn.HtnGrounder;
import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Atom;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.Goal;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.ground.State;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayer.FactStatus;
import edu.kit.aquaplanning.model.ground.htn.HierarchyLayerStatistics;
import edu.kit.aquaplanning.model.ground.htn.Reduction;
import edu.kit.aquaplanning.sat.AbstractSatSolver;
import edu.kit.aquaplanning.util.BinaryEncoding;
import edu.kit.aquaplanning.util.Logger;

public class TreeRexPlanner {

	private HtnGrounder grounder;
	private int depth;

	private AbstractSatSolver solver;
	private Configuration config;
	
	public TreeRexPlanner(Configuration config, HtnGrounder grounder) {
		this.grounder = grounder;
		this.config = config;
	}
	
	public Plan findPlan() {
		
		solver = AbstractSatSolver.getSolver(config);
		
		depth = 0;
		assumptions = new ArrayList<>();
		
		Logger.log(Logger.INFO, "Generating depth " + depth);

		HierarchyLayer newLayer = grounder.getHierarchyLayers().get(depth);
		HierarchyLayerStatistics statistics = newLayer.collectStatistics();
		System.out.println(newLayer);
		System.out.println(statistics);
		
		Logger.log(Logger.INFO, "Adding clauses ...");
		
		// Initial clauses (init task network)
		addInitTaskNetwork(newLayer, grounder.getInitReduction());
		addInitState(newLayer);
		addGoal(newLayer);
		
		// Universal clauses
		for (int pos = 0; pos+1 < newLayer.getSize(); pos++) {
			addReductionClauses(newLayer, pos);
			Map<Integer, List<Action>> support = addActionClauses(newLayer, pos);
			addFrameAxioms(newLayer, pos, support, null);
		}
		
		// Goal assumption
		assumeFinishedPlan(newLayer);
		
		Logger.log(Logger.INFO, "Solving ...");
		Boolean result = solve();
		
		while (result == false) {
			
			depth++;
			Logger.log(Logger.INFO, "Generating depth " + depth);
			HierarchyLayer oldLayer = newLayer;
			grounder.computeNextLayer();
			newLayer = grounder.getHierarchyLayers().get(depth);
			System.out.println(newLayer);
			newLayer.extendStatistics(statistics);
			System.out.println(statistics);
			
			Logger.log(Logger.INFO, "Adding clauses ...");
			
			// Transitional clauses oldLayer -> newLayer
			addPropagations(oldLayer, newLayer);
			addExpansions(oldLayer, newLayer);
			
			// Universal clauses for new layer
			for (int pos = 0; pos+1 < newLayer.getSize(); pos++) {
				addReductionClauses(newLayer, pos);
				Map<Integer, List<Action>> support = addActionClauses(newLayer, pos);
				addFrameAxioms(newLayer, pos, support, null);
			}
			
			// Goal assumption
			assumeFinishedPlan(newLayer);

			Logger.log(Logger.INFO, "Solving ...");
			result = solve();
		}
		
		if (result == true) {
			Logger.log(Logger.INFO, "Extracting plan ...");
			Plan plan = extractPlan();
			solver.release();
			return plan;
		} else {
			return null;
		}
	}
	
	private void addInitState(HierarchyLayer layer) {
		
		State initState = grounder.getGroundProblem().getInitialState();
		List<Boolean> atoms = initState.getAtoms();
		for (int i = 0; i < atoms.size(); i++) {
			if (layer.isFactFluent(0, i+1))
				addClause((atoms.get(i) ? 1 : -1) * layer.getFactVariable(0, i+1));
		}
	}
	
	private void addGoal(HierarchyLayer layer) {
		
		Goal goal = grounder.getGroundProblem().getGoal();
		for (Atom atom : goal.getAtoms()) {
			if (layer.isFactFluent(0, atom.getId()+1))
				addClause((atom.getValue() ? 1 : -1) * layer.getFactVariable(layer.getSize()-1, atom.getId()+1));
		}
	}
	
	/**
	 * Add all before/after/between constraints and non-primitiveness of a reduction
	 */
	private void addInitTaskNetwork(HierarchyLayer layer, Reduction r) {
		
		for (int p = 0; p < r.getNumSubtasks(); p++) {
			
			String subtask = r.getSubtask(p);
			boolean added = false;
			Action a = grounder.getAction(subtask);
			if (a != null) {
				addToClause(layer.getActionVariable(p, a));
				added = true;
			}
			for (Reduction red : grounder.getReductions(subtask)) {
				if (grounder.isReductionPseudoPrimitive(red)) {
					Action action = grounder.getAction(red.getSubtask(0));
					if (action != null && layer.contains(p, action)) {
						addToClause(layer.getActionVariable(p, action));
						added = true;
					}
				} else if (layer.contains(p, red)) {
					addToClause(layer.getReductionVariable(p, red));					
					added = true;
				}
			}
			if (!added) {
				Logger.log(Logger.ERROR, "Position " + p + " of the initial task network is empty.");
				System.exit(1);
			}
			finishClause();
			
			Precondition constraint = r.getConstraint(p);
			addCondition(layer, p, constraint, 0);
		}
		
		Precondition constraint = r.getConstraint(r.getNumSubtasks());
		addCondition(layer, r.getNumSubtasks(), constraint, 0);
	}
	
	/**
	 * Add all before/after/between constraints and non-primitiveness of reductions
	 */
	private void addReductionClauses(HierarchyLayer layer, int pos) {
		
		for (Reduction r : layer.getReductions(pos)) {
			
			// If the reduction occurs, the position is non-primitive
			addClause(-layer.getReductionVariable(pos, r), -layer.getPrimitivenessVariable(pos));		
		}
	}
	
	/**
	 * Add primitiveness, preconditions and effects of each action and amo over actions
	 * Returns the supporting actions for each fact at that position
	 */
	private Map<Integer, List<Action>> addActionClauses(HierarchyLayer layer, int pos) {
		
		Map<Integer, List<Action>> supportingActions = new HashMap<>();
		
		for (Action a : layer.getActions(pos)) {			
			
			// If the action occurs, the position is primitive
			addClause(-layer.getActionVariable(pos, a), layer.getPrimitivenessVariable(pos));
			
			// Add preconditions and effects
			List<Integer> facts = new ArrayList<>();
			if (!checkCondition(layer, pos, a.getPreconditionsPos(), true) || !checkCondition(layer, pos, a.getPreconditionsNeg(), false)) {
				// Action not applicable
				addClause(-layer.getActionVariable(pos, a));
				continue;
			}
			addCondition(layer, pos, a.getPreconditionsPos(), -layer.getActionVariable(pos, a), true, null);
			addCondition(layer, pos, a.getPreconditionsNeg(), -layer.getActionVariable(pos, a), false, null);
			addCondition(layer, pos+1, a.getEffectsPos(), -layer.getActionVariable(pos, a), true, facts);
			addCondition(layer, pos+1, a.getEffectsNeg(), -layer.getActionVariable(pos, a), false, facts);
			
			// Add action as support to all facts that may change due to it
			for (int fact : facts) {
				List<Action> support = supportingActions.getOrDefault(fact, new ArrayList<>());
				support.add(a);
				supportingActions.put(fact, support);
			}
		}
		
		List<Action> actions = new ArrayList<>();
		actions.addAll(layer.getActions(pos));
		
		BinaryEncoding enc = layer.getBinaryEncoding(pos);
		if (enc == null) {
			// Pairwise At-Most-One constraints
			for (int i = 0; i < actions.size(); i++) {
				for (int j = i+1; j < actions.size(); j++) {
					addClause(-layer.getActionVariable(pos, actions.get(i)), 
							-layer.getActionVariable(pos, actions.get(j)));
				}
			}
		} else {
			// Binary At-Most-One constraints
			enc.getForbiddenValues().forEach(clause -> addClause(clause));
			for (int i = 0; i < actions.size(); i++) {
				Action a = actions.get(i);
				int aLit = -layer.getActionVariable(pos, a);
				
				int[] bitLits = enc.posLiterals(i);
				for (int bitLit : bitLits) {
					addClause(aLit, bitLit);
				}
			}
		}
		
		return supportingActions;
	}
	
	/**
	 * Adds frame axioms from pos to pos+1.
	 */
	private void addFrameAxioms(HierarchyLayer layer, int pos, 
			Map<Integer, List<Action>> supportingActions, Map<Integer, List<Reduction>> supportingReductions) {
		
		for (int fact : layer.getFacts(pos+1)) {
			
			if (!layer.isFactFluent(pos+1, fact)) {
				continue;
			}
			
			int posBefore = layer.getLatestPositionOfFact(pos, fact);
			
			// NEGATIVE -> POSITIVE
			if (layer.getFactStatus(posBefore, fact) != FactStatus.constantPositive
					&& layer.getFactStatus(pos+1, fact) != FactStatus.constantNegative) {
				
				if (layer.isFactFluent(posBefore, fact))
					addToClause(layer.getFactVariable(posBefore, fact));
				if (layer.isFactFluent(pos+1, fact))
					addToClause(-layer.getFactVariable(pos+1, fact));
				for (int p = posBefore; p <= pos; p++) {			
					if (supportingReductions == null) {
						addToClause(-layer.getPrimitivenessVariable(p));
					} else {						
						for (Reduction r : supportingReductions.getOrDefault(fact, new ArrayList<>())) {
							addToClause(layer.getReductionVariable(pos, r));
						}
					}
				}
				for (Action a : supportingActions.getOrDefault(fact, new ArrayList<>())) {
					addToClause(layer.getActionVariable(pos, a));
				}
				finishClause();
			}
			
			// POSITIVE -> NEGATIVE
			if (layer.getFactStatus(posBefore, fact) != FactStatus.constantNegative
					&& layer.getFactStatus(pos+1, fact) != FactStatus.constantPositive) {
				
				if (layer.isFactFluent(posBefore, fact))
					addToClause(-layer.getFactVariable(posBefore, fact));
				if (layer.isFactFluent(pos+1, fact))
					addToClause(layer.getFactVariable(pos+1, fact));
				for (int p = posBefore; p <= pos; p++) {				
					if (supportingReductions == null) {
						addToClause(-layer.getPrimitivenessVariable(p));
					} else {						
						for (Reduction r : supportingReductions.getOrDefault(fact, new ArrayList<>())) {
							addToClause(layer.getReductionVariable(pos, r));
						}
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
			
			addClause(-oldLayer.getPrimitivenessVariable(pos), newLayer.getPrimitivenessVariable(successorPos));
			
			for (Action a : oldLayer.getActions(pos)) {
				if (!newLayer.contains(successorPos, a)) {
					// Action turned out to be invalid
					addClause(-oldLayer.getActionVariable(pos, a));
					continue;
				}
				addClause(-oldLayer.getActionVariable(pos, a), newLayer.getActionVariable(successorPos, a));
			}
			
			/*
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
						addClause(oldVar, -newVar);
						addClause(-oldVar, newVar);
					}					
				}
			}*/
		}
	}
	
	/**
	 * Reductions
	 */
	private void addExpansions(HierarchyLayer oldLayer, HierarchyLayer newLayer) {
		
		int numEliminatedReductions = 0;
		
		for (int pos = 0; pos < oldLayer.getSize(); pos++) {
		
			loopReductions : for (Reduction r : oldLayer.getReductions(pos)) {
				
				int successorPos = oldLayer.getSuccessorPosition(pos);
				int maxExpansionSize = oldLayer.getSuccessorPosition(pos+1) - successorPos;
				
				for (int p = 0; p < r.getNumSubtasks(); p++) {
					
					// Check if there is some subtask that turns out to be impossible
					String subtask = r.getSubtask(p);
					
					Action action = grounder.getAction(subtask);
					if (action != null && !newLayer.contains(successorPos+p, action)) {
						// There is no such action => the reduction turns out to be impossible
						addClause(-oldLayer.getReductionVariable(pos, r));
						numEliminatedReductions++;
						continue loopReductions;
					}
					
					for (Reduction red : grounder.getReductions(subtask)) {
						if (grounder.isReductionPseudoPrimitive(red)) {
							if (!newLayer.contains(successorPos+p, grounder.getAction(red.getSubtask(0)))) {
								// There is no such action => the reduction turns out to be impossible
								addClause(-oldLayer.getReductionVariable(pos, r));
								numEliminatedReductions++;
								continue loopReductions;
							}
						} else if (!newLayer.contains(successorPos+p, red)) {
							// There is no such sub-reduction => the reduction turns out to be impossible
							addClause(-oldLayer.getReductionVariable(pos, r));
							numEliminatedReductions++;
							continue loopReductions;
						}
					}
				}
				
				int p = 0;
				for (; p < r.getNumSubtasks(); p++) {
					
					String subtask = r.getSubtask(p);
					addToClause(-oldLayer.getReductionVariable(pos, r));
					boolean successorFound = false;
					Action action = grounder.getAction(subtask);
					if (action != null) {
						addToClause(newLayer.getActionVariable(successorPos+p, action));
						successorFound = true;
					}
					for (Reduction red : grounder.getReductions(subtask)) {
						if (grounder.isReductionPseudoPrimitive(red)) {
							addToClause(newLayer.getActionVariable(successorPos+p, grounder.getAction(red.getSubtask(0))));
						} else {							
							addToClause(newLayer.getReductionVariable(successorPos+p, red));
						}
						successorFound = true;
					}
					finishClause();
					if (!successorFound) {
						//Logger.log(Logger.WARN, "Reduction " + r + " does not have any successors on offset " + p);
						continue loopReductions;
						//System.exit(1);
					}
				}
				// Fill empty positions with blank actions
				for (; p < maxExpansionSize; p++) {
					addClause(-oldLayer.getReductionVariable(pos, r), 
							newLayer.getActionVariable(successorPos+p, HierarchyLayer.BLANK_ACTION));
				}
				
				// Add reduction's constraints
				for (p = 0; p <= r.getNumSubtasks(); p++) {
					Precondition constraint = r.getConstraint(p);
					if (!checkCondition(newLayer, successorPos+p, constraint)) {
						addClause(-oldLayer.getReductionVariable(pos, r));
						numEliminatedReductions++;
						continue loopReductions;
					}
					addCondition(newLayer, successorPos+p, constraint, 
							-oldLayer.getReductionVariable(pos, r));
				}
			}
		}
		
		Logger.log(Logger.INFO, "Logically eliminated " + numEliminatedReductions + " reductions.");
	}
	
	private void assumeFinishedPlan(HierarchyLayer newLayer) {
		
		for (int pos = 0; pos < newLayer.getSize(); pos++) {
			addAssumption(newLayer.getPrimitivenessVariable(pos));
		}
	}
	
	
	private boolean checkCondition(HierarchyLayer layer, int pos, Precondition cond) {
		
		List<Precondition> conditions = new ArrayList<>();
		conditions.add(cond);
		for (int i = 0; i < conditions.size(); i++) {
			Precondition pre = conditions.get(i);
			if (pre.getType() == PreconditionType.atom) {
				Atom atom = pre.getAtom();
				int fact = atom.getId()+1;
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
		
		for (int idx = atoms.getFirstTrueAtom(); idx >= 0; idx = atoms.getNextTrueAtom(idx+1)) {
			int fact = (sign ? 1 : -1) * (idx+1);
			if (!checkCondition(layer, pos, fact, sign)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkCondition(HierarchyLayer layer, int pos, int fact, boolean sign) {
		
		int posBefore = layer.getLatestPositionOfFactVariable(pos, Math.abs(fact));
		if (pos == posBefore && !layer.isFactFluent(pos, fact)) {
			if (layer.getFactStatus(pos, fact) == (sign ? FactStatus.constantNegative : FactStatus.constantPositive)) {
				return false;
			}
		} else if (posBefore < 0) {
			if (layer.getFactStatus(pos, fact) == (sign ? FactStatus.constantNegative : FactStatus.constantPositive)) {
				return false;
			}
		} else if (posBefore < pos) {
			System.out.println(fact + " : " + posBefore + " < " + pos);
			throw new RuntimeException();
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
				int fact = atom.getId()+1;
				
				if (!layer.isFactFluent(pos, fact)) {
					continue;
				}
				
				int posBefore = layer.getLatestPositionOfFactVariable(pos, fact);
				if (posBefore < pos) {
					System.out.println(atom + " : " + posBefore + " < " + pos);
					throw new RuntimeException();
				}
				
				if (firstLit != 0) {
					addToClause(firstLit);
				}
				addToClause((atom.getValue() ? 1 : -1) * layer.getFactVariable(posBefore, fact));
				finishClause();
				
			} else if (pre.getType() == PreconditionType.conjunction) {
				conditions.addAll(pre.getChildren());
			}
		}
	}
		
	private void addCondition(HierarchyLayer layer, int pos, AtomSet atoms, int firstLit, 
			boolean sign, List<Integer> facts) {
		
		for (int idx = atoms.getFirstTrueAtom(); idx >= 0; idx = atoms.getNextTrueAtom(idx+1)) {
			int fact = (sign ? 1 : -1) * (idx+1);
			
			int posBefore = layer.getLatestPositionOfFact(pos, Math.abs(fact));
			if (posBefore < 0) {
				System.out.println(fact + " never occurs");
				continue;
			} else if (posBefore < pos) {
				System.out.println(fact + " : " + posBefore + " < " + pos);
				throw new RuntimeException();
			}
			if (posBefore < 0 || !layer.isFactFluent(posBefore, fact)) {
				if (layer.getFactStatus(posBefore, fact) == (sign ? FactStatus.constantNegative : FactStatus.constantPositive)) {
					
				}
				continue;
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
	
	private void addToClause(int lit) {
		if (currentClause == null)
			currentClause = new ArrayList<>();
		currentClause.add(lit);
	}
	
	private void finishClause() {
		int[] array = currentClause.stream().mapToInt(i->i).toArray();
		addClause(array);
		currentClause = null;
		addedClauses++;
	}
	
	private void addClause(int... lits) {
		solver.addClause(lits);
		addedClauses++;
	}
	
	private void addAssumption(int lit) {
		solver.addAssumption(lit);
	}
	
	private Boolean solve() {
		Boolean result = solver.isSatisfiable();
		Logger.log(Logger.INFO, "Solver returned " + (result != null && result ? "SAT" : "UNSAT") + ".");
		return result;
	}
	
	private Plan extractPlan() {
		Plan plan = new Plan();
		
		HierarchyLayer finalLayer = grounder.getHierarchyLayers().get(depth);
		for (int pos = 0; pos < finalLayer.getSize(); pos++) {
			for (Action a : finalLayer.getActions(pos)) {
				if (a == HierarchyLayer.BLANK_ACTION)
					continue;
				int actionVar = finalLayer.getActionVariable(pos, a);
				if (solver.getValue(actionVar) > 0) {
					if (!grounder.isActionNoop(a))
						plan.appendAtBack(a);
				}
			}
		}
		
		return plan;
	}
}
