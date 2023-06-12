package com.exception;

public class ParameterNameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParameterNameAlreadyExistsException() {
		super("Parameter name already exists");
	}

}
