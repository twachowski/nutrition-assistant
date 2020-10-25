package pl.polsl.wachowski.nutritionassistant.fdc.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FdcNutrient {

    @JsonProperty("nutrient")
    FdcNutrientDetails details;
    float amount;

    @Value
    public static class FdcNutrientDetails {
        int id;
    }

}
