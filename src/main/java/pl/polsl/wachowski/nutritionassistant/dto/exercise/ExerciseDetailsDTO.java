package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;

@Value
public class ExerciseDetailsDTO {

    String name;

    Float durationMin;

    Float calories;

}
