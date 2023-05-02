package com.outputdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.annotation.ValidPassword;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	
	@NotBlank(message = "username cannot be empty nor null")
	@Size(min = 8, max = 21, message = "username must be between 8 and 21 characters")
	@Pattern(regexp = "[a-zA-Z1-9]*", message = "username can only consist of letters and numbers")
	private String username;
	
	@ValidPassword //custom annotation
	private String password;
	
	@NotBlank(message = "email cannot be empty nor null")
	@Email(message = "email should be valid")
	private String email;

}
