import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {

  success: boolean;

  constructor(
    private readonly route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data.subscribe(
      (data: { result: boolean }) => {
        this.success = data.result;
      }
    );
  }

}
