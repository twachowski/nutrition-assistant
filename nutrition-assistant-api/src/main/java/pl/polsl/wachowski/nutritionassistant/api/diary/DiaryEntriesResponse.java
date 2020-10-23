package pl.polsl.wachowski.nutritionassistant.api.diary;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntryDetails;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntryDetails;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntryDetails;

import java.util.Collections;
import java.util.Set;

@Value
public class DiaryEntriesResponse {

    public static final DiaryEntriesResponse EMPTY_INSTANCE = new DiaryEntriesResponse(Collections.emptySet(),
                                                                                       Collections.emptySet(),
                                                                                       Collections.emptySet());

    Set<FoodEntryDetails> foodEntries;
    Set<ExerciseEntryDetails> exerciseEntries;
    Set<NoteEntryDetails> noteEntries;

}
