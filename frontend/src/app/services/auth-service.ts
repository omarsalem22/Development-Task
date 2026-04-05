import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // Your Spring Boot base URL
  private API = '/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  login(req: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API}/login`, req)
      .pipe(tap((res) => this.saveToStorage(res)));
  }

  register(req: RegisterRequest): Observable<string> {
    return this.http.post(`${this.API}/register`, req, {
      responseType: 'text',
    });
  }

  private saveToStorage(res: AuthResponse): void {
    console.log(res);

    localStorage.setItem('token', res.token);
    localStorage.setItem('role', res.role);
    localStorage.setItem('username', res.username);
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
  getRole(): string | null {
    return localStorage.getItem('role');
  }
  getUsername(): string | null {
    return localStorage.getItem('username');
  }
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
  isAdmin(): boolean {
    return this.getRole() === 'ROLE_ADMIN';
  }
}
