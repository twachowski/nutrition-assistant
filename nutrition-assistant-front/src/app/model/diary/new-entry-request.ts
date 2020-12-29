import {EntryType} from '../entries/entry-type.enum';
import {NewFoodEntry} from './new-food-entry';
import {NewExerciseEntry} from './new-exercise-entry';
import {NewNoteEntry} from './new-note-entry';

export class NewEntryRequest {

  readonly entryType: string;
  readonly foodEntry: NewFoodEntry;
  readonly exerciseEntry: NewExerciseEntry;
  readonly noteEntry: NewNoteEntry;

  constructor(entryType: string,
              foodEntry: NewFoodEntry,
              exerciseEntry: NewExerciseEntry,
              noteEntry: NewNoteEntry) {
    this.entryType = entryType;
    this.foodEntry = foodEntry;
    this.exerciseEntry = exerciseEntry;
    this.noteEntry = noteEntry;
  }

  static food(newFoodEntry: NewFoodEntry) {
    return new NewEntryRequest(
      EntryType[EntryType.FOOD],
      newFoodEntry,
      null,
      null);
  }

  static exercise(newExerciseEntry: NewExerciseEntry) {
    return new NewEntryRequest(
      EntryType[EntryType.EXERCISE],
      null,
      newExerciseEntry,
      null);
  }

  static note(newNoteEntry: NewNoteEntry) {
    return new NewEntryRequest(
      EntryType[EntryType.NOTE],
      null,
      null,
      newNoteEntry);
  }

}
