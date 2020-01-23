package pl.polsl.wachowski.nutritionassistant.dto.details.fdc;

import lombok.Value;

import java.util.List;

@Value
public class FdcFoodDetailsDTO {

    String description;

    String brandOwner;

    List<FdcNutrientDTO> foodNutrients;

}
