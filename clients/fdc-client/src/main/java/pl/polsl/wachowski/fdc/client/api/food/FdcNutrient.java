package pl.polsl.wachowski.fdc.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FdcNutrient {

    @JsonProperty("nutrient")
    FdcNutrientDetails details;
    Float amount;

    public int getId() {
        return details.id;
    }

    public float getAmount() {
        return amount == null ? 0.f : amount;
    }

    @Value
    public static class FdcNutrientDetails {
        int id;
    }

}
