package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;

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
    public MassUnit getUnit() {
        return MassUnit.GRAM;
    }

}
