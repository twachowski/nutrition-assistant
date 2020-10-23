package pl.polsl.wachowski.nutritionassistant.api.diary.entry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntryEditRequest {

    @NotNull
    EntryType entryType;
    EditedFoodEntry editedFoodEntry;
    EditedExerciseEntry editedExerciseEntry;
    NoteEntry editedNoteEntry;

    public static EntryEditRequest food(final EditedFoodEntry editedFoodEntry) {
        return new EntryEditRequest(EntryType.FOOD,
                                    Objects.requireNonNull(editedFoodEntry,
                                                           "Food entry must not be null in food edit request"),
                                    null,
                                    null);
    }

    public static EntryEditRequest exercise(final EditedExerciseEntry editedExerciseEntry) {
        return new EntryEditRequest(EntryType.EXERCISE,
                                    null,
                                    Objects.requireNonNull(editedExerciseEntry,
                                                           "Exercise entry must not be null in exercise edit request"),
                                    null);
    }

    public static EntryEditRequest note(final NoteEntry noteEntry) {
        return new EntryEditRequest(EntryType.NOTE,
                                    null,
                                    null,
                                    Objects.requireNonNull(noteEntry,
                                                           "Note entry must not be null in note edit request"));
    }

    @AssertFalse
    private boolean isFoodRequestInvalid() {
        return entryType.equals(EntryType.FOOD) && editedFoodEntry == null;
    }

    @AssertFalse
    private boolean isExerciseRequestInvalid() {
        return entryType.equals(EntryType.EXERCISE) && editedExerciseEntry == null;
    }

    @AssertFalse
    private boolean isNoteRequestInvalid() {
        return entryType.equals(EntryType.NOTE) && editedNoteEntry == null;
    }

}
