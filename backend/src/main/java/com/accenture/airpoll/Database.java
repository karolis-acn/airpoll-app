package com.accenture.airpoll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class Database {
  static Connection conn = null;

  public static void connection() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
    } catch (ClassNotFoundException e) {
      System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
      e.printStackTrace();
      return;
    }

    try {
      // DriverManager: The basic service for managing a set of JDBC drivers.
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/airpoll", "root", "root");
      if (conn != null) {
        System.out.println("Connection Successful! Enjoy. Now it's time to push data");
      } else {
        System.out.println("Failed to make connection!");
      }
    } catch (SQLException e) {
      System.out.println("MySQL Connection Failed!");
      e.printStackTrace();
      return;
    }
  }

  public static void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.out.println("MySQL Connection Close Failed!");
      e.printStackTrace();
    }
  }

  public static Double[] getCoordinates (JSONObject coordinates){
    Double longitude = Double.valueOf(0);
    Double latitude = Double.valueOf(0);

    try {
        longitude = Double.valueOf(coordinates.get("longitude").toString());
        latitude = Double.valueOf(coordinates.get("latitude").toString());
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

    return new Double[] {longitude, latitude };
  }
  
  public static void queryPopulateAverages(String city, String country, Double average, JSONObject coordinates,
      String date, String location, Long measurements, String parameter, String unit) {

    try {
      Double[] coords = getCoordinates(coordinates);

      String insertQueryStatement = "REPLACE INTO averages (city, country, average, longitude, latitude, date, location, measurements, parameter, unit) VALUES (?,?,?,?,?,?,?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, city);
      prepareState.setString(2, country);
      prepareState.setDouble(3, average);
      prepareState.setDouble(4, coords[0]);
      prepareState.setDouble(5, coords[1]);
      prepareState.setString(6, date);
      prepareState.setString(7, location);
      prepareState.setLong(8, measurements);
      prepareState.setString(9, parameter);
      prepareState.setString(10, unit);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void queryPopulateCountry(String country){
    
    try {
      String updateQueryStatement = "UPDATE countries SET populated=1 WHERE code = ?";

      PreparedStatement prepareState = conn.prepareStatement(updateQueryStatement);
      prepareState.setString(1, country);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static boolean queryCheckCitiesByCountry(String country){
    try {
      String insertQueryStatement = "SELECT populated FROM countries WHERE code = ?";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, country);

      ResultSet resultSet = prepareState.getResultSet();
      if (resultSet != null && resultSet.next()) {
        System.out.println("!");
         return resultSet.getBoolean("populated");
      }

      // execute insert SQL statement
      prepareState.executeQuery();
      prepareState.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  public static void queryPopulateCities(String name, String country, Long count, Long locations) {

    try {
      String insertQueryStatement = "REPLACE INTO cities (name, country, count, locations) VALUES  (?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, name);
      prepareState.setString(2, country);
      prepareState.setLong(3, count);
      prepareState.setLong(4, locations);
      System.out.println(name);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void queryPopulateCountries(String code, String name, Long count, Long cities, Long locations) {

    try {
      String insertQueryStatement = "REPLACE INTO countries (code, name, count, cities, locations) VALUES (?,?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      // INSERT NEW COUNTRY
      prepareState.setString(1, code);
      prepareState.setString(2, name);
      prepareState.setLong(3, count);
      prepareState.setLong(4, cities);
      prepareState.setLong(5, locations);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void queryPopulateParameters(String id, String name, String description, String preferredUnit) {

    try {
      String insertQueryStatement = "REPLACE INTO parameters (id, name, description, preferredUnit) VALUES  (?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, id);
      prepareState.setString(2, name);
      prepareState.setString(3, description);
      prepareState.setString(4, preferredUnit);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void queryPopulateMeasurements(String city, JSONObject coordinates, String country, String location, String parameter, String unit, Double value) {

    try {
      
      Double[] coords = getCoordinates(coordinates);

      String insertQueryStatement = "REPLACE INTO measurements (city, longitude, latitude, country, location, parameter, unit, value) VALUES  (?,?,?,?,?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, city);
      prepareState.setDouble(2, coords[0]);
      prepareState.setDouble(3, coords[1]);
      prepareState.setString(4, country);
      prepareState.setString(5, location);
      prepareState.setString(6, parameter);
      prepareState.setString(7, unit);
      prepareState.setDouble(8, value);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}