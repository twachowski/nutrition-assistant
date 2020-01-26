package pl.polsl.wachowski.nutritionassistant.dto.diary.exercise;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.TimeUnit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class ExerciseEntryDTO {

    @NotBlank(message = "Name must not be blank")
    String name;

    @NotNull(message = "Amount must not be null")
    BigDecimal amount;

    @NotNull(message = "Time unit must not be null")
    TimeUnit unit;

    @NotNull(message = "Position must not be null")
    Short position;

}
