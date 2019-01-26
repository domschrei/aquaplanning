package edu.kit.aquaplanning.parsing;
// Generated from Pddl.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PddlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, REQUIRE_KEY=13, DEFINE=14, DOMAIN=15, PROBLEM=16, 
		REQUIREMENTS=17, TYPES=18, EITHER=19, FUNCTIONS=20, CONSTANTS=21, PREDICATES=22, 
		CONSTRAINTS=23, ACTION=24, PARAMETERS=25, PRECONDITION=26, EFFECT=27, 
		AND=28, OR=29, NOT=30, IMPLY=31, EXISTS=32, FORALL=33, DURATIVE_ACTION=34, 
		DURATION=35, CONDITION=36, PREFERENCE=37, OVER_ALL=38, AT_START=39, AT_END=40, 
		DERIVED=41, WHEN=42, ASSIGN=43, INCREASE=44, DECREASE=45, SCALE_UP=46, 
		SCALE_DOWN=47, OBJECTS=48, INIT=49, GOAL=50, METRIC=51, MINIMIZE=52, MAXIMIZE=53, 
		TOTAL_TIME=54, IS_VIOLATED=55, ALWAYS=56, SOMETIME=57, WITHIN=58, AT_MOST_ONCE=59, 
		SOMETIME_AFTER=60, SOMETIME_BEFORE=61, ALWAYS_WITHIN=62, HOLD_DURING=63, 
		HOLD_AFTER=64, R_STRIPS=65, R_TYPING=66, R_NEGATIVE_PRECONDITIONS=67, 
		R_DISJUNCTIVE_PRECONDITIONS=68, R_EQUALITY=69, R_EXISTENTIAL_PRECONDITIONS=70, 
		R_UNIVERSAL_PRECONDITIONS=71, R_QUANTIFIED_PRECONDITIONS=72, R_CONDITIONAL_EFFECTS=73, 
		R_FLUENTS=74, R_ADL=75, R_DURATIVE_ACTIONS=76, R_DERIVED_PREDICATES=77, 
		R_TIMED_INITIAL_LITERALS=78, R_PREFERENCES=79, R_CONSTRAINTS=80, R_ACTION_COSTS=81, 
		STR_NUMBER=82, NAME=83, VARIABLE=84, EQUALS=85, NUMBER=86, LINE_COMMENT=87, 
		WHITESPACE=88;
	public static final int
		RULE_pddlDoc = 0, RULE_domain = 1, RULE_domainName = 2, RULE_requireDef = 3, 
		RULE_typesDef = 4, RULE_typedNameList = 5, RULE_singleTypeNameList = 6, 
		RULE_type = 7, RULE_primType = 8, RULE_functionsDef = 9, RULE_functionList = 10, 
		RULE_atomicFunctionSkeleton = 11, RULE_functionSymbol = 12, RULE_functionType = 13, 
		RULE_constantsDef = 14, RULE_predicatesDef = 15, RULE_atomicFormulaSkeleton = 16, 
		RULE_predicate = 17, RULE_typedVariableList = 18, RULE_singleTypeVarList = 19, 
		RULE_constraints = 20, RULE_structureDef = 21, RULE_actionDef = 22, RULE_actionSymbol = 23, 
		RULE_actionDefBody = 24, RULE_goalDesc = 25, RULE_fComp = 26, RULE_atomicTermFormula = 27, 
		RULE_term = 28, RULE_durativeActionDef = 29, RULE_daDefBody = 30, RULE_daGD = 31, 
		RULE_prefTimedGD = 32, RULE_timedGD = 33, RULE_timeSpecifier = 34, RULE_interval = 35, 
		RULE_derivedDef = 36, RULE_fExp = 37, RULE_fExp2 = 38, RULE_fHead = 39, 
		RULE_effect = 40, RULE_cEffect = 41, RULE_pEffect = 42, RULE_condEffect = 43, 
		RULE_binaryOp = 44, RULE_binaryComp = 45, RULE_assignOp = 46, RULE_durationConstraint = 47, 
		RULE_simpleDurationConstraint = 48, RULE_durOp = 49, RULE_durValue = 50, 
		RULE_daEffect = 51, RULE_timedEffect = 52, RULE_fAssignDA = 53, RULE_fExpDA = 54, 
		RULE_problem = 55, RULE_problemDecl = 56, RULE_problemDomain = 57, RULE_objectDecl = 58, 
		RULE_init = 59, RULE_initEl = 60, RULE_nameLiteral = 61, RULE_atomicNameFormula = 62, 
		RULE_goal = 63, RULE_probConstraints = 64, RULE_prefConGD = 65, RULE_metricSpec = 66, 
		RULE_optimization = 67, RULE_metricFExp = 68, RULE_conGD = 69;
	public static final String[] ruleNames = {
		"pddlDoc", "domain", "domainName", "requireDef", "typesDef", "typedNameList", 
		"singleTypeNameList", "type", "primType", "functionsDef", "functionList", 
		"atomicFunctionSkeleton", "functionSymbol", "functionType", "constantsDef", 
		"predicatesDef", "atomicFormulaSkeleton", "predicate", "typedVariableList", 
		"singleTypeVarList", "constraints", "structureDef", "actionDef", "actionSymbol", 
		"actionDefBody", "goalDesc", "fComp", "atomicTermFormula", "term", "durativeActionDef", 
		"daDefBody", "daGD", "prefTimedGD", "timedGD", "timeSpecifier", "interval", 
		"derivedDef", "fExp", "fExp2", "fHead", "effect", "cEffect", "pEffect", 
		"condEffect", "binaryOp", "binaryComp", "assignOp", "durationConstraint", 
		"simpleDurationConstraint", "durOp", "durValue", "daEffect", "timedEffect", 
		"fAssignDA", "fExpDA", "problem", "problemDecl", "problemDomain", "objectDecl", 
		"init", "initEl", "nameLiteral", "atomicNameFormula", "goal", "probConstraints", 
		"prefConGD", "metricSpec", "optimization", "metricFExp", "conGD"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "':'", "'-'", "'*'", "'+'", "'/'", "'>'", "'<'", "'>='", 
		"'<='", "'?'", null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "REQUIRE_KEY", "DEFINE", "DOMAIN", "PROBLEM", "REQUIREMENTS", "TYPES", 
		"EITHER", "FUNCTIONS", "CONSTANTS", "PREDICATES", "CONSTRAINTS", "ACTION", 
		"PARAMETERS", "PRECONDITION", "EFFECT", "AND", "OR", "NOT", "IMPLY", "EXISTS", 
		"FORALL", "DURATIVE_ACTION", "DURATION", "CONDITION", "PREFERENCE", "OVER_ALL", 
		"AT_START", "AT_END", "DERIVED", "WHEN", "ASSIGN", "INCREASE", "DECREASE", 
		"SCALE_UP", "SCALE_DOWN", "OBJECTS", "INIT", "GOAL", "METRIC", "MINIMIZE", 
		"MAXIMIZE", "TOTAL_TIME", "IS_VIOLATED", "ALWAYS", "SOMETIME", "WITHIN", 
		"AT_MOST_ONCE", "SOMETIME_AFTER", "SOMETIME_BEFORE", "ALWAYS_WITHIN", 
		"HOLD_DURING", "HOLD_AFTER", "R_STRIPS", "R_TYPING", "R_NEGATIVE_PRECONDITIONS", 
		"R_DISJUNCTIVE_PRECONDITIONS", "R_EQUALITY", "R_EXISTENTIAL_PRECONDITIONS", 
		"R_UNIVERSAL_PRECONDITIONS", "R_QUANTIFIED_PRECONDITIONS", "R_CONDITIONAL_EFFECTS", 
		"R_FLUENTS", "R_ADL", "R_DURATIVE_ACTIONS", "R_DERIVED_PREDICATES", "R_TIMED_INITIAL_LITERALS", 
		"R_PREFERENCES", "R_CONSTRAINTS", "R_ACTION_COSTS", "STR_NUMBER", "NAME", 
		"VARIABLE", "EQUALS", "NUMBER", "LINE_COMMENT", "WHITESPACE"
	};
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
	public String getGrammarFileName() { return "Pddl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PddlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PddlDocContext extends ParserRuleContext {
		public DomainContext domain() {
			return getRuleContext(DomainContext.class,0);
		}
		public ProblemContext problem() {
			return getRuleContext(ProblemContext.class,0);
		}
		public PddlDocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pddlDoc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPddlDoc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPddlDoc(this);
		}
	}

	public final PddlDocContext pddlDoc() throws RecognitionException {
		PddlDocContext _localctx = new PddlDocContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pddlDoc);
		try {
			setState(142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(140);
				domain();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				problem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DomainContext extends ParserRuleContext {
		public TerminalNode DEFINE() { return getToken(PddlParser.DEFINE, 0); }
		public DomainNameContext domainName() {
			return getRuleContext(DomainNameContext.class,0);
		}
		public RequireDefContext requireDef() {
			return getRuleContext(RequireDefContext.class,0);
		}
		public TypesDefContext typesDef() {
			return getRuleContext(TypesDefContext.class,0);
		}
		public ConstantsDefContext constantsDef() {
			return getRuleContext(ConstantsDefContext.class,0);
		}
		public PredicatesDefContext predicatesDef() {
			return getRuleContext(PredicatesDefContext.class,0);
		}
		public FunctionsDefContext functionsDef() {
			return getRuleContext(FunctionsDefContext.class,0);
		}
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		public List<StructureDefContext> structureDef() {
			return getRuleContexts(StructureDefContext.class);
		}
		public StructureDefContext structureDef(int i) {
			return getRuleContext(StructureDefContext.class,i);
		}
		public DomainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_domain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDomain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDomain(this);
		}
	}

	public final DomainContext domain() throws RecognitionException {
		DomainContext _localctx = new DomainContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_domain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(T__0);
			setState(145);
			match(DEFINE);
			setState(146);
			domainName();
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(147);
				requireDef();
				}
				break;
			}
			setState(151);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(150);
				typesDef();
				}
				break;
			}
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(153);
				constantsDef();
				}
				break;
			}
			setState(157);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(156);
				predicatesDef();
				}
				break;
			}
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(159);
				functionsDef();
				}
				break;
			}
			setState(163);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(162);
				constraints();
				}
				break;
			}
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(165);
				structureDef();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DomainNameContext extends ParserRuleContext {
		public TerminalNode DOMAIN() { return getToken(PddlParser.DOMAIN, 0); }
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public DomainNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_domainName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDomainName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDomainName(this);
		}
	}

	public final DomainNameContext domainName() throws RecognitionException {
		DomainNameContext _localctx = new DomainNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_domainName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__0);
			setState(174);
			match(DOMAIN);
			setState(175);
			match(NAME);
			setState(176);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RequireDefContext extends ParserRuleContext {
		public TerminalNode REQUIREMENTS() { return getToken(PddlParser.REQUIREMENTS, 0); }
		public List<TerminalNode> REQUIRE_KEY() { return getTokens(PddlParser.REQUIRE_KEY); }
		public TerminalNode REQUIRE_KEY(int i) {
			return getToken(PddlParser.REQUIRE_KEY, i);
		}
		public RequireDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_requireDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterRequireDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitRequireDef(this);
		}
	}

	public final RequireDefContext requireDef() throws RecognitionException {
		RequireDefContext _localctx = new RequireDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_requireDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(T__0);
			setState(179);
			match(T__2);
			setState(180);
			match(REQUIREMENTS);
			setState(182); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(181);
				match(REQUIRE_KEY);
				}
				}
				setState(184); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==REQUIRE_KEY );
			setState(186);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypesDefContext extends ParserRuleContext {
		public TerminalNode TYPES() { return getToken(PddlParser.TYPES, 0); }
		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class,0);
		}
		public TypesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typesDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTypesDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTypesDef(this);
		}
	}

	public final TypesDefContext typesDef() throws RecognitionException {
		TypesDefContext _localctx = new TypesDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typesDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(T__0);
			setState(189);
			match(T__2);
			setState(190);
			match(TYPES);
			setState(191);
			typedNameList();
			setState(192);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedNameListContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(PddlParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(PddlParser.NAME, i);
		}
		public List<SingleTypeNameListContext> singleTypeNameList() {
			return getRuleContexts(SingleTypeNameListContext.class);
		}
		public SingleTypeNameListContext singleTypeNameList(int i) {
			return getRuleContext(SingleTypeNameListContext.class,i);
		}
		public TypedNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTypedNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTypedNameList(this);
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
			setState(211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(194);
					match(NAME);
					}
					}
					setState(199);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				{
				setState(201); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(200);
						singleTypeNameList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(203); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(208);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(205);
					match(NAME);
					}
					}
					setState(210);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTypeNameListContext extends ParserRuleContext {
		public TypeContext t;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> NAME() { return getTokens(PddlParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(PddlParser.NAME, i);
		}
		public SingleTypeNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleTypeNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterSingleTypeNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitSingleTypeNameList(this);
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
			setState(214); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(213);
				match(NAME);
				}
				}
				setState(216); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NAME );
			setState(218);
			match(T__3);
			setState(219);
			((SingleTypeNameListContext)_localctx).t = type();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode EITHER() { return getToken(PddlParser.EITHER, 0); }
		public List<PrimTypeContext> primType() {
			return getRuleContexts(PrimTypeContext.class);
		}
		public PrimTypeContext primType(int i) {
			return getRuleContext(PrimTypeContext.class,i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			setState(231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(221);
				match(T__0);
				setState(222);
				match(EITHER);
				setState(224); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(223);
					primType();
					}
					}
					setState(226); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NAME );
				setState(228);
				match(T__1);
				}
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(230);
				primType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimTypeContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public PrimTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPrimType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPrimType(this);
		}
	}

	public final PrimTypeContext primType() throws RecognitionException {
		PrimTypeContext _localctx = new PrimTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_primType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionsDefContext extends ParserRuleContext {
		public TerminalNode FUNCTIONS() { return getToken(PddlParser.FUNCTIONS, 0); }
		public FunctionListContext functionList() {
			return getRuleContext(FunctionListContext.class,0);
		}
		public FunctionsDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionsDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFunctionsDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFunctionsDef(this);
		}
	}

	public final FunctionsDefContext functionsDef() throws RecognitionException {
		FunctionsDefContext _localctx = new FunctionsDefContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionsDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(T__0);
			setState(236);
			match(T__2);
			setState(237);
			match(FUNCTIONS);
			setState(238);
			functionList();
			setState(239);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionListContext extends ParserRuleContext {
		public List<AtomicFunctionSkeletonContext> atomicFunctionSkeleton() {
			return getRuleContexts(AtomicFunctionSkeletonContext.class);
		}
		public AtomicFunctionSkeletonContext atomicFunctionSkeleton(int i) {
			return getRuleContext(AtomicFunctionSkeletonContext.class,i);
		}
		public List<FunctionTypeContext> functionType() {
			return getRuleContexts(FunctionTypeContext.class);
		}
		public FunctionTypeContext functionType(int i) {
			return getRuleContext(FunctionTypeContext.class,i);
		}
		public FunctionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFunctionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFunctionList(this);
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
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(242); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(241);
						atomicFunctionSkeleton();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(244); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(246);
					match(T__3);
					setState(247);
					functionType();
					}
				}

				}
				}
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicFunctionSkeletonContext extends ParserRuleContext {
		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class,0);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public AtomicFunctionSkeletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicFunctionSkeleton; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterAtomicFunctionSkeleton(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitAtomicFunctionSkeleton(this);
		}
	}

	public final AtomicFunctionSkeletonContext atomicFunctionSkeleton() throws RecognitionException {
		AtomicFunctionSkeletonContext _localctx = new AtomicFunctionSkeletonContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_atomicFunctionSkeleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			match(T__0);
			setState(256);
			functionSymbol();
			setState(257);
			typedVariableList();
			setState(258);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionSymbolContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public FunctionSymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionSymbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFunctionSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFunctionSymbol(this);
		}
	}

	public final FunctionSymbolContext functionSymbol() throws RecognitionException {
		FunctionSymbolContext _localctx = new FunctionSymbolContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_functionSymbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeContext extends ParserRuleContext {
		public TerminalNode STR_NUMBER() { return getToken(PddlParser.STR_NUMBER, 0); }
		public FunctionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFunctionType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFunctionType(this);
		}
	}

	public final FunctionTypeContext functionType() throws RecognitionException {
		FunctionTypeContext _localctx = new FunctionTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(STR_NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantsDefContext extends ParserRuleContext {
		public TerminalNode CONSTANTS() { return getToken(PddlParser.CONSTANTS, 0); }
		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class,0);
		}
		public ConstantsDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantsDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterConstantsDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitConstantsDef(this);
		}
	}

	public final ConstantsDefContext constantsDef() throws RecognitionException {
		ConstantsDefContext _localctx = new ConstantsDefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constantsDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(T__0);
			setState(265);
			match(T__2);
			setState(266);
			match(CONSTANTS);
			setState(267);
			typedNameList();
			setState(268);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicatesDefContext extends ParserRuleContext {
		public TerminalNode PREDICATES() { return getToken(PddlParser.PREDICATES, 0); }
		public List<AtomicFormulaSkeletonContext> atomicFormulaSkeleton() {
			return getRuleContexts(AtomicFormulaSkeletonContext.class);
		}
		public AtomicFormulaSkeletonContext atomicFormulaSkeleton(int i) {
			return getRuleContext(AtomicFormulaSkeletonContext.class,i);
		}
		public PredicatesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicatesDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPredicatesDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPredicatesDef(this);
		}
	}

	public final PredicatesDefContext predicatesDef() throws RecognitionException {
		PredicatesDefContext _localctx = new PredicatesDefContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_predicatesDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(T__0);
			setState(271);
			match(T__2);
			setState(272);
			match(PREDICATES);
			setState(274); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(273);
				atomicFormulaSkeleton();
				}
				}
				setState(276); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(278);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicFormulaSkeletonContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public AtomicFormulaSkeletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicFormulaSkeleton; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterAtomicFormulaSkeleton(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitAtomicFormulaSkeleton(this);
		}
	}

	public final AtomicFormulaSkeletonContext atomicFormulaSkeleton() throws RecognitionException {
		AtomicFormulaSkeletonContext _localctx = new AtomicFormulaSkeletonContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_atomicFormulaSkeleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(T__0);
			setState(281);
			predicate();
			setState(282);
			typedVariableList();
			setState(283);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedVariableListContext extends ParserRuleContext {
		public List<TerminalNode> VARIABLE() { return getTokens(PddlParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(PddlParser.VARIABLE, i);
		}
		public List<SingleTypeVarListContext> singleTypeVarList() {
			return getRuleContexts(SingleTypeVarListContext.class);
		}
		public SingleTypeVarListContext singleTypeVarList(int i) {
			return getRuleContext(SingleTypeVarListContext.class,i);
		}
		public TypedVariableListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedVariableList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTypedVariableList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTypedVariableList(this);
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
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==VARIABLE) {
					{
					{
					setState(287);
					match(VARIABLE);
					}
					}
					setState(292);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				{
				setState(294); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(293);
						singleTypeVarList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(296); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(301);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==VARIABLE) {
					{
					{
					setState(298);
					match(VARIABLE);
					}
					}
					setState(303);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleTypeVarListContext extends ParserRuleContext {
		public TypeContext t;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> VARIABLE() { return getTokens(PddlParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(PddlParser.VARIABLE, i);
		}
		public SingleTypeVarListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleTypeVarList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterSingleTypeVarList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitSingleTypeVarList(this);
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
			setState(307); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(306);
				match(VARIABLE);
				}
				}
				setState(309); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VARIABLE );
			setState(311);
			match(T__3);
			setState(312);
			((SingleTypeVarListContext)_localctx).t = type();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintsContext extends ParserRuleContext {
		public TerminalNode CONSTRAINTS() { return getToken(PddlParser.CONSTRAINTS, 0); }
		public ConGDContext conGD() {
			return getRuleContext(ConGDContext.class,0);
		}
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraints; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterConstraints(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitConstraints(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_constraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			match(T__0);
			setState(315);
			match(T__2);
			setState(316);
			match(CONSTRAINTS);
			setState(317);
			conGD();
			setState(318);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StructureDefContext extends ParserRuleContext {
		public ActionDefContext actionDef() {
			return getRuleContext(ActionDefContext.class,0);
		}
		public DurativeActionDefContext durativeActionDef() {
			return getRuleContext(DurativeActionDefContext.class,0);
		}
		public DerivedDefContext derivedDef() {
			return getRuleContext(DerivedDefContext.class,0);
		}
		public StructureDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structureDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterStructureDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitStructureDef(this);
		}
	}

	public final StructureDefContext structureDef() throws RecognitionException {
		StructureDefContext _localctx = new StructureDefContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_structureDef);
		try {
			setState(323);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(320);
				actionDef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(321);
				durativeActionDef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(322);
				derivedDef();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDefContext extends ParserRuleContext {
		public TerminalNode ACTION() { return getToken(PddlParser.ACTION, 0); }
		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class,0);
		}
		public TerminalNode PARAMETERS() { return getToken(PddlParser.PARAMETERS, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public ActionDefBodyContext actionDefBody() {
			return getRuleContext(ActionDefBodyContext.class,0);
		}
		public ActionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterActionDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitActionDef(this);
		}
	}

	public final ActionDefContext actionDef() throws RecognitionException {
		ActionDefContext _localctx = new ActionDefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_actionDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			match(T__0);
			setState(326);
			match(T__2);
			setState(327);
			match(ACTION);
			setState(328);
			actionSymbol();
			setState(329);
			match(T__2);
			setState(330);
			match(PARAMETERS);
			setState(331);
			match(T__0);
			setState(332);
			typedVariableList();
			setState(333);
			match(T__1);
			setState(334);
			actionDefBody();
			setState(335);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionSymbolContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public ActionSymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionSymbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterActionSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitActionSymbol(this);
		}
	}

	public final ActionSymbolContext actionSymbol() throws RecognitionException {
		ActionSymbolContext _localctx = new ActionSymbolContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_actionSymbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionDefBodyContext extends ParserRuleContext {
		public TerminalNode PRECONDITION() { return getToken(PddlParser.PRECONDITION, 0); }
		public TerminalNode EFFECT() { return getToken(PddlParser.EFFECT, 0); }
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public EffectContext effect() {
			return getRuleContext(EffectContext.class,0);
		}
		public ActionDefBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionDefBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterActionDefBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitActionDefBody(this);
		}
	}

	public final ActionDefBodyContext actionDefBody() throws RecognitionException {
		ActionDefBodyContext _localctx = new ActionDefBodyContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_actionDefBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(339);
				match(T__2);
				setState(340);
				match(PRECONDITION);
				setState(344);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					{
					setState(341);
					match(T__0);
					setState(342);
					match(T__1);
					}
					}
					break;
				case 2:
					{
					setState(343);
					goalDesc();
					}
					break;
				}
				}
				break;
			}
			setState(355);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(348);
				match(T__2);
				setState(349);
				match(EFFECT);
				setState(353);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					{
					setState(350);
					match(T__0);
					setState(351);
					match(T__1);
					}
					}
					break;
				case 2:
					{
					setState(352);
					effect();
					}
					break;
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalDescContext extends ParserRuleContext {
		public AtomicTermFormulaContext atomicTermFormula() {
			return getRuleContext(AtomicTermFormulaContext.class,0);
		}
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}
		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class,i);
		}
		public TerminalNode OR() { return getToken(PddlParser.OR, 0); }
		public TerminalNode NOT() { return getToken(PddlParser.NOT, 0); }
		public TerminalNode IMPLY() { return getToken(PddlParser.IMPLY, 0); }
		public TerminalNode EXISTS() { return getToken(PddlParser.EXISTS, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public FCompContext fComp() {
			return getRuleContext(FCompContext.class,0);
		}
		public TerminalNode EQUALS() { return getToken(PddlParser.EQUALS, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public GoalDescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalDesc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterGoalDesc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitGoalDesc(this);
		}
	}

	public final GoalDescContext goalDesc() throws RecognitionException {
		GoalDescContext _localctx = new GoalDescContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_goalDesc);
		int _la;
		try {
			setState(410);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(357);
				atomicTermFormula();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				match(T__0);
				setState(359);
				match(AND);
				setState(363);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(360);
					goalDesc();
					}
					}
					setState(365);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(366);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(367);
				match(T__0);
				setState(368);
				match(OR);
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(369);
					goalDesc();
					}
					}
					setState(374);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(375);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(376);
				match(T__0);
				setState(377);
				match(NOT);
				setState(378);
				goalDesc();
				setState(379);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(381);
				match(T__0);
				setState(382);
				match(IMPLY);
				setState(383);
				goalDesc();
				setState(384);
				goalDesc();
				setState(385);
				match(T__1);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(387);
				match(T__0);
				setState(388);
				match(EXISTS);
				setState(389);
				match(T__0);
				setState(390);
				typedVariableList();
				setState(391);
				match(T__1);
				setState(392);
				goalDesc();
				setState(393);
				match(T__1);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(395);
				match(T__0);
				setState(396);
				match(FORALL);
				setState(397);
				match(T__0);
				setState(398);
				typedVariableList();
				setState(399);
				match(T__1);
				setState(400);
				goalDesc();
				setState(401);
				match(T__1);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(403);
				fComp();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(404);
				match(T__0);
				setState(405);
				match(EQUALS);
				setState(406);
				term();
				setState(407);
				term();
				setState(408);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FCompContext extends ParserRuleContext {
		public BinaryCompContext binaryComp() {
			return getRuleContext(BinaryCompContext.class,0);
		}
		public List<FExpContext> fExp() {
			return getRuleContexts(FExpContext.class);
		}
		public FExpContext fExp(int i) {
			return getRuleContext(FExpContext.class,i);
		}
		public FCompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fComp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFComp(this);
		}
	}

	public final FCompContext fComp() throws RecognitionException {
		FCompContext _localctx = new FCompContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_fComp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			match(T__0);
			setState(413);
			binaryComp();
			setState(414);
			fExp();
			setState(415);
			fExp();
			setState(416);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicTermFormulaContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public AtomicTermFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicTermFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterAtomicTermFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitAtomicTermFormula(this);
		}
	}

	public final AtomicTermFormulaContext atomicTermFormula() throws RecognitionException {
		AtomicTermFormulaContext _localctx = new AtomicTermFormulaContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_atomicTermFormula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			match(T__0);
			setState(419);
			predicate();
			setState(423);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NAME || _la==VARIABLE) {
				{
				{
				setState(420);
				term();
				}
				}
				setState(425);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(426);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public TerminalNode VARIABLE() { return getToken(PddlParser.VARIABLE, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			_la = _input.LA(1);
			if ( !(_la==NAME || _la==VARIABLE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurativeActionDefContext extends ParserRuleContext {
		public TerminalNode DURATIVE_ACTION() { return getToken(PddlParser.DURATIVE_ACTION, 0); }
		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class,0);
		}
		public TerminalNode PARAMETERS() { return getToken(PddlParser.PARAMETERS, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public DaDefBodyContext daDefBody() {
			return getRuleContext(DaDefBodyContext.class,0);
		}
		public DurativeActionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_durativeActionDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDurativeActionDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDurativeActionDef(this);
		}
	}

	public final DurativeActionDefContext durativeActionDef() throws RecognitionException {
		DurativeActionDefContext _localctx = new DurativeActionDefContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_durativeActionDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			match(T__0);
			setState(431);
			match(T__2);
			setState(432);
			match(DURATIVE_ACTION);
			setState(433);
			actionSymbol();
			setState(434);
			match(T__2);
			setState(435);
			match(PARAMETERS);
			setState(436);
			match(T__0);
			setState(437);
			typedVariableList();
			setState(438);
			match(T__1);
			setState(439);
			daDefBody();
			setState(440);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaDefBodyContext extends ParserRuleContext {
		public TerminalNode DURATION() { return getToken(PddlParser.DURATION, 0); }
		public DurationConstraintContext durationConstraint() {
			return getRuleContext(DurationConstraintContext.class,0);
		}
		public TerminalNode CONDITION() { return getToken(PddlParser.CONDITION, 0); }
		public DaGDContext daGD() {
			return getRuleContext(DaGDContext.class,0);
		}
		public TerminalNode EFFECT() { return getToken(PddlParser.EFFECT, 0); }
		public DaEffectContext daEffect() {
			return getRuleContext(DaEffectContext.class,0);
		}
		public DaDefBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_daDefBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDaDefBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDaDefBody(this);
		}
	}

	public final DaDefBodyContext daDefBody() throws RecognitionException {
		DaDefBodyContext _localctx = new DaDefBodyContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_daDefBody);
		try {
			setState(459);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(442);
				match(T__2);
				setState(443);
				match(DURATION);
				setState(444);
				durationConstraint();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(445);
				match(T__2);
				setState(446);
				match(CONDITION);
				setState(450);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					{
					setState(447);
					match(T__0);
					setState(448);
					match(T__1);
					}
					}
					break;
				case 2:
					{
					setState(449);
					daGD();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(452);
				match(T__2);
				setState(453);
				match(EFFECT);
				setState(457);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
				case 1:
					{
					{
					setState(454);
					match(T__0);
					setState(455);
					match(T__1);
					}
					}
					break;
				case 2:
					{
					setState(456);
					daEffect();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaGDContext extends ParserRuleContext {
		public PrefTimedGDContext prefTimedGD() {
			return getRuleContext(PrefTimedGDContext.class,0);
		}
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<DaGDContext> daGD() {
			return getRuleContexts(DaGDContext.class);
		}
		public DaGDContext daGD(int i) {
			return getRuleContext(DaGDContext.class,i);
		}
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public DaGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_daGD; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDaGD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDaGD(this);
		}
	}

	public final DaGDContext daGD() throws RecognitionException {
		DaGDContext _localctx = new DaGDContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_daGD);
		int _la;
		try {
			setState(479);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(461);
				prefTimedGD();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(462);
				match(T__0);
				setState(463);
				match(AND);
				setState(467);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(464);
					daGD();
					}
					}
					setState(469);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(470);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(471);
				match(T__0);
				setState(472);
				match(FORALL);
				setState(473);
				match(T__0);
				setState(474);
				typedVariableList();
				setState(475);
				match(T__1);
				setState(476);
				daGD();
				setState(477);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefTimedGDContext extends ParserRuleContext {
		public TimedGDContext timedGD() {
			return getRuleContext(TimedGDContext.class,0);
		}
		public TerminalNode PREFERENCE() { return getToken(PddlParser.PREFERENCE, 0); }
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public PrefTimedGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefTimedGD; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPrefTimedGD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPrefTimedGD(this);
		}
	}

	public final PrefTimedGDContext prefTimedGD() throws RecognitionException {
		PrefTimedGDContext _localctx = new PrefTimedGDContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_prefTimedGD);
		int _la;
		try {
			setState(490);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(481);
				timedGD();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(482);
				match(T__0);
				setState(483);
				match(PREFERENCE);
				setState(485);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(484);
					match(NAME);
					}
				}

				setState(487);
				timedGD();
				setState(488);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimedGDContext extends ParserRuleContext {
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class,0);
		}
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public TerminalNode OVER_ALL() { return getToken(PddlParser.OVER_ALL, 0); }
		public TimedGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timedGD; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTimedGD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTimedGD(this);
		}
	}

	public final TimedGDContext timedGD() throws RecognitionException {
		TimedGDContext _localctx = new TimedGDContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_timedGD);
		try {
			setState(502);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(492);
				match(T__0);
				setState(493);
				timeSpecifier();
				setState(494);
				goalDesc();
				setState(495);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(497);
				match(T__0);
				setState(498);
				match(OVER_ALL);
				setState(499);
				goalDesc();
				setState(500);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimeSpecifierContext extends ParserRuleContext {
		public TerminalNode AT_START() { return getToken(PddlParser.AT_START, 0); }
		public TerminalNode AT_END() { return getToken(PddlParser.AT_END, 0); }
		public TimeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTimeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTimeSpecifier(this);
		}
	}

	public final TimeSpecifierContext timeSpecifier() throws RecognitionException {
		TimeSpecifierContext _localctx = new TimeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_timeSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(504);
			_la = _input.LA(1);
			if ( !(_la==AT_START || _la==AT_END) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntervalContext extends ParserRuleContext {
		public TerminalNode OVER_ALL() { return getToken(PddlParser.OVER_ALL, 0); }
		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterInterval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitInterval(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_interval);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(506);
			match(OVER_ALL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DerivedDefContext extends ParserRuleContext {
		public TerminalNode DERIVED() { return getToken(PddlParser.DERIVED, 0); }
		public AtomicFormulaSkeletonContext atomicFormulaSkeleton() {
			return getRuleContext(AtomicFormulaSkeletonContext.class,0);
		}
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public DerivedDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_derivedDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDerivedDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDerivedDef(this);
		}
	}

	public final DerivedDefContext derivedDef() throws RecognitionException {
		DerivedDefContext _localctx = new DerivedDefContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_derivedDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(508);
			match(T__0);
			setState(509);
			match(T__2);
			setState(510);
			match(DERIVED);
			setState(511);
			atomicFormulaSkeleton();
			setState(512);
			goalDesc();
			setState(513);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FExpContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(PddlParser.NUMBER, 0); }
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class,0);
		}
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public FExp2Context fExp2() {
			return getRuleContext(FExp2Context.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public FExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFExp(this);
		}
	}

	public final FExpContext fExp() throws RecognitionException {
		FExpContext _localctx = new FExpContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_fExp);
		try {
			setState(528);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(515);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(516);
				match(T__0);
				setState(517);
				binaryOp();
				setState(518);
				fExp();
				setState(519);
				fExp2();
				setState(520);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(522);
				match(T__0);
				setState(523);
				match(T__3);
				setState(524);
				fExp();
				setState(525);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(527);
				fHead();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FExp2Context extends ParserRuleContext {
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public FExp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fExp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFExp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFExp2(this);
		}
	}

	public final FExp2Context fExp2() throws RecognitionException {
		FExp2Context _localctx = new FExp2Context(_ctx, getState());
		enterRule(_localctx, 76, RULE_fExp2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(530);
			fExp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FHeadContext extends ParserRuleContext {
		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class,0);
		}
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public FHeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fHead; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFHead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFHead(this);
		}
	}

	public final FHeadContext fHead() throws RecognitionException {
		FHeadContext _localctx = new FHeadContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_fHead);
		int _la;
		try {
			setState(543);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(532);
				match(T__0);
				setState(533);
				functionSymbol();
				setState(537);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME || _la==VARIABLE) {
					{
					{
					setState(534);
					term();
					}
					}
					setState(539);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(540);
				match(T__1);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(542);
				functionSymbol();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EffectContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<CEffectContext> cEffect() {
			return getRuleContexts(CEffectContext.class);
		}
		public CEffectContext cEffect(int i) {
			return getRuleContext(CEffectContext.class,i);
		}
		public EffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_effect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitEffect(this);
		}
	}

	public final EffectContext effect() throws RecognitionException {
		EffectContext _localctx = new EffectContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_effect);
		int _la;
		try {
			setState(555);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(545);
				match(T__0);
				setState(546);
				match(AND);
				setState(550);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(547);
					cEffect();
					}
					}
					setState(552);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(553);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(554);
				cEffect();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CEffectContext extends ParserRuleContext {
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public EffectContext effect() {
			return getRuleContext(EffectContext.class,0);
		}
		public TerminalNode WHEN() { return getToken(PddlParser.WHEN, 0); }
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public CondEffectContext condEffect() {
			return getRuleContext(CondEffectContext.class,0);
		}
		public PEffectContext pEffect() {
			return getRuleContext(PEffectContext.class,0);
		}
		public CEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cEffect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterCEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitCEffect(this);
		}
	}

	public final CEffectContext cEffect() throws RecognitionException {
		CEffectContext _localctx = new CEffectContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_cEffect);
		try {
			setState(572);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
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
				effect();
				setState(563);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(565);
				match(T__0);
				setState(566);
				match(WHEN);
				setState(567);
				goalDesc();
				setState(568);
				condEffect();
				setState(569);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(571);
				pEffect();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PEffectContext extends ParserRuleContext {
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public TerminalNode NOT() { return getToken(PddlParser.NOT, 0); }
		public AtomicTermFormulaContext atomicTermFormula() {
			return getRuleContext(AtomicTermFormulaContext.class,0);
		}
		public PEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pEffect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPEffect(this);
		}
	}

	public final PEffectContext pEffect() throws RecognitionException {
		PEffectContext _localctx = new PEffectContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_pEffect);
		try {
			setState(586);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(574);
				match(T__0);
				setState(575);
				assignOp();
				setState(576);
				fHead();
				setState(577);
				fExp();
				setState(578);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(580);
				match(T__0);
				setState(581);
				match(NOT);
				setState(582);
				atomicTermFormula();
				setState(583);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(585);
				atomicTermFormula();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondEffectContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<PEffectContext> pEffect() {
			return getRuleContexts(PEffectContext.class);
		}
		public PEffectContext pEffect(int i) {
			return getRuleContext(PEffectContext.class,i);
		}
		public CondEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condEffect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterCondEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitCondEffect(this);
		}
	}

	public final CondEffectContext condEffect() throws RecognitionException {
		CondEffectContext _localctx = new CondEffectContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_condEffect);
		int _la;
		try {
			setState(598);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(588);
				match(T__0);
				setState(589);
				match(AND);
				setState(593);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(590);
					pEffect();
					}
					}
					setState(595);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(596);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(597);
				pEffect();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryOpContext extends ParserRuleContext {
		public BinaryOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterBinaryOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitBinaryOp(this);
		}
	}

	public final BinaryOpContext binaryOp() throws RecognitionException {
		BinaryOpContext _localctx = new BinaryOpContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_binaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(600);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryCompContext extends ParserRuleContext {
		public BinaryCompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryComp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterBinaryComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitBinaryComp(this);
		}
	}

	public final BinaryCompContext binaryComp() throws RecognitionException {
		BinaryCompContext _localctx = new BinaryCompContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_binaryComp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(602);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0) || _la==EQUALS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignOpContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(PddlParser.ASSIGN, 0); }
		public TerminalNode SCALE_UP() { return getToken(PddlParser.SCALE_UP, 0); }
		public TerminalNode SCALE_DOWN() { return getToken(PddlParser.SCALE_DOWN, 0); }
		public TerminalNode INCREASE() { return getToken(PddlParser.INCREASE, 0); }
		public TerminalNode DECREASE() { return getToken(PddlParser.DECREASE, 0); }
		public AssignOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterAssignOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitAssignOp(this);
		}
	}

	public final AssignOpContext assignOp() throws RecognitionException {
		AssignOpContext _localctx = new AssignOpContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_assignOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << INCREASE) | (1L << DECREASE) | (1L << SCALE_UP) | (1L << SCALE_DOWN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurationConstraintContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<SimpleDurationConstraintContext> simpleDurationConstraint() {
			return getRuleContexts(SimpleDurationConstraintContext.class);
		}
		public SimpleDurationConstraintContext simpleDurationConstraint(int i) {
			return getRuleContext(SimpleDurationConstraintContext.class,i);
		}
		public DurationConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_durationConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDurationConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDurationConstraint(this);
		}
	}

	public final DurationConstraintContext durationConstraint() throws RecognitionException {
		DurationConstraintContext _localctx = new DurationConstraintContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_durationConstraint);
		int _la;
		try {
			setState(618);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(606);
				match(T__0);
				setState(607);
				match(AND);
				setState(609); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(608);
					simpleDurationConstraint();
					}
					}
					setState(611); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(613);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(615);
				match(T__0);
				setState(616);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(617);
				simpleDurationConstraint();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleDurationConstraintContext extends ParserRuleContext {
		public DurOpContext durOp() {
			return getRuleContext(DurOpContext.class,0);
		}
		public TerminalNode DURATION() { return getToken(PddlParser.DURATION, 0); }
		public DurValueContext durValue() {
			return getRuleContext(DurValueContext.class,0);
		}
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class,0);
		}
		public SimpleDurationConstraintContext simpleDurationConstraint() {
			return getRuleContext(SimpleDurationConstraintContext.class,0);
		}
		public SimpleDurationConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleDurationConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterSimpleDurationConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitSimpleDurationConstraint(this);
		}
	}

	public final SimpleDurationConstraintContext simpleDurationConstraint() throws RecognitionException {
		SimpleDurationConstraintContext _localctx = new SimpleDurationConstraintContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_simpleDurationConstraint);
		try {
			setState(632);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(620);
				match(T__0);
				setState(621);
				durOp();
				setState(622);
				match(T__11);
				setState(623);
				match(DURATION);
				setState(624);
				durValue();
				setState(625);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(627);
				match(T__0);
				setState(628);
				timeSpecifier();
				setState(629);
				simpleDurationConstraint();
				setState(630);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurOpContext extends ParserRuleContext {
		public DurOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_durOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDurOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDurOp(this);
		}
	}

	public final DurOpContext durOp() throws RecognitionException {
		DurOpContext _localctx = new DurOpContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_durOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(634);
			_la = _input.LA(1);
			if ( !(_la==T__9 || _la==T__10 || _la==EQUALS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DurValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(PddlParser.NUMBER, 0); }
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public DurValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_durValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDurValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDurValue(this);
		}
	}

	public final DurValueContext durValue() throws RecognitionException {
		DurValueContext _localctx = new DurValueContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_durValue);
		try {
			setState(638);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(636);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(637);
				fExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaEffectContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<DaEffectContext> daEffect() {
			return getRuleContexts(DaEffectContext.class);
		}
		public DaEffectContext daEffect(int i) {
			return getRuleContext(DaEffectContext.class,i);
		}
		public TimedEffectContext timedEffect() {
			return getRuleContext(TimedEffectContext.class,0);
		}
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public TerminalNode WHEN() { return getToken(PddlParser.WHEN, 0); }
		public DaGDContext daGD() {
			return getRuleContext(DaGDContext.class,0);
		}
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public FExpDAContext fExpDA() {
			return getRuleContext(FExpDAContext.class,0);
		}
		public DaEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_daEffect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterDaEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitDaEffect(this);
		}
	}

	public final DaEffectContext daEffect() throws RecognitionException {
		DaEffectContext _localctx = new DaEffectContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_daEffect);
		int _la;
		try {
			setState(670);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(640);
				match(T__0);
				setState(641);
				match(AND);
				setState(645);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(642);
					daEffect();
					}
					}
					setState(647);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(648);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(649);
				timedEffect();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(650);
				match(T__0);
				setState(651);
				match(FORALL);
				setState(652);
				match(T__0);
				setState(653);
				typedVariableList();
				setState(654);
				match(T__1);
				setState(655);
				daEffect();
				setState(656);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(658);
				match(T__0);
				setState(659);
				match(WHEN);
				setState(660);
				daGD();
				setState(661);
				timedEffect();
				setState(662);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(664);
				match(T__0);
				setState(665);
				assignOp();
				setState(666);
				fHead();
				setState(667);
				fExpDA();
				setState(668);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimedEffectContext extends ParserRuleContext {
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class,0);
		}
		public DaEffectContext daEffect() {
			return getRuleContext(DaEffectContext.class,0);
		}
		public FAssignDAContext fAssignDA() {
			return getRuleContext(FAssignDAContext.class,0);
		}
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public TimedEffectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timedEffect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterTimedEffect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitTimedEffect(this);
		}
	}

	public final TimedEffectContext timedEffect() throws RecognitionException {
		TimedEffectContext _localctx = new TimedEffectContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_timedEffect);
		try {
			setState(688);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(672);
				match(T__0);
				setState(673);
				timeSpecifier();
				setState(674);
				daEffect();
				setState(675);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(677);
				match(T__0);
				setState(678);
				timeSpecifier();
				setState(679);
				fAssignDA();
				setState(680);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(682);
				match(T__0);
				setState(683);
				assignOp();
				setState(684);
				fHead();
				setState(685);
				fExp();
				setState(686);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FAssignDAContext extends ParserRuleContext {
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public FExpDAContext fExpDA() {
			return getRuleContext(FExpDAContext.class,0);
		}
		public FAssignDAContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fAssignDA; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFAssignDA(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFAssignDA(this);
		}
	}

	public final FAssignDAContext fAssignDA() throws RecognitionException {
		FAssignDAContext _localctx = new FAssignDAContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_fAssignDA);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(690);
			match(T__0);
			setState(691);
			assignOp();
			setState(692);
			fHead();
			setState(693);
			fExpDA();
			setState(694);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FExpDAContext extends ParserRuleContext {
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class,0);
		}
		public List<FExpDAContext> fExpDA() {
			return getRuleContexts(FExpDAContext.class);
		}
		public FExpDAContext fExpDA(int i) {
			return getRuleContext(FExpDAContext.class,i);
		}
		public TerminalNode DURATION() { return getToken(PddlParser.DURATION, 0); }
		public FExpContext fExp() {
			return getRuleContext(FExpContext.class,0);
		}
		public FExpDAContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fExpDA; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterFExpDA(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitFExpDA(this);
		}
	}

	public final FExpDAContext fExpDA() throws RecognitionException {
		FExpDAContext _localctx = new FExpDAContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_fExpDA);
		try {
			setState(710);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(696);
				match(T__0);
				setState(703);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
				case 1:
					{
					{
					setState(697);
					binaryOp();
					setState(698);
					fExpDA();
					setState(699);
					fExpDA();
					}
					}
					break;
				case 2:
					{
					{
					setState(701);
					match(T__3);
					setState(702);
					fExpDA();
					}
					}
					break;
				}
				setState(705);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(707);
				match(T__11);
				setState(708);
				match(DURATION);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(709);
				fExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemContext extends ParserRuleContext {
		public TerminalNode DEFINE() { return getToken(PddlParser.DEFINE, 0); }
		public ProblemDeclContext problemDecl() {
			return getRuleContext(ProblemDeclContext.class,0);
		}
		public ProblemDomainContext problemDomain() {
			return getRuleContext(ProblemDomainContext.class,0);
		}
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public GoalContext goal() {
			return getRuleContext(GoalContext.class,0);
		}
		public RequireDefContext requireDef() {
			return getRuleContext(RequireDefContext.class,0);
		}
		public ObjectDeclContext objectDecl() {
			return getRuleContext(ObjectDeclContext.class,0);
		}
		public ProbConstraintsContext probConstraints() {
			return getRuleContext(ProbConstraintsContext.class,0);
		}
		public MetricSpecContext metricSpec() {
			return getRuleContext(MetricSpecContext.class,0);
		}
		public ProblemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_problem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterProblem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitProblem(this);
		}
	}

	public final ProblemContext problem() throws RecognitionException {
		ProblemContext _localctx = new ProblemContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_problem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(712);
			match(T__0);
			setState(713);
			match(DEFINE);
			setState(714);
			problemDecl();
			setState(715);
			problemDomain();
			setState(717);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(716);
				requireDef();
				}
				break;
			}
			setState(720);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				{
				setState(719);
				objectDecl();
				}
				break;
			}
			setState(722);
			init();
			setState(723);
			goal();
			setState(725);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				{
				setState(724);
				probConstraints();
				}
				break;
			}
			setState(728);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(727);
				metricSpec();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemDeclContext extends ParserRuleContext {
		public TerminalNode PROBLEM() { return getToken(PddlParser.PROBLEM, 0); }
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public ProblemDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_problemDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterProblemDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitProblemDecl(this);
		}
	}

	public final ProblemDeclContext problemDecl() throws RecognitionException {
		ProblemDeclContext _localctx = new ProblemDeclContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_problemDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			match(T__0);
			setState(731);
			match(PROBLEM);
			setState(732);
			match(NAME);
			setState(733);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProblemDomainContext extends ParserRuleContext {
		public TerminalNode DOMAIN() { return getToken(PddlParser.DOMAIN, 0); }
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public ProblemDomainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_problemDomain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterProblemDomain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitProblemDomain(this);
		}
	}

	public final ProblemDomainContext problemDomain() throws RecognitionException {
		ProblemDomainContext _localctx = new ProblemDomainContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_problemDomain);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(735);
			match(T__0);
			setState(736);
			match(T__2);
			setState(737);
			match(DOMAIN);
			setState(738);
			match(NAME);
			setState(739);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectDeclContext extends ParserRuleContext {
		public TerminalNode OBJECTS() { return getToken(PddlParser.OBJECTS, 0); }
		public TypedNameListContext typedNameList() {
			return getRuleContext(TypedNameListContext.class,0);
		}
		public ObjectDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterObjectDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitObjectDecl(this);
		}
	}

	public final ObjectDeclContext objectDecl() throws RecognitionException {
		ObjectDeclContext _localctx = new ObjectDeclContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_objectDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(741);
			match(T__0);
			setState(742);
			match(T__2);
			setState(743);
			match(OBJECTS);
			setState(744);
			typedNameList();
			setState(745);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitContext extends ParserRuleContext {
		public TerminalNode INIT() { return getToken(PddlParser.INIT, 0); }
		public List<InitElContext> initEl() {
			return getRuleContexts(InitElContext.class);
		}
		public InitElContext initEl(int i) {
			return getRuleContext(InitElContext.class,i);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitInit(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(747);
			match(T__0);
			setState(748);
			match(T__2);
			setState(749);
			match(INIT);
			setState(753);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(750);
				initEl();
				}
				}
				setState(755);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(756);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitElContext extends ParserRuleContext {
		public NameLiteralContext nameLiteral() {
			return getRuleContext(NameLiteralContext.class,0);
		}
		public FHeadContext fHead() {
			return getRuleContext(FHeadContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(PddlParser.NUMBER, 0); }
		public InitElContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initEl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterInitEl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitInitEl(this);
		}
	}

	public final InitElContext initEl() throws RecognitionException {
		InitElContext _localctx = new InitElContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_initEl);
		try {
			setState(770);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(758);
				nameLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(759);
				match(T__0);
				setState(760);
				match(EQUALS);
				setState(761);
				fHead();
				setState(762);
				match(NUMBER);
				setState(763);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(765);
				match(T__0);
				setState(766);
				match(NUMBER);
				setState(767);
				nameLiteral();
				setState(768);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameLiteralContext extends ParserRuleContext {
		public AtomicNameFormulaContext atomicNameFormula() {
			return getRuleContext(AtomicNameFormulaContext.class,0);
		}
		public TerminalNode NOT() { return getToken(PddlParser.NOT, 0); }
		public NameLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterNameLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitNameLiteral(this);
		}
	}

	public final NameLiteralContext nameLiteral() throws RecognitionException {
		NameLiteralContext _localctx = new NameLiteralContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_nameLiteral);
		try {
			setState(778);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(772);
				atomicNameFormula();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(773);
				match(T__0);
				setState(774);
				match(NOT);
				setState(775);
				atomicNameFormula();
				setState(776);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicNameFormulaContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public List<TerminalNode> NAME() { return getTokens(PddlParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(PddlParser.NAME, i);
		}
		public AtomicNameFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicNameFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterAtomicNameFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitAtomicNameFormula(this);
		}
	}

	public final AtomicNameFormulaContext atomicNameFormula() throws RecognitionException {
		AtomicNameFormulaContext _localctx = new AtomicNameFormulaContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_atomicNameFormula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780);
			match(T__0);
			setState(781);
			predicate();
			setState(785);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NAME) {
				{
				{
				setState(782);
				match(NAME);
				}
				}
				setState(787);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(788);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalContext extends ParserRuleContext {
		public TerminalNode GOAL() { return getToken(PddlParser.GOAL, 0); }
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterGoal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitGoal(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_goal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			match(T__0);
			setState(791);
			match(T__2);
			setState(792);
			match(GOAL);
			setState(793);
			goalDesc();
			setState(794);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProbConstraintsContext extends ParserRuleContext {
		public TerminalNode CONSTRAINTS() { return getToken(PddlParser.CONSTRAINTS, 0); }
		public PrefConGDContext prefConGD() {
			return getRuleContext(PrefConGDContext.class,0);
		}
		public ProbConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_probConstraints; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterProbConstraints(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitProbConstraints(this);
		}
	}

	public final ProbConstraintsContext probConstraints() throws RecognitionException {
		ProbConstraintsContext _localctx = new ProbConstraintsContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_probConstraints);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(796);
			match(T__0);
			setState(797);
			match(T__2);
			setState(798);
			match(CONSTRAINTS);
			setState(799);
			prefConGD();
			setState(800);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefConGDContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<PrefConGDContext> prefConGD() {
			return getRuleContexts(PrefConGDContext.class);
		}
		public PrefConGDContext prefConGD(int i) {
			return getRuleContext(PrefConGDContext.class,i);
		}
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public TerminalNode PREFERENCE() { return getToken(PddlParser.PREFERENCE, 0); }
		public ConGDContext conGD() {
			return getRuleContext(ConGDContext.class,0);
		}
		public TerminalNode NAME() { return getToken(PddlParser.NAME, 0); }
		public PrefConGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefConGD; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterPrefConGD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitPrefConGD(this);
		}
	}

	public final PrefConGDContext prefConGD() throws RecognitionException {
		PrefConGDContext _localctx = new PrefConGDContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_prefConGD);
		int _la;
		try {
			setState(828);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(802);
				match(T__0);
				setState(803);
				match(AND);
				setState(807);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(804);
					prefConGD();
					}
					}
					setState(809);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(810);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(811);
				match(T__0);
				setState(812);
				match(FORALL);
				setState(813);
				match(T__0);
				setState(814);
				typedVariableList();
				setState(815);
				match(T__1);
				setState(816);
				prefConGD();
				setState(817);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(819);
				match(T__0);
				setState(820);
				match(PREFERENCE);
				setState(822);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(821);
					match(NAME);
					}
				}

				setState(824);
				conGD();
				setState(825);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(827);
				conGD();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MetricSpecContext extends ParserRuleContext {
		public TerminalNode METRIC() { return getToken(PddlParser.METRIC, 0); }
		public OptimizationContext optimization() {
			return getRuleContext(OptimizationContext.class,0);
		}
		public MetricFExpContext metricFExp() {
			return getRuleContext(MetricFExpContext.class,0);
		}
		public MetricSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_metricSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterMetricSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitMetricSpec(this);
		}
	}

	public final MetricSpecContext metricSpec() throws RecognitionException {
		MetricSpecContext _localctx = new MetricSpecContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_metricSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(830);
			match(T__0);
			setState(831);
			match(T__2);
			setState(832);
			match(METRIC);
			setState(833);
			optimization();
			setState(834);
			metricFExp();
			setState(835);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptimizationContext extends ParserRuleContext {
		public TerminalNode MINIMIZE() { return getToken(PddlParser.MINIMIZE, 0); }
		public TerminalNode MAXIMIZE() { return getToken(PddlParser.MAXIMIZE, 0); }
		public OptimizationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optimization; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterOptimization(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitOptimization(this);
		}
	}

	public final OptimizationContext optimization() throws RecognitionException {
		OptimizationContext _localctx = new OptimizationContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_optimization);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(837);
			_la = _input.LA(1);
			if ( !(_la==MINIMIZE || _la==MAXIMIZE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MetricFExpContext extends ParserRuleContext {
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class,0);
		}
		public List<MetricFExpContext> metricFExp() {
			return getRuleContexts(MetricFExpContext.class);
		}
		public MetricFExpContext metricFExp(int i) {
			return getRuleContext(MetricFExpContext.class,i);
		}
		public TerminalNode NUMBER() { return getToken(PddlParser.NUMBER, 0); }
		public FunctionSymbolContext functionSymbol() {
			return getRuleContext(FunctionSymbolContext.class,0);
		}
		public List<TerminalNode> NAME() { return getTokens(PddlParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(PddlParser.NAME, i);
		}
		public TerminalNode TOTAL_TIME() { return getToken(PddlParser.TOTAL_TIME, 0); }
		public TerminalNode IS_VIOLATED() { return getToken(PddlParser.IS_VIOLATED, 0); }
		public MetricFExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_metricFExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterMetricFExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitMetricFExp(this);
		}
	}

	public final MetricFExpContext metricFExp() throws RecognitionException {
		MetricFExpContext _localctx = new MetricFExpContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_metricFExp);
		int _la;
		try {
			setState(877);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(839);
				match(T__0);
				setState(840);
				binaryOp();
				setState(841);
				metricFExp();
				setState(842);
				metricFExp();
				setState(843);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(845);
				match(T__0);
				setState(846);
				_la = _input.LA(1);
				if ( !(_la==T__4 || _la==T__6) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(847);
				metricFExp();
				setState(849); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(848);
					metricFExp();
					}
					}
					setState(851); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 || _la==TOTAL_TIME || _la==NAME || _la==NUMBER );
				setState(853);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(855);
				match(T__0);
				setState(856);
				match(T__3);
				setState(857);
				metricFExp();
				setState(858);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(860);
				match(NUMBER);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(861);
				match(T__0);
				setState(862);
				functionSymbol();
				setState(866);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(863);
					match(NAME);
					}
					}
					setState(868);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(869);
				match(T__1);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(871);
				functionSymbol();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(872);
				match(TOTAL_TIME);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(873);
				match(T__0);
				setState(874);
				match(IS_VIOLATED);
				setState(875);
				match(NAME);
				setState(876);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConGDContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(PddlParser.AND, 0); }
		public List<ConGDContext> conGD() {
			return getRuleContexts(ConGDContext.class);
		}
		public ConGDContext conGD(int i) {
			return getRuleContext(ConGDContext.class,i);
		}
		public TerminalNode FORALL() { return getToken(PddlParser.FORALL, 0); }
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public TerminalNode AT_END() { return getToken(PddlParser.AT_END, 0); }
		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}
		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class,i);
		}
		public TerminalNode ALWAYS() { return getToken(PddlParser.ALWAYS, 0); }
		public TerminalNode SOMETIME() { return getToken(PddlParser.SOMETIME, 0); }
		public TerminalNode WITHIN() { return getToken(PddlParser.WITHIN, 0); }
		public List<TerminalNode> NUMBER() { return getTokens(PddlParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(PddlParser.NUMBER, i);
		}
		public TerminalNode AT_MOST_ONCE() { return getToken(PddlParser.AT_MOST_ONCE, 0); }
		public TerminalNode SOMETIME_AFTER() { return getToken(PddlParser.SOMETIME_AFTER, 0); }
		public TerminalNode SOMETIME_BEFORE() { return getToken(PddlParser.SOMETIME_BEFORE, 0); }
		public TerminalNode ALWAYS_WITHIN() { return getToken(PddlParser.ALWAYS_WITHIN, 0); }
		public TerminalNode HOLD_DURING() { return getToken(PddlParser.HOLD_DURING, 0); }
		public TerminalNode HOLD_AFTER() { return getToken(PddlParser.HOLD_AFTER, 0); }
		public ConGDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conGD; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).enterConGD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PddlListener ) ((PddlListener)listener).exitConGD(this);
		}
	}

	public final ConGDContext conGD() throws RecognitionException {
		ConGDContext _localctx = new ConGDContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_conGD);
		int _la;
		try {
			setState(954);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(879);
				match(T__0);
				setState(880);
				match(AND);
				setState(884);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(881);
					conGD();
					}
					}
					setState(886);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(887);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(888);
				match(T__0);
				setState(889);
				match(FORALL);
				setState(890);
				match(T__0);
				setState(891);
				typedVariableList();
				setState(892);
				match(T__1);
				setState(893);
				conGD();
				setState(894);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(896);
				match(T__0);
				setState(897);
				match(AT_END);
				setState(898);
				goalDesc();
				setState(899);
				match(T__1);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(901);
				match(T__0);
				setState(902);
				match(ALWAYS);
				setState(903);
				goalDesc();
				setState(904);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(906);
				match(T__0);
				setState(907);
				match(SOMETIME);
				setState(908);
				goalDesc();
				setState(909);
				match(T__1);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(911);
				match(T__0);
				setState(912);
				match(WITHIN);
				setState(913);
				match(NUMBER);
				setState(914);
				goalDesc();
				setState(915);
				match(T__1);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(917);
				match(T__0);
				setState(918);
				match(AT_MOST_ONCE);
				setState(919);
				goalDesc();
				setState(920);
				match(T__1);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(922);
				match(T__0);
				setState(923);
				match(SOMETIME_AFTER);
				setState(924);
				goalDesc();
				setState(925);
				goalDesc();
				setState(926);
				match(T__1);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(928);
				match(T__0);
				setState(929);
				match(SOMETIME_BEFORE);
				setState(930);
				goalDesc();
				setState(931);
				goalDesc();
				setState(932);
				match(T__1);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(934);
				match(T__0);
				setState(935);
				match(ALWAYS_WITHIN);
				setState(936);
				match(NUMBER);
				setState(937);
				goalDesc();
				setState(938);
				goalDesc();
				setState(939);
				match(T__1);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(941);
				match(T__0);
				setState(942);
				match(HOLD_DURING);
				setState(943);
				match(NUMBER);
				setState(944);
				match(NUMBER);
				setState(945);
				goalDesc();
				setState(946);
				match(T__1);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(948);
				match(T__0);
				setState(949);
				match(HOLD_AFTER);
				setState(950);
				match(NUMBER);
				setState(951);
				goalDesc();
				setState(952);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3Z\u03bf\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\3\2\3\2\5"+
		"\2\u0091\n\2\3\3\3\3\3\3\3\3\5\3\u0097\n\3\3\3\5\3\u009a\n\3\3\3\5\3\u009d"+
		"\n\3\3\3\5\3\u00a0\n\3\3\3\5\3\u00a3\n\3\3\3\5\3\u00a6\n\3\3\3\7\3\u00a9"+
		"\n\3\f\3\16\3\u00ac\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\6"+
		"\5\u00b9\n\5\r\5\16\5\u00ba\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\7\7\u00c6"+
		"\n\7\f\7\16\7\u00c9\13\7\3\7\6\7\u00cc\n\7\r\7\16\7\u00cd\3\7\7\7\u00d1"+
		"\n\7\f\7\16\7\u00d4\13\7\5\7\u00d6\n\7\3\b\6\b\u00d9\n\b\r\b\16\b\u00da"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\6\t\u00e3\n\t\r\t\16\t\u00e4\3\t\3\t\3\t\5\t"+
		"\u00ea\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\6\f\u00f5\n\f\r\f"+
		"\16\f\u00f6\3\f\3\f\5\f\u00fb\n\f\7\f\u00fd\n\f\f\f\16\f\u0100\13\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\6\21\u0115\n\21\r\21\16\21\u0116\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\24\7\24\u0123\n\24\f\24\16\24\u0126\13\24"+
		"\3\24\6\24\u0129\n\24\r\24\16\24\u012a\3\24\7\24\u012e\n\24\f\24\16\24"+
		"\u0131\13\24\5\24\u0133\n\24\3\25\6\25\u0136\n\25\r\25\16\25\u0137\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\5\27\u0146\n\27"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\32\3\32\3\32\5\32\u015b\n\32\5\32\u015d\n\32\3\32\3\32\3"+
		"\32\3\32\3\32\5\32\u0164\n\32\5\32\u0166\n\32\3\33\3\33\3\33\3\33\7\33"+
		"\u016c\n\33\f\33\16\33\u016f\13\33\3\33\3\33\3\33\3\33\7\33\u0175\n\33"+
		"\f\33\16\33\u0178\13\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u019d"+
		"\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\7\35\u01a8\n\35\f\35"+
		"\16\35\u01ab\13\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \5 \u01c5\n \3 \3"+
		" \3 \3 \3 \5 \u01cc\n \5 \u01ce\n \3!\3!\3!\3!\7!\u01d4\n!\f!\16!\u01d7"+
		"\13!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u01e2\n!\3\"\3\"\3\"\3\"\5\"\u01e8"+
		"\n\"\3\"\3\"\3\"\5\"\u01ed\n\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u01f9"+
		"\n#\3$\3$\3%\3%\3&\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\5\'\u0213\n\'\3(\3(\3)\3)\3)\7)\u021a\n)\f)\16)\u021d"+
		"\13)\3)\3)\3)\5)\u0222\n)\3*\3*\3*\7*\u0227\n*\f*\16*\u022a\13*\3*\3*"+
		"\5*\u022e\n*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\5+\u023f\n+"+
		"\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u024d\n,\3-\3-\3-\7-\u0252\n-"+
		"\f-\16-\u0255\13-\3-\3-\5-\u0259\n-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3"+
		"\61\6\61\u0264\n\61\r\61\16\61\u0265\3\61\3\61\3\61\3\61\3\61\5\61\u026d"+
		"\n\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\5\62"+
		"\u027b\n\62\3\63\3\63\3\64\3\64\5\64\u0281\n\64\3\65\3\65\3\65\7\65\u0286"+
		"\n\65\f\65\16\65\u0289\13\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\5"+
		"\65\u02a1\n\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\5\66\u02b3\n\66\3\67\3\67\3\67\3\67\3\67\3\67"+
		"\38\38\38\38\38\38\38\58\u02c2\n8\38\38\38\38\38\58\u02c9\n8\39\39\39"+
		"\39\39\59\u02d0\n9\39\59\u02d3\n9\39\39\39\59\u02d8\n9\39\59\u02db\n9"+
		"\3:\3:\3:\3:\3:\3;\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\7=\u02f2"+
		"\n=\f=\16=\u02f5\13=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3>\3>\3>\3>\3>\5>\u0305"+
		"\n>\3?\3?\3?\3?\3?\3?\5?\u030d\n?\3@\3@\3@\7@\u0312\n@\f@\16@\u0315\13"+
		"@\3@\3@\3A\3A\3A\3A\3A\3A\3B\3B\3B\3B\3B\3B\3C\3C\3C\7C\u0328\nC\fC\16"+
		"C\u032b\13C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\5C\u0339\nC\3C\3C\3C\3"+
		"C\5C\u033f\nC\3D\3D\3D\3D\3D\3D\3D\3E\3E\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\6F\u0354\nF\rF\16F\u0355\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\7F\u0363\n"+
		"F\fF\16F\u0366\13F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u0370\nF\3G\3G\3G\7G\u0375"+
		"\nG\fG\16G\u0378\13G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\5G\u03bd\nG\3G\2\2H\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082"+
		"\u0084\u0086\u0088\u008a\u008c\2\n\3\2UV\3\2)*\3\2\6\t\4\2\n\rWW\3\2-"+
		"\61\4\2\f\rWW\3\2\66\67\4\2\7\7\t\t\2\u03eb\2\u0090\3\2\2\2\4\u0092\3"+
		"\2\2\2\6\u00af\3\2\2\2\b\u00b4\3\2\2\2\n\u00be\3\2\2\2\f\u00d5\3\2\2\2"+
		"\16\u00d8\3\2\2\2\20\u00e9\3\2\2\2\22\u00eb\3\2\2\2\24\u00ed\3\2\2\2\26"+
		"\u00fe\3\2\2\2\30\u0101\3\2\2\2\32\u0106\3\2\2\2\34\u0108\3\2\2\2\36\u010a"+
		"\3\2\2\2 \u0110\3\2\2\2\"\u011a\3\2\2\2$\u011f\3\2\2\2&\u0132\3\2\2\2"+
		"(\u0135\3\2\2\2*\u013c\3\2\2\2,\u0145\3\2\2\2.\u0147\3\2\2\2\60\u0153"+
		"\3\2\2\2\62\u015c\3\2\2\2\64\u019c\3\2\2\2\66\u019e\3\2\2\28\u01a4\3\2"+
		"\2\2:\u01ae\3\2\2\2<\u01b0\3\2\2\2>\u01cd\3\2\2\2@\u01e1\3\2\2\2B\u01ec"+
		"\3\2\2\2D\u01f8\3\2\2\2F\u01fa\3\2\2\2H\u01fc\3\2\2\2J\u01fe\3\2\2\2L"+
		"\u0212\3\2\2\2N\u0214\3\2\2\2P\u0221\3\2\2\2R\u022d\3\2\2\2T\u023e\3\2"+
		"\2\2V\u024c\3\2\2\2X\u0258\3\2\2\2Z\u025a\3\2\2\2\\\u025c\3\2\2\2^\u025e"+
		"\3\2\2\2`\u026c\3\2\2\2b\u027a\3\2\2\2d\u027c\3\2\2\2f\u0280\3\2\2\2h"+
		"\u02a0\3\2\2\2j\u02b2\3\2\2\2l\u02b4\3\2\2\2n\u02c8\3\2\2\2p\u02ca\3\2"+
		"\2\2r\u02dc\3\2\2\2t\u02e1\3\2\2\2v\u02e7\3\2\2\2x\u02ed\3\2\2\2z\u0304"+
		"\3\2\2\2|\u030c\3\2\2\2~\u030e\3\2\2\2\u0080\u0318\3\2\2\2\u0082\u031e"+
		"\3\2\2\2\u0084\u033e\3\2\2\2\u0086\u0340\3\2\2\2\u0088\u0347\3\2\2\2\u008a"+
		"\u036f\3\2\2\2\u008c\u03bc\3\2\2\2\u008e\u0091\5\4\3\2\u008f\u0091\5p"+
		"9\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\3\3\2\2\2\u0092\u0093"+
		"\7\3\2\2\u0093\u0094\7\20\2\2\u0094\u0096\5\6\4\2\u0095\u0097\5\b\5\2"+
		"\u0096\u0095\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u009a"+
		"\5\n\6\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b"+
		"\u009d\5\36\20\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3"+
		"\2\2\2\u009e\u00a0\5 \21\2\u009f\u009e\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a2\3\2\2\2\u00a1\u00a3\5\24\13\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3"+
		"\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a6\5*\26\2\u00a5\u00a4\3\2\2\2\u00a5"+
		"\u00a6\3\2\2\2\u00a6\u00aa\3\2\2\2\u00a7\u00a9\5,\27\2\u00a8\u00a7\3\2"+
		"\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7\4\2\2\u00ae\5\3\2\2\2"+
		"\u00af\u00b0\7\3\2\2\u00b0\u00b1\7\21\2\2\u00b1\u00b2\7U\2\2\u00b2\u00b3"+
		"\7\4\2\2\u00b3\7\3\2\2\2\u00b4\u00b5\7\3\2\2\u00b5\u00b6\7\5\2\2\u00b6"+
		"\u00b8\7\23\2\2\u00b7\u00b9\7\17\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3"+
		"\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc"+
		"\u00bd\7\4\2\2\u00bd\t\3\2\2\2\u00be\u00bf\7\3\2\2\u00bf\u00c0\7\5\2\2"+
		"\u00c0\u00c1\7\24\2\2\u00c1\u00c2\5\f\7\2\u00c2\u00c3\7\4\2\2\u00c3\13"+
		"\3\2\2\2\u00c4\u00c6\7U\2\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00d6\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00ca\u00cc\5\16\b\2\u00cb\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d2\3\2\2\2\u00cf\u00d1\7U"+
		"\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00c7\3\2"+
		"\2\2\u00d5\u00cb\3\2\2\2\u00d6\r\3\2\2\2\u00d7\u00d9\7U\2\2\u00d8\u00d7"+
		"\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\u00dc\3\2\2\2\u00dc\u00dd\7\6\2\2\u00dd\u00de\5\20\t\2\u00de\17\3\2\2"+
		"\2\u00df\u00e0\7\3\2\2\u00e0\u00e2\7\25\2\2\u00e1\u00e3\5\22\n\2\u00e2"+
		"\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\7\4\2\2\u00e7\u00ea\3\2\2\2\u00e8"+
		"\u00ea\5\22\n\2\u00e9\u00df\3\2\2\2\u00e9\u00e8\3\2\2\2\u00ea\21\3\2\2"+
		"\2\u00eb\u00ec\7U\2\2\u00ec\23\3\2\2\2\u00ed\u00ee\7\3\2\2\u00ee\u00ef"+
		"\7\5\2\2\u00ef\u00f0\7\26\2\2\u00f0\u00f1\5\26\f\2\u00f1\u00f2\7\4\2\2"+
		"\u00f2\25\3\2\2\2\u00f3\u00f5\5\30\r\2\u00f4\u00f3\3\2\2\2\u00f5\u00f6"+
		"\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8"+
		"\u00f9\7\6\2\2\u00f9\u00fb\5\34\17\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3"+
		"\2\2\2\u00fb\u00fd\3\2\2\2\u00fc\u00f4\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\27\3\2\2\2\u0100\u00fe\3\2\2"+
		"\2\u0101\u0102\7\3\2\2\u0102\u0103\5\32\16\2\u0103\u0104\5&\24\2\u0104"+
		"\u0105\7\4\2\2\u0105\31\3\2\2\2\u0106\u0107\7U\2\2\u0107\33\3\2\2\2\u0108"+
		"\u0109\7T\2\2\u0109\35\3\2\2\2\u010a\u010b\7\3\2\2\u010b\u010c\7\5\2\2"+
		"\u010c\u010d\7\27\2\2\u010d\u010e\5\f\7\2\u010e\u010f\7\4\2\2\u010f\37"+
		"\3\2\2\2\u0110\u0111\7\3\2\2\u0111\u0112\7\5\2\2\u0112\u0114\7\30\2\2"+
		"\u0113\u0115\5\"\22\2\u0114\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0114"+
		"\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\7\4\2\2\u0119"+
		"!\3\2\2\2\u011a\u011b\7\3\2\2\u011b\u011c\5$\23\2\u011c\u011d\5&\24\2"+
		"\u011d\u011e\7\4\2\2\u011e#\3\2\2\2\u011f\u0120\7U\2\2\u0120%\3\2\2\2"+
		"\u0121\u0123\7V\2\2\u0122\u0121\3\2\2\2\u0123\u0126\3\2\2\2\u0124\u0122"+
		"\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0133\3\2\2\2\u0126\u0124\3\2\2\2\u0127"+
		"\u0129\5(\25\2\u0128\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u0128\3\2"+
		"\2\2\u012a\u012b\3\2\2\2\u012b\u012f\3\2\2\2\u012c\u012e\7V\2\2\u012d"+
		"\u012c\3\2\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2"+
		"\2\2\u0130\u0133\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u0124\3\2\2\2\u0132"+
		"\u0128\3\2\2\2\u0133\'\3\2\2\2\u0134\u0136\7V\2\2\u0135\u0134\3\2\2\2"+
		"\u0136\u0137\3\2\2\2\u0137\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139"+
		"\3\2\2\2\u0139\u013a\7\6\2\2\u013a\u013b\5\20\t\2\u013b)\3\2\2\2\u013c"+
		"\u013d\7\3\2\2\u013d\u013e\7\5\2\2\u013e\u013f\7\31\2\2\u013f\u0140\5"+
		"\u008cG\2\u0140\u0141\7\4\2\2\u0141+\3\2\2\2\u0142\u0146\5.\30\2\u0143"+
		"\u0146\5<\37\2\u0144\u0146\5J&\2\u0145\u0142\3\2\2\2\u0145\u0143\3\2\2"+
		"\2\u0145\u0144\3\2\2\2\u0146-\3\2\2\2\u0147\u0148\7\3\2\2\u0148\u0149"+
		"\7\5\2\2\u0149\u014a\7\32\2\2\u014a\u014b\5\60\31\2\u014b\u014c\7\5\2"+
		"\2\u014c\u014d\7\33\2\2\u014d\u014e\7\3\2\2\u014e\u014f\5&\24\2\u014f"+
		"\u0150\7\4\2\2\u0150\u0151\5\62\32\2\u0151\u0152\7\4\2\2\u0152/\3\2\2"+
		"\2\u0153\u0154\7U\2\2\u0154\61\3\2\2\2\u0155\u0156\7\5\2\2\u0156\u015a"+
		"\7\34\2\2\u0157\u0158\7\3\2\2\u0158\u015b\7\4\2\2\u0159\u015b\5\64\33"+
		"\2\u015a\u0157\3\2\2\2\u015a\u0159\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u0155"+
		"\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u0165\3\2\2\2\u015e\u015f\7\5\2\2\u015f"+
		"\u0163\7\35\2\2\u0160\u0161\7\3\2\2\u0161\u0164\7\4\2\2\u0162\u0164\5"+
		"R*\2\u0163\u0160\3\2\2\2\u0163\u0162\3\2\2\2\u0164\u0166\3\2\2\2\u0165"+
		"\u015e\3\2\2\2\u0165\u0166\3\2\2\2\u0166\63\3\2\2\2\u0167\u019d\58\35"+
		"\2\u0168\u0169\7\3\2\2\u0169\u016d\7\36\2\2\u016a\u016c\5\64\33\2\u016b"+
		"\u016a\3\2\2\2\u016c\u016f\3\2\2\2\u016d\u016b\3\2\2\2\u016d\u016e\3\2"+
		"\2\2\u016e\u0170\3\2\2\2\u016f\u016d\3\2\2\2\u0170\u019d\7\4\2\2\u0171"+
		"\u0172\7\3\2\2\u0172\u0176\7\37\2\2\u0173\u0175\5\64\33\2\u0174\u0173"+
		"\3\2\2\2\u0175\u0178\3\2\2\2\u0176\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177"+
		"\u0179\3\2\2\2\u0178\u0176\3\2\2\2\u0179\u019d\7\4\2\2\u017a\u017b\7\3"+
		"\2\2\u017b\u017c\7 \2\2\u017c\u017d\5\64\33\2\u017d\u017e\7\4\2\2\u017e"+
		"\u019d\3\2\2\2\u017f\u0180\7\3\2\2\u0180\u0181\7!\2\2\u0181\u0182\5\64"+
		"\33\2\u0182\u0183\5\64\33\2\u0183\u0184\7\4\2\2\u0184\u019d\3\2\2\2\u0185"+
		"\u0186\7\3\2\2\u0186\u0187\7\"\2\2\u0187\u0188\7\3\2\2\u0188\u0189\5&"+
		"\24\2\u0189\u018a\7\4\2\2\u018a\u018b\5\64\33\2\u018b\u018c\7\4\2\2\u018c"+
		"\u019d\3\2\2\2\u018d\u018e\7\3\2\2\u018e\u018f\7#\2\2\u018f\u0190\7\3"+
		"\2\2\u0190\u0191\5&\24\2\u0191\u0192\7\4\2\2\u0192\u0193\5\64\33\2\u0193"+
		"\u0194\7\4\2\2\u0194\u019d\3\2\2\2\u0195\u019d\5\66\34\2\u0196\u0197\7"+
		"\3\2\2\u0197\u0198\7W\2\2\u0198\u0199\5:\36\2\u0199\u019a\5:\36\2\u019a"+
		"\u019b\7\4\2\2\u019b\u019d\3\2\2\2\u019c\u0167\3\2\2\2\u019c\u0168\3\2"+
		"\2\2\u019c\u0171\3\2\2\2\u019c\u017a\3\2\2\2\u019c\u017f\3\2\2\2\u019c"+
		"\u0185\3\2\2\2\u019c\u018d\3\2\2\2\u019c\u0195\3\2\2\2\u019c\u0196\3\2"+
		"\2\2\u019d\65\3\2\2\2\u019e\u019f\7\3\2\2\u019f\u01a0\5\\/\2\u01a0\u01a1"+
		"\5L\'\2\u01a1\u01a2\5L\'\2\u01a2\u01a3\7\4\2\2\u01a3\67\3\2\2\2\u01a4"+
		"\u01a5\7\3\2\2\u01a5\u01a9\5$\23\2\u01a6\u01a8\5:\36\2\u01a7\u01a6\3\2"+
		"\2\2\u01a8\u01ab\3\2\2\2\u01a9\u01a7\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa"+
		"\u01ac\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ac\u01ad\7\4\2\2\u01ad9\3\2\2\2"+
		"\u01ae\u01af\t\2\2\2\u01af;\3\2\2\2\u01b0\u01b1\7\3\2\2\u01b1\u01b2\7"+
		"\5\2\2\u01b2\u01b3\7$\2\2\u01b3\u01b4\5\60\31\2\u01b4\u01b5\7\5\2\2\u01b5"+
		"\u01b6\7\33\2\2\u01b6\u01b7\7\3\2\2\u01b7\u01b8\5&\24\2\u01b8\u01b9\7"+
		"\4\2\2\u01b9\u01ba\5> \2\u01ba\u01bb\7\4\2\2\u01bb=\3\2\2\2\u01bc\u01bd"+
		"\7\5\2\2\u01bd\u01be\7%\2\2\u01be\u01ce\5`\61\2\u01bf\u01c0\7\5\2\2\u01c0"+
		"\u01c4\7&\2\2\u01c1\u01c2\7\3\2\2\u01c2\u01c5\7\4\2\2\u01c3\u01c5\5@!"+
		"\2\u01c4\u01c1\3\2\2\2\u01c4\u01c3\3\2\2\2\u01c5\u01ce\3\2\2\2\u01c6\u01c7"+
		"\7\5\2\2\u01c7\u01cb\7\35\2\2\u01c8\u01c9\7\3\2\2\u01c9\u01cc\7\4\2\2"+
		"\u01ca\u01cc\5h\65\2\u01cb\u01c8\3\2\2\2\u01cb\u01ca\3\2\2\2\u01cc\u01ce"+
		"\3\2\2\2\u01cd\u01bc\3\2\2\2\u01cd\u01bf\3\2\2\2\u01cd\u01c6\3\2\2\2\u01ce"+
		"?\3\2\2\2\u01cf\u01e2\5B\"\2\u01d0\u01d1\7\3\2\2\u01d1\u01d5\7\36\2\2"+
		"\u01d2\u01d4\5@!\2\u01d3\u01d2\3\2\2\2\u01d4\u01d7\3\2\2\2\u01d5\u01d3"+
		"\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d8\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d8"+
		"\u01e2\7\4\2\2\u01d9\u01da\7\3\2\2\u01da\u01db\7#\2\2\u01db\u01dc\7\3"+
		"\2\2\u01dc\u01dd\5&\24\2\u01dd\u01de\7\4\2\2\u01de\u01df\5@!\2\u01df\u01e0"+
		"\7\4\2\2\u01e0\u01e2\3\2\2\2\u01e1\u01cf\3\2\2\2\u01e1\u01d0\3\2\2\2\u01e1"+
		"\u01d9\3\2\2\2\u01e2A\3\2\2\2\u01e3\u01ed\5D#\2\u01e4\u01e5\7\3\2\2\u01e5"+
		"\u01e7\7\'\2\2\u01e6\u01e8\7U\2\2\u01e7\u01e6\3\2\2\2\u01e7\u01e8\3\2"+
		"\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01ea\5D#\2\u01ea\u01eb\7\4\2\2\u01eb\u01ed"+
		"\3\2\2\2\u01ec\u01e3\3\2\2\2\u01ec\u01e4\3\2\2\2\u01edC\3\2\2\2\u01ee"+
		"\u01ef\7\3\2\2\u01ef\u01f0\5F$\2\u01f0\u01f1\5\64\33\2\u01f1\u01f2\7\4"+
		"\2\2\u01f2\u01f9\3\2\2\2\u01f3\u01f4\7\3\2\2\u01f4\u01f5\7(\2\2\u01f5"+
		"\u01f6\5\64\33\2\u01f6\u01f7\7\4\2\2\u01f7\u01f9\3\2\2\2\u01f8\u01ee\3"+
		"\2\2\2\u01f8\u01f3\3\2\2\2\u01f9E\3\2\2\2\u01fa\u01fb\t\3\2\2\u01fbG\3"+
		"\2\2\2\u01fc\u01fd\7(\2\2\u01fdI\3\2\2\2\u01fe\u01ff\7\3\2\2\u01ff\u0200"+
		"\7\5\2\2\u0200\u0201\7+\2\2\u0201\u0202\5\"\22\2\u0202\u0203\5\64\33\2"+
		"\u0203\u0204\7\4\2\2\u0204K\3\2\2\2\u0205\u0213\7X\2\2\u0206\u0207\7\3"+
		"\2\2\u0207\u0208\5Z.\2\u0208\u0209\5L\'\2\u0209\u020a\5N(\2\u020a\u020b"+
		"\7\4\2\2\u020b\u0213\3\2\2\2\u020c\u020d\7\3\2\2\u020d\u020e\7\6\2\2\u020e"+
		"\u020f\5L\'\2\u020f\u0210\7\4\2\2\u0210\u0213\3\2\2\2\u0211\u0213\5P)"+
		"\2\u0212\u0205\3\2\2\2\u0212\u0206\3\2\2\2\u0212\u020c\3\2\2\2\u0212\u0211"+
		"\3\2\2\2\u0213M\3\2\2\2\u0214\u0215\5L\'\2\u0215O\3\2\2\2\u0216\u0217"+
		"\7\3\2\2\u0217\u021b\5\32\16\2\u0218\u021a\5:\36\2\u0219\u0218\3\2\2\2"+
		"\u021a\u021d\3\2\2\2\u021b\u0219\3\2\2\2\u021b\u021c\3\2\2\2\u021c\u021e"+
		"\3\2\2\2\u021d\u021b\3\2\2\2\u021e\u021f\7\4\2\2\u021f\u0222\3\2\2\2\u0220"+
		"\u0222\5\32\16\2\u0221\u0216\3\2\2\2\u0221\u0220\3\2\2\2\u0222Q\3\2\2"+
		"\2\u0223\u0224\7\3\2\2\u0224\u0228\7\36\2\2\u0225\u0227\5T+\2\u0226\u0225"+
		"\3\2\2\2\u0227\u022a\3\2\2\2\u0228\u0226\3\2\2\2\u0228\u0229\3\2\2\2\u0229"+
		"\u022b\3\2\2\2\u022a\u0228\3\2\2\2\u022b\u022e\7\4\2\2\u022c\u022e\5T"+
		"+\2\u022d\u0223\3\2\2\2\u022d\u022c\3\2\2\2\u022eS\3\2\2\2\u022f\u0230"+
		"\7\3\2\2\u0230\u0231\7#\2\2\u0231\u0232\7\3\2\2\u0232\u0233\5&\24\2\u0233"+
		"\u0234\7\4\2\2\u0234\u0235\5R*\2\u0235\u0236\7\4\2\2\u0236\u023f\3\2\2"+
		"\2\u0237\u0238\7\3\2\2\u0238\u0239\7,\2\2\u0239\u023a\5\64\33\2\u023a"+
		"\u023b\5X-\2\u023b\u023c\7\4\2\2\u023c\u023f\3\2\2\2\u023d\u023f\5V,\2"+
		"\u023e\u022f\3\2\2\2\u023e\u0237\3\2\2\2\u023e\u023d\3\2\2\2\u023fU\3"+
		"\2\2\2\u0240\u0241\7\3\2\2\u0241\u0242\5^\60\2\u0242\u0243\5P)\2\u0243"+
		"\u0244\5L\'\2\u0244\u0245\7\4\2\2\u0245\u024d\3\2\2\2\u0246\u0247\7\3"+
		"\2\2\u0247\u0248\7 \2\2\u0248\u0249\58\35\2\u0249\u024a\7\4\2\2\u024a"+
		"\u024d\3\2\2\2\u024b\u024d\58\35\2\u024c\u0240\3\2\2\2\u024c\u0246\3\2"+
		"\2\2\u024c\u024b\3\2\2\2\u024dW\3\2\2\2\u024e\u024f\7\3\2\2\u024f\u0253"+
		"\7\36\2\2\u0250\u0252\5V,\2\u0251\u0250\3\2\2\2\u0252\u0255\3\2\2\2\u0253"+
		"\u0251\3\2\2\2\u0253\u0254\3\2\2\2\u0254\u0256\3\2\2\2\u0255\u0253\3\2"+
		"\2\2\u0256\u0259\7\4\2\2\u0257\u0259\5V,\2\u0258\u024e\3\2\2\2\u0258\u0257"+
		"\3\2\2\2\u0259Y\3\2\2\2\u025a\u025b\t\4\2\2\u025b[\3\2\2\2\u025c\u025d"+
		"\t\5\2\2\u025d]\3\2\2\2\u025e\u025f\t\6\2\2\u025f_\3\2\2\2\u0260\u0261"+
		"\7\3\2\2\u0261\u0263\7\36\2\2\u0262\u0264\5b\62\2\u0263\u0262\3\2\2\2"+
		"\u0264\u0265\3\2\2\2\u0265\u0263\3\2\2\2\u0265\u0266\3\2\2\2\u0266\u0267"+
		"\3\2\2\2\u0267\u0268\7\4\2\2\u0268\u026d\3\2\2\2\u0269\u026a\7\3\2\2\u026a"+
		"\u026d\7\4\2\2\u026b\u026d\5b\62\2\u026c\u0260\3\2\2\2\u026c\u0269\3\2"+
		"\2\2\u026c\u026b\3\2\2\2\u026da\3\2\2\2\u026e\u026f\7\3\2\2\u026f\u0270"+
		"\5d\63\2\u0270\u0271\7\16\2\2\u0271\u0272\7%\2\2\u0272\u0273\5f\64\2\u0273"+
		"\u0274\7\4\2\2\u0274\u027b\3\2\2\2\u0275\u0276\7\3\2\2\u0276\u0277\5F"+
		"$\2\u0277\u0278\5b\62\2\u0278\u0279\7\4\2\2\u0279\u027b\3\2\2\2\u027a"+
		"\u026e\3\2\2\2\u027a\u0275\3\2\2\2\u027bc\3\2\2\2\u027c\u027d\t\7\2\2"+
		"\u027de\3\2\2\2\u027e\u0281\7X\2\2\u027f\u0281\5L\'\2\u0280\u027e\3\2"+
		"\2\2\u0280\u027f\3\2\2\2\u0281g\3\2\2\2\u0282\u0283\7\3\2\2\u0283\u0287"+
		"\7\36\2\2\u0284\u0286\5h\65\2\u0285\u0284\3\2\2\2\u0286\u0289\3\2\2\2"+
		"\u0287\u0285\3\2\2\2\u0287\u0288\3\2\2\2\u0288\u028a\3\2\2\2\u0289\u0287"+
		"\3\2\2\2\u028a\u02a1\7\4\2\2\u028b\u02a1\5j\66\2\u028c\u028d\7\3\2\2\u028d"+
		"\u028e\7#\2\2\u028e\u028f\7\3\2\2\u028f\u0290\5&\24\2\u0290\u0291\7\4"+
		"\2\2\u0291\u0292\5h\65\2\u0292\u0293\7\4\2\2\u0293\u02a1\3\2\2\2\u0294"+
		"\u0295\7\3\2\2\u0295\u0296\7,\2\2\u0296\u0297\5@!\2\u0297\u0298\5j\66"+
		"\2\u0298\u0299\7\4\2\2\u0299\u02a1\3\2\2\2\u029a\u029b\7\3\2\2\u029b\u029c"+
		"\5^\60\2\u029c\u029d\5P)\2\u029d\u029e\5n8\2\u029e\u029f\7\4\2\2\u029f"+
		"\u02a1\3\2\2\2\u02a0\u0282\3\2\2\2\u02a0\u028b\3\2\2\2\u02a0\u028c\3\2"+
		"\2\2\u02a0\u0294\3\2\2\2\u02a0\u029a\3\2\2\2\u02a1i\3\2\2\2\u02a2\u02a3"+
		"\7\3\2\2\u02a3\u02a4\5F$\2\u02a4\u02a5\5h\65\2\u02a5\u02a6\7\4\2\2\u02a6"+
		"\u02b3\3\2\2\2\u02a7\u02a8\7\3\2\2\u02a8\u02a9\5F$\2\u02a9\u02aa\5l\67"+
		"\2\u02aa\u02ab\7\4\2\2\u02ab\u02b3\3\2\2\2\u02ac\u02ad\7\3\2\2\u02ad\u02ae"+
		"\5^\60\2\u02ae\u02af\5P)\2\u02af\u02b0\5L\'\2\u02b0\u02b1\7\4\2\2\u02b1"+
		"\u02b3\3\2\2\2\u02b2\u02a2\3\2\2\2\u02b2\u02a7\3\2\2\2\u02b2\u02ac\3\2"+
		"\2\2\u02b3k\3\2\2\2\u02b4\u02b5\7\3\2\2\u02b5\u02b6\5^\60\2\u02b6\u02b7"+
		"\5P)\2\u02b7\u02b8\5n8\2\u02b8\u02b9\7\4\2\2\u02b9m\3\2\2\2\u02ba\u02c1"+
		"\7\3\2\2\u02bb\u02bc\5Z.\2\u02bc\u02bd\5n8\2\u02bd\u02be\5n8\2\u02be\u02c2"+
		"\3\2\2\2\u02bf\u02c0\7\6\2\2\u02c0\u02c2\5n8\2\u02c1\u02bb\3\2\2\2\u02c1"+
		"\u02bf\3\2\2\2\u02c2\u02c3\3\2\2\2\u02c3\u02c4\7\4\2\2\u02c4\u02c9\3\2"+
		"\2\2\u02c5\u02c6\7\16\2\2\u02c6\u02c9\7%\2\2\u02c7\u02c9\5L\'\2\u02c8"+
		"\u02ba\3\2\2\2\u02c8\u02c5\3\2\2\2\u02c8\u02c7\3\2\2\2\u02c9o\3\2\2\2"+
		"\u02ca\u02cb\7\3\2\2\u02cb\u02cc\7\20\2\2\u02cc\u02cd\5r:\2\u02cd\u02cf"+
		"\5t;\2\u02ce\u02d0\5\b\5\2\u02cf\u02ce\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0"+
		"\u02d2\3\2\2\2\u02d1\u02d3\5v<\2\u02d2\u02d1\3\2\2\2\u02d2\u02d3\3\2\2"+
		"\2\u02d3\u02d4\3\2\2\2\u02d4\u02d5\5x=\2\u02d5\u02d7\5\u0080A\2\u02d6"+
		"\u02d8\5\u0082B\2\u02d7\u02d6\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02da"+
		"\3\2\2\2\u02d9\u02db\5\u0086D\2\u02da\u02d9\3\2\2\2\u02da\u02db\3\2\2"+
		"\2\u02dbq\3\2\2\2\u02dc\u02dd\7\3\2\2\u02dd\u02de\7\22\2\2\u02de\u02df"+
		"\7U\2\2\u02df\u02e0\7\4\2\2\u02e0s\3\2\2\2\u02e1\u02e2\7\3\2\2\u02e2\u02e3"+
		"\7\5\2\2\u02e3\u02e4\7\21\2\2\u02e4\u02e5\7U\2\2\u02e5\u02e6\7\4\2\2\u02e6"+
		"u\3\2\2\2\u02e7\u02e8\7\3\2\2\u02e8\u02e9\7\5\2\2\u02e9\u02ea\7\62\2\2"+
		"\u02ea\u02eb\5\f\7\2\u02eb\u02ec\7\4\2\2\u02ecw\3\2\2\2\u02ed\u02ee\7"+
		"\3\2\2\u02ee\u02ef\7\5\2\2\u02ef\u02f3\7\63\2\2\u02f0\u02f2\5z>\2\u02f1"+
		"\u02f0\3\2\2\2\u02f2\u02f5\3\2\2\2\u02f3\u02f1\3\2\2\2\u02f3\u02f4\3\2"+
		"\2\2\u02f4\u02f6\3\2\2\2\u02f5\u02f3\3\2\2\2\u02f6\u02f7\7\4\2\2\u02f7"+
		"y\3\2\2\2\u02f8\u0305\5|?\2\u02f9\u02fa\7\3\2\2\u02fa\u02fb\7W\2\2\u02fb"+
		"\u02fc\5P)\2\u02fc\u02fd\7X\2\2\u02fd\u02fe\7\4\2\2\u02fe\u0305\3\2\2"+
		"\2\u02ff\u0300\7\3\2\2\u0300\u0301\7X\2\2\u0301\u0302\5|?\2\u0302\u0303"+
		"\7\4\2\2\u0303\u0305\3\2\2\2\u0304\u02f8\3\2\2\2\u0304\u02f9\3\2\2\2\u0304"+
		"\u02ff\3\2\2\2\u0305{\3\2\2\2\u0306\u030d\5~@\2\u0307\u0308\7\3\2\2\u0308"+
		"\u0309\7 \2\2\u0309\u030a\5~@\2\u030a\u030b\7\4\2\2\u030b\u030d\3\2\2"+
		"\2\u030c\u0306\3\2\2\2\u030c\u0307\3\2\2\2\u030d}\3\2\2\2\u030e\u030f"+
		"\7\3\2\2\u030f\u0313\5$\23\2\u0310\u0312\7U\2\2\u0311\u0310\3\2\2\2\u0312"+
		"\u0315\3\2\2\2\u0313\u0311\3\2\2\2\u0313\u0314\3\2\2\2\u0314\u0316\3\2"+
		"\2\2\u0315\u0313\3\2\2\2\u0316\u0317\7\4\2\2\u0317\177\3\2\2\2\u0318\u0319"+
		"\7\3\2\2\u0319\u031a\7\5\2\2\u031a\u031b\7\64\2\2\u031b\u031c\5\64\33"+
		"\2\u031c\u031d\7\4\2\2\u031d\u0081\3\2\2\2\u031e\u031f\7\3\2\2\u031f\u0320"+
		"\7\5\2\2\u0320\u0321\7\31\2\2\u0321\u0322\5\u0084C\2\u0322\u0323\7\4\2"+
		"\2\u0323\u0083\3\2\2\2\u0324\u0325\7\3\2\2\u0325\u0329\7\36\2\2\u0326"+
		"\u0328\5\u0084C\2\u0327\u0326\3\2\2\2\u0328\u032b\3\2\2\2\u0329\u0327"+
		"\3\2\2\2\u0329\u032a\3\2\2\2\u032a\u032c\3\2\2\2\u032b\u0329\3\2\2\2\u032c"+
		"\u033f\7\4\2\2\u032d\u032e\7\3\2\2\u032e\u032f\7#\2\2\u032f\u0330\7\3"+
		"\2\2\u0330\u0331\5&\24\2\u0331\u0332\7\4\2\2\u0332\u0333\5\u0084C\2\u0333"+
		"\u0334\7\4\2\2\u0334\u033f\3\2\2\2\u0335\u0336\7\3\2\2\u0336\u0338\7\'"+
		"\2\2\u0337\u0339\7U\2\2\u0338\u0337\3\2\2\2\u0338\u0339\3\2\2\2\u0339"+
		"\u033a\3\2\2\2\u033a\u033b\5\u008cG\2\u033b\u033c\7\4\2\2\u033c\u033f"+
		"\3\2\2\2\u033d\u033f\5\u008cG\2\u033e\u0324\3\2\2\2\u033e\u032d\3\2\2"+
		"\2\u033e\u0335\3\2\2\2\u033e\u033d\3\2\2\2\u033f\u0085\3\2\2\2\u0340\u0341"+
		"\7\3\2\2\u0341\u0342\7\5\2\2\u0342\u0343\7\65\2\2\u0343\u0344\5\u0088"+
		"E\2\u0344\u0345\5\u008aF\2\u0345\u0346\7\4\2\2\u0346\u0087\3\2\2\2\u0347"+
		"\u0348\t\b\2\2\u0348\u0089\3\2\2\2\u0349\u034a\7\3\2\2\u034a\u034b\5Z"+
		".\2\u034b\u034c\5\u008aF\2\u034c\u034d\5\u008aF\2\u034d\u034e\7\4\2\2"+
		"\u034e\u0370\3\2\2\2\u034f\u0350\7\3\2\2\u0350\u0351\t\t\2\2\u0351\u0353"+
		"\5\u008aF\2\u0352\u0354\5\u008aF\2\u0353\u0352\3\2\2\2\u0354\u0355\3\2"+
		"\2\2\u0355\u0353\3\2\2\2\u0355\u0356\3\2\2\2\u0356\u0357\3\2\2\2\u0357"+
		"\u0358\7\4\2\2\u0358\u0370\3\2\2\2\u0359\u035a\7\3\2\2\u035a\u035b\7\6"+
		"\2\2\u035b\u035c\5\u008aF\2\u035c\u035d\7\4\2\2\u035d\u0370\3\2\2\2\u035e"+
		"\u0370\7X\2\2\u035f\u0360\7\3\2\2\u0360\u0364\5\32\16\2\u0361\u0363\7"+
		"U\2\2\u0362\u0361\3\2\2\2\u0363\u0366\3\2\2\2\u0364\u0362\3\2\2\2\u0364"+
		"\u0365\3\2\2\2\u0365\u0367\3\2\2\2\u0366\u0364\3\2\2\2\u0367\u0368\7\4"+
		"\2\2\u0368\u0370\3\2\2\2\u0369\u0370\5\32\16\2\u036a\u0370\78\2\2\u036b"+
		"\u036c\7\3\2\2\u036c\u036d\79\2\2\u036d\u036e\7U\2\2\u036e\u0370\7\4\2"+
		"\2\u036f\u0349\3\2\2\2\u036f\u034f\3\2\2\2\u036f\u0359\3\2\2\2\u036f\u035e"+
		"\3\2\2\2\u036f\u035f\3\2\2\2\u036f\u0369\3\2\2\2\u036f\u036a\3\2\2\2\u036f"+
		"\u036b\3\2\2\2\u0370\u008b\3\2\2\2\u0371\u0372\7\3\2\2\u0372\u0376\7\36"+
		"\2\2\u0373\u0375\5\u008cG\2\u0374\u0373\3\2\2\2\u0375\u0378\3\2\2\2\u0376"+
		"\u0374\3\2\2\2\u0376\u0377\3\2\2\2\u0377\u0379\3\2\2\2\u0378\u0376\3\2"+
		"\2\2\u0379\u03bd\7\4\2\2\u037a\u037b\7\3\2\2\u037b\u037c\7#\2\2\u037c"+
		"\u037d\7\3\2\2\u037d\u037e\5&\24\2\u037e\u037f\7\4\2\2\u037f\u0380\5\u008c"+
		"G\2\u0380\u0381\7\4\2\2\u0381\u03bd\3\2\2\2\u0382\u0383\7\3\2\2\u0383"+
		"\u0384\7*\2\2\u0384\u0385\5\64\33\2\u0385\u0386\7\4\2\2\u0386\u03bd\3"+
		"\2\2\2\u0387\u0388\7\3\2\2\u0388\u0389\7:\2\2\u0389\u038a\5\64\33\2\u038a"+
		"\u038b\7\4\2\2\u038b\u03bd\3\2\2\2\u038c\u038d\7\3\2\2\u038d\u038e\7;"+
		"\2\2\u038e\u038f\5\64\33\2\u038f\u0390\7\4\2\2\u0390\u03bd\3\2\2\2\u0391"+
		"\u0392\7\3\2\2\u0392\u0393\7<\2\2\u0393\u0394\7X\2\2\u0394\u0395\5\64"+
		"\33\2\u0395\u0396\7\4\2\2\u0396\u03bd\3\2\2\2\u0397\u0398\7\3\2\2\u0398"+
		"\u0399\7=\2\2\u0399\u039a\5\64\33\2\u039a\u039b\7\4\2\2\u039b\u03bd\3"+
		"\2\2\2\u039c\u039d\7\3\2\2\u039d\u039e\7>\2\2\u039e\u039f\5\64\33\2\u039f"+
		"\u03a0\5\64\33\2\u03a0\u03a1\7\4\2\2\u03a1\u03bd\3\2\2\2\u03a2\u03a3\7"+
		"\3\2\2\u03a3\u03a4\7?\2\2\u03a4\u03a5\5\64\33\2\u03a5\u03a6\5\64\33\2"+
		"\u03a6\u03a7\7\4\2\2\u03a7\u03bd\3\2\2\2\u03a8\u03a9\7\3\2\2\u03a9\u03aa"+
		"\7@\2\2\u03aa\u03ab\7X\2\2\u03ab\u03ac\5\64\33\2\u03ac\u03ad\5\64\33\2"+
		"\u03ad\u03ae\7\4\2\2\u03ae\u03bd\3\2\2\2\u03af\u03b0\7\3\2\2\u03b0\u03b1"+
		"\7A\2\2\u03b1\u03b2\7X\2\2\u03b2\u03b3\7X\2\2\u03b3\u03b4\5\64\33\2\u03b4"+
		"\u03b5\7\4\2\2\u03b5\u03bd\3\2\2\2\u03b6\u03b7\7\3\2\2\u03b7\u03b8\7B"+
		"\2\2\u03b8\u03b9\7X\2\2\u03b9\u03ba\5\64\33\2\u03ba\u03bb\7\4\2\2\u03bb"+
		"\u03bd\3\2\2\2\u03bc\u0371\3\2\2\2\u03bc\u037a\3\2\2\2\u03bc\u0382\3\2"+
		"\2\2\u03bc\u0387\3\2\2\2\u03bc\u038c\3\2\2\2\u03bc\u0391\3\2\2\2\u03bc"+
		"\u0397\3\2\2\2\u03bc\u039c\3\2\2\2\u03bc\u03a2\3\2\2\2\u03bc\u03a8\3\2"+
		"\2\2\u03bc\u03af\3\2\2\2\u03bc\u03b6\3\2\2\2\u03bd\u008d\3\2\2\2N\u0090"+
		"\u0096\u0099\u009c\u009f\u00a2\u00a5\u00aa\u00ba\u00c7\u00cd\u00d2\u00d5"+
		"\u00da\u00e4\u00e9\u00f6\u00fa\u00fe\u0116\u0124\u012a\u012f\u0132\u0137"+
		"\u0145\u015a\u015c\u0163\u0165\u016d\u0176\u019c\u01a9\u01c4\u01cb\u01cd"+
		"\u01d5\u01e1\u01e7\u01ec\u01f8\u0212\u021b\u0221\u0228\u022d\u023e\u024c"+
		"\u0253\u0258\u0265\u026c\u027a\u0280\u0287\u02a0\u02b2\u02c1\u02c8\u02cf"+
		"\u02d2\u02d7\u02da\u02f3\u0304\u030c\u0313\u0329\u0338\u033e\u0355\u0364"+
		"\u036f\u0376\u03bc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}