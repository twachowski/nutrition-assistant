package pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FdcFoodSearchRequest {

    @JsonProperty("generalSearchInput")
    String query;

}
