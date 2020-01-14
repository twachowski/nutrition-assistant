package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.EnergyUnit;
import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;
import pl.polsl.wachowski.nutritionassistant.def.measure.Unit;

public enum GeneralNutrient implements Nutrient {

    ENERGY(EnergyUnit.KCAL),
    WATER(MassUnit.GRAM),
    CAFFEINE(MassUnit.MILLIGRAM),
    ALCOHOL(MassUnit.MILLIGRAM);

    private final Unit unit;

    GeneralNutrient(final Unit unit) {
        this.unit = unit;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

}
