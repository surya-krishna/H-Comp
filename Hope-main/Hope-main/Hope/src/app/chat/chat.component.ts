// chat.component.ts
import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  messages: { text: any[]; sender: string }[] = [];
  message: string = '';
  stompClient: any;
  initial: number=1;

  constructor(private appService: AppServiceService) {}

  ngOnInit(): void {
    this.appService.initializeWebSocketConnection();
    this.messages=this.appService.messages;
    this.initial=1;
      // this.appService.getMessage()?.subscribe((message: any) => {
      // this.messages.push(message);
      // });
  }

  sendMessage() {
    if (this.message.trim()) {
      this.messages.push({ text: [this.message], sender: 'user' });
      this.messages.push({ text:[], sender: 'llm' });

      this.appService.sendMessage(this.message,this.initial);
      this.message = '';
      this.initial=0;


      // this.appService.postAndGetStream("/user/queryPatientInfo",this.message).subscribe({
      //   next: response => {
      //   console.log(response);
          
      //   let last = this.messages.length-1;
      //   this.messages[last].text.push(response);
      //   },
      //   error: err => {
      //     console.error('Error getting data:', err);
      //   }
      // });
      this.message = '';
    }
  }
}
