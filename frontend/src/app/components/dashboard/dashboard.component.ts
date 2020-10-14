import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { DataService } from '../../data.service';
import { Averages } from './../../interfaces/averages.interface';
import { City } from './../../interfaces/city.interface';
import { Country } from './../../interfaces/country.interface';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  // filters
  countries: Country[];
  cities: City[];
  citiesFiltered: City[];
  selectedCountry: Country;
  selectedCity: City;

  // table
  averages: Averages[];
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['location', 'city', 'country', 'average', 'parameter', 'date'];


  constructor(private dataService: DataService) {

  }

  ngOnInit() {
    this.dataService.getAverages()
      .subscribe((averages: any) => {
        this.averages = averages.results;
        this.dataSource = new MatTableDataSource(this.averages);
      });

    this.dataService.getCountries()
      .subscribe((result: any) => {
        this.countries = result.results;
      });

    this.dataService.getCities()
      .subscribe((result: any) => {
        this.cities = result.results;
      });
  }

  sortData(event){
    console.log(event);
  }

  selectCountry(event){
    this.citiesFiltered = this.cities.filter(city => city.country === this.selectedCountry.code);
    console.log(this.citiesFiltered, this.selectedCountry);
  }
}
