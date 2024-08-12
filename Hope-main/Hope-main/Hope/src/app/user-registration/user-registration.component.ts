import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import { Router } from '@angular/router';
import * as $ from "jquery";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.scss']
})
export class UserRegistrationComponent implements OnInit {

  inputData:any;
  loginFailed:any;
  fail: boolean | undefined;
  regStatus:boolean | undefined;
  verifyIp:any;

  constructor(private appService:AppServiceService, private router: Router) { }
  ngOnInit() {
    this.inputData={
      emailId:'',
      password:'',
      dob:'',
      gender:'',
      conpassword:''
    };
    this.verifyIp={
      emailId:"",
      otpCode:""
    }
  }

  register(){
    for(var i in this.inputData){
      if(this.inputData==0||this.inputData[i]==''){
        this.fail=true;
      }
    }

    this.appService.postNoHeader("/register/signup",this.inputData)
    .subscribe(data=>{
      if(data != null){
        this.verifyIp.emailId=this.inputData.emailId;
        if(data.errFlag==1){
          $("#failure-alert-detail").show();
          $("#failure-alert-detail").fadeOut(2000);
          return;
        }
        this.regStatus=true;
        console.log(data);
        $("#success-alert-detail").show();
        $("#success-alert-detail").fadeOut(2000);
        this.reset();
        this.showOtp();
      }
      else{
        $("#failure-alert-detail").show();
        $("#failure-alert-detail").fadeOut(2000);
      }
    });

  }
  showOtp() {
    $("#verify").show();
  }
  closeOtp(){
    $("#verify").hide();
  }
  verifyOtp(){
    this.appService.postNoHeader("/register/verifyOtp",this.verifyIp).subscribe((data)=>{
      if(data.errFlag==1){
       $("#failure-otp-detail").show();
       $("#failure-otp-detail").fadeOut(2000);
      }
      else{
        $("#success-otp-detail").show();
        $("#success-otp-detail").fadeOut(2000);
        this.router.navigateByUrl("/login");
      }
    })
  }
  reset() {
    this.inputData={
      emailId:'',
      password:'',
      dob:'',
      gender:'',
    };
  }

}
