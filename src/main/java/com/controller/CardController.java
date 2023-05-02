package com.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inputdto.AddCardDTO;
import com.inputdto.AddRemoveTagDTO;
import com.inputdto.CardAnswerDTO;
import com.inputdto.CardUpdateDTO;
import com.inputdto.OnlyNameDTO;
import com.model.CardStatus;
import com.outputdto.CardDTO;
import com.service.CardService;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {

	@Autowired
	CardService cardService;

	@GetMapping
	public String getView() {
		return "card";
	}

	@PatchMapping("/addTag")
	@ResponseStatus(HttpStatus.OK)
	public void addTag(@Valid @RequestBody AddRemoveTagDTO dto) {
		cardService.addTag(dto.getCardId(), dto.getTagName());
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody AddCardDTO dto) {
		cardService.create(dto);
	}

	@PatchMapping("/schedule")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CardStatus schedule(@Valid @RequestBody CardAnswerDTO answer) {
		return cardService.schedule(answer);
	}

	// jajajajajajjajjajajjajjajajajajajajajajajajajajajajaj
	// hice una modificacion my rara para que jalara
	// porque no sabia como hacer la consulta
	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestBody OnlyNameDTO cardId) {
		cardService.delete(cardId.getName());
	}

	@PatchMapping("/removeTag")
	@ResponseStatus(HttpStatus.OK)
	public void removeTag(@Valid @RequestBody AddRemoveTagDTO dto) {
		cardService.removeTag(dto.getCardId(), dto.getTagName());
	}

	@GetMapping("/getByDeckName")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CardDTO> getByDeckName(
			@NotBlank(message = "Name cannot be empty nor null") @Valid @RequestParam("deckName") String deckName) {
		return cardService.getByDeckName(deckName);
	}

	@PatchMapping("/updateFields")
	@ResponseStatus(HttpStatus.OK)
	public void updateFields(@Valid @RequestBody CardUpdateDTO cardUpdateDTO) {
		cardService.updateFields(cardUpdateDTO);
	}
}
