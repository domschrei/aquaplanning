package edu.kit.aquaplanning.model.lifted;

import java.util.ArrayList;
import java.util.List;

public class Function {

	private String name;
	private List<Type> argumentTypes;
	private List<Argument> arguments;

	public Function(String name) {
		this.name = name;
		this.arguments = new ArrayList<>();
		this.argumentTypes = new ArrayList<>();
	}

	public void addArgument(Argument arg) {
		this.arguments.add(arg);
	}

	public void addArgumentType(Type type) {
		this.argumentTypes.add(type);
	}

	public String getName() {
		return name;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public List<Type> getArgumentTypes() {
		return argumentTypes;
	}

	public int getNumArgs() {
		return argumentTypes.size();
	}

	public Function getFunctionBoundToArguments(List<Argument> refArgs, List<Argument> argValues) {
		Function f = new Function(name);
		for (Argument arg : arguments) {
			boolean replaced = false;
			for (int refArgIdx = 0; refArgIdx < refArgs.size(); refArgIdx++) {
				if (arg.getName().equals(refArgs.get(refArgIdx).getName())) {
					f.addArgument(argValues.get(refArgIdx));
					replaced = true;
					break;
				}
			}
			if (!replaced)
				f.addArgument(arg);
		}
		return f;
	}

	@Override
	public String toString() {
		String out = "";
		out += name + "( ";
		for (Type type : argumentTypes) {
			out += type.getName() + " ";
		}
		out += ")";
		return out;
	}

	public String toString(List<Argument> args) {
		String out = "";
		out += name + "( ";
		for (Argument arg : args) {
			out += arg + " ";
		}
		out += ")";
		return out;
	}

	public Function copy() {
		Function f = new Function(name);
		f.arguments = new ArrayList<>();
		f.arguments.addAll(arguments);
		f.argumentTypes = new ArrayList<>();
		f.argumentTypes.addAll(argumentTypes);
		return f;
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
		Function other = (Function) obj;
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
}
