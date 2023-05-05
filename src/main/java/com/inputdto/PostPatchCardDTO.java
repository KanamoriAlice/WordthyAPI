package com.inputdto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostPatchCardDTO {
	
	@NotBlank(message = "Name cannot be empty nor null")
	private String cardTypeName;
	@NotBlank(message = "Name cannot be empty nor null")
	private String deckName;
	private List<String> fields;
	private Set<String> cardTags;
}
