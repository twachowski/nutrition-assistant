package pl.polsl.wachowski.nutritionassistant.dto.details;

import lombok.Value;

import java.util.List;

@Value
public class FoodDetailsDTO {

    private final List<NutrientDetailDTO> nutrientDetails;

}
