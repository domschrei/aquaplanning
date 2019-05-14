package edu.kit.aquaplanning.util;

import java.util.ArrayList;
import java.util.List;

public class BinaryEncoding {

	private int numStates;
	private int bitLength;

	/**
	 * The most significant bit is at the highest index and the least significant
	 * bit is at index 0.
	 */
	private int[] encodedAtoms;

	/**
	 * Initializes a binary encoding inside the given domain, with values ranging
	 * from 0 inclusive to numStates exclusive. <br/>
	 * Side effect: A new AtomType is added to the domain. <br/>
	 * If numStates != 2^k - 1 for some k, then some values of the encoding must be
	 * explicitly forbidden by adding the clauses of the method
	 * getForbiddenValues().
	 * 
	 * @param d
	 * @param numStates
	 */
	public BinaryEncoding(int numStates, int startVariable) {

		this.numStates = numStates;

		init(startVariable);
	}

	private void init(int startVariable) {

		bitLength = log2(numStates - 1);

		encodedAtoms = new int[bitLength];
		for (int i = 0; i < bitLength; i++) {
			encodedAtoms[i] = startVariable + i;
		}
	}

	/**
	 * In the binary encoding, a value domain always ranges from 0 to 2^k - 1 for
	 * some k, even when the desired domain is actually 2^k - 1 - x for some
	 * positive x. <br/>
	 * This method creates a formula containing clauses that explicitly forbid these
	 * undesired values.
	 */
	public List<int[]> getForbiddenValues() {

		List<int[]> f = new ArrayList<>();

		int[] maxValidBits = posLiterals(numStates - 1);
		for (int i = 0; i < maxValidBits.length; i++) {
			if (maxValidBits[i] < 0) {
				int[] c = new int[maxValidBits.length - i];
				c[0] = maxValidBits[i];
				int cIdx = 1;
				for (int j = i + 1; j < maxValidBits.length; j++) {
					c[cIdx++] = -maxValidBits[j];
				}
				f.add(c);
			}
		}

		return f;
	}

	/**
	 * Returns a clause consisting of literals representing the given value in
	 * binary. <br/>
	 * Standalone semantics of this clause: at least one of the bits corresponding
	 * to the given value must be true.
	 */
	public List<int[]> posClause(int val) {

		return clause(val, true);
	}

	/**
	 * Returns a clause consisting of literals representing the given value in
	 * binary. <br/>
	 * Standalone semantics of this clause: The given value must not hold (i.e. at
	 * least one of the bits is different). Can be used on the left side of an
	 * implication: "If the value holds, then..."
	 */
	public List<int[]> negClause(int val) {

		return clause(val, false);
	}

	/**
	 * Returns literals representing the given value in binary. <br/>
	 * Standalone semantics of this clause: at least one of the bits corresponding
	 * to the given value must be true.
	 */
	public int[] posLiterals(int val) {

		return literals(val, true);
	}

	/**
	 * Returns literals representing the given value in binary. <br/>
	 * Standalone semantics of this clause: The given value must not hold (i.e. at
	 * least one of the bits is different). Can be used on the left side of an
	 * implication: "If the value holds, then..."
	 */
	public int[] negLiterals(int val) {

		return literals(val, false);
	}

	private List<int[]> clause(int val, boolean positive) {

		int[] literals = literals(val, positive);
		List<int[]> clauses = new ArrayList<>();
		clauses.add(literals);
		return clauses;
	}

	/**
	 * Calculates the corresponding literals to a given value that shall be encoded.
	 */
	private int[] literals(int val, boolean positive) {

		int[] lits = new int[bitLength];
		int remainder = val;
		for (int i = bitLength - 1; i >= 0; i--) {
			int power = (int) Math.pow(2, i);
			int atomSign;
			if (remainder >= power) {
				atomSign = 1;
				remainder -= power;
			} else {
				atomSign = -1;
			}
			lits[i] = (positive ? 1 : -1) * atomSign * encodedAtoms[i];
		}
		if (remainder != 0)
			System.out.println(val + ": Binary value calculation incorrect");
		return lits;
	}

	public int getAtomForBit(int bitPos) {

		return encodedAtoms[bitPos];
	}

	public int getBitLength() {

		return bitLength;
	}

	/**
	 * log2 method
	 */
	public static int log2(int n) {

		return (int) Math.floor(Math.log(n) / Math.log(2)) + 1;
	}
}
