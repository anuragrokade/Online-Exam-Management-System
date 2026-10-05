import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

import { LoginRequest } from '../../../core/models/login-request.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule], //Allows: [(ngModel)]. Angular automatically updates email when the user types.
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email = '';

  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogin() {

    const request: LoginRequest = {

      email: this.email,

      password: this.password

    };

    this.authService.login(request)
      .subscribe({

        next: (response) => {

          console.log(response);

          localStorage.setItem(
            'userId',
            response.userId.toString()
          );

          localStorage.setItem(
            'role',
            response.role
          );

          if (response.role === 'ADMIN') {

            this.router.navigate([
              '/admin/dashboard'
            ]);

          } else {

            this.router.navigate([
              '/candidate/dashboard'
            ]);

          }

        },

        error: (error) => {

          console.error(error);

          alert('Invalid credentials');

        }

      });

  }

}