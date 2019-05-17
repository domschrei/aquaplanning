package edu.kit.aquaplanning.grounding.htn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kit.aquaplanning.Configuration;
import edu.kit.aquaplanning.grounding.PlanningGraphGrounder;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.htn.HtnPlanningProblem;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Type;
import edu.kit.aquaplanning.model.lifted.htn.Method;
import edu.kit.aquaplanning.model.lifted.htn.Task;

public class HtnPreprocessor {

	private Configuration config;
	
	private GroundPlanningProblem problem;
	private HtnPlanningProblem htnLiftedProblem;
	
	public HtnPreprocessor(Configuration config) {
		this.config = config;
	}
	
	public void preprocess(GroundPlanningProblem groundProblem, PlanningGraphGrounder grounder) {
		
		problem = groundProblem;
		htnLiftedProblem = (HtnPlanningProblem) grounder.getProblem();
		
		inferImplicitMethodArgTypes();
	}
	
	private void inferImplicitMethodArgTypes() {
		
		boolean change = true;
		while (change) {
			change = false;

			for (Method m : htnLiftedProblem.getMethods()) {

				Map<String, Integer> implicitArgs = new HashMap<>();
				for (int i = 0; i < m.getImplicitArguments().size(); i++) {
					Type type = m.getImplicitArguments().get(i).getType();
					if (type.equals(htnLiftedProblem.getSupertype())) {
						implicitArgs.put(m.getImplicitArguments().get(i).getName(), i);
					}
				}
				List<Type> types = htnLiftedProblem.getTasks().get(m.getName());
				if (types != null)
					for (int typeIdx = 0; typeIdx < types.size(); typeIdx++) {
						if (types.get(typeIdx).equals(htnLiftedProblem.getSupertype())) {
							Type argType = m.getTypeOfArgument(m.getExplicitArguments().get(typeIdx).getName());
							types.set(typeIdx, argType);
							if (types.get(typeIdx).equals(htnLiftedProblem.getSupertype())) {
								change = true;
							}
						}
					}

				for (Task task : m.getSubtasks()) {
					List<Type> taskTypes = htnLiftedProblem.getTasks().get(task.getName());
					for (int i = 0; i < task.getArguments().size(); i++) {
						Argument taskArg = task.getArguments().get(i);
						if (taskTypes.get(i).equals(htnLiftedProblem.getSupertype())
								&& !taskArg.getType().equals(htnLiftedProblem.getSupertype())) {
							// Type is still undefined in the task definition
							taskTypes.set(i, taskArg.getType());
							change = true;
						}
						if (!taskTypes.get(i).equals(htnLiftedProblem.getSupertype())
								&& implicitArgs.containsKey(taskArg.getName())) {
							m.getImplicitArguments().get(implicitArgs.get(taskArg.getName())).setType(taskTypes.get(i));
							taskArg.setType(taskTypes.get(i));
							task.getArguments().set(i, taskArg);
							change = true;
						}
						if (taskArg.getType().equals(htnLiftedProblem.getSupertype())) {
							if (m.getExplicitArguments().contains(taskArg)) {
								int argIdx = m.getExplicitArguments().indexOf(taskArg);
								taskArg.setType(m.getExplicitArguments().get(argIdx).getType());
								change |= !taskArg.getType().equals(htnLiftedProblem.getSupertype());
							}
							if (m.getImplicitArguments().contains(taskArg)) {
								int argIdx = m.getImplicitArguments().indexOf(taskArg);
								taskArg.setType(m.getImplicitArguments().get(argIdx).getType());
								change |= !taskArg.getType().equals(htnLiftedProblem.getSupertype());
							}
						}
					}
				}
			}
		}
	}
}
