package com.accenture.airpoll.city;

import java.util.List;

import com.accenture.airpoll.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/cities")
public class CityController {
  
  @Autowired
  CityService service;

  @GetMapping("")
  public List<City> list(@RequestParam String country) {
    DataService dataService = new DataService();
    dataService.getCities(country);
    return service.listAll(country);
  }

}
