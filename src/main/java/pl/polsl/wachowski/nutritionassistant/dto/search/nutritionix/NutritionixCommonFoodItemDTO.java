package pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixCommonFoodItemDTO {

    @JsonProperty("food_name")
    String foodName;

    @JsonCreator
    public NutritionixCommonFoodItemDTO(final String foodName) {
        this.foodName = foodName;
    }

}
