package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Deck has missing IDs")
public class IllegalDeckStateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IllegalDeckStateException() {
		super("Deck has missing IDs");
	}

}
