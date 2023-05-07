package com.exception;

public class DuplicatedCardTypeParametersException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicatedCardTypeParametersException() {
		super("Two or more parameters are duplicated");
	}
	
	

}
