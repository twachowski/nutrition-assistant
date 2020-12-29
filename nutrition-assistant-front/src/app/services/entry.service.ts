import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { EntryDialogData } from '../model/entries/entry-dialog-data';
import { NewFoodDialogData } from '../diary-screen/activities/food-search-dialog/food-search-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class EntryService {

  private foodEntry = new Subject<NewFoodDialogData>();
  private exerciseEntry = new Subject<EntryDialogData>();
  private noteEntry = new Subject<string>();
  private entryCount = new Subject<number>();

  private currentEntryCount = 0;

  constructor() {
    this.getEntryCount().subscribe(newValue => this.currentEntryCount = newValue);
  }

  getFoodEntry() {
    return this.foodEntry.asObservable();
  }

  getExerciseEntry() {
    return this.exerciseEntry.asObservable();
  }

  getNoteEntry() {
    return this.noteEntry.asObservable();
  }

  getEntryCount() {
    return this.entryCount.asObservable();
  }

  setFoodEntry(entry: NewFoodDialogData) {
    this.foodEntry.next(entry);
  }

  setExerciseEntry(entry: EntryDialogData) {
    this.exerciseEntry.next(entry);
  }

  setNoteEntry(entry: string) {
    this.noteEntry.next(entry);
  }

  setEntryCount(count: number) {
    this.entryCount.next(count);
  }

  getCurrentEntryCount() {
    return this.currentEntryCount;
  }

}
