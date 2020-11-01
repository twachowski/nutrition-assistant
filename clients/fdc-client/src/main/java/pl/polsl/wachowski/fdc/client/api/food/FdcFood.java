package pl.polsl.wachowski.fdc.client.api.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Set;

@Value
public class FdcFood {

    String description;
    String brandOwner;
    @JsonProperty("foodNutrients")
    Set<FdcNutrient> nutrients;

}
