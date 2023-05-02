package com.inputdto;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RenameCardTypeParameterDTO {

	@NotBlank(message = "name cannot be empty nor null")
	private String cardTypeName;
	@NotBlank(message = "Parameter name cannot be empty nor null")
	private String parameterName;
	@NotBlank(message = "New parameter name cannot be empty nor null")
	private String newParameterName;
	
}
