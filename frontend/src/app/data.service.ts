import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';

import { Averages } from './interfaces/averages.interface';
import { City } from './interfaces/city.interface';
import { Country } from './interfaces/country.interface';

@Injectable({ providedIn: 'root' })
export class DataService {
  private serviceUrl = 'localhost:3000'; // URL to web api
  private GET_AVERAGES = '/averages';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  /** GET averages */
  getAverages(): Observable<Averages[]> {
    const url = `https://api.openaq.org/beta/averages?limit=500`;
    return this.http.get<Averages[]>(url);
  }
  /** GET cities */
  getCities(): Observable<City[]> {
    const url = `https://api.openaq.org/v1/cities?limit=500`;
    return this.http.get<City[]>(url);
  }
  /** GET cities */
  getCountries(): Observable<Country[]> {
    const url = `https://api.openaq.org/v1/countries?limit=500`;
    return this.http.get<Country[]>(url);
  }
}
