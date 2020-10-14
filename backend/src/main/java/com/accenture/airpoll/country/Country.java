package com.accenture.airpoll.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {
  @Id
  @Column(name = "code")
  private String code;
  @Column(name = "name")
  private String name;

  public Country() {
  }

  public Country(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
  // other setters and getters
}