package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Card has missing IDs")
public class IllegalCardStateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IllegalCardStateException() {
		super("Card has missing IDs");
	}

}
