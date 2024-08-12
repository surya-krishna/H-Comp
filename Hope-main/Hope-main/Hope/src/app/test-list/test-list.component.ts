import { Component, OnInit } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {
  freqTestIp:any;
  freqTest: any=[] ;
  noFreqTest: boolean=false;

  constructor(private appService:AppServiceService,private router:Router) { }

  ngOnInit(): void {
    this.freqTestIp={
      consultationId:this.appService.getConsultationId(),
      lowerLimit:0,
      count:1000
    };
    this.getTestList(this.freqTestIp);
  }
  getTestList(input:any) {
    this.appService.postRequest('/user/getTestList',input).subscribe(
      (data)=>{
        if(data!=null){
        let testList = data.testBasicList;
        if(testList==null||testList.length==0){
          this.noFreqTest=true;
        }
        for(let i in testList){
          let testObj = {
            'testMasterId':testList[i].testMasterId,
            'testName':testList[i].testName,
            'count':testList[i].count,
            'testValue':testList[i].meanValue,
            'normalMin':testList[i].meanNormalMin,
            'normalMax':testList[i].meanNormalMax
          };
          this.freqTest.push(testObj);
        }
        }
        else{
          this.noFreqTest=true;
        }
      }
    );
  }

  timeLine(testName:string){
  this.appService.lastRoute="Test";  
  this.appService.testName=testName;
  this.router.navigateByUrl("/timeline");
  }
}
