package edu.kit.aquaplanning.grounding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.util.Logger;
import edu.kit.aquaplanning.model.lifted.NumericExpression.TermType;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.condition.NumericEffect;
import edu.kit.aquaplanning.model.lifted.condition.Quantification;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.condition.NumericEffect.Type;
import edu.kit.aquaplanning.model.lifted.condition.Quantification.Quantifier;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.Axiom;
import edu.kit.aquaplanning.model.lifted.NumericExpression;

/**
 * Provides simplification routines for lifted planning problems,
 * in particular for the structure of logical expressions and the
 * resolution of quantifications.
 */
public class Preprocessor {

	private Configuration config;
	private PlanningProblem problem;
	
	public Preprocessor(Configuration config) {
		this.config = config;
	}
	
	/**
	 * Does an in-place preprocessing of the problem at hand.
	 * Depending on the known configuration, all logical expressions
	 * are either simplified into a structure where negations occur
	 * only on an atomic level, or they are even converted into
	 * disjunctive normal form (DNF). Quantifications are always
	 * resolved (using the constants defined in the problem).
	 */
	public void preprocess(PlanningProblem problem) {
		
		this.problem = problem;
		
		// If necessary and possible, compile away total-cost function
		// into much simpler per-operator cost attributes
		extractActionCosts();
		
		// Eliminate quantifications,
		// Simplify structure of logical expressions,
		// Convert to DNF, if desired
		boolean convertToDNF = !config.keepDisjunctions || config.eliminateConditionalEffects;
		simplifyProblem(convertToDNF);
		if (convertToDNF) {
			// Split DNF operators w.r.t. their preconditions
			// into new, simple operators
			List<Operator> newOperators = new ArrayList<>();
			for (Operator op : problem.getOperators()) {
				newOperators.addAll(split(op));
			}
			problem.getOperators().clear();
			problem.getOperators().addAll(newOperators);
		}
	}
	
	/**
	 * Simplifies the structure of all logical expressions in the problem.
	 */
	private void simplifyProblem(boolean toDNF) {
		
		// Simplify operators
		List<Operator> operators = new ArrayList<>();
		for (Operator op : problem.getOperators()) {
			simplify(op, toDNF);
			operators.add(op);
		}
		problem.getOperators().clear();
		problem.getOperators().addAll(operators);
		if (config.eliminateConditionalEffects) {			
			eliminateConditionalEffects();
		}
		
		// Simplify goal
		ConditionSet goalSet = new ConditionSet(ConditionType.conjunction);
		for (AbstractCondition cond : problem.getGoals()) {
			goalSet.add(cond);
		}
		AbstractCondition newGoal = instantiateQuantifications(goalSet);
		newGoal = newGoal.simplify(false);
		if (toDNF) {
			newGoal = newGoal.getDNF();
		}
		problem.getGoals().clear();
		problem.getGoals().add(newGoal);
		
		// Simplify derived conditions
		Map<String, Axiom> derived = problem.getDerivedPredicates();
		for (Axiom cond : derived.values()) {
			AbstractCondition c = cond.getCondition();
			c = instantiateQuantifications(c).simplify(false);
			if (toDNF) {
				c = c.getDNF();
			}
			cond.setCondition(c);
		}
	}
	
	private void simplify(Operator op, boolean toDNF) {
		
		// Preconditions
		AbstractCondition pre = op.getPrecondition();
		pre = instantiateQuantifications(pre);
		pre = pre.simplify(/*negated = */false);
		if (toDNF) {
			pre = pre.getDNF();
		}
		op.setPrecondition(pre);
		
		// Effects
		AbstractCondition eff = op.getEffect();
		eff = instantiateQuantifications(eff);
		eff = eff.simplify(/*negated = */false);
		if (toDNF) {
			eff = eff.getDNF();
		}
		op.setEffect(eff);
	}
	
	/**
	 * Resolves all quantifications occurring in the provided condition.
	 * Existential quantifications are replaced with a disjunction and
	 * universal quantifications are replaced with a conjunction.
	 * Quantified variables in nested conditions are resolved for each
	 * possible constant defined in the problem.
	 */
	private AbstractCondition instantiateQuantifications(AbstractCondition abstractCondition) {
		
		// Recursively traverse the condition tree
		return abstractCondition.traverse(cond -> {
			
			// Only apply some change for quantifications
			if (cond.getConditionType() == ConditionType.quantification) {
				Quantification q = (Quantification) cond;
				
				// Inner condition is already instantiated (head recursion)
				AbstractCondition innerCondition = q.getCondition();
				
				// New, dequantified condition
				ConditionType type = null;
				if (q.getQuantifier().equals(Quantifier.universal)) {
					type = ConditionType.conjunction; // forall: big AND
				} else {
					type = ConditionType.disjunction; // exists: big OR
				}
				ConditionSet dequantifiedSet = new ConditionSet(type);
				
				// For each quantified variable, replace its occurrences 
				// with all possible constants
				List<List<Argument>> eligibleArgs = 
				ArgumentCombinationUtils.getEligibleArguments(q.getVariables(), 
						problem, problem.getConstants());
				ArgumentCombinationUtils.iterator(eligibleArgs).forEachRemaining(args -> {
					
					AbstractCondition deq = innerCondition.getConditionBoundToArguments(
							q.getVariables(), args);
					dequantifiedSet.add(deq);
				});
				
				return dequantifiedSet;
			}
			return cond;
			
		}, AbstractCondition.RECURSE_HEAD);
	}
	
	/**
	 * Given an operator in DNF, creates a new operator for each
	 * element of the disjunction. This leads to a set of operators
	 * which together can achieve exactly the same as the old operator.
	 * Conditional effects are split up into multiple effects
	 * in a similar manner.
	 */
	private List<Operator> split(Operator op) {
		
		List<Operator> newOperators = new ArrayList<>();
		
		// Compute flat conditional effects
		ConditionSet effectSet = new ConditionSet(ConditionType.conjunction);
		AbstractCondition effect = effectSet;
		if (op.getEffect().getConditionType() == ConditionType.consequential) {
			// Top level is a conditional effect
			
			// Split up conditional effect and add each of them
			for (ConsequentialCondition cc : split((ConsequentialCondition) op.getEffect())) {							
				effectSet.add(cc);
			}
		} else if (op.getEffect().getConditionType() == ConditionType.conjunction) {
			// Top level is a conjunction
			
			// For all contained effects:
			for (AbstractCondition c : ((ConditionSet) op.getEffect()).getConditions()) {
				
				if (c.getConditionType() == ConditionType.consequential) {
					// Split conditional effect
					for (ConsequentialCondition cc : split((ConsequentialCondition) c)) {							
						effectSet.add(cc);
					}
				} else {
					// Add usual effect
					effectSet.add(c);
				}
			}
		} else {
			// Top level is atomic
			effect = op.getEffect();
		}
		
		// Split operator along its top-level disjunction
		if (op.getPrecondition().getConditionType() == ConditionType.disjunction) {
			
			// Create new operator for each contained element in disjunction
			int counter = 1;
			for (AbstractCondition child : ((ConditionSet) op.getPrecondition()).getConditions()) {
				
				Operator opSplit = new Operator(op.getName() + "$" + counter + "$");
				for (Argument arg : op.getArguments()) {
					opSplit.addArgument(arg);
				}
				opSplit.setPrecondition(child);
				opSplit.setEffect(effect);
				
				newOperators.add(opSplit);
				counter++;
			}
			
		} else {
			// No disjunction: Operator does not need to be split
			Operator opSplit = new Operator(op.getName());
			for (Argument arg : op.getArguments()) {
				opSplit.addArgument(arg);
			}
			opSplit.setPrecondition(op.getPrecondition().copy());
			opSplit.setEffect(effect);
			
			newOperators.add(opSplit);
		}
		
		return newOperators;
	}
	
	/**
	 * If the provided conditional effect (in DNF!) has a disjunction on its
	 * top level, it is split into multiple conditional effects which together
	 * are equivalent to the original one. If no disjunction is present,
	 * the original conditional effect is returned as the only element.
	 */
	private List<ConsequentialCondition> split(ConsequentialCondition cond) {
		
		List<ConsequentialCondition> splitConds = new ArrayList<>();
		if (cond.getPrerequisite().getConditionType() == ConditionType.disjunction) {
			// For each element in the disjunction, create a new cond. effect
			for (AbstractCondition partCond : ((ConditionSet) cond.getPrerequisite()).getConditions()) {
				ConsequentialCondition partCC = new ConsequentialCondition();
				partCC.setPrerequisite(partCond);
				partCC.setConsequence(cond.getConsequence());
				splitConds.add(partCC);
			}
		} else {
			// The prerequisite is already simple, so add the old cond. effect
			splitConds.add(cond);
		}
		
		return splitConds;
	}
	
	private void eliminateConditionalEffects() {
		
		List<Operator> newOperators = new ArrayList<>();
		
		for (Operator op : problem.getOperators()) {
			
			// Partition effects of the operator into conditional and other effects
			List<ConsequentialCondition> ccs = new ArrayList<>();
			ConditionSet normalEffects = new ConditionSet(ConditionType.conjunction);
			AbstractCondition effect = op.getEffect();
			List<AbstractCondition> effectList = new ArrayList<>();
			effectList.add(effect);
			for (int i = 0; i < effectList.size(); i++) {
				AbstractCondition eff = effectList.get(i);
				if (eff.getConditionType() == ConditionType.conjunction) {
					effectList.addAll(((ConditionSet) eff).getConditions());
				} else if (eff.getConditionType() == ConditionType.consequential) {
					ccs.add((ConsequentialCondition) eff);
				} else {
					normalEffects.add(eff);
				}
			}
			
			// Warn if amount of resulting STRIPS actions is large
			if (ccs.size() > 4) {
				Logger.log(Logger.WARN, "An operator contains " + ccs.size()
						+ " conditional effects after simplification. "
						+ "Attempting compilation into at least " + (int) Math.pow(2, ccs.size()) 
						+ " STRIPS actions.");
			}
			
			// Create a new operator for each combination of cond. effects
			for (int condEffChoice = 0; condEffChoice < Math.pow(2, ccs.size()); condEffChoice++) {
				
				List<AbstractCondition> posPre = new ArrayList<>();
				List<AbstractCondition> posNeg = new ArrayList<>();
				List<AbstractCondition> eff = new ArrayList<>();
				
				int remainder = condEffChoice;
				for (int ccIdx = 0; ccIdx < ccs.size(); ccIdx++) {
					int power = (int) Math.pow(2, ccs.size()-ccIdx-1);
					if (remainder >= power) {
						remainder -= power;
						posPre.add(ccs.get(ccIdx).getPrerequisite());
						eff.add(ccs.get(ccIdx).getConsequence());
					} else {
						Negation n = new Negation();
						n.setChildCondition(ccs.get(ccIdx).getPrerequisite());
						posNeg.add(n);
					}
				}
				
				String opName = op.getName() + (ccs.size() > 0 ? "*" + condEffChoice + "*" : "");
				Operator newOp = new Operator(opName);
				op.getArguments().forEach(arg -> newOp.addArgument(arg));
				newOp.setCost(op.getCost());
				
				final ConditionSet preconds = new ConditionSet(ConditionType.conjunction);
				preconds.add(op.getPrecondition());
				posPre.forEach(p -> preconds.add(p));
				posNeg.forEach(p -> preconds.add(p));
				newOp.setPrecondition(preconds);
				
				final ConditionSet postconds = new ConditionSet(ConditionType.conjunction);
				postconds.add(normalEffects);
				eff.forEach(c -> postconds.add(c));
				newOp.setEffect(postconds);
				
				simplify(newOp, /*toDNF=*/true);
				newOperators.addAll(split(newOp));
			}
		}
		
		problem.getOperators().clear();
		problem.getOperators().addAll(newOperators);
	}
	
	/**
	 * Removes all occurrences of the numeric fluent (total-cost) 
	 * and instead defines the cost attributes of operators.
	 */
	private void extractActionCosts() {
		
		// Is there a total-cost function?
		Map<String, Function> functions = problem.getFunctions();
		if (!functions.containsKey("total-cost")) {
			return;
		}
		
		Function totalCost = functions.get("total-cost");
		List<Integer> costs = new ArrayList<>();

		// Should action costs definitions be considered overall?
		if (!config.keepActionCosts) {
			Logger.log(Logger.INFO, "Clearing all action cost definitions from the problem.");
			
		} else {
			// Check if action costs can be extracted into flat integers per operator
			
			// Compute all functions which never change
			Set<Function> rigidFunctions = getRigidFunctions();
			
			// For each operator:
			boolean error = false;
			for (Operator op : problem.getOperators()) {
				int cost = 0;
				
				// Check precondition
				AbstractCondition precond = op.getPrecondition();
				if (precond.toString().contains("(total-cost)")) {
					Logger.log(Logger.WARN, "(total-cost) is used in an operator precondition.");
					error = true;
					break;
				}
				
				// Traverse effect, and extract cost increases
				List<AbstractCondition> effects = new ArrayList<>();
				effects.add(op.getEffect());
				while (!effects.isEmpty()) {
					AbstractCondition c = effects.remove(0);
					
					if (c.getConditionType() == ConditionType.numericEffect) {
						NumericEffect eff = (NumericEffect) c;
						if (eff.getFunction().equals(totalCost)) {
							if (eff.getType() != Type.increase) {
								Logger.log(Logger.WARN, "(total-cost) function is defined "
										+ "using operators other than \"increase\".");
								error = true;
							} else if (eff.getExpression().getType() == TermType.constant) {
								cost += eff.getExpression().getValue();
							} else {
								// Allow numeric update if expression is effectively constant,
								// i.e. only rigid functions are used
								List<Function> usedFunctions = getContainedFunctions(eff.getExpression());
								for (Function f : usedFunctions) {
									if (!rigidFunctions.contains(f)) {
										Logger.log(Logger.WARN, "(total-cost) function is increased "
												+ "by a non-atomic and/or non-constant value.");
										error = true;
										break;
									}
								}
								if (!error) {
									Logger.log(Logger.WARN, "(total-cost) function is increased "
											+ "by an effectively constant value, but simplification "
											+ "to an actual constant is not implemented yet.");
									error = true;
								}
							} 
						}
					} else if (c.getConditionType() == ConditionType.conjunction) {
						// Process children as well
						effects.addAll(((ConditionSet) c).getConditions());
					} else if (c.getConditionType() == ConditionType.consequential) {
						// Check if total-cost occurs here
						if (c.toString().contains("(total-cost)")) {
							Logger.log(Logger.WARN, "(total-cost) function appears "
									+ "in a conditional effect.");
							error = true;
						}
					} 
				}
				// Can total-cost be compiled away here?
				if (error) {
					// -- no
					Logger.log(Logger.WARN, "The (total-cost) function will be kept "
							+ "as a full-featured numeric fluent in the problem "
							+ "definition. This can affect performance.");
					return;
				}
				// Remember found cost for this operator
				costs.add(cost);
			}
		}
		
		// Remove all total-cost numeric effects from the operators
		for (int i = 0; i < problem.getOperators().size(); i++) {
			
			Operator op = problem.getOperators().get(i);
			if (config.keepActionCosts)
				op.setCost(costs.get(i));
			
			AbstractCondition cond = op.getEffect();
			
			// Traverse effect condition, removing all total-cost effects
			cond = cond.traverse((c -> {
				if (c.getConditionType() == ConditionType.numericEffect) {
					NumericEffect numEff = (NumericEffect) c;
					if (numEff.getFunction().equals(totalCost)) {
						return null;
					}
				}
				return c;
			}), AbstractCondition.RECURSE_HEAD);
			
			op.setEffect(cond);
		}
		
		// Remove total-cost from functions
		problem.getFunctions().remove("total-cost");
		problem.getInitialFunctionValues().remove(totalCost);
	}
	
	private Set<Function> getRigidFunctions() {
		
		// Set of all functions
		final Set<Function> functionSet = new TreeSet<>((f1, f2) -> f1.getName().compareTo(f2.getName()));
		for (Function f : problem.getFunctions().values()) {
			functionSet.add(f);
		}
		
		// Filter functions by occurrence in an operator effect
		for (Operator op : problem.getOperators()) {
			AbstractCondition effect = op.getEffect();
			effect.traverse(c -> {
				switch (c.getConditionType()) {
				case numericEffect:
					functionSet.remove(((NumericEffect) c).getFunction());
				default:
					return c;
				}
			}, AbstractCondition.RECURSE_HEAD);
		}
		
		return functionSet;
	}
	
	private List<Function> getContainedFunctions(NumericExpression exp) {
		List<Function> functions = new ArrayList<>();
		switch (exp.getType()) {
		case function:
			functions.add(exp.getFunction());
			break;
		case constant:
			break;
		default:
			for (NumericExpression child : exp.getChildren()) {
				functions.addAll(getContainedFunctions(child));
			}
			break;
		}
		return functions;
	}
}
