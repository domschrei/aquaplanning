package edu.kit.aquaplanning.model.ground;

import java.util.HashMap;
import java.util.Map;

import edu.kit.aquaplanning.model.lifted.NumericExpression;

public class GroundFunctionTable {

	private Map<String, Float> functionValues;
	
	public GroundFunctionTable() {
		this.functionValues = new HashMap<>();
	}
	
	public void set(String function, float value) {
		functionValues.put(function, value);
	}
	
	public float get(String function) {
		if (functionValues.containsKey(function)) {			
			return functionValues.get(function);
		} else {
			return NumericExpression.UNDEFINED;
		}
	}
}
