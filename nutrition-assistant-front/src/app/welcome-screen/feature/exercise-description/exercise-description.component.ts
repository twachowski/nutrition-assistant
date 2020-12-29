import { Component, OnInit } from '@angular/core';
import { FeatureDescriptionComponent } from '../feature-description/feature-description.component';

@Component({
  selector: 'app-exercise-description',
  templateUrl: './exercise-description.component.html',
  styleUrls: ['./exercise-description.component.css']
})
export class ExerciseDescriptionComponent extends FeatureDescriptionComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
