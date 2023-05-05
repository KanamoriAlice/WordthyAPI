package com.model;

import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSettings {
	
	/*TODO: think of a better way to display showAnswerTime and
	 *		nextQuestionTime (general or review settings)*/
	
	private ReviewOrder reviewOrder;
	private int maxReviewsPerDay;
	private int intervalModifier; //In %
	private Period maxInterval;
	
	
	public ReviewSettings() {
		this(ReviewOrder.NEW_CARDS_AFTER_REVIEWS, 250, 191, Period.ofDays(365));
	}

	public ReviewSettings(ReviewOrder reviewOrder, int maxReviewsPerDay, int intervalModifier, boolean autoDisplayAnswer, int showAnswerTime,
			int nextQuestionTime, int maxInterval) {
		super();
		this.reviewOrder = reviewOrder;
		this.maxReviewsPerDay = maxReviewsPerDay;
		this.intervalModifier = intervalModifier;
		this.maxInterval = Period.ofDays(maxInterval);
		
	}
	
	

}
