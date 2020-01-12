package pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixBrandedFoodItemDTO {

    @JsonProperty("food_name")
    String foodName;

    @JsonProperty("nix_brand_id")
    String brandId;

    @JsonProperty("brand_name")
    String brandName;

}
