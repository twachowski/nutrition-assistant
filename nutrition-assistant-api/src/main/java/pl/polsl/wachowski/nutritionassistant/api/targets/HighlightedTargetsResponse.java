package pl.polsl.wachowski.nutritionassistant.api.targets;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.api.nutrients.Nutrient;

@Value
public class HighlightedTargetsResponse {

    Nutrient target1;
    Nutrient target2;
    Nutrient target3;
    Nutrient target4;
    Nutrient target5;
    Nutrient target6;

}
