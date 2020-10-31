package pl.polsl.wachowski.nutritionix.client.api.exercise.search;

import lombok.Value;

import java.util.List;

@Value
public class NutritionixExerciseSearchResponse {

    List<NutritionixExercise> exercises;

}
