package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum Mineral implements Nutrient {

    CALCIUM,
    COPPER,
    FLUORIDE,
    IRON,
    MAGNESIUM,
    MANGANESE,
    PHOSPHORUS,
    POTASSIUM,
    SELENIUM(NutrientMassUnit.MICROGRAM),
    SODIUM,
    ZINC;

    public static Map<String, Mineral> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(Mineral::getName, Function.identity()));

    private final NutrientMassUnit unit;

    Mineral() {
        this.unit = NutrientMassUnit.MILLIGRAM;
    }

    @Override
    public NutrientMassUnit getUnit() {
        return unit;
    }

    @Override
    public String getName() {
        return name();
    }

}
