package edu.kit.aquaplanning.parsing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.Quantification.Quantifier;
import edu.kit.aquaplanning.parsing.PddlParser.ActionDefBodyContext;
import edu.kit.aquaplanning.parsing.PddlParser.ActionDefContext;
import edu.kit.aquaplanning.parsing.PddlParser.AtomicFormulaSkeletonContext;
import edu.kit.aquaplanning.parsing.PddlParser.AtomicNameFormulaContext;
import edu.kit.aquaplanning.parsing.PddlParser.AtomicTermFormulaContext;
import edu.kit.aquaplanning.parsing.PddlParser.CEffectContext;
import edu.kit.aquaplanning.parsing.PddlParser.CondEffectContext;
import edu.kit.aquaplanning.parsing.PddlParser.ConstantsDefContext;
import edu.kit.aquaplanning.parsing.PddlParser.DomainNameContext;
import edu.kit.aquaplanning.parsing.PddlParser.EffectContext;
import edu.kit.aquaplanning.parsing.PddlParser.FunctionListContext;
import edu.kit.aquaplanning.parsing.PddlParser.FunctionsDefContext;
import edu.kit.aquaplanning.parsing.PddlParser.GoalContext;
import edu.kit.aquaplanning.parsing.PddlParser.GoalDescContext;
import edu.kit.aquaplanning.parsing.PddlParser.InitContext;
import edu.kit.aquaplanning.parsing.PddlParser.InitElContext;
import edu.kit.aquaplanning.parsing.PddlParser.MetricSpecContext;
import edu.kit.aquaplanning.parsing.PddlParser.NameLiteralContext;
import edu.kit.aquaplanning.parsing.PddlParser.ObjectDeclContext;
import edu.kit.aquaplanning.parsing.PddlParser.PEffectContext;
import edu.kit.aquaplanning.parsing.PddlParser.PddlDocContext;
import edu.kit.aquaplanning.parsing.PddlParser.PredicatesDefContext;
import edu.kit.aquaplanning.parsing.PddlParser.ProblemDeclContext;
import edu.kit.aquaplanning.parsing.PddlParser.RequireDefContext;
import edu.kit.aquaplanning.parsing.PddlParser.SingleTypeNameListContext;
import edu.kit.aquaplanning.parsing.PddlParser.SingleTypeVarListContext;
import edu.kit.aquaplanning.parsing.PddlParser.TypedNameListContext;
import edu.kit.aquaplanning.parsing.PddlParser.TypedVariableListContext;
import edu.kit.aquaplanning.parsing.PddlParser.TypesDefContext;

@SuppressWarnings("deprecation")
public class ProblemParser extends PddlBaseListener {
	
	// Fields to populate during the parsing process
	private String domainName;
	private String problemName;
	private List<String> requirements;
	private Map<String, Type> types;
	private List<Argument> constants; // both from domain and problem
	private Map<String, Predicate> predicates;
	private List<Operator> operators;
	private List<Condition> initialState;
	private List<Condition> goals;
	private List<Quantification> quantifiedGoals;
	private boolean hasActionCosts;
	
	private ParserRuleContext parseContext;
	/**
	 * All different contexts of the parser which may need to be remembered
	 */
	private enum ParseContext {
		typeDefs, constantDefs, predicateDefs, functionsDef, actionDef, 
		actionPre, actionEff, actionCondEff, actionCondEffPre, actionCondEffCons,
		objectDefs, initialStateDef, goalDef, atom, negation, conjunction,
		quantification, literal, equivalence;
	}
	private Stack<ParseContext> context;
	private String parsedFile; // the file *currently* being parsed

	private Predicate predicate;
	private Type supertype;
	
	/**
	 * Parses a pair of PDDL domain and problem files.
	 * Returns the parsed problem instance as an object.
	 * 
	 * @param domainFile
	 * @param problemFile
	 */
	public PlanningProblem parse(String domainFile, String problemFile) 
			throws FileNotFoundException, IOException {
		
		context = new Stack<>();
		
		// Get domain
        ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(domainFile));
        PddlLexer lexer = new PddlLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PddlParser parser = new PddlParser(tokens);
        parser.setBuildParseTree(true);
        PddlDocContext ctx = parser.pddlDoc();
        
        // Parse domain
        parsedFile = domainFile;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, ctx);
        
        // Get problem
        in = new ANTLRInputStream(new FileInputStream(problemFile));
        lexer = new PddlLexer(in);
        tokens = new CommonTokenStream(lexer);
        parser = new PddlParser(tokens);
        parser.setBuildParseTree(true);
        ctx = parser.pddlDoc();
        
        // Parse problem
        parsedFile = problemFile;
        walker = new ParseTreeWalker();
        walker.walk(this, ctx);
        
        // Create object to return
        PlanningProblem problem = new PlanningProblem(domainName, problemName, 
        		types, constants, predicates, operators, 
        		initialState, goals, quantifiedGoals, hasActionCosts);
        return problem;
	}
	
	
	
	// Remember the context for error messages etc.
	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		parseContext = ctx;
	}
	
	
	
	// Names of the domain and the problem
	
	@Override
	public void enterDomainName(DomainNameContext ctx) {
		domainName = ctx.children.get(2).getText();
	}
	@Override
	public void enterProblemDecl(ProblemDeclContext ctx) {
		problemName = ctx.children.get(2).getText();
	}
	
	
	
	// Requirement parsing
	
	@Override
	public void enterRequireDef(RequireDefContext ctx) {
		
		if (requirements == null)
			requirements = new ArrayList<>();
		
		// Read all requirements. It is not checked whether a requirement
		// is actually needed, or if features from an unspecified requirement
		// are used. It is only checked to give an immediate error when an 
		// explicit requirement is unsupported.
		for (int childIdx = 2; childIdx+1 < ctx.getChildCount(); childIdx++) {
			String requirement = ctx.getChild(childIdx).getText();
			switch(requirement) {
			case ":strips":
			case ":typing":
			case ":negative-preconditions":
			case ":conditional-effects":
			case ":equality":
			case ":universal-preconditions":
			case ":action-costs":
				if (!requirements.contains(requirement.substring(1))) {
					requirements.add(requirement.substring(1));
				}
				break;
			default:
				error("Unsupported requirement: " + requirement + ".");
			}
		}
	}
	
	
	
	// Types definition (domain)
	
	@Override
	public void enterTypesDef(TypesDefContext ctx) {
		
		context.push(ParseContext.typeDefs);
		types = new HashMap<>();

		// Create a virtual supertype for general predicate "="
		supertype = new Type("_root_type");
		types.put("_root_type",  supertype);
	}

	@Override
	public void exitTypesDef(TypesDefContext ctx) {
		context.pop(); // typeDefs
	}

	@Override
	public void enterTypedNameList(TypedNameListContext ctx) {
		
		if (ctx.children == null) {
			// Empty constants definition - just skip it
			return;
		}
		
		// direct type definitions (without dash)
		// Traverse type list from back to front, because subtype definitions
		// will always be at the front
		for (int childIdx = ctx.children.size()-1; childIdx >= 0; childIdx--) {
			
			if (ctx.children.get(childIdx).getChildCount() > 1) {
				// A subtype definition begins here
				break;
			} else {
				// Primitive type: add, if not already contained
				String typeName = ctx.children.get(childIdx).getText().toLowerCase();
				if (!types.containsKey(typeName)) {
					types.put(typeName, new Type(typeName));
					supertype.addSubtype(typeName);
				} else {
					error("Type \"" + typeName + "\" is defined multiple times.");
				}
			}
		}
	}
	
	/**
	 * Parsing of structures of the form:
	 * element1 element2 ... elementk - type
	 * Used inside type, constant, and object definitions.
	 */
	@Override
	public void enterSingleTypeNameList(SingleTypeNameListContext ctx) {
		
		List<String> elements = new ArrayList<>();
		boolean readingSubtypes = true;
		for (int i = 0; i < ctx.getChildCount(); i++) {
			ParseTree child = ctx.getChild(i);
			
			if (readingSubtypes && child.getText().equals("-")) {
				
				// Transition to supertype
				readingSubtypes = false;
				
			} else {
				
				String elemName = child.getText().toLowerCase();
				
				if (readingSubtypes) {
					// left side of the dash
					
					// Add subtype
					elements.add(elemName);
					if (isInContext(ParseContext.typeDefs) 
							&& !types.containsKey(elemName)) {
						
						types.put(elemName, new Type(elemName));
					}
					
				} else {
					// right side of the dash
					
					Type type;
					if (types.containsKey(elemName)) {
						type = types.get(elemName);
					} else {
						type = new Type(elemName);
						types.put(elemName, type);
						supertype.addSubtype(elemName);
					}
					
					if (isInContext(ParseContext.typeDefs)) {
						// Type definitions: set supertype
						type.addSubtypes(elements);
					
					} else if (isInContext(ParseContext.constantDefs) 
							|| isInContext(ParseContext.objectDefs)) {
						// Constant definitions: add constants
						for (String constantName : elements) {
							constants.add(new Argument(constantName, type));
						}
					}
				}		
			}
		}		
	}
	
	
	
	// Constants definition
	
	@Override
	public void enterConstantsDef(ConstantsDefContext ctx) {
		
		context.push(ParseContext.constantDefs);
		constants = new ArrayList<>();
	}

	@Override
	public void exitConstantsDef(ConstantsDefContext ctx) {
		
		assertTopContext(ParseContext.constantDefs);
		context.pop();
	}

	
	
	// Functions definition
	
	@Override
	public void enterFunctionsDef(FunctionsDefContext ctx) {
		
		context.push(ParseContext.functionsDef);
	}
	
	@Override
	public void enterFunctionList(FunctionListContext ctx) {
		
		if (ctx.getText().equalsIgnoreCase("(total-cost)-number")) {
			// Metric function detected
			hasActionCosts = true;
			
		} else {
			error("Unsupported function definition found – "
				+ "only the function \"(total-cost) - number\" is allowed.");
		}
	}
	
	@Override
	public void exitFunctionsDef(FunctionsDefContext ctx) {
		
		assertTopContext(ParseContext.functionsDef);
		context.pop();
	}

	
	
	// Predicates definition	
	
	@Override
	public void enterPredicatesDef(PredicatesDefContext ctx) {
		
		context.push(ParseContext.predicateDefs);
		predicates = new HashMap<>();
	}

	@Override
	public void exitPredicatesDef(PredicatesDefContext ctx) {
		
		assertTopContext(ParseContext.predicateDefs);
		context.pop();
	}
	
	@Override
	public void enterAtomicFormulaSkeleton(AtomicFormulaSkeletonContext ctx) {
		
		if (isInContext(ParseContext.predicateDefs)) {
			// A new predicate is being defined; create the object
			String predName = ctx.children.get(1).getText().toLowerCase();
			predicate = new Predicate(predName);
			predicates.put(predName, predicate);
		}
	}

	@Override
	public void exitAtomicFormulaSkeleton(AtomicFormulaSkeletonContext ctx) {
		
		// Predicate is fully processed at this point
		predicate = null;
	}

	/**
	 * Parsing of structures of the form
	 * <br/><code>arg1 arg2 ... argk - type</code>
	 * <br/>where each arg may be a variable or a constant.
	 * Used in predicate definitions, action argument definitions,
	 * and quantifications (in action preconditions, effects and goals).
	 */
	@Override
	public void enterSingleTypeVarList(SingleTypeVarListContext ctx) {
		
		// Extract type
		String typeName = ctx.children.get(ctx.children.size()-1).getText().toLowerCase();
		Type type = types.get(typeName);
		if (type == null) {
			error("Usage of undefined type \"" + typeName + "\".");
		}
		
		// Iterate over arguments
		for (int childIdx = 0; childIdx < ctx.children.size()-2; childIdx++) {
			
			if (isInContext(ParseContext.predicateDefs)) {	
				// Add type to current predicate
				predicate.addArgumentType(type);
				
			} else if (isInContext(ParseContext.quantification)) {
				// Add argument to quantification
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				Quantification q = currentQuantification();
				q.addVariable(new Argument(argName, type));

			} else if (isInContext(ParseContext.actionDef)) {
				// Add argument to current operator
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				currentOperator().addArgument(new Argument(argName, type));
			}
		}
	}
	
	/**
	 * Parsing of structures of the form <code>singleTypeVarList* variable*</code>.
	 * SingleTypeVarLists are handled in another method, so here only the isolated
	 * variables at the end are processed. 
	 */
	@Override
	public void enterTypedVariableList(TypedVariableListContext ctx) {
		
		if (isInContext(ParseContext.quantification)) {
			// Quantified variables
			
			// Read variables from left to right until a SingleTypeVarList is hit
			for (int childIdx = ctx.children.size()-1; childIdx >= 0; childIdx--) {
				
				if (ctx.children.get(childIdx).getChildCount() > 1) {
					// A typed definition begins here
					break;
				} else {
					// Variable ("untyped" for now; type is added later)
					String varName = ctx.children.get(childIdx).getText();
					Type type = new Type("quantified"); // to be added later
					Argument arg = new Argument(varName, type);
					currentQuantification().addVariable(arg);
				}
			}
		}
	}
	
	
	
	// Action definitions
	
	@Override
	public void enterActionDef(ActionDefContext ctx) {
		
		context.push(ParseContext.actionDef);
		if (operators == null)
			operators = new ArrayList<>();
		
		// Create new, empty operator
		String opName = ctx.children.get(2).getText().toLowerCase();
		Operator op = new Operator(opName);
		operators.add(op);
	}

	@Override
	public void exitActionDef(ActionDefContext ctx) {
		
		assertTopContext(ParseContext.actionDef);
		context.pop();
	}

	/**
	 * An action body may contain a preconditions block, 
	 * and always contains an effects block.
	 */
	@Override
	public void enterActionDefBody(ActionDefBodyContext ctx) {
		
		// Precondition definition?
		if (ctx.children.get(0).getText().equalsIgnoreCase(":precondition")) {
			
			// Empty precondition?
			if (ctx.children.size() > 2 &&
				ctx.children.get(0).getText().equals("(") &&
				ctx.children.get(1).getText().equals(")")) {
				
				// Parse effects now (in the case that the preconds are empty)
				context.push(ParseContext.actionEff);

			} else {
				
				// Parse preconditions now
				context.push(ParseContext.actionPre);
			}
			
		} else if (ctx.children.get(0).getText().equalsIgnoreCase(":effect")) {
			
			// Parse effects now (in the case that there are no preconds)
			context.push(ParseContext.actionEff);
		}
	}
	
	@Override
	public void exitActionDefBody(ActionDefBodyContext ctx) {
		
		assertTopContext(ParseContext.actionEff);
		context.pop();
	}
	
	
	
	/**
	 * Parsing of a composition of conditions.
	 * Used in action preconditions, conditional effect prerequisites, and goals.
	 */
	@Override
	public void enterGoalDesc(GoalDescContext ctx) {
		
		// If conditional effects are being parsed, 
		// then the prerequisite part is entered now
		if (isTopContext(ParseContext.actionCondEff)) {
			context.push(ParseContext.actionCondEffPre);
		}
		
		if (ctx.children.size() == 1) {
			
			// Atomic formula
			context.push(ParseContext.atom);
			// Will be processed as an atomicTermFormula
			
		} else {
			
			// Check the kind of the expression
			String expressionType = ctx.children.get(1).getText();
			
			if (expressionType.equalsIgnoreCase("not")) {
				if (isTopContext(ParseContext.negation)) {
					error("Nested negations are not supported; "
							+ "you can just remove them pairwise.");
				}
				context.push(ParseContext.negation); // remember negation
				
			} else if (expressionType.equalsIgnoreCase("and")) {
				// Currently, conjunctions are used for nothing
				// except for bundling multiple conditions into a list
				if (isTopContext(ParseContext.negation)) {
					error("Negated conjunctions of conditions are unsupported "
						+ "(because they effectively become disjunctive conditions).");
				}
				context.push(ParseContext.conjunction);
				
			} else if (expressionType.equalsIgnoreCase("forall")) {
				// Universal quantification
				context.push(ParseContext.quantification);
				
				// Create quantification, add to currently parsed object
				Quantification q = new Quantification(Quantifier.universal);
				if (isInContext(ParseContext.actionDef)) {
					if (isInContext(ParseContext.actionCondEffPre)) {
						currentConditionalEffect().addPrerequisite(q);
					} else if (isInContext(ParseContext.actionCondEffCons)) {
						currentConditionalEffect().addConsequence(q);
					} else {	
						currentOperator().addQuantifiedPrecondition(q);
					}
				} else if (isInContext(ParseContext.goalDef)) {
					quantifiedGoals.add(new Quantification(Quantifier.universal));
				}
			
			} else if (expressionType.equalsIgnoreCase("exists")) {
				// Existential quantification - not supported as of now
				context.push(ParseContext.quantification);
				error("Existantial quantifications are not supported.");
				
			} else if (expressionType.equals("=")) {
				// Equality
				
				boolean negated = isTopContext(ParseContext.negation);
				context.push(ParseContext.equivalence);
				
				// Create new "equals" predicate, if necessary
				Predicate p = null;
				if (!predicates.containsKey("=")) {						
					p = new Predicate("=");
					p.addArgumentType(supertype);
					p.addArgumentType(supertype);
					predicates.put("=", p);
				} else {
					p = predicates.get("=");
				}
				
				// Create corresponding condition and add it to currently parsed object
				Condition cond = new Condition(p, negated);
				cond.addArgument(new Argument(ctx.children.get(2).getText(), supertype));
				cond.addArgument(new Argument(ctx.children.get(3).getText(), supertype));
				addToCurrentObject(cond);
			} else {
				error("Unsupported conditional expression \"" + expressionType + "\".");
			}
		}
		
	}

	@Override
	public void exitGoalDesc(GoalDescContext ctx) {
		
		context.pop(); // atomic, and, not, forall, exists, etc.
		
		if (isTopContext(ParseContext.actionPre)) {
			
			// Preconditions are finished: switch to effects
			assertTopContext(ParseContext.actionPre);
			context.pop();
			context.push(ParseContext.actionEff);
		
		} else if (isTopContext(ParseContext.actionCondEffPre)) {
			
			// Prerequisites of an effect are finished;
			// switch to consequences
			assertTopContext(ParseContext.actionCondEffPre);
			context.pop();
		}
	}

	
	
	/**
	 * Parsing of a condition, where each argument may be either
	 * a variable or a constant.
	 * Used in preconditions, effects, and goals.
	 */
	@Override
	public void enterAtomicTermFormula(AtomicTermFormulaContext ctx) {
		
		// Read the relevant context sub-stack and apply relevant elements
		boolean negated = false;
		for (int pos = context.size()-1; pos >= 0; pos--) {
			ParseContext c = context.get(pos);
			
			// End of relevant context reached?
			if (c == ParseContext.actionPre || c == ParseContext.actionEff 
				|| c == ParseContext.goalDef || c == ParseContext.quantification) {
				break;
			}
			
			// Apply negation
			if (c == ParseContext.negation) {
				negated = !negated;
			}
		}
		
		// Create condition
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null) {
			error("Predicate \"" + predicateName + "\" is undefined.");
		}
		Condition cond = new Condition(predicate, negated);
		
		// Add arguments of condition
		for (int childIdx = 2; childIdx+1 < ctx.children.size(); childIdx++) {
			
			// Name and type of the argument
			String termStr = ctx.children.get(childIdx).getText().toLowerCase();
			Type type = null;
			
			// Is this argument a variable or a constant?
			if (termStr.charAt(0) == '?') {
				
				// -- variable
				
				if (isInContext(ParseContext.quantification)) {
					// Quantification
					
					// Is the variable bound to the quantifier?
					Quantification q = currentQuantification();
					for (Argument quantifiedVar : q.getVariables()) {
						if (termStr.equalsIgnoreCase(quantifiedVar.getName())) {								
							// -- yes: quantified variable (type will be inferred later)
							type = new Type("quantified");
						}
					} // -- if not: type == null
				
				} else if (isInContext(ParseContext.goalDef)) {
					error("Variables in goal specifications are illegal.");
				}
				
				if (type == null) {
					// Check if the variable occurs in the operator definition,
					// and which type it has
					for (Argument arg : operators.get(operators.size()-1).getArguments()) {
						if (arg.getName().equalsIgnoreCase(termStr)) {
							type = arg.getType();
						}
					}
				}
				
			} else {
				
				// -- constant
				
				// Check if there is a fitting constant,
				// and which type it has
				for (Argument constant : constants) {
					if (constant.getName().equalsIgnoreCase(termStr)) {
						type = constant.getType();
					}
				}
			}
			
			if (type == null) {
				error("No fitting type could be found for argument \"" + termStr + "\".");
			}
			
			// Add variable/constant as an argument
			cond.addArgument(new Argument(termStr, type));
		}
		
		// Add created condition to preconditions/effects
		// of the current operator
		if (isInContext(ParseContext.quantification)) {
			// We need to infer the types of bound variables
			Quantification q = currentQuantification();
			inferQuantifiedTypes(q, cond);
			addToCurrentObject(cond);
		} else {
			addToCurrentObject(cond);
		}
		
		// Basic checks for correct typing and predicate use
		checkConsistency(cond);
	}		
	
	
	
	// Effect definition
	
	@Override
	public void enterEffect(EffectContext ctx) {
		
		// Recognize conjunctions ("and") inside effects
		if (isInContext(ParseContext.actionEff)) {
			if (ctx.children.get(0).getText().equals("(")) {
				if (ctx.children.get(1).getText().equalsIgnoreCase("and")) {
					context.push(ParseContext.conjunction);
				}
			}
		}
	}

	@Override
	public void exitEffect(EffectContext ctx) {
		
		// If there is a conjunction left in the context, remove it
		if (isTopContext(ParseContext.conjunction)) {
			context.pop();
		}
	}

	@Override
	public void enterCEffect(CEffectContext ctx) {
		
		if (ctx.children.size() == 5) {
			// Conditional effect:
			// ( when <goalDesc> <condEffect> )
			
			// Create new cond. effect object
			if (isInContext(ParseContext.quantification)) {
				// Conditional effect inside quantification
				currentQuantification().addCondition(new ConsequentialCondition());
			} else {
				// Conditional effect as one of the operator effects
				currentOperator().addConditionalEffect(new ConsequentialCondition());
			}
			context.push(ParseContext.actionCondEff);
		
		} else if (ctx.children.size() == 7 
				&& ctx.children.get(1).getText().equalsIgnoreCase("forall")) {
			// Quantification as one of the operator effects
			
			// Create new quantification object
			context.push(ParseContext.quantification);
			currentOperator().addQuantifiedEffect(new Quantification(Quantifier.universal));
			
		} else if (ctx.children == null || ctx.children.size() > 1) {
			error("An effect specification is unsupported: " + ctx.getText());
		}
	}
	
	@Override
	public void exitCEffect(CEffectContext ctx) {
		
		// Remove any context that must belong to this CEffect
		if (isTopContext(ParseContext.actionCondEff)) {			
			context.pop(); // actionCondEff
		} else if (isTopContext(ParseContext.quantification)) {
			context.pop(); // quantification
		}
	}
	
	/**
	 * Parsing of the second part of a conditional effect:
	 * The consequences.
	 */
	@Override
	public void enterCondEffect(CondEffectContext ctx) {
		
		context.push(ParseContext.actionCondEffCons);
	}
	
	@Override
	public void exitCondEffect(CondEffectContext ctx) {
		
		assertTopContext(ParseContext.actionCondEffCons);
		context.pop();
	}
	
	@Override
	public void enterPEffect(PEffectContext ctx) {
		
		if (isInContext(ParseContext.actionEff)) {
			
			if (ctx.children.size() > 4) {
				
				// Function operation
				if (hasActionCosts) {

					// Get parameters of the function operation
					String operation = ctx.children.get(1).getText();
					String functionName = ctx.children.get(2).getText();
					String value = ctx.children.get(3).getText();
					
					// Only total-cost is a valid function
					if (functionName.equalsIgnoreCase("(total-cost)")) {
						
						// No conditional effects, and no increase/assign operation
						if (isInContext(ParseContext.actionCondEff)) {
							error("Function operations in conditional effects "
									+ "are not supported.");
						} else if (!operation.equalsIgnoreCase("increase")) {
							error("Action cost may only increase as an operator effect.");
						} else {
							
							// Set action cost of the operator
							try {
								currentOperator().setCost(Integer.parseInt(value));
							} catch (NumberFormatException e) {
								error("Invalid function assignment \"" + value 
									+ "\" (only positive integer constants are allowed).");
							}
						}
						
					} else {
						error("Unrecognized function \"" + functionName + "\".");
					}
					
				} else {
					error("A function effect is used, "
						+ "but no functions have been defined.");
				}
				
			} else if (ctx.children.size() == 1) {
				
				// atomic term
				context.push(ParseContext.atom);
				
			} else {
				
				// "not"
				context.push(ParseContext.negation);
			}
		}
	}

	@Override
	public void exitPEffect(PEffectContext ctx) {
		
		if (isTopContext(ParseContext.atom) 
				|| isTopContext(ParseContext.negation))
			context.pop(); // atom, negation
	}
	
	
	
	// Objects definition of problem file
	// (just gets merged into the domain's constants)
	
	@Override
	public void enterObjectDecl(ObjectDeclContext ctx) {
		
		context.push(ParseContext.objectDefs);
		if (constants == null)
			constants = new ArrayList<>();
	}

	@Override
	public void exitObjectDecl(ObjectDeclContext ctx) {
		
		assertTopContext(ParseContext.objectDefs);
		context.pop();
	}
	
	
	
	// Initial state definition
	
	@Override
	public void enterInit(InitContext ctx) {
		
		context.push(ParseContext.initialStateDef);
		initialState = new ArrayList<>();
	}

	@Override
	public void exitInit(InitContext ctx) {
		
		assertTopContext(ParseContext.initialStateDef);
		context.pop();
	}

	@Override
	public void enterInitEl(InitElContext ctx) {
		
		if (ctx.children.size() == 5) {
			
			if (ctx.children.get(1).getText().equals("=")) {
				
				if (!ctx.children.get(1).getText().equals("=")) {
					error("Invalid function assignment operator \"" 
						+ ctx.children.get(1).getText() + "\" in goal definition.");
				}
				if (!ctx.children.get(2).getText().equalsIgnoreCase("(total-cost)")) {
					error("Invalid function \"" 
						+ ctx.children.get(2).getText() + "\" in goal definition.");
				}
				if (!ctx.children.get(3).getText().equals("0")) {
					error("Invalid function assignment value \"" 
						+ ctx.children.get(3).getText() + "\" in goal definition "
								+ "(only 0 is allowed).");
				}
				
			} else {				
				error("An unsupported initial state specification is used.");			
			}
			
		}
	}
	
	@Override
	public void enterNameLiteral(NameLiteralContext ctx) {
		
		context.push(ParseContext.literal);
		
		// "not" expression
		if (ctx.children.size() > 1) {
			context.push(ParseContext.negation);
		}
	}
	
	@Override
	public void exitNameLiteral(NameLiteralContext ctx) {
		
		if (isTopContext(ParseContext.negation)) {
			context.pop(); // negation
		}
		assertTopContext(ParseContext.literal);
		context.pop(); // literal
	}

	/**
	 * Parsing of a condition, where each argument must be a constant
	 * (i.e. not a variable). Used in initial state.
	 */
	@Override
	public void enterAtomicNameFormula(AtomicNameFormulaContext ctx) {
		
		// Negation status
		boolean negated = (isTopContext(ParseContext.negation));
		// Predicate
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null)
			error("Predicate \"" + predicateName + "\" is undefined.");
		
		Condition condition = new Condition(predicate, negated);
		
		// Parse condition's arguments
		for (int childIdx = 2; childIdx+1 < ctx.getChildCount(); childIdx++) {
			String name = ctx.children.get(childIdx).getText().toLowerCase();
			Type type = null;
			
			// Look up the type of this object
			for (Argument c : constants) {
				if (c.getName().equalsIgnoreCase(name)) {
					type = c.getType();
				}
			}
			
			if (type == null) {
				error("The type of an argument in the goal definition "
						+ "could not be inferred.");
			}
			
			// Add argument to condition
			condition.addArgument(new Argument(name, type));
		}
		
		checkConsistency(condition);
		initialState.add(condition);
	}
	
	
	
	// Goal definition (uses the AtomicTermFormula sub-structure
	// further up).
	
	@Override
	public void enterGoal(GoalContext ctx) {
		
		context.push(ParseContext.goalDef);
		goals = new ArrayList<>();
		quantifiedGoals = new ArrayList<>();
	}
	
	@Override
	public void exitGoal(GoalContext ctx) {
		
		assertTopContext(ParseContext.goalDef);
		context.pop();
	}
	
	@Override
	public void enterMetricSpec(MetricSpecContext ctx) {
		
		if (hasActionCosts) {
			
			if (!ctx.children.get(2).getText().equals("minimize") 
					|| !ctx.children.get(3).getText().equals("(total-cost)")) {
				error("Invalid metric specification. (Expected \"minimize (total-cost)\".)");
			}
			
		} else {
			error("A metric is specified, but no action cost "
					+ "function has been defined.");
		}
	}
	
	
	
	/* END OF PARSING LISTENER METHODS */
	
	
	
	/**
	 * Do basic predicate arity and type checks for a given condition.
	 */
	private void checkConsistency(Condition cond) {
		
		Predicate p = cond.getPredicate();
		if (p == null) {
			error("The predicate of a condition is not properly defined.");
		}
		if (p.getNumArgs() != cond.getNumArgs()) {
			error("The arity of condition \"" + cond.toString() 
			+ "\"does not fit the definition of its predicate.");
		}
		for (int argIdx = 0; argIdx < p.getNumArgs(); argIdx++) {
			Argument arg = cond.getArguments().get(argIdx);
			if (!isArgumentOfType(arg, p.getArgumentTypes().get(argIdx))) {
				error("The type \"" + arg.getType() + "\" of argument \"" + arg.getName() + "\""
						+ " does not fit the surrounding predicate \"" + p.toString() + "\".");
			}
		}
	}
	
	/**
	 * For a given quantification expression, sets the correct types
	 * of the arguments of the provided conditions.
	 */
	private void inferQuantifiedTypes(Quantification q, Condition... conditions) {
		
		// Variables which are being quantified
		List<Argument> variables = q.getVariables();
		
		// For each provided condition
		for (Condition cond : conditions) {
			// For each of its arguments
			for (int argIdx = 0; argIdx < cond.getArguments().size(); argIdx++) {
				
				// Get the argument and the type it *should* have
				// according to the enclosing predicate
				Argument arg = cond.getArguments().get(argIdx);
				Type argType = cond.getPredicate().getArgumentTypes().get(argIdx);
				
				// Find the right quantified variable (name matching)
				for (Argument quantifiedVar : variables) {
					if (arg.getName().equalsIgnoreCase(quantifiedVar.getName())) {
						
						// Does the argument have its type still unassigned?
						if (arg.getType().getName().equals("quantified")) {
							
							// Set the type which the enclosing predicate wants
							arg.setType(argType);
							if (quantifiedVar.getType().getName().equals("quantified")) {
								// Update the type for the quantified variable itself, too
								quantifiedVar.setType(argType);								
							} else if (!isArgumentOfType(quantifiedVar, argType)) {
								// Q. variable already has a type, and it is illegal
								// regarding the type the argument should have
								error("No valid type inference is possible for the "
										+ "quantified variable \"" + quantifiedVar + "\".");
							} 
							
						} else if (!isArgumentOfType(arg, argType)) {
							error("The quantified variable " + quantifiedVar 
									+ " has the wrong type \"" + quantifiedVar.getType() 
									+ "\" as an argument of type \"" + arg.getType() + "\".");
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks if the provided argument is of the provided type (i.e.
	 * the provided type may also be a supertype of the argument's type).
	 */
	private boolean isArgumentOfType(Argument arg, Type type) {
		
		// Does the argument have this exact type?
		Type argType = arg.getType();
		if (argType.equals(type)) {
			return true;
		
		} else {
			// Expand subtypes of the provided type
			Stack<String> subtypes = new Stack<>();
			for (String subtypeName : type.getSubtypes()) {
				subtypes.push(subtypeName);
			}
			
			// Traversal of subtype tree
			while (!subtypes.isEmpty()) {
				
				// Check subtype
				String subtypeName = subtypes.pop();
				Type subtype = types.get(subtypeName);
				if (subtype.equals(argType)) {
					return true;
				}
				
				// Expand subtypes
				for (String subsubtypeName : subtype.getSubtypes()) {
					subtypes.push(subsubtypeName);
				}
			}
		}
		
		// No fitting subtype identified
		return false;
	}
	
	private boolean isTopContext(ParseContext context) {
		return this.context.peek() == context;
	}
	
	private boolean isInContext(ParseContext context) {
		return this.context.contains(context);
	}
	
	private boolean isContextHigherThan(ParseContext higher, ParseContext lower) {
		boolean reachedHigherEl = false;
		for (int i = context.size()-1; i >= 0; i--) {
			ParseContext ctx = context.get(i);
			if (ctx == higher)
				reachedHigherEl = true;
			if (ctx == lower)
				return reachedHigherEl;
		}
		return false;
	}
	
	private void assertTopContext(ParseContext context) {
		if (context != this.context.peek()) {
			new Exception("Context mismatch. Expected: " + context 
					+ ", actual: " + this.context.peek()).printStackTrace();
			error("The parser reached an illegal internal state. This might be a bug.");
		}
	}
	
	/**
	 * Adds the provided condition to the correct object
	 * depending on the current parsing context 
	 * (precondition, effect, goal, ...).
	 */
	private void addToCurrentObject(Condition cond) {
		
		if (isInContext(ParseContext.actionDef)) {
			if (isInContext(ParseContext.quantification)) {
				
				if (isInContext(ParseContext.actionCondEff) 
						&& isContextHigherThan(ParseContext.actionCondEff, ParseContext.quantification)) {
					
					// Conditional effect inside a quantification
					if (isInContext(ParseContext.actionCondEffPre)) {
						List<AbstractCondition> conds = currentQuantification().getConditions();
						ConsequentialCondition c = (ConsequentialCondition) conds.get(conds.size()-1);
						c.addPrerequisite(cond);
					} else if (isInContext(ParseContext.actionCondEffCons)) {
						List<AbstractCondition> conds = currentQuantification().getConditions();
						ConsequentialCondition c = (ConsequentialCondition) conds.get(conds.size()-1);
						c.addConsequence(cond);
					}
				} else {					
					currentQuantification().addCondition(cond);
				}
				
			} else if (isInContext(ParseContext.actionCondEffPre)) {
				currentConditionalEffect().addPrerequisite(cond);
			} else if (isInContext(ParseContext.actionCondEffCons)) {
				currentConditionalEffect().addConsequence(cond);
			} else if (isInContext(ParseContext.actionEff)) {
				currentOperator().addEffect(cond);
			} else if (isInContext(ParseContext.actionPre)) {						
				currentOperator().addPrecondition(cond);
			}
		} else if (isInContext(ParseContext.goalDef)) {
			if (isInContext(ParseContext.quantification)) {
				currentQuantification().addCondition(cond);
			} else {				
				goals.add(cond);
			}
		}
	}
	
	/**
	 * The operator currently being parsed.
	 */
	private Operator currentOperator() {
		return operators.get(operators.size()-1);
	}

	/**
	 * The quantification currently being parsed.
	 */
	private Quantification currentQuantification() {
		
		if (isInContext(ParseContext.actionCondEff) && 
				isContextHigherThan(ParseContext.quantification, ParseContext.actionCondEff)) {
			
			// Quantification inside a conditional effect
			ConsequentialCondition c = (ConsequentialCondition) currentOperator().getEffects()
					.get(currentOperator().getEffects().size()-1);
			if (isInContext(ParseContext.actionCondEffPre)) {
				return (Quantification) c.getPrerequisites().get(c.getPrerequisites().size()-1);
			}
			if (isInContext(ParseContext.actionCondEffCons)) {
				return (Quantification) c.getConsequences().get(c.getConsequences().size()-1);
			}
			
		} else if (isInContext(ParseContext.actionPre)) {			
			return (Quantification) currentOperator().getPreconditions().get(
					currentOperator().getPreconditions().size()-1);
		} else if (isInContext(ParseContext.actionEff)) {
			return (Quantification) currentOperator().getEffects().get(
					currentOperator().getEffects().size()-1);
		} else if (isInContext(ParseContext.goalDef)) {
			return quantifiedGoals.get(quantifiedGoals.size()-1);
		}
		return null;
	}
	
	/**
	 * The conditional effect currently being parsed.
	 */
	private ConsequentialCondition currentConditionalEffect() {
		
		List<AbstractCondition> effects = currentOperator().getEffects();					
		return (ConsequentialCondition) effects.get(effects.size()-1);
	}
	
	
	
	// Error handling
	
	@Override
	public void visitErrorNode(ErrorNode node) {
		error("Unrecognized or illegal token \"" + node.toString() + "\".");
	}	
	
	/**
	 * Prints the provided error message together with the name of the
	 * file currently being parsed and the exact position inside the file.
	 */
	private void error(String msg) {
		
		// Get position range inside the parsed text file
		int lineStart = parseContext.start.getLine();
		int columnStart = parseContext.start.getCharPositionInLine();
		int lineEnd = parseContext.stop.getLine();
		int columnEnd = parseContext.stop.getCharPositionInLine();
		
		// Print error and exit
		System.err.print("Parsing error in " + parsedFile 
				+ " at (line " + lineStart + ", col. " + columnStart 
				+ ") until (line " + lineEnd + ", col. " + columnEnd + "):\n" + msg);
		System.exit(1);
	}

}
