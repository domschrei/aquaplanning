package edu.kit.aquaplanning.model.ground.htn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import edu.kit.aquaplanning.model.ground.Precondition;
import edu.kit.aquaplanning.model.ground.Precondition.PreconditionType;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition;
import edu.kit.aquaplanning.model.lifted.condition.AbstractCondition.ConditionType;
import edu.kit.aquaplanning.model.lifted.condition.Condition;
import edu.kit.aquaplanning.model.lifted.condition.ConditionSet;
import edu.kit.aquaplanning.model.lifted.condition.Negation;
import edu.kit.aquaplanning.model.lifted.htn.Constraint;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;
import edu.kit.aquaplanning.model.lifted.htn.Constraint.ConstraintType;

public class Reduction {

	private String name;
	private String[] subtasks;
	private Precondition[] constraints;
	
	private Method baseMethod;
	
	public Reduction(String name, int numChildren) {
		this.name = name;
		subtasks = new String[numChildren];
		constraints = new Precondition[numChildren+1];
	}
	
	public Reduction(Method groundMethod, Function<AbstractCondition, Precondition> conditionGrounder) {
		
		this.name = groundMethod.getName();
		this.baseMethod = groundMethod;
		int numChildren = groundMethod.getSubtasks().size();
		subtasks = new String[numChildren];
		constraints = new Precondition[numChildren+1];
		
		List<Constraint> openConstraints = new ArrayList<>();
		constraints[0] = new Precondition(PreconditionType.conjunction);
		for (int i = 0; i < numChildren; i++) {
			Task t = groundMethod.getSubtasks().get(i);
			subtasks[i] = t.toTaskString();
			String tag = t.getTag();
			constraints[i+1] = new Precondition(PreconditionType.conjunction);
			for (Constraint c : groundMethod.getConstraints()) {
				Precondition p = groundConstraint(c.getCondition(), conditionGrounder);
				if (c.getType() != ConstraintType.between && c.getSingleTag().equals(tag)) {
					boolean before = c.getType() == ConstraintType.before;
					constraints[before ? i : i+1].add(p);
				} else if (c.getType() == ConstraintType.between && c.getFirstTag().equals(tag)) {
					constraints[i].add(p);
					openConstraints.add(c);
				} 
			}
			
			for (int constrIdx = 0; constrIdx < openConstraints.size(); constrIdx++) {
				Constraint c = openConstraints.get(constrIdx);
				Precondition p = groundConstraint(c.getCondition(), conditionGrounder);
				constraints[i].add(p);
				if (c.getSecondTag().equals(tag)) {
					openConstraints.remove(constrIdx);
					constrIdx--;
				}
			}
		}
	}
	
	private Precondition groundConstraint(AbstractCondition c, 
			Function<AbstractCondition, Precondition> conditionGrounder) {
		
		Precondition pre = new Precondition(PreconditionType.conjunction);
		
		List<AbstractCondition> conditions = new ArrayList<>();
		conditions.add(c);
		for (int i = 0; i < conditions.size(); i++) {
			AbstractCondition cond = conditions.get(i);
			if (cond.getConditionType() == ConditionType.atomic) {
				
				Condition atomicCond = (Condition) cond;
				if (atomicCond.getPredicate().getName().equals("=")) {
					// Assume that an equality always holds 
					// (because only "useful" methods become reductions)
					// TODO actual check
					continue;
				}
				
				pre.add(conditionGrounder.apply(atomicCond));
				
			} else if (cond.getConditionType() == ConditionType.negation) {
				Condition negated = (Condition) ((Negation) cond).getChildCondition().copy();
				negated.setNegated(!negated.isNegated());
				conditions.add(negated);
						
			} else if (cond.getConditionType() == ConditionType.conjunction) {
				conditions.addAll(((ConditionSet) cond).getConditions());
			}
		}
		
		return pre;
	}
	
	public void addSubtask(int position, String task) {
		subtasks[position] = task;
	}
	
	public void addConstraint(int position, Precondition constraint) {
		constraints[position] = constraint;
	}
	
	public String getSubtask(int position) {
		return subtasks[position];
	}
	
	public int getNumSubtasks() {
		return subtasks.length;
	}
	
	public boolean hasConstraint(int position) {
		return constraints[position] != null;
	}
	
	public Precondition getConstraint(int position) {
		return constraints[position];
	}
	
	public Method getBaseMethod() {
		return baseMethod;
	}
	
	@Override
	public String toString() {
		String out = name + " : ";
		for (String subtask : subtasks) {
			out += subtask + " ";
		}
		return out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(subtasks);
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
		Reduction other = (Reduction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(subtasks, other.subtasks))
			return false;
		return true;
	}
}
