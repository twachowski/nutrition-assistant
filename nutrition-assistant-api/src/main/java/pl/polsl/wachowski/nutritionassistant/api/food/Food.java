package pl.polsl.wachowski.nutritionassistant.api.food;

import lombok.Value;

import java.util.Set;

@Value
public class Food {

    String name;
    String brand;
    Set<NutrientDetails> nutrients;

}
