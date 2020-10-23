package pl.polsl.wachowski.nutritionassistant.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityLevel {

    NONE(1.f),
    SEDENTARY(1.2f),
    LIGHT(1.375f),
    MODERATE(1.55f),
    HIGH(1.725f),
    EXTRA(1.9f);

    private final float modifier;

}
