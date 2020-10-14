package com.accenture.airpoll;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataService {

  public JSONArray getData(String urlString){
    try {
       URL url = new URL(urlString);
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestProperty("content-type", "application/json;  charset=utf-8");
       conn.setRequestMethod("GET");
       conn.connect();

       int responsecode = conn.getResponseCode();

       if(responsecode == 200){
        Scanner sc = new Scanner(url.openStream(), "UTF-8");
        String inline = "";
        while(sc.hasNext())
          {
            inline+=sc.nextLine();
          }
        sc.close();
      JSONParser parse = new JSONParser();
      JSONObject responseData = (JSONObject) parse.parse(inline);
      return (JSONArray) responseData.get("results");
       }
     } catch (IOException e) {
       e.printStackTrace();
     } catch (ParseException e) {
       e.printStackTrace();
    }
    return null;
  }

  public void getAverages() {
    JSONArray results = getData("https://api.openaq.org/beta/averages?limit=10000");
    if (results != null) {
      for (int i = 0; i < results.size(); i++) {
        JSONObject item = (JSONObject) results.get(i);
        String city = (String) item.get("city");
        String country = (String) item.get("country");
        Double average = Double.valueOf(item.get("average").toString());
        JSONObject coordinates = (JSONObject) item.get("coordinates");
        String date = (String) item.get("date");
        String location = (String) item.get("location");
        Long measurement_count = (Long) item.get("measurement_count");
        String parameter = (String) item.get("parameter");
        String unit = (String) item.get("unit");

        Database.queryPopulateAverages(city, country, average, coordinates, date, location, measurement_count,
            parameter, unit);
      }
    }
  }

  public void getCities() {
    JSONArray results = getData("https://api.openaq.org/v1/cities?limit=10000");
    if (results != null) {
      for (int i = 0; i < results.size(); i++) {
        JSONObject item = (JSONObject) results.get(i);
        String name = (String) item.get("name");
        String country = (String) item.get("country");
        Long count = (Long) item.get("count");
        Long locations = (Long) item.get("locations");

        Database.queryPopulateCities(name, country, count, locations);
      }
    }
  }

  public void getCountries() {
    JSONArray results = getData("https://api.openaq.org/v1/countries?limit=10000");
    if (results != null) {
      for (int i = 0; i < results.size(); i++) {
        JSONObject item = (JSONObject) results.get(i);
        String code = (String) item.get("code");
        String name = (String) item.get("name");
        Long count = (Long) item.get("count");
        Long cities = (Long) item.get("cities");
        Long locations = (Long) item.get("locations");

        Database.queryPopulateCountries(code, name, count, cities, locations);
      }
    }
  }

  public void getParameters() {
    JSONArray results = getData("https://api.openaq.org/v1/parameters?limit=10000");
    if (results != null) {
      for (int i = 0; i < results.size(); i++) {
        JSONObject item = (JSONObject) results.get(i);
        String id = (String) item.get("id");
        String name = (String) item.get("name");
        String description = (String) item.get("description");
        String preferredUnit = (String) item.get("preferredUnit");

        Database.queryPopulateParameters(id, name, description, preferredUnit);
      }
    }
  }

  public void getMeasurements() {
    JSONArray results = getData("https://api.openaq.org/v1/measurements?limit=10000");
    if (results != null) {
      for (int i = 0; i < results.size(); i++) {
        JSONObject item = (JSONObject) results.get(i);
        String city = (String) item.get("city");
        JSONObject coordinates = (JSONObject) item.get("coordinates");
        String country = (String) item.get("country");
        String location = (String) item.get("location");
        String parameter = (String) item.get("parameter");
        String unit = (String) item.get("unit");
        Double value = Double.valueOf(item.get("value").toString());

        Database.queryPopulateMeasurements(city, coordinates, country, location, parameter, unit, value);
      }
    }
  }
}