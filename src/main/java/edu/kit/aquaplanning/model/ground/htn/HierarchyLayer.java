package edu.kit.aquaplanning.model.ground.htn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.Effect;
import edu.kit.aquaplanning.model.ground.Effect.EffectType;
import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.util.BinaryEncoding;

public class HierarchyLayer {

	public static final Action BLANK_ACTION = new Action("_BLANK_ACTION",
			new Precondition(PreconditionType.conjunction), new Effect(EffectType.conjunction));
	public static final int BINARY_AMO_THRESHOLD = 512;
	public static final boolean ADD_REDUCTION_AMO = false;
	
	public enum FactStatus {
		constantPositive, constantNegative, fluent;
	};

	private List<Set<Reduction>> reductions;
	private List<Set<Action>> actions;
	private List<Set<Integer>> facts;
	private List<Map<Integer, FactStatus>> factsStatus;

	private List<Map<Reduction, Integer>> reductionVars;
	private List<Map<Action, Integer>> actionVars;
	private List<Map<Integer, Integer>> factVars;

	private List<Integer> successorPositions;
	private List<Integer> variableStarts;
	private List<BinaryEncoding> binaryActionHelperVars;
	private List<BinaryEncoding> binaryReductionHelperVars;

	public HierarchyLayer() {
		this.reductions = new ArrayList<>();
		this.actions = new ArrayList<>();
		this.facts = new ArrayList<>();
		this.factsStatus = new ArrayList<>();
		this.successorPositions = new ArrayList<>();
	}

	public void addReduction(int position, Reduction r) {
		while (position >= reductions.size())
			reductions.add(new HashSet<>());
		reductions.get(position).add(r);
	}

	public void addAction(int position, Action a) {
		while (position >= actions.size())
			actions.add(new HashSet<>());
		actions.get(position).add(a);
	}

	public void addFact(int position, int p) {
		while (position >= facts.size()) {
			facts.add(new HashSet<>());
		}
		facts.get(position).add(Math.abs(p));
	}

	public void addFactStatus(int position, int p, FactStatus status) {
		while (position >= factsStatus.size()) {
			factsStatus.add(new HashMap<>());
		}
		factsStatus.get(position).put(Math.abs(p), status);
	}

	public void setSuccessor(int position, int successorPosition) {
		while (position >= successorPositions.size())
			successorPositions.add(0);
		successorPositions.set(position, successorPosition);
	}

	public int consolidate(int globalVariableStart, List<Map<Integer, Integer>> existingFactVariables) {

		reductionVars = new ArrayList<>();
		actionVars = new ArrayList<>();
		factVars = new ArrayList<>();
		variableStarts = new ArrayList<>();
		binaryActionHelperVars = new ArrayList<>();
		binaryReductionHelperVars = new ArrayList<>();

		for (int pos = 0; pos < getSize(); pos++) {
			variableStarts.add(globalVariableStart);
			globalVariableStart++; // primitiveness variable

			// Reductions
			Map<Reduction, Integer> rVars = new HashMap<>();
			for (Reduction r : getReductions(pos)) {
				rVars.put(r, globalVariableStart++);
			}
			reductionVars.add(rVars);
			if (ADD_REDUCTION_AMO) {
				// At-most-one helper variables for reductions
				if (getReductions(pos).size() >= BINARY_AMO_THRESHOLD) {
					BinaryEncoding enc = new BinaryEncoding(getReductions(pos).size() + 1, globalVariableStart);
					binaryReductionHelperVars.add(enc);
					globalVariableStart += enc.getBitLength();
				} else {
					binaryReductionHelperVars.add(null);
				}
			}
			
			// Actions
			Map<Action, Integer> aVars = new HashMap<>();
			for (Action a : getActions(pos)) {
				aVars.put(a, globalVariableStart++);
			}
			actionVars.add(aVars);
			// At-most-one helper variables for actions
			if (getActions(pos).size() >= BINARY_AMO_THRESHOLD) {
				BinaryEncoding enc = new BinaryEncoding(getActions(pos).size() + 1, globalVariableStart);
				binaryActionHelperVars.add(enc);
				globalVariableStart += enc.getBitLength();
			} else {
				binaryActionHelperVars.add(null);
			}

			// Facts
			Map<Integer, Integer> fVars = new HashMap<>();
			for (Integer f : getFacts(pos)) {
				// Only add facts to the variables which are actually fluent!
				if (getFactStatus(pos, f) == FactStatus.fluent) {
					if (existingFactVariables != null && existingFactVariables.get(pos).containsKey(f)) {
						// Reuse fact variable (from a previous layer)
						fVars.put(f, existingFactVariables.get(pos).get(f));
					} else {
						// Create new fact variable
						fVars.put(f, globalVariableStart++);
					}
				}
			}
			factVars.add(fVars);
		}

		return globalVariableStart;
	}

	public Set<Reduction> getReductions(int position) {
		return (position < reductions.size() ? reductions.get(position) : new HashSet<>());
	}

	public Set<Action> getActions(int position) {
		return (position < actions.size() ? actions.get(position) : new HashSet<>());
	}

	public Set<Integer> getFacts(int position) {
		return (position < facts.size() ? facts.get(position) : new HashSet<>());
	}

	public FactStatus getFactStatus(int position, int fact) {
		return (position < factsStatus.size() ? factsStatus.get(position).get(Math.abs(fact)) : null);
	}

	public boolean isFactFluent(int position, int fact) {
		return getFactStatus(position, fact) == FactStatus.fluent;
	}

	public int getSuccessorPosition(int position) {
		return (position < successorPositions.size() ? successorPositions.get(position) : -1);
	}

	public int getSize() {
		return Math.max(Math.max(reductions.size(), actions.size()), facts.size());
	}

	public boolean contains(int pos, Reduction r) {
		return pos >= 0 && pos < reductionVars.size() && reductionVars.get(pos).containsKey(r);
	}

	public boolean contains(int pos, Action a) {
		return pos >= 0 && pos < actionVars.size() && actionVars.get(pos).containsKey(a);
	}

	public int getReductionVariable(int pos, Reduction r) {
		if (pos >= reductionVars.size() || !reductionVars.get(pos).containsKey(r))
			error();
		return reductionVars.get(pos).get(r);
	}

	public int getActionVariable(int pos, Action a) {
		if (pos >= actionVars.size() || !actionVars.get(pos).containsKey(a))
			error();
		return actionVars.get(pos).get(a);
	}

	public int getFactVariable(int pos, int fact) {
		boolean sign = fact > 0;
		fact = Math.abs(fact);
		if (getFactStatus(pos, fact) != FactStatus.fluent) {
			System.out.println(
					"Fact " + fact + " at position " + pos + " is not a fluent, but " + getFactStatus(pos, fact) + "!");
			error();
		}
		if (pos >= factVars.size() || !factVars.get(pos).containsKey(fact)) {
			System.out.println("Position " + pos + " : no fact " + fact);
			error();
		}
		return (sign ? 1 : -1) * factVars.get(pos).get(fact);
	}

	public int getLatestPositionOfFactVariable(int maxPos, int fact) {
		int pos = maxPos;
		fact = Math.abs(fact);
		while (pos >= 0 && !factVars.get(pos).containsKey(fact)) {
			pos--;
		}
		return pos;
	}

	public int getLatestPositionOfFact(int maxPos, int fact) {
		int pos = maxPos;
		fact = Math.abs(fact);
		while (pos >= 0 && !facts.get(pos).contains(fact)) {
			pos--;
		}
		return pos;
	}

	public int getPrimitivenessVariable(int pos) {
		if (pos >= variableStarts.size())
			error();
		return variableStarts.get(pos);
	}

	public BinaryEncoding getActionBinaryEncoding(int pos) {
		return binaryActionHelperVars.get(pos);
	}
	public BinaryEncoding getReductionBinaryEncoding(int pos) {
		return ADD_REDUCTION_AMO ? binaryReductionHelperVars.get(pos) : null;
	}

	public HierarchyLayerStatistics collectStatistics() {

		HierarchyLayerStatistics s = new HierarchyLayerStatistics();
		for (int pos = 0; pos < getSize(); pos++) {
			s.totalNumReductions += getReductions(pos).size();
			s.totalNumActions += getActions(pos).size();
			s.totalNumFacts += getFacts(pos).size();
		}
		s.size = getSize();
		s.meanNumReductions = s.totalNumReductions / s.size;
		s.meanNumActions = s.totalNumActions / s.size;
		s.meanNumFacts = s.totalNumFacts / s.size;
		return s;
	}

	public void extendStatistics(HierarchyLayerStatistics s) {

		HierarchyLayerStatistics sNew = collectStatistics();
		s.totalNumReductions += sNew.totalNumReductions;
		s.totalNumActions += sNew.totalNumActions;
		s.totalNumFacts += sNew.totalNumFacts;
		s.meanNumReductions = (s.size * s.meanNumReductions + sNew.size * sNew.meanNumReductions)
				/ (s.size + sNew.size);
		s.meanNumActions = (s.size * s.meanNumActions + sNew.size * sNew.meanNumActions) / (s.size + sNew.size);
		s.meanNumFacts = (s.size * s.meanNumFacts + sNew.size * sNew.meanNumFacts) / (s.size + sNew.size);
		s.size += sNew.size;
	}

	private void error() {
		System.out.println("Error: Variable index out of bounds");
		throw new RuntimeException();
	}

	@Override
	public String toString() {
		String out = "";
		for (int pos = 0; pos < getSize(); pos++) {
			out += (pos + " : ");
			int i = 0;
			for (Reduction r : getReductions(pos)) {
				out += (r.getBaseMethod().toTaskWithImplicitArgs().toTaskString() + " , ");
				i++;
				if (i == 5) {
					out += "... (" + getReductions(pos).size() + "), ";
					break;
				}
			}
			i = 0;
			for (Action a : getActions(pos)) {
				out += (a.getName() + " , ");
				i++;
				if (i == 5) {
					out += "... (" + getActions(pos).size() + "), ";
					break;
				}
			}
			i = 0;
			for (int p : getFacts(pos)) {
				out += (p + " , ");
				i++;
				if (i == 5) {
					out += "... (" + getFacts(pos).size() + "), ";
					break;
				}
			}
			out = out.substring(0, out.length() - 2) + "\n";
		}
		return out;
	}
}
