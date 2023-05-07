package com.outputdto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardTypeReviewDTO {
	
	private String name;
	private String back;
	private String front;
	private String format;
	private List<String> fields;

}
