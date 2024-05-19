import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { LoginDTO } from '../AuthInterfaces';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent {

  constructor(private authService: AuthService, private router: Router) { }

  login: boolean = true;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  onSubmit() {
    if (this.loginForm.invalid) {
      console.error('Form is invalid');
      return;
    }

    const loginCreds: LoginDTO = {
      email: this.loginForm.value.email ?? '', // Ensuring no null values
      password: this.loginForm.value.password ?? '' // Ensuring no null values
    };

    this.authService.login(loginCreds).subscribe({
      next: (token: string) => {
        console.log('JWT Token:', token);
        // Store the token or use it as needed
      },
      error: (err) => {
        console.error('Login failed', err.error.text);
        sessionStorage.setItem('token', err.error.text);
        this.router.navigate(['/home'])
      }
    });
  }


}
