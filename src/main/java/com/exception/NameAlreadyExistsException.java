package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Name already exists")
public class NameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NameAlreadyExistsException(Class<?> classType, String name) {
		super("Cannot name " + classType.getName() + " as: " + name +
				" because it already exists");
	}

}
