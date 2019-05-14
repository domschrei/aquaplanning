package edu.kit.aquaplanning.parsing;

// Generated from PddlHtn.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PddlHtnParser}.
 */
public interface PddlHtnListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#pddlDoc}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPddlDoc(PddlHtnParser.PddlDocContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#pddlDoc}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPddlDoc(PddlHtnParser.PddlDocContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#domain}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDomain(PddlHtnParser.DomainContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#domain}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDomain(PddlHtnParser.DomainContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#domainName}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDomainName(PddlHtnParser.DomainNameContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#domainName}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDomainName(PddlHtnParser.DomainNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#requireDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterRequireDef(PddlHtnParser.RequireDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#requireDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitRequireDef(PddlHtnParser.RequireDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#typesDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTypesDef(PddlHtnParser.TypesDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#typesDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTypesDef(PddlHtnParser.TypesDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#typedNameList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTypedNameList(PddlHtnParser.TypedNameListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#typedNameList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTypedNameList(PddlHtnParser.TypedNameListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#singleTypeNameList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterSingleTypeNameList(PddlHtnParser.SingleTypeNameListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#singleTypeNameList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitSingleTypeNameList(PddlHtnParser.SingleTypeNameListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#type}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterType(PddlHtnParser.TypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#type}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitType(PddlHtnParser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#primType}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPrimType(PddlHtnParser.PrimTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#primType}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPrimType(PddlHtnParser.PrimTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#functionsDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFunctionsDef(PddlHtnParser.FunctionsDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#functionsDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFunctionsDef(PddlHtnParser.FunctionsDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#functionList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFunctionList(PddlHtnParser.FunctionListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#functionList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFunctionList(PddlHtnParser.FunctionListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#atomicFunctionSkeleton}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterAtomicFunctionSkeleton(PddlHtnParser.AtomicFunctionSkeletonContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#atomicFunctionSkeleton}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitAtomicFunctionSkeleton(PddlHtnParser.AtomicFunctionSkeletonContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#functionSymbol}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFunctionSymbol(PddlHtnParser.FunctionSymbolContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#functionSymbol}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFunctionSymbol(PddlHtnParser.FunctionSymbolContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#functionType}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFunctionType(PddlHtnParser.FunctionTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#functionType}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFunctionType(PddlHtnParser.FunctionTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#constantsDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterConstantsDef(PddlHtnParser.ConstantsDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#constantsDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitConstantsDef(PddlHtnParser.ConstantsDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#predicatesDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPredicatesDef(PddlHtnParser.PredicatesDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#predicatesDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPredicatesDef(PddlHtnParser.PredicatesDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#atomicFormulaSkeleton}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterAtomicFormulaSkeleton(PddlHtnParser.AtomicFormulaSkeletonContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#atomicFormulaSkeleton}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitAtomicFormulaSkeleton(PddlHtnParser.AtomicFormulaSkeletonContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#predicate}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPredicate(PddlHtnParser.PredicateContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#predicate}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPredicate(PddlHtnParser.PredicateContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#typedVariableList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTypedVariableList(PddlHtnParser.TypedVariableListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#typedVariableList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTypedVariableList(PddlHtnParser.TypedVariableListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#singleTypeVarList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterSingleTypeVarList(PddlHtnParser.SingleTypeVarListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#singleTypeVarList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitSingleTypeVarList(PddlHtnParser.SingleTypeVarListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#constraints}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterConstraints(PddlHtnParser.ConstraintsContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#constraints}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitConstraints(PddlHtnParser.ConstraintsContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#structureDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterStructureDef(PddlHtnParser.StructureDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#structureDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitStructureDef(PddlHtnParser.StructureDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#actionDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterActionDef(PddlHtnParser.ActionDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#actionDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitActionDef(PddlHtnParser.ActionDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#actionSymbol}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterActionSymbol(PddlHtnParser.ActionSymbolContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#actionSymbol}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitActionSymbol(PddlHtnParser.ActionSymbolContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#actionDefBody}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterActionDefBody(PddlHtnParser.ActionDefBodyContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#actionDefBody}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitActionDefBody(PddlHtnParser.ActionDefBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#goalDesc}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterGoalDesc(PddlHtnParser.GoalDescContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#goalDesc}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitGoalDesc(PddlHtnParser.GoalDescContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#fComp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFComp(PddlHtnParser.FCompContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#fComp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFComp(PddlHtnParser.FCompContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#atomicTermFormula}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterAtomicTermFormula(PddlHtnParser.AtomicTermFormulaContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#atomicTermFormula}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitAtomicTermFormula(PddlHtnParser.AtomicTermFormulaContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#term}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTerm(PddlHtnParser.TermContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#term}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTerm(PddlHtnParser.TermContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#methodDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterMethodDef(PddlHtnParser.MethodDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#methodDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitMethodDef(PddlHtnParser.MethodDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#taskList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTaskList(PddlHtnParser.TaskListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#taskList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTaskList(PddlHtnParser.TaskListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#task}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTask(PddlHtnParser.TaskContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#task}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTask(PddlHtnParser.TaskContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#taggedTask}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTaggedTask(PddlHtnParser.TaggedTaskContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#taggedTask}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTaggedTask(PddlHtnParser.TaggedTaskContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#constraintList}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterConstraintList(PddlHtnParser.ConstraintListContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#constraintList}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitConstraintList(PddlHtnParser.ConstraintListContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#constraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterConstraint(PddlHtnParser.ConstraintContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#constraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitConstraint(PddlHtnParser.ConstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#durativeActionDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDurativeActionDef(PddlHtnParser.DurativeActionDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#durativeActionDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDurativeActionDef(PddlHtnParser.DurativeActionDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#daDefBody}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDaDefBody(PddlHtnParser.DaDefBodyContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#daDefBody}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDaDefBody(PddlHtnParser.DaDefBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#daGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDaGD(PddlHtnParser.DaGDContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#daGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDaGD(PddlHtnParser.DaGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#prefTimedGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPrefTimedGD(PddlHtnParser.PrefTimedGDContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#prefTimedGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPrefTimedGD(PddlHtnParser.PrefTimedGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#timedGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTimedGD(PddlHtnParser.TimedGDContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#timedGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTimedGD(PddlHtnParser.TimedGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#timeSpecifier}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTimeSpecifier(PddlHtnParser.TimeSpecifierContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#timeSpecifier}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTimeSpecifier(PddlHtnParser.TimeSpecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#interval}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterInterval(PddlHtnParser.IntervalContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#interval}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitInterval(PddlHtnParser.IntervalContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#derivedDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDerivedDef(PddlHtnParser.DerivedDefContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#derivedDef}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDerivedDef(PddlHtnParser.DerivedDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#fExp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFExp(PddlHtnParser.FExpContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#fExp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFExp(PddlHtnParser.FExpContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#fHead}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFHead(PddlHtnParser.FHeadContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#fHead}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFHead(PddlHtnParser.FHeadContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#effect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterEffect(PddlHtnParser.EffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#effect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitEffect(PddlHtnParser.EffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#cEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterCEffect(PddlHtnParser.CEffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#cEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitCEffect(PddlHtnParser.CEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#pEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPEffect(PddlHtnParser.PEffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#pEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPEffect(PddlHtnParser.PEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#condEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterCondEffect(PddlHtnParser.CondEffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#condEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitCondEffect(PddlHtnParser.CondEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#binaryOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterBinaryOp(PddlHtnParser.BinaryOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#binaryOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitBinaryOp(PddlHtnParser.BinaryOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#binaryComp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterBinaryComp(PddlHtnParser.BinaryCompContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#binaryComp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitBinaryComp(PddlHtnParser.BinaryCompContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#assignOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterAssignOp(PddlHtnParser.AssignOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#assignOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitAssignOp(PddlHtnParser.AssignOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#durationConstraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDurationConstraint(PddlHtnParser.DurationConstraintContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#durationConstraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDurationConstraint(PddlHtnParser.DurationConstraintContext ctx);

	/**
	 * Enter a parse tree produced by
	 * {@link PddlHtnParser#simpleDurationConstraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterSimpleDurationConstraint(PddlHtnParser.SimpleDurationConstraintContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#simpleDurationConstraint}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitSimpleDurationConstraint(PddlHtnParser.SimpleDurationConstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#durOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDurOp(PddlHtnParser.DurOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#durOp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDurOp(PddlHtnParser.DurOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#durValue}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDurValue(PddlHtnParser.DurValueContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#durValue}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDurValue(PddlHtnParser.DurValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#daEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterDaEffect(PddlHtnParser.DaEffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#daEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitDaEffect(PddlHtnParser.DaEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#timedEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterTimedEffect(PddlHtnParser.TimedEffectContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#timedEffect}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitTimedEffect(PddlHtnParser.TimedEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#fAssignDA}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFAssignDA(PddlHtnParser.FAssignDAContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#fAssignDA}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFAssignDA(PddlHtnParser.FAssignDAContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#fExpDA}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterFExpDA(PddlHtnParser.FExpDAContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#fExpDA}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitFExpDA(PddlHtnParser.FExpDAContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#problem}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterProblem(PddlHtnParser.ProblemContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#problem}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitProblem(PddlHtnParser.ProblemContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#problemDecl}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterProblemDecl(PddlHtnParser.ProblemDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#problemDecl}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitProblemDecl(PddlHtnParser.ProblemDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#problemDomain}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterProblemDomain(PddlHtnParser.ProblemDomainContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#problemDomain}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitProblemDomain(PddlHtnParser.ProblemDomainContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#objectDecl}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterObjectDecl(PddlHtnParser.ObjectDeclContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#objectDecl}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitObjectDecl(PddlHtnParser.ObjectDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#init}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterInit(PddlHtnParser.InitContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#init}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitInit(PddlHtnParser.InitContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#initEl}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterInitEl(PddlHtnParser.InitElContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#initEl}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitInitEl(PddlHtnParser.InitElContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#nameLiteral}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterNameLiteral(PddlHtnParser.NameLiteralContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#nameLiteral}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitNameLiteral(PddlHtnParser.NameLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#atomicNameFormula}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterAtomicNameFormula(PddlHtnParser.AtomicNameFormulaContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#atomicNameFormula}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitAtomicNameFormula(PddlHtnParser.AtomicNameFormulaContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#goal}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterGoal(PddlHtnParser.GoalContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#goal}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitGoal(PddlHtnParser.GoalContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#initTaskNetwork}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterInitTaskNetwork(PddlHtnParser.InitTaskNetworkContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#initTaskNetwork}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitInitTaskNetwork(PddlHtnParser.InitTaskNetworkContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#probConstraints}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterProbConstraints(PddlHtnParser.ProbConstraintsContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#probConstraints}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitProbConstraints(PddlHtnParser.ProbConstraintsContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#prefConGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterPrefConGD(PddlHtnParser.PrefConGDContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#prefConGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitPrefConGD(PddlHtnParser.PrefConGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#metricSpec}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterMetricSpec(PddlHtnParser.MetricSpecContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#metricSpec}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitMetricSpec(PddlHtnParser.MetricSpecContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#optimization}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterOptimization(PddlHtnParser.OptimizationContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#optimization}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitOptimization(PddlHtnParser.OptimizationContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#metricFExp}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterMetricFExp(PddlHtnParser.MetricFExpContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#metricFExp}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitMetricFExp(PddlHtnParser.MetricFExpContext ctx);

	/**
	 * Enter a parse tree produced by {@link PddlHtnParser#conGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void enterConGD(PddlHtnParser.ConGDContext ctx);

	/**
	 * Exit a parse tree produced by {@link PddlHtnParser#conGD}.
	 * 
	 * @param ctx the parse tree
	 */
	void exitConGD(PddlHtnParser.ConGDContext ctx);
}