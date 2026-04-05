// src/app/user/dashboard/user-dashboard.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DestinationService } from '../../services/destination';
import { WishlistService } from '../../services/wishlist-service';
import { Destination } from '../../models/destination.model';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-dashboard.html',
  styleUrl: './user-dashboard.scss',
})
export class UserDashboard implements OnInit {
  destinations: Destination[] = [];
  wishlistIds: Set<number> = new Set();

  // pagination
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalItems = 0;

  // search
  searchQuery = '';
  private searchSubject = new Subject<string>();

  // ui state
  loading = false;
  username = '';

  constructor(
    private destinationService: DestinationService,
    private wishlistService: WishlistService,
    private auth: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.username = this.auth.getUsername() ?? '';
    this.loadDestinations();
    this.loadWishlist();

    // debounce search — wait 300ms after user stops typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((query) => {
      this.searchQuery = query;
      this.currentPage = 0;
      this.loadDestinations();
    });
  }

  loadDestinations(): void {
    this.loading = true;
    this.destinationService.getAll(this.currentPage, this.pageSize, this.searchQuery).subscribe({
      next: (res) => {
        this.destinations = res.content;
        this.totalPages = res.totalPages;
        this.totalItems = res.totalElements;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  loadWishlist(): void {
    this.wishlistService.getWishlist().subscribe({
      next: (list) => {
        this.wishlistIds = new Set(list.map((d) => d.id));
      },
    });
  }

  onSearch(query: string): void {
    this.searchSubject.next(query);
  }

  toggleWishlist(destination: Destination): void {
    this.wishlistService.toggle(destination.id).subscribe({
      next: (res) => {
        if (res.wishlisted) {
          this.wishlistIds.add(destination.id);
        } else {
          this.wishlistIds.delete(destination.id);
        }
      },
    });
  }

  isWishlisted(id: number): boolean {
    return this.wishlistIds.has(id);
  }

  // pagination
  goToPage(page: number): void {
    if (page < 0 || page >= this.totalPages) return;
    this.currentPage = page;
    this.loadDestinations();
  }

  get pages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  formatPopulation(pop: number): string {
    if (pop >= 1_000_000) return (pop / 1_000_000).toFixed(1) + 'M';
    if (pop >= 1_000) return (pop / 1_000).toFixed(0) + 'K';
    return pop.toString();
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
