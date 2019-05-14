package edu.kit.aquaplanning.planning.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.AtomSet;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.State;

public class ActionIndex {

	protected Map<Integer, List<Action>> atomActionMap;
	protected List<Action> noPrecondActions;

	protected boolean relaxed;

	protected void addAction(int index, Action action) {
		if (!atomActionMap.containsKey(index)) {
			atomActionMap.put(index, new ArrayList<>());
		}
		atomActionMap.get(index).add(action);
	}

	protected ActionIndex() {
	}

	protected ActionIndex(boolean relaxed) {
		this.relaxed = relaxed;
	}

	public ActionIndex(GroundPlanningProblem gpp, boolean relaxed) {
		init(gpp, relaxed);
	}

	public ActionIndex(GroundPlanningProblem gpp) {
		init(gpp, false);
	}

	public void init(GroundPlanningProblem gpp, boolean relaxed) {
		this.relaxed = relaxed;
		atomActionMap = new HashMap<>();
		noPrecondActions = new ArrayList<>();
		for (Action a : gpp.getActions()) {
			int posx = a.getPreconditionsPos().getFirstTrueAtom();
			if (posx >= 0) {
				addAction(posx + 1, a);
			} else if (!relaxed) {
				int negx = a.getPreconditionsNeg().getFirstTrueAtom();
				if (negx >= 0) {
					addAction(-negx - 1, a);
				} else {
					noPrecondActions.add(a);
				}
			} else {
				noPrecondActions.add(a);
			}
		}
	}

	public Collection<Action> getApplicableActions(State state) {

		HashSet<Action> result = new HashSet<>();

		// Add actions without any (simple) preconditions
		for (Action candidate : noPrecondActions) {
			// Still check applicability (derived predicates etc.)
			if (!relaxed && candidate.isApplicable(state)) {
				result.add(candidate);
			}
			if (relaxed && candidate.isApplicableRelaxed(state)) {
				result.add(candidate);
			}
		}

		// Add actions with preconditions
		AtomSet stateAtoms = state.getAtomSet();
		for (int atomId = 0; atomId < stateAtoms.size(); atomId++) {
			int index = stateAtoms.get(atomId) ? atomId + 1 : -atomId - 1;
			if (!atomActionMap.containsKey(index)) {
				continue;
			}
			for (Action candidate : atomActionMap.get(index)) {
				if (!relaxed && candidate.isApplicable(state)) {
					result.add(candidate);
				}
				if (relaxed && candidate.isApplicableRelaxed(state)) {
					result.add(candidate);
				}
			}
		}

		return result;
	}
}
