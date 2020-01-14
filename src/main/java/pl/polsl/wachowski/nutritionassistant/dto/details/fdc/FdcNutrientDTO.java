package pl.polsl.wachowski.nutritionassistant.dto.details.fdc;

import lombok.Value;

@Value
public class FdcNutrientDTO {

    FdcNutrientDefinitionDTO nutrient;

    Float amount;

    public Integer getId() {
        return nutrient.getId();
    }

}
