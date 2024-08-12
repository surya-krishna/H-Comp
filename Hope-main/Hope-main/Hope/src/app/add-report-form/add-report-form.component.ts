import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import * as $ from "jquery";
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-report-form',
  templateUrl: './add-report-form.component.html',
  styleUrls: ['./add-report-form.component.scss']
})
export class AddReportFormComponent implements OnInit {
  pdfIdCSS: any;
  
  inputData: any={};
  currInd: any=0;
  
  testMaster: any[]=[];
  testMasterMap: any[]=[];
  files:any[]=[];
  
  lastIndex=0;
  lastMedIndex=0;
  fails: boolean=false;
  pdfFileId: String="";
  pdfName: any;
  preAddJob:boolean=false;

  statusText="";
  showLoader=false;
  constainsPii: any;
  pii_terms:any=[];
  constructor(private appService:AppServiceService,private router:Router) { }

  ngOnInit(): void {
    this.inputData=this.appService.inputData;
    if(this.inputData==null||this.inputData==undefined||Object.keys(this.inputData).length==0){
      this.inputData={
        nameOnReport:"",
        age:"",
        gender:"",
        reportDate:"",
        labName:"",
        labAddress:"",
        userTestsList:[
          {
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
     
     this.appService.inputData=this.inputData;
    }
    this.lastIndex=this.inputData.userTestsList.length-1;
    this.files=this.appService.files;
  }

  addRow(index:any){
    this.inputData.userTestsList.push(
        {
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
    );
    this.lastIndex=this.inputData.userTestsList.length-1;
  }
  deleteRow(index:any){
    this.inputData.userTestsList.splice(index,1);
    this.lastIndex=this.inputData.userTestsList.length-1;
    if(this.inputData.userTestsList.length==0)
      this.addRow(0);
    
  }

  addObservation(index:any){
    this.currInd=index;
    if(this.inputData.userTestsList[index].medication.length==0)
    {this.inputData.userTestsList[index].medication.push(
      {
        medication:"",
        timings:""
      }
    );
    }
    this.lastMedIndex=this.inputData.userTestsList[index].medication.length-1;
    $(("#addNotes") as any).show();
  }

  closeNotes(index:any){
    let meds=this.inputData.userTestsList[index].medication;
    for(let i in meds){
      if(meds[i].medication.trim()==""){
        meds.splice(i,1);
      }
    }
    this.lastMedIndex=0;
    $(("#addNotes") as any).hide();
  }

  addMedRow(i:any,j:any){
    this.inputData.userTestsList[i].medication.push(
      {
        medication:"",
        timings:""
      }
    );
    this.lastMedIndex=this.inputData.userTestsList[i].medication.length-1;
  }
  deleteMedRow(i:any,j:any){
    this.inputData.userTestsList[i].medication.splice(j,1);
    this.lastMedIndex=this.inputData.userTestsList[i].medication.length-1;
  }
  getTestMaster(){
    
this.appService.getRequest("/register/getTestData").subscribe(
  (data)=>{
    console.log(data);
    this.testMaster=data.testDataList;
    for(let i in this.testMaster){
      this.testMasterMap[this.testMaster[i].testId]=this.testMaster[i];
    }
    this.testMasterMap;
  }
)
}


reset() {
  this.inputData={
    nameOnReport:"",
    age:"",
    gender:"",
    reportDate:"",
    labName:"",
    labAddress:"",
    userTestsList:[
      {
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
}

addReport(){
  this.inputData.reportPdf=this.pdfIdCSS;
  //if there is a pii term we are masking them
  for(var i in this.inputData){
    if(this.pii_terms.indexOf(this.inputData[i])!=-1){
      let str="";
      let len=this.inputData[i].length;
      for(let i=0;i<(len-len/2);i++){
        str+="X";
      }
      this.inputData[i]=this.inputData[i].substring(0,len/2)+str;
    }
  }
  //even if name is regognized as pii or not we are masking it anyway
  
  if(this.inputData.reportPdf!=null){
    this.inputData.reportMimeType="application/pdf";
  }
  let len=this.inputData.nameOnReport.length;
  let str="";
  for(let i=0;i<(len-len/2);i++){
    str+="X";
  }
  this.inputData.nameOnReport=this.inputData.nameOnReport.substring(0,len/2)+str;
  for(let i in this.inputData.userTestsList){
    if(this.inputData.userTestsList[i].testname==""||this.inputData.userTestsList[i].testValue==""){
      this.inputData.userTestsList.splice(i,1);
    }
    
  }
  if(this.inputData.nameOnReport==""||this.inputData.age==""||this.inputData.gender==""||this.inputData.reportDate==""||this.inputData.userTestsList.length==0){
    this.fails=true;
    return;
  }
  for(var i in this.inputData.userTestsList){
    if(this.inputData.userTestsList[i].testValue&&!isNaN(this.inputData.userTestsList[i].testValue.trim())){
      this.inputData.testResultType="Number";
    }
    else{
      this.inputData.testResultType="Text";
    }
  }
  let url="/user/addReport";
  if(this.appService.fromEdit){
    url="/user/editReport";
  }
  this.statusText="Adding report details";
  this.showLoader=true;
  this.appService.postRequest(url,this.inputData).subscribe((data)=>{
    console.log(data);
    if(data.errFlag==1){
      $("#failure-alert-detail").show();
      $("#failure-alert-detail").fadeOut(2000);
      this.statusText="";
      return;
    }
    $("#success-alert-detail").show();
    $("#success-alert-detail").fadeOut(2000);
    this.reset();
    this.statusText="";
    this.showLoader=false;
    this.appService.fromEdit=false;
    this.router.navigateByUrl("/reportList");
  });
}




}