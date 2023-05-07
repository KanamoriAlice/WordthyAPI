package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exception.DuplicatedCardTypeParametersException;
import com.exception.NameAlreadyExistsException;
import com.exception.ParameterNameAlreadyExists;
import com.outputdto.ErrorDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<ErrorDTO>> handleConstraintExceptions(
			ConstraintViolationException exception) {
		List<ErrorDTO> errors = new ArrayList<>();
		exception.getConstraintViolations().forEach(error ->
			errors.add(new ErrorDTO(
					error.getPropertyPath().toString().split("\\.")[1],
					error.getMessage()))
		);
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NameAlreadyExistsException.class)
	public ResponseEntity<ErrorDTO> handleNameAlreadyExistsException() {
		return ResponseEntity.internalServerError().body(new ErrorDTO("name", "Name already exists"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorDTO>> handleValidationExceptions(
			 MethodArgumentNotValidException exception) {
		List<ErrorDTO> errors = new ArrayList<>();
		exception.getBindingResult().getAllErrors().forEach(
				error -> 
					errors.add(new ErrorDTO(((FieldError) error).getField(),
							error.getDefaultMessage()))
				);
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DuplicatedCardTypeParametersException.class)
	public ResponseEntity<ErrorDTO> handleCardTypeParameterIsBlankException(
			DuplicatedCardTypeParametersException exception) {
		return ResponseEntity.internalServerError().body(
				new ErrorDTO("fields", exception.getMessage()));
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ParameterNameAlreadyExists.class)
	public ResponseEntity<ErrorDTO> handleParameterNameAlreadyExists(
			ParameterNameAlreadyExists exception) {
		return ResponseEntity.internalServerError().body(
				new ErrorDTO("fields", exception.getMessage()));
	}
	
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleValidationExceptions(
//			 MethodArgumentNotValidException exception) {
//		Map<String, String> errors = new HashMap<>();
//		exception.getBindingResult().getAllErrors().forEach(
//				error -> {
//					String fieldName = ((FieldError) error).getField();
//					String errorMessage = error.getDefaultMessage();
//					errors.put(fieldName, errorMessage);
//				});
//		return errors;
//	}
	
	
	
}
