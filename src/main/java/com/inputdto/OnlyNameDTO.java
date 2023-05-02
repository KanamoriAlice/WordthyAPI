package com.inputdto;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//Used to create, delete and get resources by name
public class OnlyNameDTO {
	
	@NotBlank(message = "Name cannot be empty nor null")
	private String name;

}
