import { Component, OnInit } from '@angular/core';
import { FeatureDescriptionComponent } from '../feature-description/feature-description.component';

@Component({
  selector: 'app-biometrics-and-goals-description',
  templateUrl: './biometrics-and-goals-description.component.html',
  styleUrls: ['./biometrics-and-goals-description.component.css']
})
export class BiometricsAndGoalsDescriptionComponent extends FeatureDescriptionComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
