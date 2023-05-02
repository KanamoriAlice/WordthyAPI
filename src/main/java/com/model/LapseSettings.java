package com.model;

import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LapseSettings {
	
	//TODO: handle leech action
	private int steps;
	private int newInterval; //In %
	private Period minInterval;
	private int leechThreshold;
	
	public LapseSettings() {
		this(2, 50, Period.ofDays(1), 6);
	}

	public LapseSettings(int steps, int newInterval, int minInterval, int leechThreshold) {
		super();
		this.steps = steps;
		this.newInterval = newInterval;
		this.minInterval = Period.ofDays(minInterval);
		this.leechThreshold = leechThreshold;
	}
	
	

}
