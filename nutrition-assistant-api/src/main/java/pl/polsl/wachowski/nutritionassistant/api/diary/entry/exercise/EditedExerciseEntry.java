package pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class EditedExerciseEntry {

    @NotNull
    TimeUnit timeUnit;

    @NotNull
    @Positive
    BigDecimal duration;

}
