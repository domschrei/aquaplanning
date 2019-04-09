package edu.kit.aquaplanning.model.lifted.htn;

import java.util.ArrayList;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;

public class Method {

	private String name;
	private List<Argument> explicitArguments;
	private List<Argument> implicitArguments;
	
	private List<Task> subtasks;
	private List<Constraint> constraints;
	
	public Method(String name) {
		this.name = name;
		this.explicitArguments = new ArrayList<>();
		this.implicitArguments = new ArrayList<>();
		this.subtasks = new ArrayList<>();
		this.constraints = new ArrayList<>();
	}
	
	public void addExplicitArgument(Argument arg) {
		this.explicitArguments.add(arg);
	}
	
	public void addImplicitArgument(Argument arg) {
		this.implicitArguments.add(arg);
	}
	
	public void addSubtask(Task task) {
		this.subtasks.add(task);
	}
	
	public void addConstraint(Constraint constr) {
		this.constraints.add(constr);
	}
	
	public void tagLastSubtask(String tag) {
		this.subtasks.get(subtasks.size()-1).setTag(tag);
	}
	
	public void addConditionToLastConstraint(AbstractCondition c) {
		this.constraints.get(constraints.size()-1).setCondition(c);
	}
	
	public Type getTypeOfArgument(String argStr) {
		for (Argument arg : explicitArguments) {
			if (arg.getName().equals(argStr)) {
				return arg.getType();
			}
		}
		for (Argument arg : implicitArguments) {
			if (arg.getName().equals(argStr)) {
				return arg.getType();
			}
		}
		return null;
	}
	
	public boolean hasArgument(String argStr) {
		for (Argument arg : explicitArguments) {
			if (arg.getName().equals(argStr)) {
				return true;
			}
		}
		for (Argument arg : implicitArguments) {
			if (arg.getName().equals(argStr)) {
				return true;
			}
		}
		return false;
	}
	
	public void updateArgumentType(String argStr, Type type) {
		for (Argument arg : explicitArguments) {
			if (arg.getName().equals(argStr)) {
				arg.setType(type);
				return;
			}
		}
		for (Argument arg : implicitArguments) {
			if (arg.getName().equals(argStr)) {
				arg.setType(type);
				return;
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public List<Task> getSubtasks() {
		return subtasks;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	public List<Argument> getExplicitArguments() {
		return explicitArguments;
	}
	
	public List<Argument> getImplicitArguments() {
		return implicitArguments;
	}

	public Method getMethodBoundToArguments(List<Argument> explicitArgs, List<Argument> implicitArgs) {

		if (explicitArgs.size() != this.explicitArguments.size()) {
			throw new RuntimeException("Illegal number of binding arguments: " + this.explicitArguments + " , " + explicitArgs);
		}
		if (implicitArgs.size() != this.implicitArguments.size()) {
			throw new RuntimeException("Illegal number of binding arguments: " + this.implicitArguments + " , " + implicitArgs);
		}
		
		List<Argument> refArgs = new ArrayList<>();
		List<Argument> argVals = new ArrayList<>();
		List<Argument> newExplicits = new ArrayList<>();
		for (int i = 0; i < explicitArguments.size(); i++) {
			Argument oldArg = explicitArguments.get(i);
			Argument newArg = explicitArgs.get(i);
			if (newArg != null && !oldArg.equals(newArg)) {
				refArgs.add(oldArg);
				argVals.add(newArg);
				newExplicits.add(newArg);
			} else {
				newExplicits.add(oldArg);
			}
		}
		List<Argument> newImplicits = new ArrayList<>();
		for (int i = 0; i < implicitArguments.size(); i++) {
			Argument oldArg = implicitArguments.get(i);
			Argument newArg = implicitArgs.get(i);
			if (newArg != null && !oldArg.equals(newArg)) {
				refArgs.add(oldArg);
				argVals.add(newArg);
				newImplicits.add(newArg);
			} else {
				newImplicits.add(oldArg);
			}
		}
		
		Method m = new Method(name);
		m.implicitArguments = newImplicits;
		m.explicitArguments = newExplicits;
		for (Task t : subtasks) {
			m.addSubtask(t.getTaskBoundToArguments(refArgs, argVals));
		}
		for (Constraint c : constraints) {
			m.addConstraint(c.getConstraintBoundToArguments(refArgs, argVals));
		}
		return m;
	}
	
	public void setImplicitArgument(Argument refArg, Argument argVal) {
		
		for (int i = 0; i < implicitArguments.size(); i++) {
			if (implicitArguments.get(i).getName().equals(refArg.getName())) {
				implicitArguments.set(i, argVal);
				break;
			}
		}
		for (Task t : subtasks) {
			t.setArgument(refArg, argVal);
		}
		for (Constraint c : constraints) {
			c.setArgument(refArg, argVal);
		}
	}
	
	public String getTaskString() {
		String out = "(" + name + " ";
		for (Argument arg : explicitArguments) {
			out += arg.getName() + " ";
		}
		out = out.substring(0, out.length()-1) + ")";
		return out;
	}
	
	public Task toTask() {
		Task task = new Task(name);
		explicitArguments.forEach(arg -> task.addArgument(arg));
		return task;
	}
	
	public Task toTaskWithImplicitArgs() {
		Task task = new Task(name);
		explicitArguments.forEach(arg -> task.addArgument(arg));
		implicitArguments.forEach(arg -> task.addArgument(arg));
		return task;
	}
	
	@Override
	public String toString() {
		String out = "(" + name;
		for (Argument arg : explicitArguments) {
			out += " " + arg;
		}
		out += ") [";
		for (Argument arg : implicitArguments) {
			out += " " + arg;
		}
		out += "] {";
		for (Task task : subtasks) {
			out += " " + task;
		}
		out += " } ";
		for (Constraint c : constraints) {
			out += c + " ";
		}
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((explicitArguments == null) ? 0 : explicitArguments.hashCode());
		result = prime * result + ((implicitArguments == null) ? 0 : implicitArguments.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subtasks == null) ? 0 : subtasks.hashCode());
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
		Method other = (Method) obj;
		if (explicitArguments == null) {
			if (other.explicitArguments != null)
				return false;
		} else if (!explicitArguments.equals(other.explicitArguments))
			return false;
		if (implicitArguments == null) {
			if (other.implicitArguments != null)
				return false;
		} else if (!implicitArguments.equals(other.implicitArguments))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subtasks == null) {
			if (other.subtasks != null)
				return false;
		} else if (!subtasks.equals(other.subtasks))
			return false;
		return true;
	}
	
	public Method copy() {
		Method m = new Method(name);
		m.constraints = new ArrayList<>();
		for (Constraint c : constraints) {
			m.constraints.add(c.copy());
		}
		m.explicitArguments = new ArrayList<>(explicitArguments);
		m.implicitArguments = new ArrayList<>(implicitArguments);
		for (Task t : subtasks) {
			m.subtasks.add(t.copy());
		}
		return m;
	}
}
