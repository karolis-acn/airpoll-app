package com.accenture.airpoll.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityService {
  @Autowired
  private CityRepository repository;

  public List<City> listAll() {
    return repository.findAll();
  }
}