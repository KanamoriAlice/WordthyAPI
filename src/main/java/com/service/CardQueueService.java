package com.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.IllegalDeckStateException;
import com.model.Card;
import com.model.CardStatus;
import com.model.Deck;
import com.model.DeckSettings;
import com.model.NewCardOrder;
import com.outputdto.CardPlayDTO;
import com.outputdto.CardQueueDTO;
import com.outputdto.DeckReviewDTO;
import com.repository.CardRepository;
import com.repository.DeckRepository;
import com.repository.DeckSettingsRepository;

@Service
public class CardQueueService {
	
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private DeckSettingsRepository deckSettingsRepository;
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private DeckService deckService;
	
	//TODO check cases for subdecks
	public CardQueueDTO getCardQueue(String deckName) {
		Deck deck = deckRepository.findByName(deckName);
		DeckSettings deckSettings = deckSettingsRepository.findById(
				deck.getDeckSettingsId())
				.orElseThrow(IllegalDeckStateException::new);
		List<CardPlayDTO> laspsedCards = deckService.convertToCardDTOList(getLapsedCards(deck));
		int newCardLimit = 
				deckSettings.getNewCardSettings().getMaxNewCardsPerDay() -
				deck.getNewCardsReviewed();
		List<CardPlayDTO> newCards = deckService.convertToCardDTOList(
				getNewCards(deck, 
						deckSettings.getNewCardSettings().getNewCardOrder(),
						newCardLimit));
		int learnedCardLimit =
				deckSettings.getReviewSettings().getMaxReviewsPerDay() -
				deck.getLearnedCardsReviewed();
		List<CardPlayDTO> learnedCards = deckService.convertToCardDTOList(
				getDueCards(deck, learnedCardLimit));
		return new CardQueueDTO(newCards, learnedCards, laspsedCards,
				deckSettings.getReviewSettings().getReviewOrder());
	}
	//TODO refactor this method
	public List<DeckReviewDTO> getAllDeckReviews() {
		List<Deck> decks = deckRepository.findAll();
		List<DeckReviewDTO> deckReviews = new ArrayList<>();
		decks.forEach(deck -> {
			String parentDeck = "";
			if(deck.getParentDeckId() != null) {
				Optional<Deck> optParentDeck = deckRepository.findById(deck.getParentDeckId());
				if(optParentDeck.isPresent())
					parentDeck = optParentDeck.get().getName();
			}
			String deckSettings = deckSettingsRepository.findById(deck.getDeckSettingsId())
					.orElseThrow(IllegalDeckStateException::new).getName();
			CardQueueDTO cardQueue = getCardQueue(deck.getName());
			int lapsedCards = cardQueue.getLapsedCards().size();
			int reviewCards = cardQueue.getReviewCards().size();
			int newCards = cardQueue.getNewCards().size();
			deckReviews.add(new DeckReviewDTO(deck.getName(), parentDeck,
					deckSettings,lapsedCards, reviewCards, newCards,
					lapsedCards + reviewCards + newCards));
		});
		return deckReviews;
	}
	
	public DeckReviewDTO getDeckReview(String name) {
		Deck deck = deckRepository.findByName(name);
		String parentDeck = "";
		if(deck.getParentDeckId() != null) {
			Optional<Deck> optParentDeck = deckRepository.findById(deck.getParentDeckId());
			if(optParentDeck.isPresent())
				parentDeck = optParentDeck.get().getName();
		}
		String deckSettings = deckSettingsRepository.findById(deck.getDeckSettingsId())
				.orElseThrow(IllegalDeckStateException::new).getName();
		CardQueueDTO cardQueue = getCardQueue(deck.getName());
		int lapsedCards = cardQueue.getLapsedCards().size();
		int reviewCards = cardQueue.getReviewCards().size();
		int newCards = cardQueue.getNewCards().size();
		return new DeckReviewDTO(deck.getName(), parentDeck,
				deckSettings, lapsedCards, reviewCards, newCards,
				lapsedCards + reviewCards + newCards);
	}
	
	//TODO TEST THIS ONE AS WELL XD
	public List<Card> getDueCards(Deck deck, int limit) {
		if(limit == 0)
			return new ArrayList<>();
		List<Card> dueCards = cardRepository.findDueCardsInDeck(deck.getId(), limit, LocalDate.now());
		int newLimit = limit - dueCards.size();
		if(newLimit > 0) {
			List<Deck> subdecks = deckRepository.findAllByDeckSettingsId(deck.getId());
			for(Deck subdeck : subdecks) {
				dueCards.addAll(getDueCards(subdeck, newLimit));
				newLimit = limit - dueCards.size();
				if (newLimit <= 0)
					break;
			}
		}
		return dueCards;
			
	}
	
	//TODO TEST THIS METHOD XDDD
	public List<Card> getLapsedCards(Deck deck) {
		List<Card> lapsedCards = cardRepository.
				findAllByDeckIdAndCardStatus(deck.getId(),CardStatus.LAPSED);
		List<Deck> subdecks = deckRepository.findAllByParentDeckId(deck.getId());
		subdecks.forEach(x -> lapsedCards.addAll(getLapsedCards(x)));
		return lapsedCards;
	}
	
	public List<Card> getNewCards(Deck deck, NewCardOrder order, int limit) {
		if(limit == 0)
			return new ArrayList<>();
		return order == NewCardOrder.ADDED_ORDER ?
				cardRepository.findNewCardsInDeck(deck.getId(), limit, LocalDate.now()):
				cardRepository.findRandomNewCardsInDeck(deck.getId(), limit, LocalDate.now());
	}

}
