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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.inputdto.OnlyNameDTO;
import com.inputdto.RenameDTO;
import com.model.CardTag;
import com.service.CardTagService;

@Controller
@RequestMapping("/cardTag")
public class CardTagController {
	
	@Autowired
	CardTagService cardTagService;
	
	@GetMapping
	public String getView() {
		return "cardTag";
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody OnlyNameDTO cardTagName) {
		String newName = cardTagName.getName();
		if (cardTagService.checkIfNameExists(newName))
			throw new NameAlreadyExistsException(CardTag.class, newName);
		cardTagService.create(newName);
	}
	
	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@Valid @RequestBody OnlyNameDTO name) {
		String currentName = name.getName();
		if(currentName.equals("default"))
			throw new CannotDeleteDefaultException(CardTag.class);
		if (!cardTagService.checkIfNameExists(currentName))
			throw new NameDoesNotExistException(CardTag.class, currentName);
		cardTagService.delete(currentName);
	}
	
	@GetMapping("/getAllNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllNames() {
		return cardTagService.getAllNames();
	}
	
	@PatchMapping("/rename")
	@ResponseStatus(HttpStatus.OK)
	public void rename(@Valid @RequestBody RenameDTO names) {
		String currentName = names.getCurrentName();
		String newName = names.getNewName();
		if(currentName.equals("default"))
			throw new CannotRenameDefaultException(CardTag.class);
		if (cardTagService.checkIfNameExists(currentName))
			throw new NameAlreadyExistsException(CardTag.class, currentName);
		cardTagService.rename(currentName, newName);
	}

}
