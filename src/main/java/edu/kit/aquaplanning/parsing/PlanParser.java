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
			actionMap.put(a.getName(), a);
		}
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			String actionName = line.split(":")[1].trim();
			plan.appendAtBack(actionMap.get(actionName));
		}
		reader.close();
		if (zf != null) {
			zf.close();
		}
		return plan;
	}

}
