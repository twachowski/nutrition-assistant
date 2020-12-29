import {EntryType} from '../entries/entry-type.enum';
import {EditedFoodEntry} from './edited-food-entry';
import {EditedExerciseEntry} from './edited-exercise-entry';
import {EditedNoteEntry} from './edited-note-entry';

export class EntryEditRequest {

  readonly entryType: string;
  readonly editedFoodEntry: EditedFoodEntry;
  readonly editedExerciseEntry: EditedExerciseEntry;
  readonly editedNoteEntry: EditedNoteEntry;

  constructor(entryType: string,
              foodEntry: EditedFoodEntry,
              exerciseEntry: EditedExerciseEntry,
              noteEntry: EditedNoteEntry) {
    this.entryType = entryType;
    this.editedFoodEntry = foodEntry;
    this.editedExerciseEntry = exerciseEntry;
    this.editedNoteEntry = noteEntry;
  }

  static food(newFoodEntry: EditedFoodEntry) {
    return new EntryEditRequest(
      EntryType[EntryType.FOOD],
      newFoodEntry,
      null,
      null);
  }

  static exercise(newExerciseEntry: EditedExerciseEntry) {
    return new EntryEditRequest(
      EntryType[EntryType.EXERCISE],
      null,
      newExerciseEntry,
      null);
  }

  static note(newNoteEntry: EditedNoteEntry) {
    return new EntryEditRequest(
      EntryType[EntryType.NOTE],
      null,
      null,
      newNoteEntry);
  }

}
