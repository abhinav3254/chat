import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) { }

  getAllTheUsers(): Observable<any> {
    const url = 'http://localhost:8080/auth/user';
    const token = sessionStorage.getItem('token');
    const httpHeaders: HttpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + token
    });
    return this.http.get(url, { headers: httpHeaders });
  }

  getChatHistoryByUserId(userId: any): Observable<any> {
    const url = 'http://localhost:8080/auth/chat-history/' + userId;
    const token = sessionStorage.getItem('token');
    const httpHeaders: HttpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + token
    });
    return this.http.get(url, { headers: httpHeaders });
  }

  sendMessage(): void {
    const url = 'ws://localhost:8080/chat';
  }

}
