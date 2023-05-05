package com.outputdto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardPlayDTO {

	private String id;
	private String back;
	private String formatting;
	private String front;
	private List<String> fields;
	private List<String> fieldNames;

}
