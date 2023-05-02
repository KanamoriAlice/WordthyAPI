package com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.model.DeckSettings;

public interface DeckSettingsRepository extends MongoRepository<DeckSettings, String> {
	
	public DeckSettings findByName(String name);

}
