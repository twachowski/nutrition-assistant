package pl.polsl.wachowski.nutritionassistant.api.diary.entry.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.food.NutrientDetails;

import java.math.BigDecimal;
import java.util.Set;

@Value
public class FoodEntryDetails {

    String name;
    String brand;
    FoodMassUnit massUnit;
    BigDecimal amount;
    Set<NutrientDetails> nutrients;
    Short position;

}
