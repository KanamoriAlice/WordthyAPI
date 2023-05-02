package com.model;

import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSettings {
	
	/*TODO: think of a better way to display showAnswerTime and
	 *		nextQuestionTime (general or review settings)*/
	
	private int maxReviewsPerDay;
	private int intervalModifier; //In %
	private boolean autoDisplayAnswer;
	//Time until the answer is shown
	private int showAnswerTime;
	//Time until next question is shown
	private int nextQuestionTime;
	private Period maxInterval;
	private ReviewOrder reviewOrder;
	
	public ReviewSettings() {
		this(250, 191, false, 60, 60, Period.ofDays(365),
				ReviewOrder.NEW_CARDS_AFTER_REVIEWS);
	}

	public ReviewSettings(int maxReviewsPerDay, int intervalModifier, boolean autoDisplayAnswer, int showAnswerTime,
			int nextQuestionTime, int maxInterval, ReviewOrder reviewOrder) {
		super();
		this.maxReviewsPerDay = maxReviewsPerDay;
		this.intervalModifier = intervalModifier;
		this.autoDisplayAnswer = autoDisplayAnswer;
		this.showAnswerTime = showAnswerTime;
		this.nextQuestionTime = nextQuestionTime;
		this.maxInterval = Period.ofDays(maxInterval);
		this.reviewOrder = reviewOrder;
	}
	
	

}
