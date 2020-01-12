package pl.polsl.wachowski.nutritionassistant.dto.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class FoodSearchCriteriaDTO {

    @NotBlank(message = "Query must not be blank")
    private String query;

    @JsonCreator
    public FoodSearchCriteriaDTO(final String query) {
        this.query = query;
    }

}
