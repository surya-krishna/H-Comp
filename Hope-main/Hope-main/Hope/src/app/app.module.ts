import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PlainHeaderComponent } from './plain-header/plain-header.component';
import { HeaderComponent } from './header/header.component';
import { HttpClientModule } from '@angular/common/http';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { NgChartsModule,NgChartsConfiguration } from 'ng2-charts';

import { LandingComponent } from './landing/landing.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { DoctorRegistrationComponent } from './doctor-registration/doctor-registration.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BodyComponent } from './body/body.component';
import { TestTimelineComponent } from './test-timeline/test-timeline.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AddReportComponent } from './add-report/add-report.component';
import { LoadingMaskComponent } from './loading-mask/loading-mask.component';
import { AddReportFormComponent } from './add-report-form/add-report-form.component';
import { ReportsListComponent } from './reports-list/reports-list.component';
import { TestListComponent } from './test-list/test-list.component';
import { ConsultationComponent } from './consultation/consultation.component';
import { ActivityComponent } from './activity/activity.component';
import { OAuthModule,OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from './auth.config';
import { ViewerComponent } from './viewer/viewer.component';
import { ChatComponent } from './chat/chat.component';


declare var $: any;

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PlainHeaderComponent,
    HeaderComponent,
    LandingComponent,
    UserRegistrationComponent,
    DoctorRegistrationComponent,
    SidenavComponent,
    BodyComponent,
    TestTimelineComponent,
    UserDashboardComponent,
    AddReportComponent,
    LoadingMaskComponent,
    AddReportFormComponent,
    ReportsListComponent,
    TestListComponent,
    ConsultationComponent,
    ActivityComponent,
    ViewerComponent,
    ChatComponent
  ],
  imports: [
    NgChartsModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FontAwesomeModule,
    OAuthModule.forRoot({resourceServer: {
      allowedUrls: ['https://www.googleapis.com'],
      sendAccessToken: true,
    },
    ...authConfig,}),

  ],
  providers: [
    //{ provide: NgChartsConfiguration, useValue: { generateColors: false }}
    OAuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
