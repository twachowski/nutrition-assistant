package pl.polsl.wachowski.nutritionassistant.def.nutrition;

import pl.polsl.wachowski.nutritionassistant.def.measure.MassUnit;

public enum Carbohydrate implements Nutrient {

    SUGAR,
    SALT,
    FIBER,
    STARCH;

    @Override
    public MassUnit getUnit() {
        return MassUnit.GRAM;
    }

}
