package edu.kit.aquaplanning.model.lifted.htn;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;

public class Task {

	private String name;
	private List<Argument> arguments;
	private String tag;
	
	public Task(String name) {
		this.name = name;
		this.arguments = new ArrayList<>();
	}
	
	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public List<Argument> getArguments() {
		return arguments;
	}
	
	public String getName() {
		return name;
	}
	
	public Task getTaskBoundToArguments(List<Argument> refArgs, List<Argument> argVals) {
		return getTaskBoundToArguments(refArgs, argVals, false);
	}
	
	public Task getTaskBoundToArguments(List<Argument> refArgs, List<Argument> argVals, boolean setNullArgs) {
		Task t = new Task(name);
		t.setTag(tag);
		for (int argIdx = 0; argIdx < arguments.size(); argIdx++) {
			Argument arg = arguments.get(argIdx);
			boolean added = false;
			for (int refArgIdx = 0; refArgIdx < refArgs.size(); refArgIdx++) {
				Argument refArg = refArgs.get(refArgIdx);
				if (refArg != null && arg.getName().equals(refArg.getName())) {
					Argument argVal = argVals.get(refArgIdx);
					if (argVal != null) {
						t.addArgument(argVal);
						added = true;
						break;
					}
				}
			}
			if (!added)
				t.addArgument(setNullArgs ? null : arg);
		}
		
		return t;
	}
	
	public void setArgument(Argument refArg, Argument argVal) {
		for (int argIdx = 0; argIdx < arguments.size(); argIdx++) {
			if (arguments.get(argIdx).getName().equals(refArg.getName())) {
				arguments.set(argIdx, argVal);
			}
		}
	}
	
	public String toTaskString() {
		String out = "(" + name + " ";
		for (Argument arg : arguments) {
			if (arg == null) return null;
			out += arg.getName() + " ";
		}
		out = out.substring(0, out.length()-1) + ")";
		return out;
	}
	
	public Task normalize() {
		Task task = new Task(name);
		int i = 0;
		for (Argument arg : arguments) {
			task.addArgument(new Argument("?a" + i, arg.getType()));
			i++;
		}
		return task;
	}
	
	@Override
	public String toString() {
		String out = (tag != null ? tag+":" : "") + "(" + name + " ";
		for (Argument arg : arguments) {
			out += arg + " ";
		}
		out = out.substring(0, out.length()-1) + ")";
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public Task copy() {
		Task task = new Task(this.name);
		task.arguments = new ArrayList<>(arguments);
		task.tag = this.tag;
		return task;
	}
}
