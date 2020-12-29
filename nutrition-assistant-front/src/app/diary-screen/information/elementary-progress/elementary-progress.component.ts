import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-elementary-progress',
  templateUrl: './elementary-progress.component.html',
  styleUrls: ['./elementary-progress.component.css']
})
export class ElementaryProgressComponent implements OnInit {

  @Input() private readonly progress: number;
  @Input() private readonly label: string;

  constructor() { }

  ngOnInit() {
  }

}
