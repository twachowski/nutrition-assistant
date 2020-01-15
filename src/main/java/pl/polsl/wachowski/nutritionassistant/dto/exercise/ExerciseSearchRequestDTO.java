package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ExerciseSearchRequestDTO {

    @NotBlank(message = "User must not be blank")
    String user;

    @NotBlank(message = "Query must not be blank")
    String query;

}
