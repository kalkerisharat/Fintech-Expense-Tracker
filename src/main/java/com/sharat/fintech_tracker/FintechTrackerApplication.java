package com.sharat.fintech_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FintechTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechTrackerApplication.class, args);
	}

}
