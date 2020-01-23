package pl.polsl.wachowski.nutritionassistant.util;

import pl.polsl.wachowski.nutritionassistant.def.measure.FoodMeasureUnit;
import pl.polsl.wachowski.nutritionassistant.def.measure.TimeUnit;

public final class AmountConverter {

    private static final float OUNCE_GRAMS = 28.35f;

    private AmountConverter() {
    }

    public static float getFoodAmountFactor(final float amount, final FoodMeasureUnit unit) {
        final float amountGrams = FoodMeasureUnit.GRAM.equals(unit) ? amount : amount * OUNCE_GRAMS;
        return amountGrams / 100.f;
    }

    public static float getExerciseDurationCoeff(final float defaultDuration,
                                                 final float duration,
                                                 final TimeUnit unit) {
        final float durationMin = TimeUnit.MINUTE.equals(unit) ? duration : duration * 60;
        return durationMin / defaultDuration;
    }

}
