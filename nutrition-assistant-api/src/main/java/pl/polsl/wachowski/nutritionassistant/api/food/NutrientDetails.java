package pl.polsl.wachowski.nutritionassistant.api.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;

@Value
public class NutrientDetails {

    Nutrient nutrient;
    float amount;

    public NutrientDetails multiply(final float factor) {
        return new NutrientDetails(nutrient, amount * factor);
    }

}
