package com.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.InsufficientParameterNumberException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.exception.ParamaterAlreadyExistsException;
import com.inputdto.PostCardTypeDTO;
import com.inputdto.AddRemoveCardTypeParameterDTO;
import com.inputdto.OnlyNameDTO;
import com.inputdto.RenameCardTypeParameterDTO;
import com.inputdto.RenameDTO;
import com.model.CardTag;
import com.model.CardType;
import com.outputdto.CardTypeFormatDTO;
import com.service.CardTypeService;

@Controller
@RequestMapping("/cardType")
public class CardTypeController {

	@Autowired
	CardTypeService cardTypeService;

	@GetMapping
	public String getView() {
		return "cardType";
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody PostCardTypeDTO dto) {
		String newName = dto.getName();
		if (cardTypeService.checkIfNameExists(newName))
			throw new NameAlreadyExistsException(CardTag.class, newName);
		cardTypeService.create(dto);
	}

	@PatchMapping("/addParameter")
	@ResponseStatus(HttpStatus.OK)
	public void addParameter(@Valid @RequestBody AddRemoveCardTypeParameterDTO dto) {
		String cardTypeName = dto.getCardTypeName();
		String parameterName = dto.getParameterName();
		if (cardTypeService.checkIfParameterNameExists(cardTypeName, parameterName))
			throw new ParamaterAlreadyExistsException();
		cardTypeService.addParameter(cardTypeName, parameterName);
	}

	@GetMapping("/getFields")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getFields(@RequestParam String name) {
		return cardTypeService.getFields(name);
	}
	
	@GetMapping("/getFormat")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CardTypeFormatDTO getFormat(@RequestParam String name) {
		return cardTypeService.getFormat(name);
	}

	@GetMapping("/getAllNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllNames() {
		return cardTypeService.getAllNames();
	}

	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@Valid @RequestBody OnlyNameDTO name) {
		String currentName = name.getName();
		if (currentName.equals("default"))
			throw new CannotDeleteDefaultException(CardType.class);
		if (!cardTypeService.checkIfNameExists(currentName))
			throw new NameDoesNotExistException(CardType.class, currentName);
		cardTypeService.delete(currentName);
	}

	@PatchMapping("/removeParameter")
	@ResponseStatus(HttpStatus.OK)
	public void removeParameter(@Valid @RequestBody AddRemoveCardTypeParameterDTO dto) {
		String cardTypeName = dto.getCardTypeName();
		if (cardTypeService.getParameterCount(cardTypeName) <= 2) // Should never be less than 2
			throw new InsufficientParameterNumberException();
		String parameterName = dto.getParameterName();
		cardTypeService.removeParameter(cardTypeName, parameterName);
	}

	@PatchMapping("/rename")
	@ResponseStatus(HttpStatus.OK)
	public void rename(@Valid @RequestBody RenameDTO names) {
		String currentName = names.getCurrentName();
		String newName = names.getNewName();
		if (currentName.equals("default"))
			throw new CannotRenameDefaultException(CardType.class);
		if (cardTypeService.checkIfNameExists(currentName))
			throw new NameAlreadyExistsException(CardType.class, currentName);
		cardTypeService.rename(currentName, newName);
	}

	@PatchMapping("/renameParameter")
	@ResponseStatus(HttpStatus.OK)
	public void renameParameter(@Valid @RequestBody RenameCardTypeParameterDTO dto) {
		cardTypeService.renameParameter(dto.getCardTypeName(),
			dto.getParameterName(), dto.getNewParameterName());
	}
	
	@PatchMapping("/updateFormat")
	@ResponseStatus(HttpStatus.OK)
	public void updateFormat(@Valid @RequestBody CardTypeFormatDTO dto) {
		cardTypeService.updateFormat(
			dto.getName(), dto.getBack(),
			dto.getFront(), dto.getFormatting());
	}
}
