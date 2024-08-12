import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppServiceService } from '../app-service.service';
import * as $ from "jquery";
@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.scss']
})
export class ReportsListComponent implements OnInit {
  noRecentReport: boolean=true;
  recentReports: any=[];
  recentReportIp: any;
  inputData:any;
  currInd: any="";
  role: any;
  downloadInput:any;
  viewerData:any;

  constructor(public appService:AppServiceService,public router:Router) { }

  ngOnInit(): void {
    this.recentReportIp={
      lowerLimit:0,
      count:1000
    };
    this.role=this.appService.getRole();
    this.inputData={
      reportId:"",
      nameOnReport:"",
      age:"",
      gender:"",
      reportDate:"",
      labName:"",
      labAddress:"",
      userTestsList:[
        {
          userTestId:"",
          testMasterId:"",
          testName:"",
          units:"",
          testValue:"",
          testMinNormal:"",
          testMaxNormal:"",
          testResultText:"",// to capture text based results like colors, blood groups etc
          testResultType:"",
          observation:"",
          suggestion:"",
          medication:[],
        }
      ]
    };

    this.viewerData={
      reportId:"",
      password:""
    };
    this.downloadInput={
      reportId:"",
      password:""
    };
    this.getReports(this.recentReportIp);
    if(this.appService.lastRoute=="Dashboard"&&this.appService.reportId!=""){
      this.getReport(this.appService.reportId); 
    }
  }

  getReports(input:any) {
    input.consultationId=this.appService.getConsultationId();
    this.appService.postRequest('/user/getReportList',input).subscribe(
      (data)=>{
        if(data!=null){
        let reportList = data.reportBasicModelList;
        if(reportList==null||reportList.length==0){
          this.noRecentReport=true;
        }
        for(let i in reportList){
          let repoObj = {
            'reportId':reportList[i].reportId,
            'labName':reportList[i].labName,
            'reportDate':reportList[i].reportDate,
            'testCount':0,
            'tests_performed':""
          };
          let testsPerformed="";
          let testCount=0;
          for(let j in reportList[i].testsList){
            testCount++;
            testsPerformed+=reportList[i].testsList[j]+",";
          }
          testsPerformed=testsPerformed!=""?(testsPerformed.slice(0,testsPerformed.length-1)):testsPerformed;
          repoObj.testCount=testCount;
          if(testsPerformed.length>75){
          repoObj.tests_performed=testsPerformed.substring(0,72)+"...";
          }
          else{
           
            repoObj.tests_performed=testsPerformed;
          }
          this.recentReports.push(repoObj);
        }
        }
        else{
          this.noRecentReport=true;
        }
      }
    );
  }

  getReport(reportId:any){
    let input={
      reportId:reportId,
      consultationId:this.appService.getConsultationId()
    }
    this.appService.postRequest('/user/getReportDetails',input).subscribe(
      (data)=>{
        this.appService.reportId="";
        if(data.errFlag!=1){
          this.inputData.nameOnReport=data.nameOnReport;
          this.inputData.reportId=data.reportId;
          this.inputData.reportDate=data.reportDate;
          this.inputData.labId=data.labId;
          this.inputData.age=data.age;
          this.inputData.gender=data.gender;
          this.inputData.labName=data.labName;
          this.inputData.containsPII=data.containsPII;
          this.inputData.labAddress=data.labAddress;
          this.inputData.userTestsList=data.userTestsList;
          
        }
        $(("#viewReport") as any).show();
      }) 
  }
  closeReport(){
    $(("#viewReport") as any).hide();
  }
  viewObservation(index:any){
    this.currInd=index;
    $(("#addNotes") as any).show();
  }
  editReport(){
    this.appService.inputData=this.inputData;
    this.appService.fromEdit=true;
    this.router.navigateByUrl("/addReportForm");
  }
  deleteReport(reportId:any){
    let input={
      reportId:reportId
    }
    this.appService.postRequest('/user/deleteReport',input).subscribe(
      (data)=>{
        if(data.errflag!=1){
          this.recentReports=[];
          this.getReports(this.recentReportIp);
          this.closeReport();
          
          $("#success-alert-detail").show();
          $("#success-alert-detail").fadeOut(2000);
        }
        else{
          $("#failure-alert-detail").show();
          $("#failure-alert-detail").fadeOut(2000);
        }
      }
    );


  }
  closeNotes(Index:any){
    this.currInd="";
    $(("#addNotes") as any).hide();
  }

  verifyForPII(){
    if(this.inputData.containsPII){
      $(("#download") as any).show();
    }
    else{
      this.downloadReport();
    }
  }
  closeDownload(){
    $(("#download") as any).hide();
  }
  downloadReport(){
    this.downloadInput.reportId=this.inputData.reportId;
    if(!this.inputData.containsPII){
      $(("#download") as any).hide();
    }
    this.appService.postRequest('/user/download',this.downloadInput).subscribe(
      (data)=>{
        if(data.error){
            $("#failure-dwnl-detail").show();
            $("#failure-dwnl-detail").fadeOut(2000);
        }
        else
        if(this.inputData.containsPII){
          $("#success-alert-detail").show();
          $("#success-alert-detail").fadeOut(2000);
          $(("#download") as any).hide();
        }
          console.log(data);

          const byteCharacters = window.atob(data.fileBytes);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
          const blob = new Blob([byteArray], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
      
          const a = document.createElement('a');
          a.style.display = 'none';
          a.href = url;
          a.download = "Report_"+this.formatDate()+".pdf";
      
          document.body.appendChild(a);
          a.click(); 
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
      }
    );
  }

   formatDate(date = new Date()) {
    const year = date.toLocaleString('default', {year: 'numeric'});
    const month = date.toLocaleString('default', {
      month: '2-digit',
    });
    const day = date.toLocaleString('default', {day: '2-digit'});
  
    return [year, month, day].join('-');
  }
  viewOriginal(){
    this.viewerData.reportId=""; 
   this.viewerData.reportId=this.inputData.reportId;
   this.viewerData.consultationId=this.appService.getConsultationId();
   this.appService.viewerIp=this.viewerData;
    setTimeout(()=>{},2000);

    this.router.navigateByUrl("/viewer");
  }

}
