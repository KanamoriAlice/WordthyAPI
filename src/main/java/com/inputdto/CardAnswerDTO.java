package com.inputdto;


import com.model.CardAnswer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//It is the answer of a card
public class CardAnswerDTO {
	
	private String id;
	private CardAnswer answer;

}
