package com.model;

import java.time.LocalDateTime;
import java.time.Period;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardReview {
	
	private CardAnswer answer;
	private CardStatus cardStatus;
	private LocalDateTime reviewTime;
	private Period interval;

}
