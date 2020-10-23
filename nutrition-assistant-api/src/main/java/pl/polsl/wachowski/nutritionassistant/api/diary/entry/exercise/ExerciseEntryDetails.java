package pl.polsl.wachowski.nutritionassistant.api.diary.entry.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.math.BigDecimal;

@Value
public class ExerciseEntryDetails {

    String name;
    TimeUnit timeUnit;
    BigDecimal duration;
    BigDecimal calories;
    Short position;

}
