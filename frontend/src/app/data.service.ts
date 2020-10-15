import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { Averages } from './interfaces/averages.interface';
import { City } from './interfaces/city.interface';
import { Country } from './interfaces/country.interface';

@Injectable({ providedIn: 'root' })
export class DataService {
  private static serviceUrl = 'http://localhost:8080'; // URL to web api
  private static GET_AVERAGES = DataService.serviceUrl + '/averages';
  private static GET_COUNTRIES = DataService.serviceUrl + '/countries';
  private static GET_CITIES = DataService.serviceUrl + '/cities';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  /** GET averages sorted filtered & paged */
  getAverages(
    page: number,
    sortBy: string,
    sortOrder: string,
    filterCountry: string = '',
    filterCity: string = ''
  ): Observable<Averages[]> {
    const url = DataService.GET_AVERAGES + '?pageNo=' + page + '&sortBy=' + sortBy + '&sortOrder=' + sortOrder
      + '&filterCountry=' + filterCountry + '&filterCity=' + filterCity;
    return this.http.get<Averages[]>(url);
  }
  /** GET cities by country */
  getCities(country: string): Observable<City[]> {
    const url = DataService.GET_CITIES + '?country=' + country;
    return this.http.get<City[]>(url);
  }
  /** GET countries */
  getCountries(): Observable<Country[]> {
    const url = DataService.GET_COUNTRIES;
    return this.http.get<Country[]>(url);
  }
}
