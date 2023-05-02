package com.model;

import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCardSettings {
	
	private NewCardOrder newCardOrder;
	private int steps;
	private Period graduatingInterval;
	private int maxNewCardsPerDay;
	private int startingEase;
	
	public NewCardSettings() {
		this(NewCardOrder.ADDED_ORDER, 2, Period.ofDays(1), 10, 130);
	}

	public NewCardSettings(NewCardOrder newCardOrder, int steps, int graduatingInterval, int maxNewCardsPerDay,
			int startingEase) {
		super();
		this.newCardOrder = newCardOrder;
		this.steps = steps;
		this.graduatingInterval = Period.ofDays(graduatingInterval);
		this.maxNewCardsPerDay = maxNewCardsPerDay;
		this.startingEase = startingEase;
	}
	
	

}
