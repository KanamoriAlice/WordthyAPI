package com.inputdto;


import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RenameDTO {
	
	@NotBlank(message = "Current name must not be empty")
	private String currentName;
	@NotBlank(message = "New name must not be empty")
	private String newName;

}
