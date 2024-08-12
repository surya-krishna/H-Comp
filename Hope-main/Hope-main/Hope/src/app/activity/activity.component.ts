import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../authservice';
import { authConfig } from '../auth.config';
import { AppServiceService } from '../app-service.service';
import {Router, ActivatedRoute, Params} from '@angular/router';


import {  ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';

@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss']
})
export class ActivityComponent implements OnInit {
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
    


  authInput: any;
  nutrition: any;
  steps:any;
  stepsLabels:any;
  stepFlag:boolean=false;
  nutFlag:boolean=false;
  Number:boolean=false;
  activitySample: boolean=false;
  nutriSample: boolean=false;
  activityIp: any;
    //barchart option
  constructor(private appService:AppServiceService,private router:Router,private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    
    let path = this.router.url;
    let token=localStorage.getItem("Token");
    if(token){
      this.appService.authToken=token;
    }
    this.authInput={
      accessToken:'',
      tokenType:'', 
      expiresIn:'',
      scope:'https://www.googleapis.com/auth/fitness.activity.read https://www.googleapis.com/auth/fitness.body.read https://www.googleapis.com/auth/fitness.nutrition.read', 
    }
    if(path.indexOf("#")!=-1 && path.indexOf("&")!=-1){
      this.authInput.accessToken=path.split("#")[1].split("&")[1].split("=")[1];
      this.authInput.expiresIn=parseInt(path.split("#")[1].split("&")[3].split("=")[1]);
      this.authInput.tokenType=path.split("#")[1].split("&")[2].split("=")[1];
      this.appService.gAuthToken=this.authInput.accessToken;
      this.addAuthToken();
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
            },
         
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
  ],

};
this.activityIp={
  consultationId:this.appService.getConsultationId(),
}

this.stepsLabels=["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
  

    this.getNutritionInfo();
    this.getStepsInfo();
  }
  getStepsInfo() {
    this.appService.postRequest("/user/steps",this.activityIp).subscribe(
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
  getNutritionInfo() {
    this.appService.postRequest("/user/nutrition",this.activityIp).subscribe(
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





  addAuthToken() {
    this.appService.postRequest("/user/add-google-token",this.authInput).subscribe(
      (data)=>{
        console.log(data);
      }
    );
  }
  
   login(){
  //   var oauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';
  //   let str="";
  //   for(let i in authConfig){
  //     str+=i+"="+authConfig+"&";
  //   }
  //   let final=oauth2Endpoint+"?"+str.substring(0,str.length-1);
  //   this.appService.getRequest(final);

  var oauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';

  // Create <form> element to submit parameters to OAuth 2.0 endpoint.
  var form = document.createElement('form');
  form.setAttribute('method', 'GET'); // Send as a GET request.
  form.setAttribute('action', oauth2Endpoint);

  // Parameters to pass to OAuth 2.0 endpoint.
  var params = {'client_id': '606375098538-b2tn5mllvk7dr3na9qpb7nbqfv9bml73.apps.googleusercontent.com',//'866839830909-rmfmibp8grentrnfl91hlurmqqu4mf75.apps.googleusercontent.com',
                'redirect_uri': 'http://localhost:4200/activity',
                'response_type': 'token',
                'scope': 'https://www.googleapis.com/auth/fitness.activity.read https://www.googleapis.com/auth/fitness.body.read https://www.googleapis.com/auth/fitness.nutrition.read',
                'include_granted_scopes': 'true',
                'state': 'pass-through value'};

  // Add form parameters as hidden input values.
  for (let p in params) {
    var input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', p);
    input.setAttribute('value', params[p as keyof typeof params] );
    form.appendChild(input);
  }

  // Add form to page and submit it to open the OAuth 2.0 endpoint.
  document.body.appendChild(form);
  form.submit();
  }

}
