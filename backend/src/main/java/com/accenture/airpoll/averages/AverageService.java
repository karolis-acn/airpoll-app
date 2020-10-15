package com.accenture.airpoll.averages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AverageService {
  @Autowired
  private AverageRepository repository;

  public List<Average> listAll(Integer pageNo, Integer pageSize, String sortBy, Integer sortOrder, String filterCountry, String filterCity) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(
        sortOrder == 1 ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

    Page<Average> pagedResult;

    if(filterCountry != ""){
      if(filterCity != ""){
        pagedResult = repository.findByCountryAndCity(filterCountry, filterCity, paging);
      } else {
        pagedResult = repository.findByCountry(filterCountry, paging);
      }
    } else {
      pagedResult = repository.findAll(paging);
    }
    
    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return new ArrayList<Average>();
    }
  }
}