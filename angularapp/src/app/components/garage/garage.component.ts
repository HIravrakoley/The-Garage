import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { GarageService } from '../../services/garage.service';
import { Garage } from '../../models/garage.model';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Router } from '@angular/router';
import { Vehicle } from '../../models/vehicle.model';
import { FooterComponent } from '../../footer/footer.component';
import { ViewportScroller } from '@angular/common';

@Component({
  selector: 'app-garage',
  standalone: true, 
  templateUrl: './garage.component.html',
  styleUrls: ['./garage.component.css'],
  imports: [CommonModule, NgFor, NgIf, NavbarComponent,FooterComponent],
})
export class GarageComponent implements OnInit {

  videos = [
    {
      src: 'bike2.mp4',
      title: 'Powerful Performance',
      description: 'Engineered for speed and precision.',
    },
    {
      src: 'car2.mp4',
      title: 'Luxury & Comfort',
      description: 'Refined care for elite vehicles.',
    },
    {
      src: 'engine.mp4',
      title: 'Next-Gen Engineering',
      description: 'Smart tech. Flawless service.',
    },
  ];
  
  
  vehicles: Vehicle[] = [];
  garages: Garage[] = [];
  selectedGarage: Garage | null = null; 
  selectedGarageId: number = 0; 
  loading = true;
  error: string = '';


  constructor(private garageService: GarageService,
    private router: Router,
    private viewportScroller: ViewportScroller
  ) {}

  ngOnInit(): void {
    this.fetchGarages();
  }

  fetchGarages(): void {
    this.loading = true; 
    this.garageService.getGarages().subscribe({
      next: (data) => {
        this.garages = data;
        this.loading = false;
        this.error = '';
        console.log('Fetched garages:', this.garages);
      },
      error: (err) => {
        this.error = 'Failed to load garages';
        this.loading = false;
        console.error('Error fetching garages:', err);
      },
    });
  }

  fetchGarageById(id: number): void {
    this.garageService.getGarageById(id).subscribe({
      next: (garage) => {
        this.selectedGarageId= id;
        this.selectedGarage = garage;
        console.log('Selected Garage:', this.selectedGarage);
        console.log('Selected Garage ID', this.selectedGarageId);
       sessionStorage.setItem('garageId',id.toString());
        this.router.navigate(['/appointment']);
        this.viewportScroller.scrollToPosition([0, 0])
      },
      error: (err) => {
        this.error = 'Failed to fetch garage details';
        console.error(' Error fetching garage by ID:', err);
      },
    });
  }

}
