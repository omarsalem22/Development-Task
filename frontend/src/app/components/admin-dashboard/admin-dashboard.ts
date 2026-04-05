// src/app/admin/dashboard/admin-dashboard.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../services/admin';
import { CountryApi, Destination } from '../../models/destination.model';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.scss',
})
export class AdminDashboard implements OnInit {
  // API preview list
  apiCountries: CountryApi[] = [];
  selectedCountries: Set<number> = new Set(); // tracks checked rows by index

  // saved destinations
  savedDestinations: Destination[] = [];

  // UI state
  loadingFetch = false;
  loadingBulk = false;
  loadingSaved = false;
  fetchError = '';
  successMsg = '';

  constructor(
    private adminService: AdminService,
    private auth: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadSaved();
  }

  // 1. fetch from REST Countries
  fetchFromApi(): void {
    this.loadingFetch = true;
    this.fetchError = '';
    this.apiCountries = [];

    this.adminService.fetchFromApi().subscribe({
      next: (data) => {
        this.apiCountries = data;
        this.loadingFetch = false;
      },
      error: () => {
        this.fetchError = 'Failed to fetch from API';
        this.loadingFetch = false;
      },
    });
  }

  // 2. toggle checkbox selection
  toggleSelect(index: number): void {
    if (this.selectedCountries.has(index)) {
      this.selectedCountries.delete(index);
    } else {
      this.selectedCountries.add(index);
    }
  }

  isSelected(index: number): boolean {
    return this.selectedCountries.has(index);
  }

  selectAll(): void {
    this.apiCountries.forEach((_, i) => this.selectedCountries.add(i));
  }

  clearSelection(): void {
    this.selectedCountries.clear();
  }

  // 3. add single country
  addOne(country: CountryApi): void {
    this.adminService.addOne(country).subscribe({
      next: () => {
        this.successMsg = `${country.name.common} added successfully`;
        this.loadSaved();
        setTimeout(() => (this.successMsg = ''), 3000);
      },
      error: (err) => alert(err.error?.message ?? 'Failed to add'),
    });
  }

  // 4. bulk add selected
  bulkAdd(): void {
    if (this.selectedCountries.size === 0) return;

    const selected = Array.from(this.selectedCountries).map((i) => this.apiCountries[i]);

    this.loadingBulk = true;
    this.adminService.bulkAdd(selected).subscribe({
      next: (res) => {
        this.successMsg = `${res.saved} destinations added`;
        this.loadingBulk = false;
        this.selectedCountries.clear();
        this.loadSaved();
        setTimeout(() => (this.successMsg = ''), 3000);
      },
      error: () => {
        this.loadingBulk = false;
        alert('Bulk add failed');
      },
    });
  }

  // 5. load saved destinations
  loadSaved(): void {
    this.loadingSaved = true;
    this.adminService.getSaved().subscribe({
      next: (data) => {
        this.savedDestinations = data;
        this.loadingSaved = false;
      },
      error: () => (this.loadingSaved = false),
    });
  }

  // 6. remove destination
  remove(id: number): void {
    if (!confirm('Remove this destination?')) return;
    this.adminService.remove(id).subscribe({
      next: () => {
        this.successMsg = 'Destination removed';
        this.savedDestinations = this.savedDestinations.filter((d) => d.id !== id);
        setTimeout(() => (this.successMsg = ''), 3000);
      },
    });
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  get selectedCount(): number {
    return this.selectedCountries.size;
  }
}
