import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserStoreService {

  constructor() { }

  // ✅ Save JWT token
  saveToken(token: string): void {
    sessionStorage.setItem('token', token);
  }

  // ✅ Retrieve JWT token
  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  // ✅ Save username in sessionStorage
  setUsername(username: string): void {
    sessionStorage.setItem('username', username);
  }

  // ✅ Retrieve username from sessionStorage
  getUsername(): string {
    return sessionStorage.getItem('username') || '';
  }

  // ✅ Clear all session storage data
  clear(): void {
    sessionStorage.clear();
  }

  // ✅ Save role in sessionStorage
  setRole(role: string): void {
    sessionStorage.setItem('role', role);
  }

  // ✅ Retrieve role from sessionStorage
  getRole(): string {
    return sessionStorage.getItem('role') || '';
  }


}
