package edu.kit.aquaplanning.validation;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.grounding.datastructures.LiftedState;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.util.Logger;

public class LiftedValidator {

	public static boolean planIsValid(PlanningProblem problem, List<Operator> plan) {
		
		LiftedState state = new LiftedState(problem.getConstants(), problem.getInitialState());
		
		int step = 0;
		
		for (Operator op : plan) {
			
			// Check preconditions
			if (!op.getPrecondition().holds(condition -> state.holds(condition))) {
				Logger.log(Logger.ERROR, "Error at step " + step + ": Action " + op.toActionString() + " is not applicable in state "
						+ state.toString() + ".");
				return false;
			}
			
			// Apply effects
			apply(op.getEffect(), state);
			
			step++;
		}
		
		// Check goal
		for (AbstractCondition goal : problem.getGoals()) {
			if (!goal.holds(condition -> state.holds(condition))) {
				Logger.log(Logger.ERROR, "Error at step " + step + ": The goal " + goal 
						+ " is not satisfied in the final state " + state.toString() + ".");
				return false;
			}
		}
		
		return true;
	}
	
	private static void apply(AbstractCondition effect, LiftedState state) {
		
		List<Condition> addList = new ArrayList<>();
		List<Condition> delList = new ArrayList<>();
		
		List<AbstractCondition> effects = new ArrayList<>();
		effects.add(effect);
		for (int i = 0; i < effects.size(); i++) {
			AbstractCondition eff = effects.get(i);
			switch (eff.getConditionType()) {
			case consequential:
				ConsequentialCondition cc = (ConsequentialCondition) eff;
				if (cc.getPrerequisite().holds(condition -> state.holds(condition))) {
					effects.add(cc.getConsequence());
				}
				break;
			case conjunction:
				effects.addAll((((ConditionSet) eff).getConditions()));
				break;
			case atomic:
			case negation:
				boolean negated = false;
				while (eff.getConditionType() == ConditionType.negation) {
					eff = ((Negation) eff).getChildCondition();
					negated = !negated;
				}
				Condition c = (Condition) eff;
				negated = negated ^ c.isNegated();
				c = c.copy(); c.setNegated(negated);
				(negated ? delList : addList).add(c);
				break;
			default:
				throw new RuntimeException("Evaluation of lifted condition type \"" 
						+ eff.getConditionType() + "\" is not supported yet.");	
			}
		}
		
		// Conditions in addList are positive, conditions in delList are negated
		for (Condition c : delList) state.add(c);
		for (Condition c : addList) state.add(c);
	}
}
