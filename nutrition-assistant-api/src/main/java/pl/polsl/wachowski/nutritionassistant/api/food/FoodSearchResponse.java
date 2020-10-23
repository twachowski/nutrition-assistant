package pl.polsl.wachowski.nutritionassistant.api.food;

import lombok.Value;

import java.util.Set;

@Value
public class FoodSearchResponse {

    Set<FoodBasicData> foods;

}
