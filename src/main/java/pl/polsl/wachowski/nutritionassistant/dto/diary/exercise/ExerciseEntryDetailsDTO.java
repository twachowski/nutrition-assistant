package pl.polsl.wachowski.nutritionassistant.dto.diary.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.TimeUnit;

import java.math.BigDecimal;

@Value
public class ExerciseEntryDetailsDTO {

    String name;

    BigDecimal duration;

    TimeUnit unit;

    BigDecimal calories;

    Short position;

}
