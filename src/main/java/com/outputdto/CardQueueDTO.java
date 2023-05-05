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
	
	private List<CardPlayDTO> newCards;
	private List<CardPlayDTO> reviewCards;
	private List<CardPlayDTO> lapsedCards; //Also includes new ones that were "lapsed"
	private ReviewOrder reviewOrder;

}
