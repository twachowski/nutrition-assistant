package pl.polsl.wachowski.nutritionix.client.api.exercise.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class NutritionixExercise {

    String name;

    @JsonProperty("duration_min")
    BigDecimal duration;

    @JsonProperty("nf_calories")
    BigDecimal calories;

    public BigDecimal getKcalPerMin() {
        return calories.divide(duration, 2, RoundingMode.HALF_UP);
    }

}
