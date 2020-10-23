package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.details.ExternalNutrientDetails;

@Value
public class NutritionixNutrientDTO implements ExternalNutrientDetails {

    @JsonProperty("attr_id")
    Integer id;

    @JsonProperty("value")
    Float amount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Float getAmount() {
        return amount;
    }

}
