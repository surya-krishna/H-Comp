import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import { Router } from '@angular/router';
import * as $ from "jquery";
@Component({
  selector: 'app-consultation',
  templateUrl: './consultation.component.html',
  styleUrls: ['./consultation.component.scss']
})
export class ConsultationComponent implements OnInit {


  sharing: any;
  activeCon: any;
  sharingError:boolean=false;
  cons: any;
  unSharingError: any=false;

  role:any;
  verifyIp:any;

  constructor(private appService:AppServiceService,private router:Router) { }

  ngOnInit(): void {
    this.sharing={
      doctorEmail:"",
      activeFlag:1
    }
    this.verifyIp={
      consultationId:"",
      otp:""
    };
    this.role=localStorage.getItem("role");
    this.getActiveConsultations();
    this.getAllConsultations();
    
  }


  shareProfile() {
    this.appService.postRequest("/user/shareProfile",this.sharing).subscribe(
      (data)=>{
        if(data.errFlag!=1){
          this.sharing={
            doctorEmail:"",
            activeFlag:1
          }
          
    this.getActiveConsultations();
    this.getAllConsultations();
        }
        else{
          this.sharingError=true;
        }
      }
      );
    }

  stopShare(cons: any) {
    let unShareIp={
      sharingId:cons.consultationId,
      doctorEmail:cons.userName,
      activeFlag:0
    }
    let url="/user/unShareProfile";
    if(this.role=="role_doctor"){
       url="/doctor/unShareProfile";
    }
    this.appService.postRequest(url,unShareIp).subscribe(
      (data)=>{
        if(data.errFlag!=1){
          this.sharing={
            doctorEmail:"",
            activeFlag:1
          }
          
    this.getActiveConsultations();
    this.getAllConsultations();
        }
        else{
          this.unSharingError=true;
        }
      }
      );

  }
  getAllConsultations() {
    let url="/user/allConsultations";
    if(this.role=="role_doctor"){
       url="/doctor/allConsultations";
    }
    this.appService.getRequest(url).subscribe(
      (data)=>{
        this.cons=data.consultationList;
      }
      );
  }
  getActiveConsultations() {
    
    let url="/user/activeConsultations";
    if(this.role=="role_doctor"){
       url="/doctor/activeConsultations";
    }
    this.appService.getRequest(url).subscribe(
      (data)=>{
        this.activeCon=data.consultationList;
        for(let i in this.activeCon){
        if(this.role=="role_doctor"&&this.activeCon[i].status=="Verified"){
          this.appService.consultationId=this.activeCon[i].consultationId;
          localStorage.setItem("consultationId",this.activeCon[i].consultationId);
        }
      }
      }
      );
  }
  verifyOtp(cons:any){
    this.verifyIp.consultationId=cons.consultationId;
    this.appService.postRequest("/doctor/verifyOtp",this.verifyIp).subscribe(
      (data)=>{
        if(data.errFlag==0){
          this.getActiveConsultations();
          this.appService.consultationId=this.verifyIp.consultationId;
          localStorage.setItem("consultationId",this.verifyIp.consultationId);
        }
        else{
          $("#failure-alert-detail").show();
          $("#failure-alert-detail").fadeOut(2000);
        }
      }
      );
  }


}
