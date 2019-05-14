package edu.kit.aquaplanning.model;

public class PddlRequirement {

	// atomic requirements
	public static final String STRIPS = "strips";
	public static final String TYPING = "typing";
	public static final String NEGATIVE_PRECONDS = "negative-preconditions";
	public static final String CONDITIONAL_EFFECTS = "conditional-effects";
	public static final String EQUALITY = "equality";
	public static final String ACTION_COSTS = "action-costs";
	public static final String UNIVERSAL_PRECONDS = "universal-preconditions";
	public static final String EXISTENTIAL_PRECONDITIONS = "existential-preconditions";
	public static final String DISJUNCTIVE_PRECONDITIONS = "disjunctive-preconditions";
	public static final String DERIVED_PREDICATES = ":derived-predicates";
	public static final String HTN = ":htn";

	// composite requirements
	public static final String QUANTIFIED_PRECONDITIONS = "quantified-preconditions";
	public static final String[] QUANTIFIED_PRECONDITIONS_CHILDREN = { UNIVERSAL_PRECONDS, EXISTENTIAL_PRECONDITIONS };
	public static final String ADL = "adl";
	public static final String[] ADL_CHILDREN = { STRIPS, TYPING, NEGATIVE_PRECONDS, DISJUNCTIVE_PRECONDITIONS,
			EQUALITY, UNIVERSAL_PRECONDS, EXISTENTIAL_PRECONDITIONS, CONDITIONAL_EFFECTS };

	// supported requirements
	public static final String[] ALL_SUPPORTED = { STRIPS, TYPING, NEGATIVE_PRECONDS, CONDITIONAL_EFFECTS, EQUALITY,
			ACTION_COSTS, UNIVERSAL_PRECONDS, EXISTENTIAL_PRECONDITIONS, DISJUNCTIVE_PRECONDITIONS,
			QUANTIFIED_PRECONDITIONS, ADL, DERIVED_PREDICATES, HTN };

	public static boolean isSupported(String requireKey) {
		for (String supported : ALL_SUPPORTED) {
			if (supported.equals(requireKey) || (":" + supported).equals(requireKey)) {
				return true;
			}
		}
		return false;
	}
}
