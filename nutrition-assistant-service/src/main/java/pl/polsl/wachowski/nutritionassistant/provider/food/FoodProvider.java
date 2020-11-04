package pl.polsl.wachowski.nutritionassistant.provider.food;

import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;

import java.util.Set;

public interface FoodProvider {

    Set<FoodBasicData> searchFoods(String query);

    Food getFood(String id);

    NutritionDataProvider getType();

}
