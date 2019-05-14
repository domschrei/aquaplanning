package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Type;

/**
 * Provides some tools for full iteration over possible argument combinations
 * under certain constraints.
 */
public class ArgumentCombinationUtils {

	/**
	 * Given a list of possible constants at each argument index, allows to iterate
	 * over all possible combinations.
	 */
	public static class Iterator implements java.util.Iterator<List<Argument>> {

		private List<List<Argument>> eligibleArgs;
		private List<Integer> currentArgIndices;
		private boolean hasNext;

		/**
		 * @param eligibleArgs At index i, contains a list of all eligible constants for
		 *                     the argument position i.
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
			for (int pos = currentArgIndices.size() - 1; pos >= 0; pos--) {

				// Are there more argument options at this position?
				if (currentArgIndices.get(pos) + 1 < eligibleArgs.get(pos).size()) {
					// -- Yes

					// Proceed to the next argument option at this position
					currentArgIndices.set(pos, currentArgIndices.get(pos) + 1);

					// Reset all succeeding argument options to zero
					for (int posAfter = pos + 1; posAfter < currentArgIndices.size(); posAfter++) {
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
	 * For a list of arguments, returns a list containing all valid argument
	 * combinations which can be retrieved by replacing each variable in the
	 * arguments by a constant of an appropriate type. This list of eligible
	 * arguments may have been shortened by applying simplification strategies.
	 */
	public static List<List<Argument>> getEligibleArguments(List<Argument> args, PlanningProblem problem,
			List<Argument> allConstants) {

		List<Type> argTypes = new ArrayList<>();
		for (Argument arg : args) {
			argTypes.add(arg.getType());
		}
		return getEligibleArgumentsOfType(argTypes, problem, allConstants);
	}

	/**
	 * Returns each possible combination of constants with the provided order of
	 * types.
	 */
	public static List<List<Argument>> getEligibleArgumentsOfType(List<Type> argTypes, PlanningProblem problem,
			List<Argument> allConstants) {

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
}