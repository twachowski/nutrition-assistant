import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-error-snack-bar',
  template: '{{message}}',
  styleUrls: ['./error-snack-bar.component.css']
})
export class ErrorSnackBarComponent implements OnInit {

  constructor(@Inject(MAT_SNACK_BAR_DATA) public message: string) { }

  ngOnInit() {
  }

}
