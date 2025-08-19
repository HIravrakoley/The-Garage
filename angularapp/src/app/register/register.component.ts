import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';    // ✅ Import CommonModule
import { FormsModule } from '@angular/forms';    
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [CommonModule, FormsModule,RouterLink]              // ✅ Add both CommonModule and FormsModule
})
export class RegisterComponent {
  username = '';
  password = '';
  role = 'CUSTOMER';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register(): void {
    const user = {
      username: this.username,
      password: this.password,
      role: this.role
    };

    this.authService.register(user).subscribe({
      next: () => {
        alert('Registration successful!');
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Registration failed:', err);
        alert('Registration failed');
      }
    });
  }
}
