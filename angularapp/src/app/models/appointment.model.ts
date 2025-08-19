export interface Appointment {
    id?: number;
  garageId: number;
  vehicleId: number;
  odometer: number;
  appointmentTime: string;
  username?: string;  
}
