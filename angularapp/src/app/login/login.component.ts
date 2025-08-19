import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';  // ✅ Import NgIf
import { AuthService } from '../services/auth.service';
import { UserStoreService } from '../services/user-store.service';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, NgIf,RouterLink]  // ✅ Include NgIf in imports
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';  
  loading = false;    

  constructor(
    private authService: AuthService,
    private userStore: UserStoreService,
    private router: Router
  ) {}

  login(): void {
    this.loading = true;
    this.errorMessage = '';

    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (token: string) => {   
        console.log('Received token:', token);

        this.userStore.saveToken(token);

        // Navigate after successful login
        this.router.navigate(['/dashboard']);
        console.log("Token saved");
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Invalid credentials';
        this.loading = false;
      }
    });
  }
}
