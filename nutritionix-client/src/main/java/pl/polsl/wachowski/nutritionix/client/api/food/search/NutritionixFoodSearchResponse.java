package pl.polsl.wachowski.nutritionix.client.api.food.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class NutritionixFoodSearchResponse {

    @JsonProperty("common")
    List<NutritionixCommonFood> commonFoods;

    @JsonProperty("branded")
    List<NutritionixBrandedFood> brandedFoods;

}
