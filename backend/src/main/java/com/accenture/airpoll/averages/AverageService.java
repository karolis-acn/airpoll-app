package com.accenture.airpoll.averages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AverageService {
  @Autowired
  private AverageRepository repository;

  public List<Average> listAll() {
    return repository.findAll();
  }
}