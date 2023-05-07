package com.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("cardTypes")
public class CardType {
	
	@Id
	private String id;

	private String back;
	private String format;
	private String front;
	private String name;
	
	private List<String> fieldNames;

	//Constructor used to create a new record in the database
	public CardType(String back, String format, String front, String name, List<String> fieldNames) {
		this(null, back, format, front, name, fieldNames);
	}
	
	public CardType(String name, List<String> fields) {
		this("{{FrontSide}}" +
				fields.stream().filter(x -> !x.equals(fields.get(0))).reduce("",(a,b) -> (a + "<br>" + "{{" + b + "}}")),
				"font-size: x-large;", "{{" + fields.get(0) + "}}",
				name, fields);
	}

}
