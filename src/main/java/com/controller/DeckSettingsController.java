package com.controller;

import static com.configuration.TemporaryStrings.DECK_SETTINGS_NOT_BLANK;

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
import com.exception.NameAlreadyExistsException;
import com.exception.NameDoesNotExistException;
import com.generaldto.GeneralSettingsDTO;
import com.generaldto.LapseSettingsDTO;
import com.generaldto.NewCardSettingsDTO;
import com.generaldto.ReviewSettingsDTO;
import com.model.DeckSettings;
import com.service.DeckSettingsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/deckSettings")
@Validated
public class DeckSettingsController {
	
	@Autowired
	DeckSettingsService deckSettingsService;

	@Operation(summary = "Delete a deck settings by its name")
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name) {
	    if(name.equals("default"))
	        throw new CannotDeleteDefaultException(DeckSettings.class);
	    if (!deckSettingsService.checkIfNameExists(name))
	        throw new NameDoesNotExistException(DeckSettings.class, name);
	    deckSettingsService.delete(name);
	}

	@Operation(summary = "Get all deck settings names")
	@GetMapping("/allNames")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<String> getAllDeckSettingsNames() {
	    return deckSettingsService.getAllNames();
	}

	@Operation(summary = "Get lapse settings by deck settings name")
	@GetMapping("/{name}/lapseSettings")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LapseSettingsDTO getLapseSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name) {
	    return deckSettingsService.getLapseSettings(name);
	}

	@Operation(summary = "Get general settings by deck settings name")
	@GetMapping("/{name}/generalSettings")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public GeneralSettingsDTO getGeneralSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name) {
	    return deckSettingsService.getGeneralSettings(name);
	}

	@Operation(summary = "Get new card settings by deck settings name")
	@GetMapping("/{name}/newCardSettings")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public NewCardSettingsDTO getNewCardSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name) {
	    return deckSettingsService.getNewCardSettings(name);
	}

	@Operation(summary = "Get review settings by deck settings name")
	@GetMapping("/{name}/reviewSettings")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ReviewSettingsDTO getReviewSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name) {
	    return deckSettingsService.getReviewSettings(name);
	}
	
	@Operation(summary = "Rename a deck settings by its name")
	@PatchMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void patch(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name,
	        @NotBlank(message = "New" + DECK_SETTINGS_NOT_BLANK)
	        @RequestParam String newName) {
	    if(name.equals("default"))
	        throw new CannotRenameDefaultException(DeckSettings.class);
	    if (!deckSettingsService.checkIfNameExists(name))
	        throw new NameDoesNotExistException(DeckSettings.class, name);
	    deckSettingsService.patch(name, newName);
	}
	
	@Operation(summary = "Update lapse settings by deck settings name")
	@PatchMapping("/{name}/lapseSettings")
	@ResponseStatus(HttpStatus.OK)
	public void patchLapseSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name,
	        @Valid @RequestBody LapseSettingsDTO dto) {
	    deckSettingsService.patchLapseSettings(name, dto);
	}

	@Operation(summary = "Update general settings by deck settings name")
	@PatchMapping("/{name}/generalSettings")
	@ResponseStatus(HttpStatus.OK)
	public void patchGeneralSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name,
	        @Valid @RequestBody GeneralSettingsDTO dto) {
	    deckSettingsService.patchGeneralSettings(name, dto);
	}

	@Operation(summary = "Update new card settings by deck settings name")
	@PatchMapping("/{name}/newCardSettings")
	@ResponseStatus(HttpStatus.OK)
	public void patchNewCardSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name,
	        @Valid @RequestBody NewCardSettingsDTO dto) {
	    deckSettingsService.patchNewCardSettings(name, dto);
	}

	@Operation(summary = "Update review settings by deck settings name")
	@PatchMapping("/{name}/reviewSettings")
	@ResponseStatus(HttpStatus.OK)
	public void patchGeneralSettings(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @PathVariable String name,
	        @Valid @RequestBody ReviewSettingsDTO dto) {
	    deckSettingsService.patchReviewSettings(name, dto);
	}
	
	@Operation(summary = "Add a new deck settings")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void post(
	        @NotBlank(message = DECK_SETTINGS_NOT_BLANK)
	        @RequestParam String name) {
	    if (deckSettingsService.checkIfNameExists(name))
	        throw new NameAlreadyExistsException(DeckSettings.class, name);
	    deckSettingsService.post(name);
	}

}
