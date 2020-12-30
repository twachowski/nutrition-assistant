package pl.polsl.wachowski.nutritionassistant.api.diary.entry.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class EditedFoodEntry {

    @NotNull(message = "Mass unit must not be null")
    FoodMassUnit massUnit;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be positive")
    BigDecimal amount;

}
