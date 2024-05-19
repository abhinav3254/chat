import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from './AuthInterfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080/auth/';

  constructor(private http: HttpClient) { }

  login(login: LoginDTO): Observable<any> {
    const url = 'http://localhost:8080/auth/login';
    return this.http.post(url, login);
  }

}
