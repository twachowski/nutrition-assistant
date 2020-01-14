package pl.polsl.wachowski.nutritionassistant.dto.details;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

import javax.validation.constraints.NotBlank;

@Value
public class FoodDetailsRequestDTO {

    @NotBlank(message = "Food id must not be blank")
    String externalId;

    NutritionDataProvider provider;

}
