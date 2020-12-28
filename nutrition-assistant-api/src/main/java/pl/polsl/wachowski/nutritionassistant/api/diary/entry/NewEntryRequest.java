package pl.polsl.wachowski.nutritionassistant.api.diary.entry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewEntryRequest {

    @NotNull
    EntryType entryType;
    FoodEntry foodEntry;
    ExerciseEntry exerciseEntry;
    NoteEntry noteEntry;

    public static NewEntryRequest food(final FoodEntry foodEntry) {
        return new NewEntryRequest(EntryType.FOOD,
                                   Objects.requireNonNull(foodEntry,
                                                          "Food entry must not be null in new food entry request"),
                                   null,
                                   null);
    }

    public static NewEntryRequest exercise(final ExerciseEntry exerciseEntry) {
        return new NewEntryRequest(EntryType.EXERCISE,
                                   null,
                                   Objects.requireNonNull(exerciseEntry,
                                                          "Exercise entry must not be null in new exercise entry request"),
                                   null);
    }

    public static NewEntryRequest note(final NoteEntry noteEntry) {
        return new NewEntryRequest(EntryType.NOTE,
                                   null,
                                   null,
                                   Objects.requireNonNull(noteEntry,
                                                          "Note entry must not be null in new note entry request"));
    }

    @AssertFalse(message = "Food entry must not be null in new food entry request")
    private boolean isFoodRequestInvalid() {
        return entryType.equals(EntryType.FOOD) && foodEntry == null;
    }

    @AssertFalse(message = "Exercise entry must not be null in new exercise entry request")
    private boolean isExerciseRequestInvalid() {
        return entryType.equals(EntryType.EXERCISE) && exerciseEntry == null;
    }

    @AssertFalse(message = "Note entry must not be null in new note entry request")
    private boolean isNoteRequestInvalid() {
        return entryType.equals(EntryType.NOTE) && noteEntry == null;
    }

}
