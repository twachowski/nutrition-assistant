package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ExerciseSearchRequestDTO {

    @NotBlank(message = "Query must not be blank")
    String query;

}
