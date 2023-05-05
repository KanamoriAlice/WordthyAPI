package com.inputdto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCardDTO {
	
	@NotBlank(message = "Name cannot be empty nor null")
	private String cardTypeName;
	@NotBlank(message = "Name cannot be empty nor null")
	private String deckName;
	private List<String> fields;

}
