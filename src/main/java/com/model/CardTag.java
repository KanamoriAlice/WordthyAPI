package com.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("cardTags")
public class CardTag {
	
	@Id
	private String id;
	
	private String name;

	//Constructor used to create a new record in the database
	public CardTag(String name) {
		this(null, name);
	}

}
