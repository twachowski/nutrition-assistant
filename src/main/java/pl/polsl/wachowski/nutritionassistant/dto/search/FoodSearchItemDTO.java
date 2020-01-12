package pl.polsl.wachowski.nutritionassistant.dto.search;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.NutritionDataProvider;

@Value
public class FoodSearchItemDTO {

    String name;

    String brandName;

    String externalId;

    NutritionDataProvider provider;

}
