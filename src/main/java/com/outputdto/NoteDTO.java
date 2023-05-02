package com.outputdto;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoteDTO {
	
	private String id;
	private List<String> decksId;
	private String name;
	private String content;
	private LocalDate lastUpdated;
	
}
