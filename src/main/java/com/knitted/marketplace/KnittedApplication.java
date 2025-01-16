package com.knitted.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnittedApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnittedApplication.class, args);
	}

}

//TODO: check all relations - bidirectional, cascading, orphanremoval, fetch
//TODO: add @Valid annotation to the request parameters (in all controllers)