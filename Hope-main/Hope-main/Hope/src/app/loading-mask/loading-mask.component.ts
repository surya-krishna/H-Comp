import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-loading-mask',
  templateUrl: './loading-mask.component.html',
  styleUrls: ['./loading-mask.component.scss']
})
export class LoadingMaskComponent implements OnInit {
  @Input() progress: String="Progress";
  constructor() { }

  ngOnInit(): void {
  }

}
