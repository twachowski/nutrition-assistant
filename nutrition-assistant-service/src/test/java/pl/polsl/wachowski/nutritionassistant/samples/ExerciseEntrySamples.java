package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise.ExerciseEntry;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.math.BigDecimal;

public final class ExerciseEntrySamples {

    private ExerciseEntrySamples() {
    }

    public static ExerciseEntry exerciseEntry() {
        return new ExerciseEntry("running",
                                 TimeUnit.HOUR,
                                 BigDecimal.ONE);
    }

}
