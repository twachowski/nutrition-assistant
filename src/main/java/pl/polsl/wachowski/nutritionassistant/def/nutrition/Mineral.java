package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;

public enum Mineral implements Nutrient {

    CALCIUM(MassUnit.MILLIGRAM),
    COPPER(MassUnit.MILLIGRAM),
    FLUORIDE(MassUnit.MILLIGRAM),
    IODINE(MassUnit.MICROGRAM),
    IRON(MassUnit.MILLIGRAM),
    MAGNESIUM(MassUnit.MILLIGRAM),
    MANGANESE(MassUnit.MILLIGRAM),
    PHOSPHORUS(MassUnit.MILLIGRAM),
    POTASSIUM(MassUnit.MILLIGRAM),
    SELENIUM(MassUnit.MICROGRAM),
    SODIUM(MassUnit.MILLIGRAM),
    ZINC(MassUnit.MILLIGRAM);

    private final MassUnit unit;

    Mineral(final MassUnit unit) {
        this.unit = unit;
    }

    @Override
    public MassUnit getUnit() {
        return unit;
    }

}
