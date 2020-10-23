package pl.polsl.wachowski.nutritionassistant.util;


import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.math.BigDecimal;

public final class AmountConverter {

    private static final float OUNCE_GRAMS = 28.35f;
    private static final BigDecimal SIXTY = BigDecimal.valueOf(60);

    private AmountConverter() {
    }

    public static float getFoodAmountFactor(final float amount, final FoodMassUnit unit) {
        final float amountGrams = FoodMassUnit.GRAM.equals(unit) ? amount : amount * OUNCE_GRAMS;
        return amountGrams / 100.f;
    }

    public static BigDecimal getExerciseCalories(final BigDecimal kcalPerMin,
                                                 final BigDecimal duration,
                                                 final TimeUnit unit) {
        final BigDecimal factor = TimeUnit.MINUTE.equals(unit) ? BigDecimal.ONE : SIXTY;
        return duration.multiply(factor).multiply(kcalPerMin);
    }

}
