package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralSettings {
	
	//TODO: will probably work on these ones in TT-2
	
	private int maxAnswerTime;
	private boolean showAnswerTimer;
	private boolean autoPlayAudio;
	private boolean replayQuestion;
	
	public GeneralSettings() {
		this(60, false, false, false);
	}

}
