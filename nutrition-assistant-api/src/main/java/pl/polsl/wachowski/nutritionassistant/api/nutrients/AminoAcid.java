package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.Unit;

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

    @Override
    public Unit getUnit() {
        return NutrientMassUnit.GRAM;
    }

}
