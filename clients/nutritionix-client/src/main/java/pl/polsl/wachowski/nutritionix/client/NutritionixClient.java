package pl.polsl.wachowski.nutritionix.client;

import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

public interface NutritionixClient {

    NutritionixResult<NutritionixFoodSearchResponse> searchFoods(String query);

    NutritionixResult<NutritionixFood> getFood(String id);

}
