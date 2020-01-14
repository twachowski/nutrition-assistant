package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
class NutritionixFoodDTO {

    @JsonProperty("full_nutrients")
    List<NutritionixNutrientDTO> nutrients;

    @JsonCreator
    NutritionixFoodDTO(final List<NutritionixNutrientDTO> nutrients) {
        this.nutrients = nutrients;
    }

}
