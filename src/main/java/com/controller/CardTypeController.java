package com.controller;

import static com.configuration.TemporaryStrings.CARD_TYPE_NOT_BLANK;

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

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.InsufficientParameterNumberException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.exception.ParamaterAlreadyExistsException;
import com.inputdto.PostCardTypeDTO;
import com.inputdto.AddRemoveCardTypeParameterDTO;
import com.inputdto.RenameCardTypeParameterDTO;
import com.model.CardTag;
import com.model.CardType;
import com.outputdto.CardTypeFormatDTO;
import com.service.CardTypeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cardType")
@Validated
public class CardTypeController {

	@Autowired
	CardTypeService cardTypeService;

	@PatchMapping("/{name}/addParameter")
	@ResponseStatus(HttpStatus.OK)
	public void addParameter(@Valid @RequestBody AddRemoveCardTypeParameterDTO dto) {
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
	        @NotBlank(message = CARD_TYPE_NOT_BLANK)
	        @Valid @PathVariable String name) {
	    if (name.equals("default"))
	        throw new CannotDeleteDefaultException(CardType.class);
	    if (!cardTypeService.checkIfNameExists(name))
	        throw new NameDoesNotExistException(CardType.class, name);
	    cardTypeService.delete(name);
	}

	@Operation(summary = "Get all fields of a card type by its name")
	@GetMapping("/{name}/fields")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getFields(
	        @NotBlank(message = CARD_TYPE_NOT_BLANK)
	        @Valid @PathVariable String name) {
	    return cardTypeService.getFields(name);
	}
	
	@GetMapping("/getFormat")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CardTypeFormatDTO getFormat(@RequestParam String name) {
		return cardTypeService.getFormat(name);
	}

	@Operation(summary = "Get all card type names")
	@GetMapping("/allNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllNames() {
	    return cardTypeService.getAllNames();
	}

	@Operation(summary = "Create a new card type")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(@Valid @RequestBody PostCardTypeDTO dto) {
	    String newName = dto.getName();
	    if (cardTypeService.checkIfNameExists(newName))
	        throw new NameAlreadyExistsException(CardTag.class, newName);
	    cardTypeService.post(dto);
	}

	@PatchMapping("/{name}/removeParameter")
	@ResponseStatus(HttpStatus.OK)
	public void removeParameter(
			@NotBlank(message = CARD_TYPE_NOT_BLANK)
			@PathVariable String name,
			@Valid @RequestBody AddRemoveCardTypeParameterDTO dto) {
		String cardTypeName = dto.getCardTypeName();
		if (cardTypeService.getParameterCount(cardTypeName) <= 2) // Should never be less than 2
			throw new InsufficientParameterNumberException();
		String parameterName = dto.getParameterName();
		cardTypeService.removeParameter(cardTypeName, parameterName);
	}

	@PatchMapping("/{name}/rename")
	@ResponseStatus(HttpStatus.OK)
	public void rename(
			@NotBlank(message = CARD_TYPE_NOT_BLANK)
			@PathVariable String name,
			@NotBlank(message = "New " + CARD_TYPE_NOT_BLANK)
			@Valid @RequestParam String newName) {
		if (name.equals("default"))
			throw new CannotRenameDefaultException(CardType.class);
		if (cardTypeService.checkIfNameExists(name))
			throw new NameAlreadyExistsException(CardType.class, name);
		cardTypeService.rename(name, newName);
	}

	@PatchMapping("/{name}/renameParameter")
	@ResponseStatus(HttpStatus.OK)
	public void renameParameter(
			@NotBlank(message = CARD_TYPE_NOT_BLANK)
			@PathVariable String name,
			@Valid @RequestBody RenameCardTypeParameterDTO dto) {
		cardTypeService.renameParameter(name,
			dto.getParameterName(), dto.getNewParameterName());
	}
	
	@PatchMapping("/{name}/updateFormat")
	@ResponseStatus(HttpStatus.OK)
	public void updateFormat(
			@NotBlank(message = CARD_TYPE_NOT_BLANK)
			@PathVariable String name, 
			@Valid @RequestBody CardTypeFormatDTO dto) {
		cardTypeService.updateFormat(
			name, dto.getBack(),
			dto.getFront(), dto.getFormatting());
	}
}
