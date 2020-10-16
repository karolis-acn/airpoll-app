package com.accenture.airpoll.averages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class AverageService {
  @Autowired
  private AverageRepository repository;

  private Sort getSortOrder(String order, String sortBy){
    Sort.Direction direction = null;

    if(order.equals("asc")){
      direction = Sort.Direction.ASC;
    } else if(order.equals("desc")){
      direction = Sort.Direction.DESC;
    }

    if(direction != null){
      return Sort.by(direction, sortBy);
    } else {
      return Sort.by(sortBy);
    }
  }

  public List<Average> listAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String filterCountry, String filterCity) {

    Pageable paging = PageRequest.of(pageNo, pageSize, getSortOrder(sortOrder, sortBy));
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
      return new LinkedList<Average>();
    }
  }
}