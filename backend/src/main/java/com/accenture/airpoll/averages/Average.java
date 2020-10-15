package com.accenture.airpoll.averages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "averages")
public class Average {

  @Id
  @Column(name = "location")
  private String location;
  @Column(name = "city")
  private String city;
  @Column(name = "country")
  private String country;
  @Column(name = "average")
  private Double average;
  @Column(name = "date")
  private String date;
  @Column(name = "parameter")
  private String parameter;
  @Column(name = "unit")
  private String unit;
  @Column(name = "latitude")
  private Double latitude;
  @Column(name = "longitude")
  private Double longitude;
  @Column(name = "measurements")
  private Integer measurements;

  public Average() {
  }

  public Average(String location, String city, String country, Double average, String date, String parameter, String unit, 
      Double latitude, Double longitude, Integer measurements) {
    this.location = location;
    this.city = city;
    this.country = country;
    this.average = average;
    this.date = date;
    this.parameter = parameter;
    this.unit = unit;
    this.latitude = latitude;
    this.longitude = longitude;
    this.measurements = measurements;
  }

  public String getLocation() {
    return location;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public Double getAverage() {
    return average;
  }

  public String getDate() {
    return date;
  }

  public String getParameter() {
    return parameter;
  }

  public String getUnit() {
    return unit;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Integer getMeasurements() {
    return measurements;
  }
}