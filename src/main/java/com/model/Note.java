package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("notes")
public class Note {
	
	@Id
	private String id;
	
	private List<String> decksId;
	
	private String name;
	private String content;
	private LocalDate lastUpdated;
	
	public Note(String name) {
		this(null, new ArrayList<>(), name, "", LocalDate.now());
	}
}
