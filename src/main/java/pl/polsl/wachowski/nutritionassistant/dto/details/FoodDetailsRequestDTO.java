package pl.polsl.wachowski.nutritionassistant.dto.details;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class FoodDetailsRequestDTO {

    @NotBlank(message = "Food id must not be blank")
    String externalId;

    @NotNull(message = "Provider must not be null")
    NutritionDataProvider provider;

}
