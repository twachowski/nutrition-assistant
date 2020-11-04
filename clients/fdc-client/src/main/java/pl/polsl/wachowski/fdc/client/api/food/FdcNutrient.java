package pl.polsl.wachowski.fdc.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FdcNutrient {

    @JsonProperty("nutrient")
    FdcNutrientDetails details;
    float amount;

    public int getId() {
        return details.id;
    }

    @Value
    public static class FdcNutrientDetails {
        int id;
    }

}
