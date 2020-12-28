package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.api.diary.entry.food.FoodEntry;
import pl.polsl.wachowski.nutritionassistant.api.food.NutritionDataProvider;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;

import java.math.BigDecimal;

public final class FoodEntrySamples {

    private FoodEntrySamples() {
    }

    public static FoodEntry foodEntry() {
        return new FoodEntry("12345",
                             NutritionDataProvider.USDA,
                             FoodMassUnit.GRAM,
                             BigDecimal.TEN);
    }

}
