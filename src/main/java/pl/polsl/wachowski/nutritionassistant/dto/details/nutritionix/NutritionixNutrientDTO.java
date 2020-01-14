package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixNutrientDTO {

    @JsonProperty("attr_id")
    Integer id;

    @JsonProperty("value")
    Float amount;

}
