package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class NutritionixFoodDetailsDTO {

    private final List<NutritionixNutrientDTO> nutrients;

    @JsonCreator
    public NutritionixFoodDetailsDTO(@JsonProperty("foods") final List<NutritionixFoodDTO> foods) {
        final NutritionixFoodDTO food = foods.get(0);
        this.nutrients = food.getNutrients();
    }

}
