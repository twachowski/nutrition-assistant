package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Carbohydrate implements Nutrient {

    SUGAR,
    FIBER,
    STARCH;

    public static Map<String, Carbohydrate> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(Carbohydrate::getName, Function.identity()));

    @Override
    public NutrientMassUnit getUnit() {
        return NutrientMassUnit.GRAM;
    }

    @Override
    public String getName() {
        return name();
    }

}
