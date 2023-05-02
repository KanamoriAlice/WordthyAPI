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
@Document("deckSettings")
public class DeckSettings {
	
	
	@Id
	private String id;

	private String name;
	private GeneralSettings generalSettings;
	private LapseSettings lapseSettings;
	private NewCardSettings newCardSettings;
	private ReviewSettings reviewSettings;
	
	//Constructor used to create a new record in the database
	public DeckSettings(String name) {
		this(null, name, new GeneralSettings(), new LapseSettings(),
				new NewCardSettings(), new ReviewSettings());
	}
	
}
