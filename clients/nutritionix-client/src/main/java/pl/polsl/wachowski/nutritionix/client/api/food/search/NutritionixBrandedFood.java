package pl.polsl.wachowski.nutritionix.client.api.food.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixBrandedFood implements NutritionixBasicFood {

    @JsonProperty("food_name")
    String foodName;

    @JsonProperty("nix_brand_id")
    String brandId;

    @JsonProperty("brand_name")
    String brandName;

    @Override
    public String getId() {
        return brandId;
    }

}
