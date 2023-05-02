package com.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.configuration",
			"com.controller",
			"com.service",
			"com.mapper"})
@EntityScan("com.model")
@EnableMongoRepositories(basePackages="com.repository")
public class WordthyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordthyApiApplication.class, args);
	}

}
