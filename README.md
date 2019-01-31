# Aquaplanning – QUick Automated Planning

Aquaplanning is a Java framework for Automated Planning, developed by Tomáš Balyo and Dominik Schreiber for the lecture [Automated Planning and Scheduling](https://baldur.iti.kit.edu/plan/) at Karlsruhe Institute of Technology (KIT). It is meant as a simple, but extensible and reasonably powerful planning environment for PDDL problems, for educational and any other means.

## Features

Here we give an overview on what Aquaplanning can do.

### Problem Models

Aquaplanning supports PDDL (Planning Domain Description Language) files as an input for planning domains and problems. The supported subset of PDDL is roughly **on par with [Fast Downward](http://www.fast-downward.org/)**, today's prototypical state-of-the-art classical planner. This means that the following features are implemented as of now (i.e. problems with these features can be parsed, grounded, and solved):

* Basic STRIPS planning with typing
* Negative conditions (preconditions / effects / goals)
* Equality (as a universal predicate `(= obj1 obj2)`)
* Conditional effects (`when (condition) (additional-effect)`)
* Universal and existential quantifications
* All ADL features, i.e. disjunctive conditions with `or`, `imply`, and non-atomic `not` expressions
* Action costs (in its basic form `(:function total-cost - number)`, with constant positive cost per operator)
* Derived predicates (as long as the resulting ground axioms are not directly recursing on themselves)

In addition, Aquaplanning supports the following aspects of **numeric planning**:

* Numeric fluents (a.k.a. functions), e.g. `(capacity ?truck)`
* Numeric expressions in preconditions, e.g. `(> (capacity ?truck) 0)`
* Numeric effects, e.g. `(decrease (capacity ?truck) 1)`

For planning problems using these features (or any subset), a full representation of the read problem is available in the form of Java objects after parsing, as well as a separate ground representation after grounding the problem.

### Preprocessing and Grounding

For processing PDDL input, a parser generated from [this ANTLR grammar for PDDL](https://github.com/domschrei/pddl_antlr_grammar) is used.

Parsed problems will first be simplified in a preprocessing step: notably, all quantifications are eliminated into flat conjunctions / disjunctions, and complex logical expressions can be transformed into Disjunctive Normal Form (DNF) in order to split disjunctive operators into multiple conjunctive operators, if desired. 
Derived predicates and conditional effects remain an explicit part of the problem representation, also during planning. Equality predicates can be compiled out (simplifying some preconditions) or can be kept just like normal predicates.

For grounding purposes, Aquaplanning traverses the problem's delete-relaxed planning graph. As the grounding does not exhaustively test all possible argument combinations for each operator but only a small subset, both run times and produced problem volume are usually quite reasonable.  

### Planning

The following planners are implemented:
* A generic **state space forward search** with a few common search strategies (BFS, DFS, A\*, Weighted A\*, Best-first, random choice) and a couple of heuristics to guide the heuristic search strategies searches. (Not all heuristics support all of the mentioned PDDL features.)
* Some planning algorithms based on **incremental SAT solving**. (Does not support conditional effects, derived predicates, or numeric planning.) 

The following types of planning are generally supported w.r.t. the problem representation, but there are no (notable) realizations of it yet:
* Parallel planning. (There is an easy and generic interface for implementing portfolio-style planners.)
* Optimal planning. (Optimal plans can only be found if all actions have equal cost and either incremental SAT with step size 1 or forward search with a (nearly) admissible heuristic is used.)
* Lifted planning.

### Post-Processing

Aquaplanning features an interface for **anytime plan optimization** procedures, but there are no notable realizations of it yet.

At the end of the pipeline, a tiny **plan validator** can be employed to ensure the planner's correctness.

## Building and installing

The framework is written from scratch and thus does not rely on any Planning-related frameworks. It does depend on antlr4 (for the parsing of PDDL files), Picocli for argument parsing, and sat4j as a SAT solver backend. We use [Maven](https://maven.apache.org/) as a build system to resolve these dependencies (package `maven` in Debian-based Linux distributions). 
In the base directory, run `mvn package` which will create a runnable jar file in `target/aquaplanning-<version>-jar-with-dependencies.jar`. You can launch the application with `java -jar <runnable-jar>`. JUnit tests are disabled by default (as they potentially take a long time); you can re-enable them by appending `-DskipTests=false` to the build command.

## Usage

Aquaplanning can be used as an off-the-shelf planner; you can specify a domain file and a problem file as arguments (in that order), and it will attempt to parse, ground, and solve the problem. You can try the files provided in the `testfiles/` directory. 
All available options will be displayed when running the .jar without any arguments or with the `--help` flag.

When you want to use your own planner, implement the `Planner` interface and take a look at the `DefaultPlanner` class as a point of reference.

If you find any bugs or you consider something to be missing, please let us know. We appreciate receiving issues and/or pull requests!
