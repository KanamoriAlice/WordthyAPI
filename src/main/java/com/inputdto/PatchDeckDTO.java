package com.inputdto;


import static com.configuration.TemporaryStrings.DECK_NOT_BLANK;
import static com.configuration.TemporaryStrings.DECK_SETTINGS_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchDeckDTO {
	
	@NotBlank(message = DECK_NOT_BLANK)
	private String newName;
	@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	private String deckSettings;
	@NotNull(message = "Parent deck can't be null")
	private String parentDeck;

}
