package com.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.model.Card;
import com.model.CardStatus;

public interface CardRepository extends MongoRepository<Card, String> {
	
	public void deleteAllByDeckId(String deckId);
	public void deleteAllByCardTypeId(String cardTypeId);
	public List<Card> findAllByCardTypeId(String cardTypeId);
	public List<Card> findAllByDeckId(String deckId);
	public List<Card> findAllByDeckIdAndCardStatus(String deckId,
			CardStatus cardStatus);
	
	@Query("{ 'cardTagsId' : ?0 }")
	public List<Card> findAllByCardTagId(String cardTagId);
	
	@Aggregation(pipeline = {
			"{ '$match' : {'$and' : "
			+ "[{'deckId' : ?0},"
			+ "{'cardStatus' : 'LEARNED'},"
			+ "{'cardScheduleSettings.nextReviewDay' : {$lte : ?2}}"
			+ "]}}",
			"{ '$limit' : ?1 }"})
	public List<Card> findDueCardsInDeck(String deckId, int limit, LocalDate date);
	
	@Aggregation(pipeline = {
			"{ '$match' : {'$and' : "
					+ "[{'deckId' : ?0},"
					+ "{'cardStatus' : 'NEW'}"
					+ "]}}",
			"{ '$limit' : ?1 }"})
	public List<Card> findNewCardsInDeck(String deckId, int limit, LocalDate date);
	//TODO Debug this
	@Aggregation(pipeline = {
			"{ '$match' : {'$and' : "
			+ "[{'deckId' : ?0},"
			+ "{'cardStatus' : 'NEW'}"
			+ "]}}",
			"{ '$sample' : ?1 }"})
	public List<Card> findRandomNewCardsInDeck(String deckId, int limit, LocalDate date);

}
