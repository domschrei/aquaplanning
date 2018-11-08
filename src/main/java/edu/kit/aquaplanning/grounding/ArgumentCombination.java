package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;

public class ArgumentCombination {
	
	/**
	 * Given a list of possible constants at each argument index, 
	 * allows to iterate over all possible combinations.
	 */
	public static class Iterator implements java.util.Iterator<List<Argument>> {
		
		private List<List<Argument>> eligibleArgs;
		private List<Integer> currentArgIndices;
		private boolean hasNext;
		
		/**
		 * @param eligibleArgs At index i, contains a list of all eligible
		 * constants for the argument position i.
		 */
		public Iterator(List<List<Argument>> eligibleArgs) {
			
			this.eligibleArgs = eligibleArgs;

			// Set current argument indices to zero
			// (first argument combination)
			currentArgIndices = new ArrayList<>();
			hasNext = true;
			for (int i = 0; i < eligibleArgs.size(); i++) {
				if (eligibleArgs.get(i).isEmpty())
					// no arguments at position i to choose from
					hasNext = false; 
				currentArgIndices.add(0);
			}
		}
		
		/**
		 * True, iff there is another combination not retrieved yet.
		 */
		@Override
		public boolean hasNext() {
			return hasNext;
		}
		
		/**
		 * Get the next combination of constants.
		 */
		@Override
		public List<Argument> next() {
			
			// Create current constant combination
			List<Argument> args = new ArrayList<>();
			int argPos = 0;
			for (int argIdx : currentArgIndices) {
				args.add(eligibleArgs.get(argPos++).get(argIdx));
			}
			
			// Get to next argument combination, if possible
			hasNext = false;
			for (int pos = currentArgIndices.size()-1; pos >= 0; pos--) {
				
				// Are there more argument options at this position?
				if (currentArgIndices.get(pos)+1 < eligibleArgs.get(pos).size()) {
					// -- Yes
					
					// Proceed to the next argument option at this position
					currentArgIndices.set(pos, currentArgIndices.get(pos)+1);
					
					// Reset all succeeding argument options to zero
					for (int posAfter = pos+1; posAfter < currentArgIndices.size(); posAfter++) {
						currentArgIndices.set(posAfter, 0);
					}
					
					hasNext = true;
					break;
				}
			}
			
			return args;
		}
	}
	
	public static Iterator iterator(List<List<Argument>> eligibleArgs) {
		return new Iterator(eligibleArgs);
	}
	
	/**
	 * For a list of arguments, returns a list containing all valid
	 * argument combinations which can be retrieved by
	 * replacing each variable in the arguments by a
	 * constant of an appropriate type.
	 * This list of eligible arguments may have been shortened
	 * by applying simplification strategies.
	 */
	public static List<List<Argument>> getEligibleArguments(List<Argument> args,
			PlanningProblem problem, List<Argument> allConstants) {
		
		List<Type> argTypes = new ArrayList<>();
		for (Argument arg : args) {
			argTypes.add(arg.getType());
		}
		return getEligibleArgumentsOfType(argTypes, problem, allConstants);
	}
	
	/**
	 * Returns each possible combination of constants with the 
	 * provided order of types.
	 */
	public static List<List<Argument>> getEligibleArgumentsOfType(List<Type> argTypes, 
			PlanningProblem problem, List<Argument> allConstants) {
		
		List<List<Argument>> eligibleArguments = new ArrayList<>();
		
		// For each provided type
		for (Type argType : argTypes) {
			List<Argument> eligibleArgumentsAtPos = new ArrayList<>();
			
			// For all possible constants at the argument position
			for (Argument c : allConstants) {
				if (problem.isArgumentOfType(c, argType)) {
					
					eligibleArgumentsAtPos.add(c);
				}
			}
			
			eligibleArguments.add(eligibleArgumentsAtPos);
		}
		
		return eligibleArguments;
	}
	
	/**
	 * Processes a quantification where all arguments except for the 
	 * quantified variables are already ground, and returns a flat list
	 * of atoms providing the same logical information.
	 */
	public static List<AbstractCondition> resolveQuantification(Quantification q, 
			PlanningProblem problem, List<Argument> constants) {
		
		List<AbstractCondition> dequantifiedConds = new ArrayList<>();
		
		if (q.getQuantifier() != Quantifier.universal) {
			System.err.println("Only universal quantifiers are supported.");
		}
		
		// Iterator over all possible combinations of quantified variables' values
		List<Argument> quantifiedArgs = q.getVariables();
		List<List<Argument>> eligibleDequantifiedArgs = ArgumentCombination
				.getEligibleArguments(quantifiedArgs, problem, constants);
		ArgumentCombination.Iterator dequantifiedArgIterator = 
				new ArgumentCombination.Iterator(eligibleDequantifiedArgs);
		
		dequantifiedArgIterator.forEachRemaining(dequantifiedArgs -> {
			// dequantifiedArgs : the arguments for the quantified variables
			
			// For each quantified condition, create a condition
			// with all quantified variables replaced
			List<AbstractCondition> conditions = q.getConditions();
			for (int condIdx = 0; condIdx < conditions.size(); condIdx++) {
				AbstractCondition abstractCond = conditions.get(condIdx);
				
				if (abstractCond.getConditionType() == ConditionType.atomic) {
					
					Condition cond = (Condition) abstractCond;
					dequantifiedConds.add(dequantifyCondition(cond, quantifiedArgs, dequantifiedArgs));
					
				} else if (abstractCond.getConditionType() == ConditionType.consequential) {
					
					ConsequentialCondition cond = (ConsequentialCondition) abstractCond;
					ConsequentialCondition newCond = new ConsequentialCondition();
					for (AbstractCondition c : cond.getPrerequisites()) {
						newCond.addPrerequisite(dequantifyCondition(c, quantifiedArgs, dequantifiedArgs));
					}
					for (AbstractCondition c : cond.getConsequences()) {
						newCond.addConsequence(dequantifyCondition(c, quantifiedArgs, dequantifiedArgs));
					}
					dequantifiedConds.add(newCond);
				}
			}						
		});
		
		return dequantifiedConds;
	}
	
	public static AbstractCondition dequantifyCondition(AbstractCondition abstractCond, 
			List<Argument> quantifiedArgs, List<Argument> dequantifiedArgs) {
		
		if (abstractCond.getConditionType() == ConditionType.atomic) {
			
			Condition cond = (Condition) abstractCond;
			List<Argument> condArgs = new ArrayList<>();
			
			// For each argument of the condition
			for (int argIdx = 0; argIdx < cond.getNumArgs(); argIdx++) {
				Argument arg = cond.getArguments().get(argIdx);
				Argument c = arg.copy();
				
				if (!arg.isConstant()) {
					// arg is a variable
					// Is this variable bound to the quantifier?
					
					int qArgIdx = 0;
					// Search for argument equality by name only
					while (qArgIdx < quantifiedArgs.size() && 
							!quantifiedArgs.get(qArgIdx).getName().equals(arg.getName()))
						qArgIdx++;
					
					if (qArgIdx < quantifiedArgs.size()) {
						// -- yes, bound to quantifier: 
						// assign the corresponding dequantified argument
						c = dequantifiedArgs.get(qArgIdx);
					}
				}
				// Add created constant to this condition's arguments
				condArgs.add(c);
			}
			
			// Assemble new condition
			Condition newCondition = new Condition(cond.getPredicate(), cond.isNegated());
			for (Argument arg : condArgs) newCondition.addArgument(arg);
			return newCondition;
			
		} else if (abstractCond.getConditionType() == ConditionType.consequential) {
			
			ConsequentialCondition cond = (ConsequentialCondition) abstractCond;
			List<AbstractCondition> pre = new ArrayList<>();
			List<AbstractCondition> cons = new ArrayList<>();
			for (AbstractCondition c : cond.getPrerequisites()) {
				pre.add(dequantifyCondition(c, quantifiedArgs, dequantifiedArgs));
			}
			for (AbstractCondition c : cond.getConsequences()) {
				cons.add(dequantifyCondition(c, quantifiedArgs, dequantifiedArgs));
			}
			return new ConsequentialCondition(pre, cons);
		}
		
		return null;
	}
	
}