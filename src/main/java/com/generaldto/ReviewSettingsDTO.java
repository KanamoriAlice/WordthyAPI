package com.generaldto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.model.ReviewOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewSettingsDTO {
	
	/*TODO: think of a better way to display showAnswerTime and
	 *		nextQuestionTime (general or review settings)*/
	@NotNull(message = "Order cannot be null")
	private ReviewOrder reviewOrder;
	@Positive(message = "The number should be greater than 0")
	private int maxReviewsPerDay;
	@Positive(message = "The number should be greater than 0")
	private int intervalModifier; //In %
	@Positive(message = "The number should be greater than 0")
	private int maxInterval;
	
	
}