import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../models/appointment.model';


@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private baseUrl = 'http://localhost:8080/appointment-service/appointment';

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

  getAppointments(): Observable<Appointment[]> {
    console.log('Fetching appointments...');
    return this.http.get<Appointment[]>(this.baseUrl, { headers: this.getHeaders() });
  }

  createAppointment(appointment: Appointment): Observable<Appointment> {
    console.log('Creating appointment:', appointment);
    return this.http.post<Appointment>(`${this.baseUrl}/add`, appointment, { headers: this.getHeaders() });
  }

  getAllAppointments(): Observable<Appointment[]> {
    console.log('Fetching all appointments (admin only)...');
    return this.http.get<Appointment[]>(`${this.baseUrl}/all`, {headers: this.getHeaders()});
  }

  deleteAppointment(id: number): Observable<void> {
    console.log(`Deleting appointment with ID: ${id}`);
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<void>(`${this.baseUrl}/delete`, {
      headers: this.getHeaders(),
      params
    });
  }
  

}
