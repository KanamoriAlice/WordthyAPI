package com.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardStats {
	
	//TODO implement function and attributes to count time
	private int lapseCount;
	private int reviewCount;
	private LocalDate addedDate;
	private List<CardReview> reviews;
	
	public CardStats() {
		this(0,0,LocalDate.now(), new ArrayList<>());
	}

}
