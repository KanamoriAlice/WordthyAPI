package com.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Note;
import com.outputdto.NoteDTO;
import com.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private ModelMapper mapper;

	public boolean checkIfTitleExists(String title) {
		return noteRepository.findByName(title) != null;
	}

	public void delete(String title) {
		Note note = noteRepository.findByName(title);
		noteRepository.delete(note);
	}

	public List<NoteDTO> getAllNotesReviews() {
		List<Note> notes = noteRepository.getAllNotesReviews();
		return notes.stream()
				.map(note -> mapper.map(note, NoteDTO.class))
				.collect(Collectors.toList());
	}

	public NoteDTO getNoteReview(String name) {
		Note note = noteRepository.getNoteReview(name);
		return mapper.map(note, NoteDTO.class);
	}

	public void post(String name) {
		Note note = new Note(name);
		noteRepository.save(note);
	}

	public void rename(String title, String newTitle) {
		Note note = noteRepository.findByName(title);
		note.setName(newTitle);
		noteRepository.save(note);
	}

	public void updateContent(String title, String content) {
		Note note = noteRepository.findByName(title);
		note.setContent(content);
		note.setLastUpdated(LocalDate.now());
		noteRepository.save(note);
	}

	public NoteDTO getNoteByName(String name) {
		Note note = noteRepository.findByName(name);
		return mapper.map(note, NoteDTO.class);
	}

}
