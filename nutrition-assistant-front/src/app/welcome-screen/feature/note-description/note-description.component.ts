import { Component, OnInit } from '@angular/core';
import { FeatureDescriptionComponent } from '../feature-description/feature-description.component';

@Component({
  selector: 'app-note-description',
  templateUrl: './note-description.component.html',
  styleUrls: ['./note-description.component.css']
})
export class NoteDescriptionComponent extends FeatureDescriptionComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
