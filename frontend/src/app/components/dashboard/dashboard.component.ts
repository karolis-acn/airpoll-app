import { Component, OnInit } from '@angular/core';
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

  // pagination
  page = 0;
  isPageLoading = true;
  isEOL = false; // End of List

  // sorting
  sortBy = '';
  sortOrder = 0;

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
    this.dataService.getAverages(this.page, this.sortBy, this.sortOrder)
      .subscribe((averages: Averages[]) => {
        this.averages = averages;
        this.dataSource = new MatTableDataSource(this.averages);
        this.isPageLoading = false;
      });

    this.dataService.getCountries()
      .subscribe((result: Country[]) => {
        this.countries = result;
      });

    this.dataService.getCities()
      .subscribe((result: City[]) => {
        this.cities = result;
      });
  }

  sortData(event){

    this.page = 0;
    this.sortBy = event.active;
    this.sortOrder = event.direction === 'asc' ? 0 : 1;
    this.isPageLoading = true;

    this.dataService.getAverages(this.page, this.sortBy, this.sortOrder)
    .subscribe((result: Averages[]) => {
      if(result && result.length){
        this.dataSource.data = result;
      }
      this.isPageLoading = false;
    });
  }

  selectCountry(event: Event){
    this.citiesFiltered = this.cities.filter(city => city.country === this.selectedCountry.code);
    console.log(this.citiesFiltered, this.selectedCountry);
  }

  onTableScroll(e) {
    const tableViewHeight = e.target.offsetHeight // viewport: ~500px
    const tableScrollHeight = e.target.scrollHeight // length of all table
    const scrollLocation = e.target.scrollTop; // how far user scrolled

    // If the user has scrolled within 500px of the bottom, add more data
    const buffer = 500;
    const limit = tableScrollHeight - tableViewHeight - buffer;

    if (scrollLocation > limit && !this.isPageLoading && !this.isEOL) {
      this.isPageLoading = true;
      this.page++;
      this.dataService.getAverages(this.page, this.sortBy, this.sortOrder)
        .subscribe((result: Averages[]) => {
          if(result && result.length){
            this.dataSource.data = this.dataSource.data.concat(result);
          } else {
             this.isEOL = true;
          }
          this.isPageLoading = false;
        });
    }
  }
}
