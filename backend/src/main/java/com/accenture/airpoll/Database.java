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

  private static int getCoordinatesId(Double longitude, Double latitude){
    try {
      String selectIdQueryStatement = "SELECT id FROM coordinates WHERE longitude = " + longitude + " AND latitude = " + latitude;
      PreparedStatement prepareStateID = conn.prepareStatement(selectIdQueryStatement);
      prepareStateID.executeQuery();
      ResultSet resultSetCoords = prepareStateID.getResultSet();
      if (resultSetCoords != null && resultSetCoords.next()) {
        return resultSetCoords.getInt("id");
      }
      prepareStateID.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static int queryPopulateCoordinates (JSONObject coordinates){
    Integer coordinatesId = 0;
    Double longitude;
    Double latitude;

    try {
        longitude = Double.valueOf(coordinates.get("longitude").toString());
        latitude = Double.valueOf(coordinates.get("latitude").toString());
        coordinatesId = getCoordinatesId(longitude, latitude);
    } catch (NullPointerException e) {
      e.printStackTrace();
      return 0;
    }

    if(coordinatesId == 0){
      try {

        // insert coordinates
        String insertQueryStatement = "INSERT INTO coordinates (longitude, latitude) VALUES (?,?)";

        PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
        prepareState.setDouble(1, longitude);
        prepareState.setDouble(2, latitude);
        prepareState.executeUpdate();
        prepareState.close();
        
        return getCoordinatesId(longitude, latitude);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return coordinatesId;
  }
  
  public static void queryPopulateAverages(String city, String country, Double average, JSONObject coordinates,
      String date, String location, Long measurement_count, String parameter, String unit) {

    try {
      Integer coordinatesId = queryPopulateCoordinates(coordinates);

      String insertQueryStatement = "INSERT IGNORE INTO averages (city, country, average, coordinates, date, location, measurement_count, parameter, unit) VALUES  (?,?,?,?,?,?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, city);
      prepareState.setString(2, country);
      prepareState.setDouble(3, average);
      prepareState.setInt(4, coordinatesId);
      prepareState.setString(5, date);
      prepareState.setString(6, location);
      prepareState.setLong(7, measurement_count);
      prepareState.setString(8, parameter);
      prepareState.setString(9, unit);

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
      Integer coordinatesId = queryPopulateCoordinates(coordinates);

      String insertQueryStatement = "REPLACE INTO measurements (city, coordinates, country, location, parameter, unit, value) VALUES  (?,?,?,?,?,?,?)";

      PreparedStatement prepareState = conn.prepareStatement(insertQueryStatement);
      prepareState.setString(1, city);
      prepareState.setLong(2, coordinatesId);
      prepareState.setString(3, country);
      prepareState.setString(4, location);
      prepareState.setString(5, parameter);
      prepareState.setString(6, unit);
      prepareState.setDouble(7, value);

      // execute insert SQL statement
      prepareState.executeUpdate();
      prepareState.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}