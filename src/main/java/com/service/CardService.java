package com.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exception.CardDoesNotExistException;
import com.exception.IllegalCardStateException;
import com.inputdto.PostCardDTO;
import com.inputdto.PatchCardDTO;
import com.model.Card;
import com.model.CardAnswer;
import com.model.CardReview;
import com.model.CardScheduleSettings;
import com.model.CardStatus;
import com.model.CardTag;
import com.model.CardType;
import com.model.Deck;
import com.model.DeckSettings;
import com.outputdto.GetCardDTO;
import com.repository.CardRepository;
import com.repository.CardTagRepository;
import com.repository.CardTypeRepository;
import com.repository.DeckRepository;
import com.repository.DeckSettingsRepository;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private CardTagRepository cardTagRepository;
	@Autowired
	private CardTypeRepository cardTypeRepository;
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private DeckSettingsRepository deckSettingsRepository;

	// PUBLIC METHODS

	public void post(PostCardDTO registrationCard) {
		Deck deck = deckRepository.findByName(registrationCard.getDeckName());
		CardType cardType = cardTypeRepository.findByName(registrationCard.getCardTypeName());
		Optional<DeckSettings> deckSettingsOpt = deckSettingsRepository.findById(deck.getDeckSettingsId());
		if (deckSettingsOpt.isPresent()) {
			Card card = new Card(cardType.getId(), deck.getId(),1,
					registrationCard.getFields());
			cardRepository.save(card);
		}
	}	

	public void delete(String id) {
		cardRepository.deleteById(id);
	}
	
	//This method does take TIME
	public Page<GetCardDTO> getAllCardsByPages(int pageNumber, int pageSize) {
	  Pageable pageable = PageRequest.of(pageNumber, pageSize);
	  Page<Card> cards = cardRepository.findAll(pageable);
	  return cards.map(card -> {
		  Deck deck = deckRepository.findById(card.getDeckId())
				  .orElseThrow(IllegalCardStateException::new);
		  CardType cardType = cardTypeRepository.findById(card.getCardTypeId())
				  .orElseThrow(IllegalCardStateException::new);
		  Set<String> tags = card.getCardTagsId().stream()
				  .map(x -> cardTagRepository.findById(x)
						  .orElseThrow(IllegalCardStateException::new).getName())
				  .collect(Collectors.toSet());
		  return new GetCardDTO(card.getId(), deck.getName(),
				  cardType.getName(), card.getFields(), cardType.getFieldNames(), tags);
	  });
	}

	// Returns new card status
	public CardStatus schedule(String id, CardAnswer answer) {
		Card card = cardRepository.findById(id)
				.orElseThrow(CardDoesNotExistException::new);
		Deck deck = deckRepository.findById(card.getDeckId())
				.orElseThrow(IllegalCardStateException::new);
		DeckSettings deckSettings = deckSettingsRepository.findById(deck.getDeckSettingsId())
				.orElseThrow(IllegalCardStateException::new);
		recordCardAnswer(card, answer);
		switch (card.getCardStatus()) {
			case NEW:
				scheduleNewCard(card, answer, deckSettings);
				if (card.getCardStatus() == CardStatus.LEARNED)
					deck.setNewCardsReviewed(deck.getNewCardsReviewed() + 1);
				break;
			case LEARNED:
				scheduleLearnedCard(card, answer, deckSettings);
				if (card.getCardStatus() == CardStatus.LEARNED)
					deck.setLearnedCardsReviewed(deck.getLearnedCardsReviewed() + 1);
				break;
			case LAPSED:
				scheduleLapsed(card, answer, deckSettings);
				break;
			default:
		}
		if (card.getCardStatus() == CardStatus.LEARNED)
			card.getCardScheduleSettings().setNextReviewDay(
					LocalDate.now().plus(card.getCardScheduleSettings().getInterval()));
		cardRepository.save(card);
		deckRepository.save(deck);
		return card.getCardStatus();
	}

	public void suspend(String cardId) {
		Card card = cardRepository.findById(cardId)
				.orElseThrow(IllegalCardStateException::new);
		card.setCardStatus(CardStatus.SUSPENDED);
		cardRepository.save(card);
	}

	// PRIVATE METHODS
	private void scheduleLapsed(Card card, CardAnswer answer, DeckSettings deckSettings) {
		CardScheduleSettings scheduleSettings = card.getCardScheduleSettings();
		int cardSteps = scheduleSettings.getSteps();
		// CardAnswer was AGAIN
		if (answer == CardAnswer.AGAIN)
			setSteps(card, CardStatus.LAPSED, deckSettings);
		else if (cardSteps > 0)
			scheduleSettings.setSteps(--cardSteps);
		else
			card.setCardStatus(CardStatus.LEARNED);
	}

	private void scheduleLearnedCard(Card card, CardAnswer answer, DeckSettings deckSettings) {
		CardScheduleSettings scheduleSettings = card.getCardScheduleSettings();
		Period cardInterval = scheduleSettings.getInterval();
		// CardAnswer was AGAIN
		if (answer == CardAnswer.AGAIN) {
			int lapseCount = card.getCardStats().getLapseCount();
			card.getCardStats().setLapseCount(++lapseCount);
			if (lapseCount >= deckSettings.getLapseSettings().getLeechThreshold())
				card.setCardStatus(CardStatus.SUSPENDED);
			else {
				card.setCardStatus(CardStatus.LAPSED);
				setSteps(card, CardStatus.LAPSED, deckSettings);
				scheduleSettings.setInterval(cardInterval.multipliedBy(
						Math.max(1, cardInterval.getDays() * deckSettings.getLapseSettings().getNewInterval() / 100)));
			}
			// CardAnswer was GOOD
		} else
			scheduleSettings.setInterval(
					Period.ofDays((int) Math.ceil(cardInterval.getDays() *
							(deckSettings.getReviewSettings().getIntervalModifier() / 100.0) *
							(deckSettings.getReviewSettings().getIntervalModifier() / 100.0))));
	}

	private void scheduleNewCard(Card card, CardAnswer answer, DeckSettings deckSettings) {
		CardScheduleSettings scheduleSettings = card.getCardScheduleSettings();
		int cardSteps = scheduleSettings.getSteps();
		// CardAnswer was AGAIN
		if (answer == CardAnswer.AGAIN)
			setSteps(card, CardStatus.NEW, deckSettings);
		// CardAnswer was GOOD
		else if (cardSteps > 0)
			scheduleSettings.setSteps(--cardSteps);
		else {
			scheduleSettings.setInterval(
					deckSettings.getNewCardSettings().getGraduatingInterval());
			card.setCardStatus(CardStatus.LEARNED);
		}
	}

	private void recordCardAnswer(Card card, CardAnswer answer) {
		card.getCardStats().getReviews().add(
				new CardReview(answer,
						card.getCardStatus(), LocalDateTime.now(),
						card.getCardScheduleSettings().getInterval()));
	}

	// TODO: Consider using the interval card CardStatus
	private void setSteps(Card card, CardStatus cardStatus, DeckSettings deckSettings) {
		card.getCardScheduleSettings()
				.setSteps(cardStatus == CardStatus.LAPSED ? deckSettings.getLapseSettings().getSteps() : // Lapse Steps
						deckSettings.getNewCardSettings().getSteps()); // New Steps
	}

	private void resetLapseCount(Card card) {
		card.getCardStats().setLapseCount(0);
	}
	
	public void unsuspend(String cardId) {
		Card card = cardRepository.findById(cardId)
				.orElseThrow(CardDoesNotExistException::new);
		card.setCardStatus(CardStatus.LEARNED);
		resetLapseCount(card);
		cardRepository.save(card);
	}

	public void patch(String id, PatchCardDTO dto) {
		Card card = cardRepository.findById(id)
				.orElseThrow(CardDoesNotExistException::new);
		Deck deck = deckRepository.findByName(dto.getDeck());
		card.setDeckId(deck.getId());
		CardType cardType = cardTypeRepository.findByName(dto.getCardType());
		if(!dto.getCardType().equals(cardType.getName())) {
			card.setCardTypeId(cardType.getId());
			card.setFields(dto.getFields());
		}
		card.setCardTagsId(dto.getTags().stream()
				.map(cardTagRepository::findByName)
				.collect(Collectors.toSet()).stream()
				.map(CardTag::getName).collect(Collectors.toSet()));
		cardRepository.save(card);
	}
	
	//Retiring methods
	
//	public void addTag(String cardId, String tagName) {
//		Optional<Card> cardOpt = cardRepository.findById(cardId);
//		if (cardOpt.isPresent()) {
//			Card card = cardOpt.get();
//			CardTag cardTag = cardTagRepository.findByName(tagName);
//			card.getCardTagsId().add(cardTag.getId());
//			cardRepository.save(card);
//		}
//	}
	
//	public void removeTag(String cardId, String tagName) {
//		Optional<Card> cardOpt = cardRepository.findById(cardId);
//		if (cardOpt.isPresent()) {
//			Card card = cardOpt.get();
//			card.getCardTagsId().remove(tagName);
//			cardRepository.save(card);
//		}
//	}
	
//	public void updateFields(CardUpdateDTO cardUpdateDTO) {
//		Card card = cardRepository.findById(cardUpdateDTO.getId())
//				.orElseThrow(CardDoesNotExistException::new);
//		card.setFields(cardUpdateDTO.getFields());
//		cardRepository.save(card);
//	}
}
