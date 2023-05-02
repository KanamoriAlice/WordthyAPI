package com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.model.CardTag;

public interface CardTagRepository extends MongoRepository<CardTag, String> {
	
	public CardTag findByName(String name);

}
