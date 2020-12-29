import { Component, OnInit } from '@angular/core';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import * as moment from 'moment';
import { DateService } from 'src/app/services/date.service';

export const DATE_FORMATS = {
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY'
  },
};

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css'],
  providers: [{ provide: MAT_DATE_FORMATS, useValue: DATE_FORMATS }]
})
export class DatepickerComponent implements OnInit {

  private static readonly outputDateFormat = 'YYYY-MM-DD';

  date = new FormControl({ value: moment(), disabled: true });

  constructor(private readonly dateService: DateService) {
    this.date.valueChanges.subscribe(value => this.dateService.setDate(this.getDate()));
    this.date.setValue(moment());
  }

  ngOnInit() {
  }

  nextDay() {
    const nextDay = this.date.value.add(1, 'days');
    this.date.setValue(nextDay);
  }

  previousDay() {
    const previousDay = this.date.value.subtract(1, 'days');
    this.date.setValue(previousDay);
  }

  getDate() {
    return moment(this.date.value).format(DatepickerComponent.outputDateFormat);
  }

}
