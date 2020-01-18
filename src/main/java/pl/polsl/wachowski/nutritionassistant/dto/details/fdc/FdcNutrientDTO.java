package pl.polsl.wachowski.nutritionassistant.dto.details.fdc;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.dto.details.ExternalNutrientDetails;

@Value
public class FdcNutrientDTO implements ExternalNutrientDetails {

    FdcNutrientDefinitionDTO nutrient;

    Float amount;

    @Override
    public Integer getId() {
        return nutrient.getId();
    }

    @Override
    public Float getAmount() {
        return amount;
    }

}
