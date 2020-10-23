package pl.polsl.wachowski.nutritionassistant.api.diary.entry.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class EditedFoodEntry {

    @NotNull
    FoodMassUnit massUnit;

    @NotNull
    @Positive
    BigDecimal amount;

}
