package edu.kit.aquaplanning.util;

/**
 * A generic pair of objects of differing class type. Can be used to make a
 * method return a pair of objects in a type-safe way.
 */
public class Pair<A, B> {

	private A left;
	private B right;

	/**
	 * Creates a data pair out of the two provided objects.
	 */
	public Pair(A left, B right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Returns the left object in the pair.
	 */
	public A getLeft() {
		return left;
	}

	/**
	 * Returns the right object in the pair.
	 */
	public B getRight() {
		return right;
	}
}
