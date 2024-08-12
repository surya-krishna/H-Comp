import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-plain-header',
  templateUrl: './plain-header.component.html',
  styleUrls: ['./plain-header.component.scss']
})
export class PlainHeaderComponent implements OnInit {

  constructor(public router: Router) { }

  ngOnInit() {
  }

}
