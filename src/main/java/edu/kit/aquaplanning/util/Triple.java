package edu.kit.aquaplanning.util;

public class Triple<A, B, C> {

	private A left;
	private B mid;
	private C right;
	
	public Triple(A left, B mid, C right) {
		this.left = left;
		this.mid = mid;
		this.right = right;
	}
	
	public A getLeft() {
		return left;
	}
	
	public B getMid() {
		return mid;
	}
	
	public C getRight() {
		return right;
	}
}
