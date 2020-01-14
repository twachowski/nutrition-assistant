package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;

public enum Vitamin implements Nutrient {

    A(MassUnit.IU),
    B1(MassUnit.MILLIGRAM),
    B2(MassUnit.MILLIGRAM),
    B3(MassUnit.MILLIGRAM),
    B4(MassUnit.MILLIGRAM),
    B5(MassUnit.MILLIGRAM),
    B6(MassUnit.MILLIGRAM),
    B7(MassUnit.MICROGRAM),
    B12(MassUnit.MICROGRAM),
    C(MassUnit.MILLIGRAM),
    D(MassUnit.IU),
    E(MassUnit.MILLIGRAM),
    FOLATE(MassUnit.MICROGRAM),
    K(MassUnit.MICROGRAM);

    private final MassUnit unit;

    Vitamin(final MassUnit unit) {
        this.unit = unit;
    }

    @Override
    public MassUnit getUnit() {
        return unit;
    }

}
