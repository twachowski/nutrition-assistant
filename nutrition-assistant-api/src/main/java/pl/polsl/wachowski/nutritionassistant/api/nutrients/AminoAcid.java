package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.Unit;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AminoAcid implements Nutrient {

    CYSTEINE,
    HISTIDINE,
    ISOLEUCINE,
    LEUCINE,
    LYSINE,
    METHIONINE,
    PHENYLALANINE,
    THREONINE,
    TRYPTOPHAN,
    TYROSINE,
    VALINE;

    public static Map<String, AminoAcid> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(AminoAcid::getName, Function.identity()));

    @Override
    public Unit getUnit() {
        return NutrientMassUnit.GRAM;
    }

    @Override
    public String getName() {
        return name();
    }

}
