package com.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exception.CannotDeleteDefaultException;
import com.exception.IllegalCardStateException;
import com.inputdto.PatchDeckDTO;
import com.model.Card;
import com.model.CardType;
import com.model.Deck;
import com.model.DeckSettings;
import com.outputdto.CardPlayDTO;
import com.outputdto.GetDeckDTO;
import com.repository.CardRepository;
import com.repository.CardTypeRepository;
import com.repository.DeckRepository;
import com.repository.DeckSettingsRepository;

@Service
public class DeckService {
	
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private DeckSettingsRepository deckSettingsRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private CardTypeRepository cardTypeRepository;
//	@Autowired
//	private ModelMapper mapper;

	public boolean checkIfNameExists(String name) {
		return deckRepository.findByName(name) != null;
	}
	
	@Transactional
	public void delete(String name) throws CannotDeleteDefaultException {
		Deck deck = deckRepository.findByName(name);
		cardRepository.deleteAllByDeckId(deck.getId());
		deckRepository.deleteAllByParentDeckId(deck.getId());
		deckRepository.delete(deck);
	}
	
	public List<GetDeckDTO> getAllNames() {
		List<Deck> decks = deckRepository.findAll();
		Map<String, String> parentDecks = new HashMap<>();
		decks.stream().forEach(deck ->
			parentDecks.put(deck.getId(), deck.getName()));
		return decks.stream()
				.map(deck -> {
					String parentDeck = parentDecks.get(deck.getParentDeckId());
					if(parentDeck != null)
						return new GetDeckDTO(parentDeck, deck.getName());
					return new GetDeckDTO("", deck.getName());
				}).collect(Collectors.toList());
	}
	
	public List<CardPlayDTO> getByDeckName(String name) {
		Deck deck = deckRepository.findByName(name);
		List<CardPlayDTO> cardsOfDeck;
		cardsOfDeck = convertToCardDTOList(cardRepository.findAllByDeckId(deck.getId()));
		return cardsOfDeck;
	}
	
	public void incrementNewCardsReviewed(String name) {
		Deck deck = deckRepository.findByName(name);
		int newCardCounter = deck.getNewCardsReviewed();
		deck.setNewCardsReviewed(++newCardCounter);
	}
	
	public void incrementReviewCardsReviewed(String name) {
		Deck deck = deckRepository.findByName(name);
		int learnedCardsCounter = deck.getLearnedCardsReviewed();
		deck.setLearnedCardsReviewed(++learnedCardsCounter);
	}
	
	public void patch(String name, PatchDeckDTO dto) {
		Deck deck = deckRepository.findByName(name);
		if(!dto.getParentDeck().isEmpty()) {
			Deck parentDeck = deckRepository.findByName(dto.getParentDeck());
			deck.setParentDeckId(parentDeck.getId());
		} else
			deck.setParentDeckId(null);
		if(!dto.getNewName().equals(name))
			deck.setName(dto.getNewName());
		DeckSettings deckSettings = deckSettingsRepository.findByName(dto.getDeckSettings());
		deck.setDeckSettingsId(deckSettings.getId());
		deckRepository.save(deck);
	}
	
	public void post(String name) {
		DeckSettings deckSettings = deckSettingsRepository.findByName("default");
		Deck deck = new Deck(deckSettings.getId(), name);
		deckRepository.save(deck);
	}
	
	//TODO Check if there's a better way to write this code
	public void resetAllDeckCounters() {
		List<Deck> decks = deckRepository.findAll();
		decks.stream()
		.filter(x -> x.getLastUpdated().isBefore(LocalDate.now()))
		.forEach(x -> {
			x.setNewCardsReviewed(0);
			x.setLearnedCardsReviewed(0);
			x.setLastUpdated(LocalDate.now());
		});
		deckRepository.saveAll(decks);
	}
	
	//PRIVATE METHODS
	//Converts an GetDeckDTO object to a Deck
//	private Deck mapDTOToDeck(GetDeckDTO deckDTO) {
//		return mapper.map(deckDTO, Deck.class);
//	}
	
	public List<CardPlayDTO> convertToCardDTOList(List<Card> cards) {
		List<CardPlayDTO> dto = new ArrayList<>();
		if (cards.isEmpty())
			return dto;
		CardType cardType = cardTypeRepository.findById(cards.get(0).getCardTypeId())
				.orElseThrow(IllegalCardStateException::new);
		for (Card card : cards) {
			if (!card.getCardTypeId().equals(cardType.getId()))
				cardType = cardTypeRepository.findById(card.getCardTypeId())
						.orElseThrow(IllegalCardStateException::new);
			dto.add(new CardPlayDTO(card.getId(), cardType.getBack(),
					cardType.getFormat(), cardType.getFront(),
					card.getFields(), cardType.getFieldNames()));
		}
			
		return dto;
	}
	
	//Retiring methods
	
//	public void removeParentDeck(String deckName) {
//	Deck deck = deckRepository.findByName(deckName);
//	deck.setParentDeckId(null);
//	deckRepository.save(deck);
//}

//public void rename(String deckName,String newName) throws CannotRenameDefaultException {
//	Deck deck = deckRepository.findByName(deckName);
//	deck.setName(newName);
//	deckRepository.save(deck);
//}
//
//public void setDeckSettings(String deckName, String deckSettingsName) {
//	Deck deck = deckRepository.findByName(deckName);
//	DeckSettings deckSettings = deckSettingsRepository.findByName(deckSettingsName);
//	deck.setDeckSettingsId(deckSettings.getId());
//	deckRepository.save(deck);
//}
//
//public void setParentDeck(String deckName, String parentDeckName) {
//	Deck deck = deckRepository.findByName(deckName);
//	Deck parentDeck = deckRepository.findByName(parentDeckName);
//	deck.setParentDeckId(parentDeck.getId());
//	deckRepository.save(deck);
//}

}
