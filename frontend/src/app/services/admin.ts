// src/app/services/admin.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination, CountryApi } from '../models/destination.model';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private API = '/api/admin';

  constructor(private http: HttpClient) {}

  // fetch preview from REST Countries via your backend
  fetchFromApi(): Observable<CountryApi[]> {
    return this.http.get<CountryApi[]>(`${this.API}/fetch`);
  }

  getSaved(): Observable<Destination[]> {
    return this.http.get<Destination[]>(`${this.API}/destinations`);
  }

  // add single country
  addOne(country: CountryApi): Observable<Destination> {
    return this.http.post<Destination>(`${this.API}/destinations`, country);
  }

  // bulk add selected countries
  bulkAdd(countries: CountryApi[]): Observable<{ saved: number }> {
    return this.http.post<{ saved: number }>(`${this.API}/destinations/bulk`, countries, {});
  }

  // remove a saved destination
  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/destinations/${id}`);
  }
}
