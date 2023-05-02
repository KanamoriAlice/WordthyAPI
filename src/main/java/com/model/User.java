package com.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document("users")
public class User {

	@Id
	private String id;
	
	private String username;
	private String password;
	private String email;
	private Set<UserRole> roles;
	
	//Constructor used to create a new record in the database
	public User(String username, String password, String email, Set<UserRole> roles) {
		this(null, username, password, email, roles);
	}
	
}
