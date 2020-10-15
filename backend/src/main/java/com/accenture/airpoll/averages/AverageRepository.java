package com.accenture.airpoll.averages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AverageRepository extends PagingAndSortingRepository<Average, String> {
  Page<Average> findByCountry(String country, Pageable pageable);
  Page<Average> findByCountryAndCity(String country, String city, Pageable pageable);
}
