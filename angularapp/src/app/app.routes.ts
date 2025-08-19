import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { authGuard } from './services/auth.guard';  
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { GarageComponent } from './components/garage/garage.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { BookingComponent } from './components/booking/booking.component';

export const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'dashboard',
    canActivate: [authGuard],   
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'vehicle',
    canActivate: [authGuard], 
    component: VehicleComponent
  },
  {
    path: 'garage',
    canActivate: [authGuard],  
    component: GarageComponent
  },
  {
    path: 'appointment',
    canActivate: [authGuard],  
    component: AppointmentComponent
  },
  {
    path: 'booking',
    canActivate: [authGuard],  
    component: BookingComponent
  },
  { path: '**', redirectTo: '' }
];
