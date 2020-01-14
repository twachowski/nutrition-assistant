package pl.polsl.wachowski.nutritionassistant.dto.details;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Nutrient;

@Value
public class NutrientDetailDTO {

    Nutrient nutrient;

    Float amount;

}
