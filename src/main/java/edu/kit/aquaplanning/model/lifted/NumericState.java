package edu.kit.aquaplanning.model.lifted;

import java.util.HashMap;
import java.util.Map;

public class NumericState {
	
	private Map<Function, Float> functionValues;
	
	public NumericState() {
		this.functionValues = new HashMap<>();
	}
	
	public void set(Function function, float value) {
		
		functionValues.put(function, value);
	}
	
	public float get(Function function) {
		
		if (functionValues.containsKey(function)) {
			return functionValues.get(function);
		} else {
			return NumericExpression.UNDEFINED;
		}
	}
}
