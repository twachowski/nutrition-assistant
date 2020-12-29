import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {DiaryEntriesResponse} from '../model/diary/diary-entries-response';
import {NewFoodEntry} from '../model/diary/new-food-entry';
import {NewExerciseEntry} from '../model/diary/new-exercise-entry';
import {NewNoteEntry} from '../model/diary/new-note-entry';
import {EditedFoodEntry} from '../model/diary/edited-food-entry';
import {EditedExerciseEntry} from '../model/diary/edited-exercise-entry';
import {EditedNoteEntry} from '../model/diary/edited-note-entry';
import {RoutingService} from './routing.service';
import {NewEntryRequest} from '../model/diary/new-entry-request';
import {EntryEditRequest} from '../model/diary/entry-edit-request';
import {EntryMoveRequest} from '../model/diary/entry-move-request';

@Injectable({
  providedIn: 'root'
})
export class DiaryService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) {
  }

  getDiaryEntries(date: string) {
    const url = this.routingService.getDiaryUrl(date);
    return this.http.get<DiaryEntriesResponse>(url);
  }

  addFoodEntry(date: string, foodEntry: NewFoodEntry) {
    const url = this.routingService.getEntriesUrl(date);
    const request = NewEntryRequest.food(foodEntry);
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  addExerciseEntry(date: string, exerciseEntry: NewExerciseEntry) {
    const url = this.routingService.getEntriesUrl(date);
    const request = NewEntryRequest.exercise(exerciseEntry);
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  addNoteEntry(date: string, noteEntry: NewNoteEntry) {
    const url = this.routingService.getEntriesUrl(date);
    const request = NewEntryRequest.note(noteEntry);
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  editFood(date: string, position: number, foodEntry: EditedFoodEntry) {
    const url = this.routingService.getEntryUrl(date, position);
    const request = EntryEditRequest.food(foodEntry);
    const body = JSON.stringify(request);
    return this.http.patch(url, body);
  }

  editExercise(date: string, position: number, exerciseEntry: EditedExerciseEntry) {
    const url = this.routingService.getEntryUrl(date, position);
    const request = EntryEditRequest.exercise(exerciseEntry);
    const body = JSON.stringify(request);
    return this.http.patch(url, body);
  }

  editNote(date: string, position: number, noteEntry: EditedNoteEntry) {
    const url = this.routingService.getEntryUrl(date, position);
    const request = EntryEditRequest.note(noteEntry);
    const body = JSON.stringify(request);
    return this.http.patch(url, body);
  }

  deleteEntry(date: string, position: number) {
    const url = this.routingService.getEntryUrl(date, position);
    return this.http.delete(url);
  }

  reorderEntries(date: string, previousPosition: number, currentPosition: number) {
    const url = this.routingService.getEntriesUrl(date);
    const request: EntryMoveRequest = {
      previousPosition,
      currentPosition
    };
    const body = JSON.stringify(request);
    return this.http.patch(url, body);
  }

}
