import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { AppServiceService } from '../app-service.service';
import { Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
const VIEWER_ID = 'file-viewer-root';


interface CustomBravaEvent extends CustomEvent<string> {
  source: Window;
}


@Component({
  selector: 'app-viewer',
  templateUrl: './viewer.component.html',
  styleUrls: ['./viewer.component.scss']
})
export class ViewerComponent implements OnInit {
  @Input() viewerInput: any; // Replace 'any' with the appropriate type
  @Output() newItemEvent = new EventEmitter<any>();

  private bravaApi: any; // Replace 'any' with the actual type of the Brava API object
  private bravaReadySubscription: Subscription | undefined;
  didMountRef: any={
    current:false
  };
  openToken: any;
  
  inputData: any;
  token: any;
  viewer: any;
  viewdata: any;
  showLoader:boolean=false;
  progress:string="";

  constructor(
    private http: HttpClient,
    private appService: AppServiceService ,
    private router:Router// Replace with your authentication service
  ) { }

  ngOnInit(): void {
    this.generateHTML();
    this.showLoader=true;
    this.progress="Initiating Viewer";
  }
  generateHTML() {
    this.appService.getRequest("/user/openToken").subscribe((data)=>{
      localStorage.setItem("openToken",data);
      this.appService.openToken=data.token;
      this.token=data.token;
      this.getJSScript();
    }); 
  }
  getJSScript() {
    this.progress="Fetching Scripts";
    this.appService.getRequest("/user/viewer").subscribe((data)=>{
    let script= data.JS.replaceAll("\n",";");
    script=script.replaceAll(";;",";");
    const scriptEl = document.createElement('script');
    scriptEl.appendChild(document.createTextNode(script));
    document.getElementsByTagName('head')[0].appendChild(scriptEl);
    console.log(script);
    if(!this.viewdata){
    this.initalizeViewer();
    }
    this.getPublication();
    })
  }
  getPublication() {
    this.progress="Downloading Content";
    this.inputData=this.appService.viewerIp;
    this.appService.postRequest("/user/downloadContent",this.inputData).subscribe(
      (data)=>{
        this.viewdata=data;
        //this.initalizeViewer();
        this.render();
        this.showLoader=false;
      }
    );
  }
  render() {
    this.viewer.setHttpHeaders({ "Authorization": "Bearer " + this.token });
    this.viewer.setSearchHost((window as any).ViewerAuthority);
    this.viewer.addPublication(this.viewdata,true,null);
    if(localStorage.getItem("role")=="role_doctor"){
      this.viewer.setDownloadUrl("",null);
    }
    this.viewer.setScreenWatermark("Confidental");
    this.viewer.render("file-viewer-root");
  }
  initalizeViewer(){
    window.addEventListener("bravaReady",(event)=> this.onBravaReady(event,this.token))
        let tok=this.token;
        
  }
   onBravaReady(e:any,token:any) {
    this.progress="Rendering content";
    (window as any).eventX=e;
    console.log("Received viewer API name:", e.detail)
    console.log(e);
    this.viewer = window[e.detail];
    window.addEventListener(`${e.detail}-close`, () => {this.router.navigateByUrl("/reportList");});
  }

  closeDialogEventListener(){
    this.router.navigateByUrl("/reportList");
      /*var dom = document.getElementById("file-viewer-root");
      if(dom){
        dom.innerHTML="";
        let value={
          reportId:"",
          password:""
        };
        
      }*/
    }
}


