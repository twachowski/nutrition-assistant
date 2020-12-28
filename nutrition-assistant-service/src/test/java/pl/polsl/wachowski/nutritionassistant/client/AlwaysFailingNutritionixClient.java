package pl.polsl.wachowski.nutritionassistant.client;

import pl.polsl.wachowski.nutritionix.client.NutritionixClient;
import pl.polsl.wachowski.nutritionix.client.NutritionixResult;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExercise;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchRequest;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

import java.util.List;

public class AlwaysFailingNutritionixClient implements NutritionixClient {

    @Override
    public NutritionixResult<NutritionixFoodSearchResponse> searchFoods(final String query) {
        return NutritionixResult.failure(new RuntimeException("Always fails"));
    }

    @Override
    public NutritionixResult<NutritionixFood> getFood(final String id) {
        return NutritionixResult.failure(new RuntimeException("Always fails"));
    }

    @Override
    public NutritionixResult<List<NutritionixExercise>> searchExercises(final NutritionixExerciseSearchRequest request) {
        return NutritionixResult.failure(new RuntimeException("Always fails"));
    }

}
