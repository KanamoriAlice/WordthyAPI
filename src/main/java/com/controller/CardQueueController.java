package com.controller;

import static com.configuration.TemporaryStrings.DECK_NOT_BLANK;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.outputdto.CardQueueDTO;
import com.outputdto.DeckReviewDTO;
import com.service.CardQueueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/cardQueue")
@Validated
public class CardQueueController {

	@Autowired
	CardQueueService cardQueueService;
	
	@Operation(summary = "Get a CardQueue by deck name")
	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CardQueueDTO get(
			@Parameter(description = "name of the deck of the CardQueue to be searched")
			@NotBlank(message = DECK_NOT_BLANK)
			@PathVariable String name) {
		return cardQueueService.getCardQueue(name);
	}
	
	@Operation(summary = "Get a DeckReview of all decks")
	@GetMapping("/allReviews")
	@ResponseStatus(HttpStatus.OK)
	public List<DeckReviewDTO> getAllDeckReviews() {
		return cardQueueService.getAllDeckReviews();
	}
	
	@Operation(summary = "Get a DeckReview by deck name")
	@GetMapping("/{name}/review")
	@ResponseStatus(HttpStatus.OK)
	public DeckReviewDTO getDeckReview(
			@Parameter(description = "name of the deck of the DeckReview to be searched")
			@NotBlank(message = DECK_NOT_BLANK)
			@PathVariable String name) {
		return cardQueueService.getDeckReview(name);
	}
	
}
