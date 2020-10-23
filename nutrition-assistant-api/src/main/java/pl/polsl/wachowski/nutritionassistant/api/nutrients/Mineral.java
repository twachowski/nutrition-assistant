package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import lombok.AllArgsConstructor;
import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

@AllArgsConstructor
public enum Mineral implements Nutrient {

    CALCIUM,
    COPPER,
    FLUORIDE,
    IRON,
    MAGNESIUM,
    MANGANESE,
    PHOSPHORUS,
    POTASSIUM,
    SELENIUM(NutrientMassUnit.MICROGRAM),
    SODIUM,
    ZINC;

    private final NutrientMassUnit unit;

    Mineral() {
        this.unit = NutrientMassUnit.MILLIGRAM;
    }

    @Override
    public NutrientMassUnit getUnit() {
        return unit;
    }

}
