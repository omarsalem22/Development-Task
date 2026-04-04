// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guards/auth-guard';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { AdminDashboard } from './components/admin-dashboard/admin-dashboard';
// import { RegisterComponent } from
// import { AdminDashboardComponent } from
// import { DestinationListComponent } from

export const routes: Routes = [
  { path: 'login', component: Login },
    { path: 'register', component: Register }, 

  // canActivate runs AuthGuard before loading this page
  // data.role tells the guard which role is required

{
    path: 'admin',
    component: AdminDashboard,
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' }   
  },
  //   {
  //     path: 'destinations',
  //     component: DestinationListComponent,
  //     canActivate: [AuthGuard],
  //     // no data.role = any logged-in user can access
  //   },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
