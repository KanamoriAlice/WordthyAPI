package com.outputdto;

import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCardDTO {
	
	private String id;
	private String deck;
	private String cardType;
	private List<String> fields;
	private List<String> fieldNames;
	private Set<String> tags;

}
