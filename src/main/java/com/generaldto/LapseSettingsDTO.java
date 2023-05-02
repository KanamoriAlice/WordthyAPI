package com.generaldto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LapseSettingsDTO {
	
	@Positive(message = "The steps must be greater than 0")
	private int steps;
	@Positive(message = "New interval must be greater than 0")
	private int newInterval; //In %
	@Positive(message = "Minimal interval must be greater than 0")
	private int minInterval;
	@Min(value = 2, message = "Threshold must be greater than 1")
	private int leechThreshold;

}
