package com.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("decks")
public class Deck {
	
	@Id
	private String id;
	private String deckSettingsId;
	private String parentDeckId;
	
	private String name;
	private int newCardsReviewed;
	private int learnedCardsReviewed;
	private LocalDate lastUpdated;

	//Constructor used to create a new record in the database
	public Deck(String deckSettingsId, String name) {
		this(null, deckSettingsId, null, name, 0, 0, LocalDate.now());
	}

}
