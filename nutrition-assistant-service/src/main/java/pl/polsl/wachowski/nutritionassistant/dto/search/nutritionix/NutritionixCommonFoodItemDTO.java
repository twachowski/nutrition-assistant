package pl.polsl.wachowski.nutritionassistant.dto.search.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixCommonFoodItemDTO implements NutritionixFood {

    @JsonProperty("food_name")
    String foodName;

    @JsonCreator
    public NutritionixCommonFoodItemDTO(final String foodName) {
        this.foodName = foodName;
    }

    @Override
    public String getId() {
        return foodName;
    }

    @Override
    public String getBrandName() {
        return null;
    }

}
