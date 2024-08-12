import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import { constants } from '../app.constants';
import { Router } from '@angular/router';
import * as $ from "jquery";
@Component({
  selector: 'app-add-report',
  templateUrl: './add-report.component.html',
  styleUrls: ['./add-report.component.scss']
})
export class AddReportComponent implements OnInit {
  
  public files: any[] = [];
  showLoader: boolean=false;
  public uploadedIds: any[]=[];
  inputData:any={};
  testname:String="";
  currInd:any=0;
  testMaster: any[]=[];
  testMasterMap: any[]=[];
  dataBody:any={};
  progress:String="";

  constructor(private appService:AppServiceService,private router:Router){}
  
  ngOnInit(): void {
    //throw new Error('Method not implemented.');
    this.inputData=[];
    this.inputData={
      nameOnReport:"",
      age:"",
      gender:"",
      reportDate:"",
      labName:"",
      labAddress:"",
      labPhone:"",
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
   // this.getTestMaster();
  }

  getTestMaster(){
    var input={

    }
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

  uploadFile(event:any) {
    console.log("upload");
    if(event!=null&&event.target!=null){
      console.log(event);
      this.showLoader=true;
      this.progress="Uploading file for further processing";

        var selectedFile=(event.target).files[0];
        if (selectedFile) {
          const reader = new FileReader();
      
          const readAsDataURLPromise = new Promise<string>((resolve, reject) => {
            reader.onload = (e: any) => {
              // e.target.result contains the file data as a base64 encoded string
              const base64String = e.target.result as string;
              resolve(base64String); // Resolve the Promise with the base64String
            };
            reader.onerror = (error) => {
              reject(error); // Reject the Promise if there's an error
            };
          });

        reader.readAsDataURL(selectedFile);

        readAsDataURLPromise
          .then((base64String) => {
            // Now, you can access the base64String here
            console.log(base64String);
            // You can also use it in other parts of your code
            let filedata={
              respData:"",
              fileData:base64String.split(",")[1],
              fileName:(event.target).files[0].name,
              base64String:base64String,
              contentType:"",
              fileType:""
            };

            filedata.fileType=filedata.fileName&&filedata.fileName.substring(filedata.fileName.lastIndexOf('.')+1); 

            let strings=filedata.base64String.split(",");
            let type=strings[0].replace(";base64","");
            type=type.replace("data:","");
            filedata.contentType=type;
            
            let fileContents= {
              "data": strings[1],
              "contentType": type,
              "offset": 0
            };
            let requesDataString={
              requestString:JSON.stringify(fileContents)
            }
            
            this.appService.postRequest("/user/uploadFileCaptureService",requesDataString).subscribe((data)=>{
              this.showLoader=false;
              console.log(data);
              if(data.uri!=null){
                this.progress="Uploaded File";
                filedata.respData=data;
                this.files.push(filedata);
                this.appService.files=this.files;
                $("#bulk_upload").val("");
              }
            });

          })
          .catch((error) => {
            console.error(error);
          });


        }
        
    }
  }

  delete(index:any){
    let fileData=this.files[index];
    let inputData={
      requestString:fileData.respData.uri
    }
    this.showLoader=true;
    this.appService.postRequest("/user/deleteFileCapture",inputData).subscribe((data)=>{
      this.showLoader=false;
      console.log(data);
      if(data!=null&& data.status!="Success"){
        this.files.splice(index,1);
      }
    });
    
  }


    
    /*this.appService.postOpenRequest("/session/services/fullpageocr",inputData).subscribe((data)=>{
      console.log(data);
    });*/
  

  extract(){

    let fileDataList:any[]=[];
    this.showLoader=true;
    this.progress="Extrating data from files";
    if(this.files.length>0){
      for(let i in this.files){ 
        let fileData=  this.files[i].respData;
        fileDataList.push(fileData);
      }
    } 

    let request={
      
      "requestItems": [
        {
          "files": fileDataList
        }
      ]
    };

    let requesDataString={
      requestString:JSON.stringify(request)
    }

    this.appService.postRequest("/user/extractData",requesDataString).subscribe((data)=>{
      
      let response=data.response;
      this.inputData.userTestsList=[];
      this.inputData.containsPII = data.containsPII==1?true:false;
      for(let res in response){
        let pageDetails = response[res];
        this.inputData.nameOnReport=pageDetails.PatientName!=null?pageDetails.PatientName:this.inputData.nameOnReport;
        this.inputData.age=pageDetails.PatientAge!=null?pageDetails.PatientAge:this.inputData.age;
        this.inputData.gender=pageDetails.PatientGender!=null?pageDetails.PatientGender:this.inputData.gender;
        this.inputData.reportDate=pageDetails.ReportDate!=null?pageDetails.ReportDate:this.inputData.reportDate;
        this.inputData.labName=pageDetails.LabName!=null?pageDetails.LabName:this.inputData.labName;
        this.inputData.labAddress=pageDetails.LabAddress!=null?pageDetails.LabAddress:this.inputData.labAddress;
        this.inputData.labPhone=pageDetails.LabPhoneNumber!=null?pageDetails.LabPhoneNumber:this.inputData.labPhone;
        
        let userTestsList = this.inputData.userTestsList;
          
            let testArr=pageDetails.testDetails;
            for(let i in testArr){
              if(testArr[i].labTestValue!=null){
              
                userTestsList.push({
                  testMasterId:"",
                  testName:testArr[i].labTestName,
                  units:testArr[i].labTestUnits,
                  testValue:testArr[i].labTestValue,
                  testMinNormal:testArr[i].normalRangeMinimumValue,
                  testMaxNormal:testArr[i].normalRangeMaximumValue,
                  testResultText:"",
                  testResultType:"",
                  observation:"",
                  suggestion:"",
                  medication:[],
                })
            }
              
            }
        

       }
      this.appService.inputData=this.inputData;
      this.showLoader=false;
      this.progress="Extraction Completed";
      this.appService.fromEdit=false;
      this.router.navigateByUrl("/addReportForm");
    });


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
/*  showAdd(){
    $(("#addmodal") as any).show();
  }
closeadd(){
    $(("#addmodal") as any).hide();
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
    )
  }
  deleteRow(index:any){
    this.inputData.userTestsList.splice(index,1);
  }

  addObservation(index:any){
    this.currInd=index;
    $(("#addNotes") as any).show();
  }

  closeNotes(){
    $(("#addNotes") as any).hide();
  }

  addMedRow(i:any,j:any){
    this.inputData.userTestsList[i].medication.push(
      {
        medication:"",
        timings:""
      }
    );
  }
  deleteMedRow(i:any,j:any){
    this.inputData.userTestsList[i].medication.splice(j,1);
  }*/
}

