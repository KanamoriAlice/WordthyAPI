package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inputdto.PostCardTypeDTO;
import com.model.Card;
import com.model.CardType;
import com.outputdto.CardTypeFormatDTO;
import com.repository.CardRepository;
import com.repository.CardTypeRepository;

@Service
public class CardTypeService {
	
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private CardTypeRepository cardTypeRepository;
	@Autowired
	private ModelMapper mapper;
	
	//PUBLIC METHODS
	public void addParameter(String cardTypeName, String paramaterName) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		List<Card> cards = cardRepository.findAllByCardTypeId(cardType.getId());
		cardType.getFieldNames().add(paramaterName);
		cards.forEach(x -> x.getFields().add(""));
		cardRepository.saveAll(cards);
		cardTypeRepository.save(cardType);
	}
	
	public boolean checkIfNameExists(String name) {
		return cardTypeRepository.findByName(name) != null;
	}
	
	public int getParameterCount(String cardTypeName) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		return cardType.getFieldNames().size();
	}
	
	public boolean checkIfParameterNameExists(String cardTypeName, String name) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		return cardType.getFieldNames().contains(name);
	}
	
	public void post(PostCardTypeDTO dto) {
		CardType cardType = new CardType(dto.getName(), dto.getFields());
		cardTypeRepository.save(cardType);
	}
	
	@Transactional
	public void delete(String name) {
		CardType cardType = cardTypeRepository.findByName(name);
		cardRepository.deleteAllByCardTypeId(cardType.getId());
		cardTypeRepository.delete(cardType);
	}
	
	public List<String> getFields(String name) {
		CardType cardType = cardTypeRepository.findByName(name);
		return cardType.getFieldNames();
	}
	
	public List<String> getAllNames() {
		return cardTypeRepository.findAll().stream()
				.map(CardType::getName)
				.collect(Collectors.toList());
	}
	
	public CardTypeFormatDTO getFormat(String name) {
		CardType cardType = cardTypeRepository.findByName(name);
		return mapper.map(cardType, CardTypeFormatDTO.class);
	}
	
	public void rename(String cardTypeName, String newName) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		cardType.setName(newName);
		cardTypeRepository.save(cardType);
	}
	
	public void renameParameter(String cardTypeName, String parameterName, String newName) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		List<String> parameters = cardType.getFieldNames();
		int paramenterIndex = parameters.indexOf(parameterName);
		parameters.remove(paramenterIndex);
		parameters.add(paramenterIndex, newName);
		cardTypeRepository.save(cardType);
	}
	
	@Transactional
	public void removeParameter(String cardTypeName, String parameterName) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		List<Card> cards = cardRepository.findAllByCardTypeId(cardType.getId());
		List<String> fieldNames = cardType.getFieldNames();
		int deletionIndex = fieldNames.indexOf(parameterName);
		fieldNames.remove(deletionIndex);
		cards.forEach(x -> x.getFields().remove(deletionIndex));
		cardRepository.saveAll(cards);
		cardTypeRepository.save(cardType);
	}
	
	public void updateFormat(String cardTypeName, String back, String front, String formatting) {
		CardType cardType = cardTypeRepository.findByName(cardTypeName);
		cardType.setBack(back);
		cardType.setFront(front);
		cardType.setFormatting(formatting);
		cardTypeRepository.save(cardType);
	}

}