import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { LoginRequest } from '../models/login-request.model';
import { LoginResponse } from '../models/login-response.model';
import { RegisterRequest } from '../models/register-request.model';

/*
LOGIN FLOW

1. User submits the Angular login form.
2. LoginComponent calls AuthService.
3. AuthService sends POST /api/auth/login request.
4. Spring Boot backend validates credentials.
5. Backend returns LoginResponse containing JWT token.
6. Angular stores the token.
7. Angular Router navigates to /admin/dashboard.
*/


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient 
  ) { }

  login(
    request: LoginRequest
  ): Observable<LoginResponse> {

    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      request
    );

  }

  register(
    request: RegisterRequest
  ): Observable<any> {

    return this.http.post(
      `${this.apiUrl}/register`,
      request
    );

  }

}