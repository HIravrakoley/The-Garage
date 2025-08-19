import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { NgFor,NgIf, DatePipe } from '@angular/common';
import { NavbarComponent } from "../../navbar/navbar.component";
import { GarageService } from '../../services/garage.service';
import { VehicleService } from '../../services/vehicle.service';
import { AppointmentWithNames } from '../../models/appointment-with-names.model';
import { UserStoreService } from '../../services/user-store.service';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [NgFor, DatePipe, NavbarComponent, NgIf],
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit, OnDestroy {
  appointments: AppointmentWithNames[] = [];

  constructor(private appointmentService: AppointmentService,
    private garageService: GarageService,
    private vehicleService: VehicleService,
    private userStoreService: UserStoreService
  ) {}

  ngOnInit(): void {
    this.fetchAppointmentsWithNames();
    document.body.classList.add('dark-theme');
  }

  ngOnDestroy(): void {
    document.body.classList.remove('dark-theme');
  }

  fetchAppointmentsWithNames(): void {
    this.appointmentService.getAppointments().subscribe({
      next: (appointments) => {
        this.appointments = []; 
        appointments.forEach((appointment) => {
          this.vehicleService.getVehicleById(appointment.vehicleId).subscribe(vehicle => {
            this.garageService.getGarageById(appointment.garageId).subscribe(garage => {
              this.appointments.push({
                appointmentTime: appointment.appointmentTime,
                vehicleId: appointment.vehicleId,
                garageId: appointment.garageId,
                vehicleName: `${vehicle.companyName} ${vehicle.modelName}`,
                garageName: garage.name,
                odometer: appointment.odometer
              });
            });
          });
        });
      },
      error: (err) => {
        console.error('Error fetching appointments:', err);
      }
    });
  }
}
