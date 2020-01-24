package pl.polsl.wachowski.nutritionassistant.dto.diary.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.TimeUnit;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class EditedExerciseEntryDTO {

    @NotNull(message = "Duration must not be null")
    BigDecimal duration;

    @NotNull(message = "Unit must not be null")
    TimeUnit unit;

    @NotNull(message = "Position must not be null")
    Short position;

}
