import { FoodEntryDetails } from './food-entry-details';
import { ExerciseEntryDetails } from './exercise-entry-details';
import { NoteEntryDetails } from './note-entry-details';

export interface DiaryEntriesResponse {

    readonly foodEntries: FoodEntryDetails[];
    readonly exerciseEntries: ExerciseEntryDetails[];
    readonly noteEntries: NoteEntryDetails[];

}
