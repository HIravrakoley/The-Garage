import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [RouterModule,NavbarComponent,FooterComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  carImages: string[] = [
    'car4.jpg',
    'car2.jpg',
    'car3.jpg',
    'car1.jpg'
  ];
  currentIndex: number = 0;
  latitude: number | null = null;
  longitude: number | null = null;
  locationError: string = '';

  constructor(private authService: AuthService) {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.carImages.length;
    }, 3000); 
  }

  ngOnInit() {
    this.authService.printUserRole();
    this.getUserLocation();
  }

  getUserLocation(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.latitude = position.coords.latitude;
          this.longitude = position.coords.longitude;
          
          // Store location in sessionStorage for auth service to use
          sessionStorage.setItem('latitude', this.latitude.toString());
          sessionStorage.setItem('longitude', this.longitude.toString());
          
          console.log('User Location:', this.latitude,',', this.longitude);
        },
        (error) => {
          this.locationError = 'Unable to retrieve location. Please allow location access.';
          console.error('Geolocation error:', error);
        }
      );
    } else {
      this.locationError = 'Geolocation is not supported by this browser.';
    }
  }
}
