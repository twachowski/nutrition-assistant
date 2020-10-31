package pl.polsl.wachowski.nutritionix.client.api.exercise.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class NutritionixExerciseSearchRequest {

    String query;
    String gender;
    Short age;

    @JsonProperty("height_cm")
    Short height;

    @JsonProperty("weight_kg")
    BigDecimal weight;

}
