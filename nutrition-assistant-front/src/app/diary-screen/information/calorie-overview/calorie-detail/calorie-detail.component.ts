import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-calorie-detail',
  templateUrl: './calorie-detail.component.html',
  styleUrls: ['./calorie-detail.component.css']
})
export class CalorieDetailComponent implements OnInit {

  @Input() private readonly amount: number;
  @Input() private readonly label: string;

  constructor() { }

  ngOnInit() {
  }

}
