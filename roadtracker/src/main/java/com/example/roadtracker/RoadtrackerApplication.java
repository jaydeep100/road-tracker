package com.example.roadtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoadtrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RoadtrackerApplication.class, args);
		System.out.println("Server is running !");
	}
}
