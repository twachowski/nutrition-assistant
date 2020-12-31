package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.food.Food;

import java.util.Collections;

public final class FoodSamples {

    private FoodSamples() {
    }

    public static Food mockFood() {
        return new Food("mock food",
                        "mock brand",
                        Collections.emptySet());
    }

}
