package com.outputdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoteDataDTO {
	
	@NotBlank(message = "Title cannot be empty nor null")
	@Size(max = 256,
			message = "Title cannot be longer than 256 characters")
	private String title;
	private String content;

}
