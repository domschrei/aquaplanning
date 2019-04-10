package edu.kit.aquaplanning.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.zip.ZipFile;

import edu.kit.aquaplanning.model.ground.Action;
import edu.kit.aquaplanning.model.ground.GroundPlanningProblem;
import edu.kit.aquaplanning.model.ground.Plan;
import edu.kit.aquaplanning.util.Logger;

public class PlanParser {
	
	public static Plan parsePlan(String planFile, GroundPlanningProblem gpp) throws IOException {
		Plan plan = new Plan();
		
		BufferedReader reader = null;
		ZipFile zf = null;
		
		if (planFile.endsWith(".zip")) {
			zf = new ZipFile(planFile);
			InputStream is =  zf.getInputStream(zf.entries().nextElement());
			reader = new BufferedReader(new InputStreamReader(is));
		} else {
			FileReader fr = new FileReader(planFile);
			reader = new BufferedReader(fr);
		}
		HashMap<String, Action> actionMap = new HashMap<>();
		for (Action a : gpp.getActions()) {
			actionMap.put(a.getCleanedName(), a);
		}
		int lineNo = 1;
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			String[] words = line.split(":");
			if (words.length < 2) {
				Logger.log(Logger.ERROR, "Error: Plan file is syntactically invalid. (line " + lineNo + ")");
				return null;
			}
			String actionName = words[1].trim();
			Action action = actionMap.get(actionName);
			if (action == null) {
				Logger.log(Logger.ERROR, "Could not parse action \"" + actionName + "\". (line " + lineNo + ")");
				return null;
			}
			plan.appendAtBack(action);
			lineNo++;
		}
		reader.close();
		if (zf != null) {
			zf.close();
		}
		return plan;
	}

}
