import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  private apiUrl = '/api'; // This works because of proxy

  constructor(private http: HttpClient) {}

  getHello(): Observable<any> {
    return this.http.get(`${this.apiUrl}/hello`);
  }

  getHelloWithName(name: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/hello/${name}`);
  }
}
