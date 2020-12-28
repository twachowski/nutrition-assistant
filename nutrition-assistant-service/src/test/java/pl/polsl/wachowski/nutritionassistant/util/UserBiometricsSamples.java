package pl.polsl.wachowski.nutritionassistant.util;

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

}
