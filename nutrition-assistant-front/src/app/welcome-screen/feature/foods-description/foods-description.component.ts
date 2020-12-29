import { Component, OnInit } from '@angular/core';
import { FeatureDescriptionComponent } from '../feature-description/feature-description.component';

@Component({
  selector: 'app-foods-description',
  templateUrl: './foods-description.component.html',
  styleUrls: ['./foods-description.component.css']
})
export class FoodsDescriptionComponent extends FeatureDescriptionComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
