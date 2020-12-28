package pl.polsl.wachowski.nutritionassistant.client;

import pl.polsl.wachowski.nutritionix.client.NutritionixClient;
import pl.polsl.wachowski.nutritionix.client.NutritionixResult;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExercise;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchRequest;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class MockNutritionixClient implements NutritionixClient {

    @Override
    public NutritionixResult<NutritionixFoodSearchResponse> searchFoods(final String query) {
        final NutritionixFoodSearchResponse response = new NutritionixFoodSearchResponse(Collections.emptyList(),
                                                                                         Collections.emptyList());
        return NutritionixResult.success(response);
    }

    @Override
    public NutritionixResult<NutritionixFood> getFood(final String id) {
        final NutritionixFood food = new NutritionixFood("mock food",
                                                         "mock brand",
                                                         Collections.emptySet());
        return NutritionixResult.success(food);
    }

    @Override
    public NutritionixResult<List<NutritionixExercise>> searchExercises(final NutritionixExerciseSearchRequest request) {
        final NutritionixExercise exercise = new NutritionixExercise("running",
                                                                     BigDecimal.valueOf(60),
                                                                     BigDecimal.valueOf(300));
        return NutritionixResult.success(Collections.singletonList(exercise));
    }

}
