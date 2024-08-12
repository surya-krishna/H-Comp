import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppServiceService } from '../app-service.service';
import * as $ from "jquery";
@Component({
  selector: 'app-doctor-registration',
  templateUrl: './doctor-registration.component.html',
  styleUrls: ['./doctor-registration.component.scss']
})
export class DoctorRegistrationComponent implements OnInit {

  inputData:any;
  loginFailed:any;
  fail: boolean | undefined;
  regStatus:boolean | undefined;
  confirmPassword:string='';
  verifyIp:any;

  constructor(private appService:AppServiceService, private router: Router) { }
  ngOnInit() {
    this.inputData={
      emailId:'',
      password:'',
      name:'',
      doctorRegId:''
    };
    this.confirmPassword='';
    this.verifyIp={
      emailId:"",
      otpCode:""
    }
  }

  register(){
    for(var i in this.inputData){
      if(this.inputData==0||this.inputData[i]==''){
        this.fail=true;
        return;
      }
    }

    this.appService.postNoHeader("/register/signupDoctor",this.inputData)
    .subscribe(data=>{
      if(data != null){
        if(data.errFlag==1){
          $("#failure-alert-detail").show();
          $("#failure-alert-detail").fadeOut(2000);
          return;
        }
        this.regStatus=true;
        console.log(data);
        this.verifyIp.emailId=this.inputData.emailId;
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
      name:'',
      doctorRegId:''
    };
  }

}
