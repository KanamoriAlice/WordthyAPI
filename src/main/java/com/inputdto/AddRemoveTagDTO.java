package com.inputdto;


import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//It is used to add or remove tags from a card
public class AddRemoveTagDTO {
	
	private String cardId;
	@NotBlank(message = "Tag name cannot be empty nor null")
	private String tagName;
	

}
