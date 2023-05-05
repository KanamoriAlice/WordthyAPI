package com.controller;

import static com.configuration.TemporaryStrings.CARD_ID_NOT_BLANK;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.inputdto.PostPatchCardDTO;
import com.model.CardAnswer;
import com.model.CardStatus;
import com.outputdto.GetCardDTO;
import com.service.CardService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {

	@Autowired
	CardService cardService;

	@Operation(summary = "Delete a card by its id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@NotBlank(message = CARD_ID_NOT_BLANK)
			@PathVariable String id) {
		cardService.delete(id);
	}
	
	@Operation(summary = "Get all cards in pages")
	@GetMapping("/all")
    public Page<GetCardDTO> getAllCardsByPages(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
		return cardService.getAllCardsByPages(pageNumber, pageSize);
    }
	
	@Operation(summary = "Update a card by its id")
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void patch(
			@NotBlank(message = CARD_ID_NOT_BLANK)
			@PathVariable String id,
			@Valid @RequestBody PostPatchCardDTO dto) {
		cardService.patch(id, dto);
	}
	
	@Operation(summary = "Add a new card")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(@Valid @RequestBody PostPatchCardDTO dto) {
		cardService.post(dto);
	}
	
	@Operation(summary = "Schedule a card")
	@PatchMapping("/schedule")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CardStatus schedule(
			@NotBlank(message = CARD_ID_NOT_BLANK)
			@PathVariable String id,
			@RequestParam CardAnswer answer) {
		return cardService.schedule(id, answer);
	}
	
	//Retiring methods
	
//	@PatchMapping("/addTag")
//	@ResponseStatus(HttpStatus.OK)
//	public void addTag(@Valid @RequestBody AddRemoveTagDTO dto) {
//		cardService.addTag(dto.getCardId(), dto.getTagName());
//	}
	
//	@PatchMapping("/removeTag")
//	@ResponseStatus(HttpStatus.OK)
//	public void removeTag(@Valid @RequestBody AddRemoveTagDTO dto) {
//		cardService.removeTag(dto.getCardId(), dto.getTagName());
//	}

//	@PatchMapping("/updateFields")
//	@ResponseStatus(HttpStatus.OK)
//	public void updateFields(@Valid @RequestBody CardUpdateDTO cardUpdateDTO) {
//		cardService.updateFields(cardUpdateDTO);
//	}
}
