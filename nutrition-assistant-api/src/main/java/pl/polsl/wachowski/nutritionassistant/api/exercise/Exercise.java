package pl.polsl.wachowski.nutritionassistant.api.exercise;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Exercise {

    String name;
    BigDecimal kcalPerMin;

}
