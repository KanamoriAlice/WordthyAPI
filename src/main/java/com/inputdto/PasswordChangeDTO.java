package com.inputdto;

import com.annotation.ValidPassword;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordChangeDTO {
	
	private String oldPassword;
	@ValidPassword
	private String newPassword;
	
}
