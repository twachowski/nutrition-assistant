package pl.polsl.wachowski.nutritionassistant.dto.details.fdc;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

@Value
public class FdcNutrientDefinitionDTO {

    Integer id;

    @JsonCreator
    public FdcNutrientDefinitionDTO(final Integer id) {
        this.id = id;
    }

}
