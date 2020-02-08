package pl.polsl.wachowski.nutritionassistant.dto.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ExerciseSearchRequestDTO {

    @NotBlank(message = "Query must not be blank")
    String query;

    @JsonCreator
    public ExerciseSearchRequestDTO(final String query) {
        this.query = query;
    }

}
