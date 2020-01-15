package pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NutritionixExerciseDetailsDTO {

    String name;

    @JsonProperty("duration_min")
    Float duration;

    @JsonProperty("nf_calories")
    Float calories;

}
