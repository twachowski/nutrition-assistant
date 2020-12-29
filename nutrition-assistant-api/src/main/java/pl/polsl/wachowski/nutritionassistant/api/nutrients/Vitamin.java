package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum Vitamin implements Nutrient {

    A(NutrientMassUnit.IU),
    B1,
    B2,
    B3,
    B4,
    B5,
    B6,
    B7(NutrientMassUnit.MICROGRAM),
    B12(NutrientMassUnit.MICROGRAM),
    C,
    D(NutrientMassUnit.IU),
    E,
    FOLATE(NutrientMassUnit.MICROGRAM),
    K(NutrientMassUnit.MICROGRAM);

    public static Map<String, Vitamin> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(Vitamin::getName, Function.identity()));

    private final NutrientMassUnit unit;

    Vitamin() {
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
