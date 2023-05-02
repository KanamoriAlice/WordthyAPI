package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewCardOrder {
	
	ADDED_ORDER("added"), RANDOM_ORDER("random");
	
	private String order;

}
