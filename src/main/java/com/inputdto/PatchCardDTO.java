package com.inputdto;

import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchCardDTO {
	
	private String cardType;
	private String deck;
	private Set<String> tags;
	private List<String> fields;

}
