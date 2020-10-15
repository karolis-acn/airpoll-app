package com.accenture.airpoll.averages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/averages")
public class AverageController {
  
  @Autowired
  AverageService service;

  @GetMapping("")
  public ResponseEntity<List<Average>> list(
    @RequestParam(defaultValue = "0") Integer pageNo,
    @RequestParam(defaultValue = "50") Integer pageSize, 
    @RequestParam(defaultValue = "city") String sortBy,
    @RequestParam(defaultValue = "0") Integer sortOrder,
    @RequestParam String filterCountry,
    @RequestParam String filterCity
  ) {
    List<Average> list = service.listAll(pageNo, pageSize, sortBy, sortOrder, filterCountry, filterCity);

    return new ResponseEntity<List<Average>>(list, new HttpHeaders(), HttpStatus.OK);
  }

}
