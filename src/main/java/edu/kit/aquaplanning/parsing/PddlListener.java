package edu.kit.aquaplanning.parsing;

// Generated from Pddl.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PddlParser}.
 */
public interface PddlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PddlParser#pddlDoc}.
	 * @param ctx the parse tree
	 */
	void enterPddlDoc(PddlParser.PddlDocContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#pddlDoc}.
	 * @param ctx the parse tree
	 */
	void exitPddlDoc(PddlParser.PddlDocContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#domain}.
	 * @param ctx the parse tree
	 */
	void enterDomain(PddlParser.DomainContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#domain}.
	 * @param ctx the parse tree
	 */
	void exitDomain(PddlParser.DomainContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#domainName}.
	 * @param ctx the parse tree
	 */
	void enterDomainName(PddlParser.DomainNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#domainName}.
	 * @param ctx the parse tree
	 */
	void exitDomainName(PddlParser.DomainNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#requireDef}.
	 * @param ctx the parse tree
	 */
	void enterRequireDef(PddlParser.RequireDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#requireDef}.
	 * @param ctx the parse tree
	 */
	void exitRequireDef(PddlParser.RequireDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#typesDef}.
	 * @param ctx the parse tree
	 */
	void enterTypesDef(PddlParser.TypesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#typesDef}.
	 * @param ctx the parse tree
	 */
	void exitTypesDef(PddlParser.TypesDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#typedNameList}.
	 * @param ctx the parse tree
	 */
	void enterTypedNameList(PddlParser.TypedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#typedNameList}.
	 * @param ctx the parse tree
	 */
	void exitTypedNameList(PddlParser.TypedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#singleTypeNameList}.
	 * @param ctx the parse tree
	 */
	void enterSingleTypeNameList(PddlParser.SingleTypeNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#singleTypeNameList}.
	 * @param ctx the parse tree
	 */
	void exitSingleTypeNameList(PddlParser.SingleTypeNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(PddlParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(PddlParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#primType}.
	 * @param ctx the parse tree
	 */
	void enterPrimType(PddlParser.PrimTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#primType}.
	 * @param ctx the parse tree
	 */
	void exitPrimType(PddlParser.PrimTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#functionsDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionsDef(PddlParser.FunctionsDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#functionsDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionsDef(PddlParser.FunctionsDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#functionList}.
	 * @param ctx the parse tree
	 */
	void enterFunctionList(PddlParser.FunctionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#functionList}.
	 * @param ctx the parse tree
	 */
	void exitFunctionList(PddlParser.FunctionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#atomicFunctionSkeleton}.
	 * @param ctx the parse tree
	 */
	void enterAtomicFunctionSkeleton(PddlParser.AtomicFunctionSkeletonContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#atomicFunctionSkeleton}.
	 * @param ctx the parse tree
	 */
	void exitAtomicFunctionSkeleton(PddlParser.AtomicFunctionSkeletonContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#functionSymbol}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSymbol(PddlParser.FunctionSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#functionSymbol}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSymbol(PddlParser.FunctionSymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(PddlParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(PddlParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void enterConstantsDef(PddlParser.ConstantsDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void exitConstantsDef(PddlParser.ConstantsDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#predicatesDef}.
	 * @param ctx the parse tree
	 */
	void enterPredicatesDef(PddlParser.PredicatesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#predicatesDef}.
	 * @param ctx the parse tree
	 */
	void exitPredicatesDef(PddlParser.PredicatesDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#atomicFormulaSkeleton}.
	 * @param ctx the parse tree
	 */
	void enterAtomicFormulaSkeleton(PddlParser.AtomicFormulaSkeletonContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#atomicFormulaSkeleton}.
	 * @param ctx the parse tree
	 */
	void exitAtomicFormulaSkeleton(PddlParser.AtomicFormulaSkeletonContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(PddlParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(PddlParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#typedVariableList}.
	 * @param ctx the parse tree
	 */
	void enterTypedVariableList(PddlParser.TypedVariableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#typedVariableList}.
	 * @param ctx the parse tree
	 */
	void exitTypedVariableList(PddlParser.TypedVariableListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#singleTypeVarList}.
	 * @param ctx the parse tree
	 */
	void enterSingleTypeVarList(PddlParser.SingleTypeVarListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#singleTypeVarList}.
	 * @param ctx the parse tree
	 */
	void exitSingleTypeVarList(PddlParser.SingleTypeVarListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#constraints}.
	 * @param ctx the parse tree
	 */
	void enterConstraints(PddlParser.ConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#constraints}.
	 * @param ctx the parse tree
	 */
	void exitConstraints(PddlParser.ConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#structureDef}.
	 * @param ctx the parse tree
	 */
	void enterStructureDef(PddlParser.StructureDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#structureDef}.
	 * @param ctx the parse tree
	 */
	void exitStructureDef(PddlParser.StructureDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#actionDef}.
	 * @param ctx the parse tree
	 */
	void enterActionDef(PddlParser.ActionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#actionDef}.
	 * @param ctx the parse tree
	 */
	void exitActionDef(PddlParser.ActionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#actionSymbol}.
	 * @param ctx the parse tree
	 */
	void enterActionSymbol(PddlParser.ActionSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#actionSymbol}.
	 * @param ctx the parse tree
	 */
	void exitActionSymbol(PddlParser.ActionSymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#actionDefBody}.
	 * @param ctx the parse tree
	 */
	void enterActionDefBody(PddlParser.ActionDefBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#actionDefBody}.
	 * @param ctx the parse tree
	 */
	void exitActionDefBody(PddlParser.ActionDefBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#goalDesc}.
	 * @param ctx the parse tree
	 */
	void enterGoalDesc(PddlParser.GoalDescContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#goalDesc}.
	 * @param ctx the parse tree
	 */
	void exitGoalDesc(PddlParser.GoalDescContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fComp}.
	 * @param ctx the parse tree
	 */
	void enterFComp(PddlParser.FCompContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fComp}.
	 * @param ctx the parse tree
	 */
	void exitFComp(PddlParser.FCompContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#atomicTermFormula}.
	 * @param ctx the parse tree
	 */
	void enterAtomicTermFormula(PddlParser.AtomicTermFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#atomicTermFormula}.
	 * @param ctx the parse tree
	 */
	void exitAtomicTermFormula(PddlParser.AtomicTermFormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(PddlParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(PddlParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#durativeActionDef}.
	 * @param ctx the parse tree
	 */
	void enterDurativeActionDef(PddlParser.DurativeActionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#durativeActionDef}.
	 * @param ctx the parse tree
	 */
	void exitDurativeActionDef(PddlParser.DurativeActionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#daDefBody}.
	 * @param ctx the parse tree
	 */
	void enterDaDefBody(PddlParser.DaDefBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#daDefBody}.
	 * @param ctx the parse tree
	 */
	void exitDaDefBody(PddlParser.DaDefBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#daGD}.
	 * @param ctx the parse tree
	 */
	void enterDaGD(PddlParser.DaGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#daGD}.
	 * @param ctx the parse tree
	 */
	void exitDaGD(PddlParser.DaGDContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#prefTimedGD}.
	 * @param ctx the parse tree
	 */
	void enterPrefTimedGD(PddlParser.PrefTimedGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#prefTimedGD}.
	 * @param ctx the parse tree
	 */
	void exitPrefTimedGD(PddlParser.PrefTimedGDContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#timedGD}.
	 * @param ctx the parse tree
	 */
	void enterTimedGD(PddlParser.TimedGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#timedGD}.
	 * @param ctx the parse tree
	 */
	void exitTimedGD(PddlParser.TimedGDContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#timeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTimeSpecifier(PddlParser.TimeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#timeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTimeSpecifier(PddlParser.TimeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(PddlParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(PddlParser.IntervalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#derivedDef}.
	 * @param ctx the parse tree
	 */
	void enterDerivedDef(PddlParser.DerivedDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#derivedDef}.
	 * @param ctx the parse tree
	 */
	void exitDerivedDef(PddlParser.DerivedDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fExp}.
	 * @param ctx the parse tree
	 */
	void enterFExp(PddlParser.FExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fExp}.
	 * @param ctx the parse tree
	 */
	void exitFExp(PddlParser.FExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fExp2}.
	 * @param ctx the parse tree
	 */
	void enterFExp2(PddlParser.FExp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fExp2}.
	 * @param ctx the parse tree
	 */
	void exitFExp2(PddlParser.FExp2Context ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fHead}.
	 * @param ctx the parse tree
	 */
	void enterFHead(PddlParser.FHeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fHead}.
	 * @param ctx the parse tree
	 */
	void exitFHead(PddlParser.FHeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#effect}.
	 * @param ctx the parse tree
	 */
	void enterEffect(PddlParser.EffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#effect}.
	 * @param ctx the parse tree
	 */
	void exitEffect(PddlParser.EffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#cEffect}.
	 * @param ctx the parse tree
	 */
	void enterCEffect(PddlParser.CEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#cEffect}.
	 * @param ctx the parse tree
	 */
	void exitCEffect(PddlParser.CEffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#pEffect}.
	 * @param ctx the parse tree
	 */
	void enterPEffect(PddlParser.PEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#pEffect}.
	 * @param ctx the parse tree
	 */
	void exitPEffect(PddlParser.PEffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#condEffect}.
	 * @param ctx the parse tree
	 */
	void enterCondEffect(PddlParser.CondEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#condEffect}.
	 * @param ctx the parse tree
	 */
	void exitCondEffect(PddlParser.CondEffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#binaryOp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOp(PddlParser.BinaryOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#binaryOp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOp(PddlParser.BinaryOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#binaryComp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryComp(PddlParser.BinaryCompContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#binaryComp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryComp(PddlParser.BinaryCompContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#assignOp}.
	 * @param ctx the parse tree
	 */
	void enterAssignOp(PddlParser.AssignOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#assignOp}.
	 * @param ctx the parse tree
	 */
	void exitAssignOp(PddlParser.AssignOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#durationConstraint}.
	 * @param ctx the parse tree
	 */
	void enterDurationConstraint(PddlParser.DurationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#durationConstraint}.
	 * @param ctx the parse tree
	 */
	void exitDurationConstraint(PddlParser.DurationConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#simpleDurationConstraint}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDurationConstraint(PddlParser.SimpleDurationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#simpleDurationConstraint}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDurationConstraint(PddlParser.SimpleDurationConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#durOp}.
	 * @param ctx the parse tree
	 */
	void enterDurOp(PddlParser.DurOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#durOp}.
	 * @param ctx the parse tree
	 */
	void exitDurOp(PddlParser.DurOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#durValue}.
	 * @param ctx the parse tree
	 */
	void enterDurValue(PddlParser.DurValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#durValue}.
	 * @param ctx the parse tree
	 */
	void exitDurValue(PddlParser.DurValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#daEffect}.
	 * @param ctx the parse tree
	 */
	void enterDaEffect(PddlParser.DaEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#daEffect}.
	 * @param ctx the parse tree
	 */
	void exitDaEffect(PddlParser.DaEffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#timedEffect}.
	 * @param ctx the parse tree
	 */
	void enterTimedEffect(PddlParser.TimedEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#timedEffect}.
	 * @param ctx the parse tree
	 */
	void exitTimedEffect(PddlParser.TimedEffectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fAssignDA}.
	 * @param ctx the parse tree
	 */
	void enterFAssignDA(PddlParser.FAssignDAContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fAssignDA}.
	 * @param ctx the parse tree
	 */
	void exitFAssignDA(PddlParser.FAssignDAContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#fExpDA}.
	 * @param ctx the parse tree
	 */
	void enterFExpDA(PddlParser.FExpDAContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#fExpDA}.
	 * @param ctx the parse tree
	 */
	void exitFExpDA(PddlParser.FExpDAContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#problem}.
	 * @param ctx the parse tree
	 */
	void enterProblem(PddlParser.ProblemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#problem}.
	 * @param ctx the parse tree
	 */
	void exitProblem(PddlParser.ProblemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#problemDecl}.
	 * @param ctx the parse tree
	 */
	void enterProblemDecl(PddlParser.ProblemDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#problemDecl}.
	 * @param ctx the parse tree
	 */
	void exitProblemDecl(PddlParser.ProblemDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#problemDomain}.
	 * @param ctx the parse tree
	 */
	void enterProblemDomain(PddlParser.ProblemDomainContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#problemDomain}.
	 * @param ctx the parse tree
	 */
	void exitProblemDomain(PddlParser.ProblemDomainContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#objectDecl}.
	 * @param ctx the parse tree
	 */
	void enterObjectDecl(PddlParser.ObjectDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#objectDecl}.
	 * @param ctx the parse tree
	 */
	void exitObjectDecl(PddlParser.ObjectDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInit(PddlParser.InitContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInit(PddlParser.InitContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#initEl}.
	 * @param ctx the parse tree
	 */
	void enterInitEl(PddlParser.InitElContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#initEl}.
	 * @param ctx the parse tree
	 */
	void exitInitEl(PddlParser.InitElContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#nameLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNameLiteral(PddlParser.NameLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#nameLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNameLiteral(PddlParser.NameLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#atomicNameFormula}.
	 * @param ctx the parse tree
	 */
	void enterAtomicNameFormula(PddlParser.AtomicNameFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#atomicNameFormula}.
	 * @param ctx the parse tree
	 */
	void exitAtomicNameFormula(PddlParser.AtomicNameFormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(PddlParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(PddlParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#probConstraints}.
	 * @param ctx the parse tree
	 */
	void enterProbConstraints(PddlParser.ProbConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#probConstraints}.
	 * @param ctx the parse tree
	 */
	void exitProbConstraints(PddlParser.ProbConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#prefConGD}.
	 * @param ctx the parse tree
	 */
	void enterPrefConGD(PddlParser.PrefConGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#prefConGD}.
	 * @param ctx the parse tree
	 */
	void exitPrefConGD(PddlParser.PrefConGDContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#metricSpec}.
	 * @param ctx the parse tree
	 */
	void enterMetricSpec(PddlParser.MetricSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#metricSpec}.
	 * @param ctx the parse tree
	 */
	void exitMetricSpec(PddlParser.MetricSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#optimization}.
	 * @param ctx the parse tree
	 */
	void enterOptimization(PddlParser.OptimizationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#optimization}.
	 * @param ctx the parse tree
	 */
	void exitOptimization(PddlParser.OptimizationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#metricFExp}.
	 * @param ctx the parse tree
	 */
	void enterMetricFExp(PddlParser.MetricFExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#metricFExp}.
	 * @param ctx the parse tree
	 */
	void exitMetricFExp(PddlParser.MetricFExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PddlParser#conGD}.
	 * @param ctx the parse tree
	 */
	void enterConGD(PddlParser.ConGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link PddlParser#conGD}.
	 * @param ctx the parse tree
	 */
	void exitConGD(PddlParser.ConGDContext ctx);
}