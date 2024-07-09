package com.userservice.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class UserIdentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserIdentificationApplication.class, args);
	}

}
