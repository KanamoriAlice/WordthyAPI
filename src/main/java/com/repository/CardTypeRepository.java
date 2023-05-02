package com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.model.CardType;

@Repository
public interface CardTypeRepository extends MongoRepository<CardType, String> {
	
	CardType findByName(String name);

}
