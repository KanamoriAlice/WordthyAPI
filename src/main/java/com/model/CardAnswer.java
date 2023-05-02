package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardAnswer {
	
	AGAIN("again" , 0), GOOD("good", 1);
	
	private String gradeName;
	private int grade;

}
