package pl.polsl.wachowski.nutritionassistant.api.food;

import lombok.Value;

@Value
public class FoodBasicData {

    String id;
    NutritionDataProvider provider;
    String name;
    String brand;

}
