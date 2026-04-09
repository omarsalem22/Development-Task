import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination } from '../models/destination.model';

@Injectable({ providedIn: 'root' })
export class WishlistService {
  private API = '/api/wishlist';

  constructor(private http: HttpClient) {}

  getWishlist(): Observable<Destination[]> {
    return this.http.get<Destination[]>(this.API);
  }

  toggle(destinationId: number): Observable<{ wishlisted: boolean }> {
    return this.http.post<{ wishlisted: boolean }>(`${this.API}/${destinationId}`, {});
  }
}
