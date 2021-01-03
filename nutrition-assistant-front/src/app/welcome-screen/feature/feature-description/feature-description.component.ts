import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-feature-description',
  templateUrl: './feature-description.component.html',
  styleUrls: ['./feature-description.component.css']
})
export class FeatureDescriptionComponent implements OnInit {

  @Input() readonly icon: string;

  constructor() { }

  ngOnInit() {
  }

}
