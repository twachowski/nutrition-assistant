package pl.polsl.wachowski.nutritionassistant.samples;

import org.apache.commons.lang3.StringUtils;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryEditRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryType;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.EditedExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.EditedFoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

public final class EntryEditRequestSamples {

    private EntryEditRequestSamples() {
    }

    public static EntryEditRequest withAllNullFields() {
        return create(null, null, null, null);
    }

    public static EntryEditRequest nullFoodRequest() {
        return create(EntryType.FOOD, null, null, null);
    }

    public static EntryEditRequest nullMassUnitFoodRequest() {
        return EntryEditRequest.food(new EditedFoodEntry(null, BigDecimal.ONE));
    }

    public static EntryEditRequest nullAmountFoodRequest() {
        return EntryEditRequest.food(new EditedFoodEntry(FoodMassUnit.GRAM, null));
    }

    public static EntryEditRequest negativeAmountFoodRequest() {
        return EntryEditRequest.food(new EditedFoodEntry(FoodMassUnit.GRAM, BigDecimal.valueOf(-1)));
    }

    public static EntryEditRequest zeroAmountFoodRequest() {
        return EntryEditRequest.food(new EditedFoodEntry(FoodMassUnit.GRAM, BigDecimal.ZERO));
    }

    public static EntryEditRequest validFoodRequest() {
        return EntryEditRequest.food(new EditedFoodEntry(FoodMassUnit.GRAM, BigDecimal.ONE));
    }

    public static EntryEditRequest nullExerciseRequest() {
        return create(EntryType.EXERCISE, null, null, null);
    }

    public static EntryEditRequest nullTimeUnitExerciseRequest() {
        return EntryEditRequest.exercise(new EditedExerciseEntry(null, BigDecimal.ONE));
    }

    public static EntryEditRequest nullDurationExerciseRequest() {
        return EntryEditRequest.exercise(new EditedExerciseEntry(TimeUnit.HOUR, null));
    }

    public static EntryEditRequest negativeDurationExerciseRequest() {
        return EntryEditRequest.exercise(new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.valueOf(-1)));
    }

    public static EntryEditRequest zeroDurationExerciseRequest() {
        return EntryEditRequest.exercise(new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.ZERO));
    }

    public static EntryEditRequest validExerciseRequest() {
        return EntryEditRequest.exercise(new EditedExerciseEntry(TimeUnit.HOUR, BigDecimal.ONE));
    }

    public static EntryEditRequest nullNoteRequest() {
        return create(EntryType.NOTE, null, null, null);
    }

    public static EntryEditRequest nullContentNoteRequest() {
        return EntryEditRequest.note(new NoteEntry(null));
    }

    public static EntryEditRequest emptyContentNoteRequest() {
        return EntryEditRequest.note(new NoteEntry(""));
    }

    public static EntryEditRequest blankContentNoteRequest() {
        return EntryEditRequest.note(new NoteEntry("     "));
    }

    public static EntryEditRequest noteRequestWith256ContentLength() {
        return EntryEditRequest.note(new NoteEntry(StringUtils.repeat("@", 256)));
    }

    public static EntryEditRequest validNoteRequest() {
        return EntryEditRequest.note(new NoteEntry(StringUtils.repeat("@", 255)));
    }

    private static EntryEditRequest create(final EntryType entryType,
                                           final EditedFoodEntry editedFoodEntry,
                                           final EditedExerciseEntry editedExerciseEntry,
                                           final NoteEntry editedNoteEntry) {
        try {
            final Constructor<EntryEditRequest> constructor = EntryEditRequest.class.getDeclaredConstructor(EntryType.class,
                                                                                                            EditedFoodEntry.class,
                                                                                                            EditedExerciseEntry.class,
                                                                                                            NoteEntry.class);
            constructor.setAccessible(true);
            return constructor.newInstance(entryType,
                                           editedFoodEntry,
                                           editedExerciseEntry,
                                           editedNoteEntry);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
