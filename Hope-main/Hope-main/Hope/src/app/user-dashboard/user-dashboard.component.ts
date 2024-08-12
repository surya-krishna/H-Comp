import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AppServiceService } from '../app-service.service';


import {  ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss']
})
export class UserDashboardComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {}
    },
    plugins: {
      legend: {
        display: true,
      },
      datalabels: {
        anchor: 'end',
        align: 'end'
      }
    }
  };
  public testBarChartData: ChartData<'bar'> = {
    labels: [],
    datasets: []
  };
  public barChartPlugins = [
    DataLabelsPlugin
  ];
  public barChartType: ChartType = 'bar';
    lastRoute: string="";
  

    doughnutChartType: ChartType = 'doughnut';
    public nutChartData: ChartData<'doughnut'> = {
      labels: [],
      datasets: [
        {data:[]}
      ]
    };
    showNutrition:boolean=false;
    


  nutrition: any;
  steps:any;
  stepsLabels:any;
  stepFlag:boolean=false;
  nutFlag:boolean=false;
  Number:boolean=false;
  
  freqTestIp:any;
  recentReportIp:any;
  freqTest:any;
  recentReports:any;

  noFreqTest: boolean = false;
  noRecentReport: boolean = false;
  activityIp: any;

  
  activitySample: boolean=false;
  nutriSample: boolean=false;

  constructor(private appService:AppServiceService, public router: Router) { }

  ngOnInit(): void {
    this.freqTestIp={
      consultationId:this.appService.getConsultationId(),
      lowerLimit:0,
      count:4
    };
    this.recentReportIp={
      consultationId:this.appService.getConsultationId(),
      lowerLimit:0,
      count:4
    };
    this.freqTest=[];
    this.recentReports=[];
    this.getTests(this.freqTestIp);
    this.getReports(this.recentReportIp);

    this.activityIp={
      consultationId:this.appService.getConsultationId(),
    }


    this.nutrition={
      "nutrition": {
          "fat.total": [
              40.0
          ],
          "protein": [
              2.0
          ],
          "carbs.total": [
              16.0
          ],
          "calories": [
              800.0
          ],
          "sugar": [
              1.0]
            }
  };
  this.steps={
    "steps": [
        2658,
        2340,
        1390,
        2759,
        1248,
        1890,
        4329
  ]
};


this.stepsLabels=["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
  

    this.getNutritionInfo(this.activityIp);
    this.getStepsInfo(this.activityIp);
  }




  getStepsInfo(input:any) {
    this.appService.postRequest("/user/steps",input).subscribe(
      (data)=>{
        console.log(data);
        if(data!=null){
          this.steps=data;
          this.activitySample=false;
        }
          else{
          this.activitySample=true;
          }
        
          let datasets:any=[{data:[],label:"Steps Data"}];
        let labels:any=[];
        
        for(let i in this.steps.steps){
          if(!isNaN(this.steps.steps[i])){
            datasets[0].data.push(parseFloat(this.steps.steps[i]));
            labels.push(this.stepsLabels[i]);
          }
        }
        this.testBarChartData.datasets=datasets;
        this.testBarChartData.labels=labels;
        this.stepFlag=true;
      }
    );
  }
  getNutritionInfo(input:any) {
    this.appService.postRequest("/user/nutrition",input).subscribe(
      (data)=>{
        console.log(data);
        if(data!=null){
          this.nutrition=data;  
         this.nutriSample=false;
        }
          else{
          this.nutriSample=true;
          }
          let labels=[];
        let chartData=[];
        for(let i in this.nutrition.nutrition){
          labels.push(i);
          let sum=0;
          for(let j in this.nutrition.nutrition[i]){
            sum+=this.nutrition.nutrition[i][j];
          }
          chartData.push(sum);
        }
        this.nutChartData.labels=labels;
        this.nutChartData.datasets[0].data=chartData;
        this.showNutrition=true;
      }
    );
  }



  
 getTests(input:any) {

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
          'count':testList[i].count
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

 getReports(input:any) {
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
          'reportDate':reportList[i].reportDate
        };
        this.recentReports.push(repoObj);
      }
      }
      else{
        this.noRecentReport=true;
      }
    }
  );
}

getTestDetails(testName:String){
  this.appService.lastRoute="Dashboard";
  this.appService.testName=testName;
  this.router.navigateByUrl("/timeline");
  
}
getReportDetails(reportId:string){
  this.appService.lastRoute="Dashboard";
  this.appService.reportId=reportId;
  this.router.navigateByUrl("/reportList");
}


}


