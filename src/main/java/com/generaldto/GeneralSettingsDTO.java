package com.generaldto;


import jakarta.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralSettingsDTO {
	
	//TODO: will probably work on these ones in TT-2
	@Positive(message = "The number must be greater than zero")
	private int maxAnswerTime;
	private boolean showAnswerTimer;
	private boolean autoPlayAudio;
	private boolean replayQuestion;
	private boolean autoDisplayAnswer;
	//Time until the answer is shown
	@Positive(message = "The number should be greater than 0")
	private int showAnswerTime;
	//Time until next question is shown
	@Positive(message = "The number should be greater than 0")
	private int nextQuestionTime;
	
}
