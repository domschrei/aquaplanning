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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, REQUIRE_KEY=66, 
		DOMAIN=67, DOMAIN_NAME=68, REQUIREMENTS=69, TYPES=70, EITHER_TYPE=71, 
		CONSTANTS=72, FUNCTIONS=73, PREDICATES=74, ACTION=75, DURATIVE_ACTION=76, 
		PROBLEM=77, PROBLEM_NAME=78, PROBLEM_DOMAIN=79, OBJECTS=80, INIT=81, FUNC_HEAD=82, 
		PRECONDITION=83, EFFECT=84, AND_GD=85, OR_GD=86, NOT_GD=87, IMPLY_GD=88, 
		EXISTS_GD=89, FORALL_GD=90, COMPARISON_GD=91, AND_EFFECT=92, FORALL_EFFECT=93, 
		WHEN_EFFECT=94, ASSIGN_EFFECT=95, NOT_EFFECT=96, PRED_HEAD=97, GOAL=98, 
		BINARY_OP=99, UNARY_MINUS=100, INIT_EQ=101, INIT_AT=102, NOT_PRED_INIT=103, 
		PRED_INST=104, PROBLEM_CONSTRAINT=105, PROBLEM_METRIC=106, EQUALS=107, 
		NAME=108, VARIABLE=109, NUMBER=110, AT=111, LINE_COMMENT=112, WHITESPACE=113;
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
		null, "'('", "'define'", "')'", "'domain'", "':requirements'", "':types'", 
		"'-'", "'either'", "':functions'", "'number'", "':constants'", "':predicates'", 
		"':constraints'", "':action'", "':parameters'", "':precondition'", "':effect'", 
		"'and'", "'or'", "'not'", "'imply'", "'exists'", "'forall'", "':durative-action'", 
		"':duration'", "':condition'", "'preference'", "'over'", "'start'", "'end'", 
		"'all'", "':derived'", "'when'", "'*'", "'+'", "'/'", "'>'", "'<'", "'>='", 
		"'<='", "'assign'", "'scale-up'", "'scale-down'", "'increase'", "'decrease'", 
		"'?duration'", "'problem'", "':domain'", "':objects'", "':init'", "':goal'", 
		"':metric'", "'minimize'", "'maximize'", "'total-time'", "'is-violated'", 
		"'always'", "'sometime'", "'within'", "'at-most-once'", "'sometime-after'", 
		"'sometime-before'", "'always-within'", "'hold-during'", "'hold-after'", 
		null, "'DOMAIN'", "'DOMAIN_NAME'", "'REQUIREMENTS'", "'TYPES'", "'EITHER_TYPE'", 
		"'CONSTANTS'", "'FUNCTIONS'", "'PREDICATES'", "'ACTION'", "'DURATIVE_ACTION'", 
		"'PROBLEM'", "'PROBLEM_NAME'", "'PROBLEM_DOMAIN'", "'OBJECTS'", "'INIT'", 
		"'FUNC_HEAD'", "'PRECONDITION'", "'EFFECT'", "'AND_GD'", "'OR_GD'", "'NOT_GD'", 
		"'IMPLY_GD'", "'EXISTS_GD'", "'FORALL_GD'", "'COMPARISON_GD'", "'AND_EFFECT'", 
		"'FORALL_EFFECT'", "'WHEN_EFFECT'", "'ASSIGN_EFFECT'", "'NOT_EFFECT'", 
		"'PRED_HEAD'", "'GOAL'", "'BINARY_OP'", "'UNARY_MINUS'", "'INIT_EQ'", 
		"'INIT_AT'", "'NOT_PRED_INIT'", "'PRED_INST'", "'PROBLEM_CONSTRAINT'", 
		"'PROBLEM_METRIC'", "'='", null, null, null, "'at'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "REQUIRE_KEY", "DOMAIN", "DOMAIN_NAME", 
		"REQUIREMENTS", "TYPES", "EITHER_TYPE", "CONSTANTS", "FUNCTIONS", "PREDICATES", 
		"ACTION", "DURATIVE_ACTION", "PROBLEM", "PROBLEM_NAME", "PROBLEM_DOMAIN", 
		"OBJECTS", "INIT", "FUNC_HEAD", "PRECONDITION", "EFFECT", "AND_GD", "OR_GD", 
		"NOT_GD", "IMPLY_GD", "EXISTS_GD", "FORALL_GD", "COMPARISON_GD", "AND_EFFECT", 
		"FORALL_EFFECT", "WHEN_EFFECT", "ASSIGN_EFFECT", "NOT_EFFECT", "PRED_HEAD", 
		"GOAL", "BINARY_OP", "UNARY_MINUS", "INIT_EQ", "INIT_AT", "NOT_PRED_INIT", 
		"PRED_INST", "PROBLEM_CONSTRAINT", "PROBLEM_METRIC", "EQUALS", "NAME", 
		"VARIABLE", "NUMBER", "AT", "LINE_COMMENT", "WHITESPACE"
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
			match(T__1);
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
			match(T__2);
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
			match(T__3);
			setState(175);
			match(NAME);
			setState(176);
			match(T__2);
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
			match(T__4);
			setState(181); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(180);
				match(REQUIRE_KEY);
				}
				}
				setState(183); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==REQUIRE_KEY );
			setState(185);
			match(T__2);
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
			setState(187);
			match(T__0);
			setState(188);
			match(T__5);
			setState(189);
			typedNameList();
			setState(190);
			match(T__2);
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
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(192);
					match(NAME);
					}
					}
					setState(197);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				{
				setState(199); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(198);
						singleTypeNameList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(201); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(203);
					match(NAME);
					}
					}
					setState(208);
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
			setState(212); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(211);
				match(NAME);
				}
				}
				setState(214); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NAME );
			setState(216);
			match(T__6);
			setState(217);
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
			setState(229);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(219);
				match(T__0);
				setState(220);
				match(T__7);
				setState(222); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(221);
					primType();
					}
					}
					setState(224); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NAME );
				setState(226);
				match(T__2);
				}
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(228);
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
			setState(231);
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
			setState(233);
			match(T__0);
			setState(234);
			match(T__8);
			setState(235);
			functionList();
			setState(236);
			match(T__2);
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
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(239); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(238);
						atomicFunctionSkeleton();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(241); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(243);
					match(T__6);
					setState(244);
					functionType();
					}
				}

				}
				}
				setState(251);
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
			setState(252);
			match(T__0);
			setState(253);
			functionSymbol();
			setState(254);
			typedVariableList();
			setState(255);
			match(T__2);
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
			setState(257);
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
			setState(259);
			match(T__9);
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
			setState(261);
			match(T__0);
			setState(262);
			match(T__10);
			setState(263);
			typedNameList();
			setState(264);
			match(T__2);
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
			setState(266);
			match(T__0);
			setState(267);
			match(T__11);
			setState(269); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(268);
				atomicFormulaSkeleton();
				}
				}
				setState(271); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(273);
			match(T__2);
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
			setState(275);
			match(T__0);
			setState(276);
			predicate();
			setState(277);
			typedVariableList();
			setState(278);
			match(T__2);
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
			setState(280);
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
			setState(299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==VARIABLE) {
					{
					{
					setState(282);
					match(VARIABLE);
					}
					}
					setState(287);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				{
				setState(289); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(288);
						singleTypeVarList();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(291); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==VARIABLE) {
					{
					{
					setState(293);
					match(VARIABLE);
					}
					}
					setState(298);
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
			setState(302); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(301);
				match(VARIABLE);
				}
				}
				setState(304); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VARIABLE );
			setState(306);
			match(T__6);
			setState(307);
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
			setState(309);
			match(T__0);
			setState(310);
			match(T__12);
			setState(311);
			conGD();
			setState(312);
			match(T__2);
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
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(314);
				actionDef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(315);
				durativeActionDef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(316);
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
		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class,0);
		}
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
			setState(319);
			match(T__0);
			setState(320);
			match(T__13);
			setState(321);
			actionSymbol();
			setState(322);
			match(T__14);
			setState(323);
			match(T__0);
			setState(324);
			typedVariableList();
			setState(325);
			match(T__2);
			setState(326);
			actionDefBody();
			setState(327);
			match(T__2);
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
			setState(329);
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
			setState(337);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(331);
				match(T__15);
				setState(335);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					{
					setState(332);
					match(T__0);
					setState(333);
					match(T__2);
					}
					}
					break;
				case 2:
					{
					setState(334);
					goalDesc();
					}
					break;
				}
				}
			}

			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__16) {
				{
				setState(339);
				match(T__16);
				setState(343);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					{
					setState(340);
					match(T__0);
					setState(341);
					match(T__2);
					}
					}
					break;
				case 2:
					{
					setState(342);
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
		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}
		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class,i);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
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
			setState(400);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(347);
				atomicTermFormula();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(348);
				match(T__0);
				setState(349);
				match(T__17);
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(350);
					goalDesc();
					}
					}
					setState(355);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(356);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(357);
				match(T__0);
				setState(358);
				match(T__18);
				setState(362);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(359);
					goalDesc();
					}
					}
					setState(364);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(365);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(366);
				match(T__0);
				setState(367);
				match(T__19);
				setState(368);
				goalDesc();
				setState(369);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(371);
				match(T__0);
				setState(372);
				match(T__20);
				setState(373);
				goalDesc();
				setState(374);
				goalDesc();
				setState(375);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(377);
				match(T__0);
				setState(378);
				match(T__21);
				setState(379);
				match(T__0);
				setState(380);
				typedVariableList();
				setState(381);
				match(T__2);
				setState(382);
				goalDesc();
				setState(383);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(385);
				match(T__0);
				setState(386);
				match(T__22);
				setState(387);
				match(T__0);
				setState(388);
				typedVariableList();
				setState(389);
				match(T__2);
				setState(390);
				goalDesc();
				setState(391);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(393);
				fComp();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(394);
				match(T__0);
				setState(395);
				match(EQUALS);
				setState(396);
				term();
				setState(397);
				term();
				setState(398);
				match(T__2);
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
			setState(402);
			match(T__0);
			setState(403);
			binaryComp();
			setState(404);
			fExp();
			setState(405);
			fExp();
			setState(406);
			match(T__2);
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
			setState(408);
			match(T__0);
			setState(409);
			predicate();
			setState(413);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NAME || _la==VARIABLE) {
				{
				{
				setState(410);
				term();
				}
				}
				setState(415);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(416);
			match(T__2);
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
			setState(418);
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
		public ActionSymbolContext actionSymbol() {
			return getRuleContext(ActionSymbolContext.class,0);
		}
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
			setState(420);
			match(T__0);
			setState(421);
			match(T__23);
			setState(422);
			actionSymbol();
			setState(423);
			match(T__14);
			setState(424);
			match(T__0);
			setState(425);
			typedVariableList();
			setState(426);
			match(T__2);
			setState(427);
			daDefBody();
			setState(428);
			match(T__2);
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
		public DurationConstraintContext durationConstraint() {
			return getRuleContext(DurationConstraintContext.class,0);
		}
		public DaGDContext daGD() {
			return getRuleContext(DaGDContext.class,0);
		}
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
			setState(444);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__24:
				enterOuterAlt(_localctx, 1);
				{
				setState(430);
				match(T__24);
				setState(431);
				durationConstraint();
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 2);
				{
				setState(432);
				match(T__25);
				setState(436);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					{
					setState(433);
					match(T__0);
					setState(434);
					match(T__2);
					}
					}
					break;
				case 2:
					{
					setState(435);
					daGD();
					}
					break;
				}
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(438);
				match(T__16);
				setState(442);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
				case 1:
					{
					{
					setState(439);
					match(T__0);
					setState(440);
					match(T__2);
					}
					}
					break;
				case 2:
					{
					setState(441);
					daEffect();
					}
					break;
				}
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

	public static class DaGDContext extends ParserRuleContext {
		public PrefTimedGDContext prefTimedGD() {
			return getRuleContext(PrefTimedGDContext.class,0);
		}
		public List<DaGDContext> daGD() {
			return getRuleContexts(DaGDContext.class);
		}
		public DaGDContext daGD(int i) {
			return getRuleContext(DaGDContext.class,i);
		}
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
			setState(464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
				prefTimedGD();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(447);
				match(T__0);
				setState(448);
				match(T__17);
				setState(452);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(449);
					daGD();
					}
					}
					setState(454);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(455);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(456);
				match(T__0);
				setState(457);
				match(T__22);
				setState(458);
				match(T__0);
				setState(459);
				typedVariableList();
				setState(460);
				match(T__2);
				setState(461);
				daGD();
				setState(462);
				match(T__2);
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
			setState(475);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(466);
				timedGD();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(467);
				match(T__0);
				setState(468);
				match(T__26);
				setState(470);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(469);
					match(NAME);
					}
				}

				setState(472);
				timedGD();
				setState(473);
				match(T__2);
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
		public TerminalNode AT() { return getToken(PddlParser.AT, 0); }
		public TimeSpecifierContext timeSpecifier() {
			return getRuleContext(TimeSpecifierContext.class,0);
		}
		public GoalDescContext goalDesc() {
			return getRuleContext(GoalDescContext.class,0);
		}
		public IntervalContext interval() {
			return getRuleContext(IntervalContext.class,0);
		}
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
			setState(489);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				match(T__0);
				setState(478);
				match(AT);
				setState(479);
				timeSpecifier();
				setState(480);
				goalDesc();
				setState(481);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(483);
				match(T__0);
				setState(484);
				match(T__27);
				setState(485);
				interval();
				setState(486);
				goalDesc();
				setState(487);
				match(T__2);
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
			setState(491);
			_la = _input.LA(1);
			if ( !(_la==T__28 || _la==T__29) ) {
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
			setState(493);
			match(T__30);
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
			setState(495);
			match(T__0);
			setState(496);
			match(T__31);
			setState(497);
			atomicFormulaSkeleton();
			setState(498);
			goalDesc();
			setState(499);
			match(T__2);
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
			setState(514);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(501);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(502);
				match(T__0);
				setState(503);
				binaryOp();
				setState(504);
				fExp();
				setState(505);
				fExp2();
				setState(506);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(508);
				match(T__0);
				setState(509);
				match(T__6);
				setState(510);
				fExp();
				setState(511);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(513);
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
			setState(516);
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
			setState(529);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				match(T__0);
				setState(519);
				functionSymbol();
				setState(523);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME || _la==VARIABLE) {
					{
					{
					setState(520);
					term();
					}
					}
					setState(525);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(526);
				match(T__2);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(528);
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
			setState(541);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(531);
				match(T__0);
				setState(532);
				match(T__17);
				setState(536);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(533);
					cEffect();
					}
					}
					setState(538);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(539);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(540);
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
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public EffectContext effect() {
			return getRuleContext(EffectContext.class,0);
		}
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
			setState(558);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(543);
				match(T__0);
				setState(544);
				match(T__22);
				setState(545);
				match(T__0);
				setState(546);
				typedVariableList();
				setState(547);
				match(T__2);
				setState(548);
				effect();
				setState(549);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(551);
				match(T__0);
				setState(552);
				match(T__32);
				setState(553);
				goalDesc();
				setState(554);
				condEffect();
				setState(555);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(557);
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
			setState(572);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(560);
				match(T__0);
				setState(561);
				assignOp();
				setState(562);
				fHead();
				setState(563);
				fExp();
				setState(564);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(566);
				match(T__0);
				setState(567);
				match(T__19);
				setState(568);
				atomicTermFormula();
				setState(569);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(571);
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
			setState(584);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(574);
				match(T__0);
				setState(575);
				match(T__17);
				setState(579);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(576);
					pEffect();
					}
					}
					setState(581);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(582);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(583);
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
			setState(586);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) ) {
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
			setState(588);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39))) != 0) || _la==EQUALS) ) {
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
			setState(590);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44))) != 0)) ) {
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
			setState(604);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(592);
				match(T__0);
				setState(593);
				match(T__17);
				setState(595); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(594);
					simpleDurationConstraint();
					}
					}
					setState(597); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(599);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(601);
				match(T__0);
				setState(602);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(603);
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
		public DurValueContext durValue() {
			return getRuleContext(DurValueContext.class,0);
		}
		public TerminalNode AT() { return getToken(PddlParser.AT, 0); }
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
			setState(618);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(606);
				match(T__0);
				setState(607);
				durOp();
				setState(608);
				match(T__45);
				setState(609);
				durValue();
				setState(610);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(612);
				match(T__0);
				setState(613);
				match(AT);
				setState(614);
				timeSpecifier();
				setState(615);
				simpleDurationConstraint();
				setState(616);
				match(T__2);
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
			setState(620);
			_la = _input.LA(1);
			if ( !(_la==T__38 || _la==T__39 || _la==EQUALS) ) {
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
			setState(624);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(622);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(623);
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
		public List<DaEffectContext> daEffect() {
			return getRuleContexts(DaEffectContext.class);
		}
		public DaEffectContext daEffect(int i) {
			return getRuleContext(DaEffectContext.class,i);
		}
		public TimedEffectContext timedEffect() {
			return getRuleContext(TimedEffectContext.class,0);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
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
			setState(656);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(626);
				match(T__0);
				setState(627);
				match(T__17);
				setState(631);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(628);
					daEffect();
					}
					}
					setState(633);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(634);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(635);
				timedEffect();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(636);
				match(T__0);
				setState(637);
				match(T__22);
				setState(638);
				match(T__0);
				setState(639);
				typedVariableList();
				setState(640);
				match(T__2);
				setState(641);
				daEffect();
				setState(642);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(644);
				match(T__0);
				setState(645);
				match(T__32);
				setState(646);
				daGD();
				setState(647);
				timedEffect();
				setState(648);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(650);
				match(T__0);
				setState(651);
				assignOp();
				setState(652);
				fHead();
				setState(653);
				fExpDA();
				setState(654);
				match(T__2);
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
		public TerminalNode AT() { return getToken(PddlParser.AT, 0); }
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
			setState(676);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(658);
				match(T__0);
				setState(659);
				match(AT);
				setState(660);
				timeSpecifier();
				setState(661);
				daEffect();
				setState(662);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(664);
				match(T__0);
				setState(665);
				match(AT);
				setState(666);
				timeSpecifier();
				setState(667);
				fAssignDA();
				setState(668);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(670);
				match(T__0);
				setState(671);
				assignOp();
				setState(672);
				fHead();
				setState(673);
				fExp();
				setState(674);
				match(T__2);
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
			setState(678);
			match(T__0);
			setState(679);
			assignOp();
			setState(680);
			fHead();
			setState(681);
			fExpDA();
			setState(682);
			match(T__2);
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
			setState(697);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(684);
				match(T__0);
				setState(691);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
				case 1:
					{
					{
					setState(685);
					binaryOp();
					setState(686);
					fExpDA();
					setState(687);
					fExpDA();
					}
					}
					break;
				case 2:
					{
					{
					setState(689);
					match(T__6);
					setState(690);
					fExpDA();
					}
					}
					break;
				}
				setState(693);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(695);
				match(T__45);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(696);
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
			setState(699);
			match(T__0);
			setState(700);
			match(T__1);
			setState(701);
			problemDecl();
			setState(702);
			problemDomain();
			setState(704);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(703);
				requireDef();
				}
				break;
			}
			setState(707);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				{
				setState(706);
				objectDecl();
				}
				break;
			}
			setState(709);
			init();
			setState(710);
			goal();
			setState(712);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				{
				setState(711);
				probConstraints();
				}
				break;
			}
			setState(715);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(714);
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
			setState(717);
			match(T__0);
			setState(718);
			match(T__46);
			setState(719);
			match(NAME);
			setState(720);
			match(T__2);
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
			setState(722);
			match(T__0);
			setState(723);
			match(T__47);
			setState(724);
			match(NAME);
			setState(725);
			match(T__2);
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
			setState(727);
			match(T__0);
			setState(728);
			match(T__48);
			setState(729);
			typedNameList();
			setState(730);
			match(T__2);
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
			setState(732);
			match(T__0);
			setState(733);
			match(T__49);
			setState(737);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(734);
				initEl();
				}
				}
				setState(739);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(740);
			match(T__2);
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
		public TerminalNode AT() { return getToken(PddlParser.AT, 0); }
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
			setState(755);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(742);
				nameLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(743);
				match(T__0);
				setState(744);
				match(EQUALS);
				setState(745);
				fHead();
				setState(746);
				match(NUMBER);
				setState(747);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(749);
				match(T__0);
				setState(750);
				match(AT);
				setState(751);
				match(NUMBER);
				setState(752);
				nameLiteral();
				setState(753);
				match(T__2);
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
			setState(763);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(757);
				atomicNameFormula();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(758);
				match(T__0);
				setState(759);
				match(T__19);
				setState(760);
				atomicNameFormula();
				setState(761);
				match(T__2);
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
			setState(765);
			match(T__0);
			setState(766);
			predicate();
			setState(770);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NAME) {
				{
				{
				setState(767);
				match(NAME);
				}
				}
				setState(772);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(773);
			match(T__2);
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
			setState(775);
			match(T__0);
			setState(776);
			match(T__50);
			setState(777);
			goalDesc();
			setState(778);
			match(T__2);
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
			setState(780);
			match(T__0);
			setState(781);
			match(T__12);
			setState(782);
			prefConGD();
			setState(783);
			match(T__2);
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
		public List<PrefConGDContext> prefConGD() {
			return getRuleContexts(PrefConGDContext.class);
		}
		public PrefConGDContext prefConGD(int i) {
			return getRuleContext(PrefConGDContext.class,i);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
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
			setState(811);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(785);
				match(T__0);
				setState(786);
				match(T__17);
				setState(790);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(787);
					prefConGD();
					}
					}
					setState(792);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(793);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(794);
				match(T__0);
				setState(795);
				match(T__22);
				setState(796);
				match(T__0);
				setState(797);
				typedVariableList();
				setState(798);
				match(T__2);
				setState(799);
				prefConGD();
				setState(800);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(802);
				match(T__0);
				setState(803);
				match(T__26);
				setState(805);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(804);
					match(NAME);
					}
				}

				setState(807);
				conGD();
				setState(808);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(810);
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
			setState(813);
			match(T__0);
			setState(814);
			match(T__51);
			setState(815);
			optimization();
			setState(816);
			metricFExp();
			setState(817);
			match(T__2);
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
			setState(819);
			_la = _input.LA(1);
			if ( !(_la==T__52 || _la==T__53) ) {
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
			setState(859);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(821);
				match(T__0);
				setState(822);
				binaryOp();
				setState(823);
				metricFExp();
				setState(824);
				metricFExp();
				setState(825);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(827);
				match(T__0);
				setState(828);
				_la = _input.LA(1);
				if ( !(_la==T__33 || _la==T__35) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(829);
				metricFExp();
				setState(831); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(830);
					metricFExp();
					}
					}
					setState(833); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 || _la==T__54 || _la==NAME || _la==NUMBER );
				setState(835);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(837);
				match(T__0);
				setState(838);
				match(T__6);
				setState(839);
				metricFExp();
				setState(840);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(842);
				match(NUMBER);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(843);
				match(T__0);
				setState(844);
				functionSymbol();
				setState(848);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(845);
					match(NAME);
					}
					}
					setState(850);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(851);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(853);
				functionSymbol();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(854);
				match(T__54);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(855);
				match(T__0);
				setState(856);
				match(T__55);
				setState(857);
				match(NAME);
				setState(858);
				match(T__2);
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
		public List<ConGDContext> conGD() {
			return getRuleContexts(ConGDContext.class);
		}
		public ConGDContext conGD(int i) {
			return getRuleContext(ConGDContext.class,i);
		}
		public TypedVariableListContext typedVariableList() {
			return getRuleContext(TypedVariableListContext.class,0);
		}
		public TerminalNode AT() { return getToken(PddlParser.AT, 0); }
		public List<GoalDescContext> goalDesc() {
			return getRuleContexts(GoalDescContext.class);
		}
		public GoalDescContext goalDesc(int i) {
			return getRuleContext(GoalDescContext.class,i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(PddlParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(PddlParser.NUMBER, i);
		}
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
			setState(937);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(861);
				match(T__0);
				setState(862);
				match(T__17);
				setState(866);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(863);
					conGD();
					}
					}
					setState(868);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(869);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(870);
				match(T__0);
				setState(871);
				match(T__22);
				setState(872);
				match(T__0);
				setState(873);
				typedVariableList();
				setState(874);
				match(T__2);
				setState(875);
				conGD();
				setState(876);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(878);
				match(T__0);
				setState(879);
				match(AT);
				setState(880);
				match(T__29);
				setState(881);
				goalDesc();
				setState(882);
				match(T__2);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(884);
				match(T__0);
				setState(885);
				match(T__56);
				setState(886);
				goalDesc();
				setState(887);
				match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(889);
				match(T__0);
				setState(890);
				match(T__57);
				setState(891);
				goalDesc();
				setState(892);
				match(T__2);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(894);
				match(T__0);
				setState(895);
				match(T__58);
				setState(896);
				match(NUMBER);
				setState(897);
				goalDesc();
				setState(898);
				match(T__2);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(900);
				match(T__0);
				setState(901);
				match(T__59);
				setState(902);
				goalDesc();
				setState(903);
				match(T__2);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(905);
				match(T__0);
				setState(906);
				match(T__60);
				setState(907);
				goalDesc();
				setState(908);
				goalDesc();
				setState(909);
				match(T__2);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(911);
				match(T__0);
				setState(912);
				match(T__61);
				setState(913);
				goalDesc();
				setState(914);
				goalDesc();
				setState(915);
				match(T__2);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(917);
				match(T__0);
				setState(918);
				match(T__62);
				setState(919);
				match(NUMBER);
				setState(920);
				goalDesc();
				setState(921);
				goalDesc();
				setState(922);
				match(T__2);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(924);
				match(T__0);
				setState(925);
				match(T__63);
				setState(926);
				match(NUMBER);
				setState(927);
				match(NUMBER);
				setState(928);
				goalDesc();
				setState(929);
				match(T__2);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(931);
				match(T__0);
				setState(932);
				match(T__64);
				setState(933);
				match(NUMBER);
				setState(934);
				goalDesc();
				setState(935);
				match(T__2);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3s\u03ae\4\2\t\2\4"+
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
		"\n\3\f\3\16\3\u00ac\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\6\5\u00b8"+
		"\n\5\r\5\16\5\u00b9\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\7\7\u00c4\n\7\f\7"+
		"\16\7\u00c7\13\7\3\7\6\7\u00ca\n\7\r\7\16\7\u00cb\3\7\7\7\u00cf\n\7\f"+
		"\7\16\7\u00d2\13\7\5\7\u00d4\n\7\3\b\6\b\u00d7\n\b\r\b\16\b\u00d8\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\6\t\u00e1\n\t\r\t\16\t\u00e2\3\t\3\t\3\t\5\t\u00e8"+
		"\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\6\f\u00f2\n\f\r\f\16\f\u00f3"+
		"\3\f\3\f\5\f\u00f8\n\f\7\f\u00fa\n\f\f\f\16\f\u00fd\13\f\3\r\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\6\21"+
		"\u0110\n\21\r\21\16\21\u0111\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3"+
		"\23\3\24\7\24\u011e\n\24\f\24\16\24\u0121\13\24\3\24\6\24\u0124\n\24\r"+
		"\24\16\24\u0125\3\24\7\24\u0129\n\24\f\24\16\24\u012c\13\24\5\24\u012e"+
		"\n\24\3\25\6\25\u0131\n\25\r\25\16\25\u0132\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\5\27\u0140\n\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\5\32\u0152\n\32"+
		"\5\32\u0154\n\32\3\32\3\32\3\32\3\32\5\32\u015a\n\32\5\32\u015c\n\32\3"+
		"\33\3\33\3\33\3\33\7\33\u0162\n\33\f\33\16\33\u0165\13\33\3\33\3\33\3"+
		"\33\3\33\7\33\u016b\n\33\f\33\16\33\u016e\13\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\3\33\5\33\u0193\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35"+
		"\3\35\7\35\u019e\n\35\f\35\16\35\u01a1\13\35\3\35\3\35\3\36\3\36\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \5 \u01b7"+
		"\n \3 \3 \3 \3 \5 \u01bd\n \5 \u01bf\n \3!\3!\3!\3!\7!\u01c5\n!\f!\16"+
		"!\u01c8\13!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u01d3\n!\3\"\3\"\3\"\3\"\5\""+
		"\u01d9\n\"\3\"\3\"\3\"\5\"\u01de\n\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\5#\u01ec\n#\3$\3$\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u0205\n\'\3(\3(\3)\3)\3)\7)\u020c\n)"+
		"\f)\16)\u020f\13)\3)\3)\3)\5)\u0214\n)\3*\3*\3*\7*\u0219\n*\f*\16*\u021c"+
		"\13*\3*\3*\5*\u0220\n*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\5"+
		"+\u0231\n+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u023f\n,\3-\3-\3-\7"+
		"-\u0244\n-\f-\16-\u0247\13-\3-\3-\5-\u024b\n-\3.\3.\3/\3/\3\60\3\60\3"+
		"\61\3\61\3\61\6\61\u0256\n\61\r\61\16\61\u0257\3\61\3\61\3\61\3\61\3\61"+
		"\5\61\u025f\n\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62"+
		"\3\62\5\62\u026d\n\62\3\63\3\63\3\64\3\64\5\64\u0273\n\64\3\65\3\65\3"+
		"\65\7\65\u0278\n\65\f\65\16\65\u027b\13\65\3\65\3\65\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\5\65\u0293\n\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\5\66\u02a7\n\66\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\38\58\u02b6\n8\38\38\38\3"+
		"8\58\u02bc\n8\39\39\39\39\39\59\u02c3\n9\39\59\u02c6\n9\39\39\39\59\u02cb"+
		"\n9\39\59\u02ce\n9\3:\3:\3:\3:\3:\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3="+
		"\3=\7=\u02e2\n=\f=\16=\u02e5\13=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3>\3>\3>\3"+
		">\3>\3>\5>\u02f6\n>\3?\3?\3?\3?\3?\3?\5?\u02fe\n?\3@\3@\3@\7@\u0303\n"+
		"@\f@\16@\u0306\13@\3@\3@\3A\3A\3A\3A\3A\3B\3B\3B\3B\3B\3C\3C\3C\7C\u0317"+
		"\nC\fC\16C\u031a\13C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\5C\u0328\nC\3"+
		"C\3C\3C\3C\5C\u032e\nC\3D\3D\3D\3D\3D\3D\3E\3E\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\6F\u0342\nF\rF\16F\u0343\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\7F\u0351"+
		"\nF\fF\16F\u0354\13F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u035e\nF\3G\3G\3G\7G\u0363"+
		"\nG\fG\16G\u0366\13G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3G\3G\3G\3G\3G\5G\u03ac\nG\3G\2\2H\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080"+
		"\u0082\u0084\u0086\u0088\u008a\u008c\2\n\3\2no\3\2\37 \4\2\t\t$&\4\2\'"+
		"*mm\3\2+/\4\2)*mm\3\2\678\4\2$$&&\2\u03da\2\u0090\3\2\2\2\4\u0092\3\2"+
		"\2\2\6\u00af\3\2\2\2\b\u00b4\3\2\2\2\n\u00bd\3\2\2\2\f\u00d3\3\2\2\2\16"+
		"\u00d6\3\2\2\2\20\u00e7\3\2\2\2\22\u00e9\3\2\2\2\24\u00eb\3\2\2\2\26\u00fb"+
		"\3\2\2\2\30\u00fe\3\2\2\2\32\u0103\3\2\2\2\34\u0105\3\2\2\2\36\u0107\3"+
		"\2\2\2 \u010c\3\2\2\2\"\u0115\3\2\2\2$\u011a\3\2\2\2&\u012d\3\2\2\2(\u0130"+
		"\3\2\2\2*\u0137\3\2\2\2,\u013f\3\2\2\2.\u0141\3\2\2\2\60\u014b\3\2\2\2"+
		"\62\u0153\3\2\2\2\64\u0192\3\2\2\2\66\u0194\3\2\2\28\u019a\3\2\2\2:\u01a4"+
		"\3\2\2\2<\u01a6\3\2\2\2>\u01be\3\2\2\2@\u01d2\3\2\2\2B\u01dd\3\2\2\2D"+
		"\u01eb\3\2\2\2F\u01ed\3\2\2\2H\u01ef\3\2\2\2J\u01f1\3\2\2\2L\u0204\3\2"+
		"\2\2N\u0206\3\2\2\2P\u0213\3\2\2\2R\u021f\3\2\2\2T\u0230\3\2\2\2V\u023e"+
		"\3\2\2\2X\u024a\3\2\2\2Z\u024c\3\2\2\2\\\u024e\3\2\2\2^\u0250\3\2\2\2"+
		"`\u025e\3\2\2\2b\u026c\3\2\2\2d\u026e\3\2\2\2f\u0272\3\2\2\2h\u0292\3"+
		"\2\2\2j\u02a6\3\2\2\2l\u02a8\3\2\2\2n\u02bb\3\2\2\2p\u02bd\3\2\2\2r\u02cf"+
		"\3\2\2\2t\u02d4\3\2\2\2v\u02d9\3\2\2\2x\u02de\3\2\2\2z\u02f5\3\2\2\2|"+
		"\u02fd\3\2\2\2~\u02ff\3\2\2\2\u0080\u0309\3\2\2\2\u0082\u030e\3\2\2\2"+
		"\u0084\u032d\3\2\2\2\u0086\u032f\3\2\2\2\u0088\u0335\3\2\2\2\u008a\u035d"+
		"\3\2\2\2\u008c\u03ab\3\2\2\2\u008e\u0091\5\4\3\2\u008f\u0091\5p9\2\u0090"+
		"\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\3\3\2\2\2\u0092\u0093\7\3\2\2"+
		"\u0093\u0094\7\4\2\2\u0094\u0096\5\6\4\2\u0095\u0097\5\b\5\2\u0096\u0095"+
		"\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u009a\5\n\6\2\u0099"+
		"\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u009d\5\36"+
		"\20\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e"+
		"\u00a0\5 \21\2\u009f\u009e\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2"+
		"\2\2\u00a1\u00a3\5\24\13\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3"+
		"\u00a5\3\2\2\2\u00a4\u00a6\5*\26\2\u00a5\u00a4\3\2\2\2\u00a5\u00a6\3\2"+
		"\2\2\u00a6\u00aa\3\2\2\2\u00a7\u00a9\5,\27\2\u00a8\u00a7\3\2\2\2\u00a9"+
		"\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\3\2"+
		"\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7\5\2\2\u00ae\5\3\2\2\2\u00af\u00b0"+
		"\7\3\2\2\u00b0\u00b1\7\6\2\2\u00b1\u00b2\7n\2\2\u00b2\u00b3\7\5\2\2\u00b3"+
		"\7\3\2\2\2\u00b4\u00b5\7\3\2\2\u00b5\u00b7\7\7\2\2\u00b6\u00b8\7D\2\2"+
		"\u00b7\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba"+
		"\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\7\5\2\2\u00bc\t\3\2\2\2\u00bd"+
		"\u00be\7\3\2\2\u00be\u00bf\7\b\2\2\u00bf\u00c0\5\f\7\2\u00c0\u00c1\7\5"+
		"\2\2\u00c1\13\3\2\2\2\u00c2\u00c4\7n\2\2\u00c3\u00c2\3\2\2\2\u00c4\u00c7"+
		"\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00d4\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c8\u00ca\5\16\b\2\u00c9\u00c8\3\2\2\2\u00ca\u00cb\3"+
		"\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00d0\3\2\2\2\u00cd"+
		"\u00cf\7n\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2"+
		"\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00c5\3\2\2\2\u00d3\u00c9\3\2\2\2\u00d4\r\3\2\2\2\u00d5\u00d7\7n\2\2"+
		"\u00d6\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9"+
		"\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\7\t\2\2\u00db\u00dc\5\20\t\2"+
		"\u00dc\17\3\2\2\2\u00dd\u00de\7\3\2\2\u00de\u00e0\7\n\2\2\u00df\u00e1"+
		"\5\22\n\2\u00e0\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e0\3\2\2\2"+
		"\u00e2\u00e3\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\7\5\2\2\u00e5\u00e8"+
		"\3\2\2\2\u00e6\u00e8\5\22\n\2\u00e7\u00dd\3\2\2\2\u00e7\u00e6\3\2\2\2"+
		"\u00e8\21\3\2\2\2\u00e9\u00ea\7n\2\2\u00ea\23\3\2\2\2\u00eb\u00ec\7\3"+
		"\2\2\u00ec\u00ed\7\13\2\2\u00ed\u00ee\5\26\f\2\u00ee\u00ef\7\5\2\2\u00ef"+
		"\25\3\2\2\2\u00f0\u00f2\5\30\r\2\u00f1\u00f0\3\2\2\2\u00f2\u00f3\3\2\2"+
		"\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f6"+
		"\7\t\2\2\u00f6\u00f8\5\34\17\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2"+
		"\u00f8\u00fa\3\2\2\2\u00f9\u00f1\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9"+
		"\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\27\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe"+
		"\u00ff\7\3\2\2\u00ff\u0100\5\32\16\2\u0100\u0101\5&\24\2\u0101\u0102\7"+
		"\5\2\2\u0102\31\3\2\2\2\u0103\u0104\7n\2\2\u0104\33\3\2\2\2\u0105\u0106"+
		"\7\f\2\2\u0106\35\3\2\2\2\u0107\u0108\7\3\2\2\u0108\u0109\7\r\2\2\u0109"+
		"\u010a\5\f\7\2\u010a\u010b\7\5\2\2\u010b\37\3\2\2\2\u010c\u010d\7\3\2"+
		"\2\u010d\u010f\7\16\2\2\u010e\u0110\5\"\22\2\u010f\u010e\3\2\2\2\u0110"+
		"\u0111\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113\3\2"+
		"\2\2\u0113\u0114\7\5\2\2\u0114!\3\2\2\2\u0115\u0116\7\3\2\2\u0116\u0117"+
		"\5$\23\2\u0117\u0118\5&\24\2\u0118\u0119\7\5\2\2\u0119#\3\2\2\2\u011a"+
		"\u011b\7n\2\2\u011b%\3\2\2\2\u011c\u011e\7o\2\2\u011d\u011c\3\2\2\2\u011e"+
		"\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u012e\3\2"+
		"\2\2\u0121\u011f\3\2\2\2\u0122\u0124\5(\25\2\u0123\u0122\3\2\2\2\u0124"+
		"\u0125\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u012a\3\2"+
		"\2\2\u0127\u0129\7o\2\2\u0128\u0127\3\2\2\2\u0129\u012c\3\2\2\2\u012a"+
		"\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012e\3\2\2\2\u012c\u012a\3\2"+
		"\2\2\u012d\u011f\3\2\2\2\u012d\u0123\3\2\2\2\u012e\'\3\2\2\2\u012f\u0131"+
		"\7o\2\2\u0130\u012f\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0130\3\2\2\2\u0132"+
		"\u0133\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0135\7\t\2\2\u0135\u0136\5\20"+
		"\t\2\u0136)\3\2\2\2\u0137\u0138\7\3\2\2\u0138\u0139\7\17\2\2\u0139\u013a"+
		"\5\u008cG\2\u013a\u013b\7\5\2\2\u013b+\3\2\2\2\u013c\u0140\5.\30\2\u013d"+
		"\u0140\5<\37\2\u013e\u0140\5J&\2\u013f\u013c\3\2\2\2\u013f\u013d\3\2\2"+
		"\2\u013f\u013e\3\2\2\2\u0140-\3\2\2\2\u0141\u0142\7\3\2\2\u0142\u0143"+
		"\7\20\2\2\u0143\u0144\5\60\31\2\u0144\u0145\7\21\2\2\u0145\u0146\7\3\2"+
		"\2\u0146\u0147\5&\24\2\u0147\u0148\7\5\2\2\u0148\u0149\5\62\32\2\u0149"+
		"\u014a\7\5\2\2\u014a/\3\2\2\2\u014b\u014c\7n\2\2\u014c\61\3\2\2\2\u014d"+
		"\u0151\7\22\2\2\u014e\u014f\7\3\2\2\u014f\u0152\7\5\2\2\u0150\u0152\5"+
		"\64\33\2\u0151\u014e\3\2\2\2\u0151\u0150\3\2\2\2\u0152\u0154\3\2\2\2\u0153"+
		"\u014d\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u015b\3\2\2\2\u0155\u0159\7\23"+
		"\2\2\u0156\u0157\7\3\2\2\u0157\u015a\7\5\2\2\u0158\u015a\5R*\2\u0159\u0156"+
		"\3\2\2\2\u0159\u0158\3\2\2\2\u015a\u015c\3\2\2\2\u015b\u0155\3\2\2\2\u015b"+
		"\u015c\3\2\2\2\u015c\63\3\2\2\2\u015d\u0193\58\35\2\u015e\u015f\7\3\2"+
		"\2\u015f\u0163\7\24\2\2\u0160\u0162\5\64\33\2\u0161\u0160\3\2\2\2\u0162"+
		"\u0165\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164\u0166\3\2"+
		"\2\2\u0165\u0163\3\2\2\2\u0166\u0193\7\5\2\2\u0167\u0168\7\3\2\2\u0168"+
		"\u016c\7\25\2\2\u0169\u016b\5\64\33\2\u016a\u0169\3\2\2\2\u016b\u016e"+
		"\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016f\3\2\2\2\u016e"+
		"\u016c\3\2\2\2\u016f\u0193\7\5\2\2\u0170\u0171\7\3\2\2\u0171\u0172\7\26"+
		"\2\2\u0172\u0173\5\64\33\2\u0173\u0174\7\5\2\2\u0174\u0193\3\2\2\2\u0175"+
		"\u0176\7\3\2\2\u0176\u0177\7\27\2\2\u0177\u0178\5\64\33\2\u0178\u0179"+
		"\5\64\33\2\u0179\u017a\7\5\2\2\u017a\u0193\3\2\2\2\u017b\u017c\7\3\2\2"+
		"\u017c\u017d\7\30\2\2\u017d\u017e\7\3\2\2\u017e\u017f\5&\24\2\u017f\u0180"+
		"\7\5\2\2\u0180\u0181\5\64\33\2\u0181\u0182\7\5\2\2\u0182\u0193\3\2\2\2"+
		"\u0183\u0184\7\3\2\2\u0184\u0185\7\31\2\2\u0185\u0186\7\3\2\2\u0186\u0187"+
		"\5&\24\2\u0187\u0188\7\5\2\2\u0188\u0189\5\64\33\2\u0189\u018a\7\5\2\2"+
		"\u018a\u0193\3\2\2\2\u018b\u0193\5\66\34\2\u018c\u018d\7\3\2\2\u018d\u018e"+
		"\7m\2\2\u018e\u018f\5:\36\2\u018f\u0190\5:\36\2\u0190\u0191\7\5\2\2\u0191"+
		"\u0193\3\2\2\2\u0192\u015d\3\2\2\2\u0192\u015e\3\2\2\2\u0192\u0167\3\2"+
		"\2\2\u0192\u0170\3\2\2\2\u0192\u0175\3\2\2\2\u0192\u017b\3\2\2\2\u0192"+
		"\u0183\3\2\2\2\u0192\u018b\3\2\2\2\u0192\u018c\3\2\2\2\u0193\65\3\2\2"+
		"\2\u0194\u0195\7\3\2\2\u0195\u0196\5\\/\2\u0196\u0197\5L\'\2\u0197\u0198"+
		"\5L\'\2\u0198\u0199\7\5\2\2\u0199\67\3\2\2\2\u019a\u019b\7\3\2\2\u019b"+
		"\u019f\5$\23\2\u019c\u019e\5:\36\2\u019d\u019c\3\2\2\2\u019e\u01a1\3\2"+
		"\2\2\u019f\u019d\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\3\2\2\2\u01a1"+
		"\u019f\3\2\2\2\u01a2\u01a3\7\5\2\2\u01a39\3\2\2\2\u01a4\u01a5\t\2\2\2"+
		"\u01a5;\3\2\2\2\u01a6\u01a7\7\3\2\2\u01a7\u01a8\7\32\2\2\u01a8\u01a9\5"+
		"\60\31\2\u01a9\u01aa\7\21\2\2\u01aa\u01ab\7\3\2\2\u01ab\u01ac\5&\24\2"+
		"\u01ac\u01ad\7\5\2\2\u01ad\u01ae\5> \2\u01ae\u01af\7\5\2\2\u01af=\3\2"+
		"\2\2\u01b0\u01b1\7\33\2\2\u01b1\u01bf\5`\61\2\u01b2\u01b6\7\34\2\2\u01b3"+
		"\u01b4\7\3\2\2\u01b4\u01b7\7\5\2\2\u01b5\u01b7\5@!\2\u01b6\u01b3\3\2\2"+
		"\2\u01b6\u01b5\3\2\2\2\u01b7\u01bf\3\2\2\2\u01b8\u01bc\7\23\2\2\u01b9"+
		"\u01ba\7\3\2\2\u01ba\u01bd\7\5\2\2\u01bb\u01bd\5h\65\2\u01bc\u01b9\3\2"+
		"\2\2\u01bc\u01bb\3\2\2\2\u01bd\u01bf\3\2\2\2\u01be\u01b0\3\2\2\2\u01be"+
		"\u01b2\3\2\2\2\u01be\u01b8\3\2\2\2\u01bf?\3\2\2\2\u01c0\u01d3\5B\"\2\u01c1"+
		"\u01c2\7\3\2\2\u01c2\u01c6\7\24\2\2\u01c3\u01c5\5@!\2\u01c4\u01c3\3\2"+
		"\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7"+
		"\u01c9\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01d3\7\5\2\2\u01ca\u01cb\7\3"+
		"\2\2\u01cb\u01cc\7\31\2\2\u01cc\u01cd\7\3\2\2\u01cd\u01ce\5&\24\2\u01ce"+
		"\u01cf\7\5\2\2\u01cf\u01d0\5@!\2\u01d0\u01d1\7\5\2\2\u01d1\u01d3\3\2\2"+
		"\2\u01d2\u01c0\3\2\2\2\u01d2\u01c1\3\2\2\2\u01d2\u01ca\3\2\2\2\u01d3A"+
		"\3\2\2\2\u01d4\u01de\5D#\2\u01d5\u01d6\7\3\2\2\u01d6\u01d8\7\35\2\2\u01d7"+
		"\u01d9\7n\2\2\u01d8\u01d7\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01da\3\2"+
		"\2\2\u01da\u01db\5D#\2\u01db\u01dc\7\5\2\2\u01dc\u01de\3\2\2\2\u01dd\u01d4"+
		"\3\2\2\2\u01dd\u01d5\3\2\2\2\u01deC\3\2\2\2\u01df\u01e0\7\3\2\2\u01e0"+
		"\u01e1\7q\2\2\u01e1\u01e2\5F$\2\u01e2\u01e3\5\64\33\2\u01e3\u01e4\7\5"+
		"\2\2\u01e4\u01ec\3\2\2\2\u01e5\u01e6\7\3\2\2\u01e6\u01e7\7\36\2\2\u01e7"+
		"\u01e8\5H%\2\u01e8\u01e9\5\64\33\2\u01e9\u01ea\7\5\2\2\u01ea\u01ec\3\2"+
		"\2\2\u01eb\u01df\3\2\2\2\u01eb\u01e5\3\2\2\2\u01ecE\3\2\2\2\u01ed\u01ee"+
		"\t\3\2\2\u01eeG\3\2\2\2\u01ef\u01f0\7!\2\2\u01f0I\3\2\2\2\u01f1\u01f2"+
		"\7\3\2\2\u01f2\u01f3\7\"\2\2\u01f3\u01f4\5\"\22\2\u01f4\u01f5\5\64\33"+
		"\2\u01f5\u01f6\7\5\2\2\u01f6K\3\2\2\2\u01f7\u0205\7p\2\2\u01f8\u01f9\7"+
		"\3\2\2\u01f9\u01fa\5Z.\2\u01fa\u01fb\5L\'\2\u01fb\u01fc\5N(\2\u01fc\u01fd"+
		"\7\5\2\2\u01fd\u0205\3\2\2\2\u01fe\u01ff\7\3\2\2\u01ff\u0200\7\t\2\2\u0200"+
		"\u0201\5L\'\2\u0201\u0202\7\5\2\2\u0202\u0205\3\2\2\2\u0203\u0205\5P)"+
		"\2\u0204\u01f7\3\2\2\2\u0204\u01f8\3\2\2\2\u0204\u01fe\3\2\2\2\u0204\u0203"+
		"\3\2\2\2\u0205M\3\2\2\2\u0206\u0207\5L\'\2\u0207O\3\2\2\2\u0208\u0209"+
		"\7\3\2\2\u0209\u020d\5\32\16\2\u020a\u020c\5:\36\2\u020b\u020a\3\2\2\2"+
		"\u020c\u020f\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u0210"+
		"\3\2\2\2\u020f\u020d\3\2\2\2\u0210\u0211\7\5\2\2\u0211\u0214\3\2\2\2\u0212"+
		"\u0214\5\32\16\2\u0213\u0208\3\2\2\2\u0213\u0212\3\2\2\2\u0214Q\3\2\2"+
		"\2\u0215\u0216\7\3\2\2\u0216\u021a\7\24\2\2\u0217\u0219\5T+\2\u0218\u0217"+
		"\3\2\2\2\u0219\u021c\3\2\2\2\u021a\u0218\3\2\2\2\u021a\u021b\3\2\2\2\u021b"+
		"\u021d\3\2\2\2\u021c\u021a\3\2\2\2\u021d\u0220\7\5\2\2\u021e\u0220\5T"+
		"+\2\u021f\u0215\3\2\2\2\u021f\u021e\3\2\2\2\u0220S\3\2\2\2\u0221\u0222"+
		"\7\3\2\2\u0222\u0223\7\31\2\2\u0223\u0224\7\3\2\2\u0224\u0225\5&\24\2"+
		"\u0225\u0226\7\5\2\2\u0226\u0227\5R*\2\u0227\u0228\7\5\2\2\u0228\u0231"+
		"\3\2\2\2\u0229\u022a\7\3\2\2\u022a\u022b\7#\2\2\u022b\u022c\5\64\33\2"+
		"\u022c\u022d\5X-\2\u022d\u022e\7\5\2\2\u022e\u0231\3\2\2\2\u022f\u0231"+
		"\5V,\2\u0230\u0221\3\2\2\2\u0230\u0229\3\2\2\2\u0230\u022f\3\2\2\2\u0231"+
		"U\3\2\2\2\u0232\u0233\7\3\2\2\u0233\u0234\5^\60\2\u0234\u0235\5P)\2\u0235"+
		"\u0236\5L\'\2\u0236\u0237\7\5\2\2\u0237\u023f\3\2\2\2\u0238\u0239\7\3"+
		"\2\2\u0239\u023a\7\26\2\2\u023a\u023b\58\35\2\u023b\u023c\7\5\2\2\u023c"+
		"\u023f\3\2\2\2\u023d\u023f\58\35\2\u023e\u0232\3\2\2\2\u023e\u0238\3\2"+
		"\2\2\u023e\u023d\3\2\2\2\u023fW\3\2\2\2\u0240\u0241\7\3\2\2\u0241\u0245"+
		"\7\24\2\2\u0242\u0244\5V,\2\u0243\u0242\3\2\2\2\u0244\u0247\3\2\2\2\u0245"+
		"\u0243\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0248\3\2\2\2\u0247\u0245\3\2"+
		"\2\2\u0248\u024b\7\5\2\2\u0249\u024b\5V,\2\u024a\u0240\3\2\2\2\u024a\u0249"+
		"\3\2\2\2\u024bY\3\2\2\2\u024c\u024d\t\4\2\2\u024d[\3\2\2\2\u024e\u024f"+
		"\t\5\2\2\u024f]\3\2\2\2\u0250\u0251\t\6\2\2\u0251_\3\2\2\2\u0252\u0253"+
		"\7\3\2\2\u0253\u0255\7\24\2\2\u0254\u0256\5b\62\2\u0255\u0254\3\2\2\2"+
		"\u0256\u0257\3\2\2\2\u0257\u0255\3\2\2\2\u0257\u0258\3\2\2\2\u0258\u0259"+
		"\3\2\2\2\u0259\u025a\7\5\2\2\u025a\u025f\3\2\2\2\u025b\u025c\7\3\2\2\u025c"+
		"\u025f\7\5\2\2\u025d\u025f\5b\62\2\u025e\u0252\3\2\2\2\u025e\u025b\3\2"+
		"\2\2\u025e\u025d\3\2\2\2\u025fa\3\2\2\2\u0260\u0261\7\3\2\2\u0261\u0262"+
		"\5d\63\2\u0262\u0263\7\60\2\2\u0263\u0264\5f\64\2\u0264\u0265\7\5\2\2"+
		"\u0265\u026d\3\2\2\2\u0266\u0267\7\3\2\2\u0267\u0268\7q\2\2\u0268\u0269"+
		"\5F$\2\u0269\u026a\5b\62\2\u026a\u026b\7\5\2\2\u026b\u026d\3\2\2\2\u026c"+
		"\u0260\3\2\2\2\u026c\u0266\3\2\2\2\u026dc\3\2\2\2\u026e\u026f\t\7\2\2"+
		"\u026fe\3\2\2\2\u0270\u0273\7p\2\2\u0271\u0273\5L\'\2\u0272\u0270\3\2"+
		"\2\2\u0272\u0271\3\2\2\2\u0273g\3\2\2\2\u0274\u0275\7\3\2\2\u0275\u0279"+
		"\7\24\2\2\u0276\u0278\5h\65\2\u0277\u0276\3\2\2\2\u0278\u027b\3\2\2\2"+
		"\u0279\u0277\3\2\2\2\u0279\u027a\3\2\2\2\u027a\u027c\3\2\2\2\u027b\u0279"+
		"\3\2\2\2\u027c\u0293\7\5\2\2\u027d\u0293\5j\66\2\u027e\u027f\7\3\2\2\u027f"+
		"\u0280\7\31\2\2\u0280\u0281\7\3\2\2\u0281\u0282\5&\24\2\u0282\u0283\7"+
		"\5\2\2\u0283\u0284\5h\65\2\u0284\u0285\7\5\2\2\u0285\u0293\3\2\2\2\u0286"+
		"\u0287\7\3\2\2\u0287\u0288\7#\2\2\u0288\u0289\5@!\2\u0289\u028a\5j\66"+
		"\2\u028a\u028b\7\5\2\2\u028b\u0293\3\2\2\2\u028c\u028d\7\3\2\2\u028d\u028e"+
		"\5^\60\2\u028e\u028f\5P)\2\u028f\u0290\5n8\2\u0290\u0291\7\5\2\2\u0291"+
		"\u0293\3\2\2\2\u0292\u0274\3\2\2\2\u0292\u027d\3\2\2\2\u0292\u027e\3\2"+
		"\2\2\u0292\u0286\3\2\2\2\u0292\u028c\3\2\2\2\u0293i\3\2\2\2\u0294\u0295"+
		"\7\3\2\2\u0295\u0296\7q\2\2\u0296\u0297\5F$\2\u0297\u0298\5h\65\2\u0298"+
		"\u0299\7\5\2\2\u0299\u02a7\3\2\2\2\u029a\u029b\7\3\2\2\u029b\u029c\7q"+
		"\2\2\u029c\u029d\5F$\2\u029d\u029e\5l\67\2\u029e\u029f\7\5\2\2\u029f\u02a7"+
		"\3\2\2\2\u02a0\u02a1\7\3\2\2\u02a1\u02a2\5^\60\2\u02a2\u02a3\5P)\2\u02a3"+
		"\u02a4\5L\'\2\u02a4\u02a5\7\5\2\2\u02a5\u02a7\3\2\2\2\u02a6\u0294\3\2"+
		"\2\2\u02a6\u029a\3\2\2\2\u02a6\u02a0\3\2\2\2\u02a7k\3\2\2\2\u02a8\u02a9"+
		"\7\3\2\2\u02a9\u02aa\5^\60\2\u02aa\u02ab\5P)\2\u02ab\u02ac\5n8\2\u02ac"+
		"\u02ad\7\5\2\2\u02adm\3\2\2\2\u02ae\u02b5\7\3\2\2\u02af\u02b0\5Z.\2\u02b0"+
		"\u02b1\5n8\2\u02b1\u02b2\5n8\2\u02b2\u02b6\3\2\2\2\u02b3\u02b4\7\t\2\2"+
		"\u02b4\u02b6\5n8\2\u02b5\u02af\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b6\u02b7"+
		"\3\2\2\2\u02b7\u02b8\7\5\2\2\u02b8\u02bc\3\2\2\2\u02b9\u02bc\7\60\2\2"+
		"\u02ba\u02bc\5L\'\2\u02bb\u02ae\3\2\2\2\u02bb\u02b9\3\2\2\2\u02bb\u02ba"+
		"\3\2\2\2\u02bco\3\2\2\2\u02bd\u02be\7\3\2\2\u02be\u02bf\7\4\2\2\u02bf"+
		"\u02c0\5r:\2\u02c0\u02c2\5t;\2\u02c1\u02c3\5\b\5\2\u02c2\u02c1\3\2\2\2"+
		"\u02c2\u02c3\3\2\2\2\u02c3\u02c5\3\2\2\2\u02c4\u02c6\5v<\2\u02c5\u02c4"+
		"\3\2\2\2\u02c5\u02c6\3\2\2\2\u02c6\u02c7\3\2\2\2\u02c7\u02c8\5x=\2\u02c8"+
		"\u02ca\5\u0080A\2\u02c9\u02cb\5\u0082B\2\u02ca\u02c9\3\2\2\2\u02ca\u02cb"+
		"\3\2\2\2\u02cb\u02cd\3\2\2\2\u02cc\u02ce\5\u0086D\2\u02cd\u02cc\3\2\2"+
		"\2\u02cd\u02ce\3\2\2\2\u02ceq\3\2\2\2\u02cf\u02d0\7\3\2\2\u02d0\u02d1"+
		"\7\61\2\2\u02d1\u02d2\7n\2\2\u02d2\u02d3\7\5\2\2\u02d3s\3\2\2\2\u02d4"+
		"\u02d5\7\3\2\2\u02d5\u02d6\7\62\2\2\u02d6\u02d7\7n\2\2\u02d7\u02d8\7\5"+
		"\2\2\u02d8u\3\2\2\2\u02d9\u02da\7\3\2\2\u02da\u02db\7\63\2\2\u02db\u02dc"+
		"\5\f\7\2\u02dc\u02dd\7\5\2\2\u02ddw\3\2\2\2\u02de\u02df\7\3\2\2\u02df"+
		"\u02e3\7\64\2\2\u02e0\u02e2\5z>\2\u02e1\u02e0\3\2\2\2\u02e2\u02e5\3\2"+
		"\2\2\u02e3\u02e1\3\2\2\2\u02e3\u02e4\3\2\2\2\u02e4\u02e6\3\2\2\2\u02e5"+
		"\u02e3\3\2\2\2\u02e6\u02e7\7\5\2\2\u02e7y\3\2\2\2\u02e8\u02f6\5|?\2\u02e9"+
		"\u02ea\7\3\2\2\u02ea\u02eb\7m\2\2\u02eb\u02ec\5P)\2\u02ec\u02ed\7p\2\2"+
		"\u02ed\u02ee\7\5\2\2\u02ee\u02f6\3\2\2\2\u02ef\u02f0\7\3\2\2\u02f0\u02f1"+
		"\7q\2\2\u02f1\u02f2\7p\2\2\u02f2\u02f3\5|?\2\u02f3\u02f4\7\5\2\2\u02f4"+
		"\u02f6\3\2\2\2\u02f5\u02e8\3\2\2\2\u02f5\u02e9\3\2\2\2\u02f5\u02ef\3\2"+
		"\2\2\u02f6{\3\2\2\2\u02f7\u02fe\5~@\2\u02f8\u02f9\7\3\2\2\u02f9\u02fa"+
		"\7\26\2\2\u02fa\u02fb\5~@\2\u02fb\u02fc\7\5\2\2\u02fc\u02fe\3\2\2\2\u02fd"+
		"\u02f7\3\2\2\2\u02fd\u02f8\3\2\2\2\u02fe}\3\2\2\2\u02ff\u0300\7\3\2\2"+
		"\u0300\u0304\5$\23\2\u0301\u0303\7n\2\2\u0302\u0301\3\2\2\2\u0303\u0306"+
		"\3\2\2\2\u0304\u0302\3\2\2\2\u0304\u0305\3\2\2\2\u0305\u0307\3\2\2\2\u0306"+
		"\u0304\3\2\2\2\u0307\u0308\7\5\2\2\u0308\177\3\2\2\2\u0309\u030a\7\3\2"+
		"\2\u030a\u030b\7\65\2\2\u030b\u030c\5\64\33\2\u030c\u030d\7\5\2\2\u030d"+
		"\u0081\3\2\2\2\u030e\u030f\7\3\2\2\u030f\u0310\7\17\2\2\u0310\u0311\5"+
		"\u0084C\2\u0311\u0312\7\5\2\2\u0312\u0083\3\2\2\2\u0313\u0314\7\3\2\2"+
		"\u0314\u0318\7\24\2\2\u0315\u0317\5\u0084C\2\u0316\u0315\3\2\2\2\u0317"+
		"\u031a\3\2\2\2\u0318\u0316\3\2\2\2\u0318\u0319\3\2\2\2\u0319\u031b\3\2"+
		"\2\2\u031a\u0318\3\2\2\2\u031b\u032e\7\5\2\2\u031c\u031d\7\3\2\2\u031d"+
		"\u031e\7\31\2\2\u031e\u031f\7\3\2\2\u031f\u0320\5&\24\2\u0320\u0321\7"+
		"\5\2\2\u0321\u0322\5\u0084C\2\u0322\u0323\7\5\2\2\u0323\u032e\3\2\2\2"+
		"\u0324\u0325\7\3\2\2\u0325\u0327\7\35\2\2\u0326\u0328\7n\2\2\u0327\u0326"+
		"\3\2\2\2\u0327\u0328\3\2\2\2\u0328\u0329\3\2\2\2\u0329\u032a\5\u008cG"+
		"\2\u032a\u032b\7\5\2\2\u032b\u032e\3\2\2\2\u032c\u032e\5\u008cG\2\u032d"+
		"\u0313\3\2\2\2\u032d\u031c\3\2\2\2\u032d\u0324\3\2\2\2\u032d\u032c\3\2"+
		"\2\2\u032e\u0085\3\2\2\2\u032f\u0330\7\3\2\2\u0330\u0331\7\66\2\2\u0331"+
		"\u0332\5\u0088E\2\u0332\u0333\5\u008aF\2\u0333\u0334\7\5\2\2\u0334\u0087"+
		"\3\2\2\2\u0335\u0336\t\b\2\2\u0336\u0089\3\2\2\2\u0337\u0338\7\3\2\2\u0338"+
		"\u0339\5Z.\2\u0339\u033a\5\u008aF\2\u033a\u033b\5\u008aF\2\u033b\u033c"+
		"\7\5\2\2\u033c\u035e\3\2\2\2\u033d\u033e\7\3\2\2\u033e\u033f\t\t\2\2\u033f"+
		"\u0341\5\u008aF\2\u0340\u0342\5\u008aF\2\u0341\u0340\3\2\2\2\u0342\u0343"+
		"\3\2\2\2\u0343\u0341\3\2\2\2\u0343\u0344\3\2\2\2\u0344\u0345\3\2\2\2\u0345"+
		"\u0346\7\5\2\2\u0346\u035e\3\2\2\2\u0347\u0348\7\3\2\2\u0348\u0349\7\t"+
		"\2\2\u0349\u034a\5\u008aF\2\u034a\u034b\7\5\2\2\u034b\u035e\3\2\2\2\u034c"+
		"\u035e\7p\2\2\u034d\u034e\7\3\2\2\u034e\u0352\5\32\16\2\u034f\u0351\7"+
		"n\2\2\u0350\u034f\3\2\2\2\u0351\u0354\3\2\2\2\u0352\u0350\3\2\2\2\u0352"+
		"\u0353\3\2\2\2\u0353\u0355\3\2\2\2\u0354\u0352\3\2\2\2\u0355\u0356\7\5"+
		"\2\2\u0356\u035e\3\2\2\2\u0357\u035e\5\32\16\2\u0358\u035e\79\2\2\u0359"+
		"\u035a\7\3\2\2\u035a\u035b\7:\2\2\u035b\u035c\7n\2\2\u035c\u035e\7\5\2"+
		"\2\u035d\u0337\3\2\2\2\u035d\u033d\3\2\2\2\u035d\u0347\3\2\2\2\u035d\u034c"+
		"\3\2\2\2\u035d\u034d\3\2\2\2\u035d\u0357\3\2\2\2\u035d\u0358\3\2\2\2\u035d"+
		"\u0359\3\2\2\2\u035e\u008b\3\2\2\2\u035f\u0360\7\3\2\2\u0360\u0364\7\24"+
		"\2\2\u0361\u0363\5\u008cG\2\u0362\u0361\3\2\2\2\u0363\u0366\3\2\2\2\u0364"+
		"\u0362\3\2\2\2\u0364\u0365\3\2\2\2\u0365\u0367\3\2\2\2\u0366\u0364\3\2"+
		"\2\2\u0367\u03ac\7\5\2\2\u0368\u0369\7\3\2\2\u0369\u036a\7\31\2\2\u036a"+
		"\u036b\7\3\2\2\u036b\u036c\5&\24\2\u036c\u036d\7\5\2\2\u036d\u036e\5\u008c"+
		"G\2\u036e\u036f\7\5\2\2\u036f\u03ac\3\2\2\2\u0370\u0371\7\3\2\2\u0371"+
		"\u0372\7q\2\2\u0372\u0373\7 \2\2\u0373\u0374\5\64\33\2\u0374\u0375\7\5"+
		"\2\2\u0375\u03ac\3\2\2\2\u0376\u0377\7\3\2\2\u0377\u0378\7;\2\2\u0378"+
		"\u0379\5\64\33\2\u0379\u037a\7\5\2\2\u037a\u03ac\3\2\2\2\u037b\u037c\7"+
		"\3\2\2\u037c\u037d\7<\2\2\u037d\u037e\5\64\33\2\u037e\u037f\7\5\2\2\u037f"+
		"\u03ac\3\2\2\2\u0380\u0381\7\3\2\2\u0381\u0382\7=\2\2\u0382\u0383\7p\2"+
		"\2\u0383\u0384\5\64\33\2\u0384\u0385\7\5\2\2\u0385\u03ac\3\2\2\2\u0386"+
		"\u0387\7\3\2\2\u0387\u0388\7>\2\2\u0388\u0389\5\64\33\2\u0389\u038a\7"+
		"\5\2\2\u038a\u03ac\3\2\2\2\u038b\u038c\7\3\2\2\u038c\u038d\7?\2\2\u038d"+
		"\u038e\5\64\33\2\u038e\u038f\5\64\33\2\u038f\u0390\7\5\2\2\u0390\u03ac"+
		"\3\2\2\2\u0391\u0392\7\3\2\2\u0392\u0393\7@\2\2\u0393\u0394\5\64\33\2"+
		"\u0394\u0395\5\64\33\2\u0395\u0396\7\5\2\2\u0396\u03ac\3\2\2\2\u0397\u0398"+
		"\7\3\2\2\u0398\u0399\7A\2\2\u0399\u039a\7p\2\2\u039a\u039b\5\64\33\2\u039b"+
		"\u039c\5\64\33\2\u039c\u039d\7\5\2\2\u039d\u03ac\3\2\2\2\u039e\u039f\7"+
		"\3\2\2\u039f\u03a0\7B\2\2\u03a0\u03a1\7p\2\2\u03a1\u03a2\7p\2\2\u03a2"+
		"\u03a3\5\64\33\2\u03a3\u03a4\7\5\2\2\u03a4\u03ac\3\2\2\2\u03a5\u03a6\7"+
		"\3\2\2\u03a6\u03a7\7C\2\2\u03a7\u03a8\7p\2\2\u03a8\u03a9\5\64\33\2\u03a9"+
		"\u03aa\7\5\2\2\u03aa\u03ac\3\2\2\2\u03ab\u035f\3\2\2\2\u03ab\u0368\3\2"+
		"\2\2\u03ab\u0370\3\2\2\2\u03ab\u0376\3\2\2\2\u03ab\u037b\3\2\2\2\u03ab"+
		"\u0380\3\2\2\2\u03ab\u0386\3\2\2\2\u03ab\u038b\3\2\2\2\u03ab\u0391\3\2"+
		"\2\2\u03ab\u0397\3\2\2\2\u03ab\u039e\3\2\2\2\u03ab\u03a5\3\2\2\2\u03ac"+
		"\u008d\3\2\2\2N\u0090\u0096\u0099\u009c\u009f\u00a2\u00a5\u00aa\u00b9"+
		"\u00c5\u00cb\u00d0\u00d3\u00d8\u00e2\u00e7\u00f3\u00f7\u00fb\u0111\u011f"+
		"\u0125\u012a\u012d\u0132\u013f\u0151\u0153\u0159\u015b\u0163\u016c\u0192"+
		"\u019f\u01b6\u01bc\u01be\u01c6\u01d2\u01d8\u01dd\u01eb\u0204\u020d\u0213"+
		"\u021a\u021f\u0230\u023e\u0245\u024a\u0257\u025e\u026c\u0272\u0279\u0292"+
		"\u02a6\u02b5\u02bb\u02c2\u02c5\u02ca\u02cd\u02e3\u02f5\u02fd\u0304\u0318"+
		"\u0327\u032d\u0343\u0352\u035d\u0364\u03ab";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}