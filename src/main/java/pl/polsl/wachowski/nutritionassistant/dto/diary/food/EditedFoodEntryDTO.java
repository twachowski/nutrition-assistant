package pl.polsl.wachowski.nutritionassistant.dto.diary.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.FoodMeasureUnit;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class EditedFoodEntryDTO {

    @NotNull(message = "Amount must not be null")
    BigDecimal amount;

    @NotNull(message = "Unit must not be null")
    FoodMeasureUnit unit;

    @NotNull(message = "Position must not be null")
    Short position;

}
