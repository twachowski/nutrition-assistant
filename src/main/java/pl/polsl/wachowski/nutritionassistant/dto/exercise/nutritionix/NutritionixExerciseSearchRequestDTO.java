package pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class NutritionixExerciseSearchRequestDTO {

    String query;

    String gender;

    Short age;

    @JsonProperty("height_cm")
    Short height;

    @JsonProperty("weight_kg")
    BigDecimal weight;

}
