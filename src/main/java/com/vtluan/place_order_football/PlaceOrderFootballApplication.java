package com.vtluan.place_order_football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlaceOrderFootballApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceOrderFootballApplication.class, args);
	}

}
