package com.accenture.airpoll.averages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/averages")
public class AverageController {
  
  @Autowired
  AverageService service;

  @GetMapping("")
  public List<Average> list() {
    return service.listAll();
  }

}
