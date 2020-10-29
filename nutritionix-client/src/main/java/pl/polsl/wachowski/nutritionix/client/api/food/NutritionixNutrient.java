package pl.polsl.wachowski.nutritionix.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixNutrient {

    @JsonProperty("attr_id")
    int id;

    @JsonProperty("value")
    float amount;

}
