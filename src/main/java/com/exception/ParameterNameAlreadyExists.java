package com.exception;

public class ParameterNameAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParameterNameAlreadyExists() {
		super("Parameter name already exists");
	}

}
