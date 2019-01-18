package edu.kit.aquaplanning.optimization;

public class Clock {

	private long startTime;
	private long duration;
	
	public Clock(long durationMillis) {
		this.duration = durationMillis;
		this.startTime = System.currentTimeMillis();
	}
	
	public boolean hasTimeLeft() {
		
		if (duration <= 0) {
			return true;
		}
		long timeNow = System.currentTimeMillis();
		return startTime + duration >= timeNow;
	}
}
