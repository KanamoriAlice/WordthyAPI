package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot rename default")
public class CannotRenameDefaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CannotRenameDefaultException(Class<?> classType) {
		super("Cannot rename 'Default' " + classType.getCanonicalName());
	}


}
