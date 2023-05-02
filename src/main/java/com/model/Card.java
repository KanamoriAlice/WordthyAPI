package com.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("cards")
public class Card {
	
	@Id
	private String id;
	private String cardTypeId;
	private String deckId;
	private Set<String> cardTagsId;
	
	private CardScheduleSettings cardScheduleSettings;
	private CardStats cardStats;
	private CardStatus cardStatus;
	private List<String> fields;
	
	//Constructor used to create a new record in the database
	public Card(String cardTypeId, String deckId, int steps, List<String> fields) {
		this(null, cardTypeId, deckId, new HashSet<>(),
				new CardScheduleSettings(steps), new CardStats(),
				CardStatus.NEW, fields);
	}


}
