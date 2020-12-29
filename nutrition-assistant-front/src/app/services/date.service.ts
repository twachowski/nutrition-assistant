import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DateService {

  private date = new Subject<string>();

  private currentValue: string;

  constructor() {
    this.getDate().subscribe(newValue => this.currentValue = newValue);
  }

  getDate() {
    return this.date.asObservable();
  }

  setDate(date: string) {
    this.date.next(date);
  }

  getCurrentValue() {
    return this.currentValue;
  }

}
