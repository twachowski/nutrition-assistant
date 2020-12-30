package pl.polsl.wachowski.nutritionassistant.api.diary.entry;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.validation.Secondary;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@GroupSequence({EntryEditRequest.class, Secondary.class})
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntryEditRequest {

    @NotNull(message = "Entry type must not be null")
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

    @AssertFalse(message = "Edited food entry must not be null in food entry edit request",
                 groups = Secondary.class)
    private boolean isFoodRequestInvalid() {
        return entryType.equals(EntryType.FOOD) && editedFoodEntry == null;
    }

    @AssertFalse(message = "Edited exercise entry must not be null in exercise entry edit request",
                 groups = Secondary.class)
    private boolean isExerciseRequestInvalid() {
        return entryType.equals(EntryType.EXERCISE) && editedExerciseEntry == null;
    }

    @AssertFalse(message = "Edited note entry must not be null in note entry edit request",
                 groups = Secondary.class)
    private boolean isNoteRequestInvalid() {
        return entryType.equals(EntryType.NOTE) && editedNoteEntry == null;
    }

}
