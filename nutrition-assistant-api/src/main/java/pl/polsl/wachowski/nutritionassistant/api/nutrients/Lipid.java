package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum Lipid implements Nutrient {

    SATURATED_FAT,
    MONOUNSATURATED_FAT,
    POLYUNSATURATED_FAT,
    OMEGA6,
    OMEGA3_ALA,
    OMEGA3_DPA,
    OMEGA3_EPA,
    OMEGA3_DHA,
    OMEGA3,
    TRANS_FAT,
    CHOLESTEROL(NutrientMassUnit.MILLIGRAM);

    public static Map<String, Lipid> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(Lipid::getName, Function.identity()));

    private final NutrientMassUnit unit;

    Lipid() {
        this.unit = NutrientMassUnit.GRAM;
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
