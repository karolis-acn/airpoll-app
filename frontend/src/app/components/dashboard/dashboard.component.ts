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
  sortOrder = '';

  // filters
  countries: Country[];
  cities: City[];
  filterCountry: Country;
  filterCity: City;

  // table
  averages: Averages[];
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['location', 'city', 'country', 'average', 'parameter', 'longitude', 'latitude', 'date'];

  constructor(private dataService: DataService) {}

  // Response to loaded data
  updateAveragesSubscribe(): (averages: Averages[]) => void{
    this.page = 0;
    this.isEOL = false;

    return (averages: Averages[]) => {
        this.averages = averages;
        this.dataSource = new MatTableDataSource(this.averages);
        this.isPageLoading = false;
    }
  }

  // Load average pollution data
  getAverages(subscribe: (averages: Averages[]) => void): void{
    this.dataService.getAverages(this.page, this.sortBy, this.sortOrder, this.filterCountry?.code, this.filterCity?.name)
      .subscribe(subscribe);
  }

  ngOnInit() {
    this.getAverages(this.updateAveragesSubscribe());

    this.dataService.getCountries()
      .subscribe((result: Country[]) => {
        this.countries = result.sort((a,b) => a.name.localeCompare(b.name));
      });
  }

  // Load new sorted data
  sortData(event): void{
    this.page = 0;
    this.sortBy = event.direction ? event.active : '';
    this.sortOrder = event.direction;
    this.isPageLoading = true;

    this.getAverages(this.updateAveragesSubscribe());
  }

  // Select country and load cities list
  selectCountry(selectedCountry?: string): void{
    delete this.filterCity;
    delete this.cities;

    if(selectedCountry){
      this.filterCountry = this.countries?.find(country => country.code === selectedCountry);
    }

    if(this.filterCountry){
      this.dataService.getCities(this.filterCountry.code)
        .subscribe((result: City[]) => {
          this.cities = result.sort((a,b) => a.name.localeCompare(b.name));
        });
    }

    this.getAverages(this.updateAveragesSubscribe());
  }

  // Select city and load new data
  selectCity(): void{
    this.getAverages(this.updateAveragesSubscribe());
  }

  // Get country name by country code
  getCountryName(findCountry: string): string{
    return this.countries?.find(country => country.code === findCountry)?.name || findCountry;
  }

  // Mark average measurement by color (UK standarts)
  getAverageColor(value: number){
    if(value <= 11){
      return '#cfc';
    } else if(value <= 23){
      return '#6f6';
    } else if(value <= 35){
      return '#0f0';
    } else if(value <= 41){
      return '#9f0';
    } else if(value <= 47){
      return '#ff0';
    } else if(value <= 53){
      return '#fc0';
    } else if(value <= 58){
      return '#f60';
    } else if(value <= 64){
      return '#f30';
    } else if(value <= 70){
      return '#f00';
    } else {
      return '#f06';
    }
  }

  // Open map by coordinates to show where it is
  openMap(longitude: string, latitude: string){
    window.open(`https://www.openstreetmap.org/?mlat=${latitude}&mlon=${longitude}&zoom=12`, '_blank');
  }

  // Triggered when table is scrolled to fill more data once bottom of list is reached
  onTableScroll(e) {
    const tableViewHeight = e.target.offsetHeight
    const tableScrollHeight = e.target.scrollHeight
    const scrollLocation = e.target.scrollTop;

    // If the user has scrolled within 500px of the bottom, add more data
    const buffer = 500;
    const limit = tableScrollHeight - tableViewHeight - buffer;

    if (scrollLocation > limit && !this.isPageLoading && !this.isEOL) {
      this.isPageLoading = true;
      this.page++;
      this.getAverages((result: Averages[]) => {
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
