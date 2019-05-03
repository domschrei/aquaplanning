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
			new Precondition(PreconditionType.conjunction), 
			new Effect(EffectType.conjunction));
	public static final int BINARY_AMO_THRESHOLD = 2048;

	private List<Set<Reduction>> reductions;
	private List<Set<Action>> actions;
	private List<Set<Integer>> facts;
		
	private List<Map<Reduction, Integer>> reductionVars;
	private List<Map<Action, Integer>> actionVars;
	private List<Map<Integer, Integer>> factVars;
	
	private List<Integer> successorPositions;
	private List<Integer> variableStarts;
	private List<BinaryEncoding> binaryActionHelperVars;
	
	public HierarchyLayer() {
		this.reductions = new ArrayList<>();
		this.actions = new ArrayList<>();
		this.facts = new ArrayList<>();
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
		while (position >= facts.size())
			facts.add(new HashSet<>());
		facts.get(position).add(Math.abs(p));
	}
	
	public void setSuccessor(int position, int successorPosition) {
		while (position >= successorPositions.size())
			successorPositions.add(0);
		successorPositions.set(position, successorPosition);
	}
	
	public int consolidate(int globalVariableStart) {
		
		reductionVars = new ArrayList<>();
		actionVars = new ArrayList<>();
		factVars = new ArrayList<>();
		variableStarts = new ArrayList<>();
		binaryActionHelperVars = new ArrayList<>();
		
		for (int pos = 0; pos < getSize(); pos++) {
			variableStarts.add(globalVariableStart);
			globalVariableStart++; // primitiveness variable

			Map<Reduction, Integer> rVars = new HashMap<>();
			for (Reduction r : getReductions(pos)) {
				rVars.put(r, globalVariableStart++);
			}
			reductionVars.add(rVars);

			Map<Action, Integer> aVars = new HashMap<>();
			for (Action a : getActions(pos)) {
				aVars.put(a, globalVariableStart++);
			}
			actionVars.add(aVars);
			
			if (getActions(pos).size() >= BINARY_AMO_THRESHOLD) {
				BinaryEncoding enc = new BinaryEncoding(getActions(pos).size()+1, globalVariableStart);
				binaryActionHelperVars.add(enc);
				globalVariableStart += enc.getBitLength();
			} else {
				binaryActionHelperVars.add(null);
			}

			Map<Integer, Integer> fVars = new HashMap<>();
			for (Integer f : getFacts(pos)) {
				fVars.put(f, globalVariableStart++);
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
		if (pos >= reductionVars.size() || !reductionVars.get(pos).containsKey(r)) error();
		return reductionVars.get(pos).get(r);
	}
	public int getActionVariable(int pos, Action a) {
		if (pos >= actionVars.size() || !actionVars.get(pos).containsKey(a)) error();
		return actionVars.get(pos).get(a);
	}
	public int getFactVariable(int pos, int fact) {
		boolean sign = fact > 0;
		fact = Math.abs(fact);
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
	public int getPrimitivenessVariable(int pos) {
		if (pos >= variableStarts.size()) error();
		return variableStarts.get(pos);
	}
	public BinaryEncoding getBinaryEncoding(int pos) {
		return binaryActionHelperVars.get(pos);
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
			out = out.substring(0, out.length()-2) + "\n";
		}
		return out;
	}
}
