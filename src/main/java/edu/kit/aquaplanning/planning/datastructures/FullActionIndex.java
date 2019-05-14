package edu.kit.aquaplanning.planning.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;

public class FullActionIndex extends ActionIndex {

	public List<Action> getNoPrecondActions() {
		return noPrecondActions;
	}

	public List<Action> getActionsWithPrecondition(int atomIndex) {
		return atomActionMap.get(atomIndex);
	}

	public FullActionIndex(GroundPlanningProblem gpp) {
		init(gpp, false);
	}

	public FullActionIndex(GroundPlanningProblem gpp, boolean relaxed) {
		init(gpp, relaxed);
	}

	public void init(GroundPlanningProblem gpp, boolean relaxed) {

		this.relaxed = relaxed;
		atomActionMap = new HashMap<>();
		noPrecondActions = new ArrayList<>();
		for (Action a : gpp.getActions()) {
			boolean hasPreconds = false;
			int atomid = a.getPreconditionsPos().getFirstTrueAtom();
			while (atomid >= 0) {
				hasPreconds = true;
				addAction(atomid + 1, a);
				atomid = a.getPreconditionsPos().getNextTrueAtom(atomid + 1);
			}
			if (!relaxed) {
				atomid = a.getPreconditionsNeg().getFirstTrueAtom();
				while (atomid >= 0) {
					hasPreconds = true;
					addAction(-atomid - 1, a);
					atomid = a.getPreconditionsNeg().getNextTrueAtom(atomid + 1);
				}
			}
			if (!hasPreconds) {
				noPrecondActions.add(a);
			}
		}
	}

}
