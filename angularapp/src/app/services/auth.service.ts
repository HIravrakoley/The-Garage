import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth-service/auth';  // API Gateway URL with proper routing

  constructor(private http: HttpClient) {}

  /**
   * Register a new user
   * @param user - { username, password, role }
   * @returns Observable with response
   */
  register(user: any): Observable<any> {
    console.log('Sending register request:', user);

    return this.http.post(`${this.apiUrl}/register`, user, { responseType: 'text' })
      .pipe(
        tap((response) => console.log('Register successful:', response)),
        catchError((error: HttpErrorResponse) => {
          console.error('Register error:', error);
          return throwError(() => new Error(`Register failed: ${error.message}`));
        })
      );
  }

  /**
   * Login a user
   * @param credentials 
   * @returns 
   */
  login(credentials: any): Observable<string> {
    console.log('Sending login request:', credentials);

    // Check if location data exists in sessionStorage
    const latitude = sessionStorage.getItem('latitude');
    const longitude = sessionStorage.getItem('longitude');
    
    // Add location data to credentials if available
    const enhancedCredentials = {
      ...credentials
    };
    
    if (latitude && longitude) {
      enhancedCredentials.latitude = parseFloat(latitude);
      enhancedCredentials.longitude = parseFloat(longitude);
      console.log('Adding location data to login request:', enhancedCredentials);
    } else {
      console.log('No location data available for login request');
    }

    return this.http.post(`${this.apiUrl}/login`, enhancedCredentials, { responseType: 'text' })
      .pipe(
        tap((token) => {
          console.log('Login successful, token:', token);
          sessionStorage.setItem('token', token);
        }),
        catchError((error: HttpErrorResponse) => {
          console.error('Login error:', error);
          return throwError(() => new Error(`Login failed: ${error.message}`));
        })
      );
  }

  
  isLoggedIn(): boolean {
    const token = sessionStorage.getItem('token');
    console.log('User logged in:', !!token);
    return !!token;
  }

 
  logout(): void {
    console.log('Logging out...');
    sessionStorage.removeItem('token');
  }

  /**
   * Get the role of the logged-in user from the JWT token
   * @returns The user role or null if not logged in
   */
  getUserRole(): string | null {
    const token = sessionStorage.getItem('token');
    if (!token) {
      return null;
    }
    
    try {
   
      const payload = token.split('.')[1];

      const decodedPayload = JSON.parse(atob(payload));
      const roles = decodedPayload.roles;
      if (!roles || roles.length === 0) {
        return null;
      }
      if (typeof roles[0] === 'object' && roles[0].authority) {
        return roles[0].authority;
      }
      return Array.isArray(roles) ? roles[0] : roles;
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }

  /**
   * Format the role string for display
   * @param role The role string (e.g., "ROLE_CUSTOMER")
   * @returns Formatted role string (e.g., "Customer")
   */
  formatRoleForDisplay(role: string | null): string {
    if (!role) {
      return 'Guest';
    }
    
    // Check if it starts with "ROLE_" and remove it
    if (role.startsWith('ROLE_')) {
      // Convert "ROLE_CUSTOMER" to "Customer"
      return role.substring(5).charAt(0) + role.substring(6).toLowerCase();
    }
    
    return role.charAt(0).toUpperCase() + role.substring(1).toLowerCase();
  }

  printUserRole(): void {
    const role = this.getUserRole();
    const formattedRole = this.formatRoleForDisplay(role);
    if (role) {
      console.log('Current user role:', formattedRole);
    } else {
      console.log('No user is currently logged in or role is not available');
    }
  }
}
