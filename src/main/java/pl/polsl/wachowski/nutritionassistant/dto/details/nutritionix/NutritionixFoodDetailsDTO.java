package pl.polsl.wachowski.nutritionassistant.dto.details.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class NutritionixFoodDetailsDTO {

    private final String name;

    private final String brandName;

    private final List<NutritionixNutrientDTO> nutrients;

    @JsonCreator
    public NutritionixFoodDetailsDTO(@JsonProperty("foods") final List<NutritionixFoodDTO> foods) {
        final NutritionixFoodDTO food = foods.get(0);
        this.name = food.getName();
        this.brandName = food.getBrandName();
        this.nutrients = food.getNutrients();
    }

}
