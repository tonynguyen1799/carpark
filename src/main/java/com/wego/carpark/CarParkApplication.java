package com.wego.carpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class CarParkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarParkApplication.class, args);
	}

	@Bean
	public RestTemplate govSgRestTemplate() {
		return new RestTemplate();
	}

}
