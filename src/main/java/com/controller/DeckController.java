package com.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.configuration.TemporaryStrings.DECK_NOT_BLANK;

import com.exception.CannotDeleteDefaultException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.inputdto.PatchDeckDTO;
import com.model.Deck;
import com.outputdto.CardPlayDTO;
import com.outputdto.GetDeckDTO;
import com.service.DeckService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/deck")
@Validated
public class DeckController {
	
	@Autowired
	private DeckService deckService;
	
	@Operation(summary = "Delete a deck by its name")
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
	        @NotBlank(message = DECK_NOT_BLANK)
	        @Valid @PathVariable String name) {
	    if(name.equals("default"))
	        throw new CannotDeleteDefaultException(Deck.class);
	    if (!deckService.checkIfNameExists(name))
	        throw new NameDoesNotExistException(Deck.class, name);
	    deckService.delete(name);
	}

	@Operation(summary = "Get all deck names")
	@GetMapping("/allNames")
	@ResponseStatus(HttpStatus.OK)
	public List<GetDeckDTO> getAllNames() {
	    return deckService.getAllNames();
	}

	@Operation(summary = "Get all cards by deck name")
	@GetMapping("/{name}/allCards")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CardPlayDTO> getAllCards(
	        @NotBlank(message = DECK_NOT_BLANK)
	        @Valid @RequestParam("deckName") String name) {
	    return deckService.getByDeckName(name);
	}

	@Operation(summary = "Update a deck by its name")
	@PatchMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patch(
	        @NotBlank(message = DECK_NOT_BLANK)
	        @PathVariable String name,
	        @Valid @RequestBody PatchDeckDTO dto) {
	    if(!name.equals(dto.getNewName()) && deckService.checkIfNameExists(dto.getNewName()))
	        throw new NameAlreadyExistsException(Deck.class, name);
	    deckService.patch(name, dto);
	}

	@Operation(summary = "Add a new deck")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(
	        @NotBlank(message = DECK_NOT_BLANK)
	        @RequestParam(name = "name") String name) {
	    if(deckService.checkIfNameExists(name))
	        throw new NameAlreadyExistsException(Deck.class, name);
	    deckService.post(name);
	}
	
	//Retiring methods
	
//	@PatchMapping("/incrementNewCardsReviewed/{name}")
//	@ResponseStatus(HttpStatus.OK)
//	public void incrementNewCardsReviewed(
//			@NotBlank(message = DECK_NOT_BLANK)
//			@PathVariable String name) {
//		deckService.incrementNewCardsReviewed(name);
//	}
//	
//	@PatchMapping("/incrementReviewCardsReviewed/{name}")
//	@ResponseStatus(HttpStatus.OK)
//	public void incrementReviewCardsReviewed(
//			@NotBlank(message = DECK_NOT_BLANK)
//			@PathVariable String name) {
//		deckService.incrementReviewCardsReviewed(name);
//	}
	
//	@PatchMapping("/rename")
//	@ResponseStatus(HttpStatus.OK)
//	public void rename(@Valid @RequestBody RenameDTO names) {
//		String currentName = names.getCurrentName();
//		String newName = names.getNewName();
//		if(currentName.equals("default"))
//			throw new CannotRenameDefaultException(Deck.class);
//		if (deckService.checkIfNameExists(newName))
//			throw new NameAlreadyExistsException(Deck.class, currentName);
//		deckService.rename(currentName, newName);
//	}
	
//	@PatchMapping("/setDeckSettings")
//	@ResponseStatus(HttpStatus.OK)
//	public void setDeckSettings(@Valid @RequestBody GetDeckDTO deckDTO) {
//		deckService.setDeckSettings(deckDTO.getName(), deckDTO.getDeckSettingsName());
//	}
	
//	@PatchMapping("/setParentDeck")
//	@ResponseStatus(HttpStatus.OK)
//	public void setParentDeck(@Valid @RequestBody RenameDTO dto) {
//		deckService.setParentDeck(dto.getCurrentName(), dto.getNewName());
//	}
	
//	@PatchMapping("/resetAllDeckCounters")
//	@ResponseStatus(HttpStatus.OK)
//	public void resetAllDeckCounters() {
//		deckService.resetAllDeckCounters();
//	}

}
