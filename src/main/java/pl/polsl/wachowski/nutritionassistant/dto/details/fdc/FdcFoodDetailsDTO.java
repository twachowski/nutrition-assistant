package pl.polsl.wachowski.nutritionassistant.dto.details.fdc;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.util.List;

@Value
public class FdcFoodDetailsDTO {

    List<FdcNutrientDTO> foodNutrients;

    @JsonCreator
    public FdcFoodDetailsDTO(final List<FdcNutrientDTO> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

}
