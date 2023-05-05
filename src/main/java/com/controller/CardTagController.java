package com.controller;

import static com.configuration.TemporaryStrings.CARD_TAG_NOT_BLANK;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.model.CardTag;
import com.service.CardTagService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cardTag")
@Validated
public class CardTagController {
	
	@Autowired
	CardTagService cardTagService;

	@Operation(summary = "Delete a card tag by its name")
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
	        @NotBlank(message = CARD_TAG_NOT_BLANK)
	        @PathVariable String name) {
	    if(name.equals("default"))
	        throw new CannotDeleteDefaultException(CardTag.class);
	    if (!cardTagService.checkIfNameExists(name))
	        throw new NameDoesNotExistException(CardTag.class, name);
	    cardTagService.delete(name);
	}

	@Operation(summary = "Get all card tags")
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAll() {
	    return cardTagService.getAll();
	}
	
	@Operation(summary = "Rename a card tag by its name")
	@PatchMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patch(
	        @NotBlank(message = CARD_TAG_NOT_BLANK)
	        @PathVariable String name,
	        @NotBlank(message = "New " + CARD_TAG_NOT_BLANK)
	        @RequestParam String newName) {
	    if(name.equals("default"))
	        throw new CannotRenameDefaultException(CardTag.class);
	    if (cardTagService.checkIfNameExists(newName))
	        throw new NameAlreadyExistsException(CardTag.class, name);
	    cardTagService.patch(name, newName);
	}

	
	@Operation(summary = "Create a new card tag")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(
	        @NotBlank(message = CARD_TAG_NOT_BLANK)
	        @RequestParam String name) {
	    if (cardTagService.checkIfNameExists(name))
	        throw new NameAlreadyExistsException(CardTag.class, name);
	    cardTagService.post(name);
	}
}
