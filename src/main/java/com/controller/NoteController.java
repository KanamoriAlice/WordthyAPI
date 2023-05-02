package com.controller;

import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exception.NameAlreadyExistsException;
import com.inputdto.RenameDTO;
import com.model.Note;
import com.outputdto.NoteDTO;
import com.outputdto.NoteDataDTO;
import com.service.NoteService;

@RestController
@RequestMapping("/note")
@Validated
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@GetMapping("/allNotesReviews")
	@ResponseStatus(HttpStatus.OK)
	public List<NoteDTO> getAllNotesReviews() {
		return noteService.getAllNotesReviews();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(
			@NotBlank(message = NOTE_NOT_BLANK)
			@RequestParam String name) {
		if(noteService.checkIfTitleExists(name))
			throw new NameAlreadyExistsException(Note.class,name);
		noteService.post(name);
	}
	
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
			@NotBlank(message = NOTE_NOT_BLANK)
			@PathVariable String name) {
		noteService.delete(name);
	}
	
	@GetMapping("/noteReview/{name}")
	@ResponseStatus(HttpStatus.OK)
	public NoteDTO getNoteReview(
			@NotBlank(message = NOTE_NOT_BLANK)
			@PathVariable String name) {
		return noteService.getNoteReview(name);
	}
	
	@PatchMapping("/rename")
	@ResponseStatus(HttpStatus.OK)
	public void rename(@Valid @RequestBody RenameDTO dto) {
		noteService.rename(dto.getCurrentName(), dto.getNewName());
	}
	
	@PatchMapping("/updateContent")
	@ResponseStatus(HttpStatus.OK)
	public void updateContent(@Valid @RequestBody NoteDataDTO dto) {
		noteService.updateContent(dto.getTitle(), dto.getContent());
	}
	
}
