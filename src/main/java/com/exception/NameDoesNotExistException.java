package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Name doesn't exists")
public class NameDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NameDoesNotExistException(Class<?> classType, String name) {
		super("The " + classType.getName() + " named: " +
				name + " does not exist");
	}

}
