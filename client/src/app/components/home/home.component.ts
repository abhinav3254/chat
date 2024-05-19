import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private homeService: HomeService) { }

  users: User[] = [];

  ngOnInit(): void {
    this.homeService.getAllTheUsers().subscribe((data) => {
      this.users = data as User[];
    });
  }

  messages: string[] = [];

  messageText: string = '';

  sendMessage(): void {
    console.log(this.messageText);
    this.messages.push(this.messageText);
    this.messageText = '';
  }

  getUserId(user: User) {
    this.homeService.getChatHistoryByUserId(user.id).subscribe((data) => {
      console.log(data);
    });
  }

}


export interface User {
  id: number
  name: string
  email: string
  password: string
  role: string
}
