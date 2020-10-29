package pl.polsl.wachowski.nutritionix.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Set;

@Value
public class NutritionixFood {

    @JsonProperty("food_name")
    String name;

    @JsonProperty("brand_name")
    String brandName;

    @JsonProperty("full_nutrients")
    Set<NutritionixNutrient> nutrients;

}
