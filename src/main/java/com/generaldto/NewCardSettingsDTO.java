package com.generaldto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.model.NewCardOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewCardSettingsDTO {
	
	@NotNull(message = "Order cannot be null")
	private NewCardOrder newCardOrder;
	@Positive(message = "The steps must be greater than 0")
	private int steps;
	@Positive(message = "The number should be greater than 0")
	private int graduatingInterval;
	@Positive(message = "The number should be greater than 0")
	private int maxNewCardsPerDay;
	@Positive(message = "The number should be greater than 0")
	private int startingEase;

}
