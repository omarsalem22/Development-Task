// src/app/services/admin.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination, CountryApi } from '../models/destination.model';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private API = '/api/admin';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    if (!token) {
      throw new Error('No token found! Please login first.');
    }

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // fetch preview from REST Countries via your backend
  fetchFromApi(): Observable<CountryApi[]> {
    return this.http.get<CountryApi[]>(`${this.API}/fetch`, {
      headers: this.getAuthHeaders(),
    });
  }

  // get destinations already saved in DB
  getSaved(): Observable<Destination[]> {
    return this.http.get<Destination[]>(`${this.API}/destinations`, {
      headers: this.getAuthHeaders(),
    });
  }

  // add single country
  addOne(country: CountryApi): Observable<Destination> {
    return this.http.post<Destination>(`${this.API}/destinations`, country, {
      headers: this.getAuthHeaders(),
    });
  }

  // bulk add selected countries
  bulkAdd(countries: CountryApi[]): Observable<{ saved: number }> {
    return this.http.post<{ saved: number }>(`${this.API}/destinations/bulk`, countries, {
      headers: this.getAuthHeaders(),
    });
  }

  // remove a saved destination
  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/destinations/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }
}
