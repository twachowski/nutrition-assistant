package pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class EditedExerciseEntry {

    @NotNull(message = "Time unit must not be null")
    TimeUnit timeUnit;

    @NotNull(message = "Duration must not be null")
    @Positive(message = "Duration must be positive")
    BigDecimal duration;

}
