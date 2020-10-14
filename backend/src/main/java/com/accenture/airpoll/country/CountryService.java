package com.accenture.airpoll.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {
  @Autowired
  private CountryRepository countryRepository;

  public List<Country> listAll() {
    return countryRepository.findAll();
  }

  public void saveCountry(Country user) {
    countryRepository.save(user);
  }

  public Country getCountry(String id) {
    return countryRepository.findById(id).get();
  }

  public void deleteCountry(String id) {
    countryRepository.deleteById(id);
  }
}