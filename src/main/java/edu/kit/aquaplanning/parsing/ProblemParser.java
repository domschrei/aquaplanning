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

import edu.kit.aquaplanning.model.PddlRequirement;
import edu.kit.aquaplanning.model.lifted.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Condition;
import edu.kit.aquaplanning.model.lifted.ConditionSet;
import edu.kit.aquaplanning.model.lifted.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.Implication;
import edu.kit.aquaplanning.model.lifted.Negation;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Quantification;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.AbstractCondition.ConditionType;
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
	private List<AbstractCondition> goals;
	private boolean hasActionCosts;
	
	private ParserRuleContext parseContext;
	/**
	 * All different contexts of the parser which may need to be remembered
	 */
	private enum ParseContext {
		typeDefs, constantDefs, predicateDefs, functionsDef, actionDef, 
		actionPre, actionEff, objectDefs, initialStateDef, goalDef;
	}
	private ParseContext context;
	private String parsedFile; // the file *currently* being parsed

	private Predicate predicate;
	private Type supertype;
	
	private Stack<AbstractCondition> conditionStack;
	
	/**
	 * Parses a pair of PDDL domain and problem files.
	 * Returns the parsed problem instance as an object.
	 * 
	 * @param domainFile
	 * @param problemFile
	 */
	public PlanningProblem parse(String domainFile, String problemFile) 
			throws FileNotFoundException, IOException {
				
		conditionStack = new Stack<>();

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
        System.out.println(types);
        System.out.println(constants);
        System.out.println(predicates);
        System.out.println(operators);
        System.out.println(initialState);
        System.out.println(goals);
        
        PlanningProblem problem = new PlanningProblem(domainName, problemName, 
        		types, constants, predicates, operators, 
        		initialState, goals, hasActionCosts);
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
			
			if (PddlRequirement.isSupported(requirement)) {				
				if (!requirements.contains(requirement.substring(1))) {
					requirements.add(requirement.substring(1));
				}
			} else {				
				error("Unsupported requirement: " + requirement + ".");
			}
		}
	}
	
	
	
	// Types definition (domain)
	
	@Override
	public void enterTypesDef(TypesDefContext ctx) {
		
		context = ParseContext.typeDefs;
		types = new HashMap<>();

		// Create a virtual supertype for general predicate "="
		supertype = new Type("_root_type");
		types.put("_root_type",  supertype);
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
					if (context == ParseContext.typeDefs
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
					
					if (context == ParseContext.typeDefs) {
						// Type definitions: set supertype
						type.addSubtypes(elements);
					
					} else if (context == ParseContext.constantDefs
							|| context == ParseContext.objectDefs) {
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
		
		context = ParseContext.constantDefs;
		constants = new ArrayList<>();
	}

	
	
	// Functions definition
	
	@Override
	public void enterFunctionsDef(FunctionsDefContext ctx) {
		
		context = ParseContext.functionsDef;
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

	
	
	// Predicates definition	
	
	@Override
	public void enterPredicatesDef(PredicatesDefContext ctx) {
		
		context = ParseContext.predicateDefs;
		predicates = new HashMap<>();
	}
	
	@Override
	public void enterAtomicFormulaSkeleton(AtomicFormulaSkeletonContext ctx) {
		
		if (context == ParseContext.predicateDefs) {
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
			
			if (context == ParseContext.predicateDefs) {	
				// Add type to current predicate
				predicate.addArgumentType(type);
				
			} else if (isInQuantification()) {
				// Add argument to quantification
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				Quantification q = currentQuantification();
				q.addVariable(new Argument(argName, type));

			} else if (context == ParseContext.actionDef) {
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
		
		if (isInQuantification()) {
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
		
		context = ParseContext.actionDef;
		if (operators == null)
			operators = new ArrayList<>();
		
		// Create new, empty operator
		String opName = ctx.children.get(2).getText().toLowerCase();
		Operator op = new Operator(opName);
		operators.add(op);
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
				context = ParseContext.actionEff;

			} else {
				
				// Parse preconditions now
				context = ParseContext.actionPre;
			}
			
		} else if (ctx.children.get(0).getText().equalsIgnoreCase(":effect")) {
			
			// Parse effects now (in the case that there are no preconds)
			context = ParseContext.actionEff;
		}
	}
	
	
	
	/**
	 * Parsing of a composition of conditions.
	 * Used in action preconditions, conditional effect prerequisites, and goals.
	 */
	@Override
	public void enterGoalDesc(GoalDescContext ctx) {
		
		if (ctx.children.size() == 1) {
			
			// Atomic formula
			// Will be processed as an atomicTermFormula
			
		} else {
			
			// Check the kind of the expression
			String expressionType = ctx.children.get(1).getText();
			AbstractCondition condition = null;
			
			if (expressionType.equalsIgnoreCase("not")) {

				condition = new Negation();
				
			} else if (expressionType.equalsIgnoreCase("and")) {
				
				condition = new ConditionSet(ConditionType.conjunction);
				
			} else if (expressionType.equalsIgnoreCase("or")) {
				
				condition = new ConditionSet(ConditionType.disjunction);
				
			} else if (expressionType.equalsIgnoreCase("imply")) {
					
				condition = new Implication();
					
			} else if (expressionType.equalsIgnoreCase("forall")) {

				// Universal quantification
				condition = new Quantification(Quantifier.universal);
			
			} else if (expressionType.equalsIgnoreCase("exists")) {
				
				// Existential quantification
				condition = new Quantification(Quantifier.existential);
				
			} else if (expressionType.equals("=")) {
				// Equality
				
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
				Condition cond = new Condition(p);
				cond.addArgument(new Argument(ctx.children.get(2).getText(), supertype));
				cond.addArgument(new Argument(ctx.children.get(3).getText(), supertype));
				condition = cond;
			}

			enterCondition(condition);
		}
	}

	@Override
	public void exitGoalDesc(GoalDescContext ctx) {
		
		if (ctx.children.size() > 1) {
			exitCondition();
		}
	}

	
	
	/**
	 * Parsing of a condition, where each argument may be either
	 * a variable or a constant.
	 * Used in preconditions, effects, and goals.
	 */
	@Override
	public void enterAtomicTermFormula(AtomicTermFormulaContext ctx) {
		
		// Create condition
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null) {
			error("Predicate \"" + predicateName + "\" is undefined.");
		}
		Condition cond = new Condition(predicate);
		
		// Add arguments of condition
		for (int childIdx = 2; childIdx+1 < ctx.children.size(); childIdx++) {
			
			// Name and type of the argument
			String termStr = ctx.children.get(childIdx).getText().toLowerCase();
			Type type = null;
			
			// Is this argument a variable or a constant?
			if (termStr.charAt(0) == '?') {
				
				// -- variable
				
				if (isInQuantification()) {
					// Quantification
					
					// Is the variable bound to the quantifier?
					Quantification q = currentQuantification();
					for (Argument quantifiedVar : q.getVariables()) {
						if (termStr.equalsIgnoreCase(quantifiedVar.getName())) {								
							// -- yes: quantified variable (type will be inferred later)
							type = new Type("quantified");
						}
					} // -- if not: type == null
				
				} else if (context == ParseContext.goalDef) {
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
		
		if (isInQuantification()) {
			// We need to infer the types of bound variables
			Quantification q = currentQuantification();
			inferQuantifiedTypes(q, cond);
		}
		enterCondition(cond);
		exitCondition();
		
		// Basic checks for correct typing and predicate use
		checkConsistency(cond);
	}		
	
	
	
	// Effect definition
	
	@Override
	public void enterEffect(EffectContext ctx) {
		
		// Add conjunction ("and") inside effects
		enterCondition(new ConditionSet(ConditionType.conjunction));
	}

	@Override
	public void exitEffect(EffectContext ctx) {
		
		// Conjunction left in the context
		exitCondition();
	}

	@Override
	public void enterCEffect(CEffectContext ctx) {
		
		if (ctx.children.size() == 5) {
			// Conditional effect:
			// ( when <goalDesc> <condEffect> )
			
			// Create new cond. effect object
			ConsequentialCondition condition = new ConsequentialCondition();
			enterCondition(condition);
		
		} else if (ctx.children.size() == 7) {
			// Quantification as one of the operator effects
			
			// Create new quantification object
			Quantification q = new Quantification(Quantifier.universal);
			enterCondition(q);
			
		} else if (ctx.children.size() != 1) {
			error("An effect specification is unsupported: " + ctx.getText());
		}
	}
	
	@Override
	public void exitCEffect(CEffectContext ctx) {
		
		// Cond. effect or quantification
		if (ctx.children.size() > 1)
			exitCondition();
	}
	
	@Override
	public void enterPEffect(PEffectContext ctx) {
		
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
					if (!operation.equalsIgnoreCase("increase")) {
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
			
		} else {
			
			// "not"
			Negation neg = new Negation();
			enterCondition(neg);
		}
	}

	@Override
	public void exitPEffect(PEffectContext ctx) {
		
		// Negation
		if (ctx.children.size() > 1 && ctx.children.size() <= 4) {
			exitCondition();
		}
	}
	
	@Override
	public void enterCondEffect(CondEffectContext ctx) {
		
		if (ctx.children.size() > 1 
				&& ctx.children.get(1).getText().toLowerCase().equals("and")) {
			
			ConditionSet and = new ConditionSet(ConditionType.conjunction);
			enterCondition(and);
		}
	}
	
	@Override
	public void exitCondEffect(CondEffectContext ctx) {
		
		if (ctx.children.size() > 1 
				&& ctx.children.get(1).getText().toLowerCase().equals("and")) {
			
			exitCondition();
		}
	}
	
	
	
	// Objects definition of problem file
	// (just gets merged into the domain's constants)
	
	@Override
	public void enterObjectDecl(ObjectDeclContext ctx) {
		
		context = ParseContext.objectDefs;
		if (constants == null)
			constants = new ArrayList<>();
	}
	
	
	
	// Initial state definition
	
	@Override
	public void enterInit(InitContext ctx) {
		
		context = ParseContext.initialStateDef;
		initialState = new ArrayList<>();
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
	public void exitNameLiteral(NameLiteralContext ctx) {
		
		// "not" expression
		if (ctx.children.size() > 1) {
			initialState.get(initialState.size()-1).setNegated(true);
		}
	}

	/**
	 * Parsing of a condition, where each argument must be a constant
	 * (i.e. not a variable). Used in initial state.
	 */
	@Override
	public void enterAtomicNameFormula(AtomicNameFormulaContext ctx) {
		
		// Predicate
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null)
			error("Predicate \"" + predicateName + "\" is undefined.");
		
		Condition condition = new Condition(predicate);
		
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
		
		context = ParseContext.goalDef;
		goals = new ArrayList<>();
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
	
	private void enterCondition(AbstractCondition childCond) {
		
		AbstractCondition condition = null;
		if (!conditionStack.isEmpty()) 
			condition = conditionStack.peek();
		
		if (condition != null) {
			switch (condition.getConditionType()) {
			case atomic:
				break;
			case conjunction:
			case disjunction:
				((ConditionSet) condition).add(childCond);
				break;
			case implication:
				Implication i = (Implication) condition;
				if (i.getIfCondition() == null) {
					i.setIfCondition(childCond);
				} else {
					i.setThenCondition(childCond);
				}
				break;
			case consequential:
				ConsequentialCondition c = (ConsequentialCondition) condition;
				if (c.getPrerequisite() == null) {
					c.setPrerequisite(childCond);
				} else if (c.getConsequence() == null) {
					c.setConsequence(childCond);
				} else {
					error(c + " already finished!");
				}
				break;
			case negation:
				((Negation) condition).setChildCondition(childCond);
				break;
			case quantification:
				((Quantification) condition).setCondition(childCond);
				break;
			}
		}
		
		conditionStack.push(childCond);
	}
	
	private void exitCondition() {
		
		AbstractCondition condition = conditionStack.pop();
		if (conditionStack.isEmpty())
			addToCurrentObject(condition);
	}
	
	/**
	 * Adds the provided condition to the correct object
	 * depending on the current parsing context 
	 * (precondition, effect, goal, ...).
	 */
	private void addToCurrentObject(AbstractCondition cond) {
		
		if (context == ParseContext.actionPre || context == ParseContext.actionEff) {
			Operator op = currentOperator();
			if (op.getPrecondition() == null) {
				op.setPrecondition(cond);
			} else {						
				op.setEffect(cond);
			}
		} else if (context == ParseContext.goalDef) {
			goals.add(cond);
		} else if (context == ParseContext.initialStateDef) {
			initialState.add((Condition) cond);
		}
	}
	
	/**
	 * The operator currently being parsed.
	 */
	private Operator currentOperator() {
		return operators.get(operators.size()-1);
	}

	private boolean isInQuantification() {
		return currentQuantification() != null;
	}
	
	/**
	 * The quantification currently being parsed.
	 */
	private Quantification currentQuantification() {
		
		AbstractCondition condition = null;
		int position = conditionStack.size()-1;
		while (position >= 0) {
			condition = conditionStack.get(position);
			if (condition.getConditionType() == ConditionType.quantification) {
				return (Quantification) condition;
			}
			position--;
		}
		return null;
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
