import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-calorie-detail',
  templateUrl: './calorie-detail.component.html',
  styleUrls: ['./calorie-detail.component.css']
})
export class CalorieDetailComponent implements OnInit {

  @Input() readonly amount: number;
  @Input() readonly label: string;

  constructor() { }

  ngOnInit() {
  }

}
