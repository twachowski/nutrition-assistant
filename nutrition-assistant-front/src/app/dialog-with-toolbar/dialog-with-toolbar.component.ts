import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-dialog-with-toolbar',
  templateUrl: './dialog-with-toolbar.component.html',
  styleUrls: ['./dialog-with-toolbar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogWithToolbarComponent implements OnInit {

  @Input() private readonly title: string;

  constructor() {
  }

  ngOnInit() {
  }

}
