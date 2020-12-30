package pl.polsl.wachowski.nutritionassistant.api.diary.entry.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class FoodEntry {

    @NotBlank(message = "Id must not be blank")
    String id;

    @NotNull(message = "Provider must not be null")
    NutritionDataProvider nutritionDataProvider;

    @NotNull(message = "Mass unit must not be null")
    FoodMassUnit massUnit;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be positive")
    BigDecimal amount;

}
