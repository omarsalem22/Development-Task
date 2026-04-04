import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  form: FormGroup;
  loading = false;
  error = '';
  showPass = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    this.error = '';

    this.auth.login(this.form.value).subscribe({
      next: (res) => {
        const dest = res.role === 'ADMIN' ? '/admin' : '/destinations';
        this.router.navigate([dest]);
      },
      error: (err) => {
        this.loading = false;
        this.error = err.error?.message ?? 'Invalid email or password';
      },
    });
  }

  get email() {
    return this.form.get('email')!;
  }
  get password() {
    return this.form.get('password')!;
  }
}
