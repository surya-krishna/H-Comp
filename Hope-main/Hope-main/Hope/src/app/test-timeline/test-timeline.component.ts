import { Component, OnInit, ViewChild } from '@angular/core';
import { AppServiceService } from '../app-service.service';
import { Router } from '@angular/router';

import {  ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-test-timeline',
  templateUrl: './test-timeline.component.html',
  styleUrls: ['./test-timeline.component.scss']
})
export class TestTimelineComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  testData: any;
  testName: any;
  numbers: boolean=false;
  countMap:any={};


  //barchart option
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

  constructor(private appService:AppServiceService,public router:Router) { }

  ngOnInit(): void {
    if(this.appService.testName!=""){
      this.testName=this.appService.testName;
      this.numbers=false;
      this.lastRoute=this.appService.lastRoute;
      this.getTestDetails(this.appService.testName);
    }
  }
  getTestDetails(testName: any) {
    let input={
      testName:testName,
      consultationId:this.appService.getConsultationId()
    }
    this.appService.postRequest("/user/getTestDetails",input).subscribe((data)=>{
      console.log(data);
      if(data.errFlag==0){
        this.testData = data.userTestsList;
        
        let datasets:any=[{data:[],label:"Test Data"}];
        let labels:any=[];
        
        for(let i in this.testData){
          if(!isNaN(this.testData[i].testValue)){
            datasets[0].data.push(parseFloat(this.testData[i].testValue));
            labels.push(this.testData[i].testDate);
          }
          else{

          }
        }
        this.testBarChartData.datasets=datasets;
        this.testBarChartData.labels=labels;
        this.numbers=true;
      }
    })
  }

  notNum(number:any){
    return isNaN(number); 
  }
}
