import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import {Router} from '@angular/router';
import * as $ from "jquery";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  loginData:any;
  loginFailed:any;

  constructor(private appService:AppServiceService, private router: Router) { }
  ngOnInit() {
    
    this.loginData={
      username:'',
      password:''
    };
    this.loginFailed=false;
  }

  
  login(){
  
    this.appService.postNoHeader("/token/generate-token",this.loginData)
    .subscribe(data=>{
      if(data != null){
        this.appService.authToken = data['token'];
        this.appService.role = data['role'];
        this.appService.userName = data['userName'];
        localStorage.setItem("token",data['token']);
        localStorage.setItem("role",data['role']);
        localStorage.setItem("userName",data['userName']);
        console.log(data);
        this.loginFailed=false;
        if(data['role']=='role_user'){
        this.router.navigate(['/userDashboard']);
        }
        else
        if(data['role']=='role_doctor'){
          this.appService.consultationId=0;
          localStorage.setItem("consultationId","0");
          this.router.navigate(['/doctorDashboard']);
        }
        else
        if(data['role']=='role_lab'){
          this.router.navigate(['/labDashboard']);
        }
      }
      else{
        this.loginFailed=true; 
      }
      //this.appService.getOpenAuthToken();
    });
  }


}
