package com.model;

import java.time.LocalDate;
import java.time.Period;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardScheduleSettings {
	
	//In order to avoid making complex logic to add minutes, we'll just work with days (Periods)
	private Period interval;
	private int repetition;
	private int steps;
	private LocalDate nextReviewDay;
	
	public CardScheduleSettings(int steps) {
		this(Period.ofDays(1), 0, steps, LocalDate.now());
	}
	
	

}
