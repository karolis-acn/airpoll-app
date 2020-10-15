package com.accenture.airpoll.country;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
  List<Country> findByNameNotNull();
}
