import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { constants } from './app.constants';


interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'HOPE';
  isSideNavCollapsed = false;
  screenWidth = 0;
  blockUrls = constants.noSideNavRoutes;
  constructor(public router: Router) { }
  onToggleSideNav(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }
}
