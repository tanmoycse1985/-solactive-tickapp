package com.solactive.solactivetickapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolactiveTickappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolactiveTickappApplication.class, args);
	}

}
