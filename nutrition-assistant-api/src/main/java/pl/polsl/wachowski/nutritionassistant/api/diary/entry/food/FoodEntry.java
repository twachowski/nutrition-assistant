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

    @NotBlank
    String id;

    @NotNull
    NutritionDataProvider nutritionDataProvider;

    @NotNull
    FoodMassUnit massUnit;

    @NotNull
    @Positive
    BigDecimal amount;

}
