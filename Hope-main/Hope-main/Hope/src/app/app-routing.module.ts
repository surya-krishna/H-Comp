import { NgModule } from '@angular/core';
import { Routes, RouterModule, ActivatedRoute } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent} from './login/login.component';
import { LandingComponent } from './landing/landing.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { DoctorRegistrationComponent } from './doctor-registration/doctor-registration.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { TestTimelineComponent } from './test-timeline/test-timeline.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AddReportComponent } from './add-report/add-report.component';
import { AddReportFormComponent } from './add-report-form/add-report-form.component';
import { LoadingMaskComponent } from './loading-mask/loading-mask.component';
import { ReportsListComponent } from './reports-list/reports-list.component';
import { TestListComponent } from './test-list/test-list.component';
import { ConsultationComponent } from './consultation/consultation.component';
import { ActivityComponent } from './activity/activity.component';
import { ViewerComponent } from './viewer/viewer.component';


const routes: Routes = [
  //{path:'',redirectTo:'/main',pathMatch:'full'},
  {path:'',component:LandingComponent},
  {path:'login',component:LoginComponent},
  {path:'sidenav',component:SidenavComponent},
  {path:'timeline',component:TestTimelineComponent},
  {path:'userReg',component:UserRegistrationComponent},
  {path:'docReg',component:DoctorRegistrationComponent},
  {path:'userDashboard',component:UserDashboardComponent},
  {path:'addReport',component:AddReportComponent},
  {path:'addReportForm',component:AddReportFormComponent},
  {path:'mask',component:LoadingMaskComponent},
  {path:'reportList',component:ReportsListComponent},
  {path:'testList',component:TestListComponent},
  {path:'consultation',component:ConsultationComponent},
  {path:'doctorDashboard',component:ConsultationComponent},
  {path:'activity',component:ActivityComponent},
  {path:'viewer',component:ViewerComponent},
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    FormsModule,
    BrowserModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
