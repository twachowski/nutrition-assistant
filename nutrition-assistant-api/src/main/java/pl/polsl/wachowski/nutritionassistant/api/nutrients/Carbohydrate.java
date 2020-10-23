package pl.polsl.wachowski.nutritionassistant.api.nutrients;

import pl.polsl.wachowski.nutritionassistant.api.units.NutrientMassUnit;

public enum Carbohydrate implements Nutrient {

    SUGAR,
    FIBER,
    STARCH;

    @Override
    public NutrientMassUnit getUnit() {
        return NutrientMassUnit.GRAM;
    }

}
