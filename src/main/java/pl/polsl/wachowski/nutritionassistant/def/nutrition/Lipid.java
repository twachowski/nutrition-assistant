package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;

public enum Lipid implements Nutrient {

    SATURATED_FAT(MassUnit.GRAM),
    MONOUNSATURATED_FAT(MassUnit.GRAM),
    POLYUNSATURATED_FAT(MassUnit.GRAM),
    OMEGA6(MassUnit.GRAM),
    OMEGA3(MassUnit.GRAM),
    TRANS_FAT(MassUnit.GRAM),
    CHOLESTEROL(MassUnit.MILLIGRAM);

    private final MassUnit unit;

    Lipid(final MassUnit unit) {
        this.unit = unit;
    }

    @Override
    public MassUnit getUnit() {
        return unit;
    }

}
