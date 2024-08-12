import { Injectable, NgZone } from '@angular/core';
import { constants } from '../app/app.constants';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { event } from 'jquery';
import * as SockJS from 'sockjs-client';
import { Client, Stomp } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {
  
  
  
  mobileNumber=null;
  authToken:any = null;
  regSuccessful: number | undefined;
  role:any=null;
  userName:any=null;
  mainUrl=constants.mainUrl;
  inputData:any={};
  files:any[]=[];
  
  
  openToken:any="";
  testName: any="";
  fromEdit: boolean=false;
  lastRoute: string="Dashboard";
  reportId: string="";
  consultationId: any=0;
  gAuthToken: string="";
  publicationData: any;
  viewerIp: any;
  messages:{ text: any[]; sender: string }[] = [];
  constructor(private http:HttpClient, private router:Router,private zone: NgZone) {  }

  getConsultationId(){
    return localStorage.getItem("consultationId");
  }
  assignTokens(){
    this.authToken = localStorage.getItem('token');
    this.role = localStorage.getItem('role');
    this.userName = localStorage.getItem('userName');
  }

getAuthOption(){
  
  if(this.authToken == null && localStorage.getItem('token') != null){
    this.authToken = localStorage.getItem('token');
    this.role = localStorage.getItem('role');
    this.userName = localStorage.getItem('userName');
  }
  else
  if(this.authToken==null){
    this.router.navigate(['/login']);
  }
  var apiAuthReqOptions = {
    'Content-Type':'application/json',
    'Token': 'Bearer '+this.authToken
  };
  var options={
  headers : new HttpHeaders(apiAuthReqOptions)
  }
  return options;
}

getAuthFileOption(){
  
  if(this.authToken == null && localStorage.getItem('token') != null){
    this.authToken = localStorage.getItem('token');
    this.role = localStorage.getItem('role');
    this.userName = localStorage.getItem('userName');
  }
  else
  if(this.authToken==null){
    this.router.navigate(['/login']);
  }
  var apiAuthReqOptions = {
    'Token':'Bearer '+this.authToken
  };
  var options={
  headers : new HttpHeaders(apiAuthReqOptions),
  reportProgress: true
  }
  return options;
}


getOpenAuthOption(){
  
  if((this.openToken == null||this.openToken=="") && localStorage.getItem('openToken') != null){
    this.openToken = localStorage.getItem('openToken');
  }
  else
  if(this.openToken==null||this.openToken==""){
    this.getOpenAuthToken();  
  }
  var apiAuthReqOptions = {
    'Content-Type':'application/json',
    'Authorization': 'Bearer '+this.openToken
  };
  var options={
  headers : new HttpHeaders(apiAuthReqOptions)
  }
  return options;
}

getRole(){
  this.role=localStorage.getItem("role");
  return this.role;
}

postRequest(url: string,input: any){
  var options = this.getAuthOption();
  return this.http.post<any>(this.mainUrl+url,input,options).pipe(catchError(this.handle));
}

postFile(url: string,input: any){
  var options = this.getAuthFileOption();
  return this.http.post<any>(this.mainUrl+url,input,options).pipe(catchError(this.handle));
}
authenticatedPostRequest(url: string,input: any){
  var options = this.getAuthOption();
  return this.http.post<any>(this.mainUrl+url,input,options).pipe(catchError(this.handle));
}
getRequest(url: string){
  var options = this.getAuthOption();
  return this.http.get<any>(this.mainUrl+url,options).pipe(catchError(this.handle));
}
postNoHeader(url: string,input: any){
  return this.http.post<any>(this.mainUrl+url,input).pipe(catchError(this.handle));
}
getRequestNoHeader(url: string){
  return this.http.get<any>(this.mainUrl+url).pipe(catchError(this.handle));
}


getOpenAuthToken() {
  return this.getRequest("/user/openToken").subscribe((data)=>{
    localStorage.setItem("openToken",data);
    this.openToken=data.token;
  }); 
}

handle(err:any){
  console.log(err,err.status);
  if(err.status==401||err.status==422){
    window.location.href="/login";
  }
  return err;
}

postAndGetStream(url: string, body: any): Observable<string> {
  
  
  return new Observable<string>(observer => {
    
    var options = this.getAuthOption();
    let input = {
      message: body
    }
    //this.http.post(this.mainUrl+url, input,options).subscribe();

    const eventSource = new EventSource(this.mainUrl+url+"/"+encodeURIComponent(body));

    eventSource.onopen = event=>{
      
    }

    eventSource.onmessage = event => {
      this.zone.run(() => {
        observer.next(event.data);
      });
    };

    eventSource.onerror = error => {
      this.zone.run(() => {
        observer.error(error);
      });
    };

    return () => eventSource.close();
  });
}

public stompClient:any;
public msg:any = [];
initializeWebSocketConnection():any {
  const serverUrl = 'http://localhost:8090/socket';
  const ws = new SockJS(serverUrl);
  this.stompClient = Stomp.over(ws);
  const that = this;
  // tslint:disable-next-line:only-arrow-functions
  this.stompClient.connect({}, function(frame:any) {
    that.stompClient.subscribe('/message', (message:any) => {
      if (message?.body) {
        
        let last_msg:any = that.messages.pop();
        if(last_msg?.sender=="llm"){
          let body = message.body;
          body = (message.body).replace(/\*\*(.*?)\*\*/g, '<b>$1</b>');
          body = (body).replaceAll("\n","<br/>");
          last_msg.text.push(body);      
          that.messages.push(last_msg);
        }
        else{
          that.messages.push(last_msg);
          let body = message.body;
          body = (message.body).replace(/\*\*(.*?)\*\*/g, '<b>$1</b>');
          body = (body).replaceAll("\n","<br/>");
          that.messages.push({ text:[body], sender: 'llm' });
        }
      }
    });
  });
}

sendMessage(message:any,initial:any) {
  let input = {
    message:message,
    consultationId:this.consultationId,
    initialMsg:initial
  }
  let inputStr = JSON.stringify(input);
  this.stompClient.send('/app/send/message' , {
    'Content-Type':'application/json',
    'Token': 'Bearer '+this.authToken
  }, inputStr);
}
}
