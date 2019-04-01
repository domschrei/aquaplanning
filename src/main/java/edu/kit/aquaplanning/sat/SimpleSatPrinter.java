package edu.kit.aquaplanning.sat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.sat4j.core.VecInt;
import org.sat4j.specs.IVecInt;

public class SimpleSatPrinter {

	private String filename;
	
	private String formula;
	private StringBuilder newClauses;
	int numVars = 0;
	int numClauses = 0;
	
	int counter = 1;
	
	public SimpleSatPrinter(String filename) {
		this.filename = filename;
		this.formula = "";
		this.newClauses = new StringBuilder();
	}
	
	public void addClause(IVecInt literals) {
		String out = "";
		for (int i = 0; i < literals.size(); i++) {
			out += literals.get(i) + " ";
			numVars = Math.max(numVars, Math.abs(literals.get(i)));
		}
		newClauses.append(out + "0\n");
		numClauses++;
	}
	
	public void addAssumptionsAndPrint(IVecInt assumps) {
		try {
			String name = filename;
			if (filename.contains(".")) {
				name = name.substring(0, filename.lastIndexOf('.')) + "-" + counter + name.substring(filename.lastIndexOf('.'));
			} else {
				name += "-" + counter;
			}
			FileWriter w = new FileWriter(new File(name));
			w.write("p cnf " + numVars + " " + (numClauses + assumps.size()) + "\n");
			formula += newClauses;
			newClauses = new StringBuilder();
			System.out.println("Writing...");
			w.write(formula.toString());
			for (int i = 0; i < assumps.size(); i++) {
				w.write(assumps.get(i) + " 0\n");
			}
			System.out.println("Writing complete.");
			w.close();
			counter++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void print() {
		addAssumptionsAndPrint(new VecInt());
	}
}
