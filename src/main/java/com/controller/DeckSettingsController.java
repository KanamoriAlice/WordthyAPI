package com.controller;

import static com.configuration.TemporaryStrings.DECK_SETTINGS_NOT_BLANK;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exception.CannotDeleteDefaultException;
import com.exception.CannotRenameDefaultException;
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.generaldto.GeneralSettingsDTO;
import com.generaldto.LapseSettingsDTO;
import com.generaldto.NewCardSettingsDTO;
import com.generaldto.ReviewSettingsDTO;
import com.inputdto.OnlyNameDTO;
import com.inputdto.RenameDTO;
import com.model.DeckSettings;
import com.service.DeckSettingsService;

@Controller
@RequestMapping("/deckSettings")
public class DeckSettingsController {
	
	@Autowired
	DeckSettingsService deckSettingsService;
	
	@GetMapping
	public String showView() {
		return "deckSettings";
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody OnlyNameDTO deckSettingsName) {
		String newName = deckSettingsName.getName();
		if (deckSettingsService.checkIfNameExists(newName))
			throw new NameAlreadyExistsException(DeckSettings.class, newName);
		deckSettingsService.create(newName);
	}
	
	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@Valid @RequestBody OnlyNameDTO deckSettingsName) {
		String currentName = deckSettingsName.getName();
			if(currentName.equals("default"))
				throw new CannotDeleteDefaultException(DeckSettings.class);
			if (!deckSettingsService.checkIfNameExists(currentName))
				throw new NameDoesNotExistException(DeckSettings.class, currentName);
			deckSettingsService.delete(currentName);
	}
	
	@GetMapping("/allNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllDeckSettingsNames() {
		return deckSettingsService.getAllNames();
	}
	
	@GetMapping("/lapseSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LapseSettingsDTO getLapseSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name) {
		return deckSettingsService.getLapseSettings(name);
	}
	
	@GetMapping("/generalSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GeneralSettingsDTO getGeneralSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name) {
		return deckSettingsService.getGeneralSettings(name);
	}
	
	@GetMapping("/newCardSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NewCardSettingsDTO getNewCardSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name) {
		return deckSettingsService.getNewCardSettings(name);
	}
	
	@GetMapping("/reviewSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ReviewSettingsDTO getReviewSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name) {
		return deckSettingsService.getReviewSettings(name);
	}
	
	@PatchMapping("/lapseSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patchLapseSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name,
			@Valid @RequestBody LapseSettingsDTO dto) {
		deckSettingsService.patchLapseSettings(name, dto);
	}
	
	@PatchMapping("/generalSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patchGeneralSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name,
			@Valid @RequestBody GeneralSettingsDTO dto) {
		deckSettingsService.patchGeneralSettings(name, dto);
	}
	
	@PatchMapping("/newCardSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patchNewCardSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name,
			@Valid @RequestBody NewCardSettingsDTO dto) {
		deckSettingsService.patchNewCardSettings(name, dto);
	}
	
	@PatchMapping("/reviewSettings/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patchGeneralSettings(
			@NotBlank(message = DECK_SETTINGS_NOT_BLANK)
			@Valid @PathVariable String name,
			@Valid @RequestBody ReviewSettingsDTO dto) {
		deckSettingsService.patchReviewSettings(name, dto);
	}
	
	@PatchMapping("/rename")
	@ResponseStatus(HttpStatus.OK)
	public void rename(@Valid @RequestBody RenameDTO names) {
		String currentName = names.getCurrentName();
		String newName = names.getNewName();
		if(currentName.equals("default"))
			throw new CannotRenameDefaultException(DeckSettings.class);
		if (!deckSettingsService.checkIfNameExists(currentName))
			throw new NameDoesNotExistException(DeckSettings.class, currentName);
		deckSettingsService.rename(currentName, newName);
	}

}
