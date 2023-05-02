package com.inputdto;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//It is used to add or remove parameters from a card type
public class AddRemoveCardTypeParameterDTO {
	
	@NotBlank(message = "Name cannot be empty nor null")
	private String cardTypeName;
	@NotBlank(message = "Parameter name cannot be empty nor null")
	private String parameterName;

}
