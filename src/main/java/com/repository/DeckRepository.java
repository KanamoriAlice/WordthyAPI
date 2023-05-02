package com.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.model.Deck;

public interface DeckRepository extends MongoRepository<Deck, String> {
	
	public Deck findByName(String name);
	public List<Deck> findAllByDeckSettingsId(String deckSettingsId);
	public List<Deck> findAllByParentDeckId(String deckSettingsId);
	public void deleteAllByParentDeckId(String parentDeckId);

}
