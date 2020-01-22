package pl.polsl.wachowski.nutritionassistant.dto.diary.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.FoodMeasureUnit;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class FoodEntryDTO {

    @NotBlank(message = "External id must not be blank")
    String externalId;

    @NotBlank(message = "Provider must not be blank")
    NutritionDataProvider provider;

    @NotNull(message = "Amount must not be null")
    BigDecimal amount;

    @NotBlank(message = "Unit must not be null")
    FoodMeasureUnit unit;

    @NotNull(message = "Position must not be null")
    Short position;

}
