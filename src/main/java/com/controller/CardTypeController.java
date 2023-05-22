package com.controller;

import static com.configuration.TemporaryStrings.CARD_TYPE_NOT_BLANK;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.DuplicatedCardTypeParametersException;
import com.exception.InsufficientParameterNumberException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.exception.ParamaterAlreadyExistsException;
import com.inputdto.PostCardTypeDTO;
import com.inputdto.PatchCardTypeDTO;
import com.inputdto.PatchCardTypeParameterDTO;
import com.model.CardTag;
import com.model.CardType;
import com.outputdto.CardTypeReviewDTO;
import com.service.CardTypeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cardType")
@Validated
public class CardTypeController {

	@Autowired
	CardTypeService cardTypeService;

	@Operation(summary = "Add a parameter to card type by its name")
	@PatchMapping("/{name}/addParameter")
	@ResponseStatus(HttpStatus.OK)
	public void addParameter(@Valid @RequestBody PatchCardTypeParameterDTO dto) {
		String cardTypeName = dto.getCardTypeName();
		String parameterName = dto.getParameterName();
		if (cardTypeService.checkIfParameterNameExists(cardTypeName, parameterName))
			throw new ParamaterAlreadyExistsException();
		cardTypeService.addParameter(cardTypeName, parameterName);
	}

	@Operation(summary = "Delete a card type by its name")
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name) {
		if (name.equals("default"))
			throw new CannotDeleteDefaultException(CardType.class);
		if (!cardTypeService.checkIfNameExists(name))
			throw new NameDoesNotExistException(CardType.class, name);
		cardTypeService.delete(name);
	}

	@Operation(summary = "Get all card type reviews")
	@GetMapping("/allReviews")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CardTypeReviewDTO> getAllCardTypeReviews() {
		return cardTypeService.getAllCardTypeReview();
	}

	@Operation(summary = "Get a card type review by its name")
	@GetMapping("/{name}/review")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CardTypeReviewDTO getCardTypeReview(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name) {
		return cardTypeService.getCardTypeReview(name);
	}

	@Operation(summary = "Get all fields of a card type by its name")
	@GetMapping("/{name}/fields")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getFields(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name) {
		return cardTypeService.getFields(name);
	}

	@Operation(summary = "Get all card type names")
	@GetMapping("/allNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllNames() {
		return cardTypeService.getAllNames();
	}

	@Operation(summary = "Update a card type by its name")
	@PatchMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patch(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name,
			@Valid @RequestBody PatchCardTypeDTO dto) {
		if (name.equals("default") && !name.equals(dto.getNewName()))
			throw new CannotRenameDefaultException(CardType.class);
		if (!name.equals(dto.getNewName()) && cardTypeService.checkIfNameExists(dto.getNewName()))
			throw new NameAlreadyExistsException(CardType.class, name);
		cardTypeService.patch(name, dto);
	}

	@Operation(summary = "Create a new card type")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(@Valid @RequestBody PostCardTypeDTO dto) {
		String newName = dto.getName();
		if (cardTypeService.checkIfNameExists(newName))
			throw new NameAlreadyExistsException(CardTag.class, newName);
		Set<String> fields = new HashSet<>();
		for (String field : dto.getFields()) {
			if (!fields.contains(field))
				fields.add(field);
			else
				throw new DuplicatedCardTypeParametersException();
		}
		cardTypeService.post(dto);
	}

	@Operation(summary = "Delete parameter to card type by its name")
	@PatchMapping("/{name}/removeParameter")
	@ResponseStatus(HttpStatus.OK)
	public void deleteParameter(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name,
			@Valid @RequestBody PatchCardTypeParameterDTO dto) {
		String cardTypeName = dto.getCardTypeName();
		if (cardTypeService.getParameterCount(cardTypeName) <= 2) // Should never be less than 2
			throw new InsufficientParameterNumberException();
		String parameterName = dto.getParameterName();
		cardTypeService.deleteParameter(cardTypeName, parameterName);
	}

	@Operation(summary = "Rename parameter of a card type by its name")
	@PatchMapping("/{name}/renameParameter")
	@ResponseStatus(HttpStatus.OK)
	public void renameParameter(
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @PathVariable String name,
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @RequestParam String parameterName,
			@NotBlank(message = CARD_TYPE_NOT_BLANK) @RequestParam String newParameterName) {
		cardTypeService.renameParameter(name,
				parameterName, newParameterName);
	}

	// Retiring methods

	// @GetMapping("/{name}/format")
	// @ResponseStatus(HttpStatus.OK)
	// @ResponseBody
	// public CardTypeFormatDTO getFormat(@RequestParam String name) {
	// return cardTypeService.getFormat(name);
	// }

	// @PatchMapping("/{name}/rename")
	// @ResponseStatus(HttpStatus.OK)
	// public void rename(
	// @NotBlank(message = CARD_TYPE_NOT_BLANK)
	// @PathVariable String name,
	// @NotBlank(message = "New " + CARD_TYPE_NOT_BLANK)
	// @Valid @RequestParam String newName) {
	// if (name.equals("default"))
	// throw new CannotRenameDefaultException(CardType.class);
	// if (cardTypeService.checkIfNameExists(name))
	// throw new NameAlreadyExistsException(CardType.class, name);
	// cardTypeService.rename(name, newName);
	// }

	// @PatchMapping("/{name}/updateFormat")
	// @ResponseStatus(HttpStatus.OK)
	// public void updateFormat(
	// @NotBlank(message = CARD_TYPE_NOT_BLANK)
	// @PathVariable String name,
	// @Valid @RequestBody CardTypeFormatDTO dto) {
	// cardTypeService.updateFormat(
	// name, dto.getBack(),
	// dto.getFront(), dto.getFormatting());
	// }
}
