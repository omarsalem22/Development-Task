import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth-guard';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { AdminDashboard } from './components/admin-dashboard/admin-dashboard';
import { UserDashboard } from './components/user-dashboard/user-dashboard';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'admin', component: AdminDashboard, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'destinations', component: UserDashboard, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
