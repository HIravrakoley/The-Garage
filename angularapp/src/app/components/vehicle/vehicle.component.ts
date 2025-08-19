import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from '../../models/vehicle.model';
import { CommonModule } from '@angular/common';  // Required for *ngIf and *ngFor
import { NavbarComponent } from '../../navbar/navbar.component';
import { FooterComponent } from '../../footer/footer.component';
import { GarageService } from '../../services/garage.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-vehicle',
  standalone: true,
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css'],
  imports: [CommonModule, ReactiveFormsModule,NavbarComponent,FooterComponent, NgFor]   // ✅ Import ReactiveFormsModule and CommonModule
})
export class VehicleComponent implements OnInit {

  videos = [
    {
      src: 'bike2.mp4',
      title: 'Powerful Performance',
      description: 'Experience cutting-edge speed and efficiency.',
    },
    {
      src: 'car2.mp4',
      title: 'Luxury & Comfort',
      description: 'Designed for elegance and comfort on every ride.',
    },
    {
      src: 'engine.mp4',
      title: 'Next-Gen Engineering',
      description: 'Advanced technology for a seamless driving experience.',
    },
  ];

  vehicleForm: FormGroup;
  vehicles: Vehicle[] = [];
  selectedVehicle: Vehicle | null = null;
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder, 
    private vehicleService: VehicleService,
    private garageService:GarageService
  ) {
    // ✅ Initialize the form
    this.vehicleForm = this.fb.group({
      registration: ['', Validators.required],
      companyName: ['', Validators.required],
      modelName: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchVehicleFeing();
  }

  // ✅ Fetch the vehicles belonging to the current user
  fetchVehicles(): void {
    this.isLoading = true;
    this.vehicleService.getVehiclesByOwner().subscribe({
      next: (vehicles) => {
        this.vehicles = vehicles;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load vehicles. Please try again later.';
        console.error('Error fetching vehicles:', error);
        this.isLoading = false;
      }
    });
  }
  //Fetch the vehicle from garage service using feing client 
  fetchVehicleFeing(): void{
    this.isLoading = true;
    this.garageService.getMyVehicles().subscribe({
      next: (vehicles) => {
        this.vehicles = vehicles;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load vehicles. Please try again later.';
        console.error('Error fetching vehicles:', error);
        this.isLoading = false;
      }
    });

  }

  // ✅ Add or update vehicle
  submitForm(): void {
    if (this.vehicleForm.invalid) return;

    const vehicle: Vehicle = this.vehicleForm.value;

    if (this.selectedVehicle) {
      // Update existing vehicle
      this.vehicleService.updateVehicle(this.selectedVehicle.id!, vehicle).subscribe({
        next: () => {
          this.clearForm();
          this.fetchVehicles();
        },
        error: (error) => {
          this.errorMessage = 'Failed to update vehicle.';
          console.error('Error updating vehicle:', error);
        }
      });
    } else {
      // Add new vehicle
      this.vehicleService.addVehicle(vehicle).subscribe({
        next: () => {
          this.clearForm();
          this.fetchVehicles();
        },
        error: (error) => {
          this.errorMessage = 'Failed to add vehicle.';
          console.error('Error adding vehicle:', error);
        }
      });
    }
  }

  // ✅ Edit vehicle
  editVehicle(vehicle: Vehicle): void {
    this.selectedVehicle = vehicle;
    this.vehicleForm.patchValue(vehicle);
  }

  // ✅ Delete vehicle
  deleteVehicle(id: number): void {
    if (confirm('Are you sure you want to delete this vehicle?')) {
      this.vehicleService.deleteVehicle(id).subscribe({
        next: () => {
          this.fetchVehicles();
        },
        error: (error) => {
          this.errorMessage = 'Failed to delete vehicle.';
          console.error('Error deleting vehicle:', error);
        }
      });
    }
  }

  // ✅ Clear form and reset selection
  clearForm(): void {
    this.vehicleForm.reset();
    this.selectedVehicle = null;
  }



}
