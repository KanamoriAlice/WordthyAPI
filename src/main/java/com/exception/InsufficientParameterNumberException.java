package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot have less than 2 parameters")
public class InsufficientParameterNumberException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InsufficientParameterNumberException() {
		super("Cannot have less than 2 parameters");
	}

}
