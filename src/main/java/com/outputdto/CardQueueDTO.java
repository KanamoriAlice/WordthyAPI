package com.outputdto;

import java.util.List;

import com.model.ReviewOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardQueueDTO {
	
	private List<CardDTO> newCards;
	private List<CardDTO> reviewCards;
	private List<CardDTO> lapsedCards; //Also includes new ones that were "lapsed"
	private ReviewOrder reviewOrder;

}
