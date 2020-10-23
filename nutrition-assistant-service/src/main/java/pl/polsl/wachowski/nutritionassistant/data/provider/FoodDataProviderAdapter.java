package pl.polsl.wachowski.nutritionassistant.data.provider;

import pl.polsl.wachowski.nutritionassistant.api.food.Food;
import pl.polsl.wachowski.nutritionassistant.api.food.FoodBasicData;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;

import java.util.Set;

public interface FoodDataProviderAdapter {

    Set<FoodBasicData> search(String query);

    Food getFood(String id);

    NutritionDataProvider getProviderType();

}
