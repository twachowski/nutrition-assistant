package pl.polsl.wachowski.nutritionassistant.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.polsl.wachowski.nutritionassistant.def.nutrition.Nutrient;

@Data
@AllArgsConstructor
public class NutrientDetailDTO {

    private final Nutrient nutrient;

    private Float amount;

    public void multiply(final float factor) {
        this.amount *= factor;
    }

}
