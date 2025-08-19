import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Garage } from '../models/garage.model';  
import { Vehicle } from '../models/vehicle.model';
import { Appointment } from '../models/appointment.model';
import { Rating } from '../models/rating.model';

@Injectable({
  providedIn: 'root'
})
export class GarageService {

  private apiUrl = 'http://localhost:8080/garage-service/garage';   

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token') || ''; // JWT token from sessionStorage
    console.log('JWT Token:', token); // Debugging: Print token in console

    return new HttpHeaders({
      'X-Authenticated-User': sessionStorage.getItem('username') || '',
      'X-Authenticated-Role': sessionStorage.getItem('role') || '',
      'Authorization': `Bearer ${token}`
    });
  }


 // ✅ method to fetch all garage
  getGarages(): Observable<Garage[]> {
    const token = sessionStorage.getItem('token');

    if (!token) {
      console.error('No token found!');
      return new Observable<Garage[]>(); 
    }

    console.log('JWT Token:', token);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<Garage[]>(this.apiUrl, { headers });
  }

  // ✅ New method to fetch a garage by ID
  getGarageById(id: number): Observable<Garage> {
    const token = sessionStorage.getItem('token');

    if (!token) {
      console.error('No token found!');
      return new Observable<Garage>();
    }

    console.log(`Fetching Garage with ID: ${id}`);
    console.log('JWT Token:', token);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.get<Garage>(`${this.apiUrl}/${id}`, { headers });
  }

  
// New method to book an appointment by feing client
 createAppointment(appointment: Appointment): Observable<Appointment> {
    console.log('Creating appointment:', appointment);
    return this.http.post<Appointment>(`${this.apiUrl}/bookAppointment`, appointment, { headers: this.getHeaders() });
  }


//  New method to fetch vehicle by feing client
  getMyVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.apiUrl}/my-vehicle`, { headers: this.getHeaders()  });
  }

  //Ratings//

  addRating(rating: Rating, token: string, role: string, username: string): Observable<Rating> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'X-Authenticated-Role': role,
      'X-Authenticated-User': username,
    });
    return this.http.post<Rating>(`${this.apiUrl}/addRating`, rating, { headers });
  }

  // Add garage (only for admin)
  addGarage(garage: Garage): Observable<Garage> {
    console.log('Adding new garage:', garage);
    return this.http.post<Garage>(`${this.apiUrl}`, garage, { headers: this.getHeaders() });
  }
  
}
