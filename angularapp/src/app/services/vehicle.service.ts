import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle } from '../models/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private baseUrl = "http://localhost:8080/vehicle-service/vehicle";

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token'); // Fetch token from sessionStorage
    console.log('Token:', token); // Debugging: Log the token
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // ✅ Fetch vehicles for the current user
  getVehiclesByOwner(): Observable<Vehicle[]> {
    const headers = this.getAuthHeaders();
    console.log('Sending Headers:', headers.get('Authorization')); // Debugging
    return this.http.get<Vehicle[]>(`${this.baseUrl}/my-vehicle`, { headers });
  }

  // ✅ Add vehicle
  addVehicle(vehicle: Vehicle): Observable<Vehicle> {
    const headers = this.getAuthHeaders();
    console.log('Sending Headers:', headers.get('Authorization')); // Debugging
    return this.http.post<Vehicle>(this.baseUrl, vehicle, { headers });
  }

  // ✅ Update vehicle
  updateVehicle(id: number, vehicle: Vehicle): Observable<Vehicle> {
    const headers = this.getAuthHeaders();
    console.log('Sending Headers:', headers.get('Authorization')); // Debugging
    return this.http.put<Vehicle>(`${this.baseUrl}/${id}`, vehicle, { headers });
  }

  // ✅ Delete vehicle
  deleteVehicle(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    console.log('Sending Headers:', headers.get('Authorization')); // Debugging
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }

  getVehicleById(id: number): Observable<Vehicle> {
      const token = sessionStorage.getItem('token');
  
      if (!token) {
        console.error('No token found!');
        return new Observable<Vehicle>();
      }
      console.log(`Fetching Vehicle with ID: ${id}`);
      console.log('JWT Token:', token);
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<Vehicle>(`${this.baseUrl}/${id}`, { headers });
    }
  
  
}
