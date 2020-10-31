package pl.polsl.wachowski.nutritionix.client.api.food.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixCommonFood implements NutritionixBasicFood {

    @JsonProperty("food_name")
    String name;

    @Override
    public String getId() {
        return name;
    }

    @Override
    public String getFoodName() {
        return name;
    }

    @Override
    public String getBrandName() {
        return null;
    }

}
