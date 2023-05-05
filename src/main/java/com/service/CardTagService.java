package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Card;
import com.model.CardTag;
import com.repository.CardRepository;
import com.repository.CardTagRepository;

@Service
public class CardTagService {
	
	@Autowired
	private CardTagRepository cardTagRepository;
	@Autowired
	private CardRepository cardRepository;
	
	public boolean checkIfNameExists(String name) {
		return cardTagRepository.findByName(name) != null;
	}
	
	public void post(String tagName) {
		CardTag cardTag = new CardTag(tagName);
		cardTagRepository.save(cardTag);
	}
	
	public void delete(String name) {
		CardTag cardTag = cardTagRepository.findByName(name);
		List<Card> cards = cardRepository.findAllByCardTagId(cardTag.getId());
		cards.forEach(x -> x.getCardTagsId().remove(cardTag.getId()));
		cardRepository.saveAll(cards);
		cardTagRepository.delete(cardTag);
	}
	
	public List<String> getAllNames() {
		return cardTagRepository.findAll().stream()
				.map(CardTag::getName)
				.collect(Collectors.toList());
	}
	
	public void patch(String tagName, String newName) {
		CardTag cardTag = cardTagRepository.findByName(tagName);
		cardTag.setName(newName);
		cardTagRepository.save(cardTag);
	}

}
