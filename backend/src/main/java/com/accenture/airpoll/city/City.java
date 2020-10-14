package com.accenture.airpoll.city;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class City {
  @Id
  @Column(name = "name")
  private String name;
  @Column(name = "country")
  private String country;

  public City() {
  }

  public City(String name, String country) {
    this.name = name;
    this.country = country;
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }
}