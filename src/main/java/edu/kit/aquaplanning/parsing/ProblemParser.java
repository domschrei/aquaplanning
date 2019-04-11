package edu.kit.aquaplanning.parsing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.kit.aquaplanning.model.PddlRequirement;
import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Axiom;
import edu.kit.aquaplanning.model.lifted.Function;
import edu.kit.aquaplanning.model.lifted.NumericExpression;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.Predicate;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.NumericExpression.TermType;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.ConsequentialCondition;
import edu.kit.aquaplanning.model.lifted.condition.Implication;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.condition.NumericCondition;
import edu.kit.aquaplanning.model.lifted.condition.NumericEffect;
import edu.kit.aquaplanning.model.lifted.condition.Quantification;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.condition.Quantification.Quantifier;
import edu.kit.aquaplanning.model.lifted.htn.Constraint;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;
import edu.kit.aquaplanning.model.lifted.htn.Constraint.ConstraintType;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ActionDefBodyContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ActionDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.AtomicFormulaSkeletonContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.AtomicFunctionSkeletonContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.AtomicNameFormulaContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.AtomicTermFormulaContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.CEffectContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.CondEffectContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ConstantsDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ConstraintContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.DerivedDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.DomainNameContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.EffectContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.FCompContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.FExpContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.FHeadContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.FunctionListContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.FunctionsDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.GoalContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.GoalDescContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.InitContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.InitElContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.InitTaskNetworkContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.MethodDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.MetricSpecContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.NameLiteralContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ObjectDeclContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.PEffectContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.PddlDocContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.PredicatesDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.ProblemDeclContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.RequireDefContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.SingleTypeNameListContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.SingleTypeVarListContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.TaggedTaskContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.TaskContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.TypedNameListContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.TypedVariableListContext;
import edu.kit.aquaplanning.parsing.PddlHtnParser.TypesDefContext;

@SuppressWarnings("deprecation")
public class ProblemParser extends PddlHtnBaseListener {
	
	// Fields to populate during the parsing process: 
	// Domain
	private String domainName;
	private String problemName;
	private List<String> requirements;
	private Map<String, Type> types;
	private List<Argument> constants; // both from domain and problem
	private Map<String, Predicate> predicates;
	private Map<String, Axiom> derivedPredicates;
	private Map<String, Function> functions;
	private List<Operator> operators;
	private Map<String, List<Type>> tasks;
	private List<Method> methods;
	private Set<String> methodNames;
	// Problem
	private List<Condition> initialState;
	private Map<Function, Float> initialFunctionValues;
	private Method initialTaskNetwork;
	private List<AbstractCondition> goals;
	private boolean hasActionCosts;
	
	private ParserRuleContext parseContext;
	/**
	 * All different contexts of the parser which may need to be remembered
	 */
	private enum ParseContext {
		typeDefs, constantDefs, predicateDefs, derivedPredicateDef, 
		functionsDef, actionDef, actionPre, actionEff, methodDef,
		objectDefs, initialStateDef, initialTaskNetworkDef, goalDef;
	}
	private ParseContext context;
	private String parsedFile; // the file *currently* being parsed

	private Predicate predicate;
	private Type supertype;

	private Stack<AbstractCondition> conditionStack;
	private Stack<NumericExpression> expressionStack;
	private Function function;
	
	/**
	 * Parses a pair of PDDL domain and problem files.
	 * Returns the parsed problem instance as an object.
	 * 
	 * @param domainFile
	 * @param problemFile
	 */
	public PlanningProblem parse(String domainFile, String problemFile) 
			throws FileNotFoundException, IOException {
		
		// Initialize basic data structures
		conditionStack = new Stack<>();
		expressionStack = new Stack<>();
		constants = new ArrayList<>();
		predicates = new HashMap<>();
		derivedPredicates = new HashMap<>();
		functions = new HashMap<>();
		operators = new ArrayList<>();
		methods = new ArrayList<>();
		methodNames = new HashSet<>();
		tasks = new HashMap<>();

		// Initialize typing
		types = new HashMap<>();
		supertype = new Type("_root_type"); // virtual supertype
		types.put("_root_type",  supertype);
		
		// Get domain
        ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(domainFile));
        PddlHtnLexer lexer = new PddlHtnLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PddlHtnParser parser = new PddlHtnParser(tokens);
        parser.setBuildParseTree(true);
        PddlDocContext ctx = parser.pddlDoc();
        
        // Parse domain
        parsedFile = domainFile;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, ctx);
        
        // Get problem
        in = new ANTLRInputStream(new FileInputStream(problemFile));
        lexer = new PddlHtnLexer(in);
        tokens = new CommonTokenStream(lexer);
        parser = new PddlHtnParser(tokens);
        parser.setBuildParseTree(true);
        ctx = parser.pddlDoc();
        
        // Parse problem
        parsedFile = problemFile;
        walker = new ParseTreeWalker();
        walker.walk(this, ctx);
        
        // Create object to return
        PlanningProblem problem;
        if (initialTaskNetwork == null) {
        	
        	problem = new PlanningProblem(domainName, problemName, 
        			types, constants, predicates, derivedPredicates, functions, 
        			operators, initialState, initialFunctionValues, goals, hasActionCosts);
        } else {
        	
        	problem = new HtnPlanningProblem(domainName, problemName, 
        			types, constants, predicates, derivedPredicates, functions, 
        			operators, initialState, initialFunctionValues, goals, hasActionCosts, 
        			tasks, methods, initialTaskNetwork);
        }
        
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
		for (int childIdx = 3; childIdx+1 < ctx.getChildCount(); childIdx++) {
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
	}

	@Override
	public void enterTypedNameList(TypedNameListContext ctx) {
		
		if (ctx.children == null) {
			// Empty definition - just skip it
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
				
				String name = ctx.children.get(childIdx).getText().toLowerCase();
				
				if (context == ParseContext.typeDefs) {
					
					// Primitive type: add, if not already contained
					if (!types.containsKey(name)) {
						types.put(name, new Type(name));
						supertype.addSubtype(name);
					} else {
						error("Type \"" + name + "\" is defined multiple times.");
					}
					
				} else if (context == ParseContext.constantDefs 
						|| context == ParseContext.objectDefs) {
					
					// An object (!) is declared, without an explicit type
					Argument constant = new Argument(name, supertype);
					constants.add(constant);
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
							Argument constant = new Argument(constantName, type);
							if (!constants.contains(constant)) {								
								constants.add(constant);
							}
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
	}

	
	
	// Functions definition
	
	@Override
	public void enterFunctionsDef(FunctionsDefContext ctx) {
		
		context = ParseContext.functionsDef;
	}
	
	@Override
	public void enterFunctionList(FunctionListContext ctx) {
		
		if (ctx.getText().toLowerCase().contains("(total-cost)")) {
			// Metric function detected
			hasActionCosts = true;
		}
	}
	
	@Override
	public void enterAtomicFunctionSkeleton(AtomicFunctionSkeletonContext ctx) {
		
		function = new Function(ctx.children.get(1).getText().toLowerCase());
		functions.put(function.getName(), function);
	}

	
	
	// Predicates definition	
	
	@Override
	public void enterPredicatesDef(PredicatesDefContext ctx) {
		
		context = ParseContext.predicateDefs;
	}
	
	@Override
	public void enterAtomicFormulaSkeleton(AtomicFormulaSkeletonContext ctx) {
		
		// A new predicate is being defined; create the object
		String predName = ctx.children.get(1).getText().toLowerCase();
		if (context == ParseContext.predicateDefs) {
			predicate = new Predicate(predName);
		} else if (context == ParseContext.derivedPredicateDef) {
			predicate = new Predicate(predName, true);
			if (!derivedPredicates.containsKey(predicate.getName())) {
				derivedPredicates.put(predicate.getName(), new Axiom(predicate));
			}
		}
		predicates.put(predName, predicate);
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
			
			if (isInQuantification()) {
				// Add argument to quantification
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				Quantification q = currentQuantification();
				q.addVariable(new Argument(argName, type));

			} else if (context == ParseContext.predicateDefs) {	
				// Add type to current predicate
				predicate.addArgumentType(type);
				
			} else if (context == ParseContext.derivedPredicateDef) {	
				// Add type to current predicate
				predicate.addArgumentType(type);
				// Add type to derived predicate argument list
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				derivedPredicates.get(predicate.getName()).addArgument(new Argument(argName, type));
				
			} else if (context == ParseContext.actionDef) {
				// Add argument to current operator
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				currentOperator().addArgument(new Argument(argName, type));
				
			} else if (context == ParseContext.methodDef) {
				// Add argument to current operator
				String argName = ctx.children.get(childIdx).getText().toLowerCase();
				currentMethod().addExplicitArgument(new Argument(argName, type));
				
			} else if (context == ParseContext.functionsDef) {
				// Add argument to function
				function.addArgumentType(type);
			}
		}
	}
	
	/**
	 * Parsing of structures of the form <code>variable*</code> or 
	 * <code>singleTypeVarList* variable*</code>.
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
					String varName = ctx.children.get(childIdx).getText().toLowerCase();
					Type type = new Type("quantified"); // to be added later
					Argument arg = new Argument(varName, type);
					currentQuantification().addVariable(arg);
				}
			}
			
		} else if (context == ParseContext.predicateDefs || context == ParseContext.derivedPredicateDef) {	
			// Predicate definition
			
			// Read variables from right to left until a SingleTypeVarList is hit
			if (ctx.children == null) {
				// Empty variable list
				return;
			} 		
			
			List<Argument> dpArgs = new ArrayList<>();
			for (int childIdx = ctx.children.size()-1; childIdx >= 0; childIdx--) {
				if (ctx.children.get(childIdx).getChildCount() > 1) {
					// Typed definition
					break;
				}
				// We don't know the type of this argument, 
				// so we assume the supertype
				if (context == ParseContext.predicateDefs) {						
					predicate.addArgumentType(supertype);
				} else if (context == ParseContext.derivedPredicateDef) {
					String varName = ctx.children.get(childIdx).getText().toLowerCase();
					dpArgs.add(0, new Argument(varName, supertype));
				}
			}
			// Add arguments to derived predicate in correct order
			for (Argument arg : dpArgs) derivedPredicates.get(predicate.getName()).addArgument(arg);
			
		} else if (context == ParseContext.actionDef || context == ParseContext.methodDef) {
			// Action or method parameter definition
			
			if (ctx.children == null) {
				return;
			}
			
			// Read variables from left to right until a SingleTypeVarList is hit
			List<Argument> argsBackwards = new ArrayList<>();
			for (int childIdx = ctx.children.size()-1; childIdx >= 0; childIdx--) {
				if (ctx.children.get(childIdx).getChildCount() > 1) {
					// A typed definition begins here
					break;
				} else {
					// Variable (general supertype)
					String varName = ctx.children.get(childIdx).getText().toLowerCase();
					Type type = supertype;
					Argument arg = new Argument(varName, type);
					argsBackwards.add(arg);
				}
			}
			// Now append the arguments to the current object in the correct order
			for (int i = argsBackwards.size()-1; i >= 0; i--) {
				Argument arg = argsBackwards.get(i);
				if (context == ParseContext.methodDef) {
					currentMethod().addExplicitArgument(arg);
				} else {						
					currentOperator().addArgument(arg);
				}
			}
		}
	}
	
	
	
	// Derived predicate definitions
	
	@Override
	public void enterDerivedDef(DerivedDefContext ctx) {
		// '(' ':derived' atomicFormulaSkeleton goalDesc ')'
		
		context = ParseContext.derivedPredicateDef;
	}
		
	
	
	// Action definitions
	
	@Override
	public void enterActionDef(ActionDefContext ctx) {
		
		context = ParseContext.actionDef;
		
		// Create new, empty operator
		String opName = ctx.children.get(3).getText().toLowerCase();
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
		if (ctx.children.get(1).getText().equalsIgnoreCase("precondition")) {
			
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
			
		} else if (ctx.children.get(1).getText().equalsIgnoreCase("effect")) {
			
			// Parse effects now (in the case that there are no preconds)
			context = ParseContext.actionEff;
		}
	}
	
	@Override
	public void exitActionDef(ActionDefContext ctx) {
		
		Operator op = currentOperator();
		if (op.getPrecondition() == null) {
			op.setPrecondition(new ConditionSet(ConditionType.conjunction));
		}
		if (op.getEffect() == null) {
			op.setEffect(new ConditionSet(ConditionType.conjunction));
		}
		
		List<Type> opTypes = new ArrayList<>();
		opTypes.addAll(op.getArgumentTypes());
		tasks.put(op.getName(), opTypes);
	}
	
	
	
	@Override
	public void enterMethodDef(MethodDefContext ctx) {
		this.context = ParseContext.methodDef;
		String methodName = ctx.children.get(3).getText().toLowerCase();
		methodNames.add(methodName);
		Method method = new Method(methodName);
		methods.add(method);
	}
	
	@Override
	public void enterTask(TaskContext ctx) {
		
		String name = ctx.children.get(1).getText().toLowerCase();
		Task task = new Task(name);
		Method method = null;
		if (context == ParseContext.methodDef) {
			method = currentMethod();
		} else if (context == ParseContext.initialTaskNetworkDef) {
			method = initialTaskNetwork;
		}
		
		if (!tasks.containsKey(name)) {
			tasks.put(name, new ArrayList<>());
		}
		List<Type> taskTypes = tasks.get(name);
		
		for (int i = 2; i < ctx.children.size()-1; i++) {
			
			if (i-2 >= taskTypes.size()) {
				taskTypes.add(new Type("_IMPLICIT"));
			}
			Type type = taskTypes.get(i-2);

			String argName = ctx.children.get(i).getText().toLowerCase();
			if (argName.charAt(0) == '?') {
				
				if (!method.hasArgument(argName)) {
					method.addImplicitArgument(new Argument(argName, type));
				} else {					
					Type typeFromMethod = method.getTypeOfArgument(argName);
					if (type.getName().equals("_IMPLICIT"))
						type = typeFromMethod;
				}
				
			} else {
				type = getTypeOfConstant(argName);
			}
			task.addArgument(new Argument(argName, type));
			taskTypes.set(i-2, type);
		}
		method.addSubtask(task);
	}
	
	@Override
	public void exitTaggedTask(TaggedTaskContext ctx) {
		currentMethod().tagLastSubtask(ctx.children.get(2).getText().toLowerCase());			
	}
	
	@Override
	public void enterConstraint(ConstraintContext ctx) {
		
		Method method = currentMethod();
		String typeString = ctx.children.get(1).getText().toLowerCase();
		ConstraintType type;
		
		Constraint constraint;
		if (typeString.equals("between")) {
			type = ConstraintType.between;
			constraint = new Constraint(
					ctx.children.get(3).getText().toLowerCase(), 
					ctx.children.get(4).getText().toLowerCase());
		} else {				
			if (typeString.equals("before")) {
				type = ConstraintType.before;
			} else /*if (typeString.equals("after"))*/ {
				type = ConstraintType.after;
			}
			constraint = new Constraint(type,
					ctx.children.get(3).getText().toLowerCase());
		}
		method.addConstraint(constraint);
	}
	
	@Override
	public void exitConstraint(ConstraintContext ctx) {
		// Simplify constraint
		List<Constraint> constraints = currentMethod().getConstraints();
		AbstractCondition c = constraints.get(constraints.size()-1).getCondition();
		constraints.get(constraints.size()-1).setCondition(c.getDNF());
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
	public void enterFComp(FCompContext ctx) {
		// '(' binaryComp fExp fExp ')'
		NumericCondition cond = new NumericCondition(ctx.children.get(1).getText());
		enterCondition(cond);
	}
	
	@Override
	public void exitFComp(FCompContext ctx) {
		exitCondition();
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
		Condition cond = null;
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null) {
			error("Predicate \"" + predicateName + "\" is undefined.");	
		}
		cond = new Condition(predicate);
		
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
				}

				if (type == null && context == ParseContext.derivedPredicateDef) {
					
					// Derived predicate definition
					for (Argument arg : derivedPredicates.get(this.predicate.getName()).getArguments()) {
						if (termStr.equalsIgnoreCase(arg.getName())) {
							type = arg.getType();
							break;
						}
					}
				}
				
				if (type == null) {
					if (context == ParseContext.methodDef) {
						Method method = currentMethod();
						if (method.hasArgument(termStr)) {					
							type = method.getTypeOfArgument(termStr);
							if (type.getName().equals("_IMPLICIT")) {
								// Try to infer type
								type = predicate.getArgumentTypes().get(childIdx-2);
								method.updateArgumentType(termStr, type);
							}
						} else {
							type = predicate.getArgumentTypes().get(childIdx-2);
							method.addImplicitArgument(new Argument(termStr, type));
						}
					} else {						
						// Check if the variable occurs in the operator definition,
						// and which type it has
						for (Argument arg : operators.get(operators.size()-1).getArguments()) {
							if (arg.getName().equalsIgnoreCase(termStr)) {
								type = arg.getType();
							}
						}
					}
				}
				
			} else {
				
				// -- constant
				type = getTypeOfConstant(termStr, predicate, childIdx-2);
			}
			
			if (type == null) {
				error("No fitting type could be found for argument \"" + termStr + "\".");
			}
			
			// Add variable/constant as an argument
			cond.addArgument(new Argument(termStr, type));
		}
		
		if (isInQuantification()) {
			// We need to infer the types of bound variables
			Quantification q = currentQuantification();
			inferQuantifiedTypes(q, cond);
		}
		
		// Normal predicate: atomic condition
		enterCondition(cond);
		exitCondition();
		
		// Basic checks for correct typing and predicate use
		checkConsistency(cond);
	}	
	
	@Override
	public void enterFExp(FExpContext ctx) {
		
		// NUMBER | '(' binaryOp fExp fExp2 ')' | '(' '-' fExp ')' | fHead
		NumericExpression exp = null;
		
		if (ctx.children.get(0).getText().equals("(")) {
			if (ctx.children.get(1).getText().equals("-") && ctx.children.size() == 4) {
				// Case 3 (negative fExp)
				exp = new NumericExpression(TermType.negation);
			} else {
				// Case 2 (operation)
				TermType type = null;
				switch (ctx.children.get(1).getText()) {
				case "+":
					type = TermType.addition; break;
				case "-":
					type = TermType.subtraction; break;
				case "*":
					type = TermType.multiplication; break;
				case "/":
					type = TermType.division; break;
				}
				exp = new NumericExpression(type);
			}
		} else if (ctx.children.get(0).getText().matches("-?\\d+(\\.\\d+)?")) {
			// Case 1 (number)
			exp = new NumericExpression(Float.parseFloat(ctx.children.get(0).getText()));
		} else {
			// Case 4 (function)
			exp = new NumericExpression(TermType.function);
		}
		
		enterNumericExpression(exp);
	}
	
	@Override
	public void enterFHead(FHeadContext ctx) {
		
		// '(' functionSymbol term* ')' | functionSymbol
		
		String functionName;
		if (ctx.children.size() == 1) {
			functionName = ctx.children.get(0).getText().toLowerCase();
		} else {
			functionName = ctx.children.get(1).getText().toLowerCase();
		}
		function = functions.get(functionName).copy();
		if (function == null) {
			error("Function \"" + functionName + "\" has not been defined.");
		}
		for (int i = 2; i+1 < ctx.children.size(); i++) {
			String arg = ctx.children.get(i).getText().toLowerCase();
			Type type = function.getArgumentTypes().get(i-2);
			function.addArgument(new Argument(arg, type));
		}
		
		if (!expressionStack.isEmpty()) {
			// Function is part of a numeric expression
			expressionStack.peek().setFunction(function);
		} else if (!conditionStack.isEmpty()) {
			// Function is being updated as an effect
			NumericEffect eff = (NumericEffect) conditionStack.peek();
			eff.setFunction(function);
		} // else: function will be used elsewhere
	}
	
	@Override
	public void exitFExp(FExpContext ctx) {
		
		exitNumericExpression();
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
			// Function update
			
			String operation = ctx.children.get(1).getText();
			NumericEffect.Type type = null;
			switch (operation) {
			case "assign":
				type = NumericEffect.Type.assign; break;
			case "increase":
				type = NumericEffect.Type.increase; break;
			case "decrease":
				type = NumericEffect.Type.decrease; break;
			case "scale-up":
				type = NumericEffect.Type.scaleUp; break;
			case "scale-down":
				type = NumericEffect.Type.scaleDown; break;
			}
			NumericEffect eff = new NumericEffect(type);
			enterCondition(eff);
			
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
		
		// Negation, Function update
		if (ctx.children.size() > 1) {
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
	}
	
	
	
	// Initial state definition
	
	@Override
	public void enterInit(InitContext ctx) {
		
		context = ParseContext.initialStateDef;
		initialState = new ArrayList<>();
		initialFunctionValues = new HashMap<>();
	}

	@Override
	public void exitInitEl(InitElContext ctx) {
		
		if (ctx.children.size() == 5) {
			// '(' '=' fHead NUMBER ')'
			initialFunctionValues.put(function, Float.parseFloat(ctx.children.get(3).getText()));			
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
		Condition condition = null;
		String predicateName = ctx.children.get(1).getText().toLowerCase();
		Predicate predicate = predicates.get(predicateName);
		if (predicate == null) {
			error("Predicate \"" + predicateName + "\" is undefined.");
		}
		condition = new Condition(predicate);
		
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
			
			if (!ctx.children.get(3).getText().equalsIgnoreCase("minimize") 
					|| !ctx.children.get(4).getText().equalsIgnoreCase("(total-cost)")) {
				error("Invalid metric specification. (Expected \"minimize (total-cost)\".)");
			}
			
		} else {
			error("A metric is specified, but no action cost "
					+ "function has been defined.");
		}
	}
	
	@Override
	public void enterInitTaskNetwork(InitTaskNetworkContext ctx) {
		context = ParseContext.initialTaskNetworkDef;
		initialTaskNetwork = new Method("_initTask");
		
	}
	
	
	
	/* END OF PARSING LISTENER METHODS */
	

	
	/**
	 * Do basic predicate arity and type checks for a given condition.
	 */
	private void checkConsistency(Condition cond) {
		
		Predicate p = cond.getPredicate();
		if (p == null) {
			error("The predicate of condition " + cond + " is not properly defined.");
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
			case numericPrecondition:
			case numericEffect:
			case derived:
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
	
	private void enterNumericExpression(NumericExpression exp) {
		
		NumericExpression parent = null;
		if (!expressionStack.isEmpty()) {
			parent = expressionStack.peek();
			parent.add(exp);
		} else {
			AbstractCondition cond = conditionStack.peek();
			if (cond.getConditionType() == ConditionType.numericPrecondition) {				
				NumericCondition pre = (NumericCondition) cond;
				if (pre.getExpLeft() == null) {
					pre.setExpLeft(exp);
				} else if (pre.getExpRight() == null) {
					pre.setExpRight(exp);
				}
			} else if (cond.getConditionType() == ConditionType.numericEffect) {
				NumericEffect eff = (NumericEffect) cond;
				eff.setExpression(exp);
			}
		}
		
		expressionStack.push(exp);
	}
	
	private void exitNumericExpression() {
		
		expressionStack.pop();
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
		} else if (context == ParseContext.derivedPredicateDef) {
			derivedPredicates.get(predicate.getName()).setCondition(cond);
		} else if (context == ParseContext.methodDef 
				|| context == ParseContext.initialTaskNetworkDef) {
			currentMethod().addConditionToLastConstraint(cond);
		}
	}
	
	/**
	 * The operator currently being parsed.
	 */
	private Operator currentOperator() {
		return operators.get(operators.size()-1);
	}
	
	/**
	 * The method currently being parsed.
	 */
	private Method currentMethod() {
		if (context == ParseContext.methodDef) {			
			return methods.get(methods.size()-1);
		} else {
			return initialTaskNetwork;
		}
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
	
	private Type getTypeOfConstant(String argName) {
		return getTypeOfConstant(argName, null, 0);
	}
	
	private Type getTypeOfConstant(String argName, Predicate surroundingPred, int index) {
		
		// Check if there is a fitting constant,
		// and which type it has
		Type type = null;
		for (Argument constant : constants) {
			if (constant.getName().equalsIgnoreCase(argName)) {
				type = constant.getType();
			}
		}
		
		if (type == null && surroundingPred != null) {
			// New constant is created; infer type from predicate
			type = surroundingPred.getArgumentTypes().get(index);
			constants.add(new Argument(argName, type));
		}	
		
		return type;
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
