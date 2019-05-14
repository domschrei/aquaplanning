package edu.kit.aquaplanning.util;

/**
 * A generic triple of objects of differing class type. Can be used to make a
 * method return a triple of objects in a type-safe way.
 */
public class Triple<A, B, C> {

	private A left;
	private B mid;
	private C right;

	/**
	 * Creates a data triple out of the two provided objects.
	 */
	public Triple(A left, B mid, C right) {
		this.left = left;
		this.mid = mid;
		this.right = right;
	}

	/**
	 * Returns the left object in the triple.
	 */
	public A getLeft() {
		return left;
	}

	/**
	 * Returns the mid object in the pair.
	 */
	public B getMid() {
		return mid;
	}

	/**
	 * Returns the right object in the pair.
	 */
	public C getRight() {
		return right;
	}
}
