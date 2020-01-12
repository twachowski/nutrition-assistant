package pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class NutritionixSearchResultDTO {

    @JsonProperty("common")
    List<NutritionixCommonFoodItemDTO> commonFoods;

    @JsonProperty("branded")
    List<NutritionixBrandedFoodItemDTO> brandedFoods;

}
