package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
class NutritionixFoodDTO {

    @JsonProperty("food_name")
    String name;

    @JsonProperty("brand_name")
    String brandName;

    @JsonProperty("full_nutrients")
    List<NutritionixNutrientDTO> nutrients;

}
