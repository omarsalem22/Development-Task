import { CanActivateFn } from '@angular/router';

import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth-service'; 

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
   
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }

    // Route has a required role (set in app-routing.module.ts via data: {role: 'ROLE_ADMIN'})
    const requiredRole = route.data['role'] as string;
    if (requiredRole && this.auth.getRole() !== requiredRole) {
      // Logged in but wrong role → send to destinations
      this.router.navigate(['/destinations']);
      return false;
    }

    return true; // all good, load the page
  }
}
