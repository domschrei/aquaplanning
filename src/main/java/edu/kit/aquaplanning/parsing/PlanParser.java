package edu.kit.aquaplanning.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.zip.ZipFile;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.OperatorPlan;
import edu.kit.aquaplanning.model.ground.ActionPlan;
import edu.kit.aquaplanning.model.lifted.Argument;
import edu.kit.aquaplanning.model.lifted.Operator;
import edu.kit.aquaplanning.model.lifted.PlanningProblem;
import edu.kit.aquaplanning.util.Logger;

public class PlanParser {

	public static OperatorPlan parsePlan(String planFile, PlanningProblem pp) throws IOException {
		
		Map<String, Operator> operatorsByName = new HashMap<>();
		for (Operator op : pp.getOperators()) {
			operatorsByName.put(op.getName(), op);
		}
		
		Collector<String, OperatorPlan, OperatorPlan> planBuilder = Collector.of(
			// Supplier: New partial plan
			() -> new OperatorPlan(),
			// Accumulator: Read a new line
			(partialPlan, line) -> {
				String[] words = line.split(":");
				if (words.length < 2) {
					Logger.log(Logger.ERROR, "Error: Plan file is syntactically invalid. (line \"" + line + "\")");
				}
				String actionName = words[1].trim();
				Operator operator = parseOperator(pp, operatorsByName, actionName);
				if (operator == null) {
					Logger.log(Logger.ERROR, "Could not parse action \"" + actionName + "\". (line \"" + line + "\")");
				}
				partialPlan.appendAtBack(operator);
			},
			// Combiner: Combine two partial plans (for parallelism)
			(partialPlan1, partialPlan2) -> (OperatorPlan) partialPlan1.appendAtBack(partialPlan2),
			// Finisher: Transform list of actions into final Plan object
			partialPlan -> partialPlan
		);
		
		return parse(planFile, planBuilder);
	}
	
	public static ActionPlan parsePlan(String planFile, GroundPlanningProblem gpp) throws IOException {
		
		HashMap<String, Action> actionMap = new HashMap<>();
		for (Action a : gpp.getActions()) {
			actionMap.put(a.getCleanedName(), a);
		}
		
		Collector<String, ActionPlan, ActionPlan> planBuilder = Collector.of(
			// Supplier: New partial plan
			() -> new ActionPlan(), 
			// Accumulator: Read a new line
			(partialPlan, line) -> {
				String[] words = line.split(":");
				if (words.length < 2) {
					Logger.log(Logger.ERROR, "Error: Plan file is syntactically invalid. (line \"" + line + "\")");
				}
				String actionName = words[1].trim();
				Action action = actionMap.get(actionName);
				if (action == null) {
					Logger.log(Logger.ERROR, "Could not parse action \"" + actionName + "\". (line \"" + line + "\")");
				}
				partialPlan.appendAtBack(action);
			}, 
			// Combiner: Combine two partial plans (for parallelism)
			(partialPlan1, partialPlan2) -> (ActionPlan) partialPlan1.appendAtBack(partialPlan2), 
			// Finisher: Transform list of actions into final Plan object
			partialPlan -> partialPlan
		);
		
		return parse(planFile, planBuilder);
	}
	
	private static <S, T> T parse(String planFile, Collector<String, S, T> lineHandler) throws IOException {
		
		BufferedReader reader = null;
		ZipFile zf = null;

		if (planFile.endsWith(".zip")) {
			zf = new ZipFile(planFile);
			InputStream is = zf.getInputStream(zf.entries().nextElement());
			reader = new BufferedReader(new InputStreamReader(is));
		} else {
			FileReader fr = new FileReader(planFile);
			reader = new BufferedReader(fr);
		}
		
		S partialPlan = lineHandler.supplier().get();
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			lineHandler.accumulator().accept(partialPlan, line);
		}
		reader.close();
		if (zf != null) {
			zf.close();
		}
		T plan = lineHandler.finisher().apply(partialPlan);
		
		return plan;
	}
	
	private static Operator parseOperator(PlanningProblem pp, Map<String, Operator> operatorsByName, String opString) {
		
		// Remove brackets
		opString = opString.substring(1, opString.length()-1);
		
		String[] words = opString.split(" ");
		String opName = words[0];
		
		Operator op = operatorsByName.get(opName);
		
		List<Argument> args = new ArrayList<>();
		for (int i = 1; i < words.length; i++) 
			args.add(new Argument(words[i], op.getArgumentTypes().get(i-1)));
		
		return op.getOperatorWithGroundArguments(args);
	}

}
