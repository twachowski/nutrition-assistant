package pl.polsl.wachowski.nutritionassistant.samples;

import pl.polsl.wachowski.nutritionassistant.api.user.ActivityLevel;
import pl.polsl.wachowski.nutritionassistant.api.user.Sex;
import pl.polsl.wachowski.nutritionassistant.api.user.UserBiometrics;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class UserBiometricsSamples {

    private UserBiometricsSamples() {
    }

    public static UserBiometrics shortFemale() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 165,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNullDateOfBirth() {
        return new UserBiometrics(null,
                                  Sex.FEMALE,
                                  (short) 165,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNullSex() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  null,
                                  (short) 165,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNullHeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  null,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNegativeHeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) -100,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withZeroHeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 0,
                                  new BigDecimal("57.24"),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNullWeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 160,
                                  null,
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNegativeWeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 160,
                                  BigDecimal.valueOf(-100),
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withZeroWeight() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 160,
                                  BigDecimal.ZERO,
                                  ActivityLevel.MODERATE,
                                  (short) 2000);
    }

    public static UserBiometrics withNullActivityLevel() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 160,
                                  BigDecimal.valueOf(160),
                                  null,
                                  (short) 2000);
    }

    public static UserBiometrics withNullCalorieGoal() {
        return new UserBiometrics(LocalDate.of(1990, 1, 1),
                                  Sex.FEMALE,
                                  (short) 160,
                                  BigDecimal.valueOf(160),
                                  ActivityLevel.MODERATE,
                                  null);
    }

}
