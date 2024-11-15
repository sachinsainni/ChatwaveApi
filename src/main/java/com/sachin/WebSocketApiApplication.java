package com.sachin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.sachin.repository")
@SpringBootApplication
@ComponentScan(basePackages = "com.sachin")
public class WebSocketApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApiApplication.class, args);
	}

}
