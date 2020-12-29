package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.EnergyUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.Unit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum GeneralNutrient implements Nutrient {

    ENERGY(EnergyUnit.KCAL),
    TOTAL_CARBS(NutrientMassUnit.GRAM),
    TOTAL_FAT(NutrientMassUnit.GRAM),
    TOTAL_PROTEIN(NutrientMassUnit.GRAM),
    WATER(NutrientMassUnit.GRAM),
    CAFFEINE(NutrientMassUnit.MILLIGRAM),
    ALCOHOL(NutrientMassUnit.MILLIGRAM);

    public static Map<String, GeneralNutrient> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(GeneralNutrient::getName, Function.identity()));

    private final Unit unit;

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public String getName() {
        return name();
    }

}
