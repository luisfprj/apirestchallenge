package com.b2wplanets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"controller"})
@EnableMongoRepositories ("repository")
public class B2wChallengeApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(B2wChallengeApplication.class, args);
	}
	
}
