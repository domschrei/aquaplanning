# Aquaplanning – QUick Automated Planning

This is a Java framework for Automated Planning, developed by Tomáš Balyo and Dominik Schreiber for the lecture [Automated Planning and Scheduling](https://baldur.iti.kit.edu/plan/) at Karlsruhe Institute of Technology (KIT). It is meant as a simple, but extensible and reasonably powerful planning environment for PDDL problems, for educational and any other means.

## Features

Aquaplanning supports PDDL (Planning Domain Description Language) files as an input for planning domains and problems. The supported subset of PDDL is roughly **on par with [Fast Downward](http://www.fast-downward.org/)**, today's prototypical state-of-the-art classical planner. This means that the following features are implemented as of now (i.e. problems with these features can be parsed, grounded, and solved):

* Basic STRIPS planning with typing
* Negative conditions (preconditions / effects / goals)
* Equality (as a universal predicate `(= obj1 obj2)`)
* Conditional effects (`when (condition) (additional-effect)`)
* Universal and existential quantifications
* All ADL features, i.e. disjunctive conditions with `or`, `imply`, and non-atomic `not` expressions
* Action costs (in its basic form `(:function total-cost - number)`, with constant positive cost per operator)
* Derived predicates (as long as the resulting ground axioms are not directly recursing on themselves)

For planning problems using these features (or any subset), a full representation of the read problem is available in the form of Java objects after parsing, as well as a separate representation after grounding the problem.
For grounding purposes, Aquaplanning traverses the problem's (delete-)relaxed planning graph, resulting in a reasonable amount of atoms and actions in most cases.

A generic state space forward search is provided as a planner. As of now, a few common search strategies (BFS, DFS, A\*, Weighted A\*, Best-first, random choice) are implemented, as well as a couple of simple heuristics to guide the A\* and Best-first searches.  

At the end of the pipeline, a tiny plan validator can be employed to ensure the planner's correctness.

## Building and installing

The framework is written from scratch and thus does not rely on any Planning-related frameworks. It does depend on antlr4 (for the parsing of PDDL files), Picocli for argument parsing, and sat4j as a SAT solver backend. We use [Maven](https://maven.apache.org/) as a build system to resolve these dependencies (package `maven` in Debian-based Linux distributions). 
In the base directory, run `mvn package` which will run the included JUnit tests and create a runnable jar file in `target/aquaplanning-<version>-jar-with-dependencies.jar`. You can launch the application with `java -jar <runnable-jar> <domain-file> <problem-file>`.

Using the Eclipse IDE, the project can be directly imported as a Maven project and then built and/or installed.

## Usage

Aquaplanning can be used as an off-the-shelf planner; you can specify a domain file and a problem file as arguments (in that order), and it will attempt to parse, ground, and solve the problem. You can try the files provided in the `testfiles/` directory. 
All available options will be displayed when running the .jar without any arguments.

When you want to use your own planner, implement the Planner interface and take a look at the DefaultPlanner class as a point of reference.

If you find any bugs or you consider something to be missing, please let us know. We appreciate receiving issues and/or pull requests!
