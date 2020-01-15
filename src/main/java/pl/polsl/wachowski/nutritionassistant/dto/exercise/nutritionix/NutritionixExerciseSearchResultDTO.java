package pl.polsl.wachowski.nutritionassistant.dto.exercise.nutritionix;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import java.util.List;

@Value
public class NutritionixExerciseSearchResultDTO {

    List<NutritionixExerciseDetailsDTO> exercises;

    @JsonCreator
    public NutritionixExerciseSearchResultDTO(final List<NutritionixExerciseDetailsDTO> exercises) {
        this.exercises = exercises;
    }

}
