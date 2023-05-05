package com.service;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.CannotRenameDefaultException;
import com.generaldto.GeneralSettingsDTO;
import com.generaldto.LapseSettingsDTO;
import com.generaldto.NewCardSettingsDTO;
import com.generaldto.ReviewSettingsDTO;
import com.model.Deck;
import com.model.DeckSettings;
import com.model.GeneralSettings;
import com.model.LapseSettings;
import com.model.NewCardSettings;
import com.model.ReviewSettings;
import com.repository.DeckRepository;
import com.repository.DeckSettingsRepository;

@Service
public class DeckSettingsService {
	
	@Autowired
	private DeckRepository deckRepository;
	@Autowired
	private DeckSettingsRepository deckSettingsRepository;
	@Autowired
	private ModelMapper mapper;
	
	//PUBLIC METHODS
	public boolean checkIfNameExists(String name) {
		return deckSettingsRepository.findByName(name) != null;
	}
	
	public void post(String name) {
		DeckSettings deckSettings = new DeckSettings(name);
		deckSettingsRepository.save(deckSettings);
	}
	
	public void delete(String name) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(name);
		DeckSettings defaultDeckSettings = deckSettingsRepository.findByName("default");
		List<Deck> decks = deckRepository.findAllByDeckSettingsId(deckSettings.getId());
		decks.forEach(x -> x.setDeckSettingsId(defaultDeckSettings.getId()));
		deckRepository.saveAll(decks);
		deckSettingsRepository.delete(deckSettings);
	}
	
	public List<String> getAllNames() {
		return deckSettingsRepository.findAll().stream()
				.map(DeckSettings::getName)
				.collect(Collectors.toList());
	}
	
	public LapseSettingsDTO getLapseSettings(String deckSettingsName) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(deckSettingsName);
		TypeMap<LapseSettings, LapseSettingsDTO> periodMapper = mapper.getTypeMap(LapseSettings.class, LapseSettingsDTO.class);
		 if (periodMapper == null) {
			periodMapper = mapper.createTypeMap(LapseSettings.class, LapseSettingsDTO.class);
			Converter<Period, Integer> periodToInt = x -> x.getSource().getDays();
			periodMapper.addMappings(x ->
				x.using(periodToInt).map(LapseSettings::getMinInterval, LapseSettingsDTO::setMinInterval));
		 }
		return mapper.map(deckSettings.getLapseSettings(), LapseSettingsDTO.class);
	}
	
	public GeneralSettingsDTO getGeneralSettings(String deckSettingsName) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(deckSettingsName);
		return mapper.map(deckSettings.getGeneralSettings(), GeneralSettingsDTO.class);
	}
	
	public NewCardSettingsDTO getNewCardSettings(String deckSettingsName) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(deckSettingsName);
		TypeMap<NewCardSettings, NewCardSettingsDTO> periodMapper = mapper.getTypeMap(NewCardSettings.class, NewCardSettingsDTO.class);
		if(periodMapper == null) {
			periodMapper = mapper.createTypeMap(NewCardSettings.class, NewCardSettingsDTO.class);
			Converter<Period, Integer> periodToInt = x -> x.getSource().getDays();
			periodMapper.addMappings(
				x -> x.using(periodToInt).map(
					NewCardSettings::getGraduatingInterval,
					NewCardSettingsDTO::setGraduatingInterval));
		}
		return mapper.map(deckSettings.getNewCardSettings(), NewCardSettingsDTO.class);
	}
	
	public ReviewSettingsDTO getReviewSettings(String deckSettingsName) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(deckSettingsName);
		TypeMap<ReviewSettings, ReviewSettingsDTO> periodMapper = mapper.getTypeMap(ReviewSettings.class, ReviewSettingsDTO.class);
		if(periodMapper == null) {
			periodMapper = mapper.createTypeMap(ReviewSettings.class, ReviewSettingsDTO.class);
			Converter<Period, Integer> periodToInt = x -> x.getSource().getDays();
			periodMapper.addMappings(
					x -> x.using(periodToInt).map(
							ReviewSettings::getMaxInterval,
							ReviewSettingsDTO::setMaxInterval));
		}
		return mapper.map(deckSettings.getReviewSettings(), ReviewSettingsDTO.class);
	}
	
	public void patch(String currentName, String newName) throws CannotRenameDefaultException {
		if(!currentName.equals(newName)) {
			DeckSettings deckSettings = deckSettingsRepository.findByName(currentName);
			deckSettings.setName(newName);
			deckSettingsRepository.save(deckSettings);
		}
	}
	
	public void patchLapseSettings(String name, LapseSettingsDTO dto) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(name);
		TypeMap<LapseSettingsDTO, LapseSettings> periodMapper = mapper.getTypeMap(LapseSettingsDTO.class, LapseSettings.class);
		if(periodMapper == null) {
			periodMapper = mapper.createTypeMap(LapseSettingsDTO.class, LapseSettings.class);
			Converter<Integer, Period> intToPeriod = x -> Period.ofDays(x.getSource());
			periodMapper.addMappings(
				x -> x.using(intToPeriod).map(
					LapseSettingsDTO::getMinInterval,
					LapseSettings::setMinInterval));
		}
		LapseSettings settings = mapper.map(dto, LapseSettings.class);
		deckSettings.setLapseSettings(settings);
		deckSettingsRepository.save(deckSettings);
	}
	
	public void patchGeneralSettings(String name, GeneralSettingsDTO dto) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(name);
		GeneralSettings settings = mapper.map(dto, GeneralSettings.class);
		deckSettings.setGeneralSettings(settings);
		deckSettingsRepository.save(deckSettings);
	}
	
	public void patchNewCardSettings(String name, NewCardSettingsDTO dto) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(name);
		TypeMap<NewCardSettingsDTO, NewCardSettings> periodMapper = mapper.getTypeMap(NewCardSettingsDTO.class, NewCardSettings.class);
		if(periodMapper == null) {
			periodMapper = mapper.createTypeMap(NewCardSettingsDTO.class, NewCardSettings.class);
			Converter<Integer, Period> intToPeriod = x -> Period.ofDays(x.getSource());
			periodMapper.addMappings(
				x -> x.using(intToPeriod).map(
					NewCardSettingsDTO::getGraduatingInterval,
					NewCardSettings::setGraduatingInterval));
		}
		NewCardSettings settings = mapper.map(dto, NewCardSettings.class);
		deckSettings.setNewCardSettings(settings);
		deckSettingsRepository.save(deckSettings);
	}
	
	public void patchReviewSettings(String name, ReviewSettingsDTO dto) {
		DeckSettings deckSettings = deckSettingsRepository.findByName(name);
		TypeMap<ReviewSettingsDTO, ReviewSettings> periodMapper = mapper.getTypeMap(ReviewSettingsDTO.class, ReviewSettings.class);
		if(periodMapper == null) {
			periodMapper = mapper.createTypeMap(ReviewSettingsDTO.class, ReviewSettings.class);
			Converter<Integer, Period> intToPeriod = x -> Period.ofDays(x.getSource());
			periodMapper.addMappings(
				x -> x.using(intToPeriod).map(
					ReviewSettingsDTO::getMaxInterval,
					ReviewSettings::setMaxInterval));
		}
		ReviewSettings settings = mapper.map(dto, ReviewSettings.class);
		deckSettings.setReviewSettings(settings);
		deckSettingsRepository.save(deckSettings);
	}
	
}
