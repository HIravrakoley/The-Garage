import { Component } from '@angular/core';
import { VehicleService } from '../../services/vehicle.service';
import { NgFor,NgIf} from '@angular/common';
import { Vehicle } from '../../models/vehicle.model';
import { FormBuilder, Validators } from '@angular/forms';
import { AppointmentService } from '../../services/appointment.service';
import { GarageService } from '../../services/garage.service';
import { FormGroup } from '@angular/forms';
import { Appointment } from '../../models/appointment.model';
import { ReactiveFormsModule } from '@angular/forms'; 
import { NavbarComponent } from '../../navbar/navbar.component';
import { FooterComponent } from '../../footer/footer.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-appointment',
  imports: [NgFor,NgIf,ReactiveFormsModule,NavbarComponent,FooterComponent,FormsModule],
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.css'
})
export class AppointmentComponent {
  appointmentForm: FormGroup;
  garageId: number = 0;
  garageName: string = '';
  vehicleId: number =0;
  vehicles: Vehicle[] = [];
  isLoading: boolean = false;
  errorMessage: string = '';
  odometer: number=0;

  constructor(
    private vehicleService:VehicleService,
    private fb: FormBuilder,
    private appointmentService: AppointmentService,
    private garageService:GarageService
  ) { this.appointmentForm = this.fb.group({
    date: ['', Validators.required],  // Date selection
    time: ['', Validators.required]   // Time selection
  });}
  
  ngOnInit(): void {
    this.garageId = Number(sessionStorage.getItem('garageId'));
    console.log("id =", this.garageId);
    this.fetchGarageDetails();
    this.fetchVehicles();
  }

  // ✅ Fetch garage details using garageId
  fetchGarageDetails(): void {
    if (this.garageId) {
      this.garageService.getGarageById(this.garageId).subscribe({
        next: (garage) => {
          this.garageName = garage.name;
          console.log('Fetched garage:', garage);
        },
        error: (error) => {
          console.error('Error fetching garage details:', error);
          this.errorMessage = 'Failed to load garage details.';
        }
      });
    }
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

  selectVehicle(id: number | undefined) {
    if (id !== undefined) {
      this.vehicleId = id;
      console.log('Selected Vehicle ID:', this.vehicleId);
    } else {
      console.error('Vehicle ID is undefined!');
    }
  }
// Book an appoitment

 onSubmit(): void {
   if (this.appointmentForm.valid) {
    const { date, time } = this.appointmentForm.value;
    
   // Convert to LocalDateTime format (YYYY-MM-DDTHH:mm:ss)
   const formattedAppointmentTime = `${date}T${time}:00`;

    const appointment: Appointment = {
       garageId: this.garageId,
      vehicleId: this.vehicleId,
      odometer: this.odometer,
      appointmentTime: formattedAppointmentTime
    };
     console.log('Booking Appointment:', appointment);
     //GARAGE-SERVICE = FEING CLIENT
     //APPOINTMENT-SERVICE = DIRECT
    this.garageService.createAppointment(appointment).subscribe({
       next: () => {
       console.log('Appointment booked successfully');
      this.appointmentForm.reset();
       },
       error: (err) => console.error('Error booking appointment:', err)
     });
  }
 }
}
