import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination } from '../models/destination.model';

@Injectable({ providedIn: 'root' })
export class WishlistService {
  private API = 'http://localhost:8080/api/wishlist';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getWishlist(): Observable<Destination[]> {
    return this.http.get<Destination[]>(this.API, {
      headers: this.getAuthHeaders(),
    });
  }

  toggle(destinationId: number): Observable<{ wishlisted: boolean }> {
    return this.http.post<{ wishlisted: boolean }>(
      `${this.API}/${destinationId}`,
      {},
      { headers: this.getAuthHeaders() },
    );
  }
}
