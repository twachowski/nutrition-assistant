package pl.polsl.wachowski.nutritionassistant.dto.search.fdc;

import lombok.Value;

@Value
public class FdcFoodItemDTO {

    Long fdcId;

    String description;

    String dataType;

    String brandOwner;

}
