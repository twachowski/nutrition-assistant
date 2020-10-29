package pl.polsl.wachowski.nutritionix.client.api.food;

import lombok.Value;

import java.util.List;

@Value
public class NutritionixFoodResponse {

    List<NutritionixFood> foods;

}
