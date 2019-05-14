package edu.kit.aquaplanning.grounding.datastructures;

import java.util.Arrays;
import java.util.List;

import edu.kit.aquaplanning.model.lifted.Argument;

/**
 * Represents a (partial or complete) assignment of arguments
 * to a list of parameters of fixed size. The decision level
 * of an instance indicates how many times some partial assignment
 * has been incorporated into this assignment.
 */
public class ArgumentAssignment {

	private Argument[] args;
	private int decisionLevel;
	private Integer hashCode = null;
	
	public ArgumentAssignment(Argument[] args) {
		this.args = args;
		decisionLevel = 0;
	}
	
	public ArgumentAssignment(int size) {
		this.args = new Argument[size];
		decisionLevel = 0;
	}
	
	public ArgumentAssignment(ArgumentAssignment other) {
		this.args = Arrays.copyOf(other.args, other.args.length);
		this.decisionLevel = other.decisionLevel+1;
	}
	
	public void set(int i, Argument a) {
		args[i] = a;
		hashCode = null;
	}
	
	public Argument get(int i) {
		return args[i];
	}
	
	public List<Argument> toList() {
		return Arrays.asList(args);
	}
	
	public int size() {
		return args.length;
	}
	
	public int getDecisionLevel() {
		return decisionLevel;
	}

	/**
	 * Attempts to merge this partial assignment with another assignment.
	 * If the two assignments are compatible, the merge is returned.
	 * Else, null is returned.
	 */
	public ArgumentAssignment mergeIfPossible(ArgumentAssignment a) {
		
		Argument[] assignment = this.args;
		Argument[] newAssignment = a.args;
		Argument[] match = new Argument[assignment.length];
		for (int argIdx = 0; argIdx < assignment.length; argIdx++) {
			if (assignment[argIdx] == null) {
				// match
				match[argIdx] = newAssignment[argIdx];
			} else if (newAssignment[argIdx] == null) {
				// match
				match[argIdx] = assignment[argIdx];
			} else if (assignment[argIdx].getName().equals(newAssignment[argIdx].getName())) {
				// match
				match[argIdx] = newAssignment[argIdx];
			} else {
				// no match
				match = null;
				break;
			}
		}
		
		if (match == null)
			return null;
		else
			return new ArgumentAssignment(match);
	}
	
	/**
	 * Only recomputes the hash code if something changed.
	 * Else, uses the precomputed value.
	 */
	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(args);
			hashCode = result;
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return args.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArgumentAssignment other = (ArgumentAssignment) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		return true;
	}
}
