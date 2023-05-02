package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot delete 'default'")
public class CannotDeleteDefaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CannotDeleteDefaultException(Class<?> classType) {
		super("Cannot delete 'default' " + classType.getCanonicalName());
	}

}
