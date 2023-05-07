package com.inputdto;

import static com.configuration.TemporaryStrings.CARD_TYPE_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchCardTypeDTO {
	
	@NotBlank(message = CARD_TYPE_NOT_BLANK)
	private String newName;
	private String back;
	private String front;
	private String format;

}
