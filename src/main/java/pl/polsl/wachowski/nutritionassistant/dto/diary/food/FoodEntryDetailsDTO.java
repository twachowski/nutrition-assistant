package pl.polsl.wachowski.nutritionassistant.dto.diary.food;

import lombok.Value;
import pl.polsl.wachowski.nutritionassistant.def.measure.FoodMeasureUnit;
import pl.polsl.wachowski.nutritionassistant.dto.details.NutrientDetailDTO;

import java.math.BigDecimal;
import java.util.List;

@Value
public class FoodEntryDetailsDTO {

    String name;

    String brandName;

    BigDecimal amount;

    FoodMeasureUnit unit;

    Short position;

    List<NutrientDetailDTO> nutrients;

}
