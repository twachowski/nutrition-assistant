import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-general-progress',
  templateUrl: './general-progress.component.html',
  styleUrls: ['./general-progress.component.css']
})
export class GeneralProgressComponent implements OnInit {

  @Input() readonly progress: number;
  @Input() readonly label: string;

  constructor() { }

  ngOnInit() {
  }

}
