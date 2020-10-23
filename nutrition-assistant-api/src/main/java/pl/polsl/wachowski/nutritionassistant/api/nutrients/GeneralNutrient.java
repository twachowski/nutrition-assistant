package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.EnergyUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.Unit;

@AllArgsConstructor
public enum GeneralNutrient implements Nutrient {

    ENERGY(EnergyUnit.KCAL),
    TOTAL_CARBS(NutrientMassUnit.GRAM),
    TOTAL_FAT(NutrientMassUnit.GRAM),
    TOTAL_PROTEIN(NutrientMassUnit.GRAM),
    WATER(NutrientMassUnit.GRAM),
    CAFFEINE(NutrientMassUnit.MILLIGRAM),
    ALCOHOL(NutrientMassUnit.MILLIGRAM);

    private final Unit unit;

    @Override
    public Unit getUnit() {
        return unit;
    }

}
