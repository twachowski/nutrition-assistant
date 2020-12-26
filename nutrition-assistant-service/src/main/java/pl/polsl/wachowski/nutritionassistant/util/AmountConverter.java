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
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must not be negative: amount=" + amount);
        }
        final float amountGrams = unit.equals(FoodMassUnit.GRAM) ? amount : amount * OUNCE_GRAMS;
        return amountGrams / 100.f;
    }

    public static BigDecimal getExerciseCalories(final BigDecimal kcalPerMin,
                                                 final BigDecimal duration,
                                                 final TimeUnit unit) {
        if (kcalPerMin.signum() != 1) {
            throw new IllegalArgumentException("kcalPerMin must be positive: kcalPerMin=" + kcalPerMin);
        }
        if (duration.signum() == -1) {
            throw new IllegalArgumentException("Duration must not be negative: duration=" + duration);
        }
        final BigDecimal factor = unit.equals(TimeUnit.MINUTE) ? BigDecimal.ONE : SIXTY;
        return duration.multiply(factor).multiply(kcalPerMin);
    }

}
