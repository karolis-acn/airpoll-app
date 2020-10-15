package com.accenture.airpoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirpollApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirpollApplication.class, args);
		Database.connection();

		DataService dataService = new DataService();
		dataService.getAverages();
		// dataService.getCities();
		dataService.getCountries();
		dataService.getParameters();
		dataService.getMeasurements();
	}

}
