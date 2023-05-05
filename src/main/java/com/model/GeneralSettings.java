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
	private boolean autoDisplayAnswer;
	//Time until the answer is shown
	private int showAnswerTime;
	//Time until next question is shown
	private int nextQuestionTime;
	
	public GeneralSettings() {
		this(60, false, false, false, false, 60, 60);
	}

}
