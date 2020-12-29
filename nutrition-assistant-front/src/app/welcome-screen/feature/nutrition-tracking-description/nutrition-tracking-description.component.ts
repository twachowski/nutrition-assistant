import { Component, OnInit } from '@angular/core';
import { FeatureDescriptionComponent } from '../feature-description/feature-description.component';

@Component({
  selector: 'app-nutrition-tracking-description',
  templateUrl: './nutrition-tracking-description.component.html',
  styleUrls: ['./nutrition-tracking-description.component.css']
})
export class NutritionTrackingDescriptionComponent extends FeatureDescriptionComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
