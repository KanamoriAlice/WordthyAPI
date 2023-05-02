package com.outputdto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeckReviewDTO {
	
	private String name;
	private String parentDeck;
	private String deckSettings;
	private int lapsedCards;
	private int reviewCards;
	private int newCards;
	private int totalCards;

}
