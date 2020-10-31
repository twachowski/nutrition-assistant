package pl.polsl.wachowski.nutritionix.client;

import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExercise;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchRequest;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

import java.util.List;

public interface NutritionixClient {

    NutritionixResult<NutritionixFoodSearchResponse> searchFoods(String query);

    NutritionixResult<NutritionixFood> getFood(String id);

    NutritionixResult<List<NutritionixExercise>> searchExercises(NutritionixExerciseSearchRequest request);

}
