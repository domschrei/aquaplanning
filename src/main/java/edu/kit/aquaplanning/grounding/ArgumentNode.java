package edu.kit.aquaplanning.grounding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.model.lifted.Argument;

public class ArgumentNode {
	
	private Map<String, Integer> argumentIds;
	private Map<Integer, ArgumentNode> children;
	private boolean holds;
	
	public ArgumentNode(Map<String, Integer> argumentIds) {
		this.argumentIds = argumentIds;
		this.children = new HashMap<>();
	}
	
	public void add(List<Argument> args) {
		add(args, 0);
	}
	
	public boolean contains(List<Argument> args) {
		return contains(args, 0);
	}
	
	private void add(List<Argument> args, int argPos) {
		if (argPos == args.size()) {
			holds = true;
		} else {				
			int argId = argumentIds.get(args.get(argPos).getName());
			if (!children.containsKey(argId)) {
				children.put(argId, new ArgumentNode(argumentIds));
			}
			children.get(argId).add(args, argPos+1);
		}
	}
	
	private boolean contains(List<Argument> args, int argPos) {
		if (argPos == args.size() || children.isEmpty()) {
			return holds;
		} else {
			String argName = args.get(argPos).getName();
			int argId = argumentIds.getOrDefault(argName, -1);
			if (!children.containsKey(argId))
				return false;
			return children.get(argId).contains(args, argPos+1);
		}
	}
}