package pl.polsl.wachowski.nutritionassistant.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.polsl.wachowski.nutritionassistant.api.units.FoodMassUnit;
import pl.polsl.wachowski.nutritionassistant.api.units.TimeUnit;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountConverterTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException if negative amount is provided")
    void shouldThrowIllegalArgumentExceptionIfNegativeAmountIsProvided() {
        //given
        final float amount = -15.f;
        final FoodMassUnit unit = FoodMassUnit.GRAM;
        final Executable executable = () -> AmountConverter.getFoodAmountFactor(amount, unit);

        //when

        //then
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertTrue(exception.getMessage().contains("Amount must not be negative"));
    }

    @Test
    @DisplayName("Should throw NPE if null food mass unit is provided")
    void shouldThrowNpeIfNullFoodMassUnitIsProvided() {
        //given
        final float amount = 10.f;
        final Executable executable = () -> AmountConverter.getFoodAmountFactor(amount, null);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should divide amount by 100 for gram unit")
    void shouldDivideAmountBy100ForGramUnit() {
        //given
        final float amount = 50.f;
        final FoodMassUnit unit = FoodMassUnit.GRAM;

        //when
        final float factor = AmountConverter.getFoodAmountFactor(amount, unit);

        //then
        assertEquals(0.5f, factor);
    }

    @Test
    @DisplayName("Should convert amount to grams and divide by 100 for ounce unit")
    void shouldConvertAmountToGramsAndDivideBy100ForOunceUnit() {
        //given
        final float amount = 10.f;
        final FoodMassUnit unit = FoodMassUnit.OUNCE;

        //when
        final float factor = AmountConverter.getFoodAmountFactor(amount, unit);

        //then
        assertEquals(2.835f, factor);
    }

    @Test
    @DisplayName("Should return zero if amount is zero and unit is gram")
    void shouldReturnZeroIfAmountIsZeroAndUnitIsGram() {
        //given
        final float amount = 0.f;
        final FoodMassUnit unit = FoodMassUnit.GRAM;

        //when
        final float factor = AmountConverter.getFoodAmountFactor(amount, unit);

        //then
        assertEquals(0.f, factor);
    }

    @Test
    @DisplayName("Should return zero if amount is zero and unit is ounce")
    void shouldReturnZeroIfAmountIsZeroAndUnitIsOunce() {
        //given
        final float amount = 0.f;
        final FoodMassUnit unit = FoodMassUnit.OUNCE;

        //when
        final float factor = AmountConverter.getFoodAmountFactor(amount, unit);

        //then
        assertEquals(0.f, factor);
    }

    @Test
    @DisplayName("Should throw NPE if null kcalPerMin is provided")
    void shouldThrowNpeIfNullKcalPerMinIsProvided() {
        //given
        final BigDecimal duration = BigDecimal.ONE;
        final TimeUnit timeUnit = TimeUnit.HOUR;
        final Executable executable = () -> AmountConverter.getExerciseCalories(null, duration, timeUnit);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw NPE if null duration is provided")
    void shouldThrowNpeIfNullDurationIsProvided() {
        //given
        final BigDecimal kcalPerMin = BigDecimal.ONE;
        final TimeUnit timeUnit = TimeUnit.HOUR;
        final Executable executable = () -> AmountConverter.getExerciseCalories(kcalPerMin, null, timeUnit);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw NPE if null time unit is provided")
    void shouldThrowNpeIfNullTimeUnitIsProvided() {
        //given
        final BigDecimal kcalPerMin = BigDecimal.ONE;
        final BigDecimal duration = BigDecimal.ONE;
        final Executable executable = () -> AmountConverter.getExerciseCalories(kcalPerMin, duration, null);

        //when

        //then
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException if kcalPerMin is zero")
    void shouldThrowIllegalArgumentExceptionIfKcalPerMinIsZero() {
        //given
        final BigDecimal kcalPerMin = BigDecimal.ZERO;
        final BigDecimal duration = BigDecimal.ONE;
        final TimeUnit timeUnit = TimeUnit.HOUR;
        final Executable executable = () -> AmountConverter.getExerciseCalories(kcalPerMin, duration, timeUnit);

        //when

        //then
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertTrue(exception.getMessage().contains("kcalPerMin must be positive"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException if kcalPerMin is negative")
    void shouldThrowIllegalArgumentExceptionIfKcalPerMinIsNegative() {
        //given
        final BigDecimal kcalPerMin = BigDecimal.ONE.negate();
        final BigDecimal duration = BigDecimal.ONE;
        final TimeUnit timeUnit = TimeUnit.HOUR;
        final Executable executable = () -> AmountConverter.getExerciseCalories(kcalPerMin, duration, timeUnit);

        //when

        //then
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertTrue(exception.getMessage().contains("kcalPerMin must be positive"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException if duration is negative")
    void shouldThrowIllegalArgumentExceptionIfDurationIsNegative() {
        //given
        final BigDecimal kcalPerMin = BigDecimal.ONE;
        final BigDecimal duration = BigDecimal.ONE.negate();
        final TimeUnit timeUnit = TimeUnit.HOUR;
        final Executable executable = () -> AmountConverter.getExerciseCalories(kcalPerMin, duration, timeUnit);

        //when

        //then
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertTrue(exception.getMessage().contains("Duration must not be negative"));
    }

    @Test
    @DisplayName("Should multiply calories by duration if time unit is minute")
    void shouldMultiplyCaloriesByDurationIfTimeUnitIsMinute() {
        //given
        final BigDecimal kcalPerMin = new BigDecimal("12.757");
        final BigDecimal duration = BigDecimal.TEN;
        final TimeUnit unit = TimeUnit.MINUTE;

        //when
        final BigDecimal calories = AmountConverter.getExerciseCalories(kcalPerMin, duration, unit);

        //then
        assertEquals(0, new BigDecimal("127.57").compareTo(calories));
    }

    @Test
    @DisplayName("Should convert duration in hours to minutes and multiply by kcalPerMin")
    void shouldConvertDurationInHoursToMinutesAndMultiplyByKcalPerMin() {
        //given
        final BigDecimal kcalPerMin = new BigDecimal("7.6");
        final BigDecimal duration = new BigDecimal("2");
        final TimeUnit unit = TimeUnit.HOUR;

        //when
        final BigDecimal calories = AmountConverter.getExerciseCalories(kcalPerMin, duration, unit);

        //then
        assertEquals(0, BigDecimal.valueOf(912).compareTo(calories));
    }

    @Test
    @DisplayName("Should return zero if duration is zero and time unit is minute")
    void shouldReturnZeroIfDurationIsZeroAndTimeUnitIsMinute() {
        //given
        final BigDecimal kcalPerMin = new BigDecimal("1.52");
        final BigDecimal duration = BigDecimal.ZERO;
        final TimeUnit unit = TimeUnit.MINUTE;

        //when
        final BigDecimal calories = AmountConverter.getExerciseCalories(kcalPerMin, duration, unit);

        //then
        assertEquals(0, BigDecimal.ZERO.compareTo(calories));
    }

    @Test
    @DisplayName("Should return zero if duration is zero and time unit is hour")
    void shouldReturnZeroIfDurationIsZeroAndTimeUnitIsHour() {
        //given
        final BigDecimal kcalPerMin = new BigDecimal("1.52");
        final BigDecimal duration = BigDecimal.ZERO;
        final TimeUnit unit = TimeUnit.HOUR;

        //when
        final BigDecimal calories = AmountConverter.getExerciseCalories(kcalPerMin, duration, unit);

        //then
        assertEquals(0, BigDecimal.ZERO.compareTo(calories));
    }

}
