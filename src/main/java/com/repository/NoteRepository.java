package com.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.model.Note;

public interface NoteRepository extends MongoRepository<Note, String> {

	public Note findByName(String name);

	@Query(value = "{ }", fields = "{'name' : 1, 'lastUpdated' : 1}")
	public List<Note> getAllNotesReviews();

	@Query(value = "{ 'name' : ?0 }", fields = "{'name' : 1, 'lastUpdated' : 1}")
	public Note getNoteReview(String name);

}
