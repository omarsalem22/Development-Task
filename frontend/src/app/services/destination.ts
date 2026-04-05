import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination } from '../models/destination.model';

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class DestinationService {
  private API = '/api/destinations';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAll(page = 0, size = 10, search = ''): Observable<PageResponse<Destination>> {
    const params = new HttpParams().set('page', page).set('size', size).set('search', search);

    return this.http.get<PageResponse<Destination>>(this.API, {
      headers: this.getAuthHeaders(),
      params,
    });
  }

  getOne(id: number): Observable<Destination> {
    return this.http.get<Destination>(`${this.API}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }
}
