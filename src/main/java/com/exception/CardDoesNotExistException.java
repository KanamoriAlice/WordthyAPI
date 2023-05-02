package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Card does not exist")
public class CardDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CardDoesNotExistException() {
		super("Card does not exist");
	}

}
