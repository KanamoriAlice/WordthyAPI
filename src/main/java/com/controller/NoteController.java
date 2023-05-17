package com.controller;

import jakarta.validation.constraints.NotBlank;

import static com.configuration.TemporaryStrings.NOTE_NOT_BLANK;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exception.NameAlreadyExistsException;
import com.model.Note;
import com.outputdto.NoteDTO;
import com.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/note")
@Validated
public class NoteController {

	@Autowired
	private NoteService noteService;

	@Operation(summary = "Get all notes reviews")
	@GetMapping("/allNotesReviews")
	@ResponseStatus(HttpStatus.OK)
	public List<NoteDTO> getAllNotesReviews() {
		return noteService.getAllNotesReviews();
	}

	@Operation(summary = "Delete a note by its name")
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@NotBlank(message = NOTE_NOT_BLANK) @PathVariable String name) {
		noteService.delete(name);
	}

	@Operation(summary = "Get a note review by its name")
	@GetMapping("/{name}/noteReview")
	@ResponseStatus(HttpStatus.OK)
	public NoteDTO getNoteReview(
			@NotBlank(message = NOTE_NOT_BLANK) @PathVariable String name) {
		return noteService.getNoteReview(name);
	}

	@Operation(summary = "Create a new note")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(
			@NotBlank(message = NOTE_NOT_BLANK) @RequestParam String name) {
		if (noteService.checkIfTitleExists(name))
			throw new NameAlreadyExistsException(Note.class, name);
		noteService.post(name);
	}

	@Operation(summary = "Update a note's content by its name")
	@PatchMapping("/{name}/updateContent")
	@ResponseStatus(HttpStatus.OK)
	public void updateContent(
			@NotBlank(message = NOTE_NOT_BLANK) @PathVariable String name,
			@RequestParam String content) {
		noteService.updateContent(name, content);
	}

	@Operation(summary = "Get a note by its name")
	@GetMapping("/{name}/content")
	@ResponseStatus(HttpStatus.OK)
	public NoteDTO getNoteDTO(
			@NotBlank(message = NOTE_NOT_BLANK) @PathVariable String name) {
		return noteService.getNoteByName(name);
	}

	// @PatchMapping("/{name}/rename")
	// @ResponseStatus(HttpStatus.OK)
	// public void rename(
	// @NotBlank(message = NOTE_NOT_BLANK)
	// @PathVariable String name,
	// @RequestParam String newName) {
	// noteService.patch(name, newName);
	// }

}
