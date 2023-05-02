package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardStatus {
	NEW("new"), LEARNED("learned"),
	LAPSED("lapsed"), SUSPENDED("suspended");
	
	private String status;

}
