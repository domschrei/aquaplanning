package edu.kit.aquaplanning.model.ground;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Plan<A> implements Iterable<A> {

	protected List<A> sequence;
	
	public Plan() {
		this.sequence = new LinkedList<>();
	}
	
	public Plan(List<A> sequence) {
		this.sequence = sequence;
	}
	
	public void appendAtFront(A a) {
		sequence.add(0, a);
	}
	
	public void appendAtBack(A a) {
		sequence.add(a);
	}
	
	public Plan<A> appendAtBack(Plan<A> other) {
		this.sequence.addAll(other.sequence);
		return this;
	}
	
	public A remove(int index) {
		return sequence.remove(index);
	}
	
	public A get(int index) {
		return sequence.get(index);
	}
	
	public int getLength() {
		return sequence.size();
	}
	
	public abstract int getCost();
	public abstract String toString();
	public abstract Plan<A> copy();
	
	@Override
	public Iterator<A> iterator() {
		return sequence.iterator();
	}
}
