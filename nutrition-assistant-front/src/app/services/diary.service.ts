import { Injectable } from '@angular/core';
import { DiaryEntriesRequest } from '../model/diary/diary-entries-request';
import { HttpClient } from '@angular/common/http';
import { DiaryEntriesResponse } from '../model/diary/diary-entries-response';
import { NewFoodEntry } from '../model/diary/add/new-food-entry';
import { NewExerciseEntry } from '../model/diary/add/new-exercise-entry';
import { NewNoteEntry } from '../model/diary/add/new-note-entry';
import { EditedFoodEntry } from '../model/diary/edit/edited-food-entry';
import { EntryRequest } from '../model/diary/edit/entry-request';
import { EditedExerciseEntry } from '../model/diary/edit/edited-exercise-entry';
import { EditedNoteEntry } from '../model/diary/edit/edited-note-entry';
import { ReorderRequest } from '../model/diary/reorder/reorder-request';
import { PositionChange } from '../model/diary/reorder/position-change';
import { EntryDeleteRequest } from '../model/diary/delete/entry-delete-request';
import { RoutingService } from './routing.service';

@Injectable({
  providedIn: 'root'
})
export class DiaryService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) { }

  getDiaryEntries(date: string) {
    const url = this.routingService.getDiaryUrl();
    const request: DiaryEntriesRequest = {
      diaryDate: date
    };
    const body = JSON.stringify(request);
    return this.http.post<DiaryEntriesResponse>(url, body);
  }

  addFoodEntry(date: string, foodEntry: NewFoodEntry) {
    const url = this.routingService.getFoodAddUrl();
    const request: EntryRequest<NewFoodEntry> = {
      diaryDate: date,
      entry: foodEntry
    };
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  addExerciseEntry(date: string, exerciseEntry: NewExerciseEntry) {
    const url = this.routingService.getExerciseAddUrl();
    const request: EntryRequest<NewExerciseEntry> = {
      diaryDate: date,
      entry: exerciseEntry
    };
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  addNoteEntry(date: string, noteEntry: NewNoteEntry) {
    const url = this.routingService.getNoteAddUrl();
    const request: EntryRequest<NewNoteEntry> = {
      diaryDate: date,
      entry: noteEntry
    };
    const body = JSON.stringify(request);
    return this.http.post(url, body);
  }

  editFood(date: string, foodEntry: EditedFoodEntry) {
    const url = this.routingService.getFoodEditUrl();
    const request: EntryRequest<EditedFoodEntry> = {
      diaryDate: date,
      entry: foodEntry
    };
    const body = JSON.stringify(request);
    return this.http.put(url, body);
  }

  editExercise(date: string, exerciseEntry: EditedExerciseEntry) {
    const url = this.routingService.getExerciseEditUrl();
    const request: EntryRequest<EditedExerciseEntry> = {
      diaryDate: date,
      entry: exerciseEntry
    };
    const body = JSON.stringify(request);
    return this.http.put(url, body);
  }

  editNote(date: string, noteEntry: EditedNoteEntry) {
    const url = this.routingService.getNoteEditUrl();
    const request: EntryRequest<EditedNoteEntry> = {
      diaryDate: date,
      entry: noteEntry
    };
    const body = JSON.stringify(request);
    return this.http.put(url, body);
  }

  deleteEntry(date: string, index: number) {
    const url = this.routingService.getEntryDeleteUrl();
    const request: EntryDeleteRequest = {
      diaryDate: date,
      entryPosition: index
    };
    const httpBody = JSON.stringify(request);
    const httpOptions = {
      body: httpBody
    };
    return this.http.request('DELETE', url, httpOptions);
  }

  reorderEntries(date: string, previousIndex: number, currentIndex: number) {
    const url = this.routingService.getReorderUrl();
    const posChange: PositionChange = {
      previousPosition: previousIndex,
      currentPosition: currentIndex
    };
    const request: ReorderRequest = {
      diaryDate: date,
      positionChange: posChange
    };
    const body = JSON.stringify(request);
    return this.http.put(url, body);
  }

}
