package edu.kit.aquaplanning.parsing;

// Generated from PddlHtn.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class PddlHtnParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
			T__9 = 10, T__10 = 11, T__11 = 12, REQUIRE_KEY = 13, DEFINE = 14, DOMAIN = 15, PROBLEM = 16,
			REQUIREMENTS = 17, TYPES = 18, EITHER = 19, FUNCTIONS = 20, CONSTANTS = 21, PREDICATES = 22,
			CONSTRAINTS = 23, ACTION = 24, PARAMETERS = 25, PRECONDITION = 26, EFFECT = 27, AND = 28, OR = 29, NOT = 30,
			IMPLY = 31, EXISTS = 32, FORALL = 33, DURATIVE_ACTION = 34, DURATION = 35, CONDITION = 36, PREFERENCE = 37,
			OVER_ALL = 38, AT_START = 39, AT_END = 40, DERIVED = 41, WHEN = 42, ASSIGN = 43, INCREASE = 44,
			DECREASE = 45, SCALE_UP = 46, SCALE_DOWN = 47, OBJECTS = 48, INIT = 49, GOAL = 50, METRIC = 51,
			MINIMIZE = 52, MAXIMIZE = 53, TOTAL_TIME = 54, IS_VIOLATED = 55, ALWAYS = 56, SOMETIME = 57, WITHIN = 58,
			AT_MOST_ONCE = 59, SOMETIME_AFTER = 60, SOMETIME_BEFORE = 61, ALWAYS_WITHIN = 62, HOLD_DURING = 63,
			HOLD_AFTER = 64, METHOD = 65, EXPANSION = 66, TAG = 67, BEFORE = 68, AFTER = 69, BETWEEN = 70, TASKS = 71,
			R_STRIPS = 72, R_TYPING = 73, R_NEGATIVE_PRECONDITIONS = 74, R_DISJUNCTIVE_PRECONDITIONS = 75,
			R_EQUALITY = 76, R_EXISTENTIAL_PRECONDITIONS = 77, R_UNIVERSAL_PRECONDITIONS = 78,
			R_QUANTIFIED_PRECONDITIONS = 79, R_CONDITIONAL_EFFECTS = 80, R_FLUENTS = 81, R_ADL = 82,
			R_DURATIVE_ACTIONS = 83, R_DERIVED_PREDICATES = 84, R_TIMED_INITIAL_LITERALS = 85, R_PREFERENCES = 86,
			R_ACTION_COSTS = 87, R_HTN = 88, STR_NUMBER = 89, NAME = 90, VARIABLE = 91, EQUALS = 92, NUMBER = 93,
			LINE_COMMENT = 94, WHITESPACE = 95;
	public static final int RULE_pddlDoc = 0, RULE_domain = 1, RULE_domainName = 2, RULE_requireDef = 3,
			RULE_typesDef = 4, RULE_typedNameList = 5, RULE_singleTypeNameList = 6, RULE_type = 7, RULE_primType = 8,
			RULE_functionsDef = 9, RULE_functionList = 10, RULE_atomicFunctionSkeleton = 11, RULE_functionSymbol = 12,
			RULE_functionType = 13, RULE_constantsDef = 14, RULE_predicatesDef = 15, RULE_atomicFormulaSkeleton = 16,
			RULE_predicate = 17, RULE_typedVariableList = 18, RULE_singleTypeVarList = 19, RULE_constraints = 20,
			RULE_structureDef = 21, RULE_actionDef = 22, RULE_actionSymbol = 23, RULE_actionDefBody = 24,
			RULE_goalDesc = 25, RULE_fComp = 26, RULE_atomicTermFormula = 27, RULE_term = 28, RULE_methodDef = 29,
			RULE_taskList = 30, RULE_task = 31, RULE_taggedTask = 32, RULE_constraintList = 33, RULE_constraint = 34,
			RULE_durativeActionDef = 35, RULE_daDefBody = 36, RULE_daGD = 37, RULE_prefTimedGD = 38, RULE_timedGD = 39,
			RULE_timeSpecifier = 40, RULE_interval = 41, RULE_derivedDef = 42, RULE_fExp = 43, RULE_fHead = 44,
			RULE_effect = 45, RULE_cEffect = 46, RULE_pEffect = 47, RULE_condEffect = 48, RULE_binaryOp = 49,
			RULE_binaryComp = 50, RULE_assignOp = 51, RULE_durationConstraint = 52, RULE_simpleDurationConstraint = 53,
			RULE_durOp = 54, RULE_durValue = 55, RULE_daEffect = 56, RULE_timedEffect = 57, RULE_fAssignDA = 58,
			RULE_fExpDA = 59, RULE_problem = 60, RULE_problemDecl = 61, RULE_problemDomain = 62, RULE_objectDecl = 63,
			RULE_init = 64, RULE_initEl = 65, RULE_nameLiteral = 66, RULE_atomicNameFormula = 67, RULE_goal = 68,
			RULE_initTaskNetwork = 69, RULE_probConstraints = 70, RULE_prefConGD = 71, RULE_metricSpec = 72,
			RULE_optimization = 73, RULE_metricFExp = 74, RULE_conGD = 75;
	public static final String[] ruleNames = { "pddlDoc", "domain", "domainName", "requireDef", "typesDef",
			"typedNameList", "singleTypeNameList", "type", "primType", "functionsDef", "functionList",
			"atomicFunctionSkeleton", "functionSymbol", "functionType", "constantsDef", "predicatesDef",
			"atomicFormulaSkeleton", "predicate", "typedVariableList", "singleTypeVarList", "constraints",
			"structureDef", "actionDef", "actionSymbol", "actionDefBody", "goalDesc", "fComp", "atomicTermFormula",
			"term", "methodDef", "taskList", "task", "taggedTask", "constraintList", "constraint", "durativeActionDef",
			"daDefBody", "daGD", "prefTimedGD", "timedGD", "timeSpecifier", "interval", "derivedDef", "fExp", "fHead",
			"effect", "cEffect", "pEffect", "condEffect", "binaryOp", "binaryComp", "assignOp", "durationConstraint",
			"simpleDurationConstraint", "durOp", "durValue", "daEffect", "timedEffect", "fAssignDA", "fExpDA",
			"problem", "problemDecl", "problemDomain", "objectDecl", "init", "initEl", "nameLiteral",
			"atomicNameFormula", "goal", "initTaskNetwork", "probConstraints", "prefConGD", "metricSpec",
			"optimization", "metricFExp", "conGD" };

	private static final String[] _LITERAL_NAMES = { null, "'('", "')'", "':'", "'-'", "'*'", "'+'", "'/'", "'>'",
			"'<'", "'>='", "'<='", "'?'", null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, "'='" };
	private static final String[] _SYMBOLIC_NAMES = { null, null, null, null, null, null, null, null, null, null, null,
			null, null, "REQUIRE_KEY", "DEFINE", "DOMAIN", "PROBLEM", "REQUIREMENTS", "TYPES", "EITHER", "FUNCTIONS",
			"CONSTANTS", "PREDICATES", "CONSTRAINTS", "ACTION", "PARAMETERS", "PRECONDITION", "EFFECT", "AND", "OR",
			"NOT", "IMPLY", "EXISTS", "FORALL", "DURATIVE_ACTION", "DURATION", "CONDITION", "PREFERENCE", "OVER_ALL",
			"AT_START", "AT_END", "DERIVED", "WHEN", "ASSIGN", "INCREASE", "DECREASE", "SCALE_UP", "SCALE_DOWN",
			"OBJECTS", "INIT", "GOAL", "METRIC", "MINIMIZE", "MAXIMIZE", "TOTAL_TIME", "IS_VIOLATED", "ALWAYS",
			"SOMETIME", "WITHIN", "AT_MOST_ONCE", "SOMETIME_AFTER", "SOMETIME_BEFORE", "ALWAYS_WITHIN", "HOLD_DURING",
			"HOLD_AFTER", "METHOD", "EXPANSION", "TAG", "BEFORE", "AFTER", "BETWEEN", "TASKS", "R_STRIPS", "R_TYPING",
			"R_NEGATIVE_PRECONDITIONS", "R_DISJUNCTIVE_PRECONDITIONS", "R_EQUALITY", "R_EXISTENTIAL_PRECONDITIONS",
			"R_UNIVERSAL_PRECONDITIONS", "R_QUANTIFIED_PRECONDITIONS", "R_CONDITIONAL_EFFECTS", "R_FLUENTS", "R_ADL",
			"R_DURATIVE_ACTIONS", "R_DERIVED_PREDICATES", "R_TIMED_INITIAL_LITERALS", "R_PREFERENCES", "R_ACTION_COSTS",
			"R_HTN", "STR_NUMBER", "NAME", "VARIABLE", "EQUALS", "NUMBER", "LINE_COMMENT", "WHITESPACE" };
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "PddlHtn.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public PddlHtnParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class PddlDocContext extends ParserRuleContext {
		public DomainContext domain() {
			return getRuleContext(DomainContext.class, 0);
		}

		public ProblemContext problem() {
			return getRuleContext(ProblemContext.class, 0);
		}

		public PddlDocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_pddlDoc;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPddlDoc(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPddlDoc(this);
		}
	}

	public final PddlDocContext pddlDoc() throws RecognitionException {
		PddlDocContext _localctx = new PddlDocContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pddlDoc);
		try {
			setState(154);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(152);
				domain();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(153);
				problem();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DomainContext extends ParserRuleContext {
		public TerminalNode DEFINE() {
			return getToken(PddlHtnParser.DEFINE, 0);
		}

		public DomainNameContext domainName() {
			return getRuleContext(DomainNameContext.class, 0);
		}

		public RequireDefContext requireDef() {
			return getRuleContext(RequireDefContext.class, 0);
		}

		public TypesDefContext typesDef() {
			return getRuleContext(TypesDefContext.class, 0);
		}

		public ConstantsDefContext constantsDef() {
			return getRuleContext(ConstantsDefContext.class, 0);
		}

		public PredicatesDefContext predicatesDef() {
			return getRuleContext(PredicatesDefContext.class, 0);
		}

		public FunctionsDefContext functionsDef() {
			return getRuleContext(FunctionsDefContext.class, 0);
		}

		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class, 0);
		}

		public List<StructureDefContext> structureDef() {
			return getRuleContexts(StructureDefContext.class);
		}

		public StructureDefContext structureDef(int i) {
			return getRuleContext(StructureDefContext.class, i);
		}

		public DomainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_domain;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDomain(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDomain(this);
		}
	}

	public final DomainContext domain() throws RecognitionException {
		DomainContext _localctx = new DomainContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_domain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(156);
				match(T__0);
				setState(157);
				match(DEFINE);
				setState(158);
				domainName();
				setState(160);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
				case 1: {
					setState(159);
					requireDef();
				}
					break;
				}
				setState(163);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
				case 1: {
					setState(162);
					typesDef();
				}
					break;
				}
				setState(166);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
				case 1: {
					setState(165);
					constantsDef();
				}
					break;
				}
				setState(169);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
				case 1: {
					setState(168);
					predicatesDef();
				}
					break;
				}
				setState(172);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
				case 1: {
					setState(171);
					functionsDef();
				}
					break;
				}
				setState(175);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
				case 1: {
					setState(174);
					constraints();
				}
					break;
				}
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(177);
							structureDef();
						}
					}
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(183);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DomainNameContext extends ParserRuleContext {
		public TerminalNode DOMAIN() {
			return getToken(PddlHtnParser.DOMAIN, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public DomainNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_domainName;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDomainName(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDomainName(this);
		}
	}

	public final DomainNameContext domainName() throws RecognitionException {
		DomainNameContext _localctx = new DomainNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_domainName);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(185);
				match(T__0);
				setState(186);
				match(DOMAIN);
				setState(187);
				match(NAME);
				setState(188);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RequireDefContext extends ParserRuleContext {
		public TerminalNode REQUIREMENTS() {
			return getToken(PddlHtnParser.REQUIREMENTS, 0);
		}

		public List<TerminalNode> REQUIRE_KEY() {
			return getTokens(PddlHtnParser.REQUIRE_KEY);
		}

		public TerminalNode REQUIRE_KEY(int i) {
			return getToken(PddlHtnParser.REQUIRE_KEY, i);
		}

		public RequireDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_requireDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterRequireDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitRequireDef(this);
		}
	}

	public final RequireDefContext requireDef() throws RecognitionException {
		RequireDefContext _localctx = new RequireDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_requireDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(190);
				match(T__0);
				setState(191);
				match(T__2);
				setState(192);
				match(REQUIREMENTS);
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(193);
							match(REQUIRE_KEY);
						}
					}
					setState(196);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == REQUIRE_KEY);
				setState(198);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypesDefContext extends ParserRuleContext {
		public TerminalNode TYPES() {
			return getToken(PddlHtnParser.TYPES, 0);
		}

		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class, 0);
		}

		public TypesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typesDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTypesDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTypesDef(this);
		}
	}

	public final TypesDefContext typesDef() throws RecognitionException {
		TypesDefContext _localctx = new TypesDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typesDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(200);
				match(T__0);
				setState(201);
				match(T__2);
				setState(202);
				match(TYPES);
				setState(203);
				typedNameList();
				setState(204);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedNameListContext extends ParserRuleContext {
		public List<TerminalNode> NAME() {
			return getTokens(PddlHtnParser.NAME);
		}

		public TerminalNode NAME(int i) {
			return getToken(PddlHtnParser.NAME, i);
		}

		public List<SingleTypeNameListContext> singleTypeNameList() {
			return getRuleContexts(SingleTypeNameListContext.class);
		}

		public SingleTypeNameListContext singleTypeNameList(int i) {
			return getRuleContext(SingleTypeNameListContext.class, i);
		}

		public TypedNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typedNameList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTypedNameList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTypedNameList(this);
		}
	}

	public final TypedNameListContext typedNameList() throws RecognitionException {
		TypedNameListContext _localctx = new TypedNameListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_typedNameList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(223);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
				case 1: {
					setState(209);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == NAME) {
						{
							{
								setState(206);
								match(NAME);
							}
						}
						setState(211);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
					break;
				case 2: {
					setState(213);
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1: {
							{
								setState(212);
								singleTypeNameList();
							}
						}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(215);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
					} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
					setState(220);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == NAME) {
						{
							{
								setState(217);
								match(NAME);
							}
						}
						setState(222);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
					break;
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTypeNameListContext extends ParserRuleContext {
		public TypeContext t;

		public TypeContext type() {
			return getRuleContext(TypeContext.class, 0);
		}

		public List<TerminalNode> NAME() {
			return getTokens(PddlHtnParser.NAME);
		}

		public TerminalNode NAME(int i) {
			return getToken(PddlHtnParser.NAME, i);
		}

		public SingleTypeNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_singleTypeNameList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterSingleTypeNameList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitSingleTypeNameList(this);
		}
	}

	public final SingleTypeNameListContext singleTypeNameList() throws RecognitionException {
		SingleTypeNameListContext _localctx = new SingleTypeNameListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_singleTypeNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				{
					setState(226);
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
							{
								setState(225);
								match(NAME);
							}
						}
						setState(228);
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while (_la == NAME);
					setState(230);
					match(T__3);
					setState(231);
					((SingleTypeNameListContext) _localctx).t = type();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode EITHER() {
			return getToken(PddlHtnParser.EITHER, 0);
		}

		public List<PrimTypeContext> primType() {
			return getRuleContexts(PrimTypeContext.class);
		}

		public PrimTypeContext primType(int i) {
			return getRuleContext(PrimTypeContext.class, i);
		}

		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_type;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			setState(243);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1); {
				{
					setState(233);
					match(T__0);
					setState(234);
					match(EITHER);
					setState(236);
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
							{
								setState(235);
								primType();
							}
						}
						setState(238);
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while (_la == NAME);
					setState(240);
					match(T__1);
				}
			}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2); {
				setState(242);
				primType();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimTypeContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public PrimTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_primType;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPrimType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPrimType(this);
		}
	}

	public final PrimTypeContext primType() throws RecognitionException {
		PrimTypeContext _localctx = new PrimTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_primType);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(245);
				match(NAME);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionsDefContext extends ParserRuleContext {
		public TerminalNode FUNCTIONS() {
			return getToken(PddlHtnParser.FUNCTIONS, 0);
		}

		public FunctionListContext functionList() {
			return getRuleContext(FunctionListContext.class, 0);
		}

		public FunctionsDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_functionsDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFunctionsDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFunctionsDef(this);
		}
	}

	public final FunctionsDefContext functionsDef() throws RecognitionException {
		FunctionsDefContext _localctx = new FunctionsDefContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionsDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(247);
				match(T__0);
				setState(248);
				match(T__2);
				setState(249);
				match(FUNCTIONS);
				setState(250);
				functionList();
				setState(251);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionListContext extends ParserRuleContext {
		public List<AtomicFunctionSkeletonContext> atomicFunctionSkeleton() {
			return getRuleContexts(AtomicFunctionSkeletonContext.class);
		}

		public AtomicFunctionSkeletonContext atomicFunctionSkeleton(int i) {
			return getRuleContext(AtomicFunctionSkeletonContext.class, i);
		}

		public List<FunctionTypeContext> functionType() {
			return getRuleContexts(FunctionTypeContext.class);
		}

		public FunctionTypeContext functionType(int i) {
			return getRuleContext(FunctionTypeContext.class, i);
		}

		public FunctionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_functionList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFunctionList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFunctionList(this);
		}
	}

	public final FunctionListContext functionList() throws RecognitionException {
		FunctionListContext _localctx = new FunctionListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_functionList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(264);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(254);
							_errHandler.sync(this);
							_alt = 1;
							do {
								switch (_alt) {
								case 1: {
									{
										setState(253);
										atomicFunctionSkeleton();
									}
								}
									break;
								default:
									throw new NoViableAltException(this);
								}
								setState(256);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
							} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
							setState(260);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la == T__3) {
								{
									setState(258);
									match(T__3);
									setState(259);
									functionType();
								}
							}

						}
					}
					setState(266);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicFunctionSkeletonContext extends ParserRuleContext {
		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public AtomicFunctionSkeletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_atomicFunctionSkeleton;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterAtomicFunctionSkeleton(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitAtomicFunctionSkeleton(this);
		}
	}

	public final AtomicFunctionSkeletonContext atomicFunctionSkeleton() throws RecognitionException {
		AtomicFunctionSkeletonContext _localctx = new AtomicFunctionSkeletonContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_atomicFunctionSkeleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(267);
				match(T__0);
				setState(268);
				functionSymbol();
				setState(269);
				typedVariableList();
				setState(270);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionSymbolContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public FunctionSymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_functionSymbol;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFunctionSymbol(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFunctionSymbol(this);
		}
	}

	public final FunctionSymbolContext functionSymbol() throws RecognitionException {
		FunctionSymbolContext _localctx = new FunctionSymbolContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_functionSymbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(272);
				match(NAME);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeContext extends ParserRuleContext {
		public TerminalNode STR_NUMBER() {
			return getToken(PddlHtnParser.STR_NUMBER, 0);
		}

		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_functionType;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFunctionType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFunctionType(this);
		}
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionType);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(274);
				match(STR_NUMBER);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantsDefContext extends ParserRuleContext {
		public TerminalNode CONSTANTS() {
			return getToken(PddlHtnParser.CONSTANTS, 0);
		}

		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class, 0);
		}

		public ConstantsDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constantsDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterConstantsDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitConstantsDef(this);
		}
	}

	public final ConstantsDefContext constantsDef() throws RecognitionException {
		ConstantsDefContext _localctx = new ConstantsDefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constantsDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(276);
				match(T__0);
				setState(277);
				match(T__2);
				setState(278);
				match(CONSTANTS);
				setState(279);
				typedNameList();
				setState(280);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicatesDefContext extends ParserRuleContext {
		public TerminalNode PREDICATES() {
			return getToken(PddlHtnParser.PREDICATES, 0);
		}

		public List<AtomicFormulaSkeletonContext> atomicFormulaSkeleton() {
			return getRuleContexts(AtomicFormulaSkeletonContext.class);
		}

		public AtomicFormulaSkeletonContext atomicFormulaSkeleton(int i) {
			return getRuleContext(AtomicFormulaSkeletonContext.class, i);
		}

		public PredicatesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_predicatesDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPredicatesDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPredicatesDef(this);
		}
	}

	public final PredicatesDefContext predicatesDef() throws RecognitionException {
		PredicatesDefContext _localctx = new PredicatesDefContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_predicatesDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(282);
				match(T__0);
				setState(283);
				match(T__2);
				setState(284);
				match(PREDICATES);
				setState(286);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(285);
							atomicFormulaSkeleton();
						}
					}
					setState(288);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == T__0);
				setState(290);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicFormulaSkeletonContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public AtomicFormulaSkeletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_atomicFormulaSkeleton;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterAtomicFormulaSkeleton(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitAtomicFormulaSkeleton(this);
		}
	}

	public final AtomicFormulaSkeletonContext atomicFormulaSkeleton() throws RecognitionException {
		AtomicFormulaSkeletonContext _localctx = new AtomicFormulaSkeletonContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_atomicFormulaSkeleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(292);
				match(T__0);
				setState(293);
				predicate();
				setState(294);
				typedVariableList();
				setState(295);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_predicate;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPredicate(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(297);
				match(NAME);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedVariableListContext extends ParserRuleContext {
		public List<TerminalNode> VARIABLE() {
			return getTokens(PddlHtnParser.VARIABLE);
		}

		public TerminalNode VARIABLE(int i) {
			return getToken(PddlHtnParser.VARIABLE, i);
		}

		public List<SingleTypeVarListContext> singleTypeVarList() {
			return getRuleContexts(SingleTypeVarListContext.class);
		}

		public SingleTypeVarListContext singleTypeVarList(int i) {
			return getRuleContext(SingleTypeVarListContext.class, i);
		}

		public TypedVariableListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typedVariableList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTypedVariableList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTypedVariableList(this);
		}
	}

	public final TypedVariableListContext typedVariableList() throws RecognitionException {
		TypedVariableListContext _localctx = new TypedVariableListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_typedVariableList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(316);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
				case 1: {
					setState(302);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == VARIABLE) {
						{
							{
								setState(299);
								match(VARIABLE);
							}
						}
						setState(304);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
					break;
				case 2: {
					setState(306);
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1: {
							{
								setState(305);
								singleTypeVarList();
							}
						}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(308);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
					} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
					setState(313);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == VARIABLE) {
						{
							{
								setState(310);
								match(VARIABLE);
							}
						}
						setState(315);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
					break;
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTypeVarListContext extends ParserRuleContext {
		public TypeContext t;

		public TypeContext type() {
			return getRuleContext(TypeContext.class, 0);
		}

		public List<TerminalNode> VARIABLE() {
			return getTokens(PddlHtnParser.VARIABLE);
		}

		public TerminalNode VARIABLE(int i) {
			return getToken(PddlHtnParser.VARIABLE, i);
		}

		public SingleTypeVarListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_singleTypeVarList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterSingleTypeVarList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitSingleTypeVarList(this);
		}
	}

	public final SingleTypeVarListContext singleTypeVarList() throws RecognitionException {
		SingleTypeVarListContext _localctx = new SingleTypeVarListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_singleTypeVarList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				{
					setState(319);
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
							{
								setState(318);
								match(VARIABLE);
							}
						}
						setState(321);
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while (_la == VARIABLE);
					setState(323);
					match(T__3);
					setState(324);
					((SingleTypeVarListContext) _localctx).t = type();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintsContext extends ParserRuleContext {
		public TerminalNode CONSTRAINTS() {
			return getToken(PddlHtnParser.CONSTRAINTS, 0);
		}

		public ConGDContext conGD() {
			return getRuleContext(ConGDContext.class, 0);
		}

		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constraints;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterConstraints(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitConstraints(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_constraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(326);
				match(T__0);
				setState(327);
				match(T__2);
				setState(328);
				match(CONSTRAINTS);
				setState(329);
				conGD();
				setState(330);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructureDefContext extends ParserRuleContext {
		public ActionDefContext actionDef() {
			return getRuleContext(ActionDefContext.class, 0);
		}

		public DurativeActionDefContext durativeActionDef() {
			return getRuleContext(DurativeActionDefContext.class, 0);
		}

		public DerivedDefContext derivedDef() {
			return getRuleContext(DerivedDefContext.class, 0);
		}

		public MethodDefContext methodDef() {
			return getRuleContext(MethodDefContext.class, 0);
		}

		public StructureDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_structureDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterStructureDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitStructureDef(this);
		}
	}

	public final StructureDefContext structureDef() throws RecognitionException {
		StructureDefContext _localctx = new StructureDefContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_structureDef);
		try {
			setState(336);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(332);
				actionDef();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(333);
				durativeActionDef();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(334);
				derivedDef();
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(335);
				methodDef();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDefContext extends ParserRuleContext {
		public TerminalNode ACTION() {
			return getToken(PddlHtnParser.ACTION, 0);
		}

		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class, 0);
		}

		public TerminalNode PARAMETERS() {
			return getToken(PddlHtnParser.PARAMETERS, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public ActionDefBodyContext actionDefBody() {
			return getRuleContext(ActionDefBodyContext.class, 0);
		}

		public ActionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_actionDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterActionDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitActionDef(this);
		}
	}

	public final ActionDefContext actionDef() throws RecognitionException {
		ActionDefContext _localctx = new ActionDefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_actionDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(338);
				match(T__0);
				setState(339);
				match(T__2);
				setState(340);
				match(ACTION);
				setState(341);
				actionSymbol();
				setState(342);
				match(T__2);
				setState(343);
				match(PARAMETERS);
				setState(344);
				match(T__0);
				setState(345);
				typedVariableList();
				setState(346);
				match(T__1);
				setState(347);
				actionDefBody();
				setState(348);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionSymbolContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public ActionSymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_actionSymbol;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterActionSymbol(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitActionSymbol(this);
		}
	}

	public final ActionSymbolContext actionSymbol() throws RecognitionException {
		ActionSymbolContext _localctx = new ActionSymbolContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_actionSymbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(350);
				match(NAME);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDefBodyContext extends ParserRuleContext {
		public TerminalNode PRECONDITION() {
			return getToken(PddlHtnParser.PRECONDITION, 0);
		}

		public TerminalNode EFFECT() {
			return getToken(PddlHtnParser.EFFECT, 0);
		}

		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class, 0);
		}

		public EffectContext effect() {
			return getRuleContext(EffectContext.class, 0);
		}

		public ActionDefBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_actionDefBody;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterActionDefBody(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitActionDefBody(this);
		}
	}

	public final ActionDefBodyContext actionDefBody() throws RecognitionException {
		ActionDefBodyContext _localctx = new ActionDefBodyContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_actionDefBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(359);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
				case 1: {
					setState(352);
					match(T__2);
					setState(353);
					match(PRECONDITION);
					setState(357);
					_errHandler.sync(this);
					switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
					case 1: {
						{
							setState(354);
							match(T__0);
							setState(355);
							match(T__1);
						}
					}
						break;
					case 2: {
						setState(356);
						goalDesc();
					}
						break;
					}
				}
					break;
				}
				setState(368);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__2) {
					{
						setState(361);
						match(T__2);
						setState(362);
						match(EFFECT);
						setState(366);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
						case 1: {
							{
								setState(363);
								match(T__0);
								setState(364);
								match(T__1);
							}
						}
							break;
						case 2: {
							setState(365);
							effect();
						}
							break;
						}
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalDescContext extends ParserRuleContext {
		public AtomicTermFormulaContext atomicTermFormula() {
			return getRuleContext(AtomicTermFormulaContext.class, 0);
		}

		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}

		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class, i);
		}

		public TerminalNode OR() {
			return getToken(PddlHtnParser.OR, 0);
		}

		public TerminalNode NOT() {
			return getToken(PddlHtnParser.NOT, 0);
		}

		public TerminalNode IMPLY() {
			return getToken(PddlHtnParser.IMPLY, 0);
		}

		public TerminalNode EXISTS() {
			return getToken(PddlHtnParser.EXISTS, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public FCompContext fComp() {
			return getRuleContext(FCompContext.class, 0);
		}

		public TerminalNode EQUALS() {
			return getToken(PddlHtnParser.EQUALS, 0);
		}

		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}

		public TermContext term(int i) {
			return getRuleContext(TermContext.class, i);
		}

		public GoalDescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_goalDesc;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterGoalDesc(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitGoalDesc(this);
		}
	}

	public final GoalDescContext goalDesc() throws RecognitionException {
		GoalDescContext _localctx = new GoalDescContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_goalDesc);
		int _la;
		try {
			setState(423);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 32, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(370);
				atomicTermFormula();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(371);
				match(T__0);
				setState(372);
				match(AND);
				setState(376);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(373);
							goalDesc();
						}
					}
					setState(378);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(379);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(380);
				match(T__0);
				setState(381);
				match(OR);
				setState(385);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(382);
							goalDesc();
						}
					}
					setState(387);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(388);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(389);
				match(T__0);
				setState(390);
				match(NOT);
				setState(391);
				goalDesc();
				setState(392);
				match(T__1);
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5); {
				setState(394);
				match(T__0);
				setState(395);
				match(IMPLY);
				setState(396);
				goalDesc();
				setState(397);
				goalDesc();
				setState(398);
				match(T__1);
			}
				break;
			case 6:
				enterOuterAlt(_localctx, 6); {
				setState(400);
				match(T__0);
				setState(401);
				match(EXISTS);
				setState(402);
				match(T__0);
				setState(403);
				typedVariableList();
				setState(404);
				match(T__1);
				setState(405);
				goalDesc();
				setState(406);
				match(T__1);
			}
				break;
			case 7:
				enterOuterAlt(_localctx, 7); {
				setState(408);
				match(T__0);
				setState(409);
				match(FORALL);
				setState(410);
				match(T__0);
				setState(411);
				typedVariableList();
				setState(412);
				match(T__1);
				setState(413);
				goalDesc();
				setState(414);
				match(T__1);
			}
				break;
			case 8:
				enterOuterAlt(_localctx, 8); {
				setState(416);
				fComp();
			}
				break;
			case 9:
				enterOuterAlt(_localctx, 9); {
				setState(417);
				match(T__0);
				setState(418);
				match(EQUALS);
				setState(419);
				term();
				setState(420);
				term();
				setState(421);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FCompContext extends ParserRuleContext {
		public BinaryCompContext binaryComp() {
			return getRuleContext(BinaryCompContext.class, 0);
		}

		public List<FExpContext> fExp() {
			return getRuleContexts(FExpContext.class);
		}

		public FExpContext fExp(int i) {
			return getRuleContext(FExpContext.class, i);
		}

		public FCompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_fComp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFComp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFComp(this);
		}
	}

	public final FCompContext fComp() throws RecognitionException {
		FCompContext _localctx = new FCompContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_fComp);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(425);
				match(T__0);
				setState(426);
				binaryComp();
				setState(427);
				fExp();
				setState(428);
				fExp();
				setState(429);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicTermFormulaContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class, 0);
		}

		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}

		public TermContext term(int i) {
			return getRuleContext(TermContext.class, i);
		}

		public AtomicTermFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_atomicTermFormula;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterAtomicTermFormula(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitAtomicTermFormula(this);
		}
	}

	public final AtomicTermFormulaContext atomicTermFormula() throws RecognitionException {
		AtomicTermFormulaContext _localctx = new AtomicTermFormulaContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_atomicTermFormula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(431);
				match(T__0);
				setState(432);
				predicate();
				setState(436);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == NAME || _la == VARIABLE) {
					{
						{
							setState(433);
							term();
						}
					}
					setState(438);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(439);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public TerminalNode VARIABLE() {
			return getToken(PddlHtnParser.VARIABLE, 0);
		}

		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_term;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTerm(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(441);
				_la = _input.LA(1);
				if (!(_la == NAME || _la == VARIABLE)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDefContext extends ParserRuleContext {
		public TerminalNode METHOD() {
			return getToken(PddlHtnParser.METHOD, 0);
		}

		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class, 0);
		}

		public TerminalNode PARAMETERS() {
			return getToken(PddlHtnParser.PARAMETERS, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public TerminalNode EXPANSION() {
			return getToken(PddlHtnParser.EXPANSION, 0);
		}

		public TaskListContext taskList() {
			return getRuleContext(TaskListContext.class, 0);
		}

		public TerminalNode CONSTRAINTS() {
			return getToken(PddlHtnParser.CONSTRAINTS, 0);
		}

		public ConstraintListContext constraintList() {
			return getRuleContext(ConstraintListContext.class, 0);
		}

		public MethodDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_methodDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterMethodDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitMethodDef(this);
		}
	}

	public final MethodDefContext methodDef() throws RecognitionException {
		MethodDefContext _localctx = new MethodDefContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_methodDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(443);
				match(T__0);
				setState(444);
				match(T__2);
				setState(445);
				match(METHOD);
				setState(446);
				actionSymbol();
				setState(447);
				match(T__2);
				setState(448);
				match(PARAMETERS);
				setState(449);
				match(T__0);
				setState(450);
				typedVariableList();
				setState(451);
				match(T__1);
				setState(452);
				match(T__2);
				setState(453);
				match(EXPANSION);
				setState(454);
				match(T__0);
				setState(455);
				taskList();
				setState(456);
				match(T__1);
				setState(457);
				match(T__2);
				setState(458);
				match(CONSTRAINTS);
				setState(459);
				match(T__0);
				setState(460);
				constraintList();
				setState(461);
				match(T__1);
				setState(462);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TaskListContext extends ParserRuleContext {
		public List<TaskContext> task() {
			return getRuleContexts(TaskContext.class);
		}

		public TaskContext task(int i) {
			return getRuleContext(TaskContext.class, i);
		}

		public List<TaggedTaskContext> taggedTask() {
			return getRuleContexts(TaggedTaskContext.class);
		}

		public TaggedTaskContext taggedTask(int i) {
			return getRuleContext(TaggedTaskContext.class, i);
		}

		public TaskListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_taskList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTaskList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTaskList(this);
		}
	}

	public final TaskListContext taskList() throws RecognitionException {
		TaskListContext _localctx = new TaskListContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_taskList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(468);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						setState(466);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 34, _ctx)) {
						case 1: {
							setState(464);
							task();
						}
							break;
						case 2: {
							setState(465);
							taggedTask();
						}
							break;
						}
					}
					setState(470);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TaskContext extends ParserRuleContext {
		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}

		public TermContext term(int i) {
			return getRuleContext(TermContext.class, i);
		}

		public TaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_task;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTask(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTask(this);
		}
	}

	public final TaskContext task() throws RecognitionException {
		TaskContext _localctx = new TaskContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_task);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(471);
				match(T__0);
				setState(472);
				match(NAME);
				setState(476);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == NAME || _la == VARIABLE) {
					{
						{
							setState(473);
							term();
						}
					}
					setState(478);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(479);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TaggedTaskContext extends ParserRuleContext {
		public TerminalNode TAG() {
			return getToken(PddlHtnParser.TAG, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public TaskContext task() {
			return getRuleContext(TaskContext.class, 0);
		}

		public TaggedTaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_taggedTask;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTaggedTask(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTaggedTask(this);
		}
	}

	public final TaggedTaskContext taggedTask() throws RecognitionException {
		TaggedTaskContext _localctx = new TaggedTaskContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_taggedTask);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(481);
				match(T__0);
				setState(482);
				match(TAG);
				setState(483);
				match(NAME);
				setState(484);
				task();
				setState(485);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintListContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}

		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class, i);
		}

		public ConstraintListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constraintList;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterConstraintList(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitConstraintList(this);
		}
	}

	public final ConstraintListContext constraintList() throws RecognitionException {
		ConstraintListContext _localctx = new ConstraintListContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_constraintList);
		int _la;
		try {
			setState(500);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1); {
				{
					setState(487);
					match(AND);
					setState(491);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == T__0) {
						{
							{
								setState(488);
								constraint();
							}
						}
						setState(493);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
			}
				break;
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 2); {
				{
					setState(497);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la == T__0) {
						{
							{
								setState(494);
								constraint();
							}
						}
						setState(499);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
				}
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class, 0);
		}

		public List<TerminalNode> NAME() {
			return getTokens(PddlHtnParser.NAME);
		}

		public TerminalNode NAME(int i) {
			return getToken(PddlHtnParser.NAME, i);
		}

		public TerminalNode BETWEEN() {
			return getToken(PddlHtnParser.BETWEEN, 0);
		}

		public TerminalNode BEFORE() {
			return getToken(PddlHtnParser.BEFORE, 0);
		}

		public TerminalNode AFTER() {
			return getToken(PddlHtnParser.AFTER, 0);
		}

		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_constraint;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitConstraint(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_constraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(502);
				match(T__0);
				setState(512);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case BEFORE:
				case AFTER: {
					{
						setState(503);
						_la = _input.LA(1);
						if (!(_la == BEFORE || _la == AFTER)) {
							_errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF)
								matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(504);
						goalDesc();
						setState(505);
						match(NAME);
					}
				}
					break;
				case BETWEEN: {
					{
						setState(507);
						match(BETWEEN);
						setState(508);
						goalDesc();
						setState(509);
						match(NAME);
						setState(510);
						match(NAME);
					}
				}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(514);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurativeActionDefContext extends ParserRuleContext {
		public TerminalNode DURATIVE_ACTION() {
			return getToken(PddlHtnParser.DURATIVE_ACTION, 0);
		}

		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class, 0);
		}

		public TerminalNode PARAMETERS() {
			return getToken(PddlHtnParser.PARAMETERS, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public DaDefBodyContext daDefBody() {
			return getRuleContext(DaDefBodyContext.class, 0);
		}

		public DurativeActionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_durativeActionDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDurativeActionDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDurativeActionDef(this);
		}
	}

	public final DurativeActionDefContext durativeActionDef() throws RecognitionException {
		DurativeActionDefContext _localctx = new DurativeActionDefContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_durativeActionDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(516);
				match(T__0);
				setState(517);
				match(T__2);
				setState(518);
				match(DURATIVE_ACTION);
				setState(519);
				actionSymbol();
				setState(520);
				match(T__2);
				setState(521);
				match(PARAMETERS);
				setState(522);
				match(T__0);
				setState(523);
				typedVariableList();
				setState(524);
				match(T__1);
				setState(525);
				daDefBody();
				setState(526);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaDefBodyContext extends ParserRuleContext {
		public TerminalNode DURATION() {
			return getToken(PddlHtnParser.DURATION, 0);
		}

		public DurationConstraintContext durationConstraint() {
			return getRuleContext(DurationConstraintContext.class, 0);
		}

		public TerminalNode CONDITION() {
			return getToken(PddlHtnParser.CONDITION, 0);
		}

		public DaGDContext daGD() {
			return getRuleContext(DaGDContext.class, 0);
		}

		public TerminalNode EFFECT() {
			return getToken(PddlHtnParser.EFFECT, 0);
		}

		public DaEffectContext daEffect() {
			return getRuleContext(DaEffectContext.class, 0);
		}

		public DaDefBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_daDefBody;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDaDefBody(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDaDefBody(this);
		}
	}

	public final DaDefBodyContext daDefBody() throws RecognitionException {
		DaDefBodyContext _localctx = new DaDefBodyContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_daDefBody);
		try {
			setState(545);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 43, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(528);
				match(T__2);
				setState(529);
				match(DURATION);
				setState(530);
				durationConstraint();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(531);
				match(T__2);
				setState(532);
				match(CONDITION);
				setState(536);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
				case 1: {
					{
						setState(533);
						match(T__0);
						setState(534);
						match(T__1);
					}
				}
					break;
				case 2: {
					setState(535);
					daGD();
				}
					break;
				}
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(538);
				match(T__2);
				setState(539);
				match(EFFECT);
				setState(543);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 42, _ctx)) {
				case 1: {
					{
						setState(540);
						match(T__0);
						setState(541);
						match(T__1);
					}
				}
					break;
				case 2: {
					setState(542);
					daEffect();
				}
					break;
				}
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaGDContext extends ParserRuleContext {
		public PrefTimedGDContext prefTimedGD() {
			return getRuleContext(PrefTimedGDContext.class, 0);
		}

		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<DaGDContext> daGD() {
			return getRuleContexts(DaGDContext.class);
		}

		public DaGDContext daGD(int i) {
			return getRuleContext(DaGDContext.class, i);
		}

		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public DaGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_daGD;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDaGD(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDaGD(this);
		}
	}

	public final DaGDContext daGD() throws RecognitionException {
		DaGDContext _localctx = new DaGDContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_daGD);
		int _la;
		try {
			setState(565);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 45, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(547);
				prefTimedGD();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(548);
				match(T__0);
				setState(549);
				match(AND);
				setState(553);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(550);
							daGD();
						}
					}
					setState(555);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(556);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(557);
				match(T__0);
				setState(558);
				match(FORALL);
				setState(559);
				match(T__0);
				setState(560);
				typedVariableList();
				setState(561);
				match(T__1);
				setState(562);
				daGD();
				setState(563);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefTimedGDContext extends ParserRuleContext {
		public TimedGDContext timedGD() {
			return getRuleContext(TimedGDContext.class, 0);
		}

		public TerminalNode PREFERENCE() {
			return getToken(PddlHtnParser.PREFERENCE, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public PrefTimedGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_prefTimedGD;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPrefTimedGD(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPrefTimedGD(this);
		}
	}

	public final PrefTimedGDContext prefTimedGD() throws RecognitionException {
		PrefTimedGDContext _localctx = new PrefTimedGDContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_prefTimedGD);
		int _la;
		try {
			setState(576);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 47, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(567);
				timedGD();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(568);
				match(T__0);
				setState(569);
				match(PREFERENCE);
				setState(571);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == NAME) {
					{
						setState(570);
						match(NAME);
					}
				}

				setState(573);
				timedGD();
				setState(574);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimedGDContext extends ParserRuleContext {
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class, 0);
		}

		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class, 0);
		}

		public TerminalNode OVER_ALL() {
			return getToken(PddlHtnParser.OVER_ALL, 0);
		}

		public TimedGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_timedGD;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTimedGD(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTimedGD(this);
		}
	}

	public final TimedGDContext timedGD() throws RecognitionException {
		TimedGDContext _localctx = new TimedGDContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_timedGD);
		try {
			setState(588);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 48, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(578);
				match(T__0);
				setState(579);
				timeSpecifier();
				setState(580);
				goalDesc();
				setState(581);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(583);
				match(T__0);
				setState(584);
				match(OVER_ALL);
				setState(585);
				goalDesc();
				setState(586);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimeSpecifierContext extends ParserRuleContext {
		public TerminalNode AT_START() {
			return getToken(PddlHtnParser.AT_START, 0);
		}

		public TerminalNode AT_END() {
			return getToken(PddlHtnParser.AT_END, 0);
		}

		public TimeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_timeSpecifier;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTimeSpecifier(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTimeSpecifier(this);
		}
	}

	public final TimeSpecifierContext timeSpecifier() throws RecognitionException {
		TimeSpecifierContext _localctx = new TimeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_timeSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(590);
				_la = _input.LA(1);
				if (!(_la == AT_START || _la == AT_END)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalContext extends ParserRuleContext {
		public TerminalNode OVER_ALL() {
			return getToken(PddlHtnParser.OVER_ALL, 0);
		}

		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_interval;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterInterval(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitInterval(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_interval);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(592);
				match(OVER_ALL);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DerivedDefContext extends ParserRuleContext {
		public TerminalNode DERIVED() {
			return getToken(PddlHtnParser.DERIVED, 0);
		}

		public AtomicFormulaSkeletonContext atomicFormulaSkeleton() {
			return getRuleContext(AtomicFormulaSkeletonContext.class, 0);
		}

		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class, 0);
		}

		public DerivedDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_derivedDef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDerivedDef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDerivedDef(this);
		}
	}

	public final DerivedDefContext derivedDef() throws RecognitionException {
		DerivedDefContext _localctx = new DerivedDefContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_derivedDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(594);
				match(T__0);
				setState(595);
				match(T__2);
				setState(596);
				match(DERIVED);
				setState(597);
				atomicFormulaSkeleton();
				setState(598);
				goalDesc();
				setState(599);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FExpContext extends ParserRuleContext {
		public TerminalNode NUMBER() {
			return getToken(PddlHtnParser.NUMBER, 0);
		}

		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class, 0);
		}

		public List<FExpContext> fExp() {
			return getRuleContexts(FExpContext.class);
		}

		public FExpContext fExp(int i) {
			return getRuleContext(FExpContext.class, i);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public FExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_fExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFExp(this);
		}
	}

	public final FExpContext fExp() throws RecognitionException {
		FExpContext _localctx = new FExpContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_fExp);
		try {
			setState(614);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 49, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(601);
				match(NUMBER);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(602);
				match(T__0);
				setState(603);
				binaryOp();
				setState(604);
				fExp();
				setState(605);
				fExp();
				setState(606);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(608);
				match(T__0);
				setState(609);
				match(T__3);
				setState(610);
				fExp();
				setState(611);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(613);
				fHead();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FHeadContext extends ParserRuleContext {
		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class, 0);
		}

		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}

		public TermContext term(int i) {
			return getRuleContext(TermContext.class, i);
		}

		public FHeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_fHead;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFHead(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFHead(this);
		}
	}

	public final FHeadContext fHead() throws RecognitionException {
		FHeadContext _localctx = new FHeadContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_fHead);
		int _la;
		try {
			setState(627);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1); {
				setState(616);
				match(T__0);
				setState(617);
				functionSymbol();
				setState(621);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == NAME || _la == VARIABLE) {
					{
						{
							setState(618);
							term();
						}
					}
					setState(623);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(624);
				match(T__1);
			}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2); {
				setState(626);
				functionSymbol();
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EffectContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<CEffectContext> cEffect() {
			return getRuleContexts(CEffectContext.class);
		}

		public CEffectContext cEffect(int i) {
			return getRuleContext(CEffectContext.class, i);
		}

		public EffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_effect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitEffect(this);
		}
	}

	public final EffectContext effect() throws RecognitionException {
		EffectContext _localctx = new EffectContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_effect);
		int _la;
		try {
			setState(639);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 53, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(629);
				match(T__0);
				setState(630);
				match(AND);
				setState(634);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(631);
							cEffect();
						}
					}
					setState(636);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(637);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(638);
				cEffect();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CEffectContext extends ParserRuleContext {
		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public EffectContext effect() {
			return getRuleContext(EffectContext.class, 0);
		}

		public TerminalNode WHEN() {
			return getToken(PddlHtnParser.WHEN, 0);
		}

		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class, 0);
		}

		public CondEffectContext condEffect() {
			return getRuleContext(CondEffectContext.class, 0);
		}

		public PEffectContext pEffect() {
			return getRuleContext(PEffectContext.class, 0);
		}

		public CEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_cEffect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterCEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitCEffect(this);
		}
	}

	public final CEffectContext cEffect() throws RecognitionException {
		CEffectContext _localctx = new CEffectContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_cEffect);
		try {
			setState(656);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 54, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(641);
				match(T__0);
				setState(642);
				match(FORALL);
				setState(643);
				match(T__0);
				setState(644);
				typedVariableList();
				setState(645);
				match(T__1);
				setState(646);
				effect();
				setState(647);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(649);
				match(T__0);
				setState(650);
				match(WHEN);
				setState(651);
				goalDesc();
				setState(652);
				condEffect();
				setState(653);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(655);
				pEffect();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PEffectContext extends ParserRuleContext {
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class, 0);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public FExpContext fExp() {
			return getRuleContext(FExpContext.class, 0);
		}

		public TerminalNode NOT() {
			return getToken(PddlHtnParser.NOT, 0);
		}

		public AtomicTermFormulaContext atomicTermFormula() {
			return getRuleContext(AtomicTermFormulaContext.class, 0);
		}

		public PEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_pEffect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPEffect(this);
		}
	}

	public final PEffectContext pEffect() throws RecognitionException {
		PEffectContext _localctx = new PEffectContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_pEffect);
		try {
			setState(670);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 55, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(658);
				match(T__0);
				setState(659);
				assignOp();
				setState(660);
				fHead();
				setState(661);
				fExp();
				setState(662);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(664);
				match(T__0);
				setState(665);
				match(NOT);
				setState(666);
				atomicTermFormula();
				setState(667);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(669);
				atomicTermFormula();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondEffectContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<PEffectContext> pEffect() {
			return getRuleContexts(PEffectContext.class);
		}

		public PEffectContext pEffect(int i) {
			return getRuleContext(PEffectContext.class, i);
		}

		public CondEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_condEffect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterCondEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitCondEffect(this);
		}
	}

	public final CondEffectContext condEffect() throws RecognitionException {
		CondEffectContext _localctx = new CondEffectContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_condEffect);
		int _la;
		try {
			setState(682);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 57, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(672);
				match(T__0);
				setState(673);
				match(AND);
				setState(677);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(674);
							pEffect();
						}
					}
					setState(679);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(680);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(681);
				pEffect();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryOpContext extends ParserRuleContext {
		public BinaryOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_binaryOp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterBinaryOp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitBinaryOp(this);
		}
	}

	public final BinaryOpContext binaryOp() throws RecognitionException {
		BinaryOpContext _localctx = new BinaryOpContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_binaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(684);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0
						&& ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryCompContext extends ParserRuleContext {
		public BinaryCompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_binaryComp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterBinaryComp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitBinaryComp(this);
		}
	}

	public final BinaryCompContext binaryComp() throws RecognitionException {
		BinaryCompContext _localctx = new BinaryCompContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_binaryComp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(686);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0
						&& ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)
						|| _la == EQUALS)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignOpContext extends ParserRuleContext {
		public TerminalNode ASSIGN() {
			return getToken(PddlHtnParser.ASSIGN, 0);
		}

		public TerminalNode SCALE_UP() {
			return getToken(PddlHtnParser.SCALE_UP, 0);
		}

		public TerminalNode SCALE_DOWN() {
			return getToken(PddlHtnParser.SCALE_DOWN, 0);
		}

		public TerminalNode INCREASE() {
			return getToken(PddlHtnParser.INCREASE, 0);
		}

		public TerminalNode DECREASE() {
			return getToken(PddlHtnParser.DECREASE, 0);
		}

		public AssignOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_assignOp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterAssignOp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitAssignOp(this);
		}
	}

	public final AssignOpContext assignOp() throws RecognitionException {
		AssignOpContext _localctx = new AssignOpContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_assignOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(688);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << INCREASE) | (1L << DECREASE)
						| (1L << SCALE_UP) | (1L << SCALE_DOWN))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurationConstraintContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<SimpleDurationConstraintContext> simpleDurationConstraint() {
			return getRuleContexts(SimpleDurationConstraintContext.class);
		}

		public SimpleDurationConstraintContext simpleDurationConstraint(int i) {
			return getRuleContext(SimpleDurationConstraintContext.class, i);
		}

		public DurationConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_durationConstraint;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDurationConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDurationConstraint(this);
		}
	}

	public final DurationConstraintContext durationConstraint() throws RecognitionException {
		DurationConstraintContext _localctx = new DurationConstraintContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_durationConstraint);
		int _la;
		try {
			setState(702);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 59, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(690);
				match(T__0);
				setState(691);
				match(AND);
				setState(693);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(692);
							simpleDurationConstraint();
						}
					}
					setState(695);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == T__0);
				setState(697);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(699);
				match(T__0);
				setState(700);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(701);
				simpleDurationConstraint();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleDurationConstraintContext extends ParserRuleContext {
		public DurOpContext durOp() {
			return getRuleContext(DurOpContext.class, 0);
		}

		public TerminalNode DURATION() {
			return getToken(PddlHtnParser.DURATION, 0);
		}

		public DurValueContext durValue() {
			return getRuleContext(DurValueContext.class, 0);
		}

		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class, 0);
		}

		public SimpleDurationConstraintContext simpleDurationConstraint() {
			return getRuleContext(SimpleDurationConstraintContext.class, 0);
		}

		public SimpleDurationConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_simpleDurationConstraint;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterSimpleDurationConstraint(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitSimpleDurationConstraint(this);
		}
	}

	public final SimpleDurationConstraintContext simpleDurationConstraint() throws RecognitionException {
		SimpleDurationConstraintContext _localctx = new SimpleDurationConstraintContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_simpleDurationConstraint);
		try {
			setState(716);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 60, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(704);
				match(T__0);
				setState(705);
				durOp();
				setState(706);
				match(T__11);
				setState(707);
				match(DURATION);
				setState(708);
				durValue();
				setState(709);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(711);
				match(T__0);
				setState(712);
				timeSpecifier();
				setState(713);
				simpleDurationConstraint();
				setState(714);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurOpContext extends ParserRuleContext {
		public DurOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_durOp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDurOp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDurOp(this);
		}
	}

	public final DurOpContext durOp() throws RecognitionException {
		DurOpContext _localctx = new DurOpContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_durOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(718);
				_la = _input.LA(1);
				if (!(_la == T__9 || _la == T__10 || _la == EQUALS)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() {
			return getToken(PddlHtnParser.NUMBER, 0);
		}

		public FExpContext fExp() {
			return getRuleContext(FExpContext.class, 0);
		}

		public DurValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_durValue;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDurValue(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDurValue(this);
		}
	}

	public final DurValueContext durValue() throws RecognitionException {
		DurValueContext _localctx = new DurValueContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_durValue);
		try {
			setState(722);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 61, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(720);
				match(NUMBER);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(721);
				fExp();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaEffectContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<DaEffectContext> daEffect() {
			return getRuleContexts(DaEffectContext.class);
		}

		public DaEffectContext daEffect(int i) {
			return getRuleContext(DaEffectContext.class, i);
		}

		public TimedEffectContext timedEffect() {
			return getRuleContext(TimedEffectContext.class, 0);
		}

		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public TerminalNode WHEN() {
			return getToken(PddlHtnParser.WHEN, 0);
		}

		public DaGDContext daGD() {
			return getRuleContext(DaGDContext.class, 0);
		}

		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class, 0);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public FExpDAContext fExpDA() {
			return getRuleContext(FExpDAContext.class, 0);
		}

		public DaEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_daEffect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterDaEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitDaEffect(this);
		}
	}

	public final DaEffectContext daEffect() throws RecognitionException {
		DaEffectContext _localctx = new DaEffectContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_daEffect);
		int _la;
		try {
			setState(754);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 63, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(724);
				match(T__0);
				setState(725);
				match(AND);
				setState(729);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(726);
							daEffect();
						}
					}
					setState(731);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(732);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(733);
				timedEffect();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(734);
				match(T__0);
				setState(735);
				match(FORALL);
				setState(736);
				match(T__0);
				setState(737);
				typedVariableList();
				setState(738);
				match(T__1);
				setState(739);
				daEffect();
				setState(740);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(742);
				match(T__0);
				setState(743);
				match(WHEN);
				setState(744);
				daGD();
				setState(745);
				timedEffect();
				setState(746);
				match(T__1);
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5); {
				setState(748);
				match(T__0);
				setState(749);
				assignOp();
				setState(750);
				fHead();
				setState(751);
				fExpDA();
				setState(752);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimedEffectContext extends ParserRuleContext {
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class, 0);
		}

		public DaEffectContext daEffect() {
			return getRuleContext(DaEffectContext.class, 0);
		}

		public FAssignDAContext fAssignDA() {
			return getRuleContext(FAssignDAContext.class, 0);
		}

		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class, 0);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public FExpContext fExp() {
			return getRuleContext(FExpContext.class, 0);
		}

		public TimedEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_timedEffect;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterTimedEffect(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitTimedEffect(this);
		}
	}

	public final TimedEffectContext timedEffect() throws RecognitionException {
		TimedEffectContext _localctx = new TimedEffectContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_timedEffect);
		try {
			setState(772);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 64, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(756);
				match(T__0);
				setState(757);
				timeSpecifier();
				setState(758);
				daEffect();
				setState(759);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(761);
				match(T__0);
				setState(762);
				timeSpecifier();
				setState(763);
				fAssignDA();
				setState(764);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(766);
				match(T__0);
				setState(767);
				assignOp();
				setState(768);
				fHead();
				setState(769);
				fExp();
				setState(770);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FAssignDAContext extends ParserRuleContext {
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class, 0);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public FExpDAContext fExpDA() {
			return getRuleContext(FExpDAContext.class, 0);
		}

		public FAssignDAContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_fAssignDA;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFAssignDA(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFAssignDA(this);
		}
	}

	public final FAssignDAContext fAssignDA() throws RecognitionException {
		FAssignDAContext _localctx = new FAssignDAContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_fAssignDA);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(774);
				match(T__0);
				setState(775);
				assignOp();
				setState(776);
				fHead();
				setState(777);
				fExpDA();
				setState(778);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FExpDAContext extends ParserRuleContext {
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class, 0);
		}

		public List<FExpDAContext> fExpDA() {
			return getRuleContexts(FExpDAContext.class);
		}

		public FExpDAContext fExpDA(int i) {
			return getRuleContext(FExpDAContext.class, i);
		}

		public TerminalNode DURATION() {
			return getToken(PddlHtnParser.DURATION, 0);
		}

		public FExpContext fExp() {
			return getRuleContext(FExpContext.class, 0);
		}

		public FExpDAContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_fExpDA;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterFExpDA(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitFExpDA(this);
		}
	}

	public final FExpDAContext fExpDA() throws RecognitionException {
		FExpDAContext _localctx = new FExpDAContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_fExpDA);
		try {
			setState(794);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 66, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(780);
				match(T__0);
				setState(787);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 65, _ctx)) {
				case 1: {
					{
						setState(781);
						binaryOp();
						setState(782);
						fExpDA();
						setState(783);
						fExpDA();
					}
				}
					break;
				case 2: {
					{
						setState(785);
						match(T__3);
						setState(786);
						fExpDA();
					}
				}
					break;
				}
				setState(789);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(791);
				match(T__11);
				setState(792);
				match(DURATION);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(793);
				fExp();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemContext extends ParserRuleContext {
		public TerminalNode DEFINE() {
			return getToken(PddlHtnParser.DEFINE, 0);
		}

		public ProblemDeclContext problemDecl() {
			return getRuleContext(ProblemDeclContext.class, 0);
		}

		public ProblemDomainContext problemDomain() {
			return getRuleContext(ProblemDomainContext.class, 0);
		}

		public InitContext init() {
			return getRuleContext(InitContext.class, 0);
		}

		public GoalContext goal() {
			return getRuleContext(GoalContext.class, 0);
		}

		public RequireDefContext requireDef() {
			return getRuleContext(RequireDefContext.class, 0);
		}

		public ObjectDeclContext objectDecl() {
			return getRuleContext(ObjectDeclContext.class, 0);
		}

		public ProbConstraintsContext probConstraints() {
			return getRuleContext(ProbConstraintsContext.class, 0);
		}

		public MetricSpecContext metricSpec() {
			return getRuleContext(MetricSpecContext.class, 0);
		}

		public ProblemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_problem;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterProblem(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitProblem(this);
		}
	}

	public final ProblemContext problem() throws RecognitionException {
		ProblemContext _localctx = new ProblemContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_problem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(796);
				match(T__0);
				setState(797);
				match(DEFINE);
				setState(798);
				problemDecl();
				setState(799);
				problemDomain();
				setState(801);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 67, _ctx)) {
				case 1: {
					setState(800);
					requireDef();
				}
					break;
				}
				setState(804);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 68, _ctx)) {
				case 1: {
					setState(803);
					objectDecl();
				}
					break;
				}
				setState(806);
				init();
				setState(807);
				goal();
				setState(809);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 69, _ctx)) {
				case 1: {
					setState(808);
					probConstraints();
				}
					break;
				}
				setState(812);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__0) {
					{
						setState(811);
						metricSpec();
					}
				}

				setState(814);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemDeclContext extends ParserRuleContext {
		public TerminalNode PROBLEM() {
			return getToken(PddlHtnParser.PROBLEM, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public ProblemDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_problemDecl;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterProblemDecl(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitProblemDecl(this);
		}
	}

	public final ProblemDeclContext problemDecl() throws RecognitionException {
		ProblemDeclContext _localctx = new ProblemDeclContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_problemDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(816);
				match(T__0);
				setState(817);
				match(PROBLEM);
				setState(818);
				match(NAME);
				setState(819);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemDomainContext extends ParserRuleContext {
		public TerminalNode DOMAIN() {
			return getToken(PddlHtnParser.DOMAIN, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public ProblemDomainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_problemDomain;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterProblemDomain(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitProblemDomain(this);
		}
	}

	public final ProblemDomainContext problemDomain() throws RecognitionException {
		ProblemDomainContext _localctx = new ProblemDomainContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_problemDomain);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(821);
				match(T__0);
				setState(822);
				match(T__2);
				setState(823);
				match(DOMAIN);
				setState(824);
				match(NAME);
				setState(825);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectDeclContext extends ParserRuleContext {
		public TerminalNode OBJECTS() {
			return getToken(PddlHtnParser.OBJECTS, 0);
		}

		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class, 0);
		}

		public ObjectDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_objectDecl;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterObjectDecl(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitObjectDecl(this);
		}
	}

	public final ObjectDeclContext objectDecl() throws RecognitionException {
		ObjectDeclContext _localctx = new ObjectDeclContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_objectDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(827);
				match(T__0);
				setState(828);
				match(T__2);
				setState(829);
				match(OBJECTS);
				setState(830);
				typedNameList();
				setState(831);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitContext extends ParserRuleContext {
		public TerminalNode INIT() {
			return getToken(PddlHtnParser.INIT, 0);
		}

		public List<InitElContext> initEl() {
			return getRuleContexts(InitElContext.class);
		}

		public InitElContext initEl(int i) {
			return getRuleContext(InitElContext.class, i);
		}

		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_init;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterInit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitInit(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(833);
				match(T__0);
				setState(834);
				match(T__2);
				setState(835);
				match(INIT);
				setState(839);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(836);
							initEl();
						}
					}
					setState(841);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(842);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitElContext extends ParserRuleContext {
		public NameLiteralContext nameLiteral() {
			return getRuleContext(NameLiteralContext.class, 0);
		}

		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class, 0);
		}

		public TerminalNode NUMBER() {
			return getToken(PddlHtnParser.NUMBER, 0);
		}

		public InitElContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_initEl;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterInitEl(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitInitEl(this);
		}
	}

	public final InitElContext initEl() throws RecognitionException {
		InitElContext _localctx = new InitElContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_initEl);
		try {
			setState(856);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 72, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(844);
				nameLiteral();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(845);
				match(T__0);
				setState(846);
				match(EQUALS);
				setState(847);
				fHead();
				setState(848);
				match(NUMBER);
				setState(849);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(851);
				match(T__0);
				setState(852);
				match(NUMBER);
				setState(853);
				nameLiteral();
				setState(854);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameLiteralContext extends ParserRuleContext {
		public AtomicNameFormulaContext atomicNameFormula() {
			return getRuleContext(AtomicNameFormulaContext.class, 0);
		}

		public TerminalNode NOT() {
			return getToken(PddlHtnParser.NOT, 0);
		}

		public NameLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_nameLiteral;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterNameLiteral(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitNameLiteral(this);
		}
	}

	public final NameLiteralContext nameLiteral() throws RecognitionException {
		NameLiteralContext _localctx = new NameLiteralContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_nameLiteral);
		try {
			setState(864);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 73, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(858);
				atomicNameFormula();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(859);
				match(T__0);
				setState(860);
				match(NOT);
				setState(861);
				atomicNameFormula();
				setState(862);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicNameFormulaContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class, 0);
		}

		public List<TerminalNode> NAME() {
			return getTokens(PddlHtnParser.NAME);
		}

		public TerminalNode NAME(int i) {
			return getToken(PddlHtnParser.NAME, i);
		}

		public AtomicNameFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_atomicNameFormula;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterAtomicNameFormula(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitAtomicNameFormula(this);
		}
	}

	public final AtomicNameFormulaContext atomicNameFormula() throws RecognitionException {
		AtomicNameFormulaContext _localctx = new AtomicNameFormulaContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_atomicNameFormula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(866);
				match(T__0);
				setState(867);
				predicate();
				setState(871);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == NAME) {
					{
						{
							setState(868);
							match(NAME);
						}
					}
					setState(873);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(874);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalContext extends ParserRuleContext {
		public TerminalNode GOAL() {
			return getToken(PddlHtnParser.GOAL, 0);
		}

		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}

		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class, i);
		}

		public InitTaskNetworkContext initTaskNetwork() {
			return getRuleContext(InitTaskNetworkContext.class, 0);
		}

		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_goal;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterGoal(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitGoal(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_goal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(876);
				match(T__0);
				setState(877);
				match(T__2);
				setState(878);
				match(GOAL);
				setState(882);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(879);
							goalDesc();
						}
					}
					setState(884);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(886);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__2) {
					{
						setState(885);
						initTaskNetwork();
					}
				}

				setState(888);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitTaskNetworkContext extends ParserRuleContext {
		public TerminalNode TASKS() {
			return getToken(PddlHtnParser.TASKS, 0);
		}

		public TaskListContext taskList() {
			return getRuleContext(TaskListContext.class, 0);
		}

		public TerminalNode CONSTRAINTS() {
			return getToken(PddlHtnParser.CONSTRAINTS, 0);
		}

		public ConstraintListContext constraintList() {
			return getRuleContext(ConstraintListContext.class, 0);
		}

		public InitTaskNetworkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_initTaskNetwork;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterInitTaskNetwork(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitInitTaskNetwork(this);
		}
	}

	public final InitTaskNetworkContext initTaskNetwork() throws RecognitionException {
		InitTaskNetworkContext _localctx = new InitTaskNetworkContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_initTaskNetwork);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(890);
				match(T__2);
				setState(891);
				match(TASKS);
				setState(892);
				match(T__0);
				setState(893);
				taskList();
				setState(894);
				match(T__1);
				setState(901);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == T__2) {
					{
						setState(895);
						match(T__2);
						setState(896);
						match(CONSTRAINTS);
						setState(897);
						match(T__0);
						setState(898);
						constraintList();
						setState(899);
						match(T__1);
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProbConstraintsContext extends ParserRuleContext {
		public TerminalNode CONSTRAINTS() {
			return getToken(PddlHtnParser.CONSTRAINTS, 0);
		}

		public PrefConGDContext prefConGD() {
			return getRuleContext(PrefConGDContext.class, 0);
		}

		public ProbConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_probConstraints;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterProbConstraints(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitProbConstraints(this);
		}
	}

	public final ProbConstraintsContext probConstraints() throws RecognitionException {
		ProbConstraintsContext _localctx = new ProbConstraintsContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_probConstraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(903);
				match(T__0);
				setState(904);
				match(T__2);
				setState(905);
				match(CONSTRAINTS);
				setState(906);
				prefConGD();
				setState(907);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefConGDContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<PrefConGDContext> prefConGD() {
			return getRuleContexts(PrefConGDContext.class);
		}

		public PrefConGDContext prefConGD(int i) {
			return getRuleContext(PrefConGDContext.class, i);
		}

		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public TerminalNode PREFERENCE() {
			return getToken(PddlHtnParser.PREFERENCE, 0);
		}

		public ConGDContext conGD() {
			return getRuleContext(ConGDContext.class, 0);
		}

		public TerminalNode NAME() {
			return getToken(PddlHtnParser.NAME, 0);
		}

		public PrefConGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_prefConGD;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterPrefConGD(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitPrefConGD(this);
		}
	}

	public final PrefConGDContext prefConGD() throws RecognitionException {
		PrefConGDContext _localctx = new PrefConGDContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_prefConGD);
		int _la;
		try {
			setState(935);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 80, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(909);
				match(T__0);
				setState(910);
				match(AND);
				setState(914);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(911);
							prefConGD();
						}
					}
					setState(916);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(917);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(918);
				match(T__0);
				setState(919);
				match(FORALL);
				setState(920);
				match(T__0);
				setState(921);
				typedVariableList();
				setState(922);
				match(T__1);
				setState(923);
				prefConGD();
				setState(924);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(926);
				match(T__0);
				setState(927);
				match(PREFERENCE);
				setState(929);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == NAME) {
					{
						setState(928);
						match(NAME);
					}
				}

				setState(931);
				conGD();
				setState(932);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(934);
				conGD();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MetricSpecContext extends ParserRuleContext {
		public TerminalNode METRIC() {
			return getToken(PddlHtnParser.METRIC, 0);
		}

		public OptimizationContext optimization() {
			return getRuleContext(OptimizationContext.class, 0);
		}

		public MetricFExpContext metricFExp() {
			return getRuleContext(MetricFExpContext.class, 0);
		}

		public MetricSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_metricSpec;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterMetricSpec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitMetricSpec(this);
		}
	}

	public final MetricSpecContext metricSpec() throws RecognitionException {
		MetricSpecContext _localctx = new MetricSpecContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_metricSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(937);
				match(T__0);
				setState(938);
				match(T__2);
				setState(939);
				match(METRIC);
				setState(940);
				optimization();
				setState(941);
				metricFExp();
				setState(942);
				match(T__1);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptimizationContext extends ParserRuleContext {
		public TerminalNode MINIMIZE() {
			return getToken(PddlHtnParser.MINIMIZE, 0);
		}

		public TerminalNode MAXIMIZE() {
			return getToken(PddlHtnParser.MAXIMIZE, 0);
		}

		public OptimizationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_optimization;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterOptimization(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitOptimization(this);
		}
	}

	public final OptimizationContext optimization() throws RecognitionException {
		OptimizationContext _localctx = new OptimizationContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_optimization);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(944);
				_la = _input.LA(1);
				if (!(_la == MINIMIZE || _la == MAXIMIZE)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MetricFExpContext extends ParserRuleContext {
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class, 0);
		}

		public List<MetricFExpContext> metricFExp() {
			return getRuleContexts(MetricFExpContext.class);
		}

		public MetricFExpContext metricFExp(int i) {
			return getRuleContext(MetricFExpContext.class, i);
		}

		public TerminalNode NUMBER() {
			return getToken(PddlHtnParser.NUMBER, 0);
		}

		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class, 0);
		}

		public List<TerminalNode> NAME() {
			return getTokens(PddlHtnParser.NAME);
		}

		public TerminalNode NAME(int i) {
			return getToken(PddlHtnParser.NAME, i);
		}

		public TerminalNode TOTAL_TIME() {
			return getToken(PddlHtnParser.TOTAL_TIME, 0);
		}

		public TerminalNode IS_VIOLATED() {
			return getToken(PddlHtnParser.IS_VIOLATED, 0);
		}

		public MetricFExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_metricFExp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterMetricFExp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitMetricFExp(this);
		}
	}

	public final MetricFExpContext metricFExp() throws RecognitionException {
		MetricFExpContext _localctx = new MetricFExpContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_metricFExp);
		int _la;
		try {
			setState(984);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 83, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(946);
				match(T__0);
				setState(947);
				binaryOp();
				setState(948);
				metricFExp();
				setState(949);
				metricFExp();
				setState(950);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(952);
				match(T__0);
				setState(953);
				_la = _input.LA(1);
				if (!(_la == T__4 || _la == T__6)) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF)
						matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(954);
				metricFExp();
				setState(956);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(955);
							metricFExp();
						}
					}
					setState(958);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == T__0 || _la == TOTAL_TIME || _la == NAME || _la == NUMBER);
				setState(960);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(962);
				match(T__0);
				setState(963);
				match(T__3);
				setState(964);
				metricFExp();
				setState(965);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(967);
				match(NUMBER);
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5); {
				setState(968);
				match(T__0);
				setState(969);
				functionSymbol();
				setState(973);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == NAME) {
					{
						{
							setState(970);
							match(NAME);
						}
					}
					setState(975);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(976);
				match(T__1);
			}
				break;
			case 6:
				enterOuterAlt(_localctx, 6); {
				setState(978);
				functionSymbol();
			}
				break;
			case 7:
				enterOuterAlt(_localctx, 7); {
				setState(979);
				match(TOTAL_TIME);
			}
				break;
			case 8:
				enterOuterAlt(_localctx, 8); {
				setState(980);
				match(T__0);
				setState(981);
				match(IS_VIOLATED);
				setState(982);
				match(NAME);
				setState(983);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConGDContext extends ParserRuleContext {
		public TerminalNode AND() {
			return getToken(PddlHtnParser.AND, 0);
		}

		public List<ConGDContext> conGD() {
			return getRuleContexts(ConGDContext.class);
		}

		public ConGDContext conGD(int i) {
			return getRuleContext(ConGDContext.class, i);
		}

		public TerminalNode FORALL() {
			return getToken(PddlHtnParser.FORALL, 0);
		}

		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class, 0);
		}

		public TerminalNode AT_END() {
			return getToken(PddlHtnParser.AT_END, 0);
		}

		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}

		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class, i);
		}

		public TerminalNode ALWAYS() {
			return getToken(PddlHtnParser.ALWAYS, 0);
		}

		public TerminalNode SOMETIME() {
			return getToken(PddlHtnParser.SOMETIME, 0);
		}

		public TerminalNode WITHIN() {
			return getToken(PddlHtnParser.WITHIN, 0);
		}

		public List<TerminalNode> NUMBER() {
			return getTokens(PddlHtnParser.NUMBER);
		}

		public TerminalNode NUMBER(int i) {
			return getToken(PddlHtnParser.NUMBER, i);
		}

		public TerminalNode AT_MOST_ONCE() {
			return getToken(PddlHtnParser.AT_MOST_ONCE, 0);
		}

		public TerminalNode SOMETIME_AFTER() {
			return getToken(PddlHtnParser.SOMETIME_AFTER, 0);
		}

		public TerminalNode SOMETIME_BEFORE() {
			return getToken(PddlHtnParser.SOMETIME_BEFORE, 0);
		}

		public TerminalNode ALWAYS_WITHIN() {
			return getToken(PddlHtnParser.ALWAYS_WITHIN, 0);
		}

		public TerminalNode HOLD_DURING() {
			return getToken(PddlHtnParser.HOLD_DURING, 0);
		}

		public TerminalNode HOLD_AFTER() {
			return getToken(PddlHtnParser.HOLD_AFTER, 0);
		}

		public ConGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_conGD;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).enterConGD(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof PddlHtnListener)
				((PddlHtnListener) listener).exitConGD(this);
		}
	}

	public final ConGDContext conGD() throws RecognitionException {
		ConGDContext _localctx = new ConGDContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_conGD);
		int _la;
		try {
			setState(1061);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 85, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(986);
				match(T__0);
				setState(987);
				match(AND);
				setState(991);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0) {
					{
						{
							setState(988);
							conGD();
						}
					}
					setState(993);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(994);
				match(T__1);
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(995);
				match(T__0);
				setState(996);
				match(FORALL);
				setState(997);
				match(T__0);
				setState(998);
				typedVariableList();
				setState(999);
				match(T__1);
				setState(1000);
				conGD();
				setState(1001);
				match(T__1);
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(1003);
				match(T__0);
				setState(1004);
				match(AT_END);
				setState(1005);
				goalDesc();
				setState(1006);
				match(T__1);
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(1008);
				match(T__0);
				setState(1009);
				match(ALWAYS);
				setState(1010);
				goalDesc();
				setState(1011);
				match(T__1);
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5); {
				setState(1013);
				match(T__0);
				setState(1014);
				match(SOMETIME);
				setState(1015);
				goalDesc();
				setState(1016);
				match(T__1);
			}
				break;
			case 6:
				enterOuterAlt(_localctx, 6); {
				setState(1018);
				match(T__0);
				setState(1019);
				match(WITHIN);
				setState(1020);
				match(NUMBER);
				setState(1021);
				goalDesc();
				setState(1022);
				match(T__1);
			}
				break;
			case 7:
				enterOuterAlt(_localctx, 7); {
				setState(1024);
				match(T__0);
				setState(1025);
				match(AT_MOST_ONCE);
				setState(1026);
				goalDesc();
				setState(1027);
				match(T__1);
			}
				break;
			case 8:
				enterOuterAlt(_localctx, 8); {
				setState(1029);
				match(T__0);
				setState(1030);
				match(SOMETIME_AFTER);
				setState(1031);
				goalDesc();
				setState(1032);
				goalDesc();
				setState(1033);
				match(T__1);
			}
				break;
			case 9:
				enterOuterAlt(_localctx, 9); {
				setState(1035);
				match(T__0);
				setState(1036);
				match(SOMETIME_BEFORE);
				setState(1037);
				goalDesc();
				setState(1038);
				goalDesc();
				setState(1039);
				match(T__1);
			}
				break;
			case 10:
				enterOuterAlt(_localctx, 10); {
				setState(1041);
				match(T__0);
				setState(1042);
				match(ALWAYS_WITHIN);
				setState(1043);
				match(NUMBER);
				setState(1044);
				goalDesc();
				setState(1045);
				goalDesc();
				setState(1046);
				match(T__1);
			}
				break;
			case 11:
				enterOuterAlt(_localctx, 11); {
				setState(1048);
				match(T__0);
				setState(1049);
				match(HOLD_DURING);
				setState(1050);
				match(NUMBER);
				setState(1051);
				match(NUMBER);
				setState(1052);
				goalDesc();
				setState(1053);
				match(T__1);
			}
				break;
			case 12:
				enterOuterAlt(_localctx, 12); {
				setState(1055);
				match(T__0);
				setState(1056);
				match(HOLD_AFTER);
				setState(1057);
				match(NUMBER);
				setState(1058);
				goalDesc();
				setState(1059);
				match(T__1);
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3a\u042a\4\2\t\2\4"
			+ "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
			+ "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
			+ "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
			+ "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
			+ "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"
			+ ",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"
			+ "\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="
			+ "\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"
			+ "\tI\4J\tJ\4K\tK\4L\tL\4M\tM\3\2\3\2\5\2\u009d\n\2\3\3\3\3\3\3\3\3\5\3"
			+ "\u00a3\n\3\3\3\5\3\u00a6\n\3\3\3\5\3\u00a9\n\3\3\3\5\3\u00ac\n\3\3\3\5"
			+ "\3\u00af\n\3\3\3\5\3\u00b2\n\3\3\3\7\3\u00b5\n\3\f\3\16\3\u00b8\13\3\3"
			+ "\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\6\5\u00c5\n\5\r\5\16\5\u00c6"
			+ "\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\7\7\u00d2\n\7\f\7\16\7\u00d5\13\7"
			+ "\3\7\6\7\u00d8\n\7\r\7\16\7\u00d9\3\7\7\7\u00dd\n\7\f\7\16\7\u00e0\13"
			+ "\7\5\7\u00e2\n\7\3\b\6\b\u00e5\n\b\r\b\16\b\u00e6\3\b\3\b\3\b\3\t\3\t"
			+ "\3\t\6\t\u00ef\n\t\r\t\16\t\u00f0\3\t\3\t\3\t\5\t\u00f6\n\t\3\n\3\n\3"
			+ "\13\3\13\3\13\3\13\3\13\3\13\3\f\6\f\u0101\n\f\r\f\16\f\u0102\3\f\3\f"
			+ "\5\f\u0107\n\f\7\f\u0109\n\f\f\f\16\f\u010c\13\f\3\r\3\r\3\r\3\r\3\r\3"
			+ "\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\6"
			+ "\21\u0121\n\21\r\21\16\21\u0122\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23"
			+ "\3\23\3\24\7\24\u012f\n\24\f\24\16\24\u0132\13\24\3\24\6\24\u0135\n\24"
			+ "\r\24\16\24\u0136\3\24\7\24\u013a\n\24\f\24\16\24\u013d\13\24\5\24\u013f"
			+ "\n\24\3\25\6\25\u0142\n\25\r\25\16\25\u0143\3\25\3\25\3\25\3\26\3\26\3"
			+ "\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\5\27\u0153\n\27\3\30\3\30\3\30"
			+ "\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32"
			+ "\3\32\3\32\5\32\u0168\n\32\5\32\u016a\n\32\3\32\3\32\3\32\3\32\3\32\5"
			+ "\32\u0171\n\32\5\32\u0173\n\32\3\33\3\33\3\33\3\33\7\33\u0179\n\33\f\33"
			+ "\16\33\u017c\13\33\3\33\3\33\3\33\3\33\7\33\u0182\n\33\f\33\16\33\u0185"
			+ "\13\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"
			+ "\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"
			+ "\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u01aa\n\33\3\34\3\34\3\34"
			+ "\3\34\3\34\3\34\3\35\3\35\3\35\7\35\u01b5\n\35\f\35\16\35\u01b8\13\35"
			+ "\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"
			+ "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \7 \u01d5"
			+ "\n \f \16 \u01d8\13 \3!\3!\3!\7!\u01dd\n!\f!\16!\u01e0\13!\3!\3!\3\"\3"
			+ "\"\3\"\3\"\3\"\3\"\3#\3#\7#\u01ec\n#\f#\16#\u01ef\13#\3#\7#\u01f2\n#\f"
			+ "#\16#\u01f5\13#\5#\u01f7\n#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u0203\n$"
			+ "\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&\3&\5&"
			+ "\u021b\n&\3&\3&\3&\3&\3&\5&\u0222\n&\5&\u0224\n&\3\'\3\'\3\'\3\'\7\'\u022a"
			+ "\n\'\f\'\16\'\u022d\13\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u0238"
			+ "\n\'\3(\3(\3(\3(\5(\u023e\n(\3(\3(\3(\5(\u0243\n(\3)\3)\3)\3)\3)\3)\3"
			+ ")\3)\3)\3)\5)\u024f\n)\3*\3*\3+\3+\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3"
			+ "-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u0269\n-\3.\3.\3.\7.\u026e\n.\f.\16.\u0271"
			+ "\13.\3.\3.\3.\5.\u0276\n.\3/\3/\3/\7/\u027b\n/\f/\16/\u027e\13/\3/\3/"
			+ "\5/\u0282\n/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"
			+ "\60\3\60\3\60\3\60\5\60\u0293\n\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61"
			+ "\3\61\3\61\3\61\3\61\3\61\5\61\u02a1\n\61\3\62\3\62\3\62\7\62\u02a6\n"
			+ "\62\f\62\16\62\u02a9\13\62\3\62\3\62\5\62\u02ad\n\62\3\63\3\63\3\64\3"
			+ "\64\3\65\3\65\3\66\3\66\3\66\6\66\u02b8\n\66\r\66\16\66\u02b9\3\66\3\66"
			+ "\3\66\3\66\3\66\5\66\u02c1\n\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67"
			+ "\3\67\3\67\3\67\3\67\5\67\u02cf\n\67\38\38\39\39\59\u02d5\n9\3:\3:\3:"
			+ "\7:\u02da\n:\f:\16:\u02dd\13:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3"
			+ ":\3:\3:\3:\3:\3:\3:\3:\3:\5:\u02f5\n:\3;\3;\3;\3;\3;\3;\3;\3;\3;\3;\3"
			+ ";\3;\3;\3;\3;\3;\5;\u0307\n;\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\5"
			+ "=\u0316\n=\3=\3=\3=\3=\3=\5=\u031d\n=\3>\3>\3>\3>\3>\5>\u0324\n>\3>\5"
			+ ">\u0327\n>\3>\3>\3>\5>\u032c\n>\3>\5>\u032f\n>\3>\3>\3?\3?\3?\3?\3?\3"
			+ "@\3@\3@\3@\3@\3@\3A\3A\3A\3A\3A\3A\3B\3B\3B\3B\7B\u0348\nB\fB\16B\u034b"
			+ "\13B\3B\3B\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\5C\u035b\nC\3D\3D\3D\3"
			+ "D\3D\3D\5D\u0363\nD\3E\3E\3E\7E\u0368\nE\fE\16E\u036b\13E\3E\3E\3F\3F"
			+ "\3F\3F\7F\u0373\nF\fF\16F\u0376\13F\3F\5F\u0379\nF\3F\3F\3G\3G\3G\3G\3"
			+ "G\3G\3G\3G\3G\3G\3G\5G\u0388\nG\3H\3H\3H\3H\3H\3H\3I\3I\3I\7I\u0393\n"
			+ "I\fI\16I\u0396\13I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\5I\u03a4\nI\3I"
			+ "\3I\3I\3I\5I\u03aa\nI\3J\3J\3J\3J\3J\3J\3J\3K\3K\3L\3L\3L\3L\3L\3L\3L"
			+ "\3L\3L\3L\6L\u03bf\nL\rL\16L\u03c0\3L\3L\3L\3L\3L\3L\3L\3L\3L\3L\3L\7"
			+ "L\u03ce\nL\fL\16L\u03d1\13L\3L\3L\3L\3L\3L\3L\3L\3L\5L\u03db\nL\3M\3M"
			+ "\3M\7M\u03e0\nM\fM\16M\u03e3\13M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3"
			+ "M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3"
			+ "M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3M\3"
			+ "M\3M\3M\3M\3M\3M\3M\3M\3M\5M\u0428\nM\3M\2\2N\2\4\6\b\n\f\16\20\22\24"
			+ "\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtv"
			+ "xz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094"
			+ "\u0096\u0098\2\13\3\2\\]\3\2FG\3\2)*\3\2\6\t\4\2\n\r^^\3\2-\61\4\2\f\r"
			+ "^^\3\2\66\67\4\2\7\7\t\t\2\u045b\2\u009c\3\2\2\2\4\u009e\3\2\2\2\6\u00bb"
			+ "\3\2\2\2\b\u00c0\3\2\2\2\n\u00ca\3\2\2\2\f\u00e1\3\2\2\2\16\u00e4\3\2"
			+ "\2\2\20\u00f5\3\2\2\2\22\u00f7\3\2\2\2\24\u00f9\3\2\2\2\26\u010a\3\2\2"
			+ "\2\30\u010d\3\2\2\2\32\u0112\3\2\2\2\34\u0114\3\2\2\2\36\u0116\3\2\2\2"
			+ " \u011c\3\2\2\2\"\u0126\3\2\2\2$\u012b\3\2\2\2&\u013e\3\2\2\2(\u0141\3"
			+ "\2\2\2*\u0148\3\2\2\2,\u0152\3\2\2\2.\u0154\3\2\2\2\60\u0160\3\2\2\2\62"
			+ "\u0169\3\2\2\2\64\u01a9\3\2\2\2\66\u01ab\3\2\2\28\u01b1\3\2\2\2:\u01bb"
			+ "\3\2\2\2<\u01bd\3\2\2\2>\u01d6\3\2\2\2@\u01d9\3\2\2\2B\u01e3\3\2\2\2D"
			+ "\u01f6\3\2\2\2F\u01f8\3\2\2\2H\u0206\3\2\2\2J\u0223\3\2\2\2L\u0237\3\2"
			+ "\2\2N\u0242\3\2\2\2P\u024e\3\2\2\2R\u0250\3\2\2\2T\u0252\3\2\2\2V\u0254"
			+ "\3\2\2\2X\u0268\3\2\2\2Z\u0275\3\2\2\2\\\u0281\3\2\2\2^\u0292\3\2\2\2"
			+ "`\u02a0\3\2\2\2b\u02ac\3\2\2\2d\u02ae\3\2\2\2f\u02b0\3\2\2\2h\u02b2\3"
			+ "\2\2\2j\u02c0\3\2\2\2l\u02ce\3\2\2\2n\u02d0\3\2\2\2p\u02d4\3\2\2\2r\u02f4"
			+ "\3\2\2\2t\u0306\3\2\2\2v\u0308\3\2\2\2x\u031c\3\2\2\2z\u031e\3\2\2\2|"
			+ "\u0332\3\2\2\2~\u0337\3\2\2\2\u0080\u033d\3\2\2\2\u0082\u0343\3\2\2\2"
			+ "\u0084\u035a\3\2\2\2\u0086\u0362\3\2\2\2\u0088\u0364\3\2\2\2\u008a\u036e"
			+ "\3\2\2\2\u008c\u037c\3\2\2\2\u008e\u0389\3\2\2\2\u0090\u03a9\3\2\2\2\u0092"
			+ "\u03ab\3\2\2\2\u0094\u03b2\3\2\2\2\u0096\u03da\3\2\2\2\u0098\u0427\3\2"
			+ "\2\2\u009a\u009d\5\4\3\2\u009b\u009d\5z>\2\u009c\u009a\3\2\2\2\u009c\u009b"
			+ "\3\2\2\2\u009d\3\3\2\2\2\u009e\u009f\7\3\2\2\u009f\u00a0\7\20\2\2\u00a0"
			+ "\u00a2\5\6\4\2\u00a1\u00a3\5\b\5\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2"
			+ "\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a6\5\n\6\2\u00a5\u00a4\3\2\2\2\u00a5"
			+ "\u00a6\3\2\2\2\u00a6\u00a8\3\2\2\2\u00a7\u00a9\5\36\20\2\u00a8\u00a7\3"
			+ "\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\3\2\2\2\u00aa\u00ac\5 \21\2\u00ab"
			+ "\u00aa\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00af\5\24"
			+ "\13\2\u00ae\u00ad\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0"
			+ "\u00b2\5*\26\2\u00b1\u00b0\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b6\3\2"
			+ "\2\2\u00b3\u00b5\5,\27\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6"
			+ "\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b6\3\2"
			+ "\2\2\u00b9\u00ba\7\4\2\2\u00ba\5\3\2\2\2\u00bb\u00bc\7\3\2\2\u00bc\u00bd"
			+ "\7\21\2\2\u00bd\u00be\7\\\2\2\u00be\u00bf\7\4\2\2\u00bf\7\3\2\2\2\u00c0"
			+ "\u00c1\7\3\2\2\u00c1\u00c2\7\5\2\2\u00c2\u00c4\7\23\2\2\u00c3\u00c5\7"
			+ "\17\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"
			+ "\u00c7\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\7\4\2\2\u00c9\t\3\2\2\2"
			+ "\u00ca\u00cb\7\3\2\2\u00cb\u00cc\7\5\2\2\u00cc\u00cd\7\24\2\2\u00cd\u00ce"
			+ "\5\f\7\2\u00ce\u00cf\7\4\2\2\u00cf\13\3\2\2\2\u00d0\u00d2\7\\\2\2\u00d1"
			+ "\u00d0\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2"
			+ "\2\2\u00d4\u00e2\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6\u00d8\5\16\b\2\u00d7"
			+ "\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2"
			+ "\2\2\u00da\u00de\3\2\2\2\u00db\u00dd\7\\\2\2\u00dc\u00db\3\2\2\2\u00dd"
			+ "\u00e0\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e2\3\2"
			+ "\2\2\u00e0\u00de\3\2\2\2\u00e1\u00d3\3\2\2\2\u00e1\u00d7\3\2\2\2\u00e2"
			+ "\r\3\2\2\2\u00e3\u00e5\7\\\2\2\u00e4\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2"
			+ "\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9"
			+ "\7\6\2\2\u00e9\u00ea\5\20\t\2\u00ea\17\3\2\2\2\u00eb\u00ec\7\3\2\2\u00ec"
			+ "\u00ee\7\25\2\2\u00ed\u00ef\5\22\n\2\u00ee\u00ed\3\2\2\2\u00ef\u00f0\3"
			+ "\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2"
			+ "\u00f3\7\4\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f6\5\22\n\2\u00f5\u00eb\3"
			+ "\2\2\2\u00f5\u00f4\3\2\2\2\u00f6\21\3\2\2\2\u00f7\u00f8\7\\\2\2\u00f8"
			+ "\23\3\2\2\2\u00f9\u00fa\7\3\2\2\u00fa\u00fb\7\5\2\2\u00fb\u00fc\7\26\2"
			+ "\2\u00fc\u00fd\5\26\f\2\u00fd\u00fe\7\4\2\2\u00fe\25\3\2\2\2\u00ff\u0101"
			+ "\5\30\r\2\u0100\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0100\3\2\2\2"
			+ "\u0102\u0103\3\2\2\2\u0103\u0106\3\2\2\2\u0104\u0105\7\6\2\2\u0105\u0107"
			+ "\5\34\17\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2"
			+ "\u0108\u0100\3\2\2\2\u0109\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b"
			+ "\3\2\2\2\u010b\27\3\2\2\2\u010c\u010a\3\2\2\2\u010d\u010e\7\3\2\2\u010e"
			+ "\u010f\5\32\16\2\u010f\u0110\5&\24\2\u0110\u0111\7\4\2\2\u0111\31\3\2"
			+ "\2\2\u0112\u0113\7\\\2\2\u0113\33\3\2\2\2\u0114\u0115\7[\2\2\u0115\35"
			+ "\3\2\2\2\u0116\u0117\7\3\2\2\u0117\u0118\7\5\2\2\u0118\u0119\7\27\2\2"
			+ "\u0119\u011a\5\f\7\2\u011a\u011b\7\4\2\2\u011b\37\3\2\2\2\u011c\u011d"
			+ "\7\3\2\2\u011d\u011e\7\5\2\2\u011e\u0120\7\30\2\2\u011f\u0121\5\"\22\2"
			+ "\u0120\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0123"
			+ "\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0125\7\4\2\2\u0125!\3\2\2\2\u0126"
			+ "\u0127\7\3\2\2\u0127\u0128\5$\23\2\u0128\u0129\5&\24\2\u0129\u012a\7\4"
			+ "\2\2\u012a#\3\2\2\2\u012b\u012c\7\\\2\2\u012c%\3\2\2\2\u012d\u012f\7]"
			+ "\2\2\u012e\u012d\3\2\2\2\u012f\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0130"
			+ "\u0131\3\2\2\2\u0131\u013f\3\2\2\2\u0132\u0130\3\2\2\2\u0133\u0135\5("
			+ "\25\2\u0134\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136"
			+ "\u0137\3\2\2\2\u0137\u013b\3\2\2\2\u0138\u013a\7]\2\2\u0139\u0138\3\2"
			+ "\2\2\u013a\u013d\3\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c"
			+ "\u013f\3\2\2\2\u013d\u013b\3\2\2\2\u013e\u0130\3\2\2\2\u013e\u0134\3\2"
			+ "\2\2\u013f\'\3\2\2\2\u0140\u0142\7]\2\2\u0141\u0140\3\2\2\2\u0142\u0143"
			+ "\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\3\2\2\2\u0145"
			+ "\u0146\7\6\2\2\u0146\u0147\5\20\t\2\u0147)\3\2\2\2\u0148\u0149\7\3\2\2"
			+ "\u0149\u014a\7\5\2\2\u014a\u014b\7\31\2\2\u014b\u014c\5\u0098M\2\u014c"
			+ "\u014d\7\4\2\2\u014d+\3\2\2\2\u014e\u0153\5.\30\2\u014f\u0153\5H%\2\u0150"
			+ "\u0153\5V,\2\u0151\u0153\5<\37\2\u0152\u014e\3\2\2\2\u0152\u014f\3\2\2"
			+ "\2\u0152\u0150\3\2\2\2\u0152\u0151\3\2\2\2\u0153-\3\2\2\2\u0154\u0155"
			+ "\7\3\2\2\u0155\u0156\7\5\2\2\u0156\u0157\7\32\2\2\u0157\u0158\5\60\31"
			+ "\2\u0158\u0159\7\5\2\2\u0159\u015a\7\33\2\2\u015a\u015b\7\3\2\2\u015b"
			+ "\u015c\5&\24\2\u015c\u015d\7\4\2\2\u015d\u015e\5\62\32\2\u015e\u015f\7"
			+ "\4\2\2\u015f/\3\2\2\2\u0160\u0161\7\\\2\2\u0161\61\3\2\2\2\u0162\u0163"
			+ "\7\5\2\2\u0163\u0167\7\34\2\2\u0164\u0165\7\3\2\2\u0165\u0168\7\4\2\2"
			+ "\u0166\u0168\5\64\33\2\u0167\u0164\3\2\2\2\u0167\u0166\3\2\2\2\u0168\u016a"
			+ "\3\2\2\2\u0169\u0162\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u0172\3\2\2\2\u016b"
			+ "\u016c\7\5\2\2\u016c\u0170\7\35\2\2\u016d\u016e\7\3\2\2\u016e\u0171\7"
			+ "\4\2\2\u016f\u0171\5\\/\2\u0170\u016d\3\2\2\2\u0170\u016f\3\2\2\2\u0171"
			+ "\u0173\3\2\2\2\u0172\u016b\3\2\2\2\u0172\u0173\3\2\2\2\u0173\63\3\2\2"
			+ "\2\u0174\u01aa\58\35\2\u0175\u0176\7\3\2\2\u0176\u017a\7\36\2\2\u0177"
			+ "\u0179\5\64\33\2\u0178\u0177\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3"
			+ "\2\2\2\u017a\u017b\3\2\2\2\u017b\u017d\3\2\2\2\u017c\u017a\3\2\2\2\u017d"
			+ "\u01aa\7\4\2\2\u017e\u017f\7\3\2\2\u017f\u0183\7\37\2\2\u0180\u0182\5"
			+ "\64\33\2\u0181\u0180\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2\2\2\u0183"
			+ "\u0184\3\2\2\2\u0184\u0186\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u01aa\7\4"
			+ "\2\2\u0187\u0188\7\3\2\2\u0188\u0189\7 \2\2\u0189\u018a\5\64\33\2\u018a"
			+ "\u018b\7\4\2\2\u018b\u01aa\3\2\2\2\u018c\u018d\7\3\2\2\u018d\u018e\7!"
			+ "\2\2\u018e\u018f\5\64\33\2\u018f\u0190\5\64\33\2\u0190\u0191\7\4\2\2\u0191"
			+ "\u01aa\3\2\2\2\u0192\u0193\7\3\2\2\u0193\u0194\7\"\2\2\u0194\u0195\7\3"
			+ "\2\2\u0195\u0196\5&\24\2\u0196\u0197\7\4\2\2\u0197\u0198\5\64\33\2\u0198"
			+ "\u0199\7\4\2\2\u0199\u01aa\3\2\2\2\u019a\u019b\7\3\2\2\u019b\u019c\7#"
			+ "\2\2\u019c\u019d\7\3\2\2\u019d\u019e\5&\24\2\u019e\u019f\7\4\2\2\u019f"
			+ "\u01a0\5\64\33\2\u01a0\u01a1\7\4\2\2\u01a1\u01aa\3\2\2\2\u01a2\u01aa\5"
			+ "\66\34\2\u01a3\u01a4\7\3\2\2\u01a4\u01a5\7^\2\2\u01a5\u01a6\5:\36\2\u01a6"
			+ "\u01a7\5:\36\2\u01a7\u01a8\7\4\2\2\u01a8\u01aa\3\2\2\2\u01a9\u0174\3\2"
			+ "\2\2\u01a9\u0175\3\2\2\2\u01a9\u017e\3\2\2\2\u01a9\u0187\3\2\2\2\u01a9"
			+ "\u018c\3\2\2\2\u01a9\u0192\3\2\2\2\u01a9\u019a\3\2\2\2\u01a9\u01a2\3\2"
			+ "\2\2\u01a9\u01a3\3\2\2\2\u01aa\65\3\2\2\2\u01ab\u01ac\7\3\2\2\u01ac\u01ad"
			+ "\5f\64\2\u01ad\u01ae\5X-\2\u01ae\u01af\5X-\2\u01af\u01b0\7\4\2\2\u01b0"
			+ "\67\3\2\2\2\u01b1\u01b2\7\3\2\2\u01b2\u01b6\5$\23\2\u01b3\u01b5\5:\36"
			+ "\2\u01b4\u01b3\3\2\2\2\u01b5\u01b8\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b6\u01b7"
			+ "\3\2\2\2\u01b7\u01b9\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b9\u01ba\7\4\2\2\u01ba"
			+ "9\3\2\2\2\u01bb\u01bc\t\2\2\2\u01bc;\3\2\2\2\u01bd\u01be\7\3\2\2\u01be"
			+ "\u01bf\7\5\2\2\u01bf\u01c0\7C\2\2\u01c0\u01c1\5\60\31\2\u01c1\u01c2\7"
			+ "\5\2\2\u01c2\u01c3\7\33\2\2\u01c3\u01c4\7\3\2\2\u01c4\u01c5\5&\24\2\u01c5"
			+ "\u01c6\7\4\2\2\u01c6\u01c7\7\5\2\2\u01c7\u01c8\7D\2\2\u01c8\u01c9\7\3"
			+ "\2\2\u01c9\u01ca\5> \2\u01ca\u01cb\7\4\2\2\u01cb\u01cc\7\5\2\2\u01cc\u01cd"
			+ "\7\31\2\2\u01cd\u01ce\7\3\2\2\u01ce\u01cf\5D#\2\u01cf\u01d0\7\4\2\2\u01d0"
			+ "\u01d1\7\4\2\2\u01d1=\3\2\2\2\u01d2\u01d5\5@!\2\u01d3\u01d5\5B\"\2\u01d4"
			+ "\u01d2\3\2\2\2\u01d4\u01d3\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6\u01d4\3\2"
			+ "\2\2\u01d6\u01d7\3\2\2\2\u01d7?\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01da"
			+ "\7\3\2\2\u01da\u01de\7\\\2\2\u01db\u01dd\5:\36\2\u01dc\u01db\3\2\2\2\u01dd"
			+ "\u01e0\3\2\2\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e1\3\2"
			+ "\2\2\u01e0\u01de\3\2\2\2\u01e1\u01e2\7\4\2\2\u01e2A\3\2\2\2\u01e3\u01e4"
			+ "\7\3\2\2\u01e4\u01e5\7E\2\2\u01e5\u01e6\7\\\2\2\u01e6\u01e7\5@!\2\u01e7"
			+ "\u01e8\7\4\2\2\u01e8C\3\2\2\2\u01e9\u01ed\7\36\2\2\u01ea\u01ec\5F$\2\u01eb"
			+ "\u01ea\3\2\2\2\u01ec\u01ef\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2"
			+ "\2\2\u01ee\u01f7\3\2\2\2\u01ef\u01ed\3\2\2\2\u01f0\u01f2\5F$\2\u01f1\u01f0"
			+ "\3\2\2\2\u01f2\u01f5\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4"
			+ "\u01f7\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f6\u01e9\3\2\2\2\u01f6\u01f3\3\2"
			+ "\2\2\u01f7E\3\2\2\2\u01f8\u0202\7\3\2\2\u01f9\u01fa\t\3\2\2\u01fa\u01fb"
			+ "\5\64\33\2\u01fb\u01fc\7\\\2\2\u01fc\u0203\3\2\2\2\u01fd\u01fe\7H\2\2"
			+ "\u01fe\u01ff\5\64\33\2\u01ff\u0200\7\\\2\2\u0200\u0201\7\\\2\2\u0201\u0203"
			+ "\3\2\2\2\u0202\u01f9\3\2\2\2\u0202\u01fd\3\2\2\2\u0203\u0204\3\2\2\2\u0204"
			+ "\u0205\7\4\2\2\u0205G\3\2\2\2\u0206\u0207\7\3\2\2\u0207\u0208\7\5\2\2"
			+ "\u0208\u0209\7$\2\2\u0209\u020a\5\60\31\2\u020a\u020b\7\5\2\2\u020b\u020c"
			+ "\7\33\2\2\u020c\u020d\7\3\2\2\u020d\u020e\5&\24\2\u020e\u020f\7\4\2\2"
			+ "\u020f\u0210\5J&\2\u0210\u0211\7\4\2\2\u0211I\3\2\2\2\u0212\u0213\7\5"
			+ "\2\2\u0213\u0214\7%\2\2\u0214\u0224\5j\66\2\u0215\u0216\7\5\2\2\u0216"
			+ "\u021a\7&\2\2\u0217\u0218\7\3\2\2\u0218\u021b\7\4\2\2\u0219\u021b\5L\'"
			+ "\2\u021a\u0217\3\2\2\2\u021a\u0219\3\2\2\2\u021b\u0224\3\2\2\2\u021c\u021d"
			+ "\7\5\2\2\u021d\u0221\7\35\2\2\u021e\u021f\7\3\2\2\u021f\u0222\7\4\2\2"
			+ "\u0220\u0222\5r:\2\u0221\u021e\3\2\2\2\u0221\u0220\3\2\2\2\u0222\u0224"
			+ "\3\2\2\2\u0223\u0212\3\2\2\2\u0223\u0215\3\2\2\2\u0223\u021c\3\2\2\2\u0224"
			+ "K\3\2\2\2\u0225\u0238\5N(\2\u0226\u0227\7\3\2\2\u0227\u022b\7\36\2\2\u0228"
			+ "\u022a\5L\'\2\u0229\u0228\3\2\2\2\u022a\u022d\3\2\2\2\u022b\u0229\3\2"
			+ "\2\2\u022b\u022c\3\2\2\2\u022c\u022e\3\2\2\2\u022d\u022b\3\2\2\2\u022e"
			+ "\u0238\7\4\2\2\u022f\u0230\7\3\2\2\u0230\u0231\7#\2\2\u0231\u0232\7\3"
			+ "\2\2\u0232\u0233\5&\24\2\u0233\u0234\7\4\2\2\u0234\u0235\5L\'\2\u0235"
			+ "\u0236\7\4\2\2\u0236\u0238\3\2\2\2\u0237\u0225\3\2\2\2\u0237\u0226\3\2"
			+ "\2\2\u0237\u022f\3\2\2\2\u0238M\3\2\2\2\u0239\u0243\5P)\2\u023a\u023b"
			+ "\7\3\2\2\u023b\u023d\7\'\2\2\u023c\u023e\7\\\2\2\u023d\u023c\3\2\2\2\u023d"
			+ "\u023e\3\2\2\2\u023e\u023f\3\2\2\2\u023f\u0240\5P)\2\u0240\u0241\7\4\2"
			+ "\2\u0241\u0243\3\2\2\2\u0242\u0239\3\2\2\2\u0242\u023a\3\2\2\2\u0243O"
			+ "\3\2\2\2\u0244\u0245\7\3\2\2\u0245\u0246\5R*\2\u0246\u0247\5\64\33\2\u0247"
			+ "\u0248\7\4\2\2\u0248\u024f\3\2\2\2\u0249\u024a\7\3\2\2\u024a\u024b\7("
			+ "\2\2\u024b\u024c\5\64\33\2\u024c\u024d\7\4\2\2\u024d\u024f\3\2\2\2\u024e"
			+ "\u0244\3\2\2\2\u024e\u0249\3\2\2\2\u024fQ\3\2\2\2\u0250\u0251\t\4\2\2"
			+ "\u0251S\3\2\2\2\u0252\u0253\7(\2\2\u0253U\3\2\2\2\u0254\u0255\7\3\2\2"
			+ "\u0255\u0256\7\5\2\2\u0256\u0257\7+\2\2\u0257\u0258\5\"\22\2\u0258\u0259"
			+ "\5\64\33\2\u0259\u025a\7\4\2\2\u025aW\3\2\2\2\u025b\u0269\7_\2\2\u025c"
			+ "\u025d\7\3\2\2\u025d\u025e\5d\63\2\u025e\u025f\5X-\2\u025f\u0260\5X-\2"
			+ "\u0260\u0261\7\4\2\2\u0261\u0269\3\2\2\2\u0262\u0263\7\3\2\2\u0263\u0264"
			+ "\7\6\2\2\u0264\u0265\5X-\2\u0265\u0266\7\4\2\2\u0266\u0269\3\2\2\2\u0267"
			+ "\u0269\5Z.\2\u0268\u025b\3\2\2\2\u0268\u025c\3\2\2\2\u0268\u0262\3\2\2"
			+ "\2\u0268\u0267\3\2\2\2\u0269Y\3\2\2\2\u026a\u026b\7\3\2\2\u026b\u026f"
			+ "\5\32\16\2\u026c\u026e\5:\36\2\u026d\u026c\3\2\2\2\u026e\u0271\3\2\2\2"
			+ "\u026f\u026d\3\2\2\2\u026f\u0270\3\2\2\2\u0270\u0272\3\2\2\2\u0271\u026f"
			+ "\3\2\2\2\u0272\u0273\7\4\2\2\u0273\u0276\3\2\2\2\u0274\u0276\5\32\16\2"
			+ "\u0275\u026a\3\2\2\2\u0275\u0274\3\2\2\2\u0276[\3\2\2\2\u0277\u0278\7"
			+ "\3\2\2\u0278\u027c\7\36\2\2\u0279\u027b\5^\60\2\u027a\u0279\3\2\2\2\u027b"
			+ "\u027e\3\2\2\2\u027c\u027a\3\2\2\2\u027c\u027d\3\2\2\2\u027d\u027f\3\2"
			+ "\2\2\u027e\u027c\3\2\2\2\u027f\u0282\7\4\2\2\u0280\u0282\5^\60\2\u0281"
			+ "\u0277\3\2\2\2\u0281\u0280\3\2\2\2\u0282]\3\2\2\2\u0283\u0284\7\3\2\2"
			+ "\u0284\u0285\7#\2\2\u0285\u0286\7\3\2\2\u0286\u0287\5&\24\2\u0287\u0288"
			+ "\7\4\2\2\u0288\u0289\5\\/\2\u0289\u028a\7\4\2\2\u028a\u0293\3\2\2\2\u028b"
			+ "\u028c\7\3\2\2\u028c\u028d\7,\2\2\u028d\u028e\5\64\33\2\u028e\u028f\5"
			+ "b\62\2\u028f\u0290\7\4\2\2\u0290\u0293\3\2\2\2\u0291\u0293\5`\61\2\u0292"
			+ "\u0283\3\2\2\2\u0292\u028b\3\2\2\2\u0292\u0291\3\2\2\2\u0293_\3\2\2\2"
			+ "\u0294\u0295\7\3\2\2\u0295\u0296\5h\65\2\u0296\u0297\5Z.\2\u0297\u0298"
			+ "\5X-\2\u0298\u0299\7\4\2\2\u0299\u02a1\3\2\2\2\u029a\u029b\7\3\2\2\u029b"
			+ "\u029c\7 \2\2\u029c\u029d\58\35\2\u029d\u029e\7\4\2\2\u029e\u02a1\3\2"
			+ "\2\2\u029f\u02a1\58\35\2\u02a0\u0294\3\2\2\2\u02a0\u029a\3\2\2\2\u02a0"
			+ "\u029f\3\2\2\2\u02a1a\3\2\2\2\u02a2\u02a3\7\3\2\2\u02a3\u02a7\7\36\2\2"
			+ "\u02a4\u02a6\5`\61\2\u02a5\u02a4\3\2\2\2\u02a6\u02a9\3\2\2\2\u02a7\u02a5"
			+ "\3\2\2\2\u02a7\u02a8\3\2\2\2\u02a8\u02aa\3\2\2\2\u02a9\u02a7\3\2\2\2\u02aa"
			+ "\u02ad\7\4\2\2\u02ab\u02ad\5`\61\2\u02ac\u02a2\3\2\2\2\u02ac\u02ab\3\2"
			+ "\2\2\u02adc\3\2\2\2\u02ae\u02af\t\5\2\2\u02afe\3\2\2\2\u02b0\u02b1\t\6"
			+ "\2\2\u02b1g\3\2\2\2\u02b2\u02b3\t\7\2\2\u02b3i\3\2\2\2\u02b4\u02b5\7\3"
			+ "\2\2\u02b5\u02b7\7\36\2\2\u02b6\u02b8\5l\67\2\u02b7\u02b6\3\2\2\2\u02b8"
			+ "\u02b9\3\2\2\2\u02b9\u02b7\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bb\3\2"
			+ "\2\2\u02bb\u02bc\7\4\2\2\u02bc\u02c1\3\2\2\2\u02bd\u02be\7\3\2\2\u02be"
			+ "\u02c1\7\4\2\2\u02bf\u02c1\5l\67\2\u02c0\u02b4\3\2\2\2\u02c0\u02bd\3\2"
			+ "\2\2\u02c0\u02bf\3\2\2\2\u02c1k\3\2\2\2\u02c2\u02c3\7\3\2\2\u02c3\u02c4"
			+ "\5n8\2\u02c4\u02c5\7\16\2\2\u02c5\u02c6\7%\2\2\u02c6\u02c7\5p9\2\u02c7"
			+ "\u02c8\7\4\2\2\u02c8\u02cf\3\2\2\2\u02c9\u02ca\7\3\2\2\u02ca\u02cb\5R"
			+ "*\2\u02cb\u02cc\5l\67\2\u02cc\u02cd\7\4\2\2\u02cd\u02cf\3\2\2\2\u02ce"
			+ "\u02c2\3\2\2\2\u02ce\u02c9\3\2\2\2\u02cfm\3\2\2\2\u02d0\u02d1\t\b\2\2"
			+ "\u02d1o\3\2\2\2\u02d2\u02d5\7_\2\2\u02d3\u02d5\5X-\2\u02d4\u02d2\3\2\2"
			+ "\2\u02d4\u02d3\3\2\2\2\u02d5q\3\2\2\2\u02d6\u02d7\7\3\2\2\u02d7\u02db"
			+ "\7\36\2\2\u02d8\u02da\5r:\2\u02d9\u02d8\3\2\2\2\u02da\u02dd\3\2\2\2\u02db"
			+ "\u02d9\3\2\2\2\u02db\u02dc\3\2\2\2\u02dc\u02de\3\2\2\2\u02dd\u02db\3\2"
			+ "\2\2\u02de\u02f5\7\4\2\2\u02df\u02f5\5t;\2\u02e0\u02e1\7\3\2\2\u02e1\u02e2"
			+ "\7#\2\2\u02e2\u02e3\7\3\2\2\u02e3\u02e4\5&\24\2\u02e4\u02e5\7\4\2\2\u02e5"
			+ "\u02e6\5r:\2\u02e6\u02e7\7\4\2\2\u02e7\u02f5\3\2\2\2\u02e8\u02e9\7\3\2"
			+ "\2\u02e9\u02ea\7,\2\2\u02ea\u02eb\5L\'\2\u02eb\u02ec\5t;\2\u02ec\u02ed"
			+ "\7\4\2\2\u02ed\u02f5\3\2\2\2\u02ee\u02ef\7\3\2\2\u02ef\u02f0\5h\65\2\u02f0"
			+ "\u02f1\5Z.\2\u02f1\u02f2\5x=\2\u02f2\u02f3\7\4\2\2\u02f3\u02f5\3\2\2\2"
			+ "\u02f4\u02d6\3\2\2\2\u02f4\u02df\3\2\2\2\u02f4\u02e0\3\2\2\2\u02f4\u02e8"
			+ "\3\2\2\2\u02f4\u02ee\3\2\2\2\u02f5s\3\2\2\2\u02f6\u02f7\7\3\2\2\u02f7"
			+ "\u02f8\5R*\2\u02f8\u02f9\5r:\2\u02f9\u02fa\7\4\2\2\u02fa\u0307\3\2\2\2"
			+ "\u02fb\u02fc\7\3\2\2\u02fc\u02fd\5R*\2\u02fd\u02fe\5v<\2\u02fe\u02ff\7"
			+ "\4\2\2\u02ff\u0307\3\2\2\2\u0300\u0301\7\3\2\2\u0301\u0302\5h\65\2\u0302"
			+ "\u0303\5Z.\2\u0303\u0304\5X-\2\u0304\u0305\7\4\2\2\u0305\u0307\3\2\2\2"
			+ "\u0306\u02f6\3\2\2\2\u0306\u02fb\3\2\2\2\u0306\u0300\3\2\2\2\u0307u\3"
			+ "\2\2\2\u0308\u0309\7\3\2\2\u0309\u030a\5h\65\2\u030a\u030b\5Z.\2\u030b"
			+ "\u030c\5x=\2\u030c\u030d\7\4\2\2\u030dw\3\2\2\2\u030e\u0315\7\3\2\2\u030f"
			+ "\u0310\5d\63\2\u0310\u0311\5x=\2\u0311\u0312\5x=\2\u0312\u0316\3\2\2\2"
			+ "\u0313\u0314\7\6\2\2\u0314\u0316\5x=\2\u0315\u030f\3\2\2\2\u0315\u0313"
			+ "\3\2\2\2\u0316\u0317\3\2\2\2\u0317\u0318\7\4\2\2\u0318\u031d\3\2\2\2\u0319"
			+ "\u031a\7\16\2\2\u031a\u031d\7%\2\2\u031b\u031d\5X-\2\u031c\u030e\3\2\2"
			+ "\2\u031c\u0319\3\2\2\2\u031c\u031b\3\2\2\2\u031dy\3\2\2\2\u031e\u031f"
			+ "\7\3\2\2\u031f\u0320\7\20\2\2\u0320\u0321\5|?\2\u0321\u0323\5~@\2\u0322"
			+ "\u0324\5\b\5\2\u0323\u0322\3\2\2\2\u0323\u0324\3\2\2\2\u0324\u0326\3\2"
			+ "\2\2\u0325\u0327\5\u0080A\2\u0326\u0325\3\2\2\2\u0326\u0327\3\2\2\2\u0327"
			+ "\u0328\3\2\2\2\u0328\u0329\5\u0082B\2\u0329\u032b\5\u008aF\2\u032a\u032c"
			+ "\5\u008eH\2\u032b\u032a\3\2\2\2\u032b\u032c\3\2\2\2\u032c\u032e\3\2\2"
			+ "\2\u032d\u032f\5\u0092J\2\u032e\u032d\3\2\2\2\u032e\u032f\3\2\2\2\u032f"
			+ "\u0330\3\2\2\2\u0330\u0331\7\4\2\2\u0331{\3\2\2\2\u0332\u0333\7\3\2\2"
			+ "\u0333\u0334\7\22\2\2\u0334\u0335\7\\\2\2\u0335\u0336\7\4\2\2\u0336}\3"
			+ "\2\2\2\u0337\u0338\7\3\2\2\u0338\u0339\7\5\2\2\u0339\u033a\7\21\2\2\u033a"
			+ "\u033b\7\\\2\2\u033b\u033c\7\4\2\2\u033c\177\3\2\2\2\u033d\u033e\7\3\2"
			+ "\2\u033e\u033f\7\5\2\2\u033f\u0340\7\62\2\2\u0340\u0341\5\f\7\2\u0341"
			+ "\u0342\7\4\2\2\u0342\u0081\3\2\2\2\u0343\u0344\7\3\2\2\u0344\u0345\7\5"
			+ "\2\2\u0345\u0349\7\63\2\2\u0346\u0348\5\u0084C\2\u0347\u0346\3\2\2\2\u0348"
			+ "\u034b\3\2\2\2\u0349\u0347\3\2\2\2\u0349\u034a\3\2\2\2\u034a\u034c\3\2"
			+ "\2\2\u034b\u0349\3\2\2\2\u034c\u034d\7\4\2\2\u034d\u0083\3\2\2\2\u034e"
			+ "\u035b\5\u0086D\2\u034f\u0350\7\3\2\2\u0350\u0351\7^\2\2\u0351\u0352\5"
			+ "Z.\2\u0352\u0353\7_\2\2\u0353\u0354\7\4\2\2\u0354\u035b\3\2\2\2\u0355"
			+ "\u0356\7\3\2\2\u0356\u0357\7_\2\2\u0357\u0358\5\u0086D\2\u0358\u0359\7"
			+ "\4\2\2\u0359\u035b\3\2\2\2\u035a\u034e\3\2\2\2\u035a\u034f\3\2\2\2\u035a"
			+ "\u0355\3\2\2\2\u035b\u0085\3\2\2\2\u035c\u0363\5\u0088E\2\u035d\u035e"
			+ "\7\3\2\2\u035e\u035f\7 \2\2\u035f\u0360\5\u0088E\2\u0360\u0361\7\4\2\2"
			+ "\u0361\u0363\3\2\2\2\u0362\u035c\3\2\2\2\u0362\u035d\3\2\2\2\u0363\u0087"
			+ "\3\2\2\2\u0364\u0365\7\3\2\2\u0365\u0369\5$\23\2\u0366\u0368\7\\\2\2\u0367"
			+ "\u0366\3\2\2\2\u0368\u036b\3\2\2\2\u0369\u0367\3\2\2\2\u0369\u036a\3\2"
			+ "\2\2\u036a\u036c\3\2\2\2\u036b\u0369\3\2\2\2\u036c\u036d\7\4\2\2\u036d"
			+ "\u0089\3\2\2\2\u036e\u036f\7\3\2\2\u036f\u0370\7\5\2\2\u0370\u0374\7\64"
			+ "\2\2\u0371\u0373\5\64\33\2\u0372\u0371\3\2\2\2\u0373\u0376\3\2\2\2\u0374"
			+ "\u0372\3\2\2\2\u0374\u0375\3\2\2\2\u0375\u0378\3\2\2\2\u0376\u0374\3\2"
			+ "\2\2\u0377\u0379\5\u008cG\2\u0378\u0377\3\2\2\2\u0378\u0379\3\2\2\2\u0379"
			+ "\u037a\3\2\2\2\u037a\u037b\7\4\2\2\u037b\u008b\3\2\2\2\u037c\u037d\7\5"
			+ "\2\2\u037d\u037e\7I\2\2\u037e\u037f\7\3\2\2\u037f\u0380\5> \2\u0380\u0387"
			+ "\7\4\2\2\u0381\u0382\7\5\2\2\u0382\u0383\7\31\2\2\u0383\u0384\7\3\2\2"
			+ "\u0384\u0385\5D#\2\u0385\u0386\7\4\2\2\u0386\u0388\3\2\2\2\u0387\u0381"
			+ "\3\2\2\2\u0387\u0388\3\2\2\2\u0388\u008d\3\2\2\2\u0389\u038a\7\3\2\2\u038a"
			+ "\u038b\7\5\2\2\u038b\u038c\7\31\2\2\u038c\u038d\5\u0090I\2\u038d\u038e"
			+ "\7\4\2\2\u038e\u008f\3\2\2\2\u038f\u0390\7\3\2\2\u0390\u0394\7\36\2\2"
			+ "\u0391\u0393\5\u0090I\2\u0392\u0391\3\2\2\2\u0393\u0396\3\2\2\2\u0394"
			+ "\u0392\3\2\2\2\u0394\u0395\3\2\2\2\u0395\u0397\3\2\2\2\u0396\u0394\3\2"
			+ "\2\2\u0397\u03aa\7\4\2\2\u0398\u0399\7\3\2\2\u0399\u039a\7#\2\2\u039a"
			+ "\u039b\7\3\2\2\u039b\u039c\5&\24\2\u039c\u039d\7\4\2\2\u039d\u039e\5\u0090"
			+ "I\2\u039e\u039f\7\4\2\2\u039f\u03aa\3\2\2\2\u03a0\u03a1\7\3\2\2\u03a1"
			+ "\u03a3\7\'\2\2\u03a2\u03a4\7\\\2\2\u03a3\u03a2\3\2\2\2\u03a3\u03a4\3\2"
			+ "\2\2\u03a4\u03a5\3\2\2\2\u03a5\u03a6\5\u0098M\2\u03a6\u03a7\7\4\2\2\u03a7"
			+ "\u03aa\3\2\2\2\u03a8\u03aa\5\u0098M\2\u03a9\u038f\3\2\2\2\u03a9\u0398"
			+ "\3\2\2\2\u03a9\u03a0\3\2\2\2\u03a9\u03a8\3\2\2\2\u03aa\u0091\3\2\2\2\u03ab"
			+ "\u03ac\7\3\2\2\u03ac\u03ad\7\5\2\2\u03ad\u03ae\7\65\2\2\u03ae\u03af\5"
			+ "\u0094K\2\u03af\u03b0\5\u0096L\2\u03b0\u03b1\7\4\2\2\u03b1\u0093\3\2\2"
			+ "\2\u03b2\u03b3\t\t\2\2\u03b3\u0095\3\2\2\2\u03b4\u03b5\7\3\2\2\u03b5\u03b6"
			+ "\5d\63\2\u03b6\u03b7\5\u0096L\2\u03b7\u03b8\5\u0096L\2\u03b8\u03b9\7\4"
			+ "\2\2\u03b9\u03db\3\2\2\2\u03ba\u03bb\7\3\2\2\u03bb\u03bc\t\n\2\2\u03bc"
			+ "\u03be\5\u0096L\2\u03bd\u03bf\5\u0096L\2\u03be\u03bd\3\2\2\2\u03bf\u03c0"
			+ "\3\2\2\2\u03c0\u03be\3\2\2\2\u03c0\u03c1\3\2\2\2\u03c1\u03c2\3\2\2\2\u03c2"
			+ "\u03c3\7\4\2\2\u03c3\u03db\3\2\2\2\u03c4\u03c5\7\3\2\2\u03c5\u03c6\7\6"
			+ "\2\2\u03c6\u03c7\5\u0096L\2\u03c7\u03c8\7\4\2\2\u03c8\u03db\3\2\2\2\u03c9"
			+ "\u03db\7_\2\2\u03ca\u03cb\7\3\2\2\u03cb\u03cf\5\32\16\2\u03cc\u03ce\7"
			+ "\\\2\2\u03cd\u03cc\3\2\2\2\u03ce\u03d1\3\2\2\2\u03cf\u03cd\3\2\2\2\u03cf"
			+ "\u03d0\3\2\2\2\u03d0\u03d2\3\2\2\2\u03d1\u03cf\3\2\2\2\u03d2\u03d3\7\4"
			+ "\2\2\u03d3\u03db\3\2\2\2\u03d4\u03db\5\32\16\2\u03d5\u03db\78\2\2\u03d6"
			+ "\u03d7\7\3\2\2\u03d7\u03d8\79\2\2\u03d8\u03d9\7\\\2\2\u03d9\u03db\7\4"
			+ "\2\2\u03da\u03b4\3\2\2\2\u03da\u03ba\3\2\2\2\u03da\u03c4\3\2\2\2\u03da"
			+ "\u03c9\3\2\2\2\u03da\u03ca\3\2\2\2\u03da\u03d4\3\2\2\2\u03da\u03d5\3\2"
			+ "\2\2\u03da\u03d6\3\2\2\2\u03db\u0097\3\2\2\2\u03dc\u03dd\7\3\2\2\u03dd"
			+ "\u03e1\7\36\2\2\u03de\u03e0\5\u0098M\2\u03df\u03de\3\2\2\2\u03e0\u03e3"
			+ "\3\2\2\2\u03e1\u03df\3\2\2\2\u03e1\u03e2\3\2\2\2\u03e2\u03e4\3\2\2\2\u03e3"
			+ "\u03e1\3\2\2\2\u03e4\u0428\7\4\2\2\u03e5\u03e6\7\3\2\2\u03e6\u03e7\7#"
			+ "\2\2\u03e7\u03e8\7\3\2\2\u03e8\u03e9\5&\24\2\u03e9\u03ea\7\4\2\2\u03ea"
			+ "\u03eb\5\u0098M\2\u03eb\u03ec\7\4\2\2\u03ec\u0428\3\2\2\2\u03ed\u03ee"
			+ "\7\3\2\2\u03ee\u03ef\7*\2\2\u03ef\u03f0\5\64\33\2\u03f0\u03f1\7\4\2\2"
			+ "\u03f1\u0428\3\2\2\2\u03f2\u03f3\7\3\2\2\u03f3\u03f4\7:\2\2\u03f4\u03f5"
			+ "\5\64\33\2\u03f5\u03f6\7\4\2\2\u03f6\u0428\3\2\2\2\u03f7\u03f8\7\3\2\2"
			+ "\u03f8\u03f9\7;\2\2\u03f9\u03fa\5\64\33\2\u03fa\u03fb\7\4\2\2\u03fb\u0428"
			+ "\3\2\2\2\u03fc\u03fd\7\3\2\2\u03fd\u03fe\7<\2\2\u03fe\u03ff\7_\2\2\u03ff"
			+ "\u0400\5\64\33\2\u0400\u0401\7\4\2\2\u0401\u0428\3\2\2\2\u0402\u0403\7"
			+ "\3\2\2\u0403\u0404\7=\2\2\u0404\u0405\5\64\33\2\u0405\u0406\7\4\2\2\u0406"
			+ "\u0428\3\2\2\2\u0407\u0408\7\3\2\2\u0408\u0409\7>\2\2\u0409\u040a\5\64"
			+ "\33\2\u040a\u040b\5\64\33\2\u040b\u040c\7\4\2\2\u040c\u0428\3\2\2\2\u040d"
			+ "\u040e\7\3\2\2\u040e\u040f\7?\2\2\u040f\u0410\5\64\33\2\u0410\u0411\5"
			+ "\64\33\2\u0411\u0412\7\4\2\2\u0412\u0428\3\2\2\2\u0413\u0414\7\3\2\2\u0414"
			+ "\u0415\7@\2\2\u0415\u0416\7_\2\2\u0416\u0417\5\64\33\2\u0417\u0418\5\64"
			+ "\33\2\u0418\u0419\7\4\2\2\u0419\u0428\3\2\2\2\u041a\u041b\7\3\2\2\u041b"
			+ "\u041c\7A\2\2\u041c\u041d\7_\2\2\u041d\u041e\7_\2\2\u041e\u041f\5\64\33"
			+ "\2\u041f\u0420\7\4\2\2\u0420\u0428\3\2\2\2\u0421\u0422\7\3\2\2\u0422\u0423"
			+ "\7B\2\2\u0423\u0424\7_\2\2\u0424\u0425\5\64\33\2\u0425\u0426\7\4\2\2\u0426"
			+ "\u0428\3\2\2\2\u0427\u03dc\3\2\2\2\u0427\u03e5\3\2\2\2\u0427\u03ed\3\2"
			+ "\2\2\u0427\u03f2\3\2\2\2\u0427\u03f7\3\2\2\2\u0427\u03fc\3\2\2\2\u0427"
			+ "\u0402\3\2\2\2\u0427\u0407\3\2\2\2\u0427\u040d\3\2\2\2\u0427\u0413\3\2"
			+ "\2\2\u0427\u041a\3\2\2\2\u0427\u0421\3\2\2\2\u0428\u0099\3\2\2\2X\u009c"
			+ "\u00a2\u00a5\u00a8\u00ab\u00ae\u00b1\u00b6\u00c6\u00d3\u00d9\u00de\u00e1"
			+ "\u00e6\u00f0\u00f5\u0102\u0106\u010a\u0122\u0130\u0136\u013b\u013e\u0143"
			+ "\u0152\u0167\u0169\u0170\u0172\u017a\u0183\u01a9\u01b6\u01d4\u01d6\u01de"
			+ "\u01ed\u01f3\u01f6\u0202\u021a\u0221\u0223\u022b\u0237\u023d\u0242\u024e"
			+ "\u0268\u026f\u0275\u027c\u0281\u0292\u02a0\u02a7\u02ac\u02b9\u02c0\u02ce"
			+ "\u02d4\u02db\u02f4\u0306\u0315\u031c\u0323\u0326\u032b\u032e\u0349\u035a"
			+ "\u0362\u0369\u0374\u0378\u0387\u0394\u03a3\u03a9\u03c0\u03cf\u03da\u03e1" + "\u0427";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}