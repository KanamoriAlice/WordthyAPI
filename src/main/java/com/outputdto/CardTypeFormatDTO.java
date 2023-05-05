package com.outputdto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardTypeFormatDTO {

	private String back;
	private String formatting;
	private String front;
	
}
