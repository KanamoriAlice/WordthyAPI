package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewOrder {
	
	NEW_CARDS_AFTER_REVIEWS("after"), NEW_CARDS_BEFORE_REVIEWS("before"),
	MIXED("mixed");

	private String order;
}
