package pl.polsl.wachowski.nutritionassistant.util;

import org.apache.commons.lang3.StringUtils;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.EntryType;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.NewEntryRequest;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.diary.entry.note.NoteEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

public final class NewEntryRequestSamples {

    private NewEntryRequestSamples() {
    }

    public static NewEntryRequest withAllNullFields() {
        return create(null, null, null, null);
    }

    public static NewEntryRequest nullFoodRequest() {
        return create(EntryType.FOOD, null, null, null);
    }

    public static NewEntryRequest nullIdFoodRequest() {
        return NewEntryRequest.food(new FoodEntry(null,
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest emptyIdFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest blankIdFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("         ",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest nullProviderFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  null,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest nullMassUnitFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  NutritionDataProvider.USDA,
                                                  null,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest nullAmountFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  null));
    }

    public static NewEntryRequest negativeAmountFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.valueOf(-1)));
    }

    public static NewEntryRequest zeroAmountFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ZERO));
    }

    public static NewEntryRequest validFoodRequest() {
        return NewEntryRequest.food(new FoodEntry("12345",
                                                  NutritionDataProvider.USDA,
                                                  FoodMassUnit.GRAM,
                                                  BigDecimal.ONE));
    }

    public static NewEntryRequest nullExerciseRequest() {
        return create(EntryType.EXERCISE, null, null, null);
    }

    public static NewEntryRequest nullNameExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry(null,
                                                          TimeUnit.HOUR,
                                                          BigDecimal.ONE));
    }

    public static NewEntryRequest emptyNameExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("",
                                                          TimeUnit.HOUR,
                                                          BigDecimal.ONE));
    }

    public static NewEntryRequest blankNameExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("      ",
                                                          TimeUnit.HOUR,
                                                          BigDecimal.ONE));
    }

    public static NewEntryRequest nullTimeUnitExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("12345",
                                                          null,
                                                          BigDecimal.ONE));
    }

    public static NewEntryRequest nullDurationExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("12345",
                                                          TimeUnit.HOUR,
                                                          null));
    }

    public static NewEntryRequest negativeDurationExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("12345",
                                                          TimeUnit.HOUR,
                                                          BigDecimal.valueOf(-1)));
    }

    public static NewEntryRequest zeroDurationExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("12345",
                                                          TimeUnit.HOUR,
                                                          BigDecimal.ZERO));
    }

    public static NewEntryRequest validExerciseRequest() {
        return NewEntryRequest.exercise(new ExerciseEntry("12345",
                                                          TimeUnit.HOUR,
                                                          BigDecimal.ONE));
    }

    public static NewEntryRequest nullNoteRequest() {
        return create(EntryType.NOTE, null, null, null);
    }

    public static NewEntryRequest nullContentNoteRequest() {
        return NewEntryRequest.note(new NoteEntry(null));
    }

    public static NewEntryRequest emptyContentNoteRequest() {
        return NewEntryRequest.note(new NoteEntry(""));
    }

    public static NewEntryRequest blankContentNoteRequest() {
        return NewEntryRequest.note(new NoteEntry("       "));
    }

    public static NewEntryRequest noteRequestWith256ContentLength() {
        return NewEntryRequest.note(new NoteEntry(StringUtils.repeat("@", 256)));
    }

    public static NewEntryRequest validNoteRequest() {
        return NewEntryRequest.note(new NoteEntry(StringUtils.repeat("@", 255)));
    }

    private static NewEntryRequest create(final EntryType entryType,
                                          final FoodEntry foodEntry,
                                          final ExerciseEntry exerciseEntry,
                                          final NoteEntry noteEntry) {
        try {
            final Constructor<NewEntryRequest> constructor = NewEntryRequest.class.getDeclaredConstructor(EntryType.class,
                                                                                                          FoodEntry.class,
                                                                                                          ExerciseEntry.class,
                                                                                                          NoteEntry.class);
            constructor.setAccessible(true);
            return constructor.newInstance(entryType,
                                           foodEntry,
                                           exerciseEntry,
                                           noteEntry);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
