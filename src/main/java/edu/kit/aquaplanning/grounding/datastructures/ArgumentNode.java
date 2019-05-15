package edu.kit.aquaplanning.grounding.datastructures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.lifted.Argument;

/**
 * Node of a tree-like set structure used to represent objects with instantiated
 * parameters, e.g. actions and conditions. Each node corresponds to a certain
 * argument position and contains for each possible argument choice a child node
 * with the next argument position, and so on. The root node of the set
 * structure represents the first argument position (index 0).
 */
public class ArgumentNode {

	/**
	 * Maps every argument to a unique ID.
	 */
	private Map<String, Integer> argumentIds;
	/**
	 * Maps argument IDs to corresponding child argument nodes.
	 */
	private Map<Integer, ArgumentNode> children;

	private boolean isLeafNode;

	/**
	 * Creates an empty argument node. Requires a map containing an ID of each
	 * argument in the problem.
	 */
	public ArgumentNode(Map<String, Integer> argumentIds) {
		this.argumentIds = argumentIds;
		this.children = new HashMap<>();
	}

	/**
	 * Adds a "path" to the tree rooted at this node corresponding to the provided
	 * list of arguments.
	 */
	public void add(List<Argument> args) {
		add(args, 0);
	}
	
	public void remove(List<Argument> args) {
		remove(args, 0);
	}

	/**
	 * Decides whether the tree rooted at this node contains the provided "path" of
	 * arguments.
	 */
	public boolean contains(List<Argument> args) {
		return contains(args, 0);
	}

	public boolean containsPartiallyInstantiatedArgs(List<Argument> args) {
		return containsPartiallyInstantiatedArgs(args, 0);
	}

	/**
	 * Adds a "path" to the tree rooted at this node corresponding to the provided
	 * list of arguments beginning at the provided index.
	 */
	private void add(List<Argument> args, int argPos) {
		if (argPos == args.size()) {
			// All arguments have been added;
			// this node is a leaf
			isLeafNode = true;
		} else {
			// Recurse on the child node which corresponds to the
			// argument at the first considered position (argPos),
			// creating it if it does not exist yet
			String argName = args.get(argPos).getName();
			int argId = argumentIds.get(argName);
			if (!children.containsKey(argId)) {
				children.put(argId, new ArgumentNode(argumentIds));
			}
			children.get(argId).add(args, argPos + 1);
		}
	}
	
	private void remove(List<Argument> args, int argPos) {
		if (argPos == args.size()) {
			isLeafNode = false;
		} else {
			int argId = argumentIds.get(args.get(argPos).getName());
			if (children.containsKey(argId)) {
				children.get(argId).remove(args, argPos + 1);
			}
		}
	}

	/**
	 * Decides whether the tree rooted at this node contains the provided "path" of
	 * arguments beginning at the provided position.
	 */
	private boolean contains(List<Argument> args, int argPos) {
		if (argPos == args.size()) {
			// No arguments left to check;
			// argument list is contained if this is a leaf
			return isLeafNode;
		} else {
			// Check if the next argument is contained
			String argName = args.get(argPos).getName();
			int argId = argumentIds.getOrDefault(argName, -1);
			if (argId < 0 || !children.containsKey(argId)) {
				// Dead end: argument path is not contained
				return false;
			}
			// Recurse on the corresponding child
			return children.get(argId).contains(args, argPos + 1);
		}
	}

	private boolean containsPartiallyInstantiatedArgs(List<Argument> args, int argPos) {

		if (argPos == args.size()) {
			// No arguments left to check;
			// argument list is contained!
			return true;
		} else {
			// Check if the next argument is contained
			if (!args.get(argPos).isConstant()) {
				// Argument is a variable / not instantiated:
				// Check if there is any valid instantiation
				// of the remaining argument list
				if (isLeafNode)
					return true;
				for (int id : children.keySet()) {
					if (children.get(id).containsPartiallyInstantiatedArgs(args, argPos + 1)) {
						return true;
					}
				}
				// No valid instantiation
				// Are any arguments left to check?
				for (int nextPos = argPos + 1; nextPos < args.size(); nextPos++) {
					if (args.get(nextPos).isConstant()) {

						// print
						/*
						 * System.out.println(args + " (failed at pos " + argPos + ": lookahead to pos "
						 * + nextPos + ")"); for (String arg : argumentIds.keySet()) { int argId =
						 * argumentIds.get(arg); if (children.containsKey(argId))
						 * System.out.println("  " + arg + " : " + children.get(argId)); }
						 */
						return false; // -- yes
					}
				}
				return true; // -- no
			}

			String argName = args.get(argPos).getName();
			int argId = argumentIds.getOrDefault(argName, -1);
			if (argId < 0 || !children.containsKey(argId)) {
				// Dead end: argument path is not contained
				return false;
			}
			// Recurse on the corresponding child
			return children.get(argId).containsPartiallyInstantiatedArgs(args, argPos + 1);
		}
	}

	@Override
	public String toString() {
		if (isLeafNode)
			return ".";
		String out = "{ ";
		for (String arg : argumentIds.keySet()) {
			if (children.containsKey(argumentIds.get(arg))) {
				out += "" + arg + "->" + children.get(argumentIds.get(arg)) + " ";
			}
		}
		return out + "}";
	}

}