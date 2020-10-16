package com.accenture.airpoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirpollApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirpollApplication.class, args);
		Database.connection();
	//	populateDB();
	}

	private static void populateDB(){
		DataService dataService = new DataService();
	 // dataService.getCities();
		dataService.getCountries();
		dataService.getParameters();
	//	dataService.getMeasurements();
		boolean moreAverages = true;
		Integer page = 1;
		while(moreAverages){
			boolean isNewDate = dataService.getAverages(page);
			if(isNewDate){
				page = 1;
			} else {
				page++;
			}
		}
	}

}
